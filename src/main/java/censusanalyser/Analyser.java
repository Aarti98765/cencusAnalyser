package censusanalyser;

import com.csvbuilder.CsvFileBuilder;
import com.csvbuilder.CsvFileBuilderException;
import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.stateanalyser.IndiaStateCSV;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
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

    public <T> String getStateWiseSortedData(Reader reader, Class classFile) throws CsvFileBuilderException {

        try {
            CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(classFile);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<T> csvToBean = csvToBeanBuilder.build();
            List<IndiaCensusCSV> listCSVfile = (List<IndiaCensusCSV>) csvToBean.parse();
            Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(Census -> Census.state);
            this.sort(listCSVfile, censusComparator);
            String sortedStateCensusjson = new Gson().toJson(listCSVfile);
            return sortedStateCensusjson;
        } catch (RuntimeException e) {
            if (e.getMessage().contains("header!"))
                throw new CsvFileBuilderException(e.getMessage(),
                        CsvFileBuilderException.ExceptionType.INVALID_FILE_TYPE_HEADER);
            throw new CsvFileBuilderException(e.getMessage(),
                    CsvFileBuilderException.ExceptionType.INVALID_FILE_TYPE_DATA);
        }
    }

    private void sort(List<IndiaCensusCSV> censusList, Comparator<IndiaCensusCSV> censusComparator) {
        for (int i = 0; i < censusList.size()-1; i++){
            for(int j = 0; j < censusList.size()-i-1; j++){
                IndiaCensusCSV census1 = censusList.get(j);
                IndiaCensusCSV census2= censusList.get(j+1);
                if(censusComparator.compare(census1,census2) > 0){
                    censusList.set(j, census2);
                    censusList.set(j+1, census1);
                }
            }
        }
    }
}
