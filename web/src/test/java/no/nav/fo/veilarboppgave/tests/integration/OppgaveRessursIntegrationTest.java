package no.nav.fo.veilarboppgave.tests.integration;

import no.nav.fo.veilarboppgave.TestData;
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
import static no.nav.fo.veilarboppgave.TestData.FeltNavn.*;
import static no.nav.fo.veilarboppgave.TestData.genererTilfeldigFnrUtenTilgang;
import static no.nav.fo.veilarboppgave.Util.lagStringAvLengde;
import static no.nav.fo.veilarboppgave.Util.switchOffLogging;
import static org.json.JSONObject.NULL;
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
        JSONObject json = TestData.json();
        json.put(FNR, genererTilfeldigFnrUtenTilgang().getFnr());
        Response response = sendRequest(json);
        assertEquals(403, response.getStatus());
    }

    @Test
    void skal_returnere_400_bad_request_ved_ugyldig_fnr() {
        JSONObject json = TestData.json();
        json.put(FNR, "00000000000");
        Response response = sendRequest(json);
        assertEquals(400, response.getStatus());
    }

    @Test
    void skal_returnere_400_bad_request_ved_ugyldige_datoer() {
        JSONObject json = TestData.json();
        json.put(FRADATO, "2017-09-19");
        json.put(TILDATO, "1999-09-19");
        Response response = sendRequest(json);
        assertEquals(400, response.getStatus());
    }

    @Test
    void skal_returnere_400_bad_request_ved_ugyldig_datoformat() {
        JSONObject json = TestData.json();
        json.put(FRADATO, "2017.09.19");
        Response response = sendRequest(json);
        assertEquals(400, response.getStatus());
    }

    @Test
    void skal_returnere_400_bad_request_ved_manglende_obligatoriske_felter() {
        JSONObject json = TestData.json();
        json.put(PRIORITET, NULL);
        Response response = sendRequest(json);
        assertEquals(400, response.getStatus());
    }

    @Test
    void skal_returnere_200_ok_ved_manglende_valgfrie_felter() {
        JSONObject json = TestData.json();
        json.put(VEILEDER, NULL);
        Response response = sendRequest(json);
        assertEquals(200, response.getStatus());
    }

    @Test
    void skal_returnere_400_ved_ugyldig_prioritet() {
        JSONObject json = TestData.json();
        json.put(PRIORITET, "ugyldig_prioritetKode");
        Response response = sendRequest(json);
        assertEquals(400, response.getStatus());
    }

    @Test
    void skal_returnere_400_ved_for_lang_beskrivelse() {
        JSONObject json = TestData.json();
        json.put(BESKRIVELSE, lagStringAvLengde(501));
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
