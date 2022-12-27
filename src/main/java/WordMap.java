import java.util.*;

/**
 * @author Kim Trinh (20215539)
 * @author Miliya Ai (20180783)
 *
 * WordMap utilise une structure de données ChainHashMap.
 *
 * Code inspire du prof, https://www.algolist.net/Data_structures/Hash_table/Chaining et du hashMap de java
 *  cle : un mot
 *  valeur : un fileMap
 */
public class WordMap implements Map {
    private WordMap.Entry[] table;
    private int buckets = 0; // le nombre de buckets
    protected int capacity; // size of the table
    private int prime; // prime factor
    private long scale, shift; // shift and scale factors
    int modCount;
    final double maxLoadFactor = 0.75;
    final int originalCapacity = 11;

    // ------------------------------------ CONSTRUCTEUR  ------------------------------------ //

    public WordMap(int capacity, int prime) {
        this.prime = prime;
        this.capacity = capacity;
        Random rand = new Random();
        this.scale = rand.nextInt(prime - 1) + 1;
        this.shift = rand.nextInt(prime);
        this.createTable();
    }
    public WordMap( int cap ) { this( cap, 109345121 ); } //appel le premier constructeur
    public WordMap() { this( 11 ); } //appel le deuxieme constructeur

    public WordMap.Entry[] getTable() {
        return table;
    }

