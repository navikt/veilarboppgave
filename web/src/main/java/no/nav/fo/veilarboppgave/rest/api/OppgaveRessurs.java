package no.nav.fo.veilarboppgave.rest.api;

import no.nav.fo.veilarboppgave.domene.OppgaveId;
import no.nav.fo.veilarboppgave.ws.consumer.gsak.OppgaveService;

import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/oppgave/")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class OppgaveRessurs {

    private final OppgaveService oppgaveService;

    public OppgaveRessurs(OppgaveService oppgaveService) {
        this.oppgaveService = oppgaveService;
    }

    @POST
    public OppgaveId opprettOppgave(OppgaveDTO oppgave) {
        Validering.of(oppgave.getFnr())
                .map(Validering::erGyldigFnr)
                .map(Validering::sjekkTilgangTilBruker);

        return oppgaveService
                .opprettOppgave()
                .orElseThrow(NotFoundException::new);
    }
}
