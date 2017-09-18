package no.nav.fo.veilarboppgave.tests.unit;

import no.nav.apiapp.feil.UgyldigRequest;
import no.nav.fo.veilarboppgave.TestData;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.domene.Tema;
import no.nav.fo.veilarboppgave.rest.api.Valider;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveDTO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ValideringTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void skal_validere_alle_gyldige_input_for_tema() throws Exception {
        long antallValiderteTema = Arrays.stream(Tema.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .map(Valider::tema)
                .count();

        int antallMuligeTema = Tema.values().length;
        assertEquals(antallValiderteTema, antallMuligeTema);
    }

    @Test
    public void skal_validere_gyldige_fnr() throws Exception {
        long expected = 100;
        long actual = Stream
                .generate(TestData::genererTilfeldigFnr)
                .map(Fnr::getFnr)
                .map(Valider::fnr)
                .limit(expected)
                .count();

        assertEquals(expected, actual);
    }

    @Test
    public void skal_kaste_exception_om_fra_dato_er_etter_til_dato() throws Exception {
        expectedException.expect(UgyldigRequest.class);
        String fraDato = "2000-09-18";
        String tilDato = "1900-09-18";
        OppgaveDTO testData = TestData.oppgaveDTO(fraDato, tilDato);
        Valider.fraTilDato(testData);
    }

    @Test
    public void skal_kaste_exception_om_dato_er_paa_ugyldig_format() throws Exception {
        expectedException.expect(UgyldigRequest.class);
        String ugyldigDatoFormat = "19.09.2017";
        Valider.dato(ugyldigDatoFormat);
    }
}