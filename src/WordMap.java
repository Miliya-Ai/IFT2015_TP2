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
    private int numDeleted;
    private Entry<K,V>[] table;
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
        return this.size() == 0;
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
    public boolean containsValue(Object value) {

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
    public void putAll(Map<? extends K,? extends V> m) {
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
    public void clear() {
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
    @Override
    public Set<K> keySet() {
        return null;
    }
    @Override
    public Collection<V> values() {
        return null;
    }
    @Override
    public Set<Map.Entry<K,V>> entrySet() {
        return null;
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
