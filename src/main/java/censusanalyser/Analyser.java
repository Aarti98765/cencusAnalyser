package censusanalyser;

import com.csvbuilder.CsvFileBuilder;
import com.csvbuilder.CsvFileBuilderException;
import com.google.gson.Gson;
import com.stateanalyser.IndiaStateCSV;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Analyser<T> {
    Map<String, CensusDao> csvData = new HashMap<String, CensusDao>();
    CsvFileBuilder csvFileBuilder = new CsvFileBuilder();

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        this.checkValidCsvFile(csvFilePath);
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            List<IndiaCensusCSV> censusCsvList = csvFileBuilder.getList(reader,IndiaCensusCSV.class);
            convertCensusCsvIntoMap(censusCsvList);
            return censusCsvList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CsvFileBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type);
        }
    }

    public void checkValidCsvFile(String csvFilePath) throws CensusAnalyserException {
        if(!csvFilePath.contains(".csv")){
            throw new CensusAnalyserException("Invalid file type", CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        }
    }

    private void convertCensusCsvIntoMap(List<IndiaCensusCSV> censusData) {
        censusData.stream().filter(indiaStateCSV -> indiaStateCSV != null).forEach(indiaStateCSV ->
                this.csvData.put(indiaStateCSV.stateCode,new CensusDao(indiaStateCSV)));
    }

    public String sortIndianCensusCsvDataByState(String indiaCensusCsvFilePath) throws CensusAnalyserException {
        this.loadIndiaCensusData(indiaCensusCsvFilePath);
        List<CensusDao> sortedData = (List<CensusDao>) this.csvData.values();
        sortedData.stream().sorted((csvData1,csvData2) -> csvData1.state.compareTo(csvData2.state))
        .collect(Collectors.toList());
        String sortedJsonString = new Gson().toJson(sortedData);
        return sortedJsonString;
    }

    public int loadIndiaStateData(String csvFilePath) throws CensusAnalyserException {
        this.checkValidCsvFile(csvFilePath);
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            List<IndiaStateCSV> stateCsvList = csvFileBuilder.getList(reader,IndiaStateCSV.class);
            convertStateCsvIntoMap(stateCsvList);
            return stateCsvList.size();
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.STATE_FILE_PROBLEM);
        } catch (CsvFileBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type);
        }
    }

    private void convertStateCsvIntoMap(List<IndiaStateCSV> censusData) {
        censusData.stream().filter(indiaCensusCSV -> indiaCensusCSV != null).forEach(indiaCensusCSV ->
                this.csvData.put(indiaCensusCSV.stateName,new CensusDao(indiaCensusCSV)));
    }

    public String sortIndianStateCsvDataByState(String indiaCensusCsvFilePath) throws CensusAnalyserException {
        this.loadIndiaStateData(indiaCensusCsvFilePath);
        List<CensusDao> sortedData = (List<CensusDao>) this.csvData.values();
        sortedData.stream().sorted((csvData1,csvData2) -> csvData1.stateName.compareTo(csvData2.stateName))
                .collect(Collectors.toList());
        String sortedJsonString = new Gson().toJson(sortedData);
        return sortedJsonString;
    }
}