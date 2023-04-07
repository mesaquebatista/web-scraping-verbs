package br.com.mesaque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.mesaque.db.ConnectionMySql;
import br.com.mesaque.entities.Verb;

public class VerbDAO {
    private static final String TABLE = "verbs";
    private static final String COLUMN_VERB = "verb";
    private static final String COLUMN_INFINITIVE = "infinitive";
    private static final String COLUMN_SIMPLE_PAST = "simple_past";
    private static final String COLUMN_PAST_PARTICIPLE = "past_participle";

    public Verb getByVerb(String sVerb) {
        Connection connection = ConnectionMySql.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Verb verb = null;

        try {
            stmt = connection.prepareStatement("SELECT * FROM " + TABLE + " WHERE verb = ?");
            stmt.setString(1, sVerb);
            rs = stmt.executeQuery();

            if (rs.next()) {
                verb = new Verb(rs.getInt("id"), rs.getString("verb"), rs.getString("infinitive"),
                        rs.getString("simple_past"), rs.getString("past_participle"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (stmt != null) {
                    stmt.close();
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return verb;
    }

    public boolean save(Verb verb) {
        Connection connection = ConnectionMySql.getConnection();
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            stmt = connection.prepareStatement("INSERT INTO " + TABLE + " (" + COLUMN_VERB + "," + COLUMN_INFINITIVE + "," + COLUMN_SIMPLE_PAST + "," + COLUMN_PAST_PARTICIPLE + ") VALUES (?,?,?,?)");
            stmt.setString(1, verb.getVerb());
            stmt.setString(2, verb.getInfinitive());
            stmt.setString(3, verb.getSimplePast());
            stmt.setString(4, verb.getPastParticiple());
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {

                if (stmt != null) {
                    stmt.close();
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
