import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import java.io.*;
import java.util.Properties;

/**
 * Fait le pretraitement de tous les fichiers dans le dataset.
 * @author Kim Trinh (20215539)
 * @author Miliya Ai (20180783)
 */
public class Preprocess {
    private final String pathDataSet;
    protected File[] filesList;
    private final Struct struct = new Struct();
    private int totalFichiers = 0;

    // ------------------------------------ CONSTRUCTEUR  ------------------------------------ //

    /**
     * Initialise l'environnement de pretraitement.
     * @param pathDataSet path du dataset
     * @throws IOException si le fichier n'existe, un message d'erreur s'affichera
     */
    public Preprocess(String pathDataSet) throws IOException {
        this.pathDataSet = pathDataSet;
        init();
    }

    // ------------------------------------ PRETRAITEMENT  ------------------------------------ //
    /**
     * Pour chaque fichier, faire le pretraitement.
     * @throws IOException si le fichier n'existe, un message d'erreur s'affichera
     */
    private void init() throws IOException {

        File directoryPath = new File(this.pathDataSet);
        this.filesList = directoryPath.listFiles();

        if (this.filesList != null) {
            for (File file : this.filesList) {
                preprocess(file);
                this.totalFichiers++;
            }
        }
    }

    /**
     * Remplace tous les signes de ponctuation par des espaces, en remplaçant
     * plusieurs espaces par un seul, effectuer un traitement de texte NLP et
     * creer un wordMap contenant tous les mots du dataset.
     *
     * Code de pretraitement de  Faezeh Pouya Mehr.
     * @param file un fichier du dataset
     * @throws IOException si pour le path donne, le fichier n'existe pas, une erreur s'affichera
     */
    private void preprocess(File file) throws IOException {
        FileReader reader = new FileReader(pathDataSet + "/"+file.getName());
        BufferedReader br = new BufferedReader(reader);
        StringBuilder word = new StringBuilder();
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
        this.struct.createWordMap(str, file.getName());
    }


    /**
     *
     * @param file fichier a effacer le contenu pour en ecrire du texte pretraite
     * @param str nouveau contenu pretraite a ecrire dans le fichier
     * @throws IOException
     * https://stackoverflow.com/questions/6994518/how-to-delete-the-content-of-text-file-without-deleting-itself
     * code inspiré de https://www.codejava.net/java-se/file-io/how-to-read-and-write-text-file-in-java
     */
    private void writeFile(File file, String str) throws IOException {
        // Efface le contenu du fichier
        PrintWriter pw = new PrintWriter(pathDataSet + "/" + file.getName());
        pw.close();

        FileWriter writer = new FileWriter(pathDataSet + "/" + file.getName());
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(str);
        bufferedWriter.close();
    }

    //------------------------------------ GETTERS ------------------------------------------------//

    public Struct getStruct() {
        return this.struct;
    }

    public int getTotalFichiers() {
        return this.totalFichiers;
    }
}
