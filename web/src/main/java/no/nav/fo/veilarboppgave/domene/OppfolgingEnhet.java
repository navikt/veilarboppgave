package no.nav.fo.veilarboppgave.domene;

import lombok.Value;
import no.nav.tjeneste.virksomhet.organisasjonenhet.v2.informasjon.WSOrganisasjonsenhet;
import no.nav.virksomhet.organisering.enhetogressurs.v1.Enhet;

@Value(staticConstructor = "of")
public class OppfolgingEnhet {
    String enhetId;
    String navn;

    public static OppfolgingEnhet of(no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.WSOrganisasjonsenhet wsOrganisasjonsenhet) {
        return new OppfolgingEnhet(wsOrganisasjonsenhet.getEnhetId(), wsOrganisasjonsenhet.getEnhetNavn());
    }

    public static OppfolgingEnhet of(Enhet enhet) {
        return new OppfolgingEnhet(enhet.getEnhetId(), enhet.getNavn());
    }

    public static OppfolgingEnhet of(WSOrganisasjonsenhet enhet ) { return new OppfolgingEnhet(enhet.getEnhetId(), enhet.getEnhetNavn()); }
}
