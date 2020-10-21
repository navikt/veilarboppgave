package no.nav.veilarboppgave.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class GeografiskTilknytning {
    String geofrafiskTilknytning;
}
