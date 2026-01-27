package no.nav.veilarboppgave.util;

import no.nav.veilarboppgave.domain.BehandlingstemaDTO;
import no.nav.veilarboppgave.domain.OppgaveType;
import no.nav.veilarboppgave.domain.Prioritet;
import no.nav.veilarboppgave.domain.TemaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static java.util.Optional.ofNullable;

public class OppgaveUtils {

    public static <T> List<T> mergeAndDeleteDuplicate(List<T> list1, List<T> list2) {
        Set<T> set = new LinkedHashSet<>(list1);
        set.addAll(list2);

        return new ArrayList<>(set);
    }

    public static TemaDTO tilTemaDto(String tema) {
        return TemaDTO.valueOf(tema.toUpperCase());
    }

    public static BehandlingstemaDTO tilBehadlingstemaDto(String behandlingstema) {
        if (!(behandlingstema.toUpperCase().equals(BehandlingstemaDTO.FERDIG_AVKLART_MOT_UFØRETRYGD))) return null;
        return BehandlingstemaDTO.valueOf(behandlingstema.toUpperCase());
    }

    public static Prioritet tilPrioritet(String prioritet) {
        return Prioritet.valueOf(prioritet.toUpperCase());
    }

    public static OppgaveType tilOppgaveType(String oppgaveType) {
        return OppgaveType.valueOf(oppgaveType.toUpperCase());
    }

}
