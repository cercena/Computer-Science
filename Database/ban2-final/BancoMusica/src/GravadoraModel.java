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
public class GravadoraModel {
    static void create(GravadoraBean g, Connection con) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("INSERT INTO Gravadora (id, nome, pais) "
                                  + "VALUES (?,?,?)");
        st.setInt(1, g.getId());
        st.setString(2, g.getNome());
        st.setString(3, g.getPais());
        st.execute();
        st.close();
    }
    
    static HashSet read(Connection con) throws SQLException {
        Statement st;
        HashSet list = new HashSet();
        st = con.createStatement();
        
        String sql = "SELECT id, nome, pais FROM Gravadora";
        ResultSet result = st.executeQuery(sql);
        while(result.next()) {
            list.add(new GravadoraBean(result.getInt(1), result.getString(2), result.getString(3)));
        }
        return list;
    }
    
    static void update(GravadoraBean g, Connection con) throws SQLException{
        PreparedStatement st;
        st = con.prepareStatement("UPDATE Gravadora SET nome=?, pais=? "
                                  + "WHERE id=?");
        st.setString(1, g.getNome());
        st.setString(2, g.getPais());
        
        st.setInt(3, g.getId());
        st.execute();
        st.close();        
    }
    
    static void delete(int id, Connection con) throws SQLException{
        PreparedStatement st;
        st = con.prepareStatement("DELETE FROM Gravadora WHERE id = ?");
        st.setInt(1, id);
        st.execute();
        st.close();        
    }   
}
