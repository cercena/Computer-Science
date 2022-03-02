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
public class UsuarioController {
    public void createUsuario(Connection con) throws SQLException, ParseException {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os seguintes dados para a criar um novo Usuário: ");
        System.out.print("id: ");
        int id = input.nextInt();
        System.out.print("username: ");
        String username = input.next();
        System.out.print("senha: ");
        String senha = input.next();
        System.out.println("nome: ");
        String nome = input.next();
        System.out.println("idade: ");
        int idade = input.nextInt();
        System.out.println("sexo: ");
        char sexo = input.next().charAt(0);
        System.out.println("local: ");
        String local = input.next();
        System.out.println("descricao: ");
        String descricao = input.next();
        System.out.println("email: ");
        String email = input.next();
        System.out.println("papel: ");
        char papel = input.next().charAt(0);
        
        UsuarioBean ub = new UsuarioBean(id, username, senha, nome, idade, sexo, local, descricao, email, papel);
        UsuarioModel.create(ub, con);
        System.out.println("Usuário criado com sucesso!");   
    }

    void readUsuario(Connection con) throws SQLException {
        HashSet all = UsuarioModel.read(con);
        Iterator<UsuarioBean> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }
    
    void updateUsuario(Connection con) throws SQLException, ParseException{
        Scanner input = new Scanner(System.in);
        System.out.println("Informe o id do Usuário que deseja atualizar: ");
        System.out.print("id: ");
        int id = input.nextInt();
        
        System.out.println("Informe os novos dados: ");
        System.out.print("username: ");
        String username = input.next();
        System.out.print("senha: ");
        String senha = input.next();
        System.out.println("nome: ");
        String nome = input.next();
        System.out.println("idade: ");
        int idade = input.nextInt();
        System.out.println("sexo: ");
        char sexo = input.next().charAt(0);
        System.out.println("local: ");
        String local = input.next();
        System.out.println("descricao: ");
        String descricao = input.next();
        System.out.println("email: ");
        String email = input.next();
        System.out.println("papel: ");
        char papel = input.next().charAt(0);
        
        UsuarioBean ub = new UsuarioBean(id, username, senha, nome, idade, sexo, local, descricao, email, papel);
        UsuarioModel.update(ub, con);
        System.out.println("Usuário atualizado com sucesso!");  
    }
    
    void deleteUsuario(Connection con) throws SQLException{
        Scanner input = new Scanner(System.in);
        System.out.println("Informe o id do Usuário que deseja deletar: ");
        System.out.print("id: ");
        int id = input.nextInt();
        
        UsuarioModel.delete(id, con);
        System.out.println("Usuário deletado com sucesso!");
    }
}
