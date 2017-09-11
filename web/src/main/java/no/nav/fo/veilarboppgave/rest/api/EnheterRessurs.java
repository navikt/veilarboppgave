package no.nav.fo.veilarboppgave.rest.api;

import no.nav.fo.veilarboppgave.domene.OrganisasjonsEnhet;
import no.nav.fo.veilarboppgave.norg.ArbeidsfordelingService;
import no.nav.fo.veilarboppgave.tps.PersonService;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

import static java.util.Collections.emptyList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/enheter/")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class EnheterRessurs {

    @Inject
    ArbeidsfordelingService arbeidsfordelingService;

    @Inject
    PersonService personService;

    @GET
    @Path("{fnr}/")
    public List<OrganisasjonsEnhet> hentEnheter(@PathParam("fnr") String fnr) {
        //TODO: Sjekk tilgang!!
        return emptyList();
    }
}
