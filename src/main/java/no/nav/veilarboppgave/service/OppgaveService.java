package no.nav.veilarboppgave.service;

import lombok.RequiredArgsConstructor;
import no.nav.common.types.identer.AktorId;
import no.nav.common.types.identer.Fnr;
import no.nav.veilarboppgave.client.oppgave.OppgaveClient;
import no.nav.veilarboppgave.domain.*;
import no.nav.veilarboppgave.repositoyry.OppgaveRepository;
import no.nav.veilarboppgave.util.OppgaveUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;

import static no.nav.veilarboppgave.domain.OppgaveType.utledOppgaveTypeKode;
import static no.nav.veilarboppgave.domain.Prioritet.utledPrioritetKode;
import static no.nav.veilarboppgave.util.DateUtils.tilDato;

@RequiredArgsConstructor
@Service
public class OppgaveService {

    private final OppgaveClient oppgaveClient;

    private final OppgaveRepository oppgaveRepository;

    private final AuthService authService;

    public OppgavehistorikkDTO opprettOppgave(AktorId aktorId, OppgaveDTO oppgaveDto) {
        TemaDTO temaDTO = OppgaveUtils.tilTemaDto(oppgaveDto.getTema());
        OppgaveType oppgaveType = OppgaveUtils.tilOppgaveType(oppgaveDto.getType());
        Prioritet prioritet = OppgaveUtils.tilPrioritet(oppgaveDto.getPrioritet());
        String oppgaveTypeKode = utledOppgaveTypeKode(temaDTO, oppgaveType);

        Oppgave oppgave = new Oppgave()
                .setAktorId(aktorId)
                .setTemaDTO(temaDTO)
                .setType(oppgaveTypeKode)
                .setPrioritet(prioritet.name())
                .setBeskrivelse(oppgaveDto.getBeskrivelse())
                .setTilDato(tilDato(oppgaveDto.getTilDato()))
                .setFraDato(tilDato(oppgaveDto.getFraDato()))
                .setEnhetId(oppgaveDto.getEnhetId())
                .setVeilederId(oppgaveDto.getVeilederId())
                .setAvsenderenhetId(oppgaveDto.getAvsenderenhetId());

        OppgaveId oppgaveId = oppgaveClient.opprettOppgave(oppgave)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Klarte ikke å opprette oppgave"));

        oppgaveRepository.insertOppgaveHistorikk(new OppgavehistorikkDTO(
                oppgaveDto.getTema(),
                oppgaveDto.getType(),
                new Timestamp(Instant.now().toEpochMilli()),
                authService.getInnloggetBrukerIdent(),
                oppgaveId.getOppgaveId(),
                aktorId.get()));

        return oppgaveRepository.hentOppgavehistorikkForGSAKID(oppgaveId);
    }

}
