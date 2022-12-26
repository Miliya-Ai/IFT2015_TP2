import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Preprocess {
    private final String pathDataSet;
    File directoryPath;
    File filesList[];
    Struct struct = new Struct();
    Query query;

    public int getTotalFichiers() {
        return totalFichiers;
    }

    int totalFichiers = 0;

    //constructeur
    public Preprocess(String pathDataSet) throws IOException {
        this.pathDataSet = pathDataSet;
        init();
    }

    /*---------------------- Code de pretraitement de  Faezeh Pouya Mehr ------------------------------------------------
      -------------------- https://studium.umontreal.ca/mod/forum/discuss.php?d=1241377 ------------------------------------*/
    public void init() throws IOException {

        directoryPath = new File(pathDataSet);
        filesList = directoryPath.listFiles();
        for (File file : filesList) {
            preprocess(file);
            this.totalFichiers++;
        }

    }

    public void preprocess(File file) throws IOException {
        //Pour mac
        FileReader reader = new FileReader(pathDataSet + "/"+file.getName());

        //FileReader reader = new FileReader(pathDataSet + "\\" + file.getName());
        BufferedReader br = new BufferedReader(reader);
        StringBuffer word = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {

            String newline = line.replaceAll("[^’'a-zA-Z0-9]", " ");
            String finalline = newline.replaceAll("\\s+", " ").trim();
            // set up pipeline properties
            Properties props = new Properties();
            // set the list of annotators to run
            props.setProperty("annotators", "tokenize,pos,lemma");
            // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
            props.setProperty("coref.algorithm", "neural");
            // build pipeline
            StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
            // create a document object
            CoreDocument document = new CoreDocument(finalline);
            // annnotate the document
            pipeline.annotate(document);
            //System.out.println(document.tokens());
            for (CoreLabel tok : document.tokens()) {
                String str = String.valueOf(tok.lemma());
                if (!(str.contains("'s") || str.contains("’s"))) {
                    word.append(str).append(" ");
                }
            }
        }
        br.close();
        reader.close();

        String str = String.valueOf(word);
        str = str.replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s+", " ").trim();

        writeFile(file, str);
        long temps = System.nanoTime();
        long convert = TimeUnit.SECONDS.convert(temps, TimeUnit.NANOSECONDS);
        System.out.println(temps);
        this.struct.createWordMap(str, file.getName());
        //this.struct.createBigram(str, file.getName());
    }
    //https://stackoverflow.com/questions/6994518/how-to-delete-the-content-of-text-file-without-deleting-itself
    // code inspiré de https://www.codejava.net/java-se/file-io/how-to-read-and-write-text-file-in-java
    public void writeFile(File file, String str) throws IOException {
        PrintWriter pw = new PrintWriter(pathDataSet + "/" + file.getName());
        pw.close();

        FileWriter writer = new FileWriter(pathDataSet + "/" + file.getName());
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(str);

        bufferedWriter.close();
    }

    //------------------------------------ GETTERS ------------------------------------------------//

    public Struct getStruct() {
        return struct;
    }
}
