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
public class AlbumController {
    public void createAlbum(Connection con) throws SQLException, ParseException {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os seguintes dados para a criar um novo Album: ");
        System.out.print("id: ");
        int id = input.nextInt();
        System.out.print("nome: ");
        String nome = input.next();
        System.out.print("datalanc: ");
        String data = input.next();
        System.out.print("notamedia: ");
        float notamedia = input.nextFloat();    
        System.out.print("rank: ");
        int rank = input.nextInt();        
        System.out.print("idartista: ");
        int idartista = input.nextInt();
        
        String[] dataArray = new String[3];
        dataArray = data.split("/");
        Date datalanc = new Date();
        if(dataArray.length == 3){
            datalanc = new SimpleDateFormat("yyyy-MM-dd").parse(dataArray[2]+"-"+dataArray[1]+"-"+dataArray[0]);
            System.out.println(datalanc.getTime()/1000);
            AlbumBean ab = new AlbumBean(id, nome, datalanc, notamedia, rank, idartista);
            AlbumModel.create(ab, con);
            System.out.println("Album criado com sucesso!");
        }else{
            System.out.println("Data invalida");
        }    
    }

    void readAlbum(Connection con) throws SQLException {
        HashSet all = AlbumModel.read(con);
        Iterator<AlbumBean> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }
    
    void updateAlbum(Connection con) throws SQLException, ParseException{
        Scanner input = new Scanner(System.in);
        System.out.println("Informe o id do Album que deseja atualizar: ");
        System.out.print("id: ");
        int id = input.nextInt();
        
        System.out.println("Informe os novos dados: ");
        System.out.print("nome: ");
        String nome = input.next();
        System.out.print("datalanc: ");
        String data = input.next();
        System.out.print("notamedia: ");
        float notamedia = input.nextFloat();    
        System.out.print("rank: ");
        int rank = input.nextInt();        
        System.out.print("idartista: ");
        int idartista = input.nextInt();
        
        String[] dataArray = new String[3];
        dataArray = data.split("/");
        Date datalanc = new Date();
        if(dataArray.length == 3){
            datalanc = new SimpleDateFormat("yyyy-MM-dd").parse(dataArray[2]+"-"+dataArray[1]+"-"+dataArray[0]);
            System.out.println(datalanc.getTime()/1000);
            AlbumBean ab = new AlbumBean(id, nome, datalanc, notamedia, rank, idartista);
            AlbumModel.update(ab, con);
            System.out.println("Album criado com sucesso!");
        }else{
            System.out.println("Data invalida");
        } 
    }
    
    void deleteAlbum(Connection con) throws SQLException{
        Scanner input = new Scanner(System.in);
        System.out.println("Informe o id do Album que deseja deletar: ");
        System.out.print("id: ");
        int id = input.nextInt();
        
        AlbumModel.delete(id, con);
        System.out.println("Album deletado com sucesso!");
    }
}
