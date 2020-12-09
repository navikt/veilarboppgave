package no.nav.veilarboppgave.client.veilarbperson;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import no.nav.common.health.HealthCheckResult;
import no.nav.common.health.HealthCheckUtils;
import no.nav.common.rest.client.RestClient;
import no.nav.common.rest.client.RestUtils;
import no.nav.common.types.identer.Fnr;
import no.nav.common.utils.UrlUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Optional;
import java.util.function.Supplier;

import static no.nav.common.utils.UrlUtils.joinPaths;
import static no.nav.veilarboppgave.util.RestUtils.bearerTokenFromSupplier;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class VeilarbpersonClientImpl implements VeilarbpersonClient {

    private final String veilarbpersonUrl;

    private final Supplier<String> userTokenSupplier;

    private final OkHttpClient client;

    public VeilarbpersonClientImpl(String veilarbpersonUrl, Supplier<String> userTokenSupplier) {
        this.veilarbpersonUrl = veilarbpersonUrl;
        this.userTokenSupplier = userTokenSupplier;
        this.client = RestClient.baseClient();
    }

    @SneakyThrows
    @Override
    public Personalia hentPersonalia(Fnr fnr) {
        Request request = new Request.Builder()
                .url(joinPaths(veilarbpersonUrl, "api/person", fnr.get()))
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, bearerTokenFromSupplier(userTokenSupplier))
                .build();

        try (okhttp3.Response response = client.newCall(request).execute()) {
            RestUtils.throwIfNotSuccessful(response);
            return RestUtils.parseJsonResponseOrThrow(response, Personalia.class);
        }
    }

    @Override
    public Optional<String> hentGeografiskTilknytning(Fnr fnr) {
        Request request = new Request.Builder()
                .url(joinPaths(veilarbpersonUrl, "api/person/geografisktilknytning?fnr=" + fnr.get()))
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, bearerTokenFromSupplier(userTokenSupplier))
                .build();

        try (okhttp3.Response response = client.newCall(request).execute()) {
            RestUtils.throwIfNotSuccessful(response);
            GeografiskTilknytningResponse geografiskTilknytningResponse = RestUtils.parseJsonResponseOrThrow(response, GeografiskTilknytningResponse.class);
            return Optional.ofNullable(geografiskTilknytningResponse.geografiskTilknytning);
        } catch (Exception e) {
            log.error("Klarte ikke å hente geografisk tilknytning", e);
            return Optional.empty();
        }
    }

    @Override
    public HealthCheckResult checkHealth() {
        return HealthCheckUtils.pingUrl(UrlUtils.joinPaths(this.veilarbpersonUrl, "/internal/isAlive"), this.client);
    }

    @Data
    private static class GeografiskTilknytningResponse {
        String geografiskTilknytning;
    }

}
