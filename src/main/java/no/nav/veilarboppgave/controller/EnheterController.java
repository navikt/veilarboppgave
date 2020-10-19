package no.nav.veilarboppgave.controller;

import lombok.RequiredArgsConstructor;
import no.nav.common.types.identer.AktorId;
import no.nav.common.types.identer.Fnr;
import no.nav.veilarboppgave.domain.OppfolgingEnhet;
import no.nav.veilarboppgave.domain.TemaDTO;
import no.nav.veilarboppgave.service.AuthService;
import no.nav.veilarboppgave.service.EnheterService;
import no.nav.veilarboppgave.util.OppgaveUtils;
import no.nav.veilarboppgave.util.Valider;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/enheter")
public class EnheterController {

    private final EnheterService enheterService;
    private final AuthService authService;

    @GetMapping
    public List<OppfolgingEnhet> hentEnheter(@RequestParam("fnr") Fnr fnr, @RequestParam("tema") String tema) {
        AktorId aktorid = authService.getAktorIdOrThrow(fnr);

        authService.sjekkLesetilgangMedAktorId(aktorid);

        Valider.validerTema(tema);

        TemaDTO gyldigTemaDTO = ofNullable(tema)
                .map(OppgaveUtils::tilTemaDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        return enheterService.hentEnheter(fnr, gyldigTemaDTO);
    }

}
