# Voltrix - Guia de Migração Java → Kotlin

## 📋 Estrutura do Projeto Original

### Pacotes Identificados
```
app.kizen.eletricista/
├── domain/          (Lógica de negócio - PRIORIDADE ALTA)
│   ├── ConductorSizingService.java
│   ├── ConductorTables.java
│   └── BreakerService.java
├── util/            (Validadores)
│   └── InputValidator.java
├── api/             (Utilitários legados + helpers)
│   └── AppUtil.java
└── view/            (Activities - UI)
    ├── MainActivity.java
    ├── CondutorActivity.java
    ├── DisjuntorActivity.java
    ├── CorrenteCircuitoActivity.java
    ├── PotenciaActivity.java
    ├── ConsumoActivity.java
    ├── QuedadeTensaoActivity.java
    ├── CondutorCompletoctivity.java
    ├── Ajuda.java
    ├── PoliticaDePrivacidadeActivity.java
    └── SplahActivity.java
```

---

## 🔧 Código Kotlin Pronto para Uso

### 1. ConductorTables.kt
**Tabelas de ampacidade por tipo de instalação (NBR 5410)**

```kotlin
package app.kizen.voltrix.domain

/**
 * Tabelas de ampacidade por tipo de instalação e material isolante.
 * Base: NBR 5410
 */
object ConductorTables {
    
    /** Tipo de instalação + número de condutores carregados */
    enum class InstallationCode {
        A1_2, A1_3, A2_2, A2_3, 
        B1_2, B1_3, B2_2, B2_3,
        C1_2, C1_3
    }
    
    /** Material isolante do condutor */
    enum class Insulation { PVC, EPR }
    
    /** Entrada da tabela: seção (mm²) e ampacidade (A) base */
    data class Entry(
        val sectionMm2: Double,
        val ampacity: Double
    )
    
    private val data: Map<Insulation, Map<InstallationCode, List<Entry>>> = mapOf(
        Insulation.PVC to mapOf(
            InstallationCode.A1_2 to listOf(
                Entry(0.5, 10.0), Entry(0.75, 9.0), Entry(1.0, 11.0), Entry(1.5, 14.5),
                Entry(2.5, 19.5), Entry(4.0, 26.0), Entry(6.0, 34.0), Entry(10.0, 46.0),
                Entry(16.0, 61.0), Entry(25.0, 80.0), Entry(35.0, 99.0), Entry(50.0, 119.0),
                Entry(70.0, 151.0), Entry(95.0, 182.0), Entry(120.0, 210.0), Entry(150.0, 240.0),
                Entry(185.0, 273.0), Entry(240.0, 321.0), Entry(300.0, 367.0), Entry(400.0, 438.0),
                Entry(500.0, 502.0), Entry(630.0, 578.0), Entry(800.0, 669.0), Entry(1000.0, 767.0)
            ),
            InstallationCode.A1_3 to listOf(
                Entry(0.5, 7.0), Entry(0.75, 9.0), Entry(1.0, 10.0), Entry(1.5, 13.5),
                Entry(2.5, 18.0), Entry(4.0, 24.0), Entry(6.0, 31.0), Entry(10.0, 42.0),
                Entry(16.0, 56.0), Entry(25.0, 73.0), Entry(35.0, 89.0), Entry(50.0, 108.0),
                Entry(70.0, 136.0), Entry(95.0, 164.0), Entry(120.0, 188.0), Entry(150.0, 216.0),
                Entry(185.0, 245.0), Entry(240.0, 286.0), Entry(300.0, 328.0), Entry(400.0, 390.0),
                Entry(500.0, 447.0), Entry(630.0, 514.0), Entry(800.0, 593.0), Entry(1000.0, 679.0)
            ),
            InstallationCode.A2_2 to listOf(
                Entry(0.5, 7.0), Entry(0.75, 9.0), Entry(1.0, 10.0), Entry(1.5, 14.0),
                Entry(2.5, 18.5), Entry(4.0, 25.0), Entry(6.0, 32.0), Entry(10.0, 43.0),
                Entry(16.0, 57.0), Entry(25.0, 75.0), Entry(35.0, 92.0), Entry(50.0, 110.0),
                Entry(70.0, 139.0), Entry(95.0, 167.0), Entry(120.0, 192.0), Entry(150.0, 219.0),
                Entry(185.0, 248.0), Entry(240.0, 291.0), Entry(300.0, 334.0), Entry(400.0, 398.0),
                Entry(500.0, 456.0), Entry(630.0, 526.0), Entry(800.0, 609.0), Entry(1000.0, 698.0)
            ),
            InstallationCode.A2_3 to listOf(
                Entry(0.5, 7.0), Entry(0.75, 9.0), Entry(1.0, 10.0), Entry(1.5, 13.0),
                Entry(2.5, 17.5), Entry(4.0, 23.0), Entry(6.0, 29.0), Entry(10.0, 39.0),
                Entry(16.0, 52.0), Entry(25.0, 68.0), Entry(35.0, 83.0), Entry(50.0, 99.0),
                Entry(70.0, 125.0), Entry(95.0, 150.0), Entry(120.0, 172.0), Entry(150.0, 196.0),
                Entry(185.0, 223.0), Entry(240.0, 261.0), Entry(300.0, 298.0), Entry(400.0, 355.0),
                Entry(500.0, 406.0), Entry(630.0, 467.0), Entry(800.0, 540.0), Entry(1000.0, 618.0)
            ),
            InstallationCode.B1_2 to listOf(
                Entry(0.5, 9.0), Entry(0.75, 11.0), Entry(1.0, 14.0), Entry(1.5, 17.5),
                Entry(2.5, 24.0), Entry(4.0, 32.0), Entry(6.0, 41.0), Entry(10.0, 57.0),
                Entry(16.0, 76.0), Entry(25.0, 101.0), Entry(35.0, 125.0), Entry(50.0, 151.0),
                Entry(70.0, 192.0), Entry(95.0, 232.0), Entry(120.0, 269.0), Entry(150.0, 309.0),
                Entry(185.0, 353.0), Entry(240.0, 415.0), Entry(300.0, 477.0), Entry(400.0, 571.0),
                Entry(500.0, 656.0), Entry(630.0, 758.0), Entry(800.0, 881.0), Entry(1000.0, 1012.0)
            ),
            InstallationCode.B1_3 to listOf(
                Entry(0.5, 8.0), Entry(0.75, 10.0), Entry(1.0, 12.0), Entry(1.5, 15.5),
                Entry(2.5, 21.0), Entry(4.0, 28.0), Entry(6.0, 36.0), Entry(10.0, 50.0),
                Entry(16.0, 68.0), Entry(25.0, 89.0), Entry(35.0, 110.0), Entry(50.0, 134.0),
                Entry(70.0, 171.0), Entry(95.0, 207.0), Entry(120.0, 239.0), Entry(150.0, 275.0),
                Entry(185.0, 314.0), Entry(240.0, 370.0), Entry(300.0, 426.0), Entry(400.0, 510.0),
                Entry(500.0, 587.0), Entry(630.0, 678.0), Entry(800.0, 788.0), Entry(1000.0, 906.0)
            ),
            InstallationCode.B2_2 to listOf(
                Entry(0.5, 9.0), Entry(0.75, 11.0), Entry(1.0, 13.0), Entry(1.5, 16.5),
                Entry(2.5, 23.0), Entry(4.0, 30.0), Entry(6.0, 38.0), Entry(10.0, 52.0),
                Entry(16.0, 69.0), Entry(25.0, 90.0), Entry(35.0, 111.0), Entry(50.0, 133.0),
                Entry(70.0, 168.0), Entry(95.0, 201.0), Entry(120.0, 232.0), Entry(150.0, 265.0),
                Entry(185.0, 300.0), Entry(240.0, 351.0), Entry(300.0, 401.0), Entry(400.0, 477.0),
                Entry(500.0, 545.0), Entry(630.0, 626.0), Entry(800.0, 723.0), Entry(1000.0, 827.0)
            ),
            InstallationCode.B2_3 to listOf(
                Entry(0.5, 8.0), Entry(0.75, 10.0), Entry(1.0, 12.0), Entry(1.5, 15.0),
                Entry(2.5, 20.0), Entry(4.0, 27.0), Entry(6.0, 34.0), Entry(10.0, 46.0),
                Entry(16.0, 62.0), Entry(25.0, 80.0), Entry(35.0, 99.0), Entry(50.0, 118.0),
                Entry(70.0, 149.0), Entry(95.0, 179.0), Entry(120.0, 206.0), Entry(150.0, 236.0),
                Entry(185.0, 268.0), Entry(240.0, 313.0), Entry(300.0, 358.0), Entry(400.0, 425.0),
                Entry(500.0, 486.0), Entry(630.0, 559.0), Entry(800.0, 645.0), Entry(1000.0, 738.0)
            )
        ),
        Insulation.EPR to mapOf(
            InstallationCode.A1_2 to listOf(
                Entry(0.5, 10.0), Entry(0.75, 12.0), Entry(1.0, 15.0), Entry(1.5, 19.0),
                Entry(2.5, 26.0), Entry(4.0, 35.0), Entry(6.0, 45.0), Entry(10.0, 61.0),
                Entry(16.0, 81.0), Entry(25.0, 106.0), Entry(35.0, 131.0), Entry(50.0, 158.0),
                Entry(70.0, 200.0), Entry(95.0, 241.0), Entry(120.0, 278.0), Entry(150.0, 318.0),
                Entry(185.0, 362.0), Entry(240.0, 424.0), Entry(300.0, 486.0), Entry(400.0, 579.0),
                Entry(500.0, 664.0), Entry(630.0, 765.0), Entry(800.0, 885.0), Entry(1000.0, 1014.0)
            ),
            InstallationCode.A1_3 to listOf(
                Entry(0.5, 9.0), Entry(0.75, 11.0), Entry(1.0, 13.0), Entry(1.5, 17.0),
                Entry(2.5, 23.0), Entry(4.0, 31.0), Entry(6.0, 40.0), Entry(10.0, 54.0),
                Entry(16.0, 73.0), Entry(25.0, 95.0), Entry(35.0, 117.0), Entry(50.0, 141.0),
                Entry(70.0, 179.0), Entry(95.0, 216.0), Entry(120.0, 249.0), Entry(150.0, 285.0),
                Entry(185.0, 324.0), Entry(240.0, 380.0), Entry(300.0, 435.0), Entry(400.0, 519.0),
                Entry(500.0, 595.0), Entry(630.0, 685.0), Entry(800.0, 792.0), Entry(1000.0, 908.0)
            ),
            InstallationCode.B1_2 to listOf(
                Entry(0.5, 12.0), Entry(0.75, 15.0), Entry(1.0, 18.0), Entry(1.5, 23.0),
                Entry(2.5, 31.0), Entry(4.0, 42.0), Entry(6.0, 54.0), Entry(10.0, 75.0),
                Entry(16.0, 100.0), Entry(25.0, 133.0), Entry(35.0, 144.0), Entry(50.0, 175.0),
                Entry(70.0, 222.0), Entry(95.0, 269.0), Entry(120.0, 312.0), Entry(150.0, 358.0),
                Entry(185.0, 408.0), Entry(240.0, 481.0), Entry(300.0, 553.0), Entry(400.0, 661.0),
                Entry(500.0, 760.0), Entry(630.0, 879.0), Entry(800.0, 1020.0), Entry(1000.0, 1173.0)
            ),
            InstallationCode.B1_3 to listOf(
                Entry(0.5, 12.0), Entry(0.75, 15.0), Entry(1.0, 18.0), Entry(1.5, 23.0),
                Entry(2.5, 31.0), Entry(4.0, 42.0), Entry(6.0, 54.0), Entry(10.0, 75.0),
                Entry(16.0, 100.0), Entry(25.0, 133.0), Entry(35.0, 144.0), Entry(50.0, 175.0),
                Entry(70.0, 222.0), Entry(95.0, 269.0), Entry(120.0, 312.0), Entry(150.0, 358.0),
                Entry(185.0, 408.0), Entry(240.0, 481.0), Entry(300.0, 553.0), Entry(400.0, 661.0),
                Entry(500.0, 760.0), Entry(630.0, 879.0), Entry(800.0, 1020.0), Entry(1000.0, 1173.0)
            ),
            InstallationCode.C1_2 to listOf(
                Entry(0.5, 12.0), Entry(0.75, 16.0), Entry(1.0, 19.0), Entry(1.5, 24.0),
                Entry(2.5, 33.0), Entry(4.0, 45.0), Entry(6.0, 58.0), Entry(10.0, 80.0),
                Entry(16.0, 107.0), Entry(25.0, 138.0), Entry(35.0, 171.0), Entry(50.0, 209.0),
                Entry(70.0, 269.0), Entry(95.0, 328.0), Entry(120.0, 382.0), Entry(150.0, 441.0),
                Entry(185.0, 506.0), Entry(240.0, 599.0), Entry(300.0, 693.0), Entry(400.0, 835.0),
                Entry(500.0, 966.0), Entry(630.0, 1122.0), Entry(800.0, 1311.0), Entry(1000.0, 1515.0)
            ),
            InstallationCode.C1_3 to listOf(
                Entry(0.5, 11.0), Entry(0.75, 14.0), Entry(1.0, 17.0), Entry(1.5, 22.0),
                Entry(2.5, 30.0), Entry(4.0, 40.0), Entry(6.0, 52.0), Entry(10.0, 71.0),
                Entry(16.0, 96.0), Entry(25.0, 119.0), Entry(35.0, 147.0), Entry(50.0, 179.0),
                Entry(70.0, 229.0), Entry(95.0, 278.0), Entry(120.0, 322.0), Entry(150.0, 371.0),
                Entry(185.0, 424.0), Entry(240.0, 500.0), Entry(300.0, 576.0), Entry(400.0, 692.0),
                Entry(500.0, 797.0), Entry(630.0, 923.0), Entry(800.0, 1074.0), Entry(1000.0, 1237.0)
            )
        )
    )
    
    /** Obtém lista de entradas para um material e instalação */
    fun get(insulation: Insulation, code: InstallationCode): List<Entry> =
        data[insulation]?.get(code) ?: emptyList()
}
```

