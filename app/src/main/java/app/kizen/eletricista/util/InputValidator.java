package app.kizen.eletricista.util;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * Validadores de entrada para campos numéricos com regras específicas
 * de domínio elétrico (tensão, corrente, temperatura, etc.).
 */
public class InputValidator {
    
    /**
     * Valida se o campo não está vazio e contém número válido.
     * 
     * @param editText Campo a validar
     * @param errorMessage Mensagem de erro a exibir
     * @return true se válido
     */
    public static boolean isNotEmpty(EditText editText, String errorMessage) {
        String text = editText.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            editText.setError(errorMessage);
            editText.requestFocus();
            return false;
        }
        editText.setError(null);
        return true;
    }

    /**
     * Valida número positivo dentro de range permitido.
     * 
     * @param editText Campo a validar
     * @param min Valor mínimo permitido (exclusivo)
     * @param max Valor máximo permitido (inclusivo)
     * @param errorMessage Mensagem de erro
     * @return true se válido
     */
    public static boolean isInRange(EditText editText, double min, double max, String errorMessage) {
        if (!isNotEmpty(editText, errorMessage)) return false;
        
        try {
            double value = Double.parseDouble(editText.getText().toString().trim());
            if (value <= min || value > max) {
                editText.setError(errorMessage);
                editText.requestFocus();
                return false;
            }
            editText.setError(null);
            return true;
        } catch (NumberFormatException e) {
            editText.setError("Número inválido");
            editText.requestFocus();
            return false;
        }
    }

    /**
     * Valida corrente elétrica (0.1 A a 2000 A).
     * 
     * @param editText Campo de corrente
     * @return true se válido
     */
    public static boolean isValidCurrent(EditText editText) {
        return isInRange(editText, 0.0, 2000.0, "Corrente deve estar entre 0.1 e 2000 A");
    }

    /**
     * Valida tensão comum (100 V a 1000 V).
     * 
     * @param editText Campo de tensão
     * @return true se válido
     */
    public static boolean isValidVoltage(EditText editText) {
        return isInRange(editText, 99.0, 1000.0, "Tensão deve estar entre 100 e 1000 V");
    }

    /**
     * Valida temperatura ambiente (-10°C a 70°C).
     * 
     * @param editText Campo de temperatura
     * @return true se válido
     */
    public static boolean isValidTemperature(EditText editText) {
        return isInRange(editText, -11.0, 70.0, "Temperatura deve estar entre -10 e 70°C");
    }

    /**
     * Valida potência (1 W a 500 kW).
     * 
     * @param editText Campo de potência
     * @return true se válido
     */
    public static boolean isValidPower(EditText editText) {
        return isInRange(editText, 0.0, 500000.0, "Potência deve estar entre 1 W e 500 kW");
    }

    /**
     * Valida número de condutores carregados (2 ou 3).
     * 
     * @param editText Campo de número de condutores
     * @return true se válido
     */
    public static boolean isValidLoadedConductors(EditText editText) {
        if (!isNotEmpty(editText, "Campo obrigatório")) return false;
        
        try {
            int value = Integer.parseInt(editText.getText().toString().trim());
            if (value < 2 || value > 3) {
                editText.setError("Número de condutores carregados: 2 ou 3");
                editText.requestFocus();
                return false;
            }
            editText.setError(null);
            return true;
        } catch (NumberFormatException e) {
            editText.setError("Número inválido");
            editText.requestFocus();
            return false;
        }
    }

    /**
     * Valida número de circuitos agrupados (1 a 20).
     * 
     * @param editText Campo de agrupamento
     * @return true se válido
     */
    public static boolean isValidGrouping(EditText editText) {
        if (!isNotEmpty(editText, "Campo obrigatório")) return false;
        
        try {
            int value = Integer.parseInt(editText.getText().toString().trim());
            if (value < 1 || value > 20) {
                editText.setError("Agrupamento: 1 a 20 circuitos");
                editText.requestFocus();
                return false;
            }
            editText.setError(null);
            return true;
        } catch (NumberFormatException e) {
            editText.setError("Número inválido");
            editText.requestFocus();
            return false;
        }
    }
}
