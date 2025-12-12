package app.kizen.eletricista.domain;

/**
 * Serviço para dimensionamento de geradores elétricos baseado em consumo (kVA).
 * Considera fator de potência, reserva técnica e tipo de carga para calcular
 * a potência nominal adequada do gerador.
 */
public class GeneratorSizingService {
    
    /** Resultado do dimensionamento: potência nominal e corrente máxima. */
    public static class Result {
        //
        public final double nominalPowerKva;
        public final double maxCurrent;
        public final double activePowerKw;
        public final double reserveMargin;
        
        public Result(double kva, double current, double kw, double margin) {
            this.nominalPowerKva = kva;
            this.maxCurrent = current;
            this.activePowerKw = kw;
            this.reserveMargin = margin;
        }
    }
    
    /** Tipos de carga com fatores de potência típicos */
    public enum LoadType {
        RESISTIVA(0.95, 1.10),      // Iluminação, aquecedores (FP alto, baixa reserva)
        INDUTIVA_LEVE(0.85, 1.20),  // Ar-cond, geladeiras (FP médio, reserva média)
        INDUTIVA_PESADA(0.70, 1.30), // Motores, bombas (FP baixo, alta reserva)
        MISTA(0.80, 1.25);           // Combinação de cargas (FP médio-baixo)
        
        public final double typicalPowerFactor;
        public final double reserveMultiplier; // Margem de segurança
        
        LoadType(double pf, double reserve) {
            this.typicalPowerFactor = pf;
            this.reserveMultiplier = reserve;
        }
    }
    
    /**
     * Dimensiona gerador para consumo total especificado.
     * 
     * @param totalLoadKw Carga total em kW (potência ativa)
     * @param voltage Tensão de operação (V) - 127, 220 ou 380
     * @param powerFactor Fator de potência (0.0-1.0). Use LoadType.typicalPowerFactor se incerto.
     * @param loadType Tipo de carga predominante (afeta reserva técnica)
     * @param isThreePhase Sistema trifásico (true) ou monofásico (false)
     * @return Resultado com potência nominal do gerador e corrente máxima
     */
    public static Result size(double totalLoadKw, 
                              double voltage, 
                              double powerFactor, 
                              LoadType loadType,
                              boolean isThreePhase) {
        // Validações
        if (totalLoadKw <= 0) throw new IllegalArgumentException("Carga total deve ser positiva");
        if (voltage <= 0) throw new IllegalArgumentException("Tensão deve ser positiva");
        if (powerFactor <= 0 || powerFactor > 1) throw new IllegalArgumentException("Fator de potência deve estar entre 0 e 1");
        
        // Potência aparente base (S = P / FP)
        double apparentPowerKva = totalLoadKw / powerFactor;
        
        // Aplicar margem de segurança conforme tipo de carga
        double nominalKva = apparentPowerKva * loadType.reserveMultiplier;
        
        // Arredondar para potência comercial mais próxima (acima)
        nominalKva = roundToCommercialSize(nominalKva);
        
        // Calcular corrente máxima
        double maxCurrent;
        if (isThreePhase) {
            // I = S / (√3 × V)
            maxCurrent = (nominalKva * 1000) / (Math.sqrt(3) * voltage);
        } else {
            // I = S / V
            maxCurrent = (nominalKva * 1000) / voltage;
        }
        
        double reservePercentage = ((nominalKva / apparentPowerKva) - 1) * 100;
        
        return new Result(nominalKva, maxCurrent, totalLoadKw, reservePercentage);
    }
    
    /**
     * Sobrecarga simplificada usando fator de potência típico do tipo de carga.
     */
    public static Result size(double totalLoadKw, 
                              double voltage, 
                              LoadType loadType,
                              boolean isThreePhase) {
        return size(totalLoadKw, voltage, loadType.typicalPowerFactor, loadType, isThreePhase);
    }
    
    /**
     * Arredonda para potência comercial de geradores (série de valores padronizados).
     */
    private static double roundToCommercialSize(double kva) {
        double[] commercialSizes = {
            2.5, 3.5, 4, 5, 6, 7.5, 8, 10, 12, 15, 18, 20, 25, 30, 35, 
            40, 45, 50, 60, 75, 80, 100, 125, 150, 175, 200, 250, 300, 
            350, 400, 500, 625, 750, 875, 1000, 1250, 1500, 2000
        };
        
        for (double size : commercialSizes) {
            if (size >= kva) {
                return size;
            }
        }
        
        // Se exceder máximo da lista, arredondar para múltiplo de 500
        return Math.ceil(kva / 500) * 500;
    }
    
    /**
     * Calcula consumo estimado de combustível (diesel) em L/h.
     * Estimativa: ~0.25 L/h por kW de carga (valor médio para geradores diesel).
     * 
     * @param loadKw Carga efetiva em kW
     * @param loadFactor Fator de carga (0.0-1.0). 0.75 = 75% da capacidade nominal
     * @return Consumo aproximado em litros por hora
     */
    public static double estimateFuelConsumption(double loadKw, double loadFactor) {
        // Consumo específico médio: 0.25 L/kWh a plena carga
        // Ajustado conforme fator de carga (consumo não é linear)
        double baseConsumption = 0.25; // L/kWh
        double efficiencyFactor = 0.7 + (0.3 * loadFactor); // Eficiência aumenta com carga
        
        return loadKw * baseConsumption * loadFactor / efficiencyFactor;
    }
    
    /**
     * Verifica se gerador de determinada potência é adequado para a carga.
     * 
     * @param generatorKva Potência nominal do gerador (kVA)
     * @param loadKw Carga total (kW)
     * @param powerFactor Fator de potência
     * @return true se adequado, false se subdimensionado
     */
    public static boolean isAdequate(double generatorKva, double loadKw, double powerFactor) {
        double requiredKva = loadKw / powerFactor;
        // Considera adequado se gerador tem pelo menos 10% de margem
        return generatorKva >= (requiredKva * 1.10);
    }
}
