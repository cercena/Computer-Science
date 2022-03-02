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
public class GravadoraController {
    public void createGravadora(Connection con) throws SQLException, ParseException {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os seguintes dados para a criar uma nova Gravadora: ");
        System.out.print("id: ");
        int id = input.nextInt();
        System.out.print("nome: ");
        String nome = input.next();
        System.out.print("pais: ");
        String pais = input.next();  
        
        GravadoraBean gb = new GravadoraBean(id, nome, pais);
        GravadoraModel.create(gb, con);
        System.out.println("Gravadora criada com sucesso!");   
    }

    void readGravadora(Connection con) throws SQLException {
        HashSet all = GravadoraModel.read(con);
        Iterator<GravadoraBean> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }
    
    void updateGravadora(Connection con) throws SQLException, ParseException{
        Scanner input = new Scanner(System.in);
        System.out.println("Informe o id da Gravadora que deseja atualizar: ");
        System.out.print("id: ");
        int id = input.nextInt();
        
        System.out.println("Informe os novos dados: ");
        System.out.print("nome: ");
        String nome = input.next();
        System.out.print("pais: ");
        String pais = input.next(); 
        
        GravadoraBean gb = new GravadoraBean(id, nome, pais);
        GravadoraModel.update(gb, con);
        System.out.println("Gravadora atualizada com sucesso!");  
    }
    
    void deleteGravadora(Connection con) throws SQLException{
        Scanner input = new Scanner(System.in);
        System.out.println("Informe o id da Gravadora que deseja deletar: ");
        System.out.print("id: ");
        int id = input.nextInt();
        
        GravadoraModel.delete(id, con);
        System.out.println("Gravadora deletada com sucesso!");
    }
}
