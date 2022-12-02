import java.util.*;

/**
 * FileMap utilise une structure de données ChainHashMap.
 *
 * Code inspire du prof et https://www.algolist.net/Data_structures/Hash_table/Chaining
 * @param <K> cle : noms des fichiers
 * @param <V> valeur : positions du mot dans le fichier correspondant storer dans un arrayList
 */
public class FileMap<K,V> implements Map<K,V> {
    private Entry<K, V>[] table;
    private int buckets = 0; // le nombre de buckets
    protected int capacity; // size of the table
    private int prime; // prime factor
    private long scale, shift; // shift and scale factors

    // TODO: verifier les cas exceptionnels propres a fileMap
    public FileMap(int capacity, int prime) {
        this.prime = prime;
        this.capacity = capacity;
        Random rand = new Random();
        this.scale = rand.nextInt(prime - 1) + 1;
        this.shift = rand.nextInt(prime);
        this.createTable();
    }
    public FileMap( int cap ) { this( cap, 109345121 ); } //appel le premier constructeur
    public FileMap() { this( 11 ); } //appel le deuxieme constructeur

    /**
     *
     */
    protected void createTable() {
        table = new Entry[this.capacity];
        for (int i = 0; i < capacity; i++)
            table[i] = null;
    }

    /**
     *
     * @param key une cle de FileMap
     * @return index de la cle dans la table
     */
    protected int hashValue( Object key ) {
        return (int)( ( Math.abs( key.hashCode() * scale + shift ) % prime ) % capacity );
    }

    /**
     *
     * @return le nombre de Entry de FileMap
     */
    @Override
    public int size() { return this.buckets;}

    /**
     *
     * @return true si FileMap est vide
     */
    @Override
    public boolean isEmpty() { return this.size() == 0; }

