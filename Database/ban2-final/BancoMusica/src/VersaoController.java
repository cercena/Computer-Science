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
public class VersaoController {
    public void createVersao(Connection con) throws SQLException, ParseException {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os seguintes dados para a criar uma nova Versão: ");
        System.out.print("id: ");
        int id = input.nextInt();
        System.out.print("ano: ");
        int ano = input.nextInt();
        System.out.print("midia: ");
        String midia = input.next();
        System.out.println("pais: ");
        String pais = input.next();
        System.out.println("idgravadora: ");
        int idgravadora = input.nextInt();
        System.out.println("idalbum");
        int idalbum = input.nextInt();
        
        VersaoBean vb = new VersaoBean(id, ano, midia, pais, idgravadora, idalbum);
        VersaoModel.create(vb, con);
        System.out.println("Versão criada com sucesso!");   
    }

    void readVersao(Connection con) throws SQLException {
        HashSet all = VersaoModel.read(con);
        Iterator<VersaoBean> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }
    
    void updateVersao(Connection con) throws SQLException, ParseException{
        Scanner input = new Scanner(System.in);
        System.out.println("Informe o id da Versão que deseja atualizar: ");
        System.out.print("id: ");
        int id = input.nextInt();
        
        System.out.println("Informe os novos dados: ");
        System.out.print("ano: ");
        int ano = input.nextInt();
        System.out.print("midia: ");
        String midia = input.next();
        System.out.println("pais: ");
        String pais = input.next();
        System.out.println("idgravadora: ");
        int idgravadora = input.nextInt();
        System.out.println("idalbum");
        int idalbum = input.nextInt(); 
        
        VersaoBean vb = new VersaoBean(id, ano, midia, pais, idgravadora, idalbum);
        VersaoModel.update(vb, con);
        System.out.println("Versão atualizada com sucesso!");   
    }
    
    void deleteVersao(Connection con) throws SQLException{
        Scanner input = new Scanner(System.in);
        System.out.println("Informe o id da Verão que deseja deletar: ");
        System.out.print("id: ");
        int id = input.nextInt();
        
        VersaoModel.delete(id, con);
        System.out.println("Versão deletada com sucesso!");
    }
}
