import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PreprocessTest {
    Preprocess preprocess;
    /*
    @BeforeEach
    void initiate(){

    }
     */

    @Test
    void init() {
        initiate("dataset/dataset");
        String[] files = {"900.txt", "901.txt", "902.txt", "903.txt"};
        assertEquals(files, preprocess.getFilesList());
    }

    void initiate(String pathDataSet){
        preprocess = new Preprocess(pathDataSet);
    }
}