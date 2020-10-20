package no.nav.veilarboppgave.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.nav.common.abac.AbacUtils;
import no.nav.common.abac.Pep;
import no.nav.common.abac.XacmlResponseParser;
import no.nav.common.abac.constants.AbacDomain;
import no.nav.common.abac.constants.NavAttributter;
import no.nav.common.abac.constants.StandardAttributter;
import no.nav.common.abac.domain.Attribute;
import no.nav.common.abac.domain.request.*;
import no.nav.common.auth.context.AuthContextHolder;
import no.nav.common.client.aktorregister.AktorregisterClient;
import no.nav.common.types.identer.AktorId;
import no.nav.common.types.identer.Fnr;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final Pep veilarbPep;

    private final AktorregisterClient aktorregisterClient;

    public void sjekkLesetilgangMedAktorId(AktorId aktorId) {
        if (!veilarbPep.harTilgangTilPerson(getInnloggetBrukerToken(), ActionId.READ, aktorId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    public AktorId getAktorIdOrThrow(Fnr fnr) {
        return aktorregisterClient.hentAktorId(fnr);
    }

    public String getInnloggetBrukerToken() {
        return AuthContextHolder.getIdTokenString().orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Fant ikke token for innlogget bruker"));
    }

    // NAV ident, fnr eller annen ID
    public String getInnloggetBrukerIdent() {
        return AuthContextHolder.getSubject().orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "NAV ident is missing"));
    }

}
