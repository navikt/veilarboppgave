package no.nav.veilarboppgave.config;

import lombok.extern.slf4j.Slf4j;
import no.nav.common.abac.Pep;
import no.nav.common.client.aktorregister.AktorregisterClient;
import no.nav.common.client.norg2.Norg2Client;
import no.nav.common.health.HealthCheck;
import no.nav.common.health.HealthCheckResult;
import no.nav.common.health.selftest.SelfTestCheck;
import no.nav.common.health.selftest.SelfTestChecks;
import no.nav.common.health.selftest.SelfTestMeterBinder;
import no.nav.veilarboppgave.client.norg2.Norg2ArbeidsfordelingClient;
import no.nav.veilarboppgave.client.oppgave.OppgaveClient;
import no.nav.veilarboppgave.client.veilarbperson.VeilarbpersonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
public class HelsesjekkConfig {

    @Bean
    public SelfTestChecks selfTestChecks(
            JdbcTemplate jdbcTemplate,
            Pep pep,
            AktorregisterClient aktorregisterClient,
            OppgaveClient oppgaveClient,
            Norg2ArbeidsfordelingClient norg2ArbeidsfordelingClient,
            Norg2Client norg2Client,
            VeilarbpersonClient veilarbpersonClient
            ) {

        List<SelfTestCheck> selfTestChecks = Arrays.asList(
                new SelfTestCheck("Enkel spørring mot Databasen til veilarboppfolging.", true, checkDbHealth(jdbcTemplate)),
                new SelfTestCheck("ABAC tilgangskontroll - ping", true, pep.getAbacClient()),
                new SelfTestCheck("Aktorregister (konvertere mellom aktorId og Fnr).", true, aktorregisterClient),
                new SelfTestCheck("Gsak (oppretting av oppgave)", true, oppgaveClient),
                new SelfTestCheck("Norg2 arbeidsfordeling", true, norg2ArbeidsfordelingClient),
                new SelfTestCheck("Norg2", true, norg2Client),
                new SelfTestCheck("Veilarbperson", true, veilarbpersonClient)
        );

        return new SelfTestChecks(selfTestChecks);
    }

    private HealthCheck checkDbHealth(JdbcTemplate jdbcTemplate) {
        return () -> {
            try {
                jdbcTemplate.queryForObject("SELECT 1", Long.class);
                return HealthCheckResult.healthy();
            } catch (Exception e) {
                log.error("Helsesjekk mot database feilet", e);
                return HealthCheckResult.unhealthy("Fikk ikke kontakt med databasen", e);
            }
        };
    }

    @Bean
    public SelfTestMeterBinder selfTestMeterBinder(SelfTestChecks selfTestChecks) {
        return new SelfTestMeterBinder(selfTestChecks);
    }
}
