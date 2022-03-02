<?php


function buscar_disciplinas($db, $id_aluno){
  $result = array();

  $sql = "SELECT * FROM `aluno_disciplina` WHERE  id_aluno =".$id_aluno;

  $ret = $db->query($sql);

  while($row = $ret->fetch_assoc()){
   $result[] = $row;
  }

  return $result;
}

$db = new mysqli("localhost","root","214336414","final_odaw");

if($db->connect_error){
  die("Conexão com o DB falhou " . $db->connect_error);
}

$result = array();

if(!isset($_POST['permissao'])){
  die("area restrita:</br>Usuario nao esta logado?");
}

$password_aluno = $_POST['password'];
$username_aluno = $_POST['username'];
$nome_aluno = $_POST['nome'];
$id_aluno = $_POST['id'];
$permissao_aluno = $_POST['permissao'];

if(isset($_POST['buscar_disciplinas'])){

$result['buscar_disciplinas'] = buscar_disciplinas($db, $id_aluno);
}


$db->close();
//nao sei direito o que eh isso
header('Acess-Control-Allow-Origin: *');
header('Content-type: application/json');
//

echo json_encode($result);

?>
