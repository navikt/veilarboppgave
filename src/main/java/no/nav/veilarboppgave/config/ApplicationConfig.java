package no.nav.veilarboppgave.config;

import no.nav.common.abac.Pep;
import no.nav.common.abac.VeilarbPep;
import no.nav.common.abac.audit.SpringAuditRequestInfoSupplier;
import no.nav.common.cxf.StsConfig;
import no.nav.common.sts.NaisSystemUserTokenProvider;
import no.nav.common.sts.SystemUserTokenProvider;
import no.nav.common.utils.Credentials;
import no.nav.common.utils.NaisUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static no.nav.common.utils.NaisUtils.getCredentials;

@EnableConfigurationProperties({EnvironmentProperties.class})
@Configuration
public class ApplicationConfig {

    static final String APPLICATION_NAME = "veilarboppgave";

    @Bean
    public Credentials serviceUserCredentials() {
        return getCredentials("service_user");
    }

    @Bean
    public SystemUserTokenProvider systemUserTokenProvider(EnvironmentProperties properties, Credentials serviceUserCredentials) {
        return new NaisSystemUserTokenProvider(properties.getNaisStsDiscoveryUrl(), serviceUserCredentials.username, serviceUserCredentials.password);
    }

    @Bean
    public static StsConfig stsConfig(EnvironmentProperties properties, Credentials serviceUserCredentials) {
        return StsConfig.builder()
                .url(properties.getSoapStsUrl())
                .username(serviceUserCredentials.username)
                .password(serviceUserCredentials.password)
                .build();
    }

    @Bean
    public Pep veilarbPep(EnvironmentProperties properties) {
        Credentials serviceUserCredentials = NaisUtils.getCredentials("service_user");
        return new VeilarbPep(
                properties.getAbacUrl(), serviceUserCredentials.username,
                serviceUserCredentials.password, new SpringAuditRequestInfoSupplier()
        );
    }

}