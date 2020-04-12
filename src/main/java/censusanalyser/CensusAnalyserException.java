package censusanalyser;

public class CensusAnalyserException extends Exception{
        public enum ExceptionType {
            STATE_FILE_PROBLEM,
            CENSUS_FILE_PROBLEM,
           INVALID_FILE_TYPE,
          INVALID_FILE_TYPE_DATA,
            INVALID_FILE_TYPE_HEADER,
            FILE_PROBLEM,
            NO_CENSUS_DATA,
            FILE_NOT_FOUND,
            DELIMITER_AND_HEADER_INCORRECT,
            INVALID_COUNTRY;

        }

        public ExceptionType type;

        public CensusAnalyserException(String message, ExceptionType type) {
            super(message);
            this.type = type;
        }

        public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
            super(message, cause);
            this.type = type;
        }
    }

