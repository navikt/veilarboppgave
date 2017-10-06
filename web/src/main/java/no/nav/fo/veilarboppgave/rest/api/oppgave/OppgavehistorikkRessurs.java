package no.nav.fo.veilarboppgave.rest.api.oppgave;

import no.nav.fo.veilarboppgave.db.OppgaveRepository;
import no.nav.fo.veilarboppgave.db.OppgavehistorikkDTO;
import no.nav.fo.veilarboppgave.domene.Aktoerid;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.ws.consumer.aktoer.AktoerService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/oppgavehistorikk")
public class OppgavehistorikkRessurs {

    @Inject
    private AktoerService aktoerService;

    @Inject
    private OppgaveRepository oppgaveRepository;

    @GET
    public List<Oppgavehistorikk> getOppgavehistorikk(@QueryParam("fnr") String fnr) {
        Aktoerid aktoerid = aktoerService.hentAktoeridFraFnr(Fnr.of(fnr)).getOrElseThrow(() -> new NotFoundException());
        List<OppgavehistorikkDTO> oppgavehistorikk = oppgaveRepository.hentOppgavehistorikkForBruker(aktoerid);
        return oppgavehistorikk.stream().map(Oppgavehistorikk::of).collect(toList());
    }
}
