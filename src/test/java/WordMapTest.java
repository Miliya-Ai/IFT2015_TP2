import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class WordMapTest {
    WordMap map;

    @BeforeEach
    @DisplayName("Instanciation")
    void instanciate(){
        map = new WordMap();
        assertEquals(0, map.size(), "A l'instanciation, size != 0 ");
        assertEquals(11, map.getCapacity(), "A l'instanciation, capacity != 11");

    }

    @AfterEach
    void clearElem(){
        map.clear();
    }

    @Test
    void size() {
        initializeElem();
        assertEquals(20, map.size(), "Apres avoir ajouter 20 elements dans map, mais size != 20 ");
    }

    @Test
    void isEmpty() {
        initializeElem();
        assertFalse(map.isEmpty(), "Map contient des elements, mais empty = true");
        map.clear();
        assertTrue(map.isEmpty(), "Map contient pas des elements, mais empty = false");
    }

    @Test
    void containsKey() {
        initializeElem();
        assertTrue(map.containsKey("hi17"));
    }

    @Test
    void containsValue() {
        FileMap fileMap = new FileMap();
        FileMap fileMap2 = new FileMap();
        FileMap fileMap3 = new FileMap();

        map.put("hi", fileMap);
        map.put("hi", fileMap2);

        assertTrue(map.containsValue(fileMap2));
        assertFalse(map.containsValue(fileMap3));


    }

    @Test
    void get() {
        //ex: verifier si return the value associated with the key, or null if key not in map
        //ex: verifier si ClassCastException if the key is of the wrong type
        //key doit etre un string

    }

    @Test
    void put() {
        //ex: verifier si return the previous value of the key, or null if there was no mapping
        //ex: verifier si ClassCastException if the key or value is of the wrong type
        //key doit etre un string
        //value doit etre un FileMap
    }

    @Test
    void remove() {
        //ex: verifier si return the value the key mapped to, or null if not present.
        //ex: verifier si ClassCastException if the key is of the wrong type
        //key doit etre un string

    }

    @Test
    void clear() {
    }

    @Test
    void keySet() {
        //ex: si on a ajoute 20 element dans map alors le keySet.size() = 20
    }

    @Test
    void values() {
        //ex: si on a ajoute 20 element dans map alors le set.size() = 20
    }

    public void initializeElem(){
        FileMap fileMap = new FileMap();
        for (int i=0; i<20; i++){
           map.put("hi" + i,  fileMap); //WorldMap doit accepter un FileMap comme valeur
        }

    }
}