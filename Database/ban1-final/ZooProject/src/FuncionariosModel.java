import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FuncionariosModel {

    public static void create(FuncionariosBean f, Connection con) throws SQLException {
        PreparedStatement st;
            st = con.prepareStatement("INSERT INTO Funcionarios (matricula, nome, endereco, email, crmv, especialidade) "
                    + "VALUES (?,?,?,?,?,?)");
            st.setInt(1, f.getMatricula());
            st.setString(2, f.getNome());
            st.setString(3, f.getEndereco());
            st.setString(4, f.getEmail());
            st.setInt(5, f.getCrmv());
            st.setString(6, f.getEspecialidade());
            st.execute();
            st.close();  
    }

    static HashSet listAll(Connection con) throws SQLException {
        Statement st;
        HashSet list = new HashSet();
            st = con.createStatement();
            String sql = "SELECT matricula, nome, endereco, email, crmv, especialidade FROM Funcionarios";
            ResultSet result = st.executeQuery(sql);
            while(result.next()) {
                list.add(new FuncionariosBean(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), 
                        result.getInt(5), result.getString(6)));
            }
        return list;
    }
}
