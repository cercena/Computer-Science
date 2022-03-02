/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
/**
 *
 * @author Matheus
 */
public class ArtistaController {
    public void createArtista(Connection con) throws SQLException, ParseException {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os seguintes dados para a criar um novo Artista: ");
        System.out.print("id: ");
        int id = input.nextInt();
        System.out.print("nome: ");
        String nome = input.next();
        System.out.print("bio: ");
        String bio = input.next();
        System.out.print("tipo: ");
        char tipo = input.next().charAt(0);    
        System.out.print("dataorigem: ");
        String data = input.next();        
        System.out.print("localorigem: ");
        String localorigem = input.next();
        
        String[] dataArray = new String[3];
        dataArray = data.split("/");
        Date dataorigem = new Date();
        if(dataArray.length == 3){
            dataorigem = new SimpleDateFormat("yyyy-MM-dd").parse(dataArray[2]+"-"+dataArray[1]+"-"+dataArray[0]);
            System.out.println(dataorigem.getTime()/1000);
            ArtistaBean ab = new ArtistaBean(id, nome, bio, tipo, dataorigem, localorigem);
            ArtistaModel.create(ab, con);
            System.out.println("Artista criado com sucesso!");
        }else{
            System.out.println("Data invalida");
        }    
    }

    void readArtista(Connection con) throws SQLException {
        HashSet all = ArtistaModel.read(con);
        Iterator<ArtistaBean> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }
    
    void updateArtista(Connection con) throws SQLException, ParseException{
        Scanner input = new Scanner(System.in);
        System.out.println("Informe o id do Artista que deseja atualizar: ");
        System.out.print("id: ");
        int id = input.nextInt();
        
        System.out.println("Informe os novos dados: ");
        System.out.print("nome: ");
        String nome = input.next();
        System.out.print("bio: ");
        String bio = input.next();
        System.out.print("tipo: ");
        char tipo = input.next().charAt(0);    
        System.out.print("dataorigem: ");
        String data = input.next();        
        System.out.print("localorigem: ");
        String localorigem = input.next();
        
        String[] dataArray = new String[3];
        dataArray = data.split("/");
        Date dataorigem = new Date();
        if(dataArray.length == 3){
            dataorigem = new SimpleDateFormat("yyyy-MM-dd").parse(dataArray[2]+"-"+dataArray[1]+"-"+dataArray[0]);
            System.out.println(dataorigem.getTime()/1000);
            ArtistaBean ab = new ArtistaBean(id, nome, bio, tipo, dataorigem, localorigem);
            ArtistaModel.update(ab, con);
            System.out.println("Artista atualizado com sucesso!");
        }else{
            System.out.println("Data invalida");
        }
    }
    
    void deleteArtista(Connection con) throws SQLException{
        Scanner input = new Scanner(System.in);
        System.out.println("Informe o id do Artista que deseja deletar: ");
        System.out.print("id: ");
        int id = input.nextInt();
        
        ArtistaModel.delete(id, con);
        System.out.println("Artista deletado com sucesso!");
    }
}
