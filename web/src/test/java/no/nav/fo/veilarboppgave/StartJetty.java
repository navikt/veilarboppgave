package no.nav.fo.veilarboppgave;


import no.nav.dialogarena.config.DevelopmentSecurity;
import no.nav.sbl.dialogarena.common.jetty.Jetty;

import static no.nav.dialogarena.config.DevelopmentSecurity.setupISSO;
import static no.nav.sbl.dialogarena.common.jetty.Jetty.usingWar;
import static no.nav.sbl.dialogarena.common.jetty.JettyStarterUtils.*;

public class StartJetty {

    public static final String APPLICATION_NAME = "veilarboppgave";
    public static final int PORT = 8940;


    public static void main(String[] args) throws Exception {
        Jetty jetty = setupISSO(usingWar()
                        .at(APPLICATION_NAME)
                        .loadProperties("/test.properties")
                        .port(PORT)
                , new DevelopmentSecurity.ISSOSecurityConfig(APPLICATION_NAME)).buildJetty();
        jetty.startAnd(first(waitFor(gotKeypress())).then(jetty.stop));
    }

}