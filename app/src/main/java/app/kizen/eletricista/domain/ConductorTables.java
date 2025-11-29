package app.kizen.eletricista.domain;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Tabelas de ampacidade por tipo de instalação e material isolante,
 * conforme valores utilizados no projeto (base NBR 5410).
 *
 * Estrutura data-driven para substituir mapeamentos fixos no AppUtil,
 * facilitando manutenção e testes.
 */
public final class ConductorTables {
        /**
         * Código do tipo de instalação + número de condutores carregados.
         * Ex.: A1_2 (instalação A1 com 2 condutores carregados).
         */
        public enum InstallationCode {
                A1_2, A1_3, A2_2, A2_3, B1_2, B1_3, B2_2, B2_3,
                C1_2, C1_3 // presentes na seção EPR do código original
        }
        /** Material isolante do condutor. */
        public enum Insulation { PVC, EPR }

        /**
         * Entrada da tabela contendo seção (mm²) e ampacidade (A) base
         * antes de fatores de correção.
         */
        public static final class Entry {
                public final double sectionMm2;
                public final double ampacity;
                public Entry(double s, double a){ this.sectionMm2 = s; this.ampacity = a; }
        }

        /** Estrutura de dados principal: por isolação → por instalação → lista de entradas. */
        private static final Map<Insulation, Map<InstallationCode, Entry[]>> DATA = new LinkedHashMap<>();

        static {
        Map<InstallationCode, Entry[]> pvc = new LinkedHashMap<>();
        pvc.put(InstallationCode.A1_2, entries(new double[]{0.5,0.75,1,1.5,2.5,4,6,10,16,25,35,50,70,95,120,150,185,240,300,400,500,630,800,1000},
                new double[]{10,9,11,14.5,19.5,26,34,46,61,80,99,119,151,182,210,240,273,321,367,438,502,578,669,767}));
        pvc.put(InstallationCode.A1_3, entries(new double[]{0.5,0.75,1,1.5,2.5,4,6,10,16,25,35,50,70,95,120,150,185,240,300,400,500,630,800,1000},
                new double[]{7,9,10,13.5,18,24,31,42,56,73,89,108,136,164,188,216,245,286,328,390,447,514,593,679}));
        pvc.put(InstallationCode.A2_2, entries(new double[]{0.5,0.75,1,1.5,2.5,4,6,10,16,25,35,50,70,95,120,150,185,240,300,400,500,630,800,1000},
                new double[]{7,9,10,14,18.5,25,32,43,57,75,92,110,139,167,192,219,248,291,334,398,456,526,609,698}));
        pvc.put(InstallationCode.A2_3, entries(new double[]{0.5,0.75,1,1.5,2.5,4,6,10,16,25,35,50,70,95,120,150,185,240,300,400,500,630,800,1000},
                new double[]{7,9,10,13,17.5,23,29,39,52,68,83,99,125,150,172,196,223,261,298,355,406,467,540,618}));
        pvc.put(InstallationCode.B1_2, entries(new double[]{0.5,0.75,1,1.5,2.5,4,6,10,16,25,35,50,70,95,120,150,185,240,300,400,500,630,800,1000},
                new double[]{9,11,14,17.5,24,32,41,57,76,101,125,151,192,232,269,309,353,415,477,571,656,758,881,1012}));
        pvc.put(InstallationCode.B1_3, entries(new double[]{0.5,0.75,1,1.5,2.5,4,6,10,16,25,35,50,70,95,120,150,185,240,300,400,500,630,800,1000},
                new double[]{8,10,12,15.5,21,28,36,50,68,89,110,134,171,207,239,275,314,370,426,510,587,678,788,906}));
        pvc.put(InstallationCode.B2_2, entries(new double[]{0.5,0.75,1,1.5,2.5,4,6,10,16,25,35,50,70,95,120,150,185,240,300,400,500,630,800,1000},
                new double[]{9,11,13,16.5,23,30,38,52,69,90,111,133,168,201,232,265,300,351,401,477,545,626,723,827}));
        pvc.put(InstallationCode.B2_3, entries(new double[]{0.5,0.75,1,1.5,2.5,4,6,10,16,25,35,50,70,95,120,150,185,240,300,400,500,630,800,1000},
                new double[]{8,10,12,15,20,27,34,46,62,80,99,118,149,179,206,236,268,313,358,425,486,559,645,738}));
        Map<InstallationCode, Entry[]> epr = new LinkedHashMap<>();
        epr.put(InstallationCode.A1_2, entries(new double[]{0.5,0.75,1,1.5,2.5,4,6,10,16,25,35,50,70,95,120,150,185,240,300,400,500,630,800,1000},
                new double[]{10,12,15,19,26,35,45,61,81,106,131,158,200,241,278,318,362,424,486,579,664,765,885,1014}));
        epr.put(InstallationCode.A1_3, entries(new double[]{0.5,0.75,1,1.5,2.5,4,6,10,16,25,35,50,70,95,120,150,185,240,300,400,500,630,800,1000},
                new double[]{9,11,13,17,23,31,40,54,73,95,117,141,179,216,249,285,324,380,435,519,595,685,792,908}));
        epr.put(InstallationCode.B1_2, entries(new double[]{0.5,0.75,1,1.5,2.5,4,6,10,16,25,35,50,70,95,120,150,185,240,300,400,500,630,800,1000},
                new double[]{12,15,18,23,31,42,54,75,100,133,144,175,222,269,312,358,408,481,553,661,760,879,1020,1173}));
        epr.put(InstallationCode.B1_3, epr.get(InstallationCode.B1_2));
        epr.put(InstallationCode.C1_2, entries(new double[]{0.5,0.75,1,1.5,2.5,4,6,10,16,25,35,50,70,95,120,150,185,240,300,400,500,630,800,1000},
                new double[]{12,16,19,24,33,45,58,80,107,138,171,209,269,328,382,441,506,599,693,835,966,1122,1311,1515}));
        epr.put(InstallationCode.C1_3, entries(new double[]{0.5,0.75,1,1.5,2.5,4,6,10,16,25,35,50,70,95,120,150,185,240,300,400,500,630,800,1000},
                new double[]{11,14,17,22,30,40,52,71,96,119,147,179,229,278,322,371,424,500,576,692,797,923,1074,1237}));
        DATA.put(Insulation.PVC, pvc);
        DATA.put(Insulation.EPR, epr);
    }

        /** Monta vetor de entradas a partir de arrays de seções e ampacidades. */
        private static Entry[] entries(double[] sections, double[] ampacities){
        Entry[] arr = new Entry[sections.length];
        for(int i=0;i<sections.length;i++){ arr[i]= new Entry(sections[i], ampacities[i]); }
        return arr;
    }

        /** Obtém lista de entradas para um material isolante e tipo de instalação. */
        public static Entry[] get(Insulation insulation, InstallationCode code){
        Map<InstallationCode, Entry[]> byCode = DATA.get(insulation);
        return byCode==null? new Entry[0] : byCode.getOrDefault(code, new Entry[0]);
    }
}
