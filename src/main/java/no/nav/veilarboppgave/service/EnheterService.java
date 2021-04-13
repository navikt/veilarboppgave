package no.nav.veilarboppgave.service;

import lombok.RequiredArgsConstructor;
import no.nav.common.client.norg2.Norg2Client;
import no.nav.common.types.identer.Fnr;
import no.nav.veilarboppgave.client.norg2.ArbeidsfordelingKriterier;
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

    private final static String DISKRESJONSKODE_6 = "SPSF"; // Sperret adresse, strengt fortrolig

    private final PersonService personService;

    private final Norg2ArbeidsfordelingClient norg2ArbeidsfordelingClient;

    private final Norg2Client norg2Client;

    public List<OppfolgingEnhet> hentEnheter(Fnr fnr, TemaDTO tema) {
        Personalia personalia = personService.hentPersonalia(fnr);

        if ("6".equals(personalia.getDiskresjonskode())) {
            ArbeidsfordelingKriterier kriterier = new ArbeidsfordelingKriterier();
            kriterier.setDiskresjonskode(DISKRESJONSKODE_6);
            return norg2ArbeidsfordelingClient.hentBestMatchEnheter(kriterier);
        }

        Optional<String> maybeGeografiskTilknytning = personService
                .hentGeografiskTilknytning(fnr);

        ArbeidsfordelingKriterier kriterier = new ArbeidsfordelingKriterier();
        kriterier.setSkjermet(personalia.isEgenAnsatt());
        kriterier.setTema(tema.getFagomradeKode());
        maybeGeografiskTilknytning.ifPresent(kriterier::setGeografiskOmraade);

        List<OppfolgingEnhet> arbeidsfordelingEnheter = norg2ArbeidsfordelingClient.hentBestMatchEnheter(kriterier);

        List<OppfolgingEnhet> alleNavEnheter = norg2Client.alleAktiveEnheter()
                .stream()
                .map(e -> OppfolgingEnhet.of(e.getEnhetNr(), e.getNavn()))
                .collect(Collectors.toList());

        return mergeAndDeleteDuplicate(arbeidsfordelingEnheter, alleNavEnheter);
    }

}
