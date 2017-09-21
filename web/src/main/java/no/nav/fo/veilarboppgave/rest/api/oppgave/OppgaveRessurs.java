package no.nav.fo.veilarboppgave.rest.api.oppgave;

import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.domene.OppgaveId;
import no.nav.fo.veilarboppgave.domene.Prioritet;
import no.nav.fo.veilarboppgave.domene.Tema;
import no.nav.fo.veilarboppgave.rest.api.Valider;
import no.nav.fo.veilarboppgave.security.abac.PepClient;
import no.nav.fo.veilarboppgave.ws.consumer.gsak.BehandleOppgaveService;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.time.LocalDate;

import static java.util.Optional.ofNullable;

@Path("/oppgave")
public class OppgaveRessurs {

    private final BehandleOppgaveService oppgaveService;
    private final PepClient pepClient;

    @Inject
    public OppgaveRessurs(BehandleOppgaveService oppgaveService, PepClient pepClient) {
        this.oppgaveService = oppgaveService;
        this.pepClient = pepClient;
    }

    @POST
    public OppgaveId opprettOppgave(OppgaveDTO dto) {

        Fnr fnr = ofNullable(dto.getFnr())
                .map(Valider::fnr)
                .map(pepClient::sjekkTilgangTilFnr)
                .orElseThrow(RuntimeException::new);

        Valider.fraTilDato(dto);
        LocalDate fraDato = Valider.dato(dto.getFraDato());
        LocalDate tilDato = Valider.dato(dto.getTilDato());

        Tema tema = Valider.tema(dto.getTema());
        Prioritet prioritet = Valider.prioritet(dto.getPrioritet());
        Valider.obligatoriskeFelter(dto);

        Oppgave oppgave = new Oppgave(
                fnr,
                tema,
                dto.getType(),
                prioritet,
                dto.getBeskrivelse(),
                fraDato,
                tilDato,
                dto.getEnhet(),
                dto.getVeileder()
        );

        return oppgaveService
                .opprettOppgave(oppgave)
                .orElseThrow(NotFoundException::new);
    }
}
