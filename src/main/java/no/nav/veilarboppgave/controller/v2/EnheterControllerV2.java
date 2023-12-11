package no.nav.veilarboppgave.controller.v2;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import no.nav.common.types.identer.AktorId;
import no.nav.common.types.identer.Fnr;
import no.nav.veilarboppgave.domain.EnheterRequest;
import no.nav.veilarboppgave.domain.OppfolgingEnhet;
import no.nav.veilarboppgave.service.AuthService;
import no.nav.veilarboppgave.service.EnheterService;
import no.nav.veilarboppgave.util.Valider;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static no.nav.veilarboppgave.util.OppgaveUtils.tilTemaDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class EnheterControllerV2 {

    private final EnheterService enheterService;
    private final AuthService authService;

    @PostMapping("/hent-enheter")
    @Operation(summary = "Hent enheter")
    public List<OppfolgingEnhet> hentEnheter(@RequestBody EnheterRequest enheterRequest, @RequestParam("tema") String tema) {
        Fnr fnr = enheterRequest.fnr();
        AktorId aktorid = authService.getAktorIdOrThrow(fnr);

        authService.sjekkLesetilgangMedAktorId(aktorid);

        Valider.validerTema(tema);

        return enheterService.hentEnheter(fnr, enheterRequest.behandlingsnummer(), tilTemaDto(tema));
    }

}
