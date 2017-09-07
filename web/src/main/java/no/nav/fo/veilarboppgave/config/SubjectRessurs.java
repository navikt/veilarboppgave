package no.nav.fo.veilarboppgave.config;

import no.nav.brukerdialog.security.context.SubjectHandler;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Component
@Path("/subject")
public class SubjectRessurs {

    @GET
    public String getUid() {
        return SubjectHandler.getSubjectHandler().getUid();
    }

}
