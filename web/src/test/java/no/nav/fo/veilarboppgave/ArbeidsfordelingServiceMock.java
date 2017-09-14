package no.nav.fo.veilarboppgave;

import no.nav.fo.veilarboppgave.domene.Enhet;
import no.nav.fo.veilarboppgave.domene.GeografiskTilknytning;
import no.nav.fo.veilarboppgave.ws.consumer.norg.ArbeidsfordelingService;

import java.util.Arrays;
import java.util.List;

public class ArbeidsfordelingServiceMock implements ArbeidsfordelingService {
    @Override
    public List<Enhet> hentBehandlendeEnheter(GeografiskTilknytning geografiskTilknytning) {
        return Arrays.asList(
                Enhet.of("0104", "NAV Moss"),
                Enhet.of("0106", "NAV Fredrikstad"),
                Enhet.of("0122", "NAV Trøgstad")
        );
    }
}
