package no.nav.fo.veilarboppgave.security.abac;

import no.nav.fo.veilarboppgave.domene.Fnr;

public interface PepClient {
    Fnr sjekkTilgangTilFnr(Fnr fnr);
}
