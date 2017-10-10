package no.nav.fo.veilarboppgave.mocks;

import no.nav.dialogarena.aktor.AktorService;

import java.util.Optional;

import static java.util.Optional.of;

public class AktorServiceMock implements AktorService {

    @Override
    public Optional<String> getAktorId(String fnr) {
        return of("testaktoerid");
    }

    @Override
    public Optional<String> getFnr(String aktorId) {
        return of("testfnr");
    }
}
