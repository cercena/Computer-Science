<?php
session_start();
$db = new mysqli("localhost","root","214336414","final_odaw");

if($db->connect_error){
  die("Conexão com o DB falhou " . $db->connect_error);
}

$result = array();

$login = isset($_POST['login'])? intval($_POST['login']): -1;

if($login != -1){

  $usr_nome = $_POST['username']; $usr_senha = $_POST['password'];
  //echo "login:".$login."; username:".$usr_nome;

  $sql_login = "SELECT `id`, `nome`, `senha`, `permissao`, `username` FROM `usuarios` WHERE username = \"".$usr_nome."\" and senha=\"".$usr_senha."\"";

  $ret = $db->query($sql_login);
  $contador=0;

  while($row = $ret->fetch_assoc()){
  	$result['login'][] = $row;
    $_SESSION['username'] = $row['username'];
    $_SESSION['permissao'] = $row['permissao'];
    $_SESSION['nome'] = $row['nome'];
    $_SESSION['id'] = $row['id'];
    $_SESSION['password'] = $row['senha'];

  }

}

$db->close();

header('Acess-Control-Allow-Origin: *');
header('Content-type: application/json');
echo json_encode($result);

?>
