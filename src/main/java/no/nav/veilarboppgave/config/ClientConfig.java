package no.nav.veilarboppgave.config;

import no.nav.common.client.aktoroppslag.CachedAktorOppslagClient;
import no.nav.common.client.aktoroppslag.PdlAktorOppslagClient;
import no.nav.common.client.norg2.CachedNorg2Client;
import no.nav.common.client.norg2.Norg2Client;
import no.nav.common.client.norg2.NorgHttp2Client;
import no.nav.common.client.pdl.PdlClientImpl;
import no.nav.common.sts.SystemUserTokenProvider;
import no.nav.common.utils.EnvironmentUtils;
import no.nav.veilarboppgave.client.norg2.Norg2ArbeidsfordelingClient;
import no.nav.veilarboppgave.client.norg2.Norg2ArbeidsfordelingClientImpl;
import no.nav.veilarboppgave.client.oppgave.OppgaveClient;
import no.nav.veilarboppgave.client.oppgave.OppgaveClientImpl;
import no.nav.veilarboppgave.client.veilarbperson.VeilarbpersonClient;
import no.nav.veilarboppgave.client.veilarbperson.VeilarbpersonClientImpl;
import no.nav.veilarboppgave.service.ContextAwareService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

import static no.nav.common.utils.UrlUtils.createDevInternalIngressUrl;
import static no.nav.common.utils.UrlUtils.createNaisAdeoIngressUrl;
import static no.nav.common.utils.UrlUtils.createNaisPreprodIngressUrl;
import static no.nav.common.utils.UrlUtils.createProdInternalIngressUrl;
import static no.nav.veilarboppgave.config.DownstreamApis.downstreamOppgave;
import static no.nav.veilarboppgave.config.DownstreamApis.downstreamVeilarbperson;

@Configuration
public class ClientConfig {

    @Bean
    public CachedAktorOppslagClient aktorOppslagClient(SystemUserTokenProvider systemUserTokenProvider) {
        String url = isProduction()
                ? createProdInternalIngressUrl("pdl-api")
                : createDevInternalIngressUrl("pdl-api-q1");

        PdlClientImpl pdlClient = new PdlClientImpl(
                url,
                systemUserTokenProvider::getSystemUserToken,
                systemUserTokenProvider::getSystemUserToken);

        return new CachedAktorOppslagClient(new PdlAktorOppslagClient(pdlClient));
    }

    @Bean
    public Norg2Client norg2Client(EnvironmentProperties properties) {
        return new CachedNorg2Client(new NorgHttp2Client(properties.getNorg2Url()));
    }

    @Bean
    public Norg2ArbeidsfordelingClient norg2ArbeidsfordelingClient(EnvironmentProperties properties) {
        return new Norg2ArbeidsfordelingClientImpl(properties.getNorg2Url());
    }

    @Bean
    public OppgaveClient oppgaveClient(ContextAwareService contextAwareService) {
        String safCluster = isProduction() ? "prod-fss"  : "dev-fss";
        Supplier<String> userTokenSupplier = contextAwareService.contextAwareUserTokenSupplier(
                downstreamOppgave(safCluster)
        );
        String url = EnvironmentUtils.isDevelopment().orElse(false)
                ? "https://oppgave.nais.preprod.local"
                : createNaisAdeoIngressUrl("oppgave", false);

        return new OppgaveClientImpl(url, userTokenSupplier);
    }

    @Bean
    public VeilarbpersonClient veilarbpersonClient(ContextAwareService contextAwareService) {
        String safCluster = isProduction() ? "prod-fss"  : "dev-fss";
        Supplier<String> userTokenSupplier = contextAwareService.contextAwareUserTokenSupplier(
                downstreamVeilarbperson(safCluster)
        );
        return new VeilarbpersonClientImpl(naisPreprodOrNaisAdeoIngress("veilarbperson", true), userTokenSupplier);
    }

    private static String naisPreprodOrNaisAdeoIngress(String appName, boolean withAppContextPath) {
        return EnvironmentUtils.isDevelopment().orElse(false)
                ? createNaisPreprodIngressUrl(appName, "q1", withAppContextPath)
                : createNaisAdeoIngressUrl(appName, withAppContextPath);
    }

    private static boolean isProduction() {
        return EnvironmentUtils.isProduction().orElseThrow();
    }
}
