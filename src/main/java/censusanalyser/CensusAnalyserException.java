package censusanalyser;

import com.csvbuilder.CsvFileBuilderException;

public class CensusAnalyserException extends Exception{
    public CensusAnalyserException(String message, CsvFileBuilderException.ExceptionType type) {
        super(message);
        this.type = ExceptionType.valueOf(type.name());
    }

    public enum ExceptionType {
            STATE_FILE_PROBLEM,
            CENSUS_FILE_PROBLEM,
            INVALID_FILE_TYPE,
            INVALID_FILE_TYPE_DATA,
            INVALID_FILE_TYPE_HEADER,
            FILE_PROBLEM;

        }

        ExceptionType type;

        public CensusAnalyserException(String message, ExceptionType type) {
            super(message);
            this.type = type;
        }

        public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
            super(message, cause);
            this.type = type;
        }
    }

