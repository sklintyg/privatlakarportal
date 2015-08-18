package se.inera.privatlakarportal.common.integration.kodverk;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pebe on 2015-08-19.
 */
public class Verksamhetstyper {

    public static final String VERKSAMHETSTYP_NAME = "Verksamhetskod";
    public static final String VERKSAMHETSTYP_OID = "1.2.752.129.2.2.1.3";
    public static final String VERKSAMHETSTYP_VERSION = "4.1";

    static private Map<String, String> verksamhetstyper;

    public static String getDisplayName(String code) {
        if (verksamhetstyper == null) {
            init();
        }
        return verksamhetstyper.get(code);
    }

    private static void init() {
        verksamhetstyper = new HashMap<String, String>();
        verksamhetstyper.put("10", "Barn- och ungdomsverksamhet");
        verksamhetstyper.put("11", "Medicinsk verksamhet");
        verksamhetstyper.put("12", "Laboratorieverksamhet");
        verksamhetstyper.put("13", "Opererande verksamhet");
        verksamhetstyper.put("14", "Övrig medicinsk verksamhet");
        verksamhetstyper.put("15", "Primärvårdsverksamhet");
        verksamhetstyper.put("16", "Psykiatrisk verksamhet");
        verksamhetstyper.put("17", "Radiologisk verksamhet");
        verksamhetstyper.put("18", "Tandvårdsverksamhet");
        verksamhetstyper.put("20", "Övrig medicinsk serviceverksamhet");
    }
}

