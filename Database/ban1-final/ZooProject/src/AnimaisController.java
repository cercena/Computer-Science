import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

public class AnimaisController {
    
    public void createAnimal(Connection con) throws SQLException, ParseException {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os seguintes dados para a criar um novo Animal: ");
        System.out.print("nomeanimal: ");
        String nomeanimal = input.nextLine();
        System.out.print("codespecie: ");
        int codespecie = input.nextInt();
        System.out.print("codanimal: ");
        int codanimal = input.nextInt();
        System.out.print("codanimalpai: ");
        int codanimalpai = input.nextInt();
        System.out.print("codanimalmae: ");
        int codanimalmae = input.nextInt();
        System.out.print("dtnascanimal: ");
        String data = input.next();
        String[] dataArray = new String[3];
        dataArray = data.split("/");
        Date dtnascanimal = new Date();
        if(dataArray.length == 3){
            dtnascanimal = new SimpleDateFormat("yyyy-MM-dd").parse(dataArray[2]+"-"+dataArray[1]+"-"+dataArray[0]);
            System.out.println(dtnascanimal.getTime()/1000);
            AnimaisBean ab = new AnimaisBean(nomeanimal, codespecie, codanimal, codanimalpai, codanimalmae, dtnascanimal);
            AnimaisModel.create(ab, con);
            System.out.println("Animal criado com sucesso!");
        }else{
            System.out.println("Data invalida");
        }
    }

    void listAnimal(Connection con) throws SQLException {
        HashSet all = AnimaisModel.listAll(con);
        Iterator<AnimaisBean> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }
    
    void listAnimalEspecie(Connection con) throws SQLException {
        HashSet all = AnimaisModel.listAllWithEspecies(con);
        Iterator<AnimaisBean> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }
    
    void listAnimalAboveAverage(Connection con) throws SQLException {
        HashSet all = AnimaisModel.listAboveAverageAnimais(con);
        Iterator<AnimaisBean> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }
}
