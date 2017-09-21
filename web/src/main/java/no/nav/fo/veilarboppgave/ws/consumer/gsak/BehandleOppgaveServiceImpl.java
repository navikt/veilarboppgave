package no.nav.fo.veilarboppgave.ws.consumer.gsak;

import lombok.extern.slf4j.Slf4j;
import no.nav.fo.veilarboppgave.domene.OppgaveId;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveDTO;
import no.nav.tjeneste.virksomhet.behandleoppgave.v1.BehandleOppgaveV1;

import javax.inject.Inject;
import java.util.Optional;

@Slf4j
public class BehandleOppgaveServiceImpl implements BehandleOppgaveService {

    private BehandleOppgaveV1 soapClient;

    @Inject
    public BehandleOppgaveServiceImpl(BehandleOppgaveV1 soapClient) {
        this.soapClient = soapClient;
    }

    @Override
    public Optional<OppgaveId> opprettOppgave(OppgaveDTO oppgave) {
        return Optional.of(OppgaveId.of(1));
    }
}
