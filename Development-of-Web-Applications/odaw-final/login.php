<?php
   ob_start();
   session_start();

 if(isset($_SESSION['username'])){
        echo "[debug] session esta setado: ".$_SESSION['username'];
        die ("Voce ja esta logado <br> <a href =\"/servidor/logout.php\"> Fazer logout ?</a>");

}

?>

<?
   // error_reporting(E_ALL);
   // ini_set("display_errors", 1);
?>

<html>
<head>
  <title>Login</title>

  <script src="https://code.jquery.com/jquery-3.5.0.js"></script>
  <script>

  function mandar(){

    var nome = document.getElementById('username');
    var senha = document.getElementById('pwd');

    console.log(nome.value);
    console.log(senha.value);

    $.post('http://localhost/servidor/fazer_login.php',   // url
       {
         login: 1,
             username: nome.value,
             password: senha.value
     }, // data to be submit
       function(data, status, jqXHR) {// success callback

         console.log(data);
         if(data.login != null){

	console.log("permissao " + data.login[0]['permissao']);

           alert("Logado com sucesso!");
           if(data.login[0]['permissao']==0){
             window.location.href = "/inicial_aluno.php";
           }else if(data.login[0]['permissao']==1){
             window.location.href = "/inicial_professor.php";
           }
          console.log('status: ' + status + ', data: ' + data);
        }else{
 alert('Falha no login, verifique os dados');

}
      });


  }

  function limpa(){

    document.getElementById('username').value="";
    document.getElementById('pwd').value="";

  }


  </script>
</head>

<body>

  <h2>Login</h2>

  <div id="div_main">
  <form>
    <!-- action="/valida_login.php" -->
    <br><H1>Digite o usuario e senha para login:</H1><br>
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" maxlength="12" size=12 required><br>
    <label for="pwd">Password:</label>
    <input type="password" id="pwd" name="pwd" maxlength="12" size="12" required><br>

     <button class="button" type="button" onclick="mandar()" id="logar"> Fazer login </button>
     <button class="button" type="button" onclick="limpa()" id="limpar"> Limpar campos </button>

  </form>

  </div>

</body>

</html>
