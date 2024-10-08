package no.nav.veilarboppgave.client.norg2;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import no.nav.common.health.HealthCheckResult;
import no.nav.common.health.HealthCheckUtils;
import no.nav.common.rest.client.RestClient;
import no.nav.common.rest.client.RestUtils;
import no.nav.common.utils.UrlUtils;
import no.nav.veilarboppgave.domain.OppfolgingEnhet;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static no.nav.common.utils.UrlUtils.joinPaths;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class Norg2ArbeidsfordelingClientImpl implements Norg2ArbeidsfordelingClient {

    private final String norg2Url;
    private final OkHttpClient client;

    public Norg2ArbeidsfordelingClientImpl(String norg2Url) {
        this.norg2Url = norg2Url;
        this.client = RestClient.baseClient();
    }

    @SneakyThrows
    @Override
    public List<OppfolgingEnhet> hentBestMatchEnheter(ArbeidsfordelingKriterier kriterier) {
        Request request = new Request.Builder()
                .url(joinPaths(norg2Url, "api/v1/arbeidsfordeling/enheter/bestmatch"))
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .post(RestUtils.toJsonRequestBody(kriterier))
                .build();

        try (okhttp3.Response response = client.newCall(request).execute()) {
            if (response.code() != 200) {
                log.error("Kunne ikke hente enheter fra arbeidsfordeling. Response status = " + response.code());
                return emptyList();
            }

            List<ArbeidsfordelingEnhet> arbeidsfordelingEnheter = RestUtils.parseJsonResponseArrayOrThrow(response, ArbeidsfordelingEnhet.class);
            return arbeidsfordelingEnheter.stream().map(OppfolgingEnhet::of).collect(toList());
        }
    }

    @Override
    public HealthCheckResult checkHealth() {
        return HealthCheckUtils.pingUrl(UrlUtils.joinPaths(this.norg2Url, "/internal/health/readiness"), this.client);
    }

}
