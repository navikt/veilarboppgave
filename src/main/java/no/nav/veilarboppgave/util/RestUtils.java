package no.nav.veilarboppgave.util;

import java.util.function.Supplier;

public class RestUtils {

    public static String bearerTokenFromSupplier(Supplier<String> tokenSupplier) {
        return "Bearer " + tokenSupplier.get();
    }

}
