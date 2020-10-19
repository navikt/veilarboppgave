package no.nav.veilarboppgave.config;

import no.nav.common.client.aktorregister.AktorregisterClient;
import no.nav.common.client.aktorregister.AktorregisterHttpClient;
import no.nav.common.client.aktorregister.CachedAktorregisterClient;
import no.nav.common.sts.SystemUserTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static no.nav.veilarboppgave.config.ApplicationConfig.APPLICATION_NAME;

@Configuration
public class ClientConfig {

    @Bean
    public AktorregisterClient aktorregisterClient(EnvironmentProperties properties, SystemUserTokenProvider systemUserTokenProvider) {
        AktorregisterClient aktorregisterClient = new AktorregisterHttpClient(
                properties.getAktorregisterUrl(), APPLICATION_NAME, systemUserTokenProvider::getSystemUserToken
        );
        return new CachedAktorregisterClient(aktorregisterClient);
    }


}
