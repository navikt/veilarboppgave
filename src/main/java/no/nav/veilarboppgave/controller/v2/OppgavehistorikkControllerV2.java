package no.nav.veilarboppgave.controller.v2;

import lombok.RequiredArgsConstructor;
import no.nav.common.types.identer.AktorId;
import no.nav.common.types.identer.Fnr;
import no.nav.veilarboppgave.domain.Oppgavehistorikk;
import no.nav.veilarboppgave.domain.OppgavehistorikkRequest;
import no.nav.veilarboppgave.service.AuthService;
import no.nav.veilarboppgave.service.OppgavehistorikkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/oppgavehistorikk")
public class OppgavehistorikkControllerV2 {

    private final AuthService authService;
    private final OppgavehistorikkService oppgavehistorikkService;

    @GetMapping
    public List<Oppgavehistorikk> getOppgavehistorikk(@RequestBody OppgavehistorikkRequest oppgavehistorikkRequest) {
        AktorId aktorId = authService.getAktorIdOrThrow(oppgavehistorikkRequest.fnr());

        authService.sjekkLesetilgangMedAktorId(aktorId);

        return oppgavehistorikkService.hentOppgavehistorikk(aktorId);
    }
}
