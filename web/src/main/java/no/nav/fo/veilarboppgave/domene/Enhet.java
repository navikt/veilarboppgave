package no.nav.fo.veilarboppgave.domene;

import lombok.Value;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.WSOrganisasjonsenhet;

@Value(staticConstructor = "of")
public class Enhet {
    String enhetId;
    String navn;

    public static Enhet of(WSOrganisasjonsenhet wsOrganisasjonsenhet) {
        return new Enhet(wsOrganisasjonsenhet.getEnhetId(), wsOrganisasjonsenhet.getEnhetNavn());
    }
}
