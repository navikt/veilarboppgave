package no.nav.veilarboppgave.config;

import no.nav.apiapp.ApiApplication;
import no.nav.apiapp.config.ApiAppConfigurator;
import no.nav.apiapp.security.PepClient;
import no.nav.brukerdialog.security.oidc.SystemUserTokenProvider;
import no.nav.common.auth.Subject;
import no.nav.common.auth.SubjectHandler;
import no.nav.dialogarena.aktor.AktorConfig;
import no.nav.veilarboppgave.rest.api.enheter.EnheterRessurs;
import no.nav.veilarboppgave.rest.api.oppgave.OppgaveRessurs;
import no.nav.veilarboppgave.rest.api.oppgave.OppgavehistorikkRessurs;
import no.nav.veilarboppgave.ws.consumer.gsak.BehandleOppgaveService;
import no.nav.veilarboppgave.ws.consumer.gsak.BehandleOppgaveServiceImpl;
import no.nav.veilarboppgave.ws.consumer.norg.arbeidsfordeling.ArbeidsfordelingService;
import no.nav.veilarboppgave.ws.consumer.norg.arbeidsfordeling.ArbeidsfordelingServiceImpl;
import no.nav.veilarboppgave.ws.consumer.norg.organisasjonenhet.OrganisasjonEnhetService;
import no.nav.veilarboppgave.ws.consumer.norg.organisasjonenhet.OrganisasjonEnhetServiceImpl;
import no.nav.veilarboppgave.ws.consumer.tps.PersonService;
import no.nav.veilarboppgave.ws.consumer.tps.PersonServiceImpl;
import no.nav.sbl.dialogarena.common.abac.pep.Pep;
import no.nav.sbl.dialogarena.common.abac.pep.context.AbacContext;
import no.nav.sbl.featuretoggle.unleash.UnleashService;
import no.nav.sbl.rest.RestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import static no.nav.sbl.dialogarena.common.abac.pep.domain.ResourceType.VeilArbPerson;
import static no.nav.sbl.featuretoggle.unleash.UnleashServiceConfig.resolveFromEnvironment;

@Configuration
@Import({
        EnheterRessurs.class,
        OppgaveRessurs.class,
        OppgavehistorikkRessurs.class,
        PersonServiceHelsesjekk.class,
        BehandleOppgaveServiceHelsesjekk.class,
        OrganisasjonEnhetServiceHelsesjekk.class,
        AbacContext.class,
        DatabaseConfig.class,
        CacheConfig.class,
        AktorConfig.class
})
public class ApplicationConfig implements ApiApplication {

    public static final String AKTOER_V2_ENDPOINTURL = "AKTOER_V2_ENDPOINTURL";
    public static final String VEILARBPERSON_API_URL_PROPERTY = "VEILARBPERSON_API_URL";
    public static final String NORG2_API_URL_PROPERTY = "NORG2_API_URL";

    @Override
    public void configure(ApiAppConfigurator apiAppConfigurator) {
        apiAppConfigurator
                .sts()
                .issoLogin();
    }

    @Bean
    public UnleashService unleashService() {
        return new UnleashService(resolveFromEnvironment());
    }

    @Bean
    public ArbeidsfordelingService arbeidsfordelingService() {
        Client client = RestUtils.createClient();
        return new ArbeidsfordelingServiceImpl(client);
    }

    @Bean
    public PersonService personService() {
        Client client = RestUtils.createClient();
        client.register(new SubjectOidcTokenFilter());
        return new PersonServiceImpl(SoapClientConfiguration.personV3OnBehalfOfUser(), client);
    }

    private static class SubjectOidcTokenFilter implements ClientRequestFilter {
        @Override
        public void filter(ClientRequestContext requestContext) {
            SubjectHandler.getSubject()
                    .map(Subject::getSsoToken)
                    .ifPresent(ssoToken ->
                            requestContext.getHeaders().putSingle("Authorization", "Bearer " + ssoToken.getToken()));
        }
    }

    @Bean
    public BehandleOppgaveService oppgaveService() {
        return new BehandleOppgaveServiceImpl(SoapClientConfiguration.behandleOppgaveV1OnBehalfOfUser());
    }

    @Bean
    public OrganisasjonEnhetService organisasjonEnhetService() {
        return new OrganisasjonEnhetServiceImpl(SoapClientConfiguration.organisasjonenhetOnBehalfOfUser());
    }

    @Bean
    public PepClient pepClient(Pep pep) {
        return new PepClient(pep, "veilarb", VeilArbPerson);
    }
}
