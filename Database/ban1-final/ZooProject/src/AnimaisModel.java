import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnimaisModel {

    static void create(AnimaisBean a, Connection con) throws SQLException {
        PreparedStatement st;
            st = con.prepareStatement("INSERT INTO Animais (nomeanimal, codespecie, codanimal, codanimalpai, codanimalmae, dtnascanimal)"
                    + "VALUES (?,?,?,?,?,?)");
            st.setString(1, a.getNomeanimal());
            st.setInt(2, a.getCodespecie());
            st.setInt(3, a.getCodanimal());
            st.setInt(4, a.getCodanimalpai());
            st.setInt(5, a.getCodanimalmae());
            st.setDate(6, new java.sql.Date(a.getDtnascanimal().getTime()));
            st.execute();
            st.close();
    }
    
    static HashSet listAll(Connection con) throws SQLException {
        Statement st;
        HashSet list = new HashSet();
            st = con.createStatement();
            String sql = "SELECT nomeanimal, codespecie, codanimal, codanimalpai, codanimalmae, dtnascanimal FROM Animais";
            ResultSet result = st.executeQuery(sql);
            while(result.next()) {
                list.add(new AnimaisBean(result.getString(1), result.getInt(2), result.getInt(3), 
                result.getInt(4), result.getInt(5), result.getDate(6)));
            }
        return list;
    }    

    static HashSet listAllWithEspecies(Connection con) throws SQLException {
        Statement st;
        HashSet list = new HashSet();
        st = con.createStatement();
        String sql = "SELECT nomeanimal, codanimal, codanimalpai, codanimalmae, dtnascanimal,"
                + " Animais.codespecie, nomeespecie, expectativaespecie FROM Animais NATURAL JOIN Especies";
        ResultSet result = st.executeQuery(sql);
        while(result.next()) {
            AnimaisBean ab = new AnimaisBean(result.getString(1), result.getInt(6), result.getInt(2), result.getInt(3), 
                    result.getInt(4), result.getDate(5));
            EspeciesBean eb = new EspeciesBean(result.getInt(6), result.getString(7), result.getInt(8));
            ab.setEspecie(eb);
            list.add(ab);
        }
        return list;
    }
    
    static HashSet listAboveAverageAnimais(Connection con) throws SQLException {
        Statement st;
        HashSet list = new HashSet();
        st = con.createStatement();
        String sql = "SELECT nomeanimal, codanimal, codanimalpai, codanimalmae, dtnascanimal," +
                       "Animais.codespecie, nomeespecie, expectativaespecie FROM Animais NATURAL JOIN Especies " +
                        "WHERE expectativaespecie>(SELECT AVG(expectativaespecie) " +" FROM Especies)";
        ResultSet result = st.executeQuery(sql);
        while(result.next()) {
            AnimaisBean ab = new AnimaisBean(result.getString(1), result.getInt(6), result.getInt(2), result.getInt(3), 
                    result.getInt(4), result.getDate(5));
            EspeciesBean eb = new EspeciesBean(result.getInt(6), result.getString(7), result.getInt(8));
            ab.setEspecie(eb);
            list.add(ab);
        }
        return list;
    }
    
}
