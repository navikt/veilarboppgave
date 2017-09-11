package no.nav.fo.veilarboppgave.domene;

import lombok.Value;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.WSOrganisasjonsenhet;

@Value
public class OrganisasjonsEnhet {
    String enhetId;
    String navn;

    public static OrganisasjonsEnhet of(WSOrganisasjonsenhet wsOrganisasjonsenhet) {
        return new OrganisasjonsEnhet(wsOrganisasjonsenhet.getEnhetId(), wsOrganisasjonsenhet.getEnhetNavn());
    }
}
