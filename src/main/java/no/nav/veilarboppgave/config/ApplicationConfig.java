package no.nav.veilarboppgave.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import no.nav.common.auth.context.AuthContextHolder;
import no.nav.common.auth.context.AuthContextHolderThreadLocal;
import no.nav.common.rest.client.RestClient;
import no.nav.common.token_client.builder.AzureAdTokenClientBuilder;
import no.nav.common.token_client.client.AzureAdMachineToMachineTokenClient;
import no.nav.common.token_client.client.AzureAdOnBehalfOfTokenClient;
import no.nav.poao_tilgang.api.dto.response.TilgangsattributterResponse;
import no.nav.poao_tilgang.client.*;
import no.nav.veilarboppgave.util.DbUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@EnableConfigurationProperties({EnvironmentProperties.class})
@Configuration
public class ApplicationConfig {

    private final Cache<PolicyInput, Decision> policyInputToDecisionCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(30))
            .build();
    private final Cache<UUID, List<AdGruppe>> navAnsattIdToAzureAdGrupperCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(30))
            .build();
    private final Cache<String, Boolean> norskIdentToErSkjermetCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(30))
            .build();
    private final Cache<String, TilgangsattributterResponse> tilgangsAttributterCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(30))
            .build();

    @Bean
    public AzureAdMachineToMachineTokenClient azureAdMachineToMachineTokenClient() {
        return AzureAdTokenClientBuilder.builder()
                .withNaisDefaults()
                .buildMachineToMachineTokenClient();
    }

    @Bean
    public AuthContextHolder authContextHolder() {
        return AuthContextHolderThreadLocal.instance();
    }

    @Bean
    public AzureAdOnBehalfOfTokenClient azureAdOnBehalfOfTokenClient(){
        return AzureAdTokenClientBuilder.builder()
                .withNaisDefaults()
                .buildOnBehalfOfTokenClient();
    }

    @Bean
    public DataSource dataSource(EnvironmentProperties properties) {
        return DbUtils.createDataSource(properties.getDbUrl());
    }

    @Bean
    public PoaoTilgangClient poaoTilgangClient(EnvironmentProperties properties, AzureAdMachineToMachineTokenClient tokenClient) {
        return new PoaoTilgangCachedClient(
                new PoaoTilgangHttpClient(
                        properties.getPoaoTilgangUrl(),
                        () -> tokenClient.createMachineToMachineToken(properties.getPoaoTilgangScope()),
                        RestClient.baseClient()
                ),
                policyInputToDecisionCache,
                navAnsattIdToAzureAdGrupperCache,
                norskIdentToErSkjermetCache,
                tilgangsAttributterCache
        );
    }
}
