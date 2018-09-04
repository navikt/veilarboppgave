package no.nav.fo.veilarboppgave.tests.integration;

import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.sbl.dialogarena.common.jetty.Jetty;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import static javax.ws.rs.client.ClientBuilder.newClient;
import static no.nav.fo.veilarboppgave.StartJetty.*;
import static no.nav.fo.veilarboppgave.TestData.genererTilfeldigFnrMedTilgang;
import static no.nav.fo.veilarboppgave.TestData.genererTilfeldigFnrUtenTilgang;
import static no.nav.fo.veilarboppgave.Util.switchOffLogging;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Ignore("TODO: Konverter til PACT")
public class EnheterRessursIntegrationTest {
/*
    private static Client client;
    private static Jetty jetty;
    private static final String uri = String.format("http://localhost:%s/%s", PORT, APPLICATION_NAME);

    @BeforeAll
    static void beforeAll() {
        /*
        switchOffLogging();
        jetty = startJettyUtenSikkerhet();
        client = newClient();
        
    }


    @AfterAll
    static void afterAll() {
        //jetty.stop.run();
    }

    @Test
    public void skal_returnere_403_forbidden_om_veileder_ikke_har_tilgang_til_fnr() {
        Response response = sendRequest(genererTilfeldigFnrUtenTilgang(), "oppfolging");
        assertEquals(403, response.getStatus());
    }

    @Test
    public void skal_returnere_400_bad_request_om_fnr_er_paa_ugyldig_format() {
        Response response = sendRequest(Fnr.of("00000000000"), "oppfolging");
        assertEquals(400, response.getStatus());
    }

    @Test
    public void skal_returnere_400_bad_request_om_fnr_mangler() {
        Response response = sendRequest(Fnr.of(null), "oppfolging");
        assertEquals(400, response.getStatus());
    }

    @Test
    public void skal_returnere_400_bad_request_om_tema_mangler() {
        Response response = sendRequest(genererTilfeldigFnrMedTilgang(), null);
        assertEquals(400, response.getStatus());
    }

    @Test
    public void skal_returnere_400_bad_request_om_tema_er_ugyldig() {
        Response response = sendRequest(genererTilfeldigFnrMedTilgang(), "ugyldig tema");
        assertEquals(400, response.getStatus());
    }

    private static Response sendRequest(Fnr fnr, String tema) {
        return client
                .target(uri)
                .path("/api/enheter")
                .queryParam("fnr", fnr.getFnr())
                .queryParam("tema", tema)
                .request()
                .buildGet()
                .invoke();
    }
    */
}
