package no.nav.fo.veilarboppgave.ws.consumer.norg.organisasjonenhet;


import lombok.SneakyThrows;
import no.nav.fo.veilarboppgave.domene.OppfolgingEnhet;
import no.nav.tjeneste.virksomhet.organisasjonenhet.v2.OrganisasjonEnhetV2;
import no.nav.tjeneste.virksomhet.organisasjonenhet.v2.meldinger.WSHentFullstendigEnhetListeRequest;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class OrganisasjonEnhetServiceImpl implements OrganisasjonEnhetService {

    private final OrganisasjonEnhetV2 soapClient;

    @Inject
    public OrganisasjonEnhetServiceImpl(OrganisasjonEnhetV2 organisasjonEnhetV2) {
        this.soapClient = organisasjonEnhetV2;
    }

    @Override
    @SneakyThrows
    public List<OppfolgingEnhet> hentAlleEnheter() {
        return soapClient.hentFullstendigEnhetListe(new WSHentFullstendigEnhetListeRequest()).getEnhetListe().stream()
                .map(OppfolgingEnhet::of)
                .collect(toList());

    }
}
