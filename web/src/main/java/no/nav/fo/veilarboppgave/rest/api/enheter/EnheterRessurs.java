package no.nav.fo.veilarboppgave.rest.api.enheter;

import no.nav.apiapp.feil.UgyldigRequest;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.domene.GeografiskTilknytning;
import no.nav.fo.veilarboppgave.domene.OppfolgingEnhet;
import no.nav.fo.veilarboppgave.domene.Tema;
import no.nav.fo.veilarboppgave.rest.api.Valider;
import no.nav.fo.veilarboppgave.security.abac.PepClient;
import no.nav.fo.veilarboppgave.ws.consumer.norg.arbeidsfordeling.ArbeidsfordelingService;
import no.nav.fo.veilarboppgave.ws.consumer.tps.PersonService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

import static java.util.Optional.ofNullable;

@Path("/enheter")
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
    public List<OppfolgingEnhet> hentEnheter(@QueryParam("fnr") String fnr, @QueryParam("tema") String tema) {
        Fnr gyldigFnr = ofNullable(fnr)
                .map(Valider::fnr)
                .map(pepClient::sjekkTilgangTilFnr)
                .orElseThrow(UgyldigRequest::new);

        Tema gyldigTema = ofNullable(tema)
                .map(Valider::tema)
                .orElseThrow(UgyldigRequest::new);

        GeografiskTilknytning tilknytning = personService
                .hentGeografiskTilknytning(gyldigFnr)
                .orElseThrow(UgyldigRequest::new);

        return arbeidsfordelingService.hentBehandlendeEnheter(tilknytning, gyldigTema);
    }
}
