package no.nav.fo.veilarboppgave.tests.integration;

import no.nav.fo.veilarboppgave.TestData;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.sbl.dialogarena.common.jetty.Jetty;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import static javax.ws.rs.client.ClientBuilder.newClient;
import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static no.nav.fo.veilarboppgave.StartJetty.*;
import static no.nav.fo.veilarboppgave.TestData.genererTilfeldigFnrMedTilgang;
import static no.nav.fo.veilarboppgave.TestData.genererTilfeldigFnrUtenTilgang;
import static no.nav.fo.veilarboppgave.Util.switchOffLogging;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OppgaveRessursIntegrationTest {

    private static Client client;
    private static Jetty jetty;
    private static final String uri = String.format("http://localhost:%s/%s", PORT, APPLICATION_NAME);

    @BeforeAll
    static void beforeAll() {
        switchOffLogging();
        jetty = startJettyUtenSikkerhet();
        client = newClient();
    }

    @AfterAll
    static void afterAll() {
        jetty.stop.run();
    }

    @Test
    void skal_returnere_403_forbidden_om_veileder_ikke_har_tilgang_til_fnr() {
        JSONObject json = TestData.oppgaveSomJson(genererTilfeldigFnrUtenTilgang());
        Response response = sendRequest(json);
        assertEquals(403, response.getStatus());
    }

    @Test
    void skal_returnere_400_bad_request_ved_ugyldig_fnr() {
        JSONObject json = TestData.oppgaveSomJson(Fnr.of("00000000000"));
        Response response = sendRequest(json);
        assertEquals(400, response.getStatus());
    }

    @Test
    void skal_returnere_400_bad_request_ved_ugyldige_datoer() {
        JSONObject json = TestData.oppgaveSomJson(genererTilfeldigFnrMedTilgang(), "2017-09-19", "2017-01-19");
        Response response = sendRequest(json);
        assertEquals(400, response.getStatus());
    }

    @Test
    void skal_returnere_400_bad_request_ved_ugyldig_datoformat() {
        JSONObject json = TestData.oppgaveSomJson(genererTilfeldigFnrMedTilgang(), "2017.09.19", "2020.01.19");
        Response response = sendRequest(json);
        assertEquals(400, response.getStatus());
    }

    private static Response sendRequest(JSONObject json) {
        return client
                .target(uri)
                .path("/api/oppgave")
                .request()
                .buildPost(entity(json.toString(), APPLICATION_JSON))
                .invoke();
    }

}
