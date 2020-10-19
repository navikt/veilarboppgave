package no.nav.veilarboppgave.controller;

import lombok.RequiredArgsConstructor;
import no.nav.common.types.identer.AktorId;
import no.nav.common.types.identer.Fnr;
import no.nav.veilarboppgave.domain.Oppgavehistorikk;
import no.nav.veilarboppgave.service.AuthService;
import no.nav.veilarboppgave.service.OppgavehistorikkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/oppgavehistorikk")
public class OppgavehistorikkController {

    private final AuthService authService;
    private final OppgavehistorikkService oppgavehistorikkService;

    @GetMapping
    public List<Oppgavehistorikk> getOppgavehistorikk(@RequestParam("fnr") Fnr fnr) {
        AktorId aktorId = authService.getAktorIdOrThrow(fnr);

        authService.sjekkLesetilgangMedAktorId(aktorId);

        return oppgavehistorikkService.hentOppgavehistorikk(aktorId);
    }
}
