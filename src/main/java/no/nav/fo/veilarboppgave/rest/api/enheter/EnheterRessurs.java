package no.nav.fo.veilarboppgave.rest.api.enheter;

import no.nav.apiapp.feil.IngenTilgang;
import no.nav.apiapp.feil.UgyldigRequest;
import no.nav.apiapp.security.veilarbabac.Bruker;
import no.nav.apiapp.security.veilarbabac.VeilarbAbacPepClient;
import no.nav.dialogarena.aktor.AktorService;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.domene.GeografiskTilknytning;
import no.nav.fo.veilarboppgave.domene.OppfolgingEnhet;
import no.nav.fo.veilarboppgave.domene.TemaDTO;
import no.nav.fo.veilarboppgave.rest.api.Valider;
import no.nav.fo.veilarboppgave.ws.consumer.norg.arbeidsfordeling.ArbeidsfordelingService;
import no.nav.fo.veilarboppgave.ws.consumer.norg.organisasjonenhet.OrganisasjonEnhetService;
import no.nav.fo.veilarboppgave.ws.consumer.tps.PersonService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

import static java.util.Optional.ofNullable;
import static no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveUtils.mergeAndDeleteDuplicate;

@Path("/enheter")
public class EnheterRessurs {

    private final ArbeidsfordelingService arbeidsfordelingService;
    private final PersonService personService;
    private final VeilarbAbacPepClient pepClient;
    private final OrganisasjonEnhetService organisasjonEnhetService;
    private final AktorService aktorService;

    @Inject
    public EnheterRessurs(ArbeidsfordelingService arbeidsfordelingService,
                          PersonService personService,
                          VeilarbAbacPepClient pepClient,
                          OrganisasjonEnhetService organisasjonEnhetService,
                          AktorService aktorService) {

        this.arbeidsfordelingService = arbeidsfordelingService;
        this.personService = personService;
        this.pepClient = pepClient;
        this.organisasjonEnhetService = organisasjonEnhetService;
        this.aktorService = aktorService;
    }

    @GET
    public List<OppfolgingEnhet> hentEnheter(@QueryParam("fnr") String fnr, @QueryParam("tema") String tema) {
        Fnr gyldigFnr = ofNullable(fnr)
                .map(Valider::fnr)
                .orElseThrow(UgyldigRequest::new);

        Bruker bruker = Bruker.fraFnr(fnr)
                .medAktoerIdSupplier(() -> aktorService.getAktorId(fnr).orElseThrow(IngenTilgang::new));

        pepClient.sjekkLesetilgangTilBruker(bruker);

        TemaDTO gyldigTemaDTO = ofNullable(tema)
                .map(Valider::tema)
                .orElseThrow(UgyldigRequest::new);

        GeografiskTilknytning tilknytning = personService
                .hentGeografiskTilknytning(gyldigFnr)
                .orElseThrow(UgyldigRequest::new);

        boolean egenAnsatt = personService.hentEgenAnsatt(gyldigFnr);
        List<OppfolgingEnhet> arbeidsfordelingEnheter =
                arbeidsfordelingService.hentBestMatchEnheter(tilknytning, gyldigTemaDTO, egenAnsatt);
        List<OppfolgingEnhet> alleNavEnheter = organisasjonEnhetService.hentAlleEnheter();

        return mergeAndDeleteDuplicate(arbeidsfordelingEnheter, alleNavEnheter);
    }
}
