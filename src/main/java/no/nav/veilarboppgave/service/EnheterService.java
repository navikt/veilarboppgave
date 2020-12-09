package no.nav.veilarboppgave.service;

import lombok.RequiredArgsConstructor;
import no.nav.common.client.norg2.Norg2Client;
import no.nav.common.types.identer.Fnr;
import no.nav.veilarboppgave.client.norg2.Norg2ArbeidsfordelingClient;
import no.nav.veilarboppgave.client.veilarbperson.Personalia;
import no.nav.veilarboppgave.domain.OppfolgingEnhet;
import no.nav.veilarboppgave.domain.TemaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static no.nav.veilarboppgave.util.OppgaveUtils.mergeAndDeleteDuplicate;

@RequiredArgsConstructor
@Service
public class EnheterService {

    private final PersonService personService;

    private final Norg2ArbeidsfordelingClient norg2ArbeidsfordelingClient;

    private final Norg2Client norg2Client;

    public List<OppfolgingEnhet> hentEnheter(Fnr fnr, TemaDTO tema) {
        Optional<String> maybeGeografiskTilknytning = personService
                .hentGeografiskTilknytning(fnr);

        Personalia personalia = personService.hentPersonalia(fnr);

        // Brukere som ikke har diskresjonskode må ha geografisk tilknytning
        if (personalia.getDiskresjonskode() == null && maybeGeografiskTilknytning.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Fant ikke geografisk tilknytning");
        }

        List<OppfolgingEnhet> arbeidsfordelingEnheter = maybeGeografiskTilknytning
                .map(geografiskTilknytning -> norg2ArbeidsfordelingClient.hentBestMatchEnheter(geografiskTilknytning, tema, personalia.isEgenAnsatt()))
                .orElse(Collections.emptyList());

        List<OppfolgingEnhet> alleNavEnheter = norg2Client.alleAktiveEnheter()
                .stream()
                .map(e -> OppfolgingEnhet.of(e.getEnhetNr(), e.getNavn()))
                .collect(Collectors.toList());

        return mergeAndDeleteDuplicate(arbeidsfordelingEnheter, alleNavEnheter);
    }

}
