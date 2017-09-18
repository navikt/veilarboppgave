package no.nav.fo.veilarboppgave.tests.unit;

import no.nav.fo.veilarboppgave.TestData;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.domene.Tema;
import no.nav.fo.veilarboppgave.rest.api.Validering;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ValideringTest {

    @Test
    public void skal_validere_alle_gyldige_input_for_tema() throws Exception {
        long antallValiderteTema = Arrays.stream(Tema.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .map(Validering::erGyldigTema)
                .count();

        assertEquals(antallValiderteTema, Tema.values().length);
    }

    @Test
    public void skal_validere_gyldige_fnr() throws Exception {
        long expected = 100;
        long actual = Stream
                .generate(TestData::genererTilfeldigFnr)
                .map(Fnr::getFnr)
                .map(Validering::erGyldigFnr)
                .limit(expected)
                .count();

        assertEquals(expected, actual);
    }
}