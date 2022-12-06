import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.objectbank.LineIterator;
import edu.stanford.nlp.pipeline.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

// code inspiré de https://www.tutorialspoint.com/how-to-read-data-from-all-files-in-a-directory-using-java
public class Preprocess {

    private final String pathDataSet;
    File directoryPath;
    File filesList[];

    //constructeur
    public Preprocess(String pathDataSet){
        this.pathDataSet = pathDataSet;
        init();
    }

    public void init(){
        directoryPath= new File(pathDataSet);
        filesList = directoryPath.listFiles();

        for(File file: filesList){
            replacePonctuations();
            nplTextProcession();
        }


    }

    private void nplTextProcession() {
        //TODO: use CORENLP pour traiter le texte
    }

    // code inspiré de https://www.codejava.net/java-se/file-io/how-to-read-and-write-text-file-in-java
    private void replacePonctuations() {
        try {
            FileReader reader = new FileReader(pathDataSet);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                //TODO:
                replaceBySingleSpace(line);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO: changer l'argument si necessaire
    private void replaceBySingleSpace(String line) {
        try {
            FileWriter writer = new FileWriter("MyFile.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            //TODO: if (mot w = ponctuation)
            //          then space
            //      if (mot w = plusierus spaces)
            //          then 1 space
            /*
            bufferedWriter.write("Hello World");
            bufferedWriter.newLine();
            bufferedWriter.write("See You Again!");
             */

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    //------------------------------------ GETTERS ------------------------------------------------//
    public String getPathDataSet() {
        return pathDataSet;
    }

    public File getDirectoryPath() {
        return directoryPath;
    }

    public File[] getFilesList() {
        return filesList;
    }
    /*
    public static String text = "Joe Smith wasn't born in California. " +
            "In 2017, he went to his car, sister's car Paris, France in the summer. " +
            "His flight left at 3:00pm on July 10th, 2017. " +
            "After eating some escargot for the first time, Joe said, \"That was delicious!\" " +
            "He sent a postcard to his sister Jane Smith. " +
            "After hearing about Joe's trip, Jane decided she might go to France one day.";
    public static void main(String[] args) {
        // set up pipeline properties
        Properties props = new Properties();
        // set the list of annotators to run
        props.setProperty("annotators", "tokenize,pos,lemma");
        // set a property for an annotator, in this case the coref annotator is being
        // set to use the neural algorithm
        props.setProperty("coref.algorithm", "neural");
        // build pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // create a document object
        CoreDocument document = new CoreDocument(text);
        // annnotate the document
        pipeline.annotate(document);
        //System.out.println(document.tokens());
        for (CoreLabel tok : document.tokens()) {
            System.out.println(String.format("%s\t%s", tok.word(), tok.lemma()));
        }
    }

 */

}
