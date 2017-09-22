package no.nav.fo.veilarboppgave.domene;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrioritetTest {

    @Test
    void skal_utlede_korrekt_prioritetkode_basert_paa_tema_og_prioritet() {
        Tema tema = Tema.OPPFOLGING;
        Prioritet pri = Prioritet.NORM;

        String kode = Prioritet.utledPrioritetKode(tema, pri);
        assertEquals("NORM_OPP", kode);
    }
}