package censusanalyser.adapter;

import censusanalyser.CensusAnalyserException;
import censusanalyser.CensusDao;
import censusanalyser.IndiaCensusCSV;
import com.csvbuilder.CsvFileBuilder;
import com.csvbuilder.CsvFileBuilderException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensusAdapter{
        @Override
        public Map<String, CensusDao> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
            Map<String, CensusDao> censusDAOMap = super.loadCensusData(IndiaCensusCSV.class, csvFilePath[0]);
            if (csvFilePath.length == 1)
                return censusDAOMap;
            return loadStateCodeCSVData(censusDAOMap, csvFilePath[1]);
        }

        private Map<String, CensusDao> loadStateCodeCSVData(Map<String, CensusDao> censusDaoMap, String csvFilePath) throws CensusAnalyserException, CensusAnalyserException {
            String extension = csvFilePath.substring(csvFilePath.lastIndexOf(".") + 1);
            if (!extension.equals("csv")) {
                throw new CensusAnalyserException("Incorrect file type", CensusAnalyserException.ExceptionType.FILE_NOT_FOUND);
            }
            try {
                Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
                CsvFileBuilder csvBuilder = new CsvFileBuilder();
                Iterator<CensusDao> stateCodeIterator = csvBuilder.getIterator(reader, CensusDao.class);
                Iterable<CensusDao> stateCodes = () -> stateCodeIterator;
                StreamSupport.stream(stateCodes.spliterator(), false)
                        .filter(csvStatesPojoClass -> censusDaoMap.get(csvStatesPojoClass.StateName) != null)
                        .forEach(csvStatesPojoClass -> censusDaoMap.get(csvStatesPojoClass.StateName).StateCode = csvStatesPojoClass.StateCode);
            } catch (RuntimeException e) {
                throw new CensusAnalyserException("Incorrect delimiter or header in file", CensusAnalyserException.ExceptionType.DELIMITER_AND_HEADER_INCORRECT);
            } catch (FileNotFoundException e) {
                throw new CensusAnalyserException( "No such file", CensusAnalyserException.ExceptionType.FILE_NOT_FOUND);
            } catch (IOException e) {
                e.getStackTrace();
            } catch (CsvFileBuilderException e) {
                e.printStackTrace();
            }
            return censusDaoMap;
        }
}
