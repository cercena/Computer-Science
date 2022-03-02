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

<html>
<head>

<title>Portal rofessor</title>

<script src="https://code.jquery.com/jquery-3.5.0.js"></script>

<script>
var url = 'http://localhost/servidor/chat.php', start=0;

$(document).ready(function(){
  //chat
  load();

  //disciplinas
  $.post('http://localhost/servidor/professor_disciplinas.php',   // url
     {
       buscar_disciplinas: 1,
       id: ` <?php echo "".$_SESSION['id']; ?> `,
       permissao: `<?php echo "".$_SESSION['permissao']; ?> `,
       nome:`<?php echo "".$_SESSION['nome']; ?>`,
           username: `<?php echo "".$_SESSION['username']; ?>`,
           password:` <?php echo "".$_SESSION['password']; ?>`
   }, // data to be submit
   function(data, status, jqXHR) {// success callback

     console.log(data.buscar_disciplinas);

     data.buscar_disciplinas.forEach(item => {
       console.log(item);
       $('#disciplinas').append('<br>' + formatar_disciplina(item));

     });


   });

});

function acessar_disciplina(id_disciplina, nome_disciplina){
    // alert(id_disciplina +";"+ nome_disciplina);
      var myForm = '<form id="ff" action="/disciplina_professor.php" method="POST">\
          <input name="id_disciplina" value="'+id_disciplina+'">\
          <input name="nome_disciplina" value="'+nome_disciplina+'">\
      </form>';
      $('body').append(myForm);
      $('#ff').submit();
      $('#ff').remove();

}

function formatar_disciplina(item){
  return `${item.nome_disciplina} <button class="button" id="${item.id_disciplina}" onclick="acessar_disciplina('${item.id_disciplina}', '${item.nome_disciplina}')">Acessar</button>`;
}

function fazer_logout(){
  window.location.href = "/servidor/logout.php";
}


///////////// Parte do chat (jquery) ///////////////////

function enviar_mensagem(){
    $.post(url, {
      mensagem: $('#mensagem').val(),
      from: `<?php echo $_SESSION['nome']; ?>`
    });
    $('#mensagem').val('');
}

function load(){

  $.get(url+'?start='+start, function(result){
    if(result.items){
      result.items.forEach(item =>{
        start = item.id;
        $('#mensagens').append(renderMessage(item));
      })
    }; load();
  });
}


function renderMessage(item){
  //	console.log(item);
  let time = new Date(item.timestamp);
  time = `${time.getHours()}:${time.getMinutes() < 10 ? '0' : ''}${time.getMinutes()}`;
  return `<div class="msg"> <p> ${item.from} </p>${item.mensagem}<span>${time}</span></div>`;
}

</script>

</head>

<body>

<h1>Bem vindo <?php echo $_SESSION['nome'];?> ao Portal do Professor</h1>

<button class="button" id="logout" onclick="fazer_logout()">Fazer logout?</button>

<div id="disciplinas">
<h2>Disciplinas Ministradas</h2>

</div>

<div style="overflow-y: scroll; height: 200px;" id ="mensagens">
<h4>Chat_global</h4>

</div>
<input type="text" id="mensagem" autocomplete="off" placeholder="Digite sua mensagem ...">
<button class="button" onclick="enviar_mensagem()"> enviar </button>


</body>


</html>
