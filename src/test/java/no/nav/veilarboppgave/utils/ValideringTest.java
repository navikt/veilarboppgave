package no.nav.veilarboppgave.utils;

import no.nav.veilarboppgave.util.Valider;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

class ValideringTest {

    @Test
    void skal_ikke_validere_ugyldig_tema() {
        assertThrows(ResponseStatusException.class, () -> Valider.validerTema("ugyldig_tema"));
    }

    @Test
    void skal_kaste_exception_om_fra_dato_er_etter_til_dato() {
        String fraDato = "2000-09-18";
        String tilDato = "1900-09-18";
        assertThrows(ResponseStatusException.class, () -> Valider.validerFraDatoErForTilDato(fraDato, tilDato));
    }

    @Test
    void skal_ikke_godta_beskrivelse_over_250_tegn() {
        String akkuratForLangBeskrivelse = TestUtils.lagStringAvLengde(251);
        String akkuratPasseLangBeskrivelse = TestUtils.lagStringAvLengde(250);
        assertThrows(ResponseStatusException.class, () -> Valider.validerBeskrivelse(akkuratForLangBeskrivelse));
        assertDoesNotThrow(() -> Valider.validerBeskrivelse(akkuratPasseLangBeskrivelse));
    }

    @Test
    void skal_ikke_valider_ugyldig_oppgavetype() {
        String ugyldigOppgavetype = "ugyldigOppgavetype";
        assertThrows(ResponseStatusException.class, () -> Valider.validerOppgaveType(ugyldigOppgavetype));
    }

}
