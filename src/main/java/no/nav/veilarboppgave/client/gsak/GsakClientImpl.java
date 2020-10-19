package no.nav.veilarboppgave.client.gsak;

import lombok.extern.slf4j.Slf4j;
import no.nav.common.cxf.CXFClient;
import no.nav.common.cxf.StsConfig;
import no.nav.common.health.HealthCheckResult;
import no.nav.tjeneste.virksomhet.behandleoppgave.v1.BehandleOppgaveV1;
import no.nav.tjeneste.virksomhet.behandleoppgave.v1.WSSikkerhetsbegrensningException;
import no.nav.tjeneste.virksomhet.behandleoppgave.v1.meldinger.*;
import no.nav.veilarboppgave.domain.Oppgave;
import no.nav.veilarboppgave.domain.OppgaveId;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Optional;

@Slf4j
public class GsakClientImpl implements GsakClient {

    private final BehandleOppgaveV1 behandleOppgaveV1;

    private final BehandleOppgaveV1 behandleOppgaveV1Ping;

    public GsakClientImpl(String behandleOppgaveV1Endpoint, StsConfig stsConfig) {
        behandleOppgaveV1 = new CXFClient<>(BehandleOppgaveV1.class)
                .address(behandleOppgaveV1Endpoint)
                .configureStsForSubject(stsConfig)
                .build();

        behandleOppgaveV1Ping = new CXFClient<>(BehandleOppgaveV1.class)
                .address(behandleOppgaveV1Endpoint)
                .configureStsForSystemUser(stsConfig)
                .build();
    }

    @Override
    public Optional<OppgaveId> opprettOppgave(Oppgave oppgave) {
        try {
            WSOppgave opprettOppgave = new WSOppgave();
            XMLGregorianCalendar tilDato = DatatypeFactory.newInstance().newXMLGregorianCalendar(oppgave.getTilDato().toString());
            XMLGregorianCalendar fraDato = DatatypeFactory.newInstance().newXMLGregorianCalendar(oppgave.getFraDato().toString());

            WSAktor aktor = new WSAktor().withAktorType(WSAktorType.PERSON).withIdent(oppgave.getFnr().getFnr());

            opprettOppgave.setGjelderBruker(aktor);
            opprettOppgave.setFagomradeKode(oppgave.getTemaDTO().getFagomradeKode());
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

            WSOpprettOppgaveResponse response = behandleOppgaveV1.opprettOppgave(request);
            return OppgaveId.of(response.getOppgaveId());

        } catch (DatatypeConfigurationException e) {
            log.warn("Feil med typekonfigurasjon ut mot GSAK-tjeneste: {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (WSSikkerhetsbegrensningException e) {
            log.warn("Kunne ikke opprette oppgave pga sikkerhetsbegrensning i GSAK: {} ", e.getFaultInfo());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public HealthCheckResult checkHealth() {
        try {
            behandleOppgaveV1Ping.ping();
            return HealthCheckResult.healthy();
        } catch (Exception e) {
            return HealthCheckResult.unhealthy("Failed to ping BehandleOppgaveV1", e);
        }
    }
}
