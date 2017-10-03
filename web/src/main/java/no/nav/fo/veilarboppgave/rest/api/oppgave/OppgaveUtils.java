package no.nav.fo.veilarboppgave.rest.api.oppgave;

import no.nav.fo.veilarboppgave.domene.OppfolgingEnhet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class OppgaveUtils {

    public static List<OppfolgingEnhet> mergeAndDeleteDuplicate(List<OppfolgingEnhet> list1, List<OppfolgingEnhet> list2) {
        Set<String> idsInList1 = list1.stream().map(OppfolgingEnhet::getEnhetId).collect(Collectors.toSet());
        List<OppfolgingEnhet> list2Copy = list2.stream().filter(enhet -> !idsInList1.contains(enhet.getEnhetId())).collect(Collectors.toList());

        return new ArrayList<>(Stream.concat(list1.stream(), list2Copy.stream())
                .collect(toList()));
    }
}
