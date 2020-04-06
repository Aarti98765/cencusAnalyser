package censusanalyser;

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
    public void givenIndianStateCSVFile_returnsCorrectRecords() {
        try {
            Analyser stateAnalyser = new Analyser();
            int numOfRecords = stateAnalyser.loadIndiaStateData(INDIA_STATE_CSV_FILE_PATH);
            Assert.assertEquals(37,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndiaStateData_withWrongFile_shouldThrowException() {
        try {
            Analyser stateAnalyser = new Analyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            stateAnalyser.loadIndiaStateData(WRONG_STATE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.STATE_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateData_withCorrectFile_wrongFileType_shouldThrowCustomException() {
        try {
            Analyser stateAnalyser = new Analyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            stateAnalyser.loadIndiaStateData(WRONG_STATE_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_withCorrectFile_delimiterIncorrect_shouldThrowCustomException() {
        try {
            Analyser stateAnalyser = new Analyser();
            stateAnalyser.loadIndiaStateData(WRONG_STATE_CSV_FILE_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE_DATA, e.type);
        }
    }

    @Test
    public void givenIndiaStateData_withCorrectFile_csvHeaderIncorrect_shouldThrowCustomException() {
        try {
            Analyser stateAnalyser = new Analyser();
            stateAnalyser.loadIndiaStateData(WRONG_STATE_CSV_FILE_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE_HEADER, e.type);
        }
    }
}
