package censusanalyser;

import com.csvbuilder.CsvFileBuilder;
import com.csvbuilder.CsvFileBuilderException;
import com.google.gson.Gson;
import com.stateanalyser.IndiaStateCSV;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class Analyser {
    CsvFileBuilder csvFileBuilder = new CsvFileBuilder();
    List csvUserList = null;
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        this.checkValidCsvFile(csvFilePath);
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            Iterator<IndiaCensusCSV> censusCSVIterator = csvFileBuilder.getIterator(reader,IndiaCensusCSV.class);
            int namOfEateries = 0;
            Iterable<IndiaCensusCSV> indiaCensusCSVIterable = ()->censusCSVIterator;
            namOfEateries = (int) StreamSupport.stream(indiaCensusCSVIterable.spliterator(),false).count();
            return namOfEateries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CsvFileBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    e.type);
        }
    }

    public int loadIndiaStateData(String csvFilePath) throws CensusAnalyserException {
        this.checkValidCsvFile(csvFilePath);
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            Iterator<IndiaStateCSV> censusCSVIterator = csvFileBuilder.getIterator(reader,IndiaStateCSV.class);
            int namOfEateries = 0;
            Iterable<IndiaStateCSV> indiaCensusCSVIterable = ()->censusCSVIterator;
            namOfEateries = (int) StreamSupport.stream(indiaCensusCSVIterable.spliterator(),false).count();
            return namOfEateries;
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.STATE_FILE_PROBLEM);
        } catch (CsvFileBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type);
        }
    }
    public void checkValidCsvFile(String csvFilePath) throws CensusAnalyserException {
        if(!csvFilePath.contains(".csv")){
            throw new CensusAnalyserException("Invalid file type", CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        }
    }
    public <T> String getStateWiseSortedData_censusCsvFile(String classFile) throws CsvFileBuilderException {

        try {
            Reader reader = Files.newBufferedReader(Paths.get(classFile));
            List<IndiaCensusCSV> censusCSVList = csvFileBuilder.getList(reader,IndiaCensusCSV.class);
            Comparator<IndiaCensusCSV> censusCSVComparator= Comparator.comparing(Census -> Census.state);
            this.sortStateWiseData(censusCSVList, censusCSVComparator);
            String sortedIndiaCensusjson = new Gson().toJson(censusCSVList);
            return sortedIndiaCensusjson;
        } catch (RuntimeException | IOException e) {
            throw new CsvFileBuilderException(e.getMessage(),
                    CsvFileBuilderException.ExceptionType.INVALID_FILE_TYPE_DATA);
        }
    }
    private void sortStateWiseData(List<IndiaCensusCSV> censusList, Comparator<IndiaCensusCSV> censusComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                IndiaCensusCSV census1 = censusList.get(j);
                IndiaCensusCSV census2 = censusList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }
            }
        }
    }
}
