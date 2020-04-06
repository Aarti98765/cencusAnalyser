package censusanalyser;

import com.stateanalyser.StateAnalyser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StateAnalyserTest {
    private static final String INDIA_STATE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CSV_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CSV_FILE_TYPE = "./src/main/resources/stateData.txt";
    private static final String WRONG_STATE_CSV_FILE_DELIMITER = "./src/test/resources/StateInvalidDelimiter.csv";
    private static final String WRONG_STATE_CSV_FILE_HEADER = "./src/test/resources/StateInvalidDelimiter.csv";

    @Test
    public void givenIndianStateCSVFileReturnsCorrectRecords() {
        try {
            Analyser stateAnalyser = new Analyser();
            int numOfRecords = stateAnalyser.loadIndiaStateData(INDIA_STATE_CSV_FILE_PATH);
            Assert.assertEquals(38,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

}
