package censusanalyser;

import com.stateanalyser.IndiaStateCSV;

import java.util.Comparator;

public class CensusDao {
    public float HousingDensity;
    public String StateID;
    public String StateName;
    public long Population;
    public long AreaInSqKm;
    public long DensityPerSqkm;
    public String StateCode;
    public int TIN;
    public String State;
    public int SrNo;

    public CensusDao(IndiaCensusCSV csvStatesCensus) {
        this.State = csvStatesCensus.state;
        this.Population = csvStatesCensus.population;
        this.AreaInSqKm = csvStatesCensus.areaInSqKm;
        this.DensityPerSqkm = csvStatesCensus.densityPerSqKm;
    }

    public CensusDao(IndiaStateCSV csvStatesPojoClass) {
        this.StateName = csvStatesPojoClass.stateName;
        this.SrNo = csvStatesPojoClass.srNo;
        this.TIN = csvStatesPojoClass.tin;
        this.StateCode = csvStatesPojoClass.stateCode;
    }

    public CensusDao(UsCensusCSV csvusCensus) {
        this.StateID = csvusCensus.stateId;
        this.State = csvusCensus.state;
        this.Population = csvusCensus.population;
        this.AreaInSqKm = (long) csvusCensus.totalArea;
        this.DensityPerSqkm = (long) csvusCensus.populationDensity;
        this.HousingDensity = csvusCensus.housingDensity;
    }

    public static Comparator<CensusDao> getSortComparator(Analyser.SortingMode mode) {
        if (mode.equals(Analyser.SortingMode.STATE))
            return Comparator.comparing(census -> census.State);
        if (mode.equals(Analyser.SortingMode.POPULATION))
            return Comparator.comparing(CensusDao::getPopulation).reversed();
        if (mode.equals(Analyser.SortingMode.AREA))
            return Comparator.comparing(CensusDao::getAreaInSqKm).reversed();
        if (mode.equals(Analyser.SortingMode.DENSITY))
            return Comparator.comparing(CensusDao::getDensityPerSqkm).reversed();
        return null;
    }

    public long getPopulation() {
        return Population;
    }

    public void setPopulation(long population) {
        Population = population;
    }

    public long getAreaInSqKm() {
        return AreaInSqKm;
    }

    public void setAreaInSqKm(long areaInSqKm) {
        AreaInSqKm = areaInSqKm;
    }

    public long getDensityPerSqkm() {
        return DensityPerSqkm;
    }

    public void setDensityPerSqkm(int densityPerSqkm) {
        DensityPerSqkm = densityPerSqkm;
    }

    public Object getCensusDTO(Analyser.Country country) {
        if (country.equals(Analyser.Country.INDIA))
            return new IndiaCensusCSV(State, Population, AreaInSqKm, DensityPerSqkm);
        if (country.equals(Analyser.Country.US))
            return new UsCensusCSV(StateCode, State, Population, AreaInSqKm, DensityPerSqkm);
        return null;
    }
}


