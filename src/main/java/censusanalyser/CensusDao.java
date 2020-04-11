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
    public String StateId;
    public String State;
    public int Population;
    public int HousingUnit;
    public float TotalArea;
    public float WaterArea;
    public float LandArea;
    public float PopulationDensity;

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

    public void CensusDAO(UsStateCSV csvUsCensus){
        this.StateId = csvUsCensus.stateId;
        this.State = csvUsCensus.state;
        this.Population = csvUsCensus.population;
        this.HousingUnit = csvUsCensus.housingUnits;
        this.TotalArea = csvUsCensus.totalArea;
        this.WaterArea = csvUsCensus.waterArea;
        this.LandArea= csvUsCensus.landArea;
        this.PopulationDensity = csvUsCensus.populationDensity;
    }
}


