import java.util.*;

//TODO: WorldMap doit seulement accepter un FileMap comme valeur , pas un integer

public class WordMap implements Map {
    private  static  class  Entry<K,V> {
        private Object key;
        private Object value;
        Entry<Object,Object> next;
        int hash;
        public Entry(Object key, Object value){
            this.key = key;
            this.value = value;
        }

        public void recordRemoval(WordMap kvWordMap) {
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Object getKey() {
            return key;
        }
    }


    private int size;
    private int capacity;
    private int numDeleted;
    transient volatile Set<Object>        keySet = null;
    transient volatile Collection<Object> values = null;
    private transient Set<Entry> entrySet = null;

    private Entry<Object,Object>[] table;
    /**
     * The next size value at which to resize (capacity * load factor).
     * @serial
     */
    int threshold;
    /**
     * The number of times this HashMap has been structurally modified
     * Structural modifications are those that change the number of mappings in
     * the HashMap or otherwise modify its internal structure (e.g.,
     * rehash).  This field is used to make iterators on Collection-views of
     * the HashMap fail-fast.  (See ConcurrentModificationException).
     */
    transient int modCount;
    public  static final double Max_LOAD = 0.75;
    private  final Entry<Object,Object> DELETED = new Entry(null,null);

    public WordMap(){
        size = 10;
        capacity = 1000;
        table = new Entry[capacity];
    }



    public int getCapacity() {
        return capacity;
    }

