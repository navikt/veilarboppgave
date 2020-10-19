package no.nav.veilarboppgave.mocks;

import no.nav.veilarboppgave.domene.GeografiskTilknytning;
import no.nav.veilarboppgave.domene.OppfolgingEnhet;
import no.nav.veilarboppgave.domene.TemaDTO;
import no.nav.veilarboppgave.ws.consumer.norg.arbeidsfordeling.ArbeidsfordelingService;

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
