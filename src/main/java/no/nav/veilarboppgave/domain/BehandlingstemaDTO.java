package no.nav.veilarboppgave.domain;

public enum BehandlingstemaDTO {
    FERDIG_AVKLART_MOT_UFØRETRYGD("ab0532");

    private String behandlingstema;

    BehandlingstemaDTO(String behandlingstema) {
        this.behandlingstema = behandlingstema;
    }
  public String getBehandlingstema() {
        return behandlingstema;
  }
}
