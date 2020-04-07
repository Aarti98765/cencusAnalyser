package censusanalyser;

import com.csvbuilder.CsvFileBuilder;
import com.csvbuilder.CsvFileBuilderException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.stateanalyser.IndiaStateCSV;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class Analyser {
    CsvFileBuilder csvFileBuilder = new CsvFileBuilder();
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
}
