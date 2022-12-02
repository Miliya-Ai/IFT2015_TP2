import java.io.*;

public class Main {
    /**
     * Driver class method.
     * @param args Not used.
     * @throws Exception Throws file exception.
     */
    public static void main(String[] args) throws Exception {
        WordMap<String,Integer> map = new WordMap<>();

        for(int i = 0; i < 20; i++){
            map.put((char)('a'+i) + "ello", i);
            System.out.println(map);
            System.out.println(""+map.count());
        }
    }
}
