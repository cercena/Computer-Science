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
public class GeneroController {
    public void createGenero(Connection con) throws SQLException, ParseException {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os seguintes dados para a criar um novo Gênero: ");
        System.out.print("id: ");
        int id = input.nextInt();
        System.out.print("nome: ");
        String nome = input.next();
        System.out.print("descricao: ");
        String descricao = input.next();  
        
        GeneroBean gb = new GeneroBean(id, nome, descricao);
        GeneroModel.create(gb, con);
        System.out.println("Gênero criada com sucesso!");   
    }

    void readGenero(Connection con) throws SQLException {
        HashSet all = GeneroModel.read(con);
        Iterator<GeneroBean> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }
    
    void updateGenero(Connection con) throws SQLException, ParseException{
        Scanner input = new Scanner(System.in);
        System.out.println("Informe o id do Gênero que deseja atualizar: ");
        System.out.print("id: ");
        int id = input.nextInt();
        
        System.out.println("Informe os novos dados: ");
        System.out.print("nome: ");
        String nome = input.next();
        System.out.print("descricao: ");
        String descricao = input.next(); 
        
        GeneroBean gb = new GeneroBean(id, nome, descricao);
        GeneroModel.create(gb, con);
        System.out.println("Gênero atualizado com sucesso!"); 
    }
    
    void deleteGenero(Connection con) throws SQLException{
        Scanner input = new Scanner(System.in);
        System.out.println("Informe o id do Gênero que deseja deletar: ");
        System.out.print("id: ");
        int id = input.nextInt();
        
        GeneroModel.delete(id, con);
        System.out.println("Gênero deletado com sucesso!");
    }
}
