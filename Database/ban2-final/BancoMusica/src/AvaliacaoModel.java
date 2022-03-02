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
import java.sql.Date;
/**
 *
 * @author Matheus
 */
public class AvaliacaoModel {
    static void create(AvaliacaoBean a, Connection con) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("INSERT INTO Avaliacao (id, texto, nota, idusuario, idalbum) "
                                  + "VALUES (?,?,?,?,?)");
        st.setInt(1, a.getId());
        st.setString(2, a.getTexto());
        st.setFloat(3, a.getNota());
        st.setInt(4, a.getIdusuario());
        st.setInt(5, a.getIdalbum());
        st.execute();
        st.close();
    }
    
    static HashSet read(Connection con) throws SQLException {
        Statement st;
        HashSet list = new HashSet();
        st = con.createStatement();
        
        String sql = "SELECT id, texto, nota, idusuario, idalbum FROM Avaliacao";
        ResultSet result = st.executeQuery(sql);
        while(result.next()) {
            list.add(new AvaliacaoBean(result.getInt(1), result.getString(2), result.getFloat(3), 
            result.getInt(4), result.getInt(5)));
        }
        return list;
    }
    
    static void update(AvaliacaoBean a, Connection con) throws SQLException{
        PreparedStatement st;
        st = con.prepareStatement("UPDATE Avaliacao SET id=?, texto=?, nota=?, idusuario=?, idalbum=? "
                                  + "WHERE id=?");
        st.setString(1, a.getTexto());
        st.setFloat(2, a.getNota());
        st.setInt(3, a.getIdusuario());
        st.setInt(4, a.getIdalbum());
        
        st.setInt(5, a.getId());
        st.execute();
        st.close();        
    }
    
    static void delete(int id, Connection con) throws SQLException{
        PreparedStatement st;
        st = con.prepareStatement("DELETE FROM Avaliacao WHERE id = ?");
        st.setInt(1, id);
        st.execute();
        st.close();        
    }
}
