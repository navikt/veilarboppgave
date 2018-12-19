import static no.nav.dialogarena.config.fasit.FasitUtils.getDefaultEnvironment;
import static no.nav.dialogarena.config.fasit.FasitUtils.Zone.FSS;
import static no.nav.fo.veilarboppgave.config.ApplicationConfig.AKTOER_V2_ENDPOINTURL;
import static no.nav.sbl.util.EnvironmentUtils.resolveSrvUserPropertyName;
import static no.nav.sbl.util.EnvironmentUtils.resolverSrvPasswordPropertyName;
import static no.nav.sbl.util.EnvironmentUtils.setProperty;
import static no.nav.sbl.util.EnvironmentUtils.Type.PUBLIC;
import static no.nav.sbl.util.EnvironmentUtils.Type.SECRET;

import no.nav.brukerdialog.security.Constants;
import no.nav.brukerdialog.tools.SecurityConstants;
import no.nav.dialogarena.config.fasit.FasitUtils;
import no.nav.dialogarena.config.fasit.ServiceUser;
import no.nav.dialogarena.config.fasit.dto.RestService;
import no.nav.fo.veilarboppgave.config.DatabaseConfig;
import no.nav.sbl.dialogarena.common.abac.pep.service.AbacServiceConfig;
import no.nav.sbl.dialogarena.common.cxf.StsSecurityConstants;
import no.nav.testconfig.ApiAppTest;

public class MainTest {

    private static final String APPLICATION_NAME = "veilarboppgave";
    private static final String PORT = "9591";

    private static final String NORG2_ORGANISASJONENHET_V2_URL = "VIRKSOMHET_ORGANISASJONENHET_V2_ENDPOINTURL";
    private static final String NORG_VIRKSOMHET_ENHET_URL = "VIRKSOMHET_ENHET_V1_ENDPOINTURL";

    public static void main(String[] args) {
        ApiAppTest.setupTestContext(ApiAppTest.Config.builder()
                .applicationName(APPLICATION_NAME)
                .build()
        );

        String securityTokenService = FasitUtils.getBaseUrl("securityTokenService", FSS);
        ServiceUser srvveilarboppgave = FasitUtils.getServiceUser("srvveilarboppgave", APPLICATION_NAME);

        setProperty(resolveSrvUserPropertyName(), srvveilarboppgave.username, PUBLIC);
        setProperty(resolverSrvPasswordPropertyName(), srvveilarboppgave.password, PUBLIC);

        setProperty(StsSecurityConstants.STS_URL_KEY, securityTokenService, PUBLIC);
        setProperty(StsSecurityConstants.SYSTEMUSER_USERNAME, srvveilarboppgave.getUsername(), PUBLIC);
        setProperty(StsSecurityConstants.SYSTEMUSER_PASSWORD, srvveilarboppgave.getPassword(), SECRET);

        setProperty(NORG2_ORGANISASJONENHET_V2_URL, FasitUtils.getWebServiceEndpoint("virksomhet:OrganisasjonEnhet_v2").url, PUBLIC);
        setProperty(NORG_VIRKSOMHET_ENHET_URL, FasitUtils.getWebServiceEndpoint("virksomhet:Enhet_v1").url, PUBLIC);
        setProperty(AKTOER_V2_ENDPOINTURL, FasitUtils.getWebServiceEndpoint("Aktoer_v2").url, PUBLIC);
        setProperty("VIRKSOMHET_ARBEIDSFORDELING_V1_ENDPOINTURL", FasitUtils.getWebServiceEndpoint("virksomhet:Arbeidsfordeling_v1").url, PUBLIC);
        setProperty("VIRKSOMHET_PERSON_V3_ENDPOINTURL", FasitUtils.getWebServiceEndpoint("virksomhet:Person_v3").url, PUBLIC);
        setProperty("VIRKSOMHET_BEHANDLEOPPGAVE_V1_ENDPOINTURL", FasitUtils.getWebServiceEndpoint("virksomhet:BehandleOppgave_v1").url, PUBLIC);
                
        
        String issoHost = FasitUtils.getBaseUrl("isso-host");
        String issoJWS = FasitUtils.getBaseUrl("isso-jwks");
        String issoISSUER = FasitUtils.getBaseUrl("isso-issuer");
        String issoIsAlive = FasitUtils.getBaseUrl("isso.isalive", FSS);
        
        ServiceUser isso_rp_user = FasitUtils.getServiceUser("isso-rp-user", APPLICATION_NAME);
        setProperty(Constants.ISSO_HOST_URL_PROPERTY_NAME, issoHost, PUBLIC);
        setProperty(Constants.ISSO_RP_USER_USERNAME_PROPERTY_NAME, isso_rp_user.getUsername(), PUBLIC);
        setProperty(Constants.ISSO_RP_USER_PASSWORD_PROPERTY_NAME, isso_rp_user.getPassword(), SECRET);
        setProperty(Constants.ISSO_JWKS_URL_PROPERTY_NAME, issoJWS, PUBLIC);
        setProperty(Constants.ISSO_ISSUER_URL_PROPERTY_NAME, issoISSUER, PUBLIC);
        setProperty(Constants.ISSO_ISALIVE_URL_PROPERTY_NAME, issoIsAlive, PUBLIC);
        setProperty(Constants.ISSO_ISALIVE_URL_PROPERTY_NAME, issoIsAlive, PUBLIC);
        setProperty(SecurityConstants.SYSTEMUSER_USERNAME, srvveilarboppgave.getUsername(), PUBLIC);
        setProperty(SecurityConstants.SYSTEMUSER_PASSWORD, srvveilarboppgave.getPassword(), SECRET);

        RestService loginUrl = FasitUtils.getRestService("veilarblogin.redirect-url", getDefaultEnvironment());
        setProperty(Constants.OIDC_REDIRECT_URL_PROPERTY_NAME, loginUrl.getUrl(), PUBLIC);

        RestService abacEndpoint = FasitUtils.getRestService("abac.pdp.endpoint", getDefaultEnvironment());
        setProperty(AbacServiceConfig.ABAC_ENDPOINT_URL_PROPERTY_NAME, abacEndpoint.getUrl(), PUBLIC);

        setProperty(DatabaseConfig.VEILARBOPPGAVEDB_URL, "jdbc:h2:mem:veilarboppgave;DB_CLOSE_DELAY=-1;MODE=Oracle", PUBLIC);
        setProperty(DatabaseConfig.VEILARBOPPGAVEDB_USERNAME, "sa", PUBLIC);
        setProperty(DatabaseConfig.VEILARBOPPGAVEDB_PASSWORD, "", PUBLIC);
        
        Main.main(new String[]{PORT});
    }
}
