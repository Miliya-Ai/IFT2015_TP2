import java.util.*;

/**
 * FileMap utilise une structure de données ChainHashMap.
 *
 * Code inspire du prof, https://www.algolist.net/Data_structures/Hash_table/Chaining et du hashMap de java
 *  cle : noms des fichiers
 *  valeur : positions du mot dans le fichier correspondant storer dans un arrayList
 */
public class FileMap implements Map{
    private Entry[] table;
    private int buckets = 0; // le nombre de buckets
    protected int capacity; // size of the table
    private int prime; // prime factor
    private long scale, shift; // shift and scale factors
    int modCount;
    final double maxLoadFactor = 0.75;
    final int originalCapacity = 11;

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

    public Entry[] getTable() {
        return table;
    }


    public int getCapacity() {
        return capacity;
    }

    /**
     * Des le depart, la table a toutes des entry qui sont null
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

    /**
     *
     * @param key cle a chercher dans FileMap
     * @return true si cette cle ce trouve dans le FileMap
     * @throws ClassCastException cle doit etre un String
     */
    @Override
    public boolean containsKey(Object key) throws ClassCastException {
        if (!(key instanceof String)){
            throw new ClassCastException("La cle doit etre un String, le nom du fichier");
        }
        return getEntry(key) != null;
/*


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

        }
        return false;

 */

    }
    final FileMap.Entry getEntry(Object key) {
        int bucketIndex = hashValue(key);

        for (FileMap.Entry e = table[bucketIndex];
             e != null;
             e = e.next) {
            Object k;
            if (((k = e.key) == key || (key != null && key.equals(k))))
                return e;
        }
        return null;
    }

    /**
     *
     * @param value valeur a chercher dans le FileMap
     * @return true si la valeur se trouve dans le FileMap
     * @throws ClassCastException value doit etre un int
     */
    @Override
    public boolean containsValue(Object value) throws ClassCastException {
        if (!(value instanceof Integer)){
            throw new ClassCastException("La valeur doit etre un Int, la position du mot dans le fichier.");
        }

        if (size() == 0){
            return false;
        }
        /*
        for (int bucketIndex = 0 ; bucketIndex< capacity; bucketIndex++){
            if (table[bucketIndex] == null){
                continue;
            } else {
                Entry bucket = table[bucketIndex];
                while (bucket.getNext() != null) {
                    if (bucket.containSpecificValue(value)) {
                        return true;
                    }
                    bucket = bucket.getNext();
                }


            }
        }
        return false;

         */
        FileMap.Entry[] tab = table;
        for (int i = 0; i < tab.length ; i++)
            for (FileMap.Entry e = tab[i]; e != null ; e = e.next)
                if (value.equals(e.value))
                    return true;
        return false;
    }

