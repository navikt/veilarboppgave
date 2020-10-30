package no.nav.veilarboppgave.utils.testdriver;


/*
"Fattigmanns-løsning" for å kunne bruke hsql lokalt med oracle syntax
 NB: Har byttet til h2 så mye av disse kan funke hvis syntaxen var rett
*/
class DatabaseSyntaxMapper {

    private static final String NOOP = "SELECT 1 FROM DUAL";

    static String h2Syntax(final String orginialSql) {
        String fixedSql = orginialSql;

        if (orginialSql.contains("BEGIN") || orginialSql.contains("END LOOP")) {
            fixedSql = NOOP;
        }

        /*
         Fikser en bug i Flyway som gjør at installed_by ikke blir satt i Oracle modus med H2.
         Dette har blitt fikset i en nyere versjon av Flyway, men denne versjonen støtter ikke gammle Oracle databaser som vi i dag bruker.
        */
        if (orginialSql.contains("installed_by")) {
            fixedSql = orginialSql.replace("\"installed_by\" VARCHAR(100) NOT NULL", "\"installed_by\" VARCHAR(100) DEFAULT 'local_test'");
        }

        if (fixedSql.equals(orginialSql)) {
            System.out.println(orginialSql);
        } else {
            System.out.println("Original SQL: " + orginialSql + "\n--------\nFixed SQL: " + fixedSql);
        }

        return fixedSql;
    }

}
