package censusanalyser;

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
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        checkValidCsvFile(csvFilePath);
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            Iterator<IndiaCensusCSV> censusCSVIterator = this.getIterator(reader,IndiaCensusCSV.class);
            int namOfEateries = 0;
            Iterable<IndiaCensusCSV> indiaCensusCSVIterable = ()->censusCSVIterator;
            namOfEateries = (int) StreamSupport.stream(indiaCensusCSVIterable.spliterator(),false).count();
            return namOfEateries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
    public int loadIndiaStateData(String csvFilePath) throws CensusAnalyserException {
        checkValidCsvFile(csvFilePath);
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            Iterator<IndiaStateCSV> censusCSVIterator = this.getIterator(reader,IndiaStateCSV.class);

            int namOfEateries = 0;
            Iterable<IndiaStateCSV> indiaCensusCSVIterable = ()->censusCSVIterator;
            namOfEateries = (int) StreamSupport.stream(indiaCensusCSVIterable.spliterator(),false).count();
            return namOfEateries;
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.STATE_FILE_PROBLEM);
       }
    }
    public <T> Iterator getIterator(Reader reader,Class classFile) throws CensusAnalyserException {
        try {
            CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(classFile);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<T> csvToBean = csvToBeanBuilder.build();
            Iterator<T> csvIterator = csvToBean.iterator();
            return csvIterator;
        } catch (RuntimeException e) {
            if (e.getMessage().contains("header!"))
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE_HEADER);
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE_HEADER);
        }
    }
    public void checkValidCsvFile(String csvFilePath) throws CensusAnalyserException {
        if(!csvFilePath.contains(".csv")){
            throw new CensusAnalyserException("Invalid file type", CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        }
    }
}
