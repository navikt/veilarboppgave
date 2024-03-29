package no.nav.veilarboppgave.controller.v2;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import no.nav.common.types.identer.AktorId;
import no.nav.veilarboppgave.domain.Oppgavehistorikk;
import no.nav.veilarboppgave.domain.OppgavehistorikkRequest;
import no.nav.veilarboppgave.service.AuthService;
import no.nav.veilarboppgave.service.OppgavehistorikkService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class OppgavehistorikkControllerV2 {

    private final AuthService authService;
    private final OppgavehistorikkService oppgavehistorikkService;

    @PostMapping("/hent-oppgavehistorikk")
    @Operation(summary = "Hent oppgavehistorikk")
    public List<Oppgavehistorikk> getOppgavehistorikk(@RequestBody OppgavehistorikkRequest oppgavehistorikkRequest) {
        AktorId aktorId = authService.getAktorIdOrThrow(oppgavehistorikkRequest.fnr());

        authService.sjekkLesetilgangMedAktorId(aktorId);

        return oppgavehistorikkService.hentOppgavehistorikk(aktorId);
    }
}
