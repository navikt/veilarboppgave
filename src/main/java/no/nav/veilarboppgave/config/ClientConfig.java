package no.nav.veilarboppgave.config;

import no.nav.common.client.aktorregister.AktorregisterClient;
import no.nav.common.client.aktorregister.AktorregisterHttpClient;
import no.nav.common.client.aktorregister.CachedAktorregisterClient;
import no.nav.common.client.norg2.CachedNorg2Client;
import no.nav.common.client.norg2.Norg2Client;
import no.nav.common.client.norg2.NorgHttp2Client;
import no.nav.common.cxf.StsConfig;
import no.nav.common.sts.SystemUserTokenProvider;
import no.nav.common.utils.EnvironmentUtils;
import no.nav.veilarboppgave.client.gsak.GsakClient;
import no.nav.veilarboppgave.client.gsak.GsakClientImpl;
import no.nav.veilarboppgave.client.norg2.Norg2ArbeidsfordelingClient;
import no.nav.veilarboppgave.client.norg2.Norg2ArbeidsfordelingClientImpl;
import no.nav.veilarboppgave.client.veilarbperson.VeilarbpersonClient;
import no.nav.veilarboppgave.client.veilarbperson.VeilarbpersonClientImpl;
import no.nav.veilarboppgave.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static no.nav.common.utils.UrlUtils.createNaisAdeoIngressUrl;
import static no.nav.common.utils.UrlUtils.createNaisPreprodIngressUrl;
import static no.nav.veilarboppgave.config.ApplicationConfig.APPLICATION_NAME;

@Configuration
public class ClientConfig {

    @Bean
    public AktorregisterClient aktorregisterClient(EnvironmentProperties properties, SystemUserTokenProvider systemUserTokenProvider) {
        AktorregisterClient aktorregisterClient = new AktorregisterHttpClient(
                properties.getAktorregisterUrl(), APPLICATION_NAME, systemUserTokenProvider::getSystemUserToken
        );
        return new CachedAktorregisterClient(aktorregisterClient);
    }

    @Bean
    public Norg2Client norg2Client(EnvironmentProperties properties) {
        return new CachedNorg2Client(new NorgHttp2Client(properties.getNorg2Url()));
    }

    @Bean
    public Norg2ArbeidsfordelingClient norg2ArbeidsfordelingClient(EnvironmentProperties properties, AuthService authService) {
        return new Norg2ArbeidsfordelingClientImpl(properties.getNorg2Url(), authService::getInnloggetBrukerToken);
    }

    @Bean
    public GsakClient gsakClient(EnvironmentProperties properties, StsConfig stsConfig) {
        return new GsakClientImpl(properties.getBehandleOppgaveV1Endpoint(), stsConfig);
    }

    @Bean
    public VeilarbpersonClient veilarbpersonClient(AuthService authService) {
        return new VeilarbpersonClientImpl(naisPreprodOrNaisAdeoIngress("veilarbperson", true), authService::getInnloggetBrukerToken);
    }

    private static String naisPreprodOrNaisAdeoIngress(String appName, boolean withAppContextPath) {
        return  EnvironmentUtils.isDevelopment().orElse(false)
                ? createNaisPreprodIngressUrl(appName, "q1", withAppContextPath)
                : createNaisAdeoIngressUrl(appName, withAppContextPath);
    }

}
