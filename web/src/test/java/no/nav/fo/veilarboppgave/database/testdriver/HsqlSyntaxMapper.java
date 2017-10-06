package no.nav.fo.veilarboppgave.database.testdriver;

import java.util.HashMap;
import java.util.Map;

/*
"Fattigmanns-løsning" for å kunne bruke hsql lokalt med oracle syntax
*/
class HsqlSyntaxMapper {

    private static final Map<String, String> syntaxMap = new HashMap<>();

    static {

    }

    private static void map(String oracleSyntax, String hsqlSyntax) {
        syntaxMap.put(oracleSyntax, hsqlSyntax);
    }

    static String hsqlSyntax(String sql) {
        if (sql.contains("CREATE MATERIALIZED VIEW") || sql.contains("DROP MATERIALIZED VIEW")) {
            return "SELECT 1 FROM DUAL";
        }
        return syntaxMap.getOrDefault(sql, sql);
    }

}
