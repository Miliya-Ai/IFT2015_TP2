import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Random;

/**
 * FileMap utilise une structure de données ChainHashMap
 * @param <K> cle : noms des fichiers
 * @param <V> valeur : positions du mot dans le fichier correspondant
 */
public class FileMap<K,V> extends AbstractMap<K,V> implements Map<K,V> {
    private MapEntry<String, ArrayList<Integer>>[] table;
    private int size = 0;
    protected int capacity; // size of the table
    private int prime; // prime factor
    private long scale, shift; // shift and scale factors

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

    protected void createTable() {
        table = (MapEntry<String, ArrayList<Integer>>[]) new MapEntry[this.capacity];
    }

    /**
     *
     * @param key une cle de FileMap
     * @return index de la cle dans la table
     */
    protected int hashValue( K key ) {
        return (int)( ( Math.abs( key.hashCode() * scale + shift ) % prime ) % capacity );
    }

    protected V bucketGet( int h, K k ) {
        MapEntry<String, ArrayList<Integer>>[] bucket = table[h];
        //ProbeHashMap<K,V> bucket = table[h];
        if( bucket == null ) return null;
        return bucket.get( k );
    }
    /**
     *
     * @return le nombre de Entry de FileMap
     */
    @Override
    public int size() { return this.size;}

    /**
     *
     * @return true si FileMap est vide
     */
    @Override
    public boolean isEmpty() { return this.size() == 0; }

    @Override
    public boolean containsKey(Object key) { }
    @Override
    public boolean containsValue(Object value) { }
    @Override
    public V get(Object key) {
    }
    @Override
    public V put(K key, V value) {}
    @Override
    public V remove(Object key) {}
    @Override
    public void putAll(Map<? extends K,? extends V> m) {}
    @Override
    public void clear() {}
    @Override
    public Set<K> keySet() {}
    @Override
    public Collection<V> values() {}
    @Override
    public Set<Map.Entry<K,V>> entrySet() {}
}
