package se.inera.privatlakarportal.common.integration.kodverk;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pebe on 2015-08-19.
 */
public class Vardformer {

    public static final String VARDFORM_NAME = "Vårdform";
    public static final String VARDFORM_OID = "1.2.752.129.2.2.1.13";
    public static final String VARDFORM_VERSION = "3.0";

    private static final Map<String, String> vardformer;
    static {
        Map<String, String> map = new HashMap<>();
        map.put("01", "Öppenvård (förvald)");
        map.put("02", "Slutenvård");
        map.put("03", "Hemsjukvård");
        vardformer = Collections.unmodifiableMap(map);
    }

    public static String getDisplayName(String code) {
        return vardformer.get(code);
    }

    public static Map<String, String> getVardformer() {
        return vardformer;
    }
}
