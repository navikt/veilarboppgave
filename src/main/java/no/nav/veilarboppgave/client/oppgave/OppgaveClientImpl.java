package no.nav.veilarboppgave.client.oppgave;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import no.nav.common.health.HealthCheckResult;
import no.nav.common.health.HealthCheckUtils;
import no.nav.common.log.MDCConstants;
import no.nav.common.rest.client.RestClient;
import no.nav.common.rest.client.RestUtils;
import no.nav.common.utils.IdUtils;
import no.nav.veilarboppgave.domain.BehandlingstemaDTO;
import no.nav.veilarboppgave.domain.Oppgave;
import no.nav.veilarboppgave.domain.OppgaveId;
import no.nav.veilarboppgave.domain.TemaDTO;
import no.nav.veilarboppgave.util.DateUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.function.Supplier;

import static no.nav.common.utils.StringUtils.of;
import static no.nav.common.utils.UrlUtils.joinPaths;
import static no.nav.veilarboppgave.util.RestUtils.bearerTokenFromSupplier;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class OppgaveClientImpl implements OppgaveClient {

    private final String oppgaveUrl;

    private final Supplier<String> userTokenSupplier;

    private final OkHttpClient client;


    public OppgaveClientImpl(String oppgaveUrl, Supplier<String> userTokenSupplier) {
        this.oppgaveUrl = oppgaveUrl;
        this.userTokenSupplier = userTokenSupplier;
        this.client = RestClient.baseClient();
    }

    @Override
    public Optional<OppgaveId> opprettOppgave(Oppgave oppgave) {
        String correlationId = of(MDC.get(MDCConstants.MDC_CALL_ID)).orElse(IdUtils.generateId());
        OpprettOppgaveRequest opprettOppgaveRequest = new OpprettOppgaveRequest()
                .setAktoerId(oppgave.getAktorId().get())
                .setTema(oppgave.getTemaDTO().getFagomradeKode())
                .setPrioritet(oppgave.getPrioritet().name())
                .setOppgavetype(oppgave.getType().getKode())
                .setBeskrivelse(oppgave.getBeskrivelse())
                .setTildeltEnhetsnr(oppgave.getEnhetId())
                .setOpprettetAvEnhetsnr(oppgave.getAvsenderenhetId())
                .setTilordnetRessurs(oppgave.getVeilederId())
                .setAktivDato(DateUtils.tilDatoStr(oppgave.getFraDato()))
                .setFristFerdigstillelse(DateUtils.tilDatoStr(oppgave.getTilDato()));

        if (oppgave.getTemaDTO() == TemaDTO.ARBEIDSAVKLARING) {
            opprettOppgaveRequest.setBehandlingstema((BehandlingstemaDTO.FERDIG_AVKLART_MOT_UFØRETRYGD.getBehandlingstema()));
        }

        Request request = new Request.Builder()
                .url(joinPaths(oppgaveUrl, "/api/v1/oppgaver"))
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, bearerTokenFromSupplier(userTokenSupplier))
                .header("X-Correlation-Id", correlationId)
                .post(RestUtils.toJsonRequestBodyWithoutNullValues(opprettOppgaveRequest))
                .build();

        try (okhttp3.Response response = client.newCall(request).execute()) {
            if (response.code() == 403) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bruker har ikke tilgang til å opprette oppgave");
            }

            if(!response.isSuccessful()) {
                log.error("Feil i request til oppgave: {} {}", response.message(), response.body() != null ? response.body().string() : "");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Feil i request til oppgave");
            }

            RestUtils.throwIfNotSuccessful(response);

            OpprettOppgaveResponse opprettOppgaveResponse = RestUtils.parseJsonResponseOrThrow(response, OpprettOppgaveResponse.class);

            return OppgaveId.of(opprettOppgaveResponse.id);
        } catch (Exception e) {
            log.error("Klarte ikke å opprette oppgave", e);
            return Optional.empty();
        }
    }

    @Override
    public HealthCheckResult checkHealth() {
        return HealthCheckUtils.pingUrl(oppgaveUrl, client);
    }

    @Data
    @Accessors(chain = true)
    private static class OpprettOppgaveRequest {
        String aktoerId;
        String tema;
        String behandlingstema;
        String oppgavetype;
        String prioritet;
        String tildeltEnhetsnr;
        String opprettetAvEnhetsnr;
        String beskrivelse;
        String tilordnetRessurs; // veileder ident
        String aktivDato; // yyyy-mm-dd (fraDato)
        String fristFerdigstillelse; // yyyy-mm-dd (tilDato)
    }

    @Data
    private static class OpprettOppgaveResponse {
        String id;
    }

}
