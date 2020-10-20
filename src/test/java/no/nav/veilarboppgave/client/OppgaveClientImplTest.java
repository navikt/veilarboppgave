package no.nav.veilarboppgave.client;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import no.nav.common.log.MDCConstants;
import no.nav.common.types.identer.AktorId;
import no.nav.veilarboppgave.client.oppgave.OppgaveClientImpl;
import no.nav.veilarboppgave.domain.*;
import no.nav.veilarboppgave.utils.TestUtils;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.MDC;

import java.time.LocalDate;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class OppgaveClientImplTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(0);

    @Test
    public void opprettOppgave__skal_lage_riktig_request() {
        String opprettOppgaveRequest = TestUtils.readTestResourceFile("opprett-oppgave-request.json");
        String opprettOppgaveResponse = TestUtils.readTestResourceFile("opprett-oppgave-response.json");

        String apiUrl = "http://localhost:" + wireMockRule.port();
        OppgaveClientImpl oppgaveClient = new OppgaveClientImpl(apiUrl, () -> "TOKEN");

        givenThat(post("/api/v1/oppgaver")
                .withHeader("X-Correlation-Id", equalTo("call-id"))
                .withRequestBody(equalToJson(opprettOppgaveRequest))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Authorization", "Bearer TOKEN")
                        .withBody(opprettOppgaveResponse))
        );

        MDC.put(MDCConstants.MDC_CALL_ID, "call-id");

        Oppgave oppgave = new Oppgave()
                .setAktorId(AktorId.of("11223344"))
                .setTemaDTO(TemaDTO.DAGPENGER)
                .setType(OppgaveType.VURDER_HENVENDELSE)
                .setPrioritet(Prioritet.NORM)
                .setBeskrivelse("Beskrivelse av oppgave")
                .setFraDato(LocalDate.of(2020, 10, 20))
                .setTilDato(LocalDate.of(2020, 10, 22))
                .setEnhetId("1234")
                .setAvsenderenhetId("5678")
                .setVeilederId("Z1234");

        Optional<OppgaveId> maybeOppgaveId = oppgaveClient.opprettOppgave(oppgave);

        assertTrue(maybeOppgaveId.isPresent());
        assertEquals(maybeOppgaveId.get().getOppgaveId(), "1");
    }

}