    @Override
    public int size() {
        return size;
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    private int find(Object key) {
        int index =  key.hashCode() % table.length;
        while(table[index] != null && !key.equals(table[index].key)){
            index = (index + 1) %  table.length;
        }
        return index;

    }
    @Override
    public boolean containsKey(Object key) {
        int index =  find(key);
        return table[index] != null;
    }
    @Override
    /**
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value.
     *
     * @param value value whose presence in this map is to be tested
     * @return <tt>true</tt> if this map maps one or more keys to the
     *         specified value
     */
    public boolean containsValue(Object value) {
        if (value == null)
            return containsNullValue();

        Entry[] tab = table;
        for (int i = 0; i < tab.length ; i++)
            for (Entry e = tab[i] ; e != null ; e = e.next)
                if (value.equals(e.value))
                    return true;
        return false;
    }

    /**
     * Returns index for hash code h.
     */
    static int indexFor(int h, int length) {
        return h & (length-1);
    }

    static int hash(int h) {
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * Returns the entry associated with the specified key in the
     * HashMap.  Returns null if the HashMap contains no mapping
     * for the key.
     */
    final Entry<Object,Object> getEntry(Object key) {
        int hash = (key == null) ? 0 : hash(key.hashCode());
        for (Entry<Object,Object> e = table[indexFor(hash, table.length)];
             e != null;
             e = (Entry<Object, Object>) e.next) {
            Object k;
            if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                return e;
        }
        return null;
    }

    /**
     * Special-case code for containsValue with null argument
     */
    private boolean containsNullValue() {
        Entry[] tab = table;
        for (int i = 0; i < tab.length ; i++)
            for (Entry e = tab[i] ; e != null ; e = e.next)
                if (e.value == null)
                    return true;
        return false;
    }
    @Override
    public Object get(Object key) {
        int index = key.hashCode() % capacity;
        while (true){
            if(table[index] == null){
                return null;
            }else if (key.equals(table[index].key)){
                return table[index].value;
            }else {
                index = (index + 1) % capacity;
            }
        }
    }

    @Override
    public Object put(Object key, Object value) {
        if (key == null){
            return null;
        }
        int index = key.hashCode() % capacity;
        while(true) {
            if (table [index] == null) {
                if(1.0*size/capacity >= Max_LOAD ){
                    rehash();
                }
                table[index] = (Entry<Object, Object>) new Entry<Object, Object>(key, value);
                size++;
                return null;
            }else if (key.equals(table[index].key)){
                Object old = table[index].value;
                table[index].value = value;
                return old;
            } else {
                index = (index + 1) % capacity;

            }
            modCount++;
        }
    }

    private void rehash(){
        size = 0;
        capacity = 2 * capacity + 1;
        Entry[] oldTable = table;
        table = new Entry[capacity];
        for (int i = 0; i < oldTable.length; i++){
            if(oldTable[i] == null){
                continue;
            }
            Object key =  oldTable[i].key;
            Object value = oldTable[i].value;
            this.put(key,value);

        }

    }


    @Override
    public Object remove(Object key) {
        int index = key.hashCode() % capacity;
        while (true){
            if(table[index] == null ){
                return null;
            }else if (key.equals(table[index].key)){
                Object old = table[index].value;
                table[index] = DELETED;
                size--;
                numDeleted++;
                return old;
            }else {
                index = (index + 1) % capacity;
            }
        }
    }
    @Override
    /**
     * Copies all of the mappings from the specified map to this map.
     * These mappings will replace any mappings that this map had for
     * any of the keys currently in the specified map.
     *
     * @param m mappings to be stored in this map
     * @throws NullPointerException if the specified map is null
     */
    public void putAll(Map m) {
        int numKeysToBeAdded = m.size();
        if (numKeysToBeAdded == 0)
            return;
        if (numKeysToBeAdded > threshold) {
            int targetCapacity = (int)(numKeysToBeAdded / Max_LOAD + 1);
            if (targetCapacity > capacity)
                targetCapacity = capacity;
            int newCapacity = table.length;
            while (newCapacity < targetCapacity)
                newCapacity <<= 1;
            if (newCapacity > table.length)
                rehash();
        }

    }

    public String toString(){
        StringBuilder out = new StringBuilder();
        for(Entry entry : table){
            if(entry== null  || entry == DELETED){
                continue;
            }
            out.append(entry + " ");
        }

        return out.toString();
    }
    @Override
    /**
     * Removes all of the mappings from this map.
     * The map will be empty after this call returns.
     */
    public void clear() {
        modCount++;
        Entry[] tab = table;
        for (int i = 0; i < tab.length; i++)
            tab[i] = null;
        size = 0;
    }

    public int count(){
        int count = 0;
        for (Entry e: table){
            if (e != null){
                count++;
            }
    }
        return count;
    }

    private abstract class HashIterator<E> implements Iterator<E> {
        Entry<Object,Object> next;        // next entry to return
        int expectedModCount;   // For fast-fail
        int index;              // current slot
        Entry<Object,Object> current;     // current entry

        HashIterator() {
            expectedModCount = modCount;
            if (size > 0) { // advance to first entry
                Entry[] t = table;
                while (index < t.length && (next = t[index++]) == null)
                    ;
            }
        }
    }


    @Override
    public Set<Object> keySet() {
        if (keySet == null) {
            keySet = new AbstractSet<Object>() {
                public Iterator<Object> iterator() {
                    return new Iterator<Object>() {
                        private Iterator<Entry> i = entrySet().iterator();

                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        public Object next() {
                            return i.next().getKey();
                        }

                        public void remove() {
                            i.remove();
                        }
                    };
                }

                public int size() {
                    return WordMap.this.size();
                }

                public boolean isEmpty() {
                    return WordMap.this.isEmpty();
                }

                public void clear() {
                    WordMap.this.clear();
                }

                public boolean contains(Object k) {
                    return WordMap.this.containsKey(k);
                }
            };
        }
        return (Set<Object>) keySet;
    }


    @Override
    public Collection values() {
        if (values == null) {
            values = new AbstractCollection<Object>() {
                public Iterator<Object> iterator() {
                    return new Iterator<Object>() {
                        private Iterator<Entry> i = entrySet().iterator();

                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        public Object next() {
                            return i.next().getValue();
                        }

                        public void remove() {
                            i.remove();
                        }
                    };
                }

                public int size() {
                    return WordMap.this.size();
                }

                public boolean isEmpty() {
                    return WordMap.this.isEmpty();
                }

                public void clear() {
                    WordMap.this.clear();
                }

                public boolean contains(Object v) {
                    return WordMap.this.containsValue(v);
                }
            };
        }
        return values;
    }

    public Set<Entry> entrySet() {
        Set<Entry> es = entrySet;
        return es != null ? es : (entrySet);
    }
    
    final Entry<Object,Object> removeMapping(Object o) {
        if (!(o instanceof Map.Entry))
            return null;

        Map.Entry<Object,Object> entry = (Map.Entry<Object,Object>) o;
        Object key = entry.getKey();
        int hash = (key == null) ? 0 : hash(key.hashCode());
        int i = indexFor(hash, table.length);
        Entry<Object,Object> prev = table[i];
        Entry<Object,Object> e = prev;

        while (e != null) {
            Entry<Object,Object> next = e.next;
            if (e.hash == hash && e.equals(entry)) {
                modCount++;
                size--;
                if (prev == e)
                    table[i] = next;
                else
                    prev.next = next;
                e.recordRemoval((WordMap) this);
                return e;
            }
            prev = e;
            e = next;
        }

        return e;
    }

    private final class EntrySet  {
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<Object,Object> e = (Map.Entry<Object,Object>) o;
            Entry<Object,Object> candidate = getEntry(e.getKey());
            return candidate != null && candidate.equals(e);
        }
        public boolean remove(Object o) {
            return removeMapping(o) != null;
        }
        public int size() {
            return size;
        }
        public void clear() {
            WordMap.this.clear();
        }
    }

    public static void main(String[] args){
        WordMap map = new WordMap();

        for(int i = 0; i < 5; i++){
            map.put((char)('a')+"ello", i);
            System.out.println(map.size+""+map.count());
        }
        map.remove("aello");
        System.out.println(map);

    }

}
