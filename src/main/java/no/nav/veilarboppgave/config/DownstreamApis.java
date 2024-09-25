package no.nav.veilarboppgave.config;

import no.nav.veilarboppgave.util.DownstreamApi;

public class DownstreamApis {
    public static DownstreamApi downstreamVeilarbperson(String cluster){
        return new DownstreamApi(cluster, "obo", "veilarbperson");
    }

    public static DownstreamApi downstreamOppgave(String cluster){
        return new DownstreamApi(cluster,  "oppgavehandtering", ("dev-fss".equals(cluster)) ? "oppgave-q1" : "oppgave");
    }
}
