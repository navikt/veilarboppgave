package no.nav.fo.veilarboppgave.rest.api;

import no.nav.fo.veilarboppgave.domene.Enhet;
import no.nav.fo.veilarboppgave.norg.ArbeidsfordelingService;
import no.nav.fo.veilarboppgave.tps.PersonService;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Arrays;
import java.util.List;

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
    public List<Enhet> hentEnheter(@PathParam("fnr") String fnr) {
        //TODO: Sjekk tilgang!!
        return Arrays.asList(
                Enhet.of("0106", "NAV Fredrikstad"),
                Enhet.of("0105", "NAV Sarpsborg"),
                Enhet.of("0122", "NAV Trøgstad")
        );
    }
}
