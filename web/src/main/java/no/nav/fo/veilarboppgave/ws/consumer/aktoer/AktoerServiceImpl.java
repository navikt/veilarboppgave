package no.nav.fo.veilarboppgave.ws.consumer.aktoer;


import io.vavr.control.Try;
import no.nav.fo.veilarboppgave.domene.Aktoerid;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.tjeneste.virksomhet.aktoer.v2.AktoerV2;
import no.nav.tjeneste.virksomhet.aktoer.v2.meldinger.WSHentAktoerIdForIdentRequest;
import no.nav.tjeneste.virksomhet.aktoer.v2.meldinger.WSHentAktoerIdForIdentResponse;
import no.nav.tjeneste.virksomhet.aktoer.v2.meldinger.WSHentIdentForAktoerIdRequest;
import no.nav.tjeneste.virksomhet.aktoer.v2.meldinger.WSHentIdentForAktoerIdResponse;


public class AktoerServiceImpl implements AktoerService {

    private final AktoerV2 soapService;

    public AktoerServiceImpl(AktoerV2 aktoerV2) {
        this.soapService = aktoerV2;
    }

    public Try<Aktoerid> hentAktoeridFraFnr(Fnr fnr) {
        return Try.of(() -> soapService.hentAktoerIdForIdent(new WSHentAktoerIdForIdentRequest().withIdent(fnr.getFnr())))
                .map(WSHentAktoerIdForIdentResponse::getAktoerId)
                .map(Aktoerid::of);
    }

    public Try<Fnr> hentFnrViaFraAktoerid(Aktoerid aktoerId) {
        WSHentIdentForAktoerIdRequest soapRequest = new WSHentIdentForAktoerIdRequest().withAktoerId(aktoerId.getAktoerid());
        return Try.of(() -> soapService.hentIdentForAktoerId(soapRequest))
                        .map(WSHentIdentForAktoerIdResponse::getIdent)
                        .map(Fnr::of);
    }
}
