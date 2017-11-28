package no.nav.fo.veilarboppgave;


import lombok.extern.slf4j.Slf4j;
import no.nav.dialogarena.config.DevelopmentSecurity;
import no.nav.dialogarena.config.fasit.FasitUtils;
import no.nav.fo.veilarboppgave.config.DatabaseConfig;
import no.nav.sbl.dialogarena.common.jetty.Jetty;
import org.eclipse.jetty.plus.jndi.Resource;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.io.File;

import static java.lang.System.getProperty;
import static java.util.Arrays.stream;
import static no.nav.dialogarena.config.DevelopmentSecurity.setupISSO;
import static no.nav.fo.veilarboppgave.config.LocalJndiContextConfig.setupInMemoryDatabase;
import static no.nav.fo.veilarboppgave.config.LocalJndiContextConfig.setupOracleDataSource;
import static no.nav.sbl.dialogarena.common.jetty.Jetty.usingWar;
import static no.nav.sbl.dialogarena.common.jetty.JettyStarterUtils.*;

@Slf4j
public class StartJetty {

    public static final String APPLICATION_NAME = "veilarboppgave";
    public static final int PORT = 8940;


    public static void main(String[] args) throws Exception {

        SingleConnectionDataSource dataSource;

        if (Boolean.parseBoolean(getProperty("lokal.database", "true"))) {
            dataSource = setupInMemoryDatabase();
        } else {
            dataSource = setupOracleDataSource(FasitUtils.getDbCredentials(APPLICATION_NAME));

        }

        // TODO slett når common-jetty registerer datasource fornuftig
        new Resource(DatabaseConfig.JNDI_NAME, dataSource);


        Jetty.JettyBuilder jettyBuilder = setupISSO(usingWar()
                        .at(APPLICATION_NAME)
                        .addDatasource(dataSource, DatabaseConfig.JNDI_NAME)
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
        Jetty jetty = Jetty
                .usingWar()
                .at(APPLICATION_NAME)
                .port(PORT)
                .overrideWebXml(new File("src/test/resources/disable-security-web.xml"))
                .disableAnnotationScanning()
                .buildJetty();

        // MetaInfConfiguration førte til "java.util.zip.ZipException: error in opening zip file"
        WebAppContext context = jetty.context;
        String[] configurations = stream(context.getConfigurationClasses())
                .filter(className -> !MetaInfConfiguration.class.getName().equals(className))
                .toArray(String[]::new);
        context.setConfigurationClasses(configurations);

        return jetty.start();
    }
}