package no.nav.veilarboppgave.config;

import no.nav.common.auth.context.AuthContextHolder;
import no.nav.common.client.aktoroppslag.AktorOppslagClient;
import no.nav.common.client.aktoroppslag.CachedAktorOppslagClient;
import no.nav.common.client.aktoroppslag.PdlAktorOppslagClient;
import no.nav.common.client.norg2.CachedNorg2Client;
import no.nav.common.client.norg2.Norg2Client;
import no.nav.common.client.norg2.NorgHttp2Client;
import no.nav.common.token_client.client.AzureAdOnBehalfOfTokenClient;
import no.nav.common.token_client.client.MachineToMachineTokenClient;
import no.nav.common.utils.EnvironmentUtils;
import no.nav.veilarboppgave.client.norg2.Norg2ArbeidsfordelingClient;
import no.nav.veilarboppgave.client.norg2.Norg2ArbeidsfordelingClientImpl;
import no.nav.veilarboppgave.client.oppgave.OppgaveClient;
import no.nav.veilarboppgave.client.oppgave.OppgaveClientImpl;
import no.nav.veilarboppgave.client.veilarbperson.VeilarbpersonClient;
import no.nav.veilarboppgave.client.veilarbperson.VeilarbpersonClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        return new CachedNorg2Client(new NorgHttp2Client(properties.getNorg2Url()));
    }

    @Bean
    public Norg2ArbeidsfordelingClient norg2ArbeidsfordelingClient(EnvironmentProperties properties) {
        return new Norg2ArbeidsfordelingClientImpl(properties.getNorg2Url());
    }

    @Bean
    public OppgaveClient oppgaveClient(EnvironmentProperties properties, AzureAdOnBehalfOfTokenClient tokenClient, AuthContextHolder authContextHolder) {
        return new OppgaveClientImpl(
                properties.getOppgaveUrl(),
                () -> tokenClient.exchangeOnBehalfOfToken(properties.getOppgaveScope(), authContextHolder.requireIdTokenString())
        );
    }

    @Bean
    public VeilarbpersonClient veilarbpersonClient(EnvironmentProperties properties, AzureAdOnBehalfOfTokenClient tokenClient, AuthContextHolder authContextHolder) {
        return new VeilarbpersonClientImpl(
                properties.getVeilarbpersonUrl(),
                () -> tokenClient.exchangeOnBehalfOfToken(properties.getVeilarbpersonScope(), authContextHolder.requireIdTokenString())
        );
    }

    private static boolean isProduction() {
        return EnvironmentUtils.isProduction().orElseThrow();
    }
}
