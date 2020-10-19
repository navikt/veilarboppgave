package no.nav.veilarboppgave.domene;

import lombok.Value;

import java.util.Optional;

@Value
public class OppgaveId {
    String oppgaveId;

    public static Optional<OppgaveId> of(String oppgaveId) {
        if (oppgaveId == null) {
            return Optional.empty();
        }
        return Optional.of(new OppgaveId(oppgaveId));
    }
}
