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
public class MusicaModel {
    static void create(MusicaBean m, Connection con) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("INSERT INTO Musica (id, nome, duracao, idalbum) "
                                  + "VALUES (?,?,?,?)");
        st.setInt(1, m.getId());
        st.setString(2, m.getNome());
        st.setFloat(3, m.getDuracao());
        st.setInt(4, m.getIdalbum());
        st.execute();
        st.close();
    }
    
    static HashSet read(Connection con) throws SQLException {
        Statement st;
        HashSet list = new HashSet();
        st = con.createStatement();
        
        String sql = "SELECT id, nome, duracao, idalbum FROM Musica";
        ResultSet result = st.executeQuery(sql);
        while(result.next()) {
            list.add(new MusicaBean(result.getInt(1), result.getString(2), result.getFloat(3), result.getInt(4)));
        }
        return list;
    }
    
    static void update(MusicaBean m, Connection con) throws SQLException{
        PreparedStatement st;
        st = con.prepareStatement("UPDATE Musica SET nome=?, duracao=?, idalbum=? "
                                  + "WHERE id=?");
        st.setString(1, m.getNome());
        st.setFloat(2, m.getDuracao());
        st.setInt(3, m.getIdalbum());
        
        st.setInt(4, m.getId());
        st.execute();
        st.close();        
    }
    
    static void delete(int id, Connection con) throws SQLException{
        PreparedStatement st;
        st = con.prepareStatement("DELETE FROM Musica WHERE id = ?");
        st.setInt(1, id);
        st.execute();
        st.close();        
    }
}