---

### 2. ConductorSizingService.kt
**Serviço de dimensionamento com fatores de correção**

```kotlin
package app.kizen.voltrix.domain

/**
 * Serviço de dimensionamento de condutor aplicando:
 * - Fator de agrupamento (FCA)
 * - Fator de temperatura ambiente
 * Seleciona a menor seção que atende a corrente de projeto.
 */
object ConductorSizingService {
    
    data class Result(
        val section: Double,
        val correctedAmpacity: Double
    )
    
    /**
     * Dimensiona condutor para corrente de projeto.
     * 
     * @param designCurrent Corrente de projeto (A)
     * @param code Tipo de instalação + nº condutores
     * @param insulation Material isolante (PVC/EPR)
     * @param ambientTemp Temperatura ambiente (°C)
     * @param groupingCount Nº circuitos agrupados
     * @return Seção e ampacidade corrigida, ou null se nenhuma atende
     */
    fun size(
        designCurrent: Double,
        code: ConductorTables.InstallationCode,
        insulation: ConductorTables.Insulation,
        ambientTemp: Double,
        groupingCount: Int
    ): Result? {
        val fGrouping = groupingFactor(groupingCount)
        val fTemp = temperatureFactor(ambientTemp)
        val entries = ConductorTables.get(insulation, code)
        
        for (entry in entries) {
            val corrected = entry.ampacity * fGrouping * fTemp
            if (corrected >= designCurrent) {
                return Result(entry.sectionMm2, corrected)
            }
        }
        return null
    }
    
    /** Fator de agrupamento (FCA) conforme quantidade de condutos */
    private fun groupingFactor(n: Int): Double = when {
        n <= 1 -> 1.0
        n == 2 -> 0.80
        n == 3 -> 0.70
        n == 4 -> 0.65
        n == 5 -> 0.60
        else -> 0.50 // fallback para > 5
    }
    
    /** Fator de correção por temperatura ambiente */
    private fun temperatureFactor(t: Double): Double = when {
        t <= 10 -> 1.22
        t <= 15 -> 1.17
        t <= 20 -> 1.12
        t <= 25 -> 1.06
        t <= 30 -> 1.00
        t <= 35 -> 0.94
        t <= 40 -> 0.87
        t <= 45 -> 0.79
        t <= 50 -> 0.71
        t <= 55 -> 0.61
        t <= 60 -> 0.50
        else -> 0.45 // acima da faixa
    }
}
```

