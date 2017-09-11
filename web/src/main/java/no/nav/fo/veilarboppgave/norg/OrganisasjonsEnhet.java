package no.nav.fo.veilarboppgave.norg;

import lombok.Value;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.WSOrganisasjonsenhet;

@Value
class OrganisasjonsEnhet {
    String enhetId;
    String navn;

    static OrganisasjonsEnhet of(WSOrganisasjonsenhet wsOrganisasjonsenhet) {
        return new OrganisasjonsEnhet(wsOrganisasjonsenhet.getEnhetId(), wsOrganisasjonsenhet.getEnhetNavn());
    }
}
