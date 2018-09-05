import no.nav.apiapp.ApiApp;
import no.nav.fo.veilarboppgave.config.ApplicationConfig;

import static no.nav.fo.veilarboppgave.config.ApplicationConfig.AKTOER_V2_ENDPOINTURL;
import static no.nav.dialogarena.aktor.AktorConfig.AKTOER_ENDPOINT_URL;
import static no.nav.sbl.util.EnvironmentUtils.getRequiredProperty;

public class Main {

    public static void main(String[] args) {
        System.setProperty(AKTOER_ENDPOINT_URL, getRequiredProperty(AKTOER_V2_ENDPOINTURL));

        ApiApp.runApp(ApplicationConfig.class, args);
    }
}
