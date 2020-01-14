import no.nav.apiapp.ApiApp;
import no.nav.brukerdialog.tools.SecurityConstants;
import no.nav.common.utils.NaisUtils;
import no.nav.fo.veilarboppgave.config.ApplicationConfig;
import no.nav.sbl.dialogarena.common.abac.pep.CredentialConstants;
import no.nav.sbl.dialogarena.common.cxf.StsSecurityConstants;

import static java.lang.System.setProperty;
import static no.nav.common.utils.NaisUtils.getCredentials;
import static no.nav.fo.veilarboppgave.config.ApplicationConfig.AKTOER_V2_ENDPOINTURL;
import static no.nav.dialogarena.aktor.AktorConfig.AKTOER_ENDPOINT_URL;
import static no.nav.fo.veilarboppgave.config.DatabaseConfig.VEILARBOPPGAVEDB_PASSWORD;
import static no.nav.fo.veilarboppgave.config.DatabaseConfig.VEILARBOPPGAVEDB_USERNAME;
import static no.nav.sbl.featuretoggle.unleash.UnleashServiceConfig.UNLEASH_API_URL_PROPERTY_NAME;
import static no.nav.sbl.util.EnvironmentUtils.getRequiredProperty;

public class Main {

    public static void main(String[] args) {

        NaisUtils.Credentials serviceUser = NaisUtils.getCredentials("service_user");

//        //ABAC
//        System.setProperty(CredentialConstants.SYSTEMUSER_USERNAME, serviceUser.username);
//        System.setProperty(CredentialConstants.SYSTEMUSER_PASSWORD, serviceUser.password);
//
//        //CXF
//        System.setProperty(StsSecurityConstants.SYSTEMUSER_USERNAME, serviceUser.username);
//        System.setProperty(StsSecurityConstants.SYSTEMUSER_PASSWORD, serviceUser.password);
//
//        //OIDC
//        System.setProperty(SecurityConstants.SYSTEMUSER_USERNAME, serviceUser.username);
//        System.setProperty(SecurityConstants.SYSTEMUSER_PASSWORD, serviceUser.password);
//
//        NaisUtils.Credentials oracleCreds = getCredentials("oracle_creds");
//        System.setProperty(VEILARBOPPGAVEDB_USERNAME, oracleCreds.username);
//        System.setProperty(VEILARBOPPGAVEDB_PASSWORD, oracleCreds.password);

        System.setProperty(AKTOER_ENDPOINT_URL, getRequiredProperty(AKTOER_V2_ENDPOINTURL));
        setProperty(UNLEASH_API_URL_PROPERTY_NAME, "https://unleash.nais.adeo.no/api/");

        ApiApp.runApp(ApplicationConfig.class, args);
    }
}
