import java.io.*;

public class Main {
    /**
     * Driver class method.
     * @param args Not used.
     * @throws Exception Throws file exception.
     */
    public static void main(String[] args) throws Exception {
        //double roundOff = Math.round(((double)8/11) * 100.0) / 100.0;
        //System.out.println(roundOff);
        // pour windows "C:\\Users\\Admin\\Documents\\GitHub\\ift2015_TP2\\dataset\\dataset"
        new Preprocess("C:\\Users\\Admin\\Documents\\GitHub\\ift2015_TP2\\dataset\\dataset");
        //Struct struct = new Struct(path);
        //new Query(struct);
    }
}
