package no.nav.veilarboppgave.service;

import no.nav.veilarboppgave.domain.Oppgave;
import no.nav.veilarboppgave.domain.OppgaveId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OppgaveService {

    public Optional<OppgaveId> opprettOppgave(Oppgave oppgave) {
        return Optional.empty();
    }

}
