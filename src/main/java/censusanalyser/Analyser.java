package censusanalyser;

import com.csvbuilder.CsvFileBuilder;
import com.csvbuilder.CsvFileBuilderException;
import com.google.gson.Gson;
import com.stateanalyser.IndiaStateCSV;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Analyser {
    CsvFileBuilder csvFileBuilder = new CsvFileBuilder();
    List<IndiaStateCSV> csvStatesCensusList = null;
    List<IndiaCensusCSV> csvStatesList = null;
    Map<String, IndiaStateCSV> csvStatesCensusMap = null;
    public Analyser() {
        this.csvStatesCensusMap = new HashMap<String, IndiaStateCSV>();
    }

    public int loadRecords(String path) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(path));
            Iterator<IndiaCensusCSV> csvStatesCensusIterator = csvFileBuilder.getIterator(reader, IndiaCensusCSV.class);
            while (csvStatesCensusIterator.hasNext()) {
                IndiaCensusCSV csvStatesClass = csvStatesCensusIterator.next();
                this.csvStatesCensusMap.put(IndiaCensusCSV.state, csvStatesClass);
                csvStatesCensusList = csvStatesCensusMap.values().stream().collect(Collectors.toList());
            }
            int numberOfRecords = csvStatesCensusMap.size();
        } catch (NoSuchFileException e) {
        throw new CensusAnalyserException("Give proper file name and path", CensusAnalyserException.ExceptionType.FILE_NOT_FOUND);
    } catch (RuntimeException e) {
            throw new CensusAnalyserException("Check delimiters and headers", CensusAnalyserException.ExceptionType.DELIMITER_AND_HEADER_INCORRECT);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvFileBuilderException e) {
            e.printStackTrace();
        }
        return 0;
    }
    //    METHOD TO LOAD RECORDS OF STATE CODE
    public int loadData(String path) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(path));
            CsvFileBuilder csvBuilder = new CsvFileBuilder();
            Iterator<IndiaStateCSV> csvStatesIterator = csvBuilder.getIterator(reader, IndiaStateCSV.class);
            while (csvStatesIterator.hasNext()) {
                IndiaStateCSV csvStatesClass = csvStatesIterator.next();
                this.csvStatesCensusMap.put(IndiaStateCSV.stateCode, csvStatesClass);
                List<IndiaStateCSV> list = new ArrayList<>();
                for (IndiaStateCSV indiaStateCSV : csvStatesCensusMap.values()) {
                    list.add(indiaStateCSV);
                }
                csvStatesCensusList= list;
            }
            int numberOfRecords = csvStatesCensusList.size();
        } catch (NoSuchFileException e) {
            throw new CensusAnalyserException("Give proper file name and path", CensusAnalyserException.ExceptionType.FILE_NOT_FOUND);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Check delimiters and headers", CensusAnalyserException.ExceptionType.DELIMITER_AND_HEADER_INCORRECT);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvFileBuilderException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public String SortedCensusData() throws CensusAnalyserException {
        if (csvStatesCensusList == null || csvStatesCensusList.size() == 0) {
            throw new CensusAnalyserException( "No census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV> comparator = Comparator.comparing(IndiaCensusCsv -> IndiaCensusCsv.state);
        this.sortData(comparator, csvStatesCensusList);
        String sortedStateCensusJson = new Gson().toJson(csvStatesCensusList);
        return sortedStateCensusJson;
    }
    public String SortedStateCodeData() throws CensusAnalyserException {
            if (csvStatesCensusList == null || csvStatesCensusList.size() == 0) {
                throw new CensusAnalyserException( "No census data", CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE_DATA);
            }
            Comparator<IndiaCensusCSV> comparator = Comparator.comparing(IndiaCensusCsv -> IndiaCensusCSV.state);
            this.sortData(comparator, csvStatesCensusList);
            String sortedStateCodeJson = new Gson().toJson(csvStatesCensusList);
            return sortedStateCodeJson;
    }

    private<T> void sortData(Comparator<T> csvComparator, List<T> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                T census1 = (T) list.get(j);
                T census2 = (T) list.get(j + 1);
                if (csvComparator.compare(census1, census2) > 0) {
                    list.set(j, census2);
                    list.set(j + 1, census1);
                }
            }
        }
    }
}