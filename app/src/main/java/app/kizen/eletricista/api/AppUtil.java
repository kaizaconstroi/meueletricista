package app.kizen.eletricista.api;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilitários da aplicação: formatação, tabelas e regras auxiliares
 * para cálculos e dimensionamentos conforme NBR 5410.
 */
public class AppUtil {
    public static float resistenciaDoCobre = 0.017f;
    public static String PREF_APP = "shared_preferences";


    /** Formata número com duas casas decimais e ponto como separador. */
    public static String FormatarSaida(Double numero) {
        DecimalFormat decimalFormat = new DecimalFormat("###.##");

        decimalFormat.format(numero);
        return decimalFormat.format(numero).replaceAll(",",".");
    }

    /** Verifica se a seção informada existe nas seções padrão. */
    public static boolean sessaoDosCondutores(Double c) {
        ArrayList<Double> t = new ArrayList();
        Double d;
        boolean r = true;
        t.add(0.5); //seção
        t.add(0.75); //seção
        t.add(1.); //seção
        t.add(1.5); //seção
        t.add(2.5); //seção
        t.add(4.); //seção
        t.add(6.); //seção
        t.add(10.); //seção
        t.add(16.); //seção
        t.add(25.); //seção
        t.add(35.); //seção
        t.add(50.); //seção
        t.add(70.); //seção
        t.add(95.0); //seção
        t.add(120.); //seção
        t.add(150.); //seção
        t.add(185.); //seção
        t.add(240.); //seção
        t.add(300.); //seção
        t.add(400.); //seção
        t.add(500.); //seção
        t.add(630.0); //seção
        t.add(800.0); //seção
        t.add(1000.); //seção
        for (int i = 0; i < 24; i++) {
            d = t.get(i);
            if (d.compareTo(c) == 0) {
                r = false;
                break;
            } else
                r = true;
        }

        return r;
    }

