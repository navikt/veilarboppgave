package no.nav.veilarboppgave.domain;

import lombok.EqualsAndHashCode;
import lombok.Value;
import no.nav.veilarboppgave.client.norg2.ArbeidsfordelingEnhet;

@Value(staticConstructor = "of")
@EqualsAndHashCode(exclude = "navn")
public class OppfolgingEnhet {
    String enhetId;
    String navn;

    public static OppfolgingEnhet of(ArbeidsfordelingEnhet enhet) {
        return new OppfolgingEnhet(enhet.getEnhetNr(), enhet.getNavn());
    }

}
