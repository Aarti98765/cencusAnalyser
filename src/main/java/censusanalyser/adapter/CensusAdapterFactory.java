package censusanalyser.adapter;

import censusanalyser.Analyser;
import censusanalyser.CensusAnalyserException;
import censusanalyser.CensusDao;

import java.util.Map;

public class CensusAdapterFactory {
    public static Map<String, CensusDao> getCensusData(Analyser.Country country, String[] csvFilePath) throws CensusAnalyserException, CensusAnalyserException {
        if (country.equals(Analyser.Country.INDIA))
            return new IndiaCensusAdapter().loadCensusData(csvFilePath);
        else if (country.equals(Analyser.Country.US))
            return new USCensusAdapter().loadCensusData(csvFilePath);
        else
            throw new CensusAnalyserException( "Invalid country", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }
}
