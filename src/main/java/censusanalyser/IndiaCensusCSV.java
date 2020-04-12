package censusanalyser;

import com.opencsv.bean.CsvBindByName;
import com.stateanalyser.IndiaStateCSV;

public class IndiaCensusCSV extends IndiaStateCSV {

    @CsvBindByName(column = "State", required = true)
    public static String state;

    @CsvBindByName(column = "Population", required = true)
    public long population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public long areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public long densityPerSqKm;

    public IndiaCensusCSV(String state, long population, long areaInSqKm, long densityPerSqkm) {
        state = state;
        population = population;
        areaInSqKm = areaInSqKm;
        densityPerSqkm = densityPerSqkm;
    }

    public IndiaCensusCSV() {
    }

    @Override
    public String toString() {
        return "IndiaCensusCSV{" +
                "State='" + state + '\'' +
                ", Population='" + population + '\'' +
                ", AreaInSqKm='" + areaInSqKm + '\'' +
                ", DensityPerSqKm='" + densityPerSqKm + '\'' +
                '}';
    }
}