---

### 3. BreakerService.kt
**Seleção de disjuntores padronizados (NBR 5410 / IEC 60947-2)**

```kotlin
package app.kizen.voltrix.domain

/**
 * Serviço para seleção de disjuntores padronizados conforme NBR 5410.
 * Aplica margem de segurança e seleciona disjuntor comercial imediatamente superior.
 */
object BreakerService {
    
    /** Disjuntores padronizados em ampères (IEC 60947-2) */
    private val STANDARD_BREAKERS = intArrayOf(
        6, 10, 16, 20, 25, 32, 40, 50, 63, 70, 80, 100,
        125, 150, 160, 175, 200, 225, 250, 300, 350, 400,
        500, 600, 800, 1000, 1250, 1600
    )
    
    /** Margem de segurança padrão (15%) para proteção adequada */
    const val DEFAULT_SAFETY_MARGIN = 1.15
    
    /**
     * Seleciona disjuntor adequado para a corrente calculada.
     * 
     * @param designCurrent Corrente de projeto (A)
     * @param safetyMargin Margem de segurança (ex: 1.15 para 15%)
     * @return Corrente nominal do disjuntor (A), ou 0 se exceder limite
     */
    fun selectBreaker(designCurrent: Double, safetyMargin: Double = DEFAULT_SAFETY_MARGIN): Int {
        val adjustedCurrent = designCurrent * safetyMargin
        
        for (breaker in STANDARD_BREAKERS) {
            if (breaker >= adjustedCurrent) {
                return breaker
            }
        }
        return 0 // Excede máximo disponível
    }
    
    /**
     * Verifica se disjuntor selecionado protege adequadamente o condutor.
     * Regra: In (disjuntor) ≤ Iz (capacidade corrigida do condutor).
     * 
     * @param breakerRating Corrente nominal do disjuntor (A)
     * @param conductorAmpacity Capacidade corrigida do condutor (A)
     * @return true se protegido adequadamente
     */
    fun isAdequateProtection(breakerRating: Int, conductorAmpacity: Double): Boolean =
        breakerRating <= conductorAmpacity
}
```

