package app.kizen.eletricista.domain;

/**
 * Serviço para seleção de disjuntores padronizados conforme NBR 5410.
 * Aplica margem de segurança (ex: 15%) e seleciona o disjuntor comercial
 * imediatamente superior à corrente calculada.
 */
public class BreakerService {
    /** Disjuntores padronizados em ampères (IEC 60947-2) */
    private static final int[] STANDARD_BREAKERS = {
        6, 10, 16, 20, 25, 32, 40, 50, 63, 70, 80, 100,
        125, 150, 160, 175, 200, 225, 250, 300, 350, 400,
        500, 600, 800, 1000, 1250, 1600
    };

    /** Margem de segurança padrão (15%) para proteção adequada. */
    public static final double DEFAULT_SAFETY_MARGIN = 1.15;

    /**
     * Seleciona o disjuntor adequado para a corrente calculada.
     * 
     * @param designCurrent Corrente de projeto (A)
     * @param safetyMargin Margem de segurança (ex: 1.15 para 15%)
     * @return Corrente nominal do disjuntor (A), ou 0 se exceder limite
     */
    public static int selectBreaker(double designCurrent, double safetyMargin) {
        double adjustedCurrent = designCurrent * safetyMargin;
        
        for (int breaker : STANDARD_BREAKERS) {
            if (breaker >= adjustedCurrent) {
                return breaker;
            }
        }
        return 0; // Excede máximo disponível
    }

    /**
     * Seleciona disjuntor com margem padrão de 15%.
     * 
     * @param designCurrent Corrente de projeto (A)
     * @return Corrente nominal do disjuntor (A), ou 0 se exceder
     */
    public static int selectBreaker(double designCurrent) {
        return selectBreaker(designCurrent, DEFAULT_SAFETY_MARGIN);
    }

    /**
     * Verifica se o disjuntor selecionado protege adequadamente o condutor.
     * Regra: In (disjuntor) ≤ Iz (capacidade corrigida do condutor).
     * 
     * @param breakerRating Corrente nominal do disjuntor (A)
     * @param conductorAmpacity Capacidade corrigida do condutor (A)
     * @return true se protegido adequadamente
     */
    public static boolean isAdequateProtection(int breakerRating, double conductorAmpacity) {
        return breakerRating <= conductorAmpacity;
    }
}
