package no.nav.veilarboppgave.domene;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrioritetTest {

    @Test
    void skal_utlede_korrekt_prioritetkode_basert_paa_tema_og_prioritet() {
        TemaDTO temaDTO = TemaDTO.OPPFOLGING;
        Prioritet pri = Prioritet.NORM;

        String kode = Prioritet.utledPrioritetKode(temaDTO, pri);
        assertEquals("NORM_OPP", kode);
    }
}