    /**
     * Retorna seção e corrente máxima para a corrente informada,
     * considerando tabela (instalação + nº de condutores) e material isolante.
     * Método legado com mapeamentos fixos; mantido por compatibilidade.
     */
    public static ArrayList<Double> VerificaSecaoEcorrente(Double c, String tabela, int material) { // rece a corrente informada, o tipo de instalção , material isolante
        ArrayList<Double> t = new ArrayList();
        if (material==0) { // Verifica o material isolante dos cabos
            if (tabela.equals("A1_2")) { // verifica o tipo de instalação e n° de condutor carregado
                if (c > 0.0 && c <= 7.0) { // verifica qual seção para correnten informada
                    t.add(0.5); //salva a seção de acordo com a tabela da NB5410
                    t.add(10.0); // salva a corrente maxima de acordo coma tabela
                } else if (c <= 9.0) {
                    t.add(0.75); //seção
                    t.add(9.0); //corrente
                } else if (c <= 11.0) {
                    t.add(1.); //seção
                    t.add(11.0); //corrente
                } else if (c <= 14.5) {
                    t.add(1.5); //seção
                    t.add(14.5); //corrente
                } else if (c <= 19.5) {
                    t.add(2.5); //seção
                    t.add(19.5); //corrente
                } else if (c <= 26.0) {
                    t.add(4.); //seção
                    t.add(26.0); //corrente
                } else if (c <= 34.0) {
                    t.add(6.); //seção
                    t.add(34.0); //corrente
                } else if (c <= 46.0) {
                    t.add(10.); //seção
                    t.add(46.0); //corrente
                } else if (c <= 61.0) {
                    t.add(16.); //seção
                    t.add(61.0); //corrente
                } else if (c <= 80.0) {
                    t.add(25.); //seção
                    t.add(80.0); //corrente
                } else if (c <= 99.0) {
                    t.add(35.); //seção
                    t.add(99.0); //corrente
                } else if (c <= 119.0) {
                    t.add(50.); //seção
                    t.add(119.0); //corrente
                } else if (c <= 151.0) {
                    t.add(70.); //seção
                    t.add(151.0); //corrente
                } else if (c <= 182.0) {
                    t.add(95.0); //seção
                    t.add(182.0); //corrente
                } else if (c <= 210.0) {
                    t.add(120.); //seção
                    t.add(210.0); //corrente
                } else if (c <= 240.0) {
                    t.add(150.); //seção
                    t.add(240.0); //corrente
                } else if (c <= 273.0) {
                    t.add(185.); //seção
                    t.add(273.0); //corrente
                } else if (c <= 321.0) {
                    t.add(240.); //seção
                    t.add(321.0); //corrente
                } else if (c <= 367.0) {
                    t.add(300.); //seção
                    t.add(367.0); //corrente
                } else if (c <= 438.0) {
                    t.add(400.); //seção
                    t.add(438.0); //corrente
                } else if (c <= 502.0) {
                    t.add(500.); //seção
                    t.add(502.0); //corrente
                } else if (c <= 578.0) {
                    t.add(630.0); //seção
                    t.add(578.0); //corrente
                } else if (c <= 669.0) {
                    t.add(800.0); //seção
                    t.add(669.0); //corrente
                } else if (c <= 767.0) {
                    t.add(1000.); //seção
                    t.add(767.0); //corrente
                }
            }
            else if (tabela.equals("A1_3")) {
                if (c > 0.0 && c <= 7.0) {
                    t.add(0.5); //seção
                    t.add(7.0); //corrente
                } else if (c <= 9.0) {
                    t.add(0.75); //seção
                    t.add(9.0); //corrente
                } else if (c <= 10.0) {
                    t.add(1.); //seção
                    t.add(10.0); //corrente
                } else if (c <= 13.5) {
                    t.add(1.5); //seção
                    t.add(13.5); //corrente
                } else if (c <= 18.) {
                    t.add(2.5); //seção
                    t.add(18.); //corrente
                } else if (c <= 24.) {
                    t.add(4.); //seção
                    t.add(24.); //corrente
                } else if (c <= 31.0) {
                    t.add(6.); //seção
                    t.add(31.0); //corrente
                } else if (c <= 42.0) {
                    t.add(10.); //seção
                    t.add(42.0); //corrente
                } else if (c <= 56.0) {
                    t.add(16.); //seção
                    t.add(56.0); //corrente
                } else if (c <= 73.0) {
                    t.add(25.); //seção
                    t.add(73.0); //corrente
                } else if (c <= 89.0) {
                    t.add(35.); //seção
                    t.add(89.0); //corrente
                } else if (c <= 108.0) {
                    t.add(50.); //seção
                    t.add(108.0); //corrente
                } else if (c <= 136.0) {
                    t.add(70.); //seção
                    t.add(136.0); //corrente
                } else if (c <= 164.0) {
                    t.add(95.); //seção
                    t.add(164.0); //corrente
                } else if (c <= 188.0) {
                    t.add(120.); //seção
                    t.add(188.0); //corrente
                } else if (c <= 216.0) {
                    t.add(150.); //seção
                    t.add(216.0); //corrente
                } else if (c <= 245.0) {
                    t.add(185.); //seção
                    t.add(245.0); //corrente
                } else if (c <= 286.0) {
                    t.add(240.); //seção
                    t.add(286.0); //corrente
                } else if (c <= 328.0) {
                    t.add(300.); //seção
                    t.add(328.0); //corrente
                } else if (c <= 390.0) {
                    t.add(400.); //seção
                    t.add(390.0); //corrente
                } else if (c <= 447.0) {
                    t.add(500.); //seção
                    t.add(447.0); //corrente
                } else if (c <= 514.0) {
                    t.add(630.); //seção
                    t.add(514.0); //corrente
                } else if (c <= 593.0) {
                    t.add(800.); //seção
                    t.add(593.0); //corrente
                } else if (c <= 679.0) {
                    t.add(1000.); //seção
                    t.add(679.0); //corrente
                }
            } else if (tabela.equals("A2_2")) {
                if (c > 0.0 && c <= 7.0) {
                    t.add(0.5); //seção
                    t.add(7.0); //corrente
                } else if (c <= 9.0) {
                    t.add(0.75); //seção
                    t.add(9.0); //corrente
                } else if (c <= 10.0) {
                    t.add(1.); //seção
                    t.add(10.0); //corrente
                } else if (c <= 14.) {
                    t.add(1.5); //seção
                    t.add(14.); //corrente
                } else if (c <= 18.5) {//aq
                    t.add(2.5); //seção
                    t.add(18.5); //corrente
                } else if (c <= 25.) {
                    t.add(4.); //seção
                    t.add(25.); //corrente
                } else if (c <= 32.0) {
                    t.add(6.); //seção
                    t.add(32.0); //corrente
                } else if (c <= 43.0) {
                    t.add(10.); //seção
                    t.add(43.0); //corrente
                } else if (c <= 57.0) {
                    t.add(16.); //seção
                    t.add(57.0); //corrente
                } else if (c <= 75.0) {
                    t.add(25.); //seção
                    t.add(75.0); //corrente
                } else if (c <= 92.0) {
                    t.add(35.); //seção
                    t.add(92.0); //corrente
                } else if (c <= 110.0) {
                    t.add(50.); //seção
                    t.add(110.0); //corrente
                } else if (c <= 139.0) {
                    t.add(70.); //seção
                    t.add(139.0); //corrente
                } else if (c <= 167.0) {
                    t.add(95.); //seção
                    t.add(167.0); //corrente
                } else if (c <= 192.0) {
                    t.add(120.); //seção
                    t.add(192.0); //corrente
                } else if (c <= 219.0) {
                    t.add(150.); //seção
                    t.add(219.0); //corrente
                } else if (c <= 248.0) {
                    t.add(185.); //seção
                    t.add(248.0); //corrente
                } else if (c <= 291.0) {
                    t.add(240.); //seção
                    t.add(291.0); //corrente
                } else if (c <= 334.0) {
                    t.add(300.); //seção
                    t.add(334.0); //corrente
                } else if (c <= 398.0) {
                    t.add(400.); //seção
                    t.add(398.0); //corrente
                } else if (c <= 456.0) {
                    t.add(500.); //seção
                    t.add(456.0); //corrente
                } else if (c <= 526.0) {
                    t.add(630.); //seção
                    t.add(526.0); //corrente
                } else if (c <= 609.0) {
                    t.add(800.); //seção
                    t.add(609.0); //corrente
                } else if (c <= 698.0) {
                    t.add(1000.); //seção
                    t.add(698.0); //corrente
                }
            } else if (tabela.equals("A2_3")) {
                if (c > 0.0 && c <= 7.0) {
                    t.add(0.5); //seção
                    t.add(7.0); //corrente
                } else if (c <= 9.0) {
                    t.add(0.75); //seção
                    t.add(9.0); //corrente
                } else if (c <= 10.0) {
                    t.add(1.); //seção
                    t.add(10.0); //corrente
                } else if (c <= 13.) {
                    t.add(1.5); //seção
                    t.add(13.); //corrente
                } else if (c <= 17.5) {//aq
                    t.add(2.5); //seção
                    t.add(17.5); //corrente
                } else if (c <= 23.) {
                    t.add(4.); //seção
                    t.add(23.); //corrente
                } else if (c <= 29.0) {
                    t.add(6.); //seção
                    t.add(29.0); //corrente
                } else if (c <= 39.0) {
                    t.add(10.); //seção
                    t.add(39.0); //corrente
                } else if (c <= 52.0) {
                    t.add(16.); //seção
                    t.add(52.0); //corrente
                } else if (c <= 68.0) {
                    t.add(25.); //seção
                    t.add(68.0); //corrente
                } else if (c <= 83.0) {
                    t.add(35.); //seção
                    t.add(83.0); //corrente
                } else if (c <= 99.0) {
                    t.add(50.); //seção
                    t.add(99.0); //corrente
                } else if (c <= 125.0) {
                    t.add(70.); //seção
                    t.add(125.0); //corrente
                } else if (c <= 150.0) {
                    t.add(95.); //seção
                    t.add(150.0); //corrente
                } else if (c <= 172.0) {
                    t.add(120.); //seção
                    t.add(172.0); //corrente
                } else if (c <= 196.0) {
                    t.add(150.); //seção
                    t.add(196.0); //corrente
                } else if (c <= 223.0) {
                    t.add(185.); //seção
                    t.add(223.0); //corrente
                } else if (c <= 261.0) {
                    t.add(240.); //seção
                    t.add(261.0); //corrente
                } else if (c <= 298.0) {
                    t.add(300.); //seção
                    t.add(298.0); //corrente
                } else if (c <= 355.0) {
                    t.add(400.); //seção
                    t.add(355.0); //corrente
                } else if (c <= 406.0) {
                    t.add(500.); //seção
                    t.add(406.0); //corrente
                } else if (c <= 467.0) {
                    t.add(630.); //seção
                    t.add(467.0); //corrente
                } else if (c <= 540.0) {
                    t.add(800.); //seção
                    t.add(540.0); //corrente
                } else if (c <= 618.0) {
                    t.add(1000.); //seção
                    t.add(618.0); //corrente
                }
            } else if (tabela.equals("B1_2")) {
                if (c > 0.0 && c <= 9.0) {
                    t.add(0.5); //seção
                    t.add(9.0); //corrente
                } else if (c <= 11.0) {
                    t.add(0.75); //seção
                    t.add(11.0); //corrente
                } else if (c <= 14.0) {
                    t.add(1.); //seção
                    t.add(14.0); //corrente
                } else if (c <= 17.5) {
                    t.add(1.5); //seção
                    t.add(17.5); //corrente
                } else if (c <= 24.) {//aq
                    t.add(2.5); //seção
                    t.add(24.); //corrente
                } else if (c <= 32.) {
                    t.add(4.); //seção
                    t.add(32.); //corrente
                } else if (c <= 41.0) {
                    t.add(6.); //seção
                    t.add(41.0); //corrente
                } else if (c <= 57.0) {
                    t.add(10.); //seção
                    t.add(57.0); //corrente
                } else if (c <= 76.0) {
                    t.add(16.); //seção
                    t.add(76.0); //corrente
                } else if (c <= 101.0) {
                    t.add(25.); //seção
                    t.add(101.0); //corrente
                } else if (c <= 125.0) {
                    t.add(35.); //seção
                    t.add(125.0); //corrente
                } else if (c <= 151.0) {
                    t.add(50.); //seção
                    t.add(151.0); //corrente
                } else if (c <= 192.0) {
                    t.add(70.); //seção
                    t.add(192.0); //corrente
                } else if (c <= 232.0) {
                    t.add(95.); //seção
                    t.add(232.0); //corrente
                } else if (c <= 269.0) {
                    t.add(120.); //seção
                    t.add(269.0); //corrente
                } else if (c <= 309.0) {
                    t.add(150.); //seção
                    t.add(309.0); //corrente
                } else if (c <= 353.0) {
                    t.add(185.); //seção
                    t.add(353.0); //corrente
                } else if (c <= 415.0) {
                    t.add(240.); //seção
                    t.add(415.0); //corrente
                } else if (c <= 477) {
                    t.add(300.); //seção
                    t.add(477.); //corrente
                } else if (c <= 571.) {
                    t.add(400.); //seção
                    t.add(571.); //corrente
                } else if (c <= 656.) {
                    t.add(500.); //seção
                    t.add(656.); //corrente
                } else if (c <= 758.) {
                    t.add(630.); //seção
                    t.add(758.); //corrente
                } else if (c <= 881.) {
                    t.add(800.); //seção
                    t.add(881.); //corrente
                } else if (c <= 1012.) {
                    t.add(1000.); //seção
                    t.add(1012.); //corrente
                }
            } else if (tabela.equals("B1_3")) {
                if (c > 0.0 && c <= 8.0) {
                    t.add(0.5); //seção
                    t.add(8.0); //corrente
                } else if (c <= 10.0) {
                    t.add(0.75); //seção
                    t.add(10.0); //corrente
                } else if (c <= 12.0) {
                    t.add(1.); //seção
                    t.add(12.0); //corrente
                } else if (c <= 15.5) {
                    t.add(1.5); //seção
                    t.add(15.5); //corrente
                } else if (c <= 21.) {//aq
                    t.add(2.5); //seção
                    t.add(21.); //corrente
                } else if (c <= 28.) {
                    t.add(4.); //seção
                    t.add(28.); //corrente
                } else if (c <= 36.) {
                    t.add(6.); //seção
                    t.add(36.); //corrente
                } else if (c <= 50.) {
                    t.add(10.); //seção
                    t.add(50.); //corrente
                } else if (c <= 68.) {
                    t.add(16.); //seção
                    t.add(68.); //corrente
                } else if (c <= 89.) {
                    t.add(25.); //seção
                    t.add(89.); //corrente
                } else if (c <= 110.) {
                    t.add(35.); //seção
                    t.add(110.); //corrente
                } else if (c <= 134.) {
                    t.add(50.); //seção
                    t.add(134.); //corrente
                } else if (c <= 171.) {
                    t.add(70.); //seção
                    t.add(171.); //corrente
                } else if (c <= 207.) {
                    t.add(95.); //seção
                    t.add(207.); //corrente
                } else if (c <= 239.) {
                    t.add(120.); //seção
                    t.add(239.); //corrente
                } else if (c <= 275.) {
                    t.add(150.); //seção
                    t.add(275.); //corrente
                } else if (c <= 314.) {
                    t.add(185.); //seção
                    t.add(314.); //corrente
                } else if (c <= 370.) {
                    t.add(240.); //seção
                    t.add(370.); //corrente
                } else if (c <= 426.) {
                    t.add(300.); //seção
                    t.add(426.); //corrente
                } else if (c <= 510.) {
                    t.add(400.); //seção
                    t.add(510.); //corrente
                } else if (c <= 587.) {
                    t.add(500.); //seção
                    t.add(587.); //corrente
                } else if (c <= 678.) {
                    t.add(630.); //seção
                    t.add(678.); //corrente
                } else if (c <= 788.) {
                    t.add(800.); //seção
                    t.add(788.); //corrente
                } else if (c <= 906.) {
                    t.add(1000.); //seção
                    t.add(906.); //corrente
                }
            } else if (tabela.equals("B2_2")) {
                if (c > 0.0 && c <= 9.0) {
                    t.add(0.5); //seção
                    t.add(9.0); //corrente
                } else if (c <= 11.0) {
                    t.add(0.75); //seção
                    t.add(11.0); //corrente
                } else if (c <= 13.) {
                    t.add(1.); //seção
                    t.add(13.); //corrente
                } else if (c <= 16.5) {
                    t.add(1.5); //seção
                    t.add(16.5); //corrente
                } else if (c <= 23.) {//aq
                    t.add(2.5); //seção
                    t.add(23.); //corrente
                } else if (c <= 30.) {
                    t.add(4.); //seção
                    t.add(30.); //corrente
                } else if (c <= 38.) {
                    t.add(6.); //seção
                    t.add(38.); //corrente
                } else if (c <= 52.) {
                    t.add(10.); //seção
                    t.add(52.); //corrente
                } else if (c <= 69.) {
                    t.add(16.); //seção
                    t.add(69.); //corrente
                } else if (c <= 90.) {
                    t.add(25.); //seção
                    t.add(90.); //corrente
                } else if (c <= 111.) {
                    t.add(35.); //seção
                    t.add(111.); //corrente
                } else if (c <= 133.) {
                    t.add(50.); //seção
                    t.add(133.); //corrente
                } else if (c <= 168.) {
                    t.add(70.); //seção
                    t.add(168.); //corrente
                } else if (c <= 201.) {
                    t.add(95.); //seção
                    t.add(201.); //corrente
                } else if (c <= 232.) {
                    t.add(120.); //seção
                    t.add(232.); //corrente
                } else if (c <= 265.) {
                    t.add(150.); //seção
                    t.add(265.); //corrente
                } else if (c <= 300.) {
                    t.add(185.); //seção
                    t.add(300.); //corrente
                } else if (c <= 351.) {
                    t.add(240.); //seção
                    t.add(351.); //corrente
                } else if (c <= 401.) {
                    t.add(300.); //seção
                    t.add(401.); //corrente
                } else if (c <= 477.) {
                    t.add(400.); //seção
                    t.add(477.); //corrente
                } else if (c <= 545.) {
                    t.add(500.); //seção
                    t.add(545.); //corrente
                } else if (c <= 626.) {
                    t.add(630.); //seção
                    t.add(626.); //corrente
                } else if (c <= 723.) {
                    t.add(800.); //seção
                    t.add(723.); //corrente
                } else if (c <= 827.) {
                    t.add(1000.); //seção
                    t.add(827.); //corrente
                }
            } else if (tabela.equals("B2_3")) {
                if (c > 0.0 && c <= 8.) {
                    t.add(0.5); //seção
                    t.add(8.); //corrente
                } else if (c <= 10.) {
                    t.add(0.75); //seção
                    t.add(10.); //corrente
                } else if (c <= 12.) {
                    t.add(1.); //seção
                    t.add(12.); //corrente
                } else if (c <= 15.) {
                    t.add(1.5); //seção
                    t.add(15.); //corrente
                } else if (c <= 20.) {//aq
                    t.add(2.5); //seção
                    t.add(20.); //corrente
                } else if (c <= 27.) {
                    t.add(4.); //seção
                    t.add(27.); //corrente
                } else if (c <= 34.) {
                    t.add(6.); //seção
                    t.add(34.); //corrente
                } else if (c <= 46.) {
                    t.add(10.); //seção
                    t.add(46.); //corrente
                } else if (c <= 62.) {
                    t.add(16.); //seção
                    t.add(62.); //corrente
                } else if (c <= 80.) {
                    t.add(25.); //seção
                    t.add(80.); //corrente
                } else if (c <= 99.) {
                    t.add(35.); //seção
                    t.add(99.); //corrente
                } else if (c <= 118.) {
                    t.add(50.); //seção
                    t.add(118.); //corrente
                } else if (c <= 149.) {
                    t.add(70.); //seção
                    t.add(149.); //corrente
                } else if (c <= 179.) {
                    t.add(95.); //seção
                    t.add(179.); //corrente
                } else if (c <= 206.) {
                    t.add(120.); //seção
                    t.add(206.); //corrente
                } else if (c <= 236.) {
                    t.add(150.); //seção
                    t.add(236.); //corrente
                } else if (c <= 268.) {
                    t.add(185.); //seção
                    t.add(268.); //corrente
                } else if (c <= 313.) {
                    t.add(240.); //seção
                    t.add(313.); //corrente
                } else if (c <= 358.) {
                    t.add(300.); //seção
                    t.add(358.); //corrente
                } else if (c <= 425.) {
                    t.add(400.); //seção
                    t.add(425.); //corrente
                } else if (c <= 486.) {
                    t.add(500.); //seção
                    t.add(486.); //corrente
                } else if (c <= 559.) {
                    t.add(630.); //seção
                    t.add(559.); //corrente
                } else if (c <= 645.) {
                    t.add(800.); //seção
                    t.add(645.); //corrente
                } else if (c <= 738.) {
                    t.add(1000.); //seção
                    t.add(738.); //corrente
                }
            }

// Fim dos PVC
        }
        else  if (material==1){ // EPR
            if (tabela.equals("A1_2")) {
                if (c > 0.0 && c <= 10.) {
                    t.add(0.5); //seção
                    t.add(10.); //corrente
                } else if (c <= 12.) {
                    t.add(0.75); //seção
                    t.add(12.); //corrente
                } else if (c <= 15.) {
                    t.add(1.); //seção
                    t.add(15.); //corrente
                } else if (c <= 19.) {
                    t.add(1.5); //seção
                    t.add(19.); //corrente
                } else if (c <= 26.) {//aq
                    t.add(2.5); //seção
                    t.add(26.); //corrente
                } else if (c <= 35.) {
                    t.add(4.); //seção
                    t.add(35.); //corrente
                } else if (c <= 45.) {
                    t.add(6.); //seção
                    t.add(45.); //corrente
                } else if (c <= 61.) {
                    t.add(10.); //seção
                    t.add(61.); //corrente
                } else if (c <= 81.) {
                    t.add(16.); //seção
                    t.add(81.); //corrente
                } else if (c <= 106.) {
                    t.add(25.); //seção
                    t.add(106.); //corrente
                } else if (c <= 131.) {
                    t.add(35.); //seção
                    t.add(131.); //corrente
                } else if (c <= 158.) {
                    t.add(50.); //seção
                    t.add(158.); //corrente
                } else if (c <= 200.) {
                    t.add(70.); //seção
                    t.add(200.); //corrente
                } else if (c <= 241.) {
                    t.add(95.); //seção
                    t.add(241.); //corrente
                } else if (c <= 278.) {
                    t.add(120.); //seção
                    t.add(278.); //corrente
                } else if (c <= 318.) {
                    t.add(150.); //seção
                    t.add(318.); //corrente
                } else if (c <= 362.) {
                    t.add(185.); //seção
                    t.add(362.); //corrente
                } else if (c <= 424.) {
                    t.add(240.); //seção
                    t.add(424.); //corrente
                } else if (c <= 486.) {
                    t.add(300.); //seção
                    t.add(486.); //corrente
                } else if (c <= 579.) {
                    t.add(400.); //seção
                    t.add(579.); //corrente
                } else if (c <= 664.) {
                    t.add(500.); //seção
                    t.add(664.); //corrente
                } else if (c <= 765.) {
                    t.add(630.); //seção
                    t.add(765.); //corrente
                } else if (c <= 885.) {
                    t.add(800.); //seção
                    t.add(885.); //corrente
                } else if (c <= 1014) {
                    t.add(1014.); //seção
                    t.add(738.); //corrente
                }
            }
            else if (tabela.equals("A1_3")) {
                if (c > 0.0 && c <= 9.0) {
                    t.add(0.5); //seção
                    t.add(9.0); //corrente
                } else if (c <= 11) {
                    t.add(0.75); //seção
                    t.add(11.0); //corrente
                } else if (c <= 13.0) {
                    t.add(1.); //seção
                    t.add(13.0); //corrente
                } else if (c <= 17.) {
                    t.add(1.5); //seção
                    t.add(17.); //corrente
                } else if (c <= 23.) {
                    t.add(2.5); //seção
                    t.add(23.);
                } else if (c <= 31.) {
                    t.add(4.);
                    t.add(31.);
                } else if (c <= 40.0) {
                    t.add(6.);
                    t.add(40.0);
                } else if (c <= 54.0) {
                    t.add(10.);
                    t.add(54.0);
                } else if (c <= 73.0) {
                    t.add(16.);
                    t.add(73.0);
                } else if (c <= 95.0) {
                    t.add(25.);
                    t.add(95.0);
                } else if (c <= 117.0) {
                    t.add(35.);
                    t.add(117.0);
                } else if (c <= 141.0) {
                    t.add(50.); //seção
                    t.add(141.0); //corrente
                } else if (c <= 179.0) {
                    t.add(70.);
                    t.add(179.0);
                } else if (c <= 216.0) {
                    t.add(95.);
                    t.add(216.0);
                } else if (c <= 249.0) {
                    t.add(120.);
                    t.add(249.0);
                } else if (c <= 285.0) {
                    t.add(150.);
                    t.add(285.0);
                } else if (c <= 324.0) {
                    t.add(185.);
                    t.add(324.0);
                } else if (c <= 380.0) {
                    t.add(240.);
                    t.add(380.0);
                } else if (c <= 435.0) {
                    t.add(300.); //seção
                    t.add(435.0); //corrente
                } else if (c <= 519.0) {
                    t.add(400.); //seção
                    t.add(519.0); //corrente
                } else if (c <= 595.0) {
                    t.add(500.);
                    t.add(595.0);
                } else if (c <= 685.0) {
                    t.add(630.); //seção
                    t.add(685.0); //corrente
                } else if (c <= 792.0) {
                    t.add(800.); //seção
                    t.add(792.0); //corrente
                } else if (c <= 908.0) {
                    t.add(1000.); //seção
                    t.add(908.0); //corrente
                }
            }
            else if (tabela.equals("B1_2")) {
                if (c > 0.0 && c <= 12.) {
                    t.add(0.5);
                    t.add(12.);
                } else if (c <= 15) {
                    t.add(0.75);
                    t.add(15.0);
                } else if (c <= 18.0) {
                    t.add(1.);
                    t.add(18.0);
                } else if (c <= 23.) {
                    t.add(1.5);
                    t.add(23.);
                } else if (c <= 31.) {
                    t.add(2.5);
                    t.add(31.);
                } else if (c <= 42.) {
                    t.add(4.);
                    t.add(42.);
                } else if (c <= 54.0) {
                    t.add(6.);
                    t.add(54.0);
                } else if (c <= 75.0) {
                    t.add(10.);
                    t.add(54.0);
                } else if (c <= 100.) {
                    t.add(16.);
                    t.add(100.);
                } else if (c <=133.0) {
                    t.add(25.);
                    t.add(133.);
                } else if (c <= 144.0) {
                    t.add(35.);
                    t.add(144.);
                } else if (c <= 175.0) {
                    t.add(50.); //seção
                    t.add(175.0); //corrente
                } else if (c <= 222.0) {
                    t.add(70.);
                    t.add(222.0);
                } else if (c <= 269.0) {
                    t.add(95.);
                    t.add(269.0);
                } else if (c <= 312.0) {
                    t.add(120.);
                    t.add(312.0);
                } else if (c <= 358.0) {
                    t.add(150.);
                    t.add(358.0);
                } else if (c <= 408.0) {
                    t.add(185.);
                    t.add(408.0);
                } else if (c <= 481.0) {
                    t.add(240.);
                    t.add(481.0);
                } else if (c <= 553.0) {
                    t.add(300.); //seção
                    t.add(553.0); //corrente
                } else if (c <= 661.0) {
                    t.add(400.); //seção
                    t.add(661.0); //corrente
                } else if (c <= 760.) {
                    t.add(500.);
                    t.add(760.0);
                } else if (c <= 879.0) {
                    t.add(630.);
                    t.add(879.0);
                } else if (c <= 1020.) {
                    t.add(800.);
                    t.add(1020.);
                } else if (c <= 1173.) {
                    t.add(1000.); //seção
                    t.add(1173.); //corrente
                }
            }
            else if (tabela.equals("B1_3")) {
                if (c > 0.0 && c <= 12.) {
                    t.add(0.5);
                    t.add(12.);
                } else if (c <= 15) {
                    t.add(0.75);
                    t.add(15.0);
                } else if (c <= 18.0) {
                    t.add(1.);
                    t.add(18.0);
                } else if (c <= 23.) {
                    t.add(1.5);
                    t.add(23.);
                } else if (c <= 31.) {
                    t.add(2.5);
                    t.add(31.);
                } else if (c <= 42.) {
                    t.add(4.);
                    t.add(42.);
                } else if (c <= 54.0) {
                    t.add(6.);
                    t.add(54.0);
                } else if (c <= 75.0) {
                    t.add(10.);
                    t.add(54.0);
                } else if (c <= 100.) {
                    t.add(16.);
                    t.add(100.);
                } else if (c <=133.0) {
                    t.add(25.);
                    t.add(133.);
                } else if (c <= 144.0) {
                    t.add(35.);
                    t.add(144.);
                } else if (c <= 175.0) {
                    t.add(50.); //seção
                    t.add(175.0); //corrente
                } else if (c <= 222.0) {
                    t.add(70.);
                    t.add(222.0);
                } else if (c <= 269.0) {
                    t.add(95.);
                    t.add(269.0);
                } else if (c <= 312.0) {
                    t.add(120.);
                    t.add(312.0);
                } else if (c <= 358.0) {
                    t.add(150.);
                    t.add(358.0);
                } else if (c <= 408.0) {
                    t.add(185.);
                    t.add(408.0);
                } else if (c <= 481.0) {
                    t.add(240.);
                    t.add(481.0);
                } else if (c <= 553.0) {
                    t.add(300.); //seção
                    t.add(553.0); //corrente
                } else if (c <= 661.0) {
                    t.add(400.); //seção
                    t.add(661.0); //corrente
                } else if (c <= 760.) {
                    t.add(500.);
                    t.add(760.0);
                } else if (c <= 879.0) {
                    t.add(630.);
                    t.add(879.0);
                } else if (c <= 1020.) {
                    t.add(800.);
                    t.add(1020.);
                } else if (c <= 1173.) {
                    t.add(1000.); //seção
                    t.add(1173.); //corrente
                }
            }
            else if (tabela.equals("C1_2")) {
                if (c > 0.0 && c <= 12.) {
                    t.add(0.5);
                    t.add(12.);
                } else if (c <= 16) {
                    t.add(0.75);
                    t.add(16.0);
                } else if (c <= 19.0) {
                    t.add(1.);
                    t.add(19.0);
                } else if (c <= 24.) {
                    t.add(1.5);
                    t.add(24.);
                } else if (c <= 33.) {
                    t.add(2.5);
                    t.add(33.);
                } else if (c <= 45.) {
                    t.add(4.);
                    t.add(45.);
                } else if (c <= 58.0) {
                    t.add(6.);
                    t.add(58.0);
                } else if (c <= 80.0) {
                    t.add(10.);
                    t.add(80.0);
                } else if (c <= 107.) {
                    t.add(16.);
                    t.add(107.);
                } else if (c <=138.0) {
                    t.add(25.);
                    t.add(138.);
                } else if (c <= 171.0) {
                    t.add(35.);
                    t.add(171.);
                } else if (c <= 209.0) {
                    t.add(50.); //seção
                    t.add(209.0); //corrente
                } else if (c <= 269.0) {
                    t.add(70.);
                    t.add(269.0);
                } else if (c <= 328.0) {
                    t.add(95.);
                    t.add(328.);
                } else if (c <= 382.0) {
                    t.add(120.);
                    t.add(382.0);
                } else if (c <= 441.0) {
                    t.add(150.);
                    t.add(441.0);
                } else if (c <= 506.0) {
                    t.add(185.);
                    t.add(506.0);
                } else if (c <= 599.0) {
                    t.add(240.);
                    t.add(599.0);
                } else if (c <= 693.0) {
                    t.add(300.); //seção
                    t.add(693.0); //corrente
                } else if (c <= 835.0) {
                    t.add(400.); //seção
                    t.add(835.0); //corrente
                } else if (c <= 966.) {
                    t.add(500.);
                    t.add(966.0);
                } else if (c <= 1122.) {
                    t.add(630.);
                    t.add(1122.);
                } else if (c <=1311.) {
                    t.add(800.);
                    t.add(1311.);
                } else if (c <= 1515.) {
                    t.add(1000.);
                    t.add(1515.);
                }
            }
            else if (tabela.equals("C1_3")) {
                if (c > 0.0 && c <= 11.) {
                    t.add(0.5);
                    t.add(11.);
                } else if (c <= 14) {
                    t.add(0.75);
                    t.add(14.0);
                } else if (c <= 17.0) {
                    t.add(1.);
                    t.add(17.0);
                } else if (c <= 22.) {
                    t.add(1.5);
                    t.add(22.);
                } else if (c <= 30.) {
                    t.add(2.5);
                    t.add(30.);
                } else if (c <= 40.) {
                    t.add(4.);
                    t.add(40.);
                } else if (c <= 52.0) {
                    t.add(6.);
                    t.add(52.0);
                } else if (c <= 71.0) {
                    t.add(10.);
                    t.add(71.0);
                } else if (c <= 96.) {
                    t.add(16.);
                    t.add(96.);
                } else if (c <=119.0) {
                    t.add(25.);
                    t.add(119.);
                } else if (c <= 147.0) {
                    t.add(35.);
                    t.add(147.);
                } else if (c <= 179.0) {
                    t.add(50.); //seção
                    t.add(179.0); //corrente
                } else if (c <= 229.0) {
                    t.add(70.);
                    t.add(229.0);
                } else if (c <= 278.0) {
                    t.add(95.);
                    t.add(278.0);
                } else if (c <= 322.0) {
                    t.add(120.);
                    t.add(322.0);
                } else if (c <= 371.0) {
                    t.add(150.);
                    t.add(371.0);
                } else if (c <= 424.0) {
                    t.add(185.);
                    t.add(424.0);
                } else if (c <= 500.0) {
                    t.add(240.);
                    t.add(500.0);
                } else if (c <= 576.0) {
                    t.add(300.); //seção
                    t.add(576.0); //corrente
                } else if (c <= 692.0) {
                    t.add(400.); //seção
                    t.add(692.0); //corrente
                } else if (c <= 797.) {
                    t.add(500.);
                    t.add(797.0);
                } else if (c <= 923.0) {
                    t.add(630.);
                    t.add(923.0);
                } else if (c <= 1074.) {
                    t.add(800.);
                    t.add(1074.);
                } else if (c <= 1237.) {
                    t.add(1000.); //seção
                    t.add(1237.); //corrente
                }
            }
        }

        return t;
    }

