import java.util.*;

/**
 * FileMap utilise une structure de données ChainHashMap.
 *
 * Code inspire de https://github.com/openjdk-mirror/jdk7u-jdk/blob/master/src/share/classes/java/util/HashMap.java#L163 et https://www.youtube.com/watch?v=s37Gy5N7oio&list=PLNDWoTOY5hTYtr3IuGo1K7F-8K9DpSuD_&index=23
 * @param <K> cle : noms des fichiers
 * @param <V> valeur : positions du mot dans le fichier correspondant storer dans un arrayList
 */
public class WordMap<K,V> extends AbstractMap<K,V> implements Map<K,V> {
    private  static  class  Entry<K,V> {
        private K key;
        private V value;
        Entry<K,V> next;
        int hash;
        public Entry(K key, V value){
            this.key = key;
            this.value = value;
        }

        public void recordRemoval(WordMap<K, V> kvWordMap) {
        }
    }


    private int size;
    private int capacity;
    private int numDeleted;
    transient volatile Set<K>        keySet = null;
    transient volatile Collection<V> values = null;
    private transient Set<Map.Entry<K,V>> entrySet = null;

    private Entry<K,V>[] table;
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
    private  final Entry<K,V> DELETED = new Entry(null,null);

    public WordMap(){
        size = 10;
        capacity = 1000;
        table = new Entry[capacity];
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
    final Entry<K,V> getEntry(Object key) {
        int hash = (key == null) ? 0 : hash(key.hashCode());
        for (Entry<K,V> e = table[indexFor(hash, table.length)];
             e != null;
             e = e.next) {
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
    public V get(Object key) {
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
    public V put(K key, V value) {
        if (key == null){
            return null;
        }
        int index = key.hashCode() % capacity;
        while(true) {
            if (table [index] == null) {
                if(1.0*size/capacity >= Max_LOAD ){
                    rehash();
                }
                table[index] = new Entry<K, V>(key, value);
                size++;
                return null;
            }else if (key.equals(table[index].key)){
                V old = table[index].value;
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
            K key = (K) oldTable[i].key;
            V value = (V) oldTable[i].value;
            this.put(key,value);

        }

    }


    @Override
    public V remove(Object key) {
        int index = key.hashCode() % capacity;
        while (true){
            if(table[index] == null ){
                return null;
            }else if (key.equals(table[index].key)){
                V old = table[index].value;
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
    public void putAll(Map<? extends K, ? extends V> m) {
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

        for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
            put(e.getKey(), e.getValue());
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
        Entry<K,V> next;        // next entry to return
        int expectedModCount;   // For fast-fail
        int index;              // current slot
        Entry<K,V> current;     // current entry

        HashIterator() {
            expectedModCount = modCount;
            if (size > 0) { // advance to first entry
                Entry[] t = table;
                while (index < t.length && (next = t[index++]) == null)
                    ;
            }
        }
        
    private final class ValueIterator  implements Iterator<V> {
        public V next() {
            return nextEntry().value;
        }
    }

    private final class KeyIterator extends HashIterator<K> implements Iterator<K> {
        public K next() {
            return nextEntry().getKey();
        }
    }

    private final class EntryIterator extends HashIterator<Map.Entry<K,V>> {
        public Map.Entry<K,V> next() {
            return nextEntry();
        }
    }

    // Subclass overrides these to alter behavior of views' iterator() method
    Iterator<K> newKeyIterator()   {
        return new KeyIterator();
    }
    ValueIterator newValueIterator()   {
        return new ValueIterator();
    }
    EntryIterator newEntryIterator()   {
        return new EntryIterator();
    }

    @Override
    public Set<K> keySet() {
        Set<K> ks = keySet;
        return (ks != null ? ks : (keySet = (Set<K>) new KeySet()));
    }

    private  class KeySet {
        public int size() {
            return size;
        }
        public boolean contains(Object o) {
            return containsKey(o);
        }
        public void clear() {
            WordMap.this.clear();
        }
    }


    @Override
    public Collection<V> values() {
        Collection<V> vs = values;
        return (vs != null ? vs : (values = (Collection<V>) new Values()));
    }

    private final class Values extends AbstractCollection<V> {
        public Iterator<V> iterator() {
            return newValueIterator();
        }
        public int size() {
            return size;
        }
        public boolean contains(Object o) {
            return containsValue(o);
        }
        public void clear() {
            WordMap.this.clear();
        }

    }
    @Override
    public Set<Map.Entry<K,V>> entrySet() {
        return entrySet0();
    }

    private Set<Map.Entry<K,V>> entrySet0() {
        Set<Map.Entry<K,V>> es = entrySet;
        return es != null ? es : (entrySet);
    }
    
    final Entry<K,V> removeMapping(Object o) {
        if (!(o instanceof Map.Entry))
            return null;

        Map.Entry<K,V> entry = (Map.Entry<K,V>) o;
        Object key = entry.getKey();
        int hash = (key == null) ? 0 : hash(key.hashCode());
        int i = indexFor(hash, table.length);
        Entry<K,V> prev = table[i];
        Entry<K,V> e = prev;

        while (e != null) {
            Entry<K,V> next = e.next;
            if (e.hash == hash && e.equals(entry)) {
                modCount++;
                size--;
                if (prev == e)
                    table[i] = next;
                else
                    prev.next = next;
                e.recordRemoval(this);
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
            Map.Entry<K,V> e = (Map.Entry<K,V>) o;
            Entry<K,V> candidate = getEntry(e.getKey());
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
        WordMap<String,Integer> map = new WordMap<>();

        for(int i = 0; i < 5; i++){
            map.put((char)('a')+"ello", i);
            System.out.println(map.size+""+map.count());
        }
        map.remove("aello");
        System.out.println(map);

    }

}
