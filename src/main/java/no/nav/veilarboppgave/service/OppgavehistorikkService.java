package no.nav.veilarboppgave.service;

import lombok.RequiredArgsConstructor;
import no.nav.common.types.identer.AktorId;
import no.nav.veilarboppgave.domain.Oppgavehistorikk;
import no.nav.veilarboppgave.domain.OppgavehistorikkDTO;
import no.nav.veilarboppgave.repositoyry.OppgavehistorikkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class OppgavehistorikkService {

    private final OppgavehistorikkRepository oppgavehistorikkRepository;

    public List<Oppgavehistorikk> hentOppgavehistorikk(AktorId aktorId) {
        List<OppgavehistorikkDTO> oppgavehistorikk = oppgavehistorikkRepository.hentOppgavehistorikkForBruker(aktorId);
        return oppgavehistorikk.stream().map(Oppgavehistorikk::of).collect(toList());
    }

}