    /**
     * Nova API data-driven para dimensionar seção via tabelas estruturadas,
     * aplicando fatores de temperatura e agrupamento.
     */
    public static ArrayList<Double> calcularSecaoViaTabela(Double designCurrent,
                                                           String tabela,
                                                           int material,
                                                           Double temperatura,
                                                           Integer numeroCondutores){
        ArrayList<Double> resultado = new ArrayList<>();
        try {
            app.kizen.eletricista.domain.ConductorTables.Insulation ins = material==0 ?
                    app.kizen.eletricista.domain.ConductorTables.Insulation.PVC : app.kizen.eletricista.domain.ConductorTables.Insulation.EPR;
            app.kizen.eletricista.domain.ConductorTables.InstallationCode code = app.kizen.eletricista.domain.ConductorTables.InstallationCode.valueOf(tabela);
            int agrupamento = numeroCondutores==null?1:numeroCondutores;
            Double temp = temperatura==null?30.0:temperatura;
            java.util.Optional<app.kizen.eletricista.domain.ConductorSizingService.Result> r =
                    app.kizen.eletricista.domain.ConductorSizingService.size(designCurrent, code, ins, temp, agrupamento);
            if(r.isPresent()){
                resultado.add(r.get().section);
                resultado.add(r.get().correctedAmpacity);
            }
        } catch (IllegalArgumentException ignored){
        }
        return resultado;
    }


