package no.nav.fo.veilarboppgave.tests.unit;

import no.nav.apiapp.feil.UgyldigRequest;
import no.nav.fo.veilarboppgave.TestData;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.domene.Tema;
import no.nav.fo.veilarboppgave.rest.api.Valider;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValideringTest {

    @ParameterizedTest
    @EnumSource(Tema.class)
    void skal_validere_alle_gyldige_input_for_tema(Tema tema) throws Exception {
        assertNotNull(Valider.tema(tema.name()));
    }

    @ParameterizedTest
    @MethodSource("tilfeldigFnrStream")
    void skal_validere_gyldige_fnr(String fnr) throws Exception {
        Assert.assertNotNull(Valider.fnr(fnr));
    }

    @Test
    public void skal_kaste_exception_om_fra_dato_er_etter_til_dato() throws Exception {
        String fraDato = "2000-09-18";
        String tilDato = "1900-09-18";
        OppgaveDTO testData = TestData.oppgaveDTO(fraDato, tilDato);
        assertThrows(UgyldigRequest.class, () -> Valider.fraTilDato(testData));
    }

    @Test
    public void skal_kaste_exception_om_dato_er_paa_ugyldig_format() throws Exception {
        String ugyldigDatoFormat = "19.09.2017";
        assertThrows(UgyldigRequest.class, () -> Valider.dato(ugyldigDatoFormat));
    }

    private static Stream<String> tilfeldigFnrStream() {
        return Stream.generate(TestData::genererTilfeldigFnr)
                .map(Fnr::getFnr)
                .limit(100);
    }
}