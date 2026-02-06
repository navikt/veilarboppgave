package no.nav.veilarboppgave.util;

import no.nav.veilarboppgave.domain.BehandlingstemaDTO;
import no.nav.veilarboppgave.domain.OppgaveType;
import no.nav.veilarboppgave.domain.Prioritet;
import no.nav.veilarboppgave.domain.TemaDTO;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class OppgaveUtils {

    public static <T> List<T> mergeAndDeleteDuplicate(List<T> list1, List<T> list2) {
        Set<T> set = new LinkedHashSet<>(list1);
        set.addAll(list2);

        return new ArrayList<>(set);
    }

    public static TemaDTO tilTemaDto(String tema) {
        return TemaDTO.valueOf(tema.toUpperCase());
    }

    public static BehandlingstemaDTO tilBehandlingstemaDto(String behandlingsTema) {
        if (behandlingsTema == null) return null;

        if (!BehandlingstemaDTO.FERDIG_AVKLART_MOT_UFØRETRYGD.name().equalsIgnoreCase(behandlingsTema)) {
            return null;
        }
        return BehandlingstemaDTO.FERDIG_AVKLART_MOT_UFØRETRYGD;
    }

    public static Prioritet tilPrioritet(String prioritet) {
        return Prioritet.valueOf(prioritet.toUpperCase());
    }

    public static OppgaveType tilOppgaveType(String oppgaveType) {
        return OppgaveType.valueOf(oppgaveType.toUpperCase());
    }

}
