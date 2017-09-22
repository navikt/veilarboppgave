package no.nav.fo.veilarboppgave.ws.consumer.gsak;

import lombok.extern.slf4j.Slf4j;
import no.nav.apiapp.feil.IngenTilgang;
import no.nav.fo.veilarboppgave.domene.OppgaveId;
import no.nav.fo.veilarboppgave.rest.api.oppgave.Oppgave;
import no.nav.tjeneste.virksomhet.behandleoppgave.v1.BehandleOppgaveV1;
import no.nav.tjeneste.virksomhet.behandleoppgave.v1.WsSikkerhetsbegrensning;
import no.nav.tjeneste.virksomhet.behandleoppgave.v1.meldinger.OpprettOppgaveRequest;
import no.nav.tjeneste.virksomhet.behandleoppgave.v1.meldinger.OpprettOppgaveResponse;
import no.nav.tjeneste.virksomhet.behandleoppgave.v1.meldinger.WSOpprettOppgave;

import javax.inject.Inject;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Optional;

@Slf4j
public class BehandleOppgaveServiceImpl implements BehandleOppgaveService {

    private BehandleOppgaveV1 soapClient;

    @Inject
    public BehandleOppgaveServiceImpl(BehandleOppgaveV1 soapClient) {
        this.soapClient = soapClient;
    }

    @Override
    public Optional<OppgaveId> opprettOppgave(Oppgave oppgave) {
        try {
            WSOpprettOppgave opprettOppgave = new WSOpprettOppgave();
            XMLGregorianCalendar tilDato = DatatypeFactory.newInstance().newXMLGregorianCalendar(oppgave.getTilDato().toString());
            XMLGregorianCalendar fraDato = DatatypeFactory.newInstance().newXMLGregorianCalendar(oppgave.getFraDato().toString());

            opprettOppgave.setFagomradeKode(oppgave.getTema().getFagomradeKode());
            opprettOppgave.setAktivFra(fraDato);
            opprettOppgave.setAktivTil(tilDato);
            opprettOppgave.setBeskrivelse(oppgave.getBeskrivelse());
            opprettOppgave.setPrioritetKode(oppgave.getPrioritet());
            opprettOppgave.setAnsvarligEnhetId(oppgave.getEnhetId());
            opprettOppgave.setAnsvarligId(oppgave.getVeilederId());
            opprettOppgave.setOppgavetypeKode(oppgave.getType());
            opprettOppgave.setLest(false);

            OpprettOppgaveRequest request = new OpprettOppgaveRequest();
            request.setOpprettetAvEnhetId(Integer.parseInt(oppgave.getAvsenderenhetId()));
            request.setWsOpprettOppgave(opprettOppgave);

            OpprettOppgaveResponse response = soapClient.opprettOppgave(request);
            return OppgaveId.of(response.getOppgaveId());

        } catch (DatatypeConfigurationException e) {
            log.warn("Feil med typekonfigurasjon ut mot GSAK-tjeneste: {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (WsSikkerhetsbegrensning e) {
            log.warn("Kunne ikke opprette oppgave pga sikkerhetsbegrensning i GSAK: {} ", e.getFaultInfo());
            throw new IngenTilgang();
        }
    }
}
