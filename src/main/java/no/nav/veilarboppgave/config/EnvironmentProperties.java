package no.nav.veilarboppgave.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.env")
public class EnvironmentProperties {

    private String openAmDiscoveryUrl;

    private String veilarbloginOpenAmClientId;

    private String openAmRefreshUrl;

    private String naisAadDiscoveryUrl;

    private String naisAadClientId;

    private String naisStsDiscoveryUrl;

    private String naisStsClientId;


    private String abacUrl;

    private String norg2Url;

    private String aktorregisterUrl;

    private String soapStsUrl;

    private String dbUrl;

}
