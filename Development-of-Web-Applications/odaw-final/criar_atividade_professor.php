<?php
   ob_start();
   session_start();

 if(isset($_SESSION['permissao'])){

   if($_SESSION['permissao']!=1){
        echo "area restrita: ".$_SESSION['username']."</br>";
        die ("Ir para <br> <a href =\"/inicial_aluno.php\"> Portal do aluno ?</a>");
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

<title>Disciplinas</title>

</head>

<body>

Formularios para criar uma atividade em uma disciplina especifica
- Talvez seja melhor deixar o link para isso aqui na pagina de disciplinas e aqui podendo escolher a disciplina a qual vincular

</body>


</html>
