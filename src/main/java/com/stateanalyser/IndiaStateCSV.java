package com.stateanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCSV {

        @CsvBindByName(column = "SrNo", required = true)
        public int srNo;

        @CsvBindByName(column = "State Name", required = true)
        public String stateName;

        @CsvBindByName(column = "TIN", required = true)
        public int tin;

        @CsvBindByName(column = "StateCode", required = true)
        public static String stateCode;
        public int SrNo;

        public IndiaStateCSV(int srNo, String stateName, String stateCode, int TIN) {
            srNo = srNo;
            stateName = stateName;
            stateCode = stateCode;
            this.tin = TIN;
        }

        public IndiaStateCSV() {
        }
}






