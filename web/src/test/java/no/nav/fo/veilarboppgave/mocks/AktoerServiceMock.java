package no.nav.fo.veilarboppgave.mocks;

import io.vavr.control.Try;
import no.nav.fo.veilarboppgave.domene.Aktoerid;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.ws.consumer.aktoer.AktoerService;

public class AktoerServiceMock implements AktoerService{
    @Override
    public Try<Aktoerid> hentAktoeridFraFnr(Fnr fnr) {
        return Try.success(Aktoerid.of("testaktoerid"));
    }

    @Override
    public Try<Fnr> hentFnrViaFraAktoerid(Aktoerid aktoerId) {
        return Try.success(Fnr.of("testfnr"));
    }
}
