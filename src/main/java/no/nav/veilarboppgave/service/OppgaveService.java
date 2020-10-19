package no.nav.veilarboppgave.service;

import lombok.RequiredArgsConstructor;
import no.nav.common.types.identer.AktorId;
import no.nav.common.types.identer.Fnr;
import no.nav.veilarboppgave.client.gsak.GsakClient;
import no.nav.veilarboppgave.domain.*;
import no.nav.veilarboppgave.repositoyry.OppgaveRepository;
import no.nav.veilarboppgave.util.DateUtils;
import no.nav.veilarboppgave.util.OppgaveUtils;
import no.nav.veilarboppgave.util.Valider;
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

    private final GsakClient gsakClient;

    private final OppgaveRepository oppgaveRepository;

    private final AuthService authService;

    public OppgavehistorikkDTO opprettOppgave(AktorId aktorId, OppgaveDTO oppgaveDto) {
        TemaDTO temaDTO = OppgaveUtils.tilTemaDto(oppgaveDto.getTema());
        OppgaveType oppgaveType = OppgaveUtils.tilOppgaveType(oppgaveDto.getType());
        Prioritet prioritet = OppgaveUtils.tilPrioritet(oppgaveDto.getPrioritet());
        String prioritetKode = utledPrioritetKode(temaDTO, prioritet);
        String oppgaveTypeKode = utledOppgaveTypeKode(temaDTO, oppgaveType);

        Oppgave oppgave = new Oppgave(
                Fnr.of(oppgaveDto.getFnr()),
                temaDTO,
                oppgaveTypeKode,
                prioritetKode,
                oppgaveDto.getBeskrivelse(),
                tilDato(oppgaveDto.getFraDato()),
                tilDato(oppgaveDto.getTilDato()),
                oppgaveDto.getEnhetId(),
                oppgaveDto.getVeilederId(),
                oppgaveDto.getAvsenderenhetId()
        );

        OppgaveId oppgaveId = gsakClient.opprettOppgave(oppgave)
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
