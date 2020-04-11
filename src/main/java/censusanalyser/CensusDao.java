package censusanalyser;

import com.stateanalyser.IndiaStateCSV;

public class CensusDao {
    public String state;
    public int population;
    public int area;
    public int density;
    public String stateCode;
    public int srNo;
    public int tin;
    public int SrNo;
    public String stateName;

    public CensusDao(IndiaCensusCSV csvStatesCensus) {
        this.stateCode = csvStatesCensus.stateCode;
        this.population = csvStatesCensus.population;
        this.area = csvStatesCensus. areaInSqKm;
        this.density = csvStatesCensus. densityPerSqKm;
    }

    public CensusDao(IndiaStateCSV csvStatesCensus) {
        this.SrNo = csvStatesCensus.SrNo;
        this.tin = csvStatesCensus.tin;
        this.stateName = csvStatesCensus.stateName ;
        this.stateCode = csvStatesCensus. stateCode;
    }
}


