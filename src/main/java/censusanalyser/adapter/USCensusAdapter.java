package censusanalyser.adapter;

import censusanalyser.CensusAnalyserException;
import censusanalyser.CensusDao;
import censusanalyser.UsCensusCSV;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {
        @Override
        public Map<String, CensusDao> loadCensusData(String... csvFilePath) throws CensusAnalyserException, CensusAnalyserException {
            return super.loadCensusData(UsCensusCSV.class, csvFilePath[0]);
        }
}

