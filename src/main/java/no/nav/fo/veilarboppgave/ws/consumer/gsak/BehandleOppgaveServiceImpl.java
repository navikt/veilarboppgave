package no.nav.fo.veilarboppgave.ws.consumer.gsak;

import lombok.extern.slf4j.Slf4j;
import no.nav.apiapp.feil.IngenTilgang;
import no.nav.fo.veilarboppgave.domene.OppgaveId;
import no.nav.fo.veilarboppgave.rest.api.oppgave.Oppgave;
import no.nav.tjeneste.virksomhet.behandleoppgave.v1.BehandleOppgaveV1;
import no.nav.tjeneste.virksomhet.behandleoppgave.v1.WSSikkerhetsbegrensningException;
import no.nav.tjeneste.virksomhet.behandleoppgave.v1.meldinger.*;

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
            WSOppgave opprettOppgave = new WSOppgave();
            XMLGregorianCalendar tilDato = DatatypeFactory.newInstance().newXMLGregorianCalendar(oppgave.getTilDato().toString());
            XMLGregorianCalendar fraDato = DatatypeFactory.newInstance().newXMLGregorianCalendar(oppgave.getFraDato().toString());

            WSAktor aktor = new WSAktor().withAktorType(WSAktorType.PERSON).withIdent(oppgave.getFnr().getFnr());

            opprettOppgave.setGjelderBruker(aktor);
            opprettOppgave.setFagomradeKode(oppgave.getTema().getFagomradeKode());
            opprettOppgave.setAktivFra(fraDato);
            opprettOppgave.setAktivTil(tilDato);
            opprettOppgave.setBeskrivelse(oppgave.getBeskrivelse());
            opprettOppgave.setPrioritetKode(oppgave.getPrioritet());
            opprettOppgave.setAnsvarligEnhetId(oppgave.getEnhetId());
            opprettOppgave.setAnsvarligId(oppgave.getVeilederId());
            opprettOppgave.setOppgavetypeKode(oppgave.getType());
            opprettOppgave.setLest(false);

            WSOpprettOppgaveRequest request = new WSOpprettOppgaveRequest();
            request.setOpprettetAvEnhetId(Integer.parseInt(oppgave.getAvsenderenhetId()));
            request.setWsOppgave(opprettOppgave);

            WSOpprettOppgaveResponse response = soapClient.opprettOppgave(request);
            return OppgaveId.of(response.getOppgaveId());

        } catch (DatatypeConfigurationException e) {
            log.warn("Feil med typekonfigurasjon ut mot GSAK-tjeneste: {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (WSSikkerhetsbegrensningException e) {
            log.warn("Kunne ikke opprette oppgave pga sikkerhetsbegrensning i GSAK: {} ", e.getFaultInfo());
            throw new IngenTilgang();
        }
    }
}
