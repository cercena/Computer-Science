import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) throws SQLException, ParseException {
        Conexao c = new Conexao();
        Connection con = c.getConnection();
        int op = 0;
        do{
            op = menu();
            try {
                switch (op) {
                    case 1: new AnimaisController().createAnimal(con);
                            break;
                    case 2: new FuncionariosController().createFuncionario(con);
                            break;
                    case 3: new AnimaisController().listAnimal(con);
                            break;
                    case 4: new FuncionariosController().listFuncionario(con);
                            break;
                    case 5: new AnimaisController().listAnimalEspecie(con);
                            break;
                    case 6: new AnimaisController().listAnimalAboveAverage(con);
                            break;
                }
            }catch(SQLException ex) {
                //ex.printStackTrace();
                System.out.println(ex.getMessage());
                continue;
            }
        } while(op>0 && op<7);  
        con.close();
    }    
    
    private static int menu() {
        System.out.println("");
        System.out.println("ZOOLÓGICO BD");
        System.out.println("Informe o número da opção que desejas executar: ");
        System.out.println("1 - Inserir um novo animal");
        System.out.println("2 - Inserir um novo funcionário");
        System.out.println("3 - Exibir todos os animais");
        System.out.println("4 - Exibir todos os funcionários");
        System.out.println("5 - Exibir todos os animais e suas respectivas espécies");
        System.out.println("6 - Exibir animais que possuem expectativa de vida maior que a média do Zoo");
        System.out.println("Digite qualquer outro valor para sair");
        System.out.print("Sua opção: ");
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }
}