    /** Tabela do Fator de Correção por Agrupamento (FCA). */
    public static Double tabelaFca(String numeroCondutos) {
        Map<String, Double> map = new HashMap<>();
        //tabela com valores key corrente value secao
        map.put("1", 1.0);
        map.put("2", 0.80);
        map.put("3", 0.70);
        map.put("4", 0.65);
        map.put("5", 0.60);
        return map.get(numeroCondutos);
    }

    /** Seleciona disjuntor padronizado de acordo com a corrente calculada. */
    public static Double tableaDisjuntor(Double corrente) { // Recebe valor da corrente infromada
        //Integer corrente1 = corrente;// variavel para armazenar o valor da correne
        Integer disjuntor = 1; // seta o valor do dijsuntor de acordo com a corrente
        if (corrente <= 1600) {
            if (corrente <= 6) {
                disjuntor = 6;
            } else if (corrente <= 10) {
                disjuntor = 10;
            } else if (corrente <= 16) {
                disjuntor = 16;
            } else if (corrente <= 20) {
                disjuntor = 20;
            } else if (corrente <= 25) {
                disjuntor = 25;
            } else if (corrente <= 32) {
                disjuntor = 32;
            } else if (corrente <= 40) {
                disjuntor = 40;
            } else if (corrente <= 50) {
                disjuntor = 50;
            } else if (corrente <= 63) {
                disjuntor = 63;
            } else if (corrente <= 70) {
                disjuntor = 70;
            } else if (corrente <= 80) {
                disjuntor = 80;
            } else if (corrente <= 100) {
                disjuntor = 100;
            } else if (corrente <= 125) {
                disjuntor = 125;
            } else if (corrente <= 150) {
                disjuntor = 150;
            } else if (corrente <= 160) {
                disjuntor = 160;
            } else if (corrente <= 175) {
                disjuntor = 175;
            } else if (corrente <= 200) {
                disjuntor = 200;
            } else if (corrente <= 225) {
                disjuntor = 225;
            } else if (corrente <= 250) {
                disjuntor = 250;
            } else if (corrente <= 300) {
                disjuntor = 300;
            } else if (corrente <= 350) {
                disjuntor = 350;
            } else if (corrente <= 400) {
                disjuntor = 400;
            } else if (corrente <= 500) {
                disjuntor = 500;
            } else if (corrente <= 600) {
                disjuntor = 600;
            } else if (corrente <= 700) {
                disjuntor = 800;
            } else if (corrente <= 1000) {
                disjuntor = 1000;
            } else if (corrente <= 1250) {
                disjuntor = 1250;
            } else if (corrente <= 1600) {
                disjuntor = 1600;
            }
            Double d = Double.valueOf(disjuntor);
            return d;
        }
        return 0.0;
    }


}

