package censusanalyser;

import censusanalyser.adapter.CensusAdapterFactory;
import com.csvbuilder.CsvFileBuilder;
import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class Analyser<T> {

    CsvFileBuilder csvFileBuilder = new CsvFileBuilder();
    static List<CensusDao> censusList = null;
    Map<String, CensusDao> csvData = null;
    private Country country;
    public Analyser() { }
    public enum Country {INDIA, US}

    //CONSTRUCTOR
    public Analyser(Country country) {
        this.country = country;
    }

    public int loadStateCensusCSVData(Country country, String... csvFilePath) throws CensusAnalyserException {
        csvData = CensusAdapterFactory.getCensusData(country, csvFilePath);
        censusList = csvData.values().stream().collect(Collectors.toList());
        return csvData.size();
    }


    //METHOD TO SORT CENSUS DATA BY STATE
    public String SortedCensusData(SortingMode mode) throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        ArrayList arrayList = csvData.values().stream()
                .sorted(CensusDao.getSortComparator(mode))
                .map(censusDao -> censusDao.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(arrayList);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT STATE CENSUS DATA BY POPULATION
    public static String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDao> censusComparator = Comparator.comparing(censusDao -> censusDao.Population);
        sortData(censusComparator);
        Collections.reverse(censusList);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    //METHOD TO SORT CSV DATA
    private static void sortData(Comparator<CensusDao> csvComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                CensusDao census1 = censusList.get(j);
                CensusDao census2 = censusList.get(j + 1);
                if (csvComparator.compare(census1, census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }
            }
        }
    }

    public enum SortingMode {STATE, POPULATION, DENSITY, AREA}

}