import java.util.Map;

public abstract class AbstractMap<K,V> implements Map<K,V> {

    protected static class MapEntry<K, V>{
        private K k; // for the key
        private V v; // for the value
        public MapEntry( K key, V value ) {
            this.k = key;
            this.v = value;
        }
        // getters
        public K getKey() { return this.k; }
        public V getValue() { return this.v; }
        // developer's utilities
        protected void setKey( K key ) { this.k = key; }
        protected V setValue( V value ) {
            V old = v;
            this.v = value;
            return old;
        }
        public String toString() { return "<" + this.getKey() + ":" + this.getValue() + ">"; }
    }
}
