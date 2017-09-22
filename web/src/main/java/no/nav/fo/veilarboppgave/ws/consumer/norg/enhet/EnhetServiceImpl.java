package no.nav.fo.veilarboppgave.ws.consumer.norg.enhet;

import lombok.extern.slf4j.Slf4j;
import no.nav.apiapp.feil.IngenTilgang;
import no.nav.fo.veilarboppgave.domene.OppfolgingEnhet;
import no.nav.virksomhet.tjenester.enhet.meldinger.v1.WSHentEnhetListeRequest;
import no.nav.virksomhet.tjenester.enhet.v1.Enhet;
import no.nav.virksomhet.tjenester.enhet.v1.HentEnhetListeRessursIkkeFunnet;
import no.nav.virksomhet.tjenester.enhet.v1.HentEnhetListeUgyldigInput;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
public class EnhetServiceImpl implements EnhetService {

    private final Enhet soapClient;

    @Inject
    public EnhetServiceImpl(Enhet soapClient) {
        this.soapClient = soapClient;
    }

    @Override
    public List<OppfolgingEnhet> harTilgangTilEnhet(String veilederId) {
        WSHentEnhetListeRequest request = new WSHentEnhetListeRequest();
        request.setRessursId(veilederId);
        try {
            return soapClient
                    .hentEnhetListe(request)
                    .getEnhetListe()
                    .stream()
                    .map(OppfolgingEnhet::of)
                    .collect(toList());

        } catch (HentEnhetListeUgyldigInput e) {
            log.warn("Ugyldig input til virksomhetenhettjeneste: {}", e.getFaultInfo().getFeilmelding());
            throw new IngenTilgang();
        } catch (HentEnhetListeRessursIkkeFunnet e) {
            log.warn("Fant ingen enheter for veileder: {}", e.getFaultInfo().getFeilmelding());
            throw new IngenTilgang();
        }
    }
}