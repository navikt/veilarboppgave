package no.nav.fo.veilarboppgave.ws.consumer.aktoer;

import io.vavr.control.Try;
import no.nav.fo.veilarboppgave.domene.Aktoerid;
import no.nav.fo.veilarboppgave.domene.Fnr;

public interface AktoerService {
    public Try<Aktoerid> hentAktoeridFraFnr(Fnr fnr);
    public Try<Fnr> hentFnrViaFraAktoerid(Aktoerid aktoerId);
}
