<?php
//O aluno pode buscar as atividades e responder

//preenche a resposta na tabela aluno_atividades
function responder_atividade($db, $id_atividade, $id_aluno, $resposta){
  echo "responder_atividade";
}

//retorna as atividades em lista - tabela atividades
function buscar_atividades($db,$id_disciplina){
  echo "buscar_atividades";
}

$db = new mysqli("localhost", "root", "214336414", "final_odaw");
if($db->connect_error){
die("Conexao com DB falhou ".$db->connect_error);
}

//obter por post os elementos

$db->close();

?>
