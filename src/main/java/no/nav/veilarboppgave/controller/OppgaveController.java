package no.nav.veilarboppgave.controller;

import lombok.RequiredArgsConstructor;
import no.nav.common.types.identer.AktorId;
import no.nav.common.types.identer.Fnr;
import no.nav.veilarboppgave.domain.*;
import no.nav.veilarboppgave.service.AuthService;
import no.nav.veilarboppgave.service.OppgaveService;
import no.nav.veilarboppgave.util.Valider;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/oppgave")
public class OppgaveController {

    private final OppgaveService oppgaveService;
    private final AuthService authService;

    @PostMapping
    public OppgavehistorikkDTO opprettOppgave(@RequestBody OppgaveDTO oppgaveDto) {
        Fnr fnr = ofNullable(oppgaveDto.getFnr())
                .map(Fnr::of)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fnr mangler"));

        AktorId aktorid = authService.getAktorIdOrThrow(fnr);

        authService.sjekkLesetilgangMedAktorId(aktorid);

        Valider.validerOppgaveDto(oppgaveDto);

        return oppgaveService.opprettOppgave(aktorid, oppgaveDto);
    }

}
