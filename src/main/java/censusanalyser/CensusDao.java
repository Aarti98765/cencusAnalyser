package censusanalyser;

import com.stateanalyser.IndiaStateCsvDto;

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

    public CensusDao(IndiaCensusCsvDto csvStatesCensus) {
        this.State = csvStatesCensus.state;
        this.Population = csvStatesCensus.population;
        this.AreaInSqKm = csvStatesCensus.areaInSqKm;
        this.DensityPerSqkm = csvStatesCensus.densityPerSqKm;
    }

    public CensusDao(IndiaStateCsvDto csvStatesPojoClass) {
        this.StateName = csvStatesPojoClass.stateName;
        this.SrNo = csvStatesPojoClass.srNo;
        this.TIN = csvStatesPojoClass.tin;
        this.StateCode = csvStatesPojoClass.stateCode;
    }

    public CensusDao(UsCensusCsvDto csvUsCensus) {
        this.StateID = csvUsCensus.stateId;
        this.State = csvUsCensus.state;
        this.Population = csvUsCensus.population;
        this.AreaInSqKm = (long) csvUsCensus.totalArea;
        this.DensityPerSqkm = (long) csvUsCensus.populationDensity;
        this.HousingDensity = csvUsCensus.housingDensity;
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

    public long getAreaInSqKm() {
        return AreaInSqKm;
    }

    public long getDensityPerSqkm() {
        return DensityPerSqkm;
    }

    public Object getCensusDTO(Analyser.Country country) {
        if (country.equals(Analyser.Country.INDIA))
            return new IndiaCensusCsvDto(State, Population, AreaInSqKm, DensityPerSqkm);
        if (country.equals(Analyser.Country.US))
            return new UsCensusCsvDto(StateCode, State, Population, AreaInSqKm, DensityPerSqkm);
        return null;
    }
}


