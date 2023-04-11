package no.nav.veilarboppgave.utils;

import com.nimbusds.jwt.JWTClaimsSet;
import lombok.SneakyThrows;
import no.nav.common.types.identer.Fnr;
import no.nav.common.types.identer.NavIdent;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static java.util.stream.IntStream.range;

public class TestUtils {

    public static String lagStringAvLengde(int lengde) {
        StringBuilder builder = new StringBuilder();
        range(0, lengde).forEach(
                x -> builder.append("X")
        );
        return builder.toString();
    }

    @SneakyThrows
    public static String readTestResourceFile(String fileName) {
        URL fileUrl = TestUtils.class.getClassLoader().getResource(fileName);
        Path resPath = Paths.get(fileUrl.toURI());
        return Files.readString(resPath);
    }

    public static JWTClaimsSet getJwtClaimsSet(NavIdent navIdent, Fnr FNR) {
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer("microsoftonline.com")
                .claim("azp_name", "cluster:team:veilarbregistrering")
                .claim("roles", Collections.singletonList("access_as_application"))
                .claim("NAVident", navIdent.get())
                .claim("acr", "Level4")
                .claim("oid", "00000000-0000-0001-0000-0000000003e8")
                .claim("pid", FNR.get())
                .build();
        return claims;
    }

}
