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
public class ArtistaModel {
    static void create(ArtistaBean a, Connection con) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("INSERT INTO Artista (id, nome, bio, tipo, dataorigem, localorigem) "
                                  + "VALUES (?,?,?,?,?,?)");
        st.setInt(1, a.getId());
        st.setString(2, a.getNome());
        st.setString(3, a.getBio());
        st.setString(4, String.valueOf(a.getTipo()));
        st.setDate(5, (java.sql.Date) a.getDataorigem());
        st.setString(6, a.getLocalorigem());
        st.execute();
        st.close();
    }
    
    static HashSet read(Connection con) throws SQLException {
        Statement st;
        HashSet list = new HashSet();
        st = con.createStatement();
        
        String sql = "SELECT id, nome, bio, tipo, dataorigem, localorigem FROM Artista";
        ResultSet result = st.executeQuery(sql);
        while(result.next()) {
            list.add(new ArtistaBean(result.getInt(1), result.getString(2), result.getString(3), 
            result.getString(4).charAt(0), result.getDate(5), result.getString(6)));
        }
        return list;
    }
    
    static void update(ArtistaBean a, Connection con) throws SQLException{
        PreparedStatement st;
        st = con.prepareStatement("UPDATE Artista SET nome=?, bio=?, tipo=?, dataorigem=?, localorigem=? "
                                  + "WHERE id=?");
        st.setString(1, a.getNome());
        st.setString(2, a.getBio());
        st.setString(3, String.valueOf(a.getTipo()));
        st.setDate(4, (java.sql.Date) a.getDataorigem());
        st.setString(5, a.getLocalorigem());
        
        st.setInt(6, a.getId());
        st.execute();
        st.close();        
    }
    
    static void delete(int id, Connection con) throws SQLException{
        PreparedStatement st;
        st = con.prepareStatement("DELETE FROM Artista WHERE id = ?");
        st.setInt(1, id);
        st.execute();
        st.close();        
    }  
}
