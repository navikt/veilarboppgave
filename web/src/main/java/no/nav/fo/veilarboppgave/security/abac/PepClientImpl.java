package no.nav.fo.veilarboppgave.security.abac;

import lombok.SneakyThrows;
import no.nav.apiapp.feil.IngenTilgang;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.sbl.dialogarena.common.abac.pep.Pep;
import no.nav.sbl.dialogarena.common.abac.pep.domain.ResourceType;
import no.nav.sbl.dialogarena.common.abac.pep.domain.request.Action;
import no.nav.sbl.dialogarena.common.abac.pep.domain.response.Decision;

import javax.inject.Inject;

public class PepClientImpl implements PepClient {

    Pep pep;

    @Inject
    public PepClientImpl(Pep pep) {
        this.pep = pep;
    }

    @Override
    @SneakyThrows
    public Fnr sjekkTilgangTilFnr(Fnr fnr) {
        if (Decision.Permit == pep.harInnloggetBrukerTilgangTilPerson(fnr.getFnr(), "veilarb", Action.ActionId.READ, ResourceType.VeilArbPerson).getBiasedDecision()) {
            return fnr;
        } else {
            throw new IngenTilgang();
        }
    }
}
