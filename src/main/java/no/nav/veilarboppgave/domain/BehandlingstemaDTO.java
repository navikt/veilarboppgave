package no.nav.veilarboppgave.domain;

import lombok.Getter;

@Getter
public enum BehandlingstemaDTO {
    FERDIG_AVKLART_MOT_UFØRETRYGD("ab0532");

    private final String behandlingstema;

    BehandlingstemaDTO(String behandlingstema) {
        this.behandlingstema = behandlingstema;
    }
}
