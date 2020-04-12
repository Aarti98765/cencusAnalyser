package censusanalyser.adapter;

import censusanalyser.CensusAnalyserException;
import censusanalyser.CensusDao;
import censusanalyser.IndiaCensusCSV;
import censusanalyser.UsCensusCSV;
import com.csvbuilder.CsvFileBuilder;
import com.csvbuilder.CsvFileBuilderException;
import org.apache.commons.collections.map.HashedMap;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class CensusAdapter {
    public abstract Map<String, CensusDao> loadCensusData(String... csvFilePath) throws CensusAnalyserException;

    protected <E> Map<String, CensusDao> loadCensusData(Class<E> censusCSVClass, String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDao> censusStateMap = new HashedMap();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));
            CsvFileBuilder csvBuilder = new CsvFileBuilder();
            if (!csvFilePath.equals(".csv")) {
                throw new CensusAnalyserException("Invalid file type", CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
            }
            Iterator<E> csvIterator = csvBuilder.getIterator(reader, censusCSVClass);
            Iterable<E> csvIterable = () -> csvIterator;
            if (censusCSVClass.getName().equals("analyser.dto.IndiaCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDao(censusCSV)));
            } else if (censusCSVClass.getName().equals("analyser.dto.USCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(UsCensusCSV.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDao(censusCSV)));
            }
        } catch (CensusAnalyserException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.FILE_PROBLEM);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.STATE_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE_HEADER);
        } catch (CsvFileBuilderException e) {
            e.printStackTrace();
        }
        return censusStateMap;
    }
}


