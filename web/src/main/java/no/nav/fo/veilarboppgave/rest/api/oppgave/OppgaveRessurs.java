package no.nav.fo.veilarboppgave.rest.api.oppgave;

import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.domene.OppgaveId;
import no.nav.fo.veilarboppgave.rest.api.Valider;
import no.nav.fo.veilarboppgave.security.abac.PepClient;
import no.nav.fo.veilarboppgave.ws.consumer.gsak.BehandleOppgaveService;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import static java.util.Optional.ofNullable;

@Path("/oppgave")
public class OppgaveRessurs {

    private final BehandleOppgaveService oppgaveService;
    private final PepClient pepClient;

    @Inject
    public OppgaveRessurs(BehandleOppgaveService oppgaveService, PepClient pepClient) {
        this.oppgaveService = oppgaveService;
        this.pepClient = pepClient;
    }

    @POST
    public OppgaveId opprettOppgave(OppgaveDTO dto) {

        Fnr fnr = ofNullable(dto.getFnr())
                .map(Valider::fnr)
                .map(pepClient::sjekkTilgangTilFnr)
                .orElseThrow(RuntimeException::new);

        Valider.fraDatoErFoerTilDato(dto);

        Oppgave oppgave = new Oppgave(
                fnr,
                Valider.tema(dto.getTema()),
                Valider.obligatoriskFelt(dto.getType()),
                Valider.prioritet(dto.getPrioritet()),
                Valider.obligatoriskFelt(dto.getBeskrivelse()),
                Valider.dato(dto.getFraDato()),
                Valider.dato(dto.getTilDato()),
                Valider.obligatoriskFelt(dto.getEnhet()),
                dto.getVeileder()
        );

        return oppgaveService
                .opprettOppgave(oppgave)
                .orElseThrow(NotFoundException::new);
    }
}
