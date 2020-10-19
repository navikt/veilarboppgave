package no.nav.veilarboppgave.service;

import lombok.RequiredArgsConstructor;
import no.nav.veilarboppgave.domain.Aktoerid;
import no.nav.veilarboppgave.domain.Oppgavehistorikk;
import no.nav.veilarboppgave.domain.OppgavehistorikkDTO;
import no.nav.veilarboppgave.repositoyry.OppgaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class OppgavehistorikkService {

    private final OppgaveRepository oppgaveRepository;

    public List<Oppgavehistorikk> hentOppgavehistorikk(Aktoerid aktorId) {
        List<OppgavehistorikkDTO> oppgavehistorikk = oppgaveRepository.hentOppgavehistorikkForBruker(aktorId);
        return oppgavehistorikk.stream().map(Oppgavehistorikk::of).collect(toList());
    }

}
