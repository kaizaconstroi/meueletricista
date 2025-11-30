package app.kizen.eletricista.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes unitários para BreakerService.
 * Valida seleção de disjuntores e verificação de proteção adequada.
 */
public class BreakerServiceTest {

    @Test
    public void testSelectBreaker_BasicCases() {
        // Corrente 45A -> disjuntor 63A (com margem 15%)
        assertEquals(63, BreakerService.selectBreaker(45.0));
        
        // Corrente 20A -> disjuntor 25A
        assertEquals(25, BreakerService.selectBreaker(20.0));
        
        // Corrente 8A -> disjuntor 10A
        assertEquals(10, BreakerService.selectBreaker(8.0));
    }

    @Test
    public void testSelectBreaker_WithCustomMargin() {
        // Corrente 50A com margem 1.25 (25%) -> disjuntor 70A
        assertEquals(70, BreakerService.selectBreaker(50.0, 1.25));
        
        // Corrente 50A com margem 1.0 (sem margem) -> disjuntor 50A
        assertEquals(50, BreakerService.selectBreaker(50.0, 1.0));
    }

    @Test
    public void testSelectBreaker_EdgeValues() {
        // Corrente no limite exato de um disjuntor padrão
        assertEquals(32, BreakerService.selectBreaker(27.83)); // 27.83*1.15 = 32.00
        
        // Corrente logo acima força próximo disjuntor
        assertEquals(40, BreakerService.selectBreaker(28.0)); // 28*1.15 = 32.2 -> 40A
    }

    @Test
    public void testSelectBreaker_Excessive() {
        // Corrente que excede máximo disponível (1600A)
        assertEquals(0, BreakerService.selectBreaker(1500.0)); // 1500*1.15 = 1725 > 1600
    }

    @Test
    public void testSelectBreaker_VeryLow() {
        // Corrente muito baixa
        assertEquals(6, BreakerService.selectBreaker(1.0)); // 1*1.15 = 1.15 -> 6A (mínimo)
        assertEquals(6, BreakerService.selectBreaker(5.0)); // 5*1.15 = 5.75 -> 6A
    }

    @Test
    public void testAdequateProtection_Valid() {
        // Disjuntor 50A protegendo condutor com Iz=60A -> OK
        assertTrue(BreakerService.isAdequateProtection(50, 60.0));
        
        // Disjuntor 63A protegendo condutor com Iz=63A (limite) -> OK
        assertTrue(BreakerService.isAdequateProtection(63, 63.0));
    }

    @Test
    public void testAdequateProtection_Invalid() {
        // Disjuntor 70A para condutor com Iz=60A -> FALHA (disjuntor > condutor)
        assertFalse(BreakerService.isAdequateProtection(70, 60.0));
        
        // Disjuntor 100A para Iz=99A -> FALHA
        assertFalse(BreakerService.isAdequateProtection(100, 99.0));
    }

    @Test
    public void testFullWorkflow() {
        // Workflow completo: corrente 50A -> seleciona disjuntor -> valida condutor
        double designCurrent = 50.0;
        int breaker = BreakerService.selectBreaker(designCurrent); // 50*1.15 = 57.5 -> 63A
        
        assertEquals(63, breaker);
        
        // Condutor com Iz=75A protege adequadamente?
        assertTrue(BreakerService.isAdequateProtection(breaker, 75.0));
        
        // Condutor com Iz=60A NÃO protege?
        assertFalse(BreakerService.isAdequateProtection(breaker, 60.0));
    }
}
