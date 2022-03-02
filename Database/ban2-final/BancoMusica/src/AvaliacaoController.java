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
public class AvaliacaoController {
    public void createAvaliacao(Connection con) throws SQLException, ParseException {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os seguintes dados para a criar uma nova Avaliação: ");
        System.out.print("id: ");
        int id = input.nextInt();
        System.out.print("texto: ");
        String texto = input.next();
        System.out.print("nota: ");
        float nota = input.nextFloat();
        System.out.println("idusuario: ");
        int idusuario = input.nextInt();
        System.out.println("idalbum: ");
        int idalbum = input.nextInt();
        
        AvaliacaoBean ab = new AvaliacaoBean(id, texto, nota, idusuario, idalbum);
        AvaliacaoModel.create(ab, con);
        System.out.println("Avaliação criada com sucesso!");   
    }

    void readAvaliacao(Connection con) throws SQLException {
        HashSet all = AvaliacaoModel.read(con);
        Iterator<AvaliacaoBean> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }
    
    void updateAvaliacao(Connection con) throws SQLException, ParseException{
        Scanner input = new Scanner(System.in);
        System.out.println("Informe o id da Avaliação que deseja atualizar: ");
        System.out.print("id: ");
        int id = input.nextInt();
        
        System.out.println("Informe os novos dados: ");
        System.out.print("texto: ");
        String texto = input.next();
        System.out.print("nota: ");
        float nota = input.nextFloat();
        System.out.println("idusuario: ");
        int idusuario = input.nextInt();
        System.out.println("idalbum: ");
        int idalbum = input.nextInt(); 
        
        AvaliacaoBean ab = new AvaliacaoBean(id, texto, nota, idusuario, idalbum);
        AvaliacaoModel.update(ab, con);
        System.out.println("Avaliação atualizada com sucesso!");  
    }
    
    void deleteAvaliacao(Connection con) throws SQLException{
        Scanner input = new Scanner(System.in);
        System.out.println("Informe o id da Avaliacao que deseja deletar: ");
        System.out.print("id: ");
        int id = input.nextInt();
        
        AvaliacaoModel.delete(id, con);
        System.out.println("Avaliação deletada com sucesso!");
    }
}
