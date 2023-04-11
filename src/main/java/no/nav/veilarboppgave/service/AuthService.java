package no.nav.veilarboppgave.service;

import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.nav.common.abac.Pep;
import no.nav.common.abac.domain.request.ActionId;
import no.nav.common.auth.context.AuthContextHolder;
import no.nav.common.auth.context.AuthContextHolderThreadLocal;
import no.nav.common.client.aktoroppslag.AktorOppslagClient;
import no.nav.common.types.identer.AktorId;
import no.nav.common.types.identer.Fnr;
import no.nav.poao_tilgang.client.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final Pep veilarbPep;

    private final AktorOppslagClient aktorOppslagClient;
    private final AuthContextHolder authContextHolder;
    private final PoaoTilgangClient poaoTilgangClient;
    private final UnleashService unleashService;

    public Decision harTilgangTilPersonPoaoTilgang(Fnr fnr) {
        return poaoTilgangClient.evaluatePolicy(new NavAnsattTilgangTilEksternBrukerPolicyInput(
                hentInnloggetVeilederUUID(authContextHolder),
                TilgangType.LESE,
                fnr.get())
        ).getOrThrow();
    }
    public void sjekkLesetilgangMedAktorId(AktorId aktorId) {
        if (unleashService.isPoaoTilgangEnabled()) {
            var decision = harTilgangTilPersonPoaoTilgang(getFnrOrThrow(aktorId));
            if (decision.isDeny()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else {
            if (!veilarbPep.harTilgangTilPerson(getInnloggetBrukerToken(), ActionId.READ, aktorId)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }
    }

    public AktorId getAktorIdOrThrow(Fnr fnr) {
        return aktorOppslagClient.hentAktorId(fnr);
    }

    public Fnr getFnrOrThrow(AktorId aktorId){
        return aktorOppslagClient.hentFnr(aktorId);
    }
    public String getInnloggetBrukerToken() {
        return authContextHolder.getIdTokenString().orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Fant ikke token for innlogget bruker"));
    }

    // NAV ident, fnr eller annen ID
    public String getInnloggetBrukerIdent() {
        return AuthContextHolderThreadLocal.instance()
                .getUid()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Id is missing from subject"));
    }

    public static UUID hentInnloggetVeilederUUID(AuthContextHolder authContextHolder) {
        return authContextHolder.getIdTokenClaims()
                .flatMap(claims -> getStringClaimOrEmpty(claims, "oid"))
                .map(UUID::fromString)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Fant ikke oid for innlogget veileder"));
    }

    public static Optional<String> getStringClaimOrEmpty(JWTClaimsSet claims, String claimName) {
        try {
            return ofNullable(claims.getStringClaim(claimName));
        } catch (Exception e) {
            return empty();
        }
    }
}

