package no.nav.fo.veilarboppgave.mocks;

import no.nav.fo.veilarboppgave.domene.GeografiskTilknytning;
import no.nav.fo.veilarboppgave.domene.OppfolgingEnhet;
import no.nav.fo.veilarboppgave.domene.Tema;
import no.nav.fo.veilarboppgave.ws.consumer.norg.arbeidsfordeling.ArbeidsfordelingService;

import java.util.Arrays;
import java.util.List;

public class ArbeidsfordelingServiceMock implements ArbeidsfordelingService {
    @Override
    public List<OppfolgingEnhet> hentBehandlendeEnheter(GeografiskTilknytning geografiskTilknytning, Tema gyldigTema) {
        return Arrays.asList(
                OppfolgingEnhet.of("0104", "NAV Moss"),
                OppfolgingEnhet.of("0106", "NAV Fredrikstad"),
                OppfolgingEnhet.of("0122", "NAV Trøgstad")
        );
    }
}