<?php

//professor pode inserir, alterar, remover, dar nota para atividade

function fechar_notas_disciplina($db, $id_disciplina){

//faz a media da nota de todas as atividades de cada aluno e atribui a nota dele na tabela aluno disciplina

}

function buscar_disciplinas($db, $id_professor){
  $result = array();

  $sql = "SELECT * FROM `professor_disciplina` WHERE id_professor =".$id_professor;

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

$password_professor = $_POST['password'];
$username_professor = $_POST['username'];
$nome_professor = $_POST['nome'];
$id_professor = $_POST['id'];
$permissao_professor = $_POST['permissao'];

if(isset($_POST['buscar_disciplinas'])){

$result['buscar_disciplinas'] = buscar_disciplinas($db, $id_professor);
}


$db->close();
//nao sei direito o que eh isso
header('Acess-Control-Allow-Origin: *');
header('Content-type: application/json');
//

echo json_encode($result);

?>
