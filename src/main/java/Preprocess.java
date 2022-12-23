import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.objectbank.LineIterator;
import edu.stanford.nlp.pipeline.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

public class Preprocess {
    private final String pathDataSet;
    File directoryPath;
    File filesList[];

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

            BufferedReader br = new BufferedReader(new FileReader(new File(pathDataSet + "\\" + file.getName())));
            //Pour mac
            //FileReader reader = new FileReader(pathDataSet + "/"+file.getName());

            //FileReader reader = new FileReader(pathDataSet + "\\" + file.getName());
            //BufferedReader br = new BufferedReader(reader);
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
                System.out.println(document.tokens());
                for (CoreLabel tok : document.tokens()) {
                    String str = String.valueOf(tok.lemma());
                    if (!(str.contains("'s") || str.contains("’s"))) {
                        word.append(str).append(" ");
                    }
                    //System.out.println(String.format("%s\t%s", tok.word(), tok.lemma()));
                }
            }
            br.close();
            //reader.close();

            String str = String.valueOf(word);
            str = str.replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s+", " ").trim();
            System.out.println(str);
            PrintWriter pw = new PrintWriter(file.getName());
            pw.close();
        }
    }
}


/*
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
            try {
                FileReader reader = new FileReader(pathDataSet+"\\"+ file.getName());
                BufferedReader bufferedReader = new BufferedReader(reader);


                System.out.println(file.getName());



                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
           // replacePonctuations();
           // nplTextProcession();
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

            bufferedWriter.write("Hello World");
            bufferedWriter.newLine();
            bufferedWriter.write("See You Again!");


            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*---------------------- Code de pretraitement de  Faezeh Pouya Mehr ------------------------------------------------
  -------------------- https://studium.umontreal.ca/mod/forum/discuss.php?d=1241377 ------------------------------------
for (File file : listOfFiles){
   BufferedReader br = new BufferedReader(new FileReader(new File(dir+"/"+file.getName())));
     StringBuffer word = new StringBuffer();
     String line;
     while((line = br.readLine()) != null){
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

String str = String.valueOf(word);
str = str.replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s+", " ").trim();
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




}
*/
