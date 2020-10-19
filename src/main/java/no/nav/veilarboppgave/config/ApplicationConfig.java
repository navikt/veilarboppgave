package no.nav.veilarboppgave.config;

import no.nav.common.abac.Pep;
import no.nav.common.abac.VeilarbPep;
import no.nav.common.abac.audit.SpringAuditRequestInfoSupplier;
import no.nav.common.cxf.StsConfig;
import no.nav.common.metrics.InfluxClient;
import no.nav.common.metrics.MetricsClient;
import no.nav.common.metrics.SensuConfig;
import no.nav.common.sts.NaisSystemUserTokenProvider;
import no.nav.common.sts.OpenAmSystemUserTokenProvider;
import no.nav.common.sts.SystemUserTokenProvider;
import no.nav.common.utils.Credentials;
import no.nav.common.utils.NaisUtils;
import no.nav.veilarboppgave.client.norg.arbeidsfordeling.ArbeidsfordelingService;
import no.nav.veilarboppgave.client.norg.arbeidsfordeling.ArbeidsfordelingServiceImpl;
import no.nav.veilarboppgave.client.norg.organisasjonenhet.OrganisasjonEnhetService;
import no.nav.veilarboppgave.client.norg.organisasjonenhet.OrganisasjonEnhetServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import static no.nav.common.utils.NaisUtils.getCredentials;

@EnableConfigurationProperties({EnvironmentProperties.class})
@Configuration
public class ApplicationConfig {

    public static final String APPLICATION_NAME = "veilarboppgave";
    public static final String AKTOER_V2_ENDPOINTURL = "AKTOER_V2_ENDPOINTURL";
    public static final String VEILARBPERSON_API_URL_PROPERTY = "VEILARBPERSON_API_URL";
    public static final String NORG2_API_URL_PROPERTY = "NORG2_API_URL";

    @Bean
    public Credentials serviceUserCredentials() {
        return getCredentials("service_user");
    }

    @Bean
    public MetricsClient metricsClient() {
        return new InfluxClient(SensuConfig.defaultConfig());
    }

    // TODO: Brukes kun av feedene, skal snart fjernes.
    @Bean
    public OpenAmSystemUserTokenProvider openAmSystemUserTokenProvider(EnvironmentProperties properties, Credentials serviceUserCredentials) {
        return new OpenAmSystemUserTokenProvider(
                properties.getOpenAmDiscoveryUrl(), properties.getOpenAmRedirectUrl(),
                new Credentials(properties.getOpenAmIssoRpUsername(), properties.getOpenAmIssoRpPassword()), serviceUserCredentials
        );
    }

    @Bean
    public SystemUserTokenProvider systemUserTokenProvider(EnvironmentProperties properties, Credentials serviceUserCredentials) {
        return new NaisSystemUserTokenProvider(properties.getNaisStsDiscoveryUrl(), serviceUserCredentials.username, serviceUserCredentials.password);
    }

    @Bean
    public static StsConfig stsConfig(EnvironmentProperties properties, Credentials serviceUserCredentials) {
        return StsConfig.builder()
                .url(properties.getSoapStsUrl())
                .username(serviceUserCredentials.username)
                .password(serviceUserCredentials.password)
                .build();
    }

    @Bean
    public Pep veilarbPep(EnvironmentProperties properties) {
        Credentials serviceUserCredentials = NaisUtils.getCredentials("service_user");
        return new VeilarbPep(
                properties.getAbacUrl(), serviceUserCredentials.username,
                serviceUserCredentials.password, new SpringAuditRequestInfoSupplier()
        );
    }

}
