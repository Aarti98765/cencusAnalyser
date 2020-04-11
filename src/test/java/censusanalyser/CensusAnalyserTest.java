package censusanalyser;

import com.csvbuilder.CsvFileBuilderException;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE = "./src/main/resources/CensusData.txt";
    private static final String WRONG_CSV_FILE_DELIMITER = "./src/test/resources/CensusInvalidDelimiter.csv";
    private static final String WRONG_CSV_FILE_HEADER = "./src/test/resources/CensusInvalidHeader.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            Analyser censusAnalyser = new Analyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            Analyser censusAnalyser = new Analyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithCorrectFile_wrongFileType_ShouldThrowCustomException() {
        try {
            Analyser censusAnalyser = new Analyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithCoorectFile_delimiterIncorrect_ShouldThrowCustomException() {
        try {
            Analyser censusAnalyser = new Analyser();
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE_DATA, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithCorrectFile_csvHeaderIncorrect_ShouldThrowCustomException() {
        try {
            Analyser censusAnalyser = new Analyser();
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE_HEADER, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnState_shouldReturnSortedResult() {

        String sortedCensusData = null;
        try {
            Analyser censusAnalyser = new Analyser();
            sortedCensusData = censusAnalyser.sortIndianCensusCsvDataByState(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnPopulation_shouldReturnSortedResult() {
        try {
            Analyser censusAnalyser = new Analyser();
            String sortCensusData = censusAnalyser.sortIndianCensusCsvDataByPopulation(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(199812341, censusCSV[0].population);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnDensityPerSqKm_shouldReturnSortedResult() {
        try {
            Analyser censusAnalyser = new Analyser();
            String sortCensusData = censusAnalyser.sortIndianCensusCsvDataByPopulation(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(1102, censusCSV[0].densityPerSqKm);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnDensityStateArea_shouldReturnSortedResult() {
        try {
            Analyser censusAnalyser = new Analyser();
            String sortCensusData = censusAnalyser.sortIndianCensusCsvDataByPopulation(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(1723338, censusCSV[0].areaInSqKm);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusAnalyserFile_WhenTrue_NumberOfRecordShouldMatch() throws CensusAnalyserException {
        Analyser analyser = new Analyser();
        int count = analyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH) ;
        Assert.assertEquals(51, count);
    }
}
