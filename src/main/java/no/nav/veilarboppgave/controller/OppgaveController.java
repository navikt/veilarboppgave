package no.nav.veilarboppgave.controller;

import lombok.RequiredArgsConstructor;
import no.nav.veilarboppgave.repositoyry.OppgaveRepository;
import no.nav.veilarboppgave.domain.OppgavehistorikkDTO;
import no.nav.veilarboppgave.domain.*;
import no.nav.veilarboppgave.domain.Oppgave;
import no.nav.veilarboppgave.domain.OppgaveDTO;
import no.nav.veilarboppgave.service.AuthService;
import no.nav.veilarboppgave.service.OppgaveService;
import no.nav.veilarboppgave.util.Valider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.NotFoundException;
import java.sql.Timestamp;
import java.time.Instant;

import static java.util.Optional.ofNullable;
import static no.nav.veilarboppgave.domain.OppgaveType.utledOppgaveTypeKode;
import static no.nav.veilarboppgave.domain.Prioritet.utledPrioritetKode;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/oppgave")
public class OppgaveController {

    private final OppgaveService oppgaveService;
    private final OppgaveRepository oppgaveRepository;
    private final AuthService authService;

    @PostMapping
    public OppgavehistorikkDTO opprettOppgave(OppgaveDTO dto) {
        String innloggetIdent = authService.getInnloggetBrukerIdent();

        Fnr fnr = ofNullable(dto.getFnr())
                .map(Valider::fnr)
                .orElseThrow(RuntimeException::new);

        String aktorid = authService.getAktorIdOrThrow(dto.getFnr());

        authService.sjekkLesetilgangMedAktorId(aktorid);

        ofNullable(dto.getAvsenderenhetId())
                .map(Valider::atFeltErUtfylt);

        Valider.fraDatoErFoerTilDato(dto);

        TemaDTO temaDTO = Valider.tema(dto.getTema());
        OppgaveType oppgaveType = Valider.oppgavetype(dto.getType());
        Prioritet prioritet = Valider.prioritet(dto.getPrioritet());
        String prioritetKode = utledPrioritetKode(temaDTO, prioritet);
        String oppgaveTypeKode = utledOppgaveTypeKode(temaDTO, oppgaveType);

        Oppgave oppgave = new Oppgave(
                fnr,
                Valider.tema(dto.getTema()),
                oppgaveTypeKode,
                prioritetKode,
                Valider.beskrivelse(dto.getBeskrivelse()),
                Valider.dato(dto.getFraDato()),
                Valider.dato(dto.getTilDato()),
                Valider.atFeltErUtfylt(dto.getEnhetId()),
                dto.getVeilederId(),
                dto.getAvsenderenhetId()
        );

        OppgaveId oppgaveId = oppgaveService.opprettOppgave(oppgave).orElseThrow(NotFoundException::new);
        oppgaveRepository.insertOppgaveHistorikk(new OppgavehistorikkDTO(
                dto.getTema(),
                dto.getType(),
                new Timestamp(Instant.now().toEpochMilli()),
                innloggetIdent,
                oppgaveId.getOppgaveId(),
                aktorid));

        return oppgaveRepository.hentOppgavehistorikkForGSAKID(oppgaveId);
    }
}
