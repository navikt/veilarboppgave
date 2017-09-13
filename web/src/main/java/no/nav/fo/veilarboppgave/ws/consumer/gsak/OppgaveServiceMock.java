package no.nav.fo.veilarboppgave.ws.consumer.gsak;

import no.nav.fo.veilarboppgave.domene.OppgaveId;

import java.util.Optional;

public class OppgaveServiceMock implements OppgaveService{

    @Override
    public Optional<OppgaveId> opprettOppgave() {
        return Optional.of(OppgaveId.of(1));
    }
}
