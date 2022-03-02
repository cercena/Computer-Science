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
public class MusicaController {
    public void createMusica(Connection con) throws SQLException, ParseException {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os seguintes dados para a criar uma nova Musica: ");
        System.out.print("id: ");
        int id = input.nextInt();
        System.out.print("nome: ");
        String nome = input.next();
        System.out.print("duracao: ");
        float duracao = input.nextFloat();
        System.out.print("idalbum: ");
        int idalbum = input.nextInt();    
        
        MusicaBean mb = new MusicaBean(id, nome, duracao, idalbum);
        MusicaModel.create(mb, con);
        System.out.println("Musica criada com sucesso!");   
    }

    void readMusica(Connection con) throws SQLException {
        HashSet all = MusicaModel.read(con);
        Iterator<MusicaBean> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }
    
    void updateMusica(Connection con) throws SQLException, ParseException{
        Scanner input = new Scanner(System.in);
        System.out.println("Informe o id da Musica que deseja atualizar: ");
        System.out.print("id: ");
        int id = input.nextInt();
        
        System.out.println("Informe os novos dados: ");
        System.out.print("nome: ");
        String nome = input.next();
        System.out.print("duracao: ");
        float duracao = input.nextFloat();
        System.out.print("idalbum: ");
        int idalbum = input.nextInt();
        
        MusicaBean mb = new MusicaBean(id, nome, duracao, idalbum);
        MusicaModel.update(mb, con);
        System.out.println("Musica atualizada com sucesso!");
    }
    
    void deleteMusica(Connection con) throws SQLException{
        Scanner input = new Scanner(System.in);
        System.out.println("Informe o id da Música que deseja deletar: ");
        System.out.print("id: ");
        int id = input.nextInt();
        
        MusicaModel.delete(id, con);
        System.out.println("Musica deletada com sucesso!");
    }
}
