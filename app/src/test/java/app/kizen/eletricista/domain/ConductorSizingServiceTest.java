package app.kizen.eletricista.domain;

import org.junit.Test;
import java.util.Optional;
import static org.junit.Assert.*;

/**
 * Testes unitários para ConductorSizingService.
 * Valida dimensionamento de condutores, fatores de correção e edge cases.
 */
public class ConductorSizingServiceTest {

    @Test
    public void testBasicSizing_B1_2Conductors_PVC() {
        // Cenário: 50A, instalação B1, 2 condutores, PVC, 30°C, sem agrupamento
        Optional<ConductorSizingService.Result> result = ConductorSizingService.size(
            50.0,
            ConductorTables.InstallationCode.B1_2,
            ConductorTables.Insulation.PVC,
            30.0,
            1
        );

        assertTrue("Deve retornar resultado", result.isPresent());
        assertEquals("Seção esperada 10 mm²", 10.0, result.get().section, 0.01);
        assertTrue("Ampacidade corrigida deve ser >= 50A", result.get().correctedAmpacity >= 50.0);
    }

    @Test
    public void testGroupingFactor() {
        // Cenário: corrente baixa com agrupamento de 3 circuitos
        Optional<ConductorSizingService.Result> result = ConductorSizingService.size(
            15.0,
            ConductorTables.InstallationCode.B1_2,
            ConductorTables.Insulation.PVC,
            30.0,
            3 // 3 circuitos agrupados, fator 0.70
        );

        assertTrue("Deve retornar resultado", result.isPresent());
        // Com fator 0.70, a seção precisa ser maior que sem agrupamento
        assertTrue("Seção deve ser >= 2.5 mm² com agrupamento", result.get().section >= 2.5);
    }

    @Test
    public void testTemperatureFactor() {
        // Cenário: alta temperatura ambiente (45°C)
        Optional<ConductorSizingService.Result> result = ConductorSizingService.size(
            30.0,
            ConductorTables.InstallationCode.B1_2,
            ConductorTables.Insulation.PVC,
            45.0, // Temperatura alta reduz ampacidade
            1
        );

        assertTrue("Deve retornar resultado", result.isPresent());
        // A seção deve ser maior devido à temperatura elevada
        assertTrue("Seção deve ser >= 4 mm² com 45°C", result.get().section >= 4.0);
    }

    @Test
    public void testEPR_vs_PVC() {
        // Comparar EPR (maior capacidade) vs PVC para mesma corrente
        Optional<ConductorSizingService.Result> resultPVC = ConductorSizingService.size(
            80.0,
            ConductorTables.InstallationCode.A1_2,
            ConductorTables.Insulation.PVC,
            30.0,
            1
        );

        Optional<ConductorSizingService.Result> resultEPR = ConductorSizingService.size(
            80.0,
            ConductorTables.InstallationCode.A1_2,
            ConductorTables.Insulation.EPR,
            30.0,
            1
        );

        assertTrue("Ambos devem retornar resultado", resultPVC.isPresent() && resultEPR.isPresent());
        // EPR pode usar seção menor ou igual para mesma corrente
        assertTrue("EPR deve permitir seção <= PVC", resultEPR.get().section <= resultPVC.get().section);
    }

    @Test
    public void testLowCurrent() {
        // Corrente muito baixa (1A)
        Optional<ConductorSizingService.Result> result = ConductorSizingService.size(
            1.0,
            ConductorTables.InstallationCode.B1_2,
            ConductorTables.Insulation.PVC,
            30.0,
            1
        );

        assertTrue("Deve retornar resultado", result.isPresent());
        assertTrue("Seção mínima deve ser <= 2.5 mm²", result.get().section <= 2.5);
    }

    @Test
    public void testHighCurrent() {
        // Corrente elevada (200A)
        Optional<ConductorSizingService.Result> result = ConductorSizingService.size(
            200.0,
            ConductorTables.InstallationCode.B1_2,
            ConductorTables.Insulation.PVC,
            30.0,
            1
        );

        assertTrue("Deve retornar resultado", result.isPresent());
        assertTrue("Seção para 200A deve ser >= 70 mm²", result.get().section >= 70.0);
    }

    @Test
    public void testExcessiveCurrent() {
        // Corrente que excede tabelas (3000A)
        Optional<ConductorSizingService.Result> result = ConductorSizingService.size(
            3000.0,
            ConductorTables.InstallationCode.B1_2,
            ConductorTables.Insulation.PVC,
            30.0,
            1
        );

        // Deve retornar vazio ou maior seção disponível
        if (result.isPresent()) {
            assertEquals("Deve retornar maior seção (1000 mm²)", 1000.0, result.get().section, 0.01);
        }
    }
}