---

### 4. InputValidator.kt
**Validadores de entrada para campos de UI**

```kotlin
package app.kizen.voltrix.util

import android.widget.EditText

/**
 * Validadores de entrada para campos numéricos com regras específicas
 * de domínio elétrico (tensão, corrente, temperatura, etc.).
 */
object InputValidator {
    
    /**
     * Valida se campo não está vazio e contém número válido.
     */
    fun EditText.isNotEmpty(errorMessage: String): Boolean {
        val text = this.text.toString().trim()
        return if (text.isEmpty()) {
            this.error = errorMessage
            this.requestFocus()
            false
        } else {
            this.error = null
            true
        }
    }
    
    /**
     * Valida número positivo dentro de range permitido.
     */
    fun EditText.isInRange(min: Double, max: Double, errorMessage: String): Boolean {
        if (!isNotEmpty(errorMessage)) return false
        
        return try {
            val value = this.text.toString().trim().toDouble()
            if (value <= min || value > max) {
                this.error = errorMessage
                this.requestFocus()
                false
            } else {
                this.error = null
                true
            }
        } catch (e: NumberFormatException) {
            this.error = "Número inválido"
            this.requestFocus()
            false
        }
    }
    
    /** Valida corrente elétrica (0.1 A a 2000 A) */
    fun EditText.isValidCurrent(): Boolean =
        isInRange(0.0, 2000.0, "Corrente deve estar entre 0.1 e 2000 A")
    
    /** Valida tensão comum (100 V a 1000 V) */
    fun EditText.isValidVoltage(): Boolean =
        isInRange(99.0, 1000.0, "Tensão deve estar entre 100 e 1000 V")
    
    /** Valida temperatura ambiente (-10°C a 70°C) */
    fun EditText.isValidTemperature(): Boolean =
        isInRange(-11.0, 70.0, "Temperatura deve estar entre -10 e 70°C")
    
    /** Valida potência (1 W a 500 kW) */
    fun EditText.isValidPower(): Boolean =
        isInRange(0.0, 500000.0, "Potência deve estar entre 1 W e 500 kW")
    
    /** Valida número de condutores carregados (2 ou 3) */
    fun EditText.isValidLoadedConductors(): Boolean {
        if (!isNotEmpty("Campo obrigatório")) return false
        
        return try {
            val value = this.text.toString().trim().toInt()
            if (value !in 2..3) {
                this.error = "Número de condutores carregados: 2 ou 3"
                this.requestFocus()
                false
            } else {
                this.error = null
                true
            }
        } catch (e: NumberFormatException) {
            this.error = "Número inválido"
            this.requestFocus()
            false
        }
    }
    
    /** Valida número de circuitos agrupados (1 a 20) */
    fun EditText.isValidGrouping(): Boolean {
        if (!isNotEmpty("Campo obrigatório")) return false
        
        return try {
            val value = this.text.toString().trim().toInt()
            if (value !in 1..20) {
                this.error = "Agrupamento: 1 a 20 circuitos"
                this.requestFocus()
                false
            } else {
                this.error = null
                true
            }
        } catch (e: NumberFormatException) {
            this.error = "Número inválido"
            this.requestFocus()
            false
        }
    }
}
```

