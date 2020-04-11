package censusanalyser;

import com.csvbuilder.CsvFileBuilderException;
import com.google.gson.Gson;
import com.stateanalyser.IndiaStateCSV;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static censusanalyser.Analyser.Country.INDIA;
import static censusanalyser.Analyser.Country.US;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE = "./src/main/resources/CensusData.txt";
    private static final String WRONG_CSV_FILE_DELIMITER = "./src/test/resources/CensusInvalidDelimiter.csv";
    private static final String WRONG_CSV_FILE_HEADER = "./src/test/resources/CensusInvalidHeader.csv";
    private static final String INDIA_STATE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CSV_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CSV_FILE_TYPE = "./src/main/resources/stateData.txt";
    private static final String WRONG_STATE_CSV_FILE_DELIMITER = "./src/test/resources/StateInvalidDelimiter.csv";
    private static final String WRONG_STATE_CSV_FILE_HEADER = "./src/test/resources/StateInvalidHeader.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            Analyser censusAnalyser = new Analyser();
            int numOfRecords = censusAnalyser.loadStateCensusCSVData(INDIA,INDIA_CENSUS_CSV_FILE_PATH);
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
            censusAnalyser.loadStateCensusCSVData(INDIA,WRONG_CSV_FILE_PATH);
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
            censusAnalyser.loadStateCensusCSVData(INDIA,WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithCoorectFile_delimiterIncorrect_ShouldThrowCustomException() {
        try {
            Analyser censusAnalyser = new Analyser();
            censusAnalyser.loadStateCensusCSVData(INDIA,WRONG_CSV_FILE_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE_DATA, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithCorrectFile_csvHeaderIncorrect_ShouldThrowCustomException() {
        try {
            Analyser censusAnalyser = new Analyser();
            censusAnalyser.loadStateCensusCSVData(INDIA,WRONG_CSV_FILE_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE_HEADER, e.type);
        }
    }

    @Test
    public void givenIndianStateCSVFile_returnsCorrectRecords() {
        try {
            Analyser stateAnalyser = new Analyser();
            int numOfRecords = stateAnalyser.loadStateCensusCSVData(INDIA,INDIA_STATE_CSV_FILE_PATH);
            Assert.assertEquals(37,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndiaStateData_withWrongFile_shouldThrowException() {
        try {
            Analyser stateAnalyser = new Analyser();
            stateAnalyser.loadStateCensusCSVData(INDIA,WRONG_STATE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.STATE_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateData_withCorrectFile_wrongFileType_shouldThrowCustomException() {
        try {
            Analyser stateAnalyser = new Analyser();
            stateAnalyser.loadStateCensusCSVData(INDIA,WRONG_STATE_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_withCorrectFile_delimiterIncorrect_shouldThrowCustomException() {
        try {
            Analyser stateAnalyser = new Analyser();
            stateAnalyser.loadStateCensusCSVData(INDIA,WRONG_STATE_CSV_FILE_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE_DATA, e.type);
        }
    }

    @Test
    public void givenIndiaStateData_withCorrectFile_csvHeaderIncorrect_shouldThrowCustomException() {
        try {
            Analyser stateAnalyser = new Analyser();
            stateAnalyser.loadStateCensusCSVData(INDIA,WRONG_STATE_CSV_FILE_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE_HEADER, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnState_shouldReturnSortedResult() {

        try {
            Analyser censusAnalyser = new Analyser();
            censusAnalyser.loadStateCensusCSVData(INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.SortedCensusData(Analyser.SortingMode.STATE);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", IndiaCensusCSV.state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnPopulation_shouldReturnSortedResult() {
        try {
            Analyser censusAnalyser = new Analyser();
            censusAnalyser.loadStateCensusCSVData(INDIA,INDIA_CENSUS_CSV_FILE_PATH );
            String sortedCensusData = censusAnalyser.SortedCensusData(Analyser.SortingMode.POPULATION);
            IndiaCensusCSV[] csvStatesCensus = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(199812341, csvStatesCensus[0].population);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnDensityPerSqKm_shouldReturnSortedResult() {
        try {
            Analyser censusAnalyser = new Analyser();
            censusAnalyser.loadStateCensusCSVData(INDIA,INDIA_CENSUS_CSV_FILE_PATH );
            String sortedCensusData = censusAnalyser.SortedCensusData(Analyser.SortingMode.DENSITY);
            IndiaCensusCSV[] csvStatesCensus = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(1102, csvStatesCensus[0].densityPerSqKm);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnDensityStateArea_shouldReturnSortedResult() {
        try {
            Analyser censusAnalyser = new Analyser();
            censusAnalyser.loadStateCensusCSVData(INDIA,INDIA_CENSUS_CSV_FILE_PATH );
            String sortedCensusData = censusAnalyser.SortedCensusData(Analyser.SortingMode.AREA);
            IndiaCensusCSV[] csvStatesCensus = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(1723338, csvStatesCensus[0].areaInSqKm);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusAnalyserFile_WhenTrue_NumberOfRecordShouldMatch() throws CensusAnalyserException {
        try {
            Analyser analyser = new Analyser();
            int count = analyser.loadStateCensusCSVData(US, US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, count);
        }catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenTheUSCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        try {
            Analyser analyser = new Analyser();
            analyser.loadStateCensusCSVData(Analyser.Country.US,US_CENSUS_CSV_FILE_PATH );
            String sortedCensusData = analyser.getPopulationWiseSortedCensusData();
            CensusDao[] censusDAOS = new Gson().fromJson(sortedCensusData, CensusDao[].class);
            Assert.assertEquals("California",censusDAOS[0].State);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}
