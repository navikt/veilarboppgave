package no.nav.fo.veilarboppgave.rest.api.oppgave;

import no.nav.apiapp.security.PepClient;
import no.nav.fo.veilarboppgave.domene.OppgaveId;
import no.nav.fo.veilarboppgave.rest.api.Validering;
import no.nav.fo.veilarboppgave.ws.consumer.gsak.OppgaveService;

import javax.inject.Inject;
import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/oppgave/")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class OppgaveRessurs {

    private final OppgaveService oppgaveService;
    private final PepClient pepClient;

    @Inject
    public OppgaveRessurs(OppgaveService oppgaveService, PepClient pepClient) {
        this.oppgaveService = oppgaveService;
        this.pepClient = pepClient;
    }

    @POST
    public OppgaveId opprettOppgave(OppgaveDTO oppgave) {
        Validering.of(oppgave.getFnr())
                .map(Validering::erGyldigFnr)
                .map(pepClient::sjekkTilgangTilFnr);

        return oppgaveService
                .opprettOppgave()
                .orElseThrow(NotFoundException::new);
    }
}
