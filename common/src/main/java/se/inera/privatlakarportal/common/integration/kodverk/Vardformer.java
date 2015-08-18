package se.inera.privatlakarportal.common.integration.kodverk;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pebe on 2015-08-19.
 */
public class Vardformer {

    public static final String VARDFORM_NAME = "Vårdform";
    public static final String VARDFORM_OID = "1.2.752.129.2.2.1.13";
    public static final String VARDFORM_VERSION = "3.0";

    static private Map<String, String> vardformer;

    public static String getDisplayName(String code) {
        if (vardformer == null) {
            init();
        }
        return vardformer.get(code);
    }

    private static void init() {
        vardformer = new HashMap<String, String>();
        vardformer.put("01", "Öppenvård (förvald)");
        vardformer.put("02", "Slutenvård");
        vardformer.put("03", "Hemsjukvård");
    }
}
