package no.nav.fo.veilarboppgave.rest.api.oppgave;

import no.nav.apiapp.security.PepClient;
import no.nav.brukerdialog.security.context.SubjectHandler;
import no.nav.dialogarena.aktor.AktorService;
import no.nav.fo.veilarboppgave.db.OppgaveRepository;
import no.nav.fo.veilarboppgave.db.OppgavehistorikkDTO;
import no.nav.fo.veilarboppgave.domene.*;
import no.nav.fo.veilarboppgave.rest.api.Valider;
import no.nav.fo.veilarboppgave.ws.consumer.gsak.BehandleOppgaveService;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.sql.Timestamp;
import java.time.Instant;

import static java.util.Optional.ofNullable;
import static no.nav.fo.veilarboppgave.domene.OppgaveType.utledOppgaveTypeKode;
import static no.nav.fo.veilarboppgave.domene.Prioritet.utledPrioritetKode;

@Path("/oppgave")
public class OppgaveRessurs {

    private final BehandleOppgaveService oppgaveService;
    private final PepClient pepClient;
    private final OppgaveRepository oppgaveRepository;
    private final AktorService aktorService;

    @Inject
    public OppgaveRessurs(BehandleOppgaveService oppgaveService, PepClient pepClient,
                          OppgaveRepository oppgaveRepository, AktorService aktorService) {
        this.oppgaveService = oppgaveService;
        this.pepClient = pepClient;
        this.oppgaveRepository = oppgaveRepository;
        this.aktorService = aktorService;
    }

    @POST
    public OppgavehistorikkDTO opprettOppgave(OppgaveDTO dto) {
        String innloggetIdent = SubjectHandler.getSubjectHandler().getUid();

        Fnr fnr = ofNullable(dto.getFnr())
                .map(Valider::fnr)
                .orElseThrow(RuntimeException::new);

        String aktorid = aktorService.getAktorId(fnr.getFnr()).orElseThrow(RuntimeException::new);

        pepClient.sjekkLesetilgangTilAktorId(aktorid);

        ofNullable(dto.avsenderenhetId)
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
