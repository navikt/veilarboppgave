package no.nav.veilarboppgave.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class PrioritetTest {
    @Test
    fun skal_utlede_korrekt_prioritetkode_basert_paa_tema_og_prioritet() {
        val temaDTO = TemaDTO.OPPFOLGING
        val pri = Prioritet.NORM

        val kode = Prioritet.utledPrioritetKode(temaDTO, pri)
        Assertions.assertEquals("NORM_OPP", kode)
    }
}
