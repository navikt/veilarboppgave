package no.nav.fo.veilarboppgave.rest.api.enheter;

import no.nav.apiapp.security.PepClient;
import no.nav.fo.veilarboppgave.domene.Enhet;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.rest.api.Validering;
import no.nav.fo.veilarboppgave.ws.consumer.norg.ArbeidsfordelingService;
import no.nav.fo.veilarboppgave.ws.consumer.tps.PersonService;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/enheter/")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class EnheterRessurs {

    private final ArbeidsfordelingService arbeidsfordelingService;
    private final PersonService personService;
    private final PepClient pepClient;

    @Inject
    public EnheterRessurs(ArbeidsfordelingService arbeidsfordelingService, PersonService personService, PepClient pepClient) {
        this.arbeidsfordelingService = arbeidsfordelingService;
        this.personService = personService;
        this.pepClient = pepClient;
    }

    @GET
    @Path("{fnr}/")
    public List<Enhet> hentEnheter(@PathParam("fnr") String fnr) {
        return Validering.of(fnr)
                .map(Validering::erGyldigFnr)
                .map(pepClient::sjekkTilgangTilFnr)
                .map(Fnr::of)
                .flatMap(personService::hentGeografiskTilknytning)
                .map(arbeidsfordelingService::hentBehandlendeEnheter)
                .orElseThrow(NotFoundException::new);
    }
}
