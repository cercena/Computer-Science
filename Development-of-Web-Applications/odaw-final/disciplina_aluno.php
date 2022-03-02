<?php
   ob_start();
   session_start();

 if(isset($_SESSION['permissao'])){

   if($_SESSION['permissao']!=0){
        echo "area restrita: ".$_SESSION['username']."</br>";
        die ("Ir para <br> <a href =\"/inicial_professor.php\"> Portal do professor ?</a>");
      }

}else{
        die ("area restrita: <br> <a href =\"/login.php\"> Fazer Login ?</a>");
}


?>

<?
   // error_reporting(E_ALL);
   // ini_set("display_errors", 1);
?>

<html>
<head>

<title>Disciplina - aluno</title>

</head>

<body>

Botao gerar boletim

Lista de atividades

Responder as atividades

Verificar presença

</body>


</html>