---

### 5. ElectricalCalculations.kt
**Funções auxiliares de cálculo elétrico**

```kotlin
package app.kizen.voltrix.domain

import kotlin.math.sqrt

/**
 * Utilitários para cálculos elétricos básicos
 */
object ElectricalCalculations {
    
    /** Resistividade do cobre a 20°C (Ω·mm²/m) */
    const val COPPER_RESISTIVITY = 0.017
    
    /**
     * Calcula corrente a partir de potência e tensão.
     * 
     * @param power Potência (W)
     * @param voltage Tensão (V)
     * @param powerFactor Fator de potência (default 0.92)
     * @param isThreePhase Sistema trifásico (default false)
     * @return Corrente (A)
     */
    fun calculateCurrent(
        power: Double,
        voltage: Double,
        powerFactor: Double = 0.92,
        isThreePhase: Boolean = false
    ): Double {
        return if (isThreePhase) {
            power / (sqrt(3.0) * voltage * powerFactor)
        } else {
            power / (voltage * powerFactor)
        }
    }
    
    /**
     * Calcula queda de tensão em linha monofásica.
     * 
     * @param current Corrente (A)
     * @param distance Distância (m)
     * @param section Seção do condutor (mm²)
     * @param resistivity Resistividade do material (Ω·mm²/m)
     * @return Queda de tensão (V)
     */
    fun voltageDropSinglePhase(
        current: Double,
        distance: Double,
        section: Double,
        resistivity: Double = COPPER_RESISTIVITY
    ): Double {
        // ΔV = 2 × ρ × I × L / S
        return 2 * resistivity * current * distance / section
    }
    
    /**
     * Calcula queda de tensão percentual.
     * 
     * @param voltageDrop Queda de tensão (V)
     * @param voltage Tensão nominal (V)
     * @return Queda percentual (%)
     */
    fun voltageDropPercentage(voltageDrop: Double, voltage: Double): Double =
        (voltageDrop / voltage) * 100
    
    /**
     * Calcula consumo energético.
     * 
     * @param power Potência (W)
     * @param timeHours Tempo de uso (h)
     * @return Consumo (kWh)
     */
    fun calculateConsumption(power: Double, timeHours: Double): Double =
        (power * timeHours) / 1000.0
    
    /**
     * Calcula custo de consumo.
     * 
     * @param consumptionKwh Consumo (kWh)
     * @param kwhCost Custo do kWh (R$)
     * @return Custo total (R$)
     */
    fun calculateCost(consumptionKwh: Double, kwhCost: Double): Double =
        consumptionKwh * kwhCost
    
    /**
     * Seções de condutores comerciais (mm²)
     */
    val STANDARD_SECTIONS = doubleArrayOf(
        0.5, 0.75, 1.0, 1.5, 2.5, 4.0, 6.0, 10.0, 16.0, 25.0,
        35.0, 50.0, 70.0, 95.0, 120.0, 150.0, 185.0, 240.0,
        300.0, 400.0, 500.0, 630.0, 800.0, 1000.0
    )
    
    /**
     * Verifica se seção informada está nas padrões comerciais.
     */
    fun isStandardSection(section: Double): Boolean =
        section in STANDARD_SECTIONS
}
```