    @Override
    public boolean containsKey(Object key) throws ClassCastException {
        int bucketIndex = hashValue(key);
        if (!(key instanceof String)){
            throw new ClassCastException("La cle doit etre un String, le nom du fichier");
        }

        if (size() == 0){
            return false;
        }

        if (table[bucketIndex] == null){
            return false;
        } else {
            Entry bucket = table[bucketIndex];
            while (bucket.getNext() != null){
                 if (bucket.getKey() == key){
                    return true;
                 }
                bucket = bucket.getNext();
            }
            return false;

        }
    }
    @Override
    public boolean containsValue(Object value) throws ClassCastException {
        if (!(value instanceof Integer)){
            throw new ClassCastException("La valeur doit etre un Int, la position du mot dans le fichier.");
        }

        if (size() == 0){
            return false;
        }
        for (int bucketIndex = 0 ; bucketIndex< capacity; bucketIndex++){
            if (table[bucketIndex] == null){
                continue;
            } else {
                Entry bucket = table[bucketIndex];
                while (bucket.getNext() != null) {
                    if (bucket.getValue() == value) {
                        return true;
                    }
                    bucket = bucket.getNext();
                }

            }
        }
        return false;
    }
    // TODO: value est dans un arraylist
    @Override
    public V get(Object key) throws ClassCastException {
        int bucketIndex = hashValue(key);
        if (!(key instanceof String)){
            throw new ClassCastException("La cle doit etre un String, le nom du fichier");
        }
        // la cle ne se trouve pas dans le FileMap
        if (table[bucketIndex] == null){
            return null;
        } else {
            Entry bucket = table[bucketIndex];
            while (bucket != null && bucket.getKey() != key) {
                 bucket = bucket.getNext();
            }
            if (bucket == null){ //l'index contient un bucket, mais ce bucket ne contient pas la cle
                return null;
            } else {
                return (V) bucket.getValue();
            }
        }
    }
    @Override
    public V put(K key, V value) throws ClassCastException {
        if (!((key instanceof String) && (value instanceof Integer))){
            throw new ClassCastException("La cle doit etre un string, le nom d'un fichier. " +
                                        "La valeur doit etre un int, la position du mot dans ce fichier.");
        }
        int bucketIndex = hashValue(key);
        if (table[bucketIndex] == null){
            table[bucketIndex] = new Entry<K, V>(key, value);
            buckets ++;
            return null;
        } else {
            Entry bucket = table[bucketIndex];
            while (bucket.getNext() != null && bucket.getKey() != key){
                bucket = bucket.getNext();
            }
            if (bucket.getKey() == key){
                return (V) bucket.setValue(value); // return l'ancienne valeur de la cle, null s'il en avait pas
            } else {
                bucket.setNext(new Entry(key,value));
                return null;
            }
        }
    }
    @Override
    public V remove(Object key) throws ClassCastException {
        if (!(key instanceof String)){
            throw new ClassCastException("La cle doit etre un String, le nom du fichier.");
        }
        int bucketIndex = hashValue(key);
        if (table[bucketIndex] != null){
            Entry prevEntry = null;
            Entry bucket = table[bucketIndex];
            while (bucket.getNext() != null && bucket.getKey() != key) {
                prevEntry = bucket;
                bucket = bucket.getNext();
            }
            if (bucket.getKey() == key){
                if (prevEntry == null){ //le bucket contient seulement cette cle
                    table[bucketIndex] = bucket.getNext();
                    buckets --;
                    return (V) bucket.getValue();
                } else { //le bucket enleve le pointeur vers cette cle du bucket
                    prevEntry.setNext(bucket.getNext());
                    buckets --;
                    return (V) bucket.getValue();
                }

            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void putAll(Map<? extends K,? extends V> m) {}

    @Override
    public void clear() {
        if (buckets != 0 ) {
            for (int i = 0; i < capacity; i++) {
                table[i] = null;
            }
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = null;

        if (buckets != 0) {

            for (int i = 0; i < capacity; i++){
                if (table[i] == null) {
                    continue;
                } else {
                    Entry bucket = table[i];
                    while (bucket.getNext() != null) {
                        if (bucket.getKey() != null) {
                            keySet.add((K) bucket.getKey());
                        }
                    }
                }
            }
        }
        return keySet;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = null;

        if (buckets != 0){
            for (int i = 0; i < capacity; i++){
                if (table[i] == null) {
                    continue;
                } else {
                    Entry bucket = table[i];
                    while (bucket.getNext() != null) {
                        if (bucket.getKey() != null) {
                            values.add((V) bucket.getValue());
                        }
                    }
                }
            }
        }
        return values;
    }
    @Override
    public Set<Map.Entry<K,V>> entrySet() {
        Set<Map.Entry<K, V>> entrySet = null;

        if (buckets != 0) {
            for (int i = 0; i < capacity; i++) {
                if (table[i] != null) {
                    entrySet.add(table[i]);
                }
            }
        }
        return entrySet;
    }

    /**
     * Chaque bucket est un linkedList, une idee inspiree de la documention HashMap qui utilise un chainage separe
     * ou chaque bucket est un linkedList.
     *
     * Code inspire du prof et https://www.algolist.net/Data_structures/Hash_table/Chaining
     * @param <K> cle d'une entry
     * @param <V> valeur d'une entry
     */
    protected static class Entry<K, V> implements Map.Entry<K,V>{
        private K k; // for the key
        private ArrayList v; // for the value
        private Entry next;

        public Entry( K key, V value ) {
            this.k = key;
            this.v.add(value);
        }
        // getters
        public K getKey() { return this.k; }
        public V getValue() { return (V) this.v; }
        public Entry getNext(){return this.next;}


        protected void setKey( K key ) { this.k = key; }
        @Override
        public V setValue( V value ) {
            ArrayList old = new ArrayList<>();
            old.addAll(v);
            this.v.add(value);
            return (V) old;
        }
        public void setNext(Entry next){ this.next = next;}


        public String toString() { return "<" + this.getKey() + ":" + this.getValue() + ">"; }
    }
    public static void main(String[] args){
        FileMap<String,Integer> map = new FileMap<>();

            map.put("ello", 1);
            map.put("a",2);
            map.put("a",3);
            map.put("a",4);
            map.put("a",5);
            System.out.println(map);
            System.out.println(clear());
            System.out.println(map.get("a"));
            System.out.println(map.size());


    }
}
