package no.nav.veilarboppgave.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.env")
public class EnvironmentProperties {

    private String naisAadDiscoveryUrl;

    private String naisAadClientId;

    public String naisAadIssuer;

    private String dbUrl;

    private String poaoTilgangUrl;

    private String poaoTilgangScope;

    private String veilarbpersonUrl;

    private String veilarbpersonScope;

    private String norg2Url;

}