---

## 📊 Tabelas de Referência

### Disjuntores Padronizados (IEC 60947-2)
```
6, 10, 16, 20, 25, 32, 40, 50, 63, 70, 80, 100, 125, 150, 160, 175,
200, 225, 250, 300, 350, 400, 500, 600, 800, 1000, 1250, 1600 (A)
```

### Fatores de Agrupamento (FCA)
| Nº Condutos | Fator |
|-------------|-------|
| 1           | 1.00  |
| 2           | 0.80  |
| 3           | 0.70  |
| 4           | 0.65  |
| 5           | 0.60  |
| > 5         | 0.50  |

### Fatores de Temperatura
| Temp (°C) | Fator |
|-----------|-------|
| ≤10       | 1.22  |
| ≤15       | 1.17  |
| ≤20       | 1.12  |
| ≤25       | 1.06  |
| ≤30       | 1.00  |
| ≤35       | 0.94  |
| ≤40       | 0.87  |
| ≤45       | 0.79  |
| ≤50       | 0.71  |
| ≤55       | 0.61  |
| ≤60       | 0.50  |
| >60       | 0.45  |

---

## 🏗️ Arquitetura das Activities

### Fluxo de Navegação Original
```
SplashActivity (entrada)
    ↓
MainActivity (menu principal com NavigationDrawer)
    ├─→ CorrenteCircuitoActivity (Cálculo de Corrente)
    ├─→ PotenciaActivity (Cálculo de Potência)
    ├─→ ConsumoActivity (Cálculo de Consumo)
    ├─→ CondutorActivity (Dimensionamento de Condutor)
    ├─→ DisjuntorActivity (Dimensionamento de Disjuntor)
    ├─→ CondutorCompletoctivity (Condutor + Disjuntor)
    ├─→ QuedadeTensaoActivity (Queda de Tensão)
    ├─→ Ajuda (Ajuda Web)
    └─→ PoliticaDePrivacidadeActivity (Política)
```

