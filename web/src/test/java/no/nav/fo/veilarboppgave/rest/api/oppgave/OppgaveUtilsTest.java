package no.nav.fo.veilarboppgave.rest.api.oppgave;

import no.nav.fo.veilarboppgave.domene.OppfolgingEnhet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveUtils.mergeAndDeleteDuplicate;
import static org.assertj.core.api.Java6Assertions.assertThat;

class OppgaveUtilsTest {


    @Test
    void skalReturnerLIste1Forst() {
        List<OppfolgingEnhet> list1 = new ArrayList<>();
        list1.add(OppfolgingEnhet.of("id1", "navn1"));

        List<OppfolgingEnhet> list2 = new ArrayList<>();
        list2.add(OppfolgingEnhet.of("id2", "navn2"));

        List<OppfolgingEnhet> result = mergeAndDeleteDuplicate(list1, list2);

        assertThat(result.get(0).getEnhetId()).isEqualTo("id1");
    }

    @Test
    void skalSletteDistincteEnhetider() {
        List<OppfolgingEnhet> list1 = new ArrayList<>();
        list1.add(OppfolgingEnhet.of("id1", "navn1"));

        List<OppfolgingEnhet> list2 = new ArrayList<>();
        list2.add(OppfolgingEnhet.of("id1", "Navn1"));
        list2.add(OppfolgingEnhet.of("id2", "navn2"));

        List<OppfolgingEnhet> result = mergeAndDeleteDuplicate(list1, list2);

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getEnhetId()).isEqualTo("id1");
    }

}