    @Override
    public Object get(Object key) throws ClassCastException {
        int bucketIndex = hashValue(key);
        if (!(key instanceof String)){
            throw new ClassCastException("La cle doit etre un String, le nom du fichier");
        }
        // la cle ne se trouve pas dans le FileMap
        if (table[bucketIndex] == null){
            return null;
        } else {
            Entry bucket = table[bucketIndex];
            while (bucket != null && (!(bucket.getKey().equals(key)))) {
                 bucket = bucket.getNext();
            }
            if (bucket == null){ //l'index contient un bucket, mais ce bucket ne contient pas la cle
                return null;
            } else {
                return bucket.getValue();
            }
        }
    }
    @Override
    public Object put(Object key, Object value) throws ClassCastException {
        if (!((key instanceof String) && (value instanceof Integer))){
            throw new ClassCastException("La cle doit etre un string, le nom d'un fichier. " +
                                        "La valeur doit etre un int, la position du mot dans ce fichier.");
        }
        int bucketIndex = hashValue(key);
        if (table[bucketIndex] == null){
            table[bucketIndex] = new Entry(key, value);
            buckets ++;
            resize();
            return null;

        } else {
            Entry bucket = table[bucketIndex];
            while (bucket.getNext() != null && bucket.getKey() != key)
                bucket = bucket.getNext();

            if (bucket.getKey().equals(key))
                 return bucket.setValue(value); // return l'ancienne valeur de la cle, null s'il en avait pas
             else {
                bucket.setNext(new Entry(key,value));
                buckets ++;
                resize();

                return null;

            }
        }


    }
    @Override
    public Object remove(Object key) throws ClassCastException {
        if (!(key instanceof String)){
            throw new ClassCastException("La cle doit etre un String, le nom du fichier.");
        }


        int bucketIndex = hashValue(key);

        if (table[bucketIndex] != null){
            Entry prevEntry = null;
            Entry bucket = table[bucketIndex];
            while (bucket.getNext() != null && (!(bucket.getKey().equals(key)))) {
                prevEntry = bucket;

                bucket = bucket.getNext();
            }
            if (bucket.getKey().equals(key)){
                if (prevEntry == null){ //le bucket contient seulement cette cle
                    table[bucketIndex] = bucket.getNext();
                    buckets --;
                    return bucket.getValue();
                } else { //le bucket enleve le pointeur vers cette cle du bucket
                    prevEntry.setNext(bucket.getNext());
                    buckets --;
                    return bucket.getValue();
                }

            } else {
                return null;
            }
        } else {
            return null;
        }


    }

    @Override
    public void putAll(Map m) {}