### Cálculos Implementados

#### 1. **Corrente Elétrica**
- Entrada: Potência (W), Tensão (V), Fator de Potência
- Saída: Corrente (A)
- Fórmula: `I = P / (V × FP)` (monofásico) ou `I = P / (√3 × V × FP)` (trifásico)

#### 2. **Potência**
- Entrada: Corrente (A), Tensão (V), Fator de Potência
- Saída: Potência (W)
- Fórmula: `P = V × I × FP`

#### 3. **Consumo Energético**
- Entrada: Potência (W), Tempo (h), Custo kWh (R$)
- Saída: Consumo (kWh), Custo total (R$)
- Fórmula: `kWh = (P × t) / 1000`

#### 4. **Dimensionamento de Condutor**
- Entrada: Corrente, Tipo Instalação, Material, Temp, Agrupamento
- Saída: Seção (mm²), Ampacidade corrigida (A)
- Aplica: FCA × FTemp × Ampacidade Base

#### 5. **Seleção de Disjuntor**
- Entrada: Corrente de projeto
- Saída: Disjuntor comercial (A)
- Margem: +15% sobre corrente calculada

#### 6. **Condutor + Disjuntor Completo**
- Combina dimensionamento + proteção
- Valida: `In (disjuntor) ≤ Iz (capacidade condutor)`

