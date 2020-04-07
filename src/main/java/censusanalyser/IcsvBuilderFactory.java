package censusanalyser;

import java.io.Reader;
import java.util.Iterator;

public interface IcsvBuilderFactory {
    public <T> Iterator getIterator(Reader reader, Class classFile) throws CensusAnalyserException;
}
