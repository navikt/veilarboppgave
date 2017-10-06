package no.nav.fo.veilarboppgave.util.sql;


import no.nav.fo.veilarboppgave.util.sql.where.WhereClause;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteQuery {
    private final DataSource ds;
    private final String tableName;
    private WhereClause where;

    DeleteQuery(DataSource ds, String tableName) {
        this.ds = ds;
        this.tableName = tableName;
    }

    public DeleteQuery where(WhereClause where) {
        this.where = where;
        return this;
    }

    public int execute() {
        if (tableName == null || this.where == null) {
            throw new SqlUtilsException(
                    "I need more data to create a sql-statement. " +
                            "Did you remember to specify table and a where clause?"
            );
        }

        int result;
        try (Connection conn = ds.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(createDeleteStatement());
            where.applyTo(ps, 1);

            result = ps.executeUpdate();

        } catch (SQLException e) {
            throw new SqlUtilsException(e);
        }
        return result;
    }

    private String createDeleteStatement() {
        return String.format(
                "DELETE FROM %s WHERE %s",
                tableName,
                this.where.toSql()
        );
    }

    @Override
    public String toString() {
        return createDeleteStatement();
    }
}