    static int hash(int h) {
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    public int getCapacity() {
        return capacity;
    }

    /**
     * Des le depart, la table a toutes des entry qui sont null
     */
    protected void createTable() {
        table = new WordMap.Entry[this.capacity];
        for (int i = 0; i < capacity; i++)
            table[i] = null;
    }

    /**
     *
     * @param key une cle de WordMap
     * @return index de la cle dans la table
     */
    protected int hashValue( Object key ) {
        return (int)( ( Math.abs( key.hashCode() * scale + shift ) % prime ) % capacity );
    }

    static int indexFor(int h, int length) {
        return h & (length-1);
    }
    /**
     *
     * @return le nombre de Entry de WordMap
     */
    @Override
    public int size() { return this.buckets;}

    /**
     *
     * @return true si WordMap est vide
     */
    @Override
    public boolean isEmpty() { return this.size() == 0; }

    /**
     *
     * @param key cle a chercher dans WordMap
     * @return true si cette cle ce trouve dans le WordMap
     * @throws ClassCastException cle doit etre un String
     */
    @Override
    public boolean containsKey(Object key) throws ClassCastException {
        if (!(key instanceof String)){
            throw new ClassCastException("La cle doit etre un String, le nom du fichier");
        }
        return getEntry(key) != null;


    }
    final WordMap.Entry getEntry(Object key) {
        int hash = (key == null) ? 0 : hash(key.hashCode());

        for (WordMap.Entry e = table[indexFor(hash, table.length)];
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
     *
     * @param value valeur a chercher dans le WordMap
     * @return true si la valeur se trouve dans le WordMap
     * @throws ClassCastException value doit etre un FileMap
     */
    @Override
    public boolean containsValue(Object value) throws ClassCastException {
        if (!(value instanceof FileMap)){
            throw new ClassCastException("La valeur doit etre un FileMap.");
        }

        if (size() == 0){
            return false;
        }

        WordMap.Entry[] tab = table;
        for (int i = 0; i < tab.length ; i++)
            for (WordMap.Entry e = tab[i]; e != null ; e = e.next)
                if (value.equals(e.value))
                    return true;
        return false;
    }

    @Override
    public Object get(Object key) throws ClassCastException {
        if (!(key instanceof String)){
            throw new ClassCastException("La cle doit etre un String, le mot");
        }
        int hash = hash(key.hashCode());
        for (WordMap.Entry e = table[indexFor(hash, table.length)];
             e != null;
             e = e.next) {
            Object k;
            if ( e.hash == hash && ((k = e.key) == key ||key.equals(k)))
                return e.value;
        }
        return null;
    }

    void addEntry(int hash, Object key, Object value, int bucketIndex) {
        WordMap.Entry e = table[bucketIndex];
        table[bucketIndex] = new WordMap.Entry(hash, key, value, e);
        buckets++;
        if (isAboveLoadFactor()){
            resize();
        }
    }
    @Override
    public Object put(Object key, Object value) throws ClassCastException {
        if (!((key instanceof String) && (value instanceof FileMap))){
            throw new ClassCastException("La cle doit etre un string, le nom d'un fichier. " +
                    "La valeur doit etre un FileMap.");
        }

        int hash = hash(key.hashCode());
        int i = indexFor(hash, table.length);
        for (WordMap.Entry e = table[i]; e != null; e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                return e.setValue(value);
            }
        }
        addEntry(hash, key, value, i);

        return null;

    }
    final WordMap.Entry removeEntryForKey(Object key) {
        int hash = (key == null) ? 0 : hash(key.hashCode());
        int i = indexFor(hash, table.length);
        WordMap.Entry prev = table[i];
        WordMap.Entry e = prev;

        while (e != null) {
            WordMap.Entry next = e.next;
            Object k;
            if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k)))) {

                buckets--;
                if (prev == e)
                    table[i] = next;
                else
                    prev.next = next;
                return e;
            }
            prev = e;
            e = next;
        }

        return e;
    }
    @Override
    public Object remove(Object key) throws ClassCastException {
        if (!(key instanceof String)){
            throw new ClassCastException("La cle doit etre un String, le nom du fichier.");
        }
        WordMap.Entry e = removeEntryForKey(key);
        return (e == null ? null : e.value);

    }

    @Override
    public void putAll(Map m) {
        WordMap.Entry[] tab = table;
        for (int i = 0; i < tab.length ; i++)
            for (WordMap.Entry entry = tab[i]; entry != null ; entry = entry.next){
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        if (buckets != 0 ) {
            for (int i = 0; i < capacity; i++) {
                table[i] = null;
            }
        }
        buckets = 0;
    }

    @Override
    public Set<Object> keySet() {
        Set<Object> keySet = new HashSet<>();

        if (buckets != 0) {
            WordMap.Entry[] tab = table;
            for (int i = 0; i < tab.length ; i++)
                for (WordMap.Entry e = tab[i]; e != null ; e = e.next)
                    keySet.add(e.getKey());
        }

        return keySet;
    }

    @Override
    public Collection values() {

        Collection values = new ArrayList<>();

        if (buckets != 0){
            WordMap.Entry[] tab = table;
            for (int i = 0; i < tab.length ; i++)
                for (WordMap.Entry e = tab[i]; e != null ; e = e.next)
                    values.add(e.getValue());
        }
        return values;
    }


    @Override
    public Set<WordMap.Entry> entrySet() {
        Set<WordMap.Entry> entrySet = new HashSet<>();


        if (buckets != 0) {
            WordMap.Entry[] tab = table;
            for (int i = 0; i < tab.length ; i++)
                for (WordMap.Entry e = tab[i]; e != null ; e = e.next)
                    entrySet.add(e);
        }
        return entrySet;
    }


    void transfer(WordMap.Entry[] newTable) {
        WordMap.Entry[] src = table;
        int newCapacity = newTable.length;
        for (int j = 0; j < src.length; j++) {
            WordMap.Entry e = src[j];
            if (e != null) {
                src[j] = null;
                do {
                    WordMap.Entry next = e.next;
                    int i = indexFor(e.hash, newCapacity);
                    e.next = newTable[i];
                    newTable[i] = e;
                    e = next;
                } while (e != null);
            }
        }
    }

    public void resize(){
        WordMap.Entry[] oldTable = table;
        int newCapacity = (2* this.capacity) + 1;
        WordMap.Entry[] newTable = new WordMap.Entry[newCapacity];
        transfer(newTable);

        this.capacity = newCapacity;
        table = newTable;

    }

    /**
     *
     * @return true si WordMap depasse le maxLoadFactor
     */
    public boolean isAboveLoadFactor(){
        double loadFactor = ( ((double)size()) / this.capacity);
        // https://stackoverflow.com/questions/11701399/round-up-to-2-decimal-places-in-java
        double roundOff = Math.round( loadFactor * 100.0) / 100.0;
        return (roundOff > maxLoadFactor);
    }


    /**
     * Chaque bucket est un linkedList, une idee inspiree de la documention HashMap qui utilise un chainage separe
     * ou chaque bucket est un linkedList.
     *
     * Code inspire du prof et https://www.algolist.net/Data_structures/Hash_table/Chaining
     * @param <K> cle d'une entry
     * @param <V> valeur d'une entry
     */
    protected static class Entry<K, V>{
        private K key; // for the key
        private FileMap value = new FileMap(); // for the value
        private WordMap.Entry next;
        final int hash;


        public Entry(int h, K key, V value, WordMap.Entry n ) {
            this.key = key;
            this.value = (FileMap) value;
            this.next = n;
            this.hash = h;


        }
        // getters
        public K getKey() { return this.key; }
        public FileMap getValue() { return this.value; }
        public WordMap.Entry getNext(){return this.next;}


        protected void setKey( K key ) { this.key = key; }
        public V setValue( V value ) {
            FileMap old = new FileMap();
            old = this.value;
            this.value = (FileMap) value;
            return (V) old;

        }
        public void setNext(WordMap.Entry next){ this.next = next;}



        public String toString() { return "<" + this.getKey() + ":" + this.getValue() + ">"; }

        public final int hashCode() {
            return (key==null   ? 0 : key.hashCode()) ^
                    (value==null ? 0 : value.hashCode());
        }
    }

}