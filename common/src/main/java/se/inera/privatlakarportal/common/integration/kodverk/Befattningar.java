package se.inera.privatlakarportal.common.integration.kodverk;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pebe on 2015-08-19.
 */
public class Befattningar {

    public static final String BEFATTNING_NAME = "Befattning HSA";
    public static final String BEFATTNING_OID = "1.2.752.129.2.2.1.4";
    public static final String BEFATTNING_VERSION = "3.1";

    static private Map<String, String> befattningar;

    public static String getDisplayName(String code) {
        if (befattningar == null) {
            init();
        }
        return befattningar.get(code);
    }

    private static void init() {
        befattningar = new HashMap<String, String>();
        befattningar.put("201011", "Distriktsläkare/Specialist allmänmedicin");
        befattningar.put("201012", "Skolläkare");
        befattningar.put("201013", "Företagsläkare");
        befattningar.put("202010", "Specialistläkare");
        befattningar.put("203010", "Läkare legitimerad, specialiseringstjänstgöring");
        befattningar.put("203090", "Läkare legitimerad, annan");
    }
}
