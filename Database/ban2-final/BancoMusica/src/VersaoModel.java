/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
/**
 *
 * @author Matheus
 */
public class VersaoModel {
    static void create(VersaoBean v, Connection con) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("INSERT INTO Versao (id, ano, midia, pais, idgravadora, idalbum) "
                                  + "VALUES (?,?,?,?,?,?)");
        st.setInt(1, v.getId());
        st.setInt(2, v.getAno());
        st.setString(3, v.getMidia());
        st.setString(4, v.getPais());
        st.setInt(5, v.getIdgravadora());
        st.setInt(6, v.getIdalbum());
        st.execute();
        st.close();
    }
    
    static HashSet read(Connection con) throws SQLException {
        Statement st;
        HashSet list = new HashSet();
        st = con.createStatement();
        
        String sql = "SELECT id, ano, midia, pais, idgravadora, idalbum FROM Versao";
        ResultSet result = st.executeQuery(sql);
        while(result.next()) {
            list.add(new VersaoBean(result.getInt(1), result.getInt(2), result.getString(3), 
            result.getString(4), result.getInt(5), result.getInt(6)));
        }
        return list;
    }
    
    static void update(VersaoBean v, Connection con) throws SQLException{
        PreparedStatement st;
        st = con.prepareStatement("UPDATE Versao SET ano=?, midia=?, pais=?, idgravadora=?, idalbum=? "
                                  + "WHERE id=?");
        st.setInt(1, v.getAno());
        st.setString(2, v.getMidia());
        st.setString(3, v.getPais());
        st.setInt(4, v.getIdgravadora());
        st.setInt(5, v.getIdalbum());
        
        st.setInt(6, v.getId());
        st.execute();
        st.close();        
    }
    
    static void delete(int id, Connection con) throws SQLException{
        PreparedStatement st;
        st = con.prepareStatement("DELETE FROM Versao WHERE id = ?");
        st.setInt(1, id);
        st.execute();
        st.close();        
    }
}
