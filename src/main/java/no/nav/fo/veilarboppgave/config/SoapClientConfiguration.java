package no.nav.fo.veilarboppgave.config;

import no.nav.sbl.dialogarena.common.cxf.CXFClient;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.ArbeidsfordelingV1;
import no.nav.tjeneste.virksomhet.behandleoppgave.v1.BehandleOppgaveV1;
import no.nav.tjeneste.virksomhet.organisasjonenhet.v2.OrganisasjonEnhetV2;
import no.nav.tjeneste.virksomhet.person.v3.PersonV3;
import no.nav.virksomhet.tjenester.enhet.v1.Enhet;

public class SoapClientConfiguration {

    public static PersonV3 personV3WithSystemUser() {
        return new CXFClient<>(PersonV3.class)
                .address(System.getProperty("personV3.endpoint.url"))
                .configureStsForSystemUser()
                .build();
    }

    public static PersonV3 personV3OnBehalfOfUser() {
        return new CXFClient<>(PersonV3.class)
                .address(System.getProperty("personV3.endpoint.url"))
                .configureStsForOnBehalfOfWithJWT()
                .build();
    }

    public static ArbeidsfordelingV1 arbeidsfordelingV1OnBehalfOfUser() {
        return new CXFClient<>(ArbeidsfordelingV1.class)
                .address(System.getProperty("arbeidsfordelingV1.endpoint.url"))
                .configureStsForOnBehalfOfWithJWT()
                .build();
    }

    public static ArbeidsfordelingV1 arbeidsfordelingV1WithSystemUser() {
        return new CXFClient<>(ArbeidsfordelingV1.class)
                .address(System.getProperty("arbeidsfordelingV1.endpoint.url"))
                .configureStsForSystemUser()
                .build();
    }

    public static BehandleOppgaveV1 behandleOppgaveV1OnBehalfOfUser() {
        return new CXFClient<>(BehandleOppgaveV1.class)
                .address(System.getProperty("behandleoppgaveV1.endpoint.url"))
                .configureStsForOnBehalfOfWithJWT()
                .build();
    }

    public static BehandleOppgaveV1 behandleOppgaveV1WithSystemUser() {
        return new CXFClient<>(BehandleOppgaveV1.class)
                .address(System.getProperty("behandleoppgaveV1.endpoint.url"))
                .configureStsForSystemUser()
                .build();
    }

    public static Enhet virksomhetenhetOnBehalfOfSystemUser() {
        return new CXFClient<>(Enhet.class)
                .address(System.getProperty("norg.virksomhet_enhet.url"))
                .configureStsForSystemUser()
                .build();
    }

    public static Enhet virksomhetenhetOnBehalfOfUser() {
        return new CXFClient<>(Enhet.class)
                .address(System.getProperty("norg.virksomhet_enhet.url"))
                .configureStsForOnBehalfOfWithJWT()
                .build();
    }

    public static OrganisasjonEnhetV2 organisasjonenhetOnBehalfOfSystemUser() {
        return new CXFClient<>(OrganisasjonEnhetV2.class)
                .address(System.getProperty("organisasjonEnhetV2.url"))
                .configureStsForSystemUser()
                .build();
    }

    public static OrganisasjonEnhetV2 organisasjonenhetOnBehalfOfUser() {
        return new CXFClient<>(OrganisasjonEnhetV2.class)
                .address(System.getProperty("organisasjonEnhetV2.url"))
                .configureStsForOnBehalfOfWithJWT()
                .build();
    }
}
