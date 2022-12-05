import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


class FileMapTest {
    static FileMap fileMap;
    @BeforeAll
    @DisplayName("Instanciation")
    static void instanciate(){
        fileMap= new FileMap<>();
        assertEquals(0, fileMap.size(), "A l'instanciation, size != 0 ");
        assertEquals(11, fileMap.getCapacity(), "A l'instanciation, capacity != 0");

    }

    @AfterEach
    void clearElem(){
        fileMap.clear();
    }

    @Test
    void size() {
        assertEquals(0, fileMap.size());
        fileMap.put("gg", 4);
        assertEquals(1, fileMap.size());

        initializeElem();
        assertEquals(21, fileMap.size());
    }

    @Test
    void isEmpty() {
        //
        fileMap.clear();
        assertEquals(0, fileMap.size());

    }

    @Test
    void containsKey() {
        assertThrows(ClassCastException.class, () -> {fileMap.containsKey(50);});
        initializeElem();
        assertTrue(fileMap.containsKey("hi13"));
        assertTrue(fileMap.containsKey("hi5"));
        assertTrue(fileMap.containsKey("hi19"));

    }

    @Test
    @DisplayName("ContainsValue")
    void containsValue() {
        initializeElem();
        fileMap.put("yo", 50);
        assertTrue(fileMap.containsKey("yo"), "Doit etre true");

    }

    @Test
    void get() {
        ArrayList<Integer> oneElem =  new ArrayList<>();
        oneElem.add(1);
        initializeElem();
        assertEquals(oneElem, fileMap.get("hi11"));
        assertNull(fileMap.get("hi44"));

    }

    @Test
    void put() {
        assertNull(fileMap.put("h", 7));
        ArrayList<Integer> oneElem = new ArrayList<>();
        oneElem.add(7);
        assertEquals(oneElem, fileMap.put("h", 8));

    }

    @Test
    void remove() {
        initializeElem();
        ArrayList<Integer> oneElem = new ArrayList<>();
        oneElem.add(1);
        assertEquals(oneElem, fileMap.remove("hi17"));
        assertNull(fileMap.remove("hi17"));

    }


    @Test
    void clear() {
        fileMap.put("gg", 4);
        fileMap.clear();
        assertEquals(0, fileMap.size());

        initializeElem();
        fileMap.clear();

        assertEquals(0, fileMap.size());
    }

    @Test
    void keySet() {
        initializeElem();
        assertEquals(20, fileMap.keySet().size());

    }

    @Test
    void values() {
        initializeElem();
        assertEquals(20, fileMap.values().size());
    }

    @Test
    void entrySet() {
        initializeElem();
        assertEquals(20, fileMap.entrySet().size());
    }
   /*
    @Test
    static void resize() {
        for (int i=0; i<8; i++){
            fileMap.put("hi" + i,  1);

        }

        int newCapacity = (2* fileMap.capacity) + 1;

        fileMap.put("good", 5);

        assertEquals(newCapacity, fileMap.getCapacity(),"Après avoir ajouté 8 entries, capacity != " + newCapacity );
        //assertEquals(9, fileMap.size(), "Après avoir ajouté 9 entries, size != 9");
    }

    */

    @Test
    void isAboveLoadFactor() {
        fileMap.put("l", 7);
        assertFalse(fileMap.isAboveLoadFactor());
        fileMap.remove("l");

        for (int i=0; i<8; i++){
            fileMap.put("hi" + i,  1);

        }
        assertFalse(fileMap.isAboveLoadFactor());
    }

    public void initializeElem(){
        for (int i=0; i<20; i++){
            fileMap.put("hi" + i,  1);
        }

    }




}