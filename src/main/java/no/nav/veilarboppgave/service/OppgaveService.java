package no.nav.veilarboppgave.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.nav.common.types.identer.AktorId;
import no.nav.veilarboppgave.client.oppgave.OppgaveClient;
import no.nav.veilarboppgave.domain.*;
import no.nav.veilarboppgave.repositoyry.OppgavehistorikkRepository;
import no.nav.veilarboppgave.util.OppgaveUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;

import static no.nav.veilarboppgave.util.DateUtils.tilDato;

@RequiredArgsConstructor
@Service
@Slf4j
public class OppgaveService {

    private final OppgaveClient oppgaveClient;

    private final OppgavehistorikkRepository oppgavehistorikkRepository;

    private final AuthService authService;

    public OppgavehistorikkDTO opprettOppgave(AktorId aktorId, OppgaveDTO oppgaveDto) {
        TemaDTO temaDTO = OppgaveUtils.tilTemaDto(oppgaveDto.getTema());
        BehandlingstemaDTO behandlingstemaDTO = OppgaveUtils.tilBehandlingstemaDto(oppgaveDto.getBehandlingsTema());
        OppgaveType oppgaveType = OppgaveUtils.tilOppgaveType(oppgaveDto.getType());
        Prioritet prioritet = OppgaveUtils.tilPrioritet(oppgaveDto.getPrioritet());

        Oppgave oppgave = new Oppgave()
                .setAktorId(aktorId)
                .setTemaDTO(temaDTO)
                .setBehandlingstemaDTO(behandlingstemaDTO)
                .setType(oppgaveType)
                .setPrioritet(prioritet)
                .setBeskrivelse(oppgaveDto.getBeskrivelse())
                .setTilDato(tilDato(oppgaveDto.getTilDato()))
                .setFraDato(tilDato(oppgaveDto.getFraDato()))
                .setEnhetId(oppgaveDto.getEnhetId())
                .setVeilederId(oppgaveDto.getVeilederId())
                .setAvsenderenhetId(oppgaveDto.getAvsenderenhetId());

        log.info("Oppgave {}", oppgave);
        OppgaveId oppgaveId = oppgaveClient.opprettOppgave(oppgave)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Klarte ikke å opprette oppgave"));

        oppgavehistorikkRepository.insertOppgaveHistorikk(new OppgavehistorikkDTO(
                oppgaveDto.getTema(),
                oppgaveDto.getType(),
                new Timestamp(Instant.now().toEpochMilli()),
                authService.getInnloggetBrukerIdent(),
                oppgaveId.getOppgaveId(),
                aktorId.get()));

        return oppgavehistorikkRepository.hentOppgavehistorikkForGsakId(oppgaveId);
    }

}