    //TODO: remettre a la capacity initial pour clear?
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
            FileMap.Entry[] tab = table;
            for (int i = 0; i < tab.length ; i++)
                for (FileMap.Entry e = tab[i]; e != null ; e = e.next)
                    keySet.add(e.getKey());

/*
            for (int i = 0; i < capacity; i++){
                if (table[i] == null) {
                    continue;
                } else {
                    Entry bucket = table[i];
                    //while (bucket.getNext() != null) {

                            keySet.add((K) bucket.getKey());

                      //  bucket = bucket.getNext();
                    //}


                   // keySet.add((K) bucket.getKey());
                }
            }
  */
        }


        return keySet;
    }

    @Override
    public Collection values() {

        Collection values = new ArrayList<>();

        if (buckets != 0){
            FileMap.Entry[] tab = table;
            for (int i = 0; i < tab.length ; i++)
                for (FileMap.Entry e = tab[i]; e != null ; e = e.next)
                    values.add(e.getValue());
            /*
            for (int i = 0; i < capacity; i++){
                if (table[i] == null) {
                    continue;
                } else {
                    Entry bucket = table[i];
                    while (bucket.getNext() != null) {

                            values.add((V) bucket.getValue());

                        bucket.getNext();
                    }

                }
            }

             */
        }
        return values;
    }
    @Override
    public Set<Map.Entry> entrySet() {
        Set<Map.Entry> entrySet = new HashSet<>();


        if (buckets != 0) {
            FileMap.Entry[] tab = table;
            for (int i = 0; i < tab.length ; i++)
                for (FileMap.Entry e = tab[i]; e != null ; e = e.next)
                    entrySet.add((e));
            /*
            for (int i = 0; i < capacity; i++) {
                if (table[i] != null) {

                    //entrySet.add(table[i]);
                }
            }

             */
        }
        return entrySet;
    }



    public void resize(){
        if (isAboveLoadFactor()){
            FileMap.Entry[] oldTable = table;
            int newCapacity = (2* this.capacity) + 1;
            FileMap.Entry[] newTable = new Entry[newCapacity];
            for (int i = 0; i < newTable.length; i++)
                newTable[i] = null;
            int bucketIndex;

            for (int i = 0; i < oldTable.length ; i++){
                for (FileMap.Entry e = oldTable[i]; e != null ; e = e.next) {
                    bucketIndex = hashValue(e.getKey());
                    if (newTable[bucketIndex] == null){
                        newTable[bucketIndex] = new Entry(e.getKey(), e.getValue());

                    } else {
                        Entry bucket = newTable[bucketIndex];
                        while (bucket.getNext() != null && (!(bucket.getKey().equals(e.getKey()))))
                            bucket = bucket.getNext();

                        if (bucket.getKey().equals(e.getKey()))
                            bucket.setValue(e.getValue()); // return l'ancienne valeur de la cle, null s'il en avait pas
                        else {
                            bucket.setNext(new Entry(e.getKey(),e.getValue()));

                        }
                    }
                }
            }

            this.capacity = newCapacity;
            table = newTable;
        }



    }



    /**
     *
     * @return true si FileMap depasse le maxLoadFactor
     */
    public boolean isAboveLoadFactor(){
        double loadFactor = ( ((double)size()) / this.capacity);
        double roundOff = Math.round( loadFactor * 100.0) / 100.0; // https://stackoverflow.com/questions/11701399/round-up-to-2-decimal-places-in-java
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
    protected static class Entry<K, V>  implements Map.Entry<K, V>{
        private K key; // for the key
        private ArrayList value = new ArrayList(); // for the value
        private Entry next;


        public Entry( K key, V value ) {
            this.key = key;
            this.value.add(value);
            this.next = null;
        }
        // getters
        public K getKey() { return this.key; }
        public V getValue() { return (V) this.value; }
        public Entry getNext(){return this.next;}
        public boolean containSpecificValue(V value) {
            return this.value.contains(value);
        }


        protected void setKey( K key ) { this.key = key; }
        public V setValue( V value ) {
            ArrayList old = new ArrayList<>();
            old.addAll(this.value);
            if (!(containSpecificValue(value))){
                this.value.add(value);
            }
            return (V) old;
        }
        public void setNext(Entry next){ this.next = next;}



        public String toString() { return "<" + this.getKey() + ":" + this.getValue() + ">"; }


    }

    private abstract class FileMapIterator<E> implements Iterator<E> {
        FileMap.Entry next;        // next entry to return
        int expectedModCount;   // For fast-fail
        int index;              // current slot
        FileMap.Entry current;     // current entry

        void HashIterator() {
            expectedModCount = modCount;
            if (buckets > 0) { // advance to first entry
                FileMap.Entry[] t = table;
                while (index < t.length && (next = t[index++]) == null)
                    ;
            }
        }

        public final boolean hasNext() {
            return next != null;
        }

        final FileMap.Entry nextEntry() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            FileMap.Entry e = next;
            if (e == null)
                throw new NoSuchElementException();

            if ((next = e.next) == null) {
                FileMap.Entry[] t = table;
                while (index < t.length && (next = t[index++]) == null)
                    ;
            }
            current = e;
            return e;
        }


    }

    private final class ValueIterator extends FileMapIterator<Object> {
        public Object next() {
            return nextEntry().value;
        }
    }

    private final class KeyIterator extends FileMapIterator<Object> {
        public Object next() {
            return nextEntry().getKey();
        }
    }

    private final class EntryIterator extends FileMapIterator<Map.Entry> {
        public FileMap.Entry next() {
            return nextEntry();
        }
    }

    // Subclass overrides these to alter behavior of views' iterator() method
    Iterator<Object> newKeyIterator()   {
        return new FileMap.KeyIterator();
    }
    Iterator<Object> newValueIterator()   {
        return new FileMap.ValueIterator();
    }
    Iterator<Map.Entry> newEntryIterator()   {
        return new FileMap.EntryIterator();
    }
    public static void main(String[] args) throws Exception {
        FileMap foo = new FileMap();

        for (int i=0; i<20; i++){
            foo.put("hi" + i,  1);

        }


        System.out.println(foo.containsKey("hi16"));
        System.out.println(foo.size());
        System.out.println(foo.keySet());
        System.out.println(foo.values());
        System.out.println(foo.entrySet());
    }
}
