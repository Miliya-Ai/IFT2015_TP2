import java.util.*;

public class WordMap<K,V> implements Map<K,V> {
    private  static  class  Entry<K,V> {
        private K key;
        private V value;
        public Entry(K key, V value){
            this.key = key;
            this.value = value;
        }
    }

    private int size;
    private int capacity;
    private Entry<K,V>[] table;
    public  static final double Max_LOAD = 0.75;

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
    public boolean isEmpty() { }
    @Override
    public boolean containsKey(Object key) { }
    @Override
    public boolean containsValue(Object value) { }
    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        int index = key.hashCode() % capacity;
        while(true) {
            if (table [index] == null) {
                if(1.0*size/capacity >= Max_LOAD ){
                    rehash();
                }
                table[index] = new Entry<K, V>(key, value);
                size++;
                return null;
            }else if (table[index].key.equals(key)){
                V old = table[index].value;
                table[index].value = value;
                return old;
            } else {
                index = (index + 1) % capacity;

            }
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

        }

    }


    @Override
    public V remove(Object key) {
        return null;
    }
    @Override
    public void putAll(Map<? extends K,? extends V> m) {

    }
    @Override
    public void clear() {}
    @Override
    public Set<K> keySet() {}
    @Override
    public Collection<V> values() {}
    @Override
    public Set<Map.Entry<K,V>> entrySet() {}
}
