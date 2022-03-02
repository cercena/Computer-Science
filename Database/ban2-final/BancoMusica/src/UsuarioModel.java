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
public class UsuarioModel {
    static void create(UsuarioBean u, Connection con) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("INSERT INTO Usuario (id, username, senha, nome, idade, sexo, local, descricao, email, papel) "
                                  + "VALUES (?,?,?,?,?,?,?,?,?,?)");
        st.setInt(1, u.getId());
        st.setString(2, u.getUsername());
        st.setString(3, u.getSenha());
        st.setString(4, u.getNome());
        st.setInt(5, u.getIdade());
        st.setString(6, String.valueOf(u.getSexo()));
        st.setString(7, u.getLocal());
        st.setString(8, u.getDescricao());
        st.setString(9, u.getLocal());
        st.setString(10, String.valueOf(u.getPapel()));
        st.execute();
        st.close();
    }
    
    static HashSet read(Connection con) throws SQLException {
        Statement st;
        HashSet list = new HashSet();
        st = con.createStatement();
        
        String sql = "SELECT id, username, senha, nome, idade, sexo, local, descricao, email, papel FROM Usuario";
        ResultSet result = st.executeQuery(sql);
        while(result.next()) {
            list.add(new UsuarioBean(result.getInt(1), result.getString(2), result.getString(3), 
            result.getString(4), result.getInt(5), result.getString(6).charAt(0), result.getString(7),
            result.getString(8), result.getString(9), result.getString(10).charAt(0)));
        }
        return list;
    }
    
    static void update(UsuarioBean u, Connection con) throws SQLException{
        PreparedStatement st;
        st = con.prepareStatement("UPDATE Usuario SET username=?, senha=?, nome=?, idade=?, sexo=?, local=?, descricao=?, email=?, papel=? "
                                  + "WHERE id=?");
        st.setString(1, u.getUsername());
        st.setString(2, u.getSenha());
        st.setString(3, u.getNome());
        st.setInt(4, u.getIdade());
        st.setString(5, String.valueOf(u.getSexo()));
        st.setString(6, u.getLocal());
        st.setString(7, u.getDescricao());
        st.setString(8, u.getLocal());
        st.setString(9, String.valueOf(u.getPapel()));
        
        st.setInt(10, u.getId());
        st.execute();
        st.close();        
    }
    
    static void delete(int id, Connection con) throws SQLException{
        PreparedStatement st;
        st = con.prepareStatement("DELETE FROM Usuario WHERE id = ?");
        st.setInt(1, id);
        st.execute();
        st.close();        
    }
}
