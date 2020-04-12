package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class UsCensusCSV {
    @CsvBindByName(column = "State Id", required = true)
    public String stateId;

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public long population;

    @CsvBindByName(column = "Housing units", required = true)
    public int housingUnits;

    @CsvBindByName(column = "Total area", required = true)
    public float totalArea;

    @CsvBindByName(column = "Water area", required = true)
    public float waterArea;

    @CsvBindByName(column = "Land area", required = true)
    public float landArea;

    @CsvBindByName(column = "Population Density", required = true)
    public float populationDensity;

    @CsvBindByName(column = "Housing Density", required = true)
    public float housingDensity;

    public UsCensusCSV(String stateCode, String state, long population, long areaInSqKm, long densityPerSqkm) {
            this.stateId = stateId;
            this.state = state;
            this.population = population;
            this.totalArea = totalArea;
            this.populationDensity = populationDensity;
    }

    public UsCensusCSV() {
    }

    @Override
    public String toString() {
        return "CSVUSCensus{" +
                "State Id='" + stateId + '\'' +
                ", State='" + state + '\'' +
                ", Population Density=" + populationDensity +
                ", Population=" + population +
                ", Total area=" + totalArea +
                '}';
    }
}
