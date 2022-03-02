<?php

//professor pode inserir, alterar, remover, dar nota para atividade
function buscar_respostas($db, $id_atividade, $id_disciplina){
  // $sql = "SELECT * FROM `aluno_atividades` join (select * from `atividades` WHERE id_disciplina=".$id_disciplina." and id_atividade=".$id_atividade.") as a";


  $sql = "select * from atividades as c INNER JOIN (select * from usuarios join aluno_atividades where id=id_aluno) as a where id_disciplina =".$id_disciplina." and c.id_atividade = a.id_atividade and c.id_atividade=".$id_atividade;
  $result = array();
  $ret = $db->query($sql);

  while($row = $ret->fetch_assoc()){
   $result[] = $row;
  }

  return $result;
}

function inserir_presenca($db, $id_disciplina, $id_aluno, $data){

  $sql = "INSERT INTO `presencas`(`id_aluno`, `id_disciplina`, `data`) VALUES (\"".$id_aluno."\",\"".$id_disciplina."\", \"".$data."\")";
  $db->query($sql);
}

function buscar_presencas($db, $id_disciplina){
  $sql = "SELECT * FROM `presencas` join usuarios WHERE id_aluno=id and id_disciplina=".$id_disciplina;

  $result = array();
  $ret = $db->query($sql);

  while($row = $ret->fetch_assoc()){
   $result[] = $row;
  }

  return $result;
}

function buscar_alunos($db, $id_disciplina){

$sql = "SELECT * FROM `aluno_disciplina` JOIN usuarios WHERE id_disciplina=".$id_disciplina." and id=id_aluno";

$result = array();
$ret = $db->query($sql);

while($row = $ret->fetch_assoc()){
 $result[] = $row;
}

return $result;
}

function avaliar_tarefa($db, $id_atividade, $id_aluno, $nota){

  // $sql = "UPDATE `aluno_atividades` SET `nota`= \"".$nota."\" WHERE id_aluno=".$id_aluno. " and id_atividade=".$id_atividade ;
  $sql = "UPDATE `aluno_atividades` SET `nota`= $nota WHERE id_atividade=$id_atividade and id_aluno=$id_aluno";
  $db->query($sql);
  return $sql;
}

function remover_atividade($db, $id_tarefa){

  $sql = "DELETE FROM `atividades` WHERE id_atividade=".$id_tarefa;
  $db->query($sql);

  $sql ="DELETE FROM `aluno_atividades` WHERE id_atividade=".$id_tarefa;
  $db->query($sql);

}

function editar_tarefa($db, $id_tarefa, $novo_titulo, $nova_descricao){

  $sql = "UPDATE `atividades` SET `titulo`=\"".$novo_titulo."\",`descricao`=\"".$nova_descricao."\" WHERE id_atividade=".$id_tarefa;
  $db->query($sql);
}

function buscar_atividade($db, $id_atividade){

  $result = array();
  $sql = "SELECT * FROM `atividades` WHERE id_atividade=".$id_atividade;

  $ret = $db->query($sql);

  while($row = $ret->fetch_assoc()){
   $result[] = $row;
  }

  return $result;
}

function buscar_atividades($db, $id_disciplina){
  $result = array();

  $sql = "SELECT * FROM `atividades` WHERE id_disciplina=".$id_disciplina;

  $ret = $db->query($sql);

  while($row = $ret->fetch_assoc()){
   $result[] = $row;
  }

  return $result;
}

function inserir_atividade($db, $id_disciplina, $titulo, $descricao){

  $sql= "INSERT INTO `atividades`(`id_atividade`, `id_disciplina`, `titulo`, `descricao`) VALUES (0,\"".$id_disciplina."\",\"".$titulo."\",\"".$descricao."\" )";
  // echo "inserir: ".$sql;
  $db->query($sql);
}

$db = new mysqli("localhost","root","214336414","final_odaw");

if($db->connect_error){
  die("Conexão com o DB falhou " . $db->connect_error);
}

$result = array();

if(!isset($_POST['acao'])){
  die("erro");
}

$acao = $_POST['acao'];
$id_disciplina = $_POST['id_disciplina'];

if($acao == 1){

$result['buscar_atividades'] = buscar_atividades($db, $id_disciplina);
}


if($acao == 0){
$titulo = $_POST['titulo'];
$descricao = $_POST['descricao'];

inserir_atividade($db, $id_disciplina, $titulo, $descricao);
}

if($acao == 2){
$id_tarefa = $_POST['id_tarefa'];

remover_atividade($db, $id_tarefa);
}

if($acao ==3){
$id_tarefa = $_POST['id_tarefa'];

$result['buscar_atividades'] = buscar_atividade($db, $id_tarefa);
}

if($acao ==4 ){
$id_tarefa = $_POST['id_tarefa'];
$novo_titulo = $_POST['novo_titulo'];
$nova_descricao = $_POST['nova_descricao'];

editar_tarefa($db, $id_tarefa, $novo_titulo, $nova_descricao);

}

if($acao ==5 ){
$id_atividade = $_POST['id_atividade'];
$id_aluno = $_POST['id_aluno'];
$nota = $_POST['nota'];

$result ['teste']=avaliar_tarefa($db, $id_atividade, $id_aluno, $nota);

}

if($acao ==6 ){

  $id_tarefa = $_POST['id_tarefa'];
  $id_disciplina = $_POST['id_disciplina'];

  $result['buscar_atividades'] = buscar_respostas($db, $id_tarefa, $id_disciplina);
}

if($acao == 7){

  $id_disciplina=$_POST['id_disciplina'];

  $result['buscar_presencas'] = buscar_presencas($db, $id_disciplina);
}

if($acao == 8){

  $id_disciplina = $_POST['id_disciplina'];

  $result['buscar_alunos'] = buscar_alunos($db, $id_disciplina);

}

if($acao == 9){

  $id_disciplina = $_POST['id_disciplina'];
  $id_aluno = $_POST['id_aluno'];
  $data = $_POST['data'];
  inserir_presenca($db, $id_disciplina, $id_aluno, $data);
}
//obter as atividades
//SELECT * FROM `aluno_atividades` JOIN (select * from `atividades` where id_disciplina = 1) as a

//corrigir as atividades
$db->close();
//nao sei direito o que eh isso
header('Acess-Control-Allow-Origin: *');
header('Content-type: application/json');
//

echo json_encode($result);

?>
