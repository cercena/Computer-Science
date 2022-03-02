import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class FuncionariosController {
    public void createFuncionario(Connection con) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os seguintes dados para a criar um novo funcionário: ");
        System.out.print("matricula: ");
        int matricula = input.nextInt();
        System.out.print("nome: ");
        String nome = input.next();
        System.out.print("endereco: ");
        String endereco = input.next();
        System.out.print("email: ");
        String email = input.next();    
        System.out.print("crmv: ");
        int crmv = input.nextInt();        
        System.out.print("especialidade: ");
        String especialidade = input.next();         
        
        FuncionariosBean fb = new FuncionariosBean(matricula, nome, endereco, email, crmv, especialidade);
        FuncionariosModel.create(fb, con);
        System.out.println("Funcionário criado com sucesso!!");     
    }

    void listFuncionario(Connection con) throws SQLException {
        HashSet all = FuncionariosModel.listAll(con);
        Iterator<FuncionariosBean> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }

    /*
    void listarMedicosAmbulatorios(Connection con) throws SQLException {
        HashSet all = AnimaisModel.listAllWithAmbulatorios(con);
        Iterator<FuncionariosBean> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }
    */
}
