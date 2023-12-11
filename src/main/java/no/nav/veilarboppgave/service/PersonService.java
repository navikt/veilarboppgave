package no.nav.veilarboppgave.service;

import lombok.RequiredArgsConstructor;
import no.nav.common.types.identer.Fnr;
import no.nav.veilarboppgave.client.veilarbperson.Personalia;
import no.nav.veilarboppgave.client.veilarbperson.VeilarbpersonClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PersonService {

    private final VeilarbpersonClient veilarbpersonClient;

    public Personalia hentPersonalia(Fnr fnr, String behandlingsnummer) {
        return veilarbpersonClient.hentPersonalia(fnr, behandlingsnummer);
    }

}
