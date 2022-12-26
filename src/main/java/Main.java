import java.io.*;

public class Main {
    /**
     * @author Kim Trinh (20215539)
     * @author Miliya Ai (20180783)
     * @param args index 0 = dataset, index 1 = query.txt
     */
    public static void main(String[] args) throws Exception {

        try {
            String pathDataset = args[0].trim();
            String pathQuery = args[1].trim();
            Preprocess preprocess = new Preprocess(pathDataset);
            new Query(preprocess.getStruct().getWordMap(), preprocess.getTotalFichiers(), pathQuery );
            //Preprocess preprocess= new Preprocess("C:/Users/Admin/Documents/GitHub/ift2015_TP2/dataset/dataset");
            //new Query(preprocess.getStruct().getWordMap(), preprocess.getTotalFichiers(), "C:\\Users\\Admin\\Documents\\GitHub\\ift2015_TP2\\src\\main\\java\\query.txt");
        } catch (Exception e){
            System.out.println("Veuillez mettre le path de dataset, puis le path du query.txt");

        }



        //Preprocess preprocess= new Preprocess("/Users/miliya.ai/Documents/GitHub/ift2015_TP2/dataset/dataset");

        //Preprocess preprocess= new Preprocess("C:/Users/Admin/Documents/GitHub/ift2015_TP2/dataset/dataset");
        //new Query(preprocess.getStruct().getWordMap(), preprocess.getTotalFichiers(), "C:\\Users\\Admin\\Documents\\GitHub\\ift2015_TP2\\src\\main\\java\\query.txt");
    }
}
