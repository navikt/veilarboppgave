package no.nav.veilarboppgave.controller;

import lombok.RequiredArgsConstructor;
import no.nav.veilarboppgave.domain.Fnr;
import no.nav.veilarboppgave.domain.GeografiskTilknytning;
import no.nav.veilarboppgave.domain.OppfolgingEnhet;
import no.nav.veilarboppgave.domain.TemaDTO;
import no.nav.veilarboppgave.service.AuthService;
import no.nav.veilarboppgave.service.PersonService;
import no.nav.veilarboppgave.util.Valider;
import no.nav.veilarboppgave.client.norg.arbeidsfordeling.ArbeidsfordelingService;
import no.nav.veilarboppgave.client.norg.organisasjonenhet.OrganisasjonEnhetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static java.util.Optional.ofNullable;
import static no.nav.veilarboppgave.util.OppgaveUtils.mergeAndDeleteDuplicate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/enheter")
public class EnheterController {

    private final ArbeidsfordelingService arbeidsfordelingService;
    private final PersonService personService;
    private final OrganisasjonEnhetService organisasjonEnhetService;
    private final AuthService authService;

    @GetMapping
    public List<OppfolgingEnhet> hentEnheter(@RequestParam("fnr") String fnr, @RequestParam("tema") String tema) {
        Fnr gyldigFnr = ofNullable(fnr)
                .map(Valider::fnr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        String aktorid = authService.getAktorIdOrThrow(gyldigFnr.getFnr());

        authService.sjekkLesetilgangMedAktorId(aktorid);

        TemaDTO gyldigTemaDTO = ofNullable(tema)
                .map(Valider::tema)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        GeografiskTilknytning tilknytning = personService
                .hentGeografiskTilknytning(gyldigFnr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Fant ikke geografisk tilknytning"));

        boolean egenAnsatt = personService.erEgenAnsatt(gyldigFnr);

        List<OppfolgingEnhet> arbeidsfordelingEnheter =
                arbeidsfordelingService.hentBestMatchEnheter(tilknytning, gyldigTemaDTO, egenAnsatt);

        List<OppfolgingEnhet> alleNavEnheter = organisasjonEnhetService.hentAlleEnheter();

        return mergeAndDeleteDuplicate(arbeidsfordelingEnheter, alleNavEnheter);
    }

}
