package no.nav.veilarboppgave.mocks;

import no.nav.veilarboppgave.domain.GeografiskTilknytning;
import no.nav.veilarboppgave.domain.OppfolgingEnhet;
import no.nav.veilarboppgave.domain.TemaDTO;
import no.nav.veilarboppgave.client.norg.arbeidsfordeling.ArbeidsfordelingService;

import java.util.Arrays;
import java.util.List;

public class ArbeidsfordelingServiceMock implements ArbeidsfordelingService {

    @Override
    public List<OppfolgingEnhet> hentBestMatchEnheter(GeografiskTilknytning geografiskTilknytning, TemaDTO tema, boolean egenAnsatt) {
        return Arrays.asList(
                OppfolgingEnhet.of("0104", "NAV Moss"),
                OppfolgingEnhet.of("0106", "NAV Fredrikstad"),
                OppfolgingEnhet.of("0122", "NAV Trøgstad")
        );
    }
}
