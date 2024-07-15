package no.nav.veilarboppgave.config;

import no.nav.common.client.aktoroppslag.AktorOppslagClient;
import no.nav.common.client.aktoroppslag.CachedAktorOppslagClient;
import no.nav.common.client.aktoroppslag.PdlAktorOppslagClient;
import no.nav.common.client.norg2.CachedNorg2Client;
import no.nav.common.client.norg2.Norg2Client;
import no.nav.common.client.norg2.NorgHttp2Client;
import no.nav.common.token_client.client.MachineToMachineTokenClient;
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

import static no.nav.veilarboppgave.config.DownstreamApis.downstreamOppgave;
import static no.nav.veilarboppgave.config.DownstreamApis.downstreamVeilarbperson;

@Configuration
public class ClientConfig {

    @Bean
    public CachedAktorOppslagClient aktorOppslagClient(MachineToMachineTokenClient tokenClient) {
        String url = EnvironmentUtils.isDevelopment().orElse(false)
                ? "https://pdl-api.dev-fss-pub.nais.io"
                : "https://pdl-api.prod-fss-pub.nais.io";
        String tokenScope = String.format("api://%s.pdl.pdl-api/.default",
                isProduction() ? "prod-fss" : "dev-fss");

        AktorOppslagClient aktorOppslagClient = new PdlAktorOppslagClient(
                url,
                () -> tokenClient.createMachineToMachineToken(tokenScope)
        );

        return new CachedAktorOppslagClient(aktorOppslagClient);
    }

    @Bean
    public Norg2Client norg2Client(EnvironmentProperties properties) {
        String url = EnvironmentUtils.isDevelopment().orElse(false)
                ? "https://norg2.dev-fss-pub.nais.io"
                : "https://norg2.prod-fss-pub.nais.io";
        return new CachedNorg2Client(new NorgHttp2Client(url));
    }

    @Bean
    public Norg2ArbeidsfordelingClient norg2ArbeidsfordelingClient(EnvironmentProperties properties) {
        String url = EnvironmentUtils.isDevelopment().orElse(false)
                ? "https://norg2.dev-fss-pub.nais.io"
                : "https://norg2.prod-fss-pub.nais.io";
        return new Norg2ArbeidsfordelingClientImpl(url);
    }

    @Bean
    public OppgaveClient oppgaveClient(ContextAwareService contextAwareService) {
        String safCluster = isProduction() ? "prod-fss"  : "dev-fss";
        Supplier<String> userTokenSupplier = contextAwareService.contextAwareUserTokenSupplier(
                downstreamOppgave(safCluster)
        );
        String url = EnvironmentUtils.isDevelopment().orElse(false)
                ? "https://oppgave-q1.dev-fss-pub.nais.io"
                : "https://oppgave.prod-fss-pub.nais.io";

        return new OppgaveClientImpl(url, userTokenSupplier);
    }

    @Bean
    public VeilarbpersonClient veilarbpersonClient(ContextAwareService contextAwareService) {
        String safCluster = isProduction() ? "prod-fss"  : "dev-fss";
        Supplier<String> userTokenSupplier = contextAwareService.contextAwareUserTokenSupplier(
                downstreamVeilarbperson(safCluster)
        );
        String url = EnvironmentUtils.isDevelopment().orElse(false)
                ? "https://veilarbperson.dev-fss-pub.nais.io/veilarbperson"
                : "https://veilarbperson.prod-fss-pub.nais.io/veilarbperson";

        return new VeilarbpersonClientImpl(url, userTokenSupplier);
    }

    private static boolean isProduction() {
        return EnvironmentUtils.isProduction().orElseThrow();
    }
}
