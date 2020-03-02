package no.nav.fo.veilarboppgave.domene;

import lombok.EqualsAndHashCode;
import lombok.Value;
import no.nav.fo.veilarboppgave.ws.consumer.norg.arbeidsfordeling.ArbeidsfordelingEnhet;
import no.nav.tjeneste.virksomhet.organisasjonenhet.v2.informasjon.WSOrganisasjonsenhet;
import no.nav.virksomhet.organisering.enhetogressurs.v1.Enhet;

@Value(staticConstructor = "of")
@EqualsAndHashCode(exclude = "navn")
public class OppfolgingEnhet {
    String enhetId;
    String navn;

    public static OppfolgingEnhet of(Enhet enhet) {
        return new OppfolgingEnhet(enhet.getEnhetId(), enhet.getNavn());
    }

    public static OppfolgingEnhet of(WSOrganisasjonsenhet enhet) {
        return new OppfolgingEnhet(enhet.getEnhetId(), enhet.getEnhetNavn());
    }

    public static OppfolgingEnhet of(ArbeidsfordelingEnhet enhet) {
        return new OppfolgingEnhet(enhet.getEnhetNr(), enhet.getNavn());
    }
}
