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
public class GeneroModel {
    static void create(GeneroBean g, Connection con) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("INSERT INTO Genero (id, nome, descricao) "
                                  + "VALUES (?,?,?)");
        st.setInt(1, g.getId());
        st.setString(2, g.getNome());
        st.setString(3, g.getDescricao());
        st.execute();
        st.close();
    }
    
    static HashSet read(Connection con) throws SQLException {
        Statement st;
        HashSet list = new HashSet();
        st = con.createStatement();
        
        String sql = "SELECT id, nome, descricao FROM Genero";
        ResultSet result = st.executeQuery(sql);
        while(result.next()) {
            list.add(new GeneroBean(result.getInt(1), result.getString(2), result.getString(3)));
        }
        return list;
    }
    
    static void update(GeneroBean g, Connection con) throws SQLException{
        PreparedStatement st;
        st = con.prepareStatement("UPDATE Genero SET nome=?, descricao=? "
                                  + "WHERE id=?");
        st.setString(1, g.getNome());
        st.setString(2, g.getDescricao());
        
        st.setInt(3, g.getId());
        st.execute();
        st.close();        
    }
    
    static void delete(int id, Connection con) throws SQLException{
        PreparedStatement st;
        st = con.prepareStatement("DELETE FROM Genero WHERE id = ?");
        st.setInt(1, id);
        st.execute();
        st.close();        
    }
}
