/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Scanner;

/**
 *
 * @author rebeca
 */
public class Principal {

    public static void main(String[] args) throws SQLException, ParseException {
        Conexao c = new Conexao();
        Connection con = c.getConnection();
        int op1 = 0, op2 = 0;
        do{
            op1 = menu1();
            if(op1>=1 && op1<=4){
                op2 = menu2();
            }           
            try {
                switch (op1) {
                    case 1: if(op2 == 1){new ArtistaController().createArtista(con);}
                            if(op2 == 2){new AlbumController().createAlbum(con);}
                            if(op2 == 3){new MusicaController().createMusica(con);}
                            if(op2 == 4){new GeneroController().createGenero(con);}
                            if(op2 == 5){new GravadoraController().createGravadora(con);}
                            if(op2 == 6){new VersaoController().createVersao(con);}
                            if(op2 == 7){new UsuarioController().createUsuario(con);}
                            if(op2 == 8){new AvaliacaoController().createAvaliacao(con);}
                            break;
                    case 2: if(op2 == 1){new ArtistaController().readArtista(con);}
                            if(op2 == 2){new AlbumController().readAlbum(con);}
                            if(op2 == 3){new MusicaController().readMusica(con);}
                            if(op2 == 4){new GeneroController().readGenero(con);}
                            if(op2 == 5){new GravadoraController().readGravadora(con);}
                            if(op2 == 6){new VersaoController().readVersao(con);}
                            if(op2 == 7){new UsuarioController().readUsuario(con);}
                            if(op2 == 8){new AvaliacaoController().readAvaliacao(con);}
                            break;
                    case 3: if(op2 == 1){new ArtistaController().updateArtista(con);}
                            if(op2 == 2){new AlbumController().updateAlbum(con);}
                            if(op2 == 3){new MusicaController().updateMusica(con);}
                            if(op2 == 4){new GeneroController().updateGenero(con);}
                            if(op2 == 5){new GravadoraController().updateGravadora(con);}
                            if(op2 == 6){new VersaoController().updateVersao(con);}
                            if(op2 == 7){new UsuarioController().updateUsuario(con);}
                            if(op2 == 8){new AvaliacaoController().updateAvaliacao(con);}
                            break;
                    case 4: if(op2 == 1){new ArtistaController().deleteArtista(con);}
                            if(op2 == 2){new AlbumController().deleteAlbum(con);}
                            if(op2 == 3){new MusicaController().deleteMusica(con);}
                            if(op2 == 4){new GeneroController().deleteGenero(con);}
                            if(op2 == 5){new GravadoraController().deleteGravadora(con);}
                            if(op2 == 6){new VersaoController().deleteVersao(con);}
                            if(op2 == 7){new UsuarioController().deleteUsuario(con);}
                            if(op2 == 8){new AvaliacaoController().deleteAvaliacao(con);}
                            break;
                }
            }catch(SQLException ex) {
                System.out.println(ex.getMessage());
                continue;
            }
        } while(op1>0 && op1<6);  
        con.close();
    }    
    
    private static int menu1() {
        System.out.println("Catálogo de Produtos Musicais");
        System.out.println("Informe a operação que deseja executar: ");
        System.out.println("1 - Criar uma nova entrada");
        System.out.println("2 - Exibir entradas existentes");
        System.out.println("3 - Atualizar dados de uma entrada");
        System.out.println("4 - Deletar uma entrada");
        System.out.println("5 - Listar relatórios");
        System.out.println("Digite qualquer outro valor para sair");
        System.out.print("Sua opção: ");
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }
    
    private static int menu2(){
        System.out.println("Escolha a tabela que deseja efetuar a alteração: ");
        System.out.println("1 - Artista");
        System.out.println("2 - Album");
        System.out.println("3 - Música");
        System.out.println("4 - Gênero");
        System.out.println("5 - Gravadora");
        System.out.println("6 - Versão");
        System.out.println("7 - Usuário");
        System.out.println("8 - Avaliação");
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }
    //
}
