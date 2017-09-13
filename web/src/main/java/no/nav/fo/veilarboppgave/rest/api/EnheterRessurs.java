package no.nav.fo.veilarboppgave.rest.api;

import no.nav.fo.veilarboppgave.domene.Enhet;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.norg.ArbeidsfordelingService;
import no.nav.fo.veilarboppgave.tps.PersonService;

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

    @Inject
    public EnheterRessurs(ArbeidsfordelingService arbeidsfordelingService, PersonService personService) {
        this.arbeidsfordelingService = arbeidsfordelingService;
        this.personService = personService;
    }

    @GET
    @Path("{fnr}/")
    public List<Enhet> hentEnheter(@PathParam("fnr") String fnr) {
        return Validering.of(fnr)
                .map(Validering::erGyldigFnr)
                .map(Validering::sjekkTilgangTilBruker)
                .map(Fnr::of)
                .flatMap(personService::hentGeografiskTilknytning)
                .map(arbeidsfordelingService::hentBehandlendeEnheter)
                .orElseThrow(NotFoundException::new);
    }
}