#### 7. **Queda de Tensão**
- Entrada: Corrente, Distância (m), Seção (mm²), Tensão nominal
- Saída: Queda (V), Percentual (%), Tensão final (V)
- Fórmula: `ΔV = 2 × ρ × I × L / S`
- Limites NBR 5410: 3% (terminais), 5% (total)

---

## 🎯 Estratégia de Migração Recomendada

### Fase 1: Core Business Logic (PRIORIDADE ALTA)
1. ✅ Criar pacote `domain/` no Kotlin
2. ✅ Copiar código Kotlin fornecido:
   - `ConductorTables.kt`
   - `ConductorSizingService.kt`
   - `BreakerService.kt`
   - `ElectricalCalculations.kt`
3. ✅ Criar `util/InputValidator.kt`
4. ✅ Testar em unit tests antes de UI

### Fase 2: UI Modernization
1. Migrar `MainActivity` → Compose/XML moderno
2. Substituir Activities por Fragments ou Composables
3. Implementar ViewModel + StateFlow para reatividade
4. Integrar validadores nas telas de input

### Fase 3: Polish & Features
1. Melhorar UX com Material 3
2. Adicionar histórico de cálculos (Room DB)
3. Exportar relatórios (PDF/compartilhamento)
4. Modo offline completo

---

## 🔄 Exemplo de Uso (Kotlin)

### Dimensionar Condutor
```kotlin
val result = ConductorSizingService.size(
    designCurrent = 45.0,
    code = ConductorTables.InstallationCode.B1_2,
    insulation = ConductorTables.Insulation.PVC,
    ambientTemp = 30.0,
    groupingCount = 2
)

result?.let {
    println("Seção: ${it.section} mm²")
    println("Ampacidade corrigida: ${it.correctedAmpacity} A")
}
```

### Selecionar Disjuntor
```kotlin
val breaker = BreakerService.selectBreaker(
    designCurrent = 45.0,
    safetyMargin = 1.15
)

println("Disjuntor recomendado: $breaker A")

// Validar proteção
val isProtected = BreakerService.isAdequateProtection(
    breakerRating = breaker,
    conductorAmpacity = result?.correctedAmpacity ?: 0.0
)
```

### Calcular Queda de Tensão
```kotlin
val voltageDrop = ElectricalCalculations.voltageDropSinglePhase(
    current = 45.0,
    distance = 50.0,
    section = 10.0
)

val percentage = ElectricalCalculations.voltageDropPercentage(
    voltageDrop = voltageDrop,
    voltage = 220.0
)

println("Queda: $voltageDrop V ($percentage %)")
```

---

## 📝 Notas Finais

### ⚠️ Legado a EVITAR
- `AppUtil.java`: Mapeamentos fixos duplicados → usar `ConductorTables`
- Métodos `VerificaSecaoEcorrente()`: lógica arcaica → substituir por `ConductorSizingService`
- `tableaDisjuntor()`: redundante → `BreakerService.selectBreaker()`

### ✅ Vantagens da Migração Kotlin
- **Type Safety**: Enums + data classes evitam erros
- **Null Safety**: Eliminação de NPE com `?` e `?.let`
- **Concisão**: ~60% menos código boilerplate
- **Testabilidade**: Objects/companion facilitam mocks
- **Coroutines**: Preparação para async/await em futuras features

### 🧪 Testes Recomendados
```kotlin
@Test
fun `dimensionar condutor para 45A em B1_2 PVC`() {
    val result = ConductorSizingService.size(
        45.0, InstallationCode.B1_2, Insulation.PVC, 30.0, 1
    )
    assertEquals(10.0, result?.section)
    assertTrue(result!!.correctedAmpacity >= 45.0)
}

@Test
fun `selecionar disjuntor para 45A deve retornar 63A`() {
    assertEquals(63, BreakerService.selectBreaker(45.0, 1.15))
}
```

---

**Documentação gerada para migração Java → Kotlin do projeto Voltrix (ex-Meu Eletricista)**  
*Todas as fórmulas baseadas em NBR 5410 e IEC 60947-2*
