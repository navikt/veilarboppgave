package no.nav.fo.veilarboppgave;


import lombok.extern.slf4j.Slf4j;
import no.nav.dialogarena.config.DevelopmentSecurity;
import no.nav.sbl.dialogarena.common.jetty.Jetty;

import java.io.File;

import static no.nav.dialogarena.config.DevelopmentSecurity.setupISSO;
import static no.nav.sbl.dialogarena.common.jetty.Jetty.usingWar;
import static no.nav.sbl.dialogarena.common.jetty.JettyStarterUtils.*;

@Slf4j
public class StartJetty {

    public static final String APPLICATION_NAME = "veilarboppgave";
    public static final int PORT = 8940;


    public static void main(String[] args) throws Exception {


        Jetty.JettyBuilder jettyBuilder = setupISSO(usingWar()
                        .at(APPLICATION_NAME)
                        .loadProperties("/test.properties")
                        .port(PORT)
                , new DevelopmentSecurity.ISSOSecurityConfig(APPLICATION_NAME));

        boolean disableMocks = Boolean.parseBoolean(System.getProperty("integrations.mocks.disable"));
        if (disableMocks) {
            log.warn("-------------------------------------------------------------------------");
            log.warn("SKRUR AV MOCKING AV EKSTERNE TJENESTER! INTEGRASJONER GÅR MOT TESTMILJØ");
            log.warn("-------------------------------------------------------------------------");
        } else {
            jettyBuilder.overrideWebXml();
        }

        Jetty jetty = jettyBuilder.buildJetty();
        jetty.startAnd(first(waitFor(gotKeypress())).then(jetty.stop));
    }

    public static Jetty startJettyUtenSikkerhet() {
        return Jetty
                .usingWar()
                .at(APPLICATION_NAME)
                .port(PORT)
                .overrideWebXml(new File("src/test/resources/disable-security-web.xml"))
                .disableAnnotationScanning()
                .buildJetty()
                .start();
    }
}