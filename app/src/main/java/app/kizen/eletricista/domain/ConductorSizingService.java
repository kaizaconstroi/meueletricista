package app.kizen.eletricista.domain;

import java.util.Optional;

/**
 * Serviço de dimensionamento de condutor.
 * Aplica fatores de agrupamento e temperatura sobre a ampacidade base,
 * selecionando a menor seção que atende a corrente de projeto.
 */
public class ConductorSizingService {
    /** Resultado do dimensionamento: seção e ampacidade corrigida. */
    public static class Result {
        public final double section;
        public final double correctedAmpacity;
        public Result(double s, double a){ this.section=s; this.correctedAmpacity=a; }
    }

    /**
     * Dimensiona o condutor para a corrente de projeto.
     * @param designCurrent Corrente de projeto (A)
     * @param code Tipo de instalação + nº condutores carregados
     * @param insulation Material isolante (PVC/EPR)
     * @param ambientTemp Temperatura ambiente (°C)
     * @param groupingCount Nº de circuitos agrupados
     * @return Seção e ampacidade corrigida, ou vazio se nenhum atende
     */
    public static Optional<Result> size(double designCurrent,
                                        ConductorTables.InstallationCode code,
                                        ConductorTables.Insulation insulation,
                                        double ambientTemp,
                                        int groupingCount){
        double fGrouping = groupingFactor(groupingCount);
        double fTemp = temperatureFactor(ambientTemp);
        ConductorTables.Entry[] entries = ConductorTables.get(insulation, code);
        for(ConductorTables.Entry e : entries){
            double corrected = e.ampacity * fGrouping * fTemp;
            if(corrected >= designCurrent){
                return Optional.of(new Result(e.sectionMm2, corrected));
            }
        }
        return Optional.empty();
    }

    /** Fator de agrupamento (Fca) conforme quantidade de condutos. */
    private static double groupingFactor(int n){
        if(n<=1) return 1.0;
        switch (n){
            case 2: return 0.80;
            case 3: return 0.70;
            case 4: return 0.65;
            case 5: return 0.60;
            default: return 0.50; // fallback se >5 não coberto
        }
    }

    /** Fator de correção por temperatura ambiente. */
    private static double temperatureFactor(double t){
        if (t <= 10) return 1.22;
        if (t <= 15) return 1.17;
        if (t <= 20) return 1.12;
        if (t <= 25) return 1.06;
        if (t <= 30) return 1.00;
        if (t <= 35) return 0.94;
        if (t <= 40) return 0.87;
        if (t <= 45) return 0.79;
        if (t <= 50) return 0.71;
        if (t <= 55) return 0.61;
        if (t <= 60) return 0.50;
        return 0.45; // acima da faixa, depreciação adicional
    }
}
