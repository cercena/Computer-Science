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
public class AlbumModel {
    static void create(AlbumBean a, Connection con) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("INSERT INTO Album (id, nome, datalanc, notamedia, rank, idartista) "
                                  + "VALUES (?,?,?,?,?,?)");
        st.setInt(1, a.getId());
        st.setString(2, a.getNome());
        st.setDate(3, (java.sql.Date) a.getDatalanc());
        st.setFloat(4, a.getNotamedia());
        st.setInt(5, a.getRank());
        st.setInt(6, a.getIdartista());
        st.execute();
        st.close();
    }
    
    static HashSet read(Connection con) throws SQLException {
        Statement st;
        HashSet list = new HashSet();
        st = con.createStatement();
        
        String sql = "SELECT id, nome, datalanc, notamedia, rank, idartista FROM Album";
        ResultSet result = st.executeQuery(sql);
        while(result.next()) {
            list.add(new AlbumBean(result.getInt(1), result.getString(2), result.getDate(3), 
            result.getFloat(4), result.getInt(5), result.getInt(6)));
        }
        return list;
    }
    
    static void update(AlbumBean a, Connection con) throws SQLException{
        PreparedStatement st;
        st = con.prepareStatement("UPDATE Album SET nome=?, datalanc=?, notamedia=?, rank=?, idartista=? "
                                  + "WHERE id=?");
        st.setString(1, a.getNome());
        st.setDate(2, (java.sql.Date) a.getDatalanc());
        st.setFloat(3, a.getNotamedia());
        st.setInt(4, a.getRank());
        st.setInt(5, a.getIdartista());
        
        st.setInt(6, a.getId());
        st.execute();
        st.close();        
    }
    
    static void delete(int id, Connection con) throws SQLException{
        PreparedStatement st;
        st = con.prepareStatement("DELETE FROM Album WHERE id = ?");
        st.setInt(1, id);
        st.execute();
        st.close();        
    }
}
