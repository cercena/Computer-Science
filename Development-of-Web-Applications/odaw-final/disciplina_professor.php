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

if(!isset($_POST['id_disciplina'])){
    die("<br>Nenhuma disciplina selecionada</br>");
  }

?>

<?
   // error_reporting(E_ALL);
   // ini_set("display_errors", 1);
?>

<html>
<head>

<title>Disciplina - professor</title>
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>

<script>

var title = "<?php echo $_POST['nome_disciplina']; ?>";

$(document).ready(function() {
    document.title = 'Disciplina '+ title +" - professor";

    //post -> obter todas as tarefas da disciplina e adicionar na div tarefas
    update_tarefas();
});

function update_tarefas(){

  $.post('http://localhost/servidor/professor_atividade.php',   // url
     {
       acao: 1,
       id_disciplina: ` <?php echo "".$_POST['id_disciplina']; ?> `,

   }, // data to be submit
   function(data, status, jqXHR) {// success callback

     console.log(data.buscar_atividades);

     data.buscar_atividades.forEach(item => {
       console.log(item);
           $('#tarefas').append("<h4>"+item['titulo']+"<h4>" + "<button class=\"button\" onclick=\"corrigir_tarefa("+item['id_atividade']+")\">corrigir</button>"+ "<button class=\"button\" onclick=\"editar_tarefa("+item['id_atividade']+")\">editar</button>"+ "<button class=\"button\" onclick=\"remover_tarefa("+item['id_atividade']+")\">remover</button> </br>");
     });


   });

}


function fazer_logout(){
  window.location.href = "/servidor/logout.php";
}

function verificar_presenca(){
$('#tarefas').hide();

$('body').append(`<div id=\"div_tarefa\"> <button class=\"button\" id=\"cancelar_tarefa\" onclick=\"criar_tarefa_cancelar()\">Cancelar</button></br>`);
$.post('http://localhost/servidor/professor_atividade.php',   // url
   {
     acao: 7,
     id_disciplina: ` <?php echo "".$_POST['id_disciplina']; ?> `,
 }, // data to be submit
 function(data, status, jqXHR) {// success callback

   console.log(data.buscar_presencas);

   data.buscar_presencas.forEach(item => {
     console.log(item);
         $('#div_tarefa').append(item['nome'] + "-> presença:" +item['data']+" </br>");
   });


 });
$('body').append(`</div>`);

}

function registrar_presenca(){
$('#tarefas').hide();


$('body').append(`<div id=\"div_tarefa\"> Data <input type="date" id="data" name="data"> </br><button class=\"button\" id=\"cancelar_tarefa\" onclick=\"criar_tarefa_cancelar()\">Cancelar</button>\ </br>`);

$.post('http://localhost/servidor/professor_atividade.php',   // url
   {
     acao: 8,
     id_disciplina: ` <?php echo "".$_POST['id_disciplina']; ?> `,
 }, // data to be submit
 function(data, status, jqXHR) {// success callback

   console.log(data.buscar_alunos);

   data.buscar_alunos.forEach(item => {
     console.log(item);
         $('#div_tarefa').append(item['nome'] + "-> presença: <input type=\"text\" name=\"presenca\" id=\"presenca\""+item['id_aluno']+"> <button class=\"button\" onclick=\"marcar_presenca("+item['id_aluno']+")\">ok</button> </br>");
   });


 });
$('body').append(`</div>`);

}

function marcar_presenca(id_aluno){

  // if($('#presenca'+id_aluno).val() >=1){ //marcar a presenca

    console.log(id_aluno);
    console.log($('#data').val());

  $.post('http://localhost/servidor/professor_atividade.php',   // url
     {
       acao: 9,
       id_disciplina: ` <?php echo "".$_POST['id_disciplina']; ?> `,
       id_aluno: id_aluno,
       data: $('#data').val(),
   });
 // }

 criar_tarefa_cancelar();
 $('#tarefas').empty();
 $('#tarefas').append("<h1>Tarefas da disciplina</h1>");
 update_tarefas();

}

function criar_tarefa(){


  $('#tarefas').hide();

  $('body').append(`<div id=\"div_tarefa\"> <h1> Criação de tarefas</h1>\

  Titulo: <input type=\"text\" name=\"titulo\" id=\"titulo_tarefa\"><br>\

  Comentario: <textarea id=\"comentario_tarefa\" name=\"comentario\" rows=\"4\" cols=\"50\"></textarea><br>\

<button class=\"button\" id=\"registrar_presenca\" onclick=\"criar_tarefa_confirmar()\">Confirmar</button>\
<button class=\"button\" id=\"registrar_presenca\" onclick=\"criar_tarefa_cancelar()\">Cancelar</button>\
  </div>`);
}

function criar_tarefa_cancelar(){
  $('#tarefas').show();
  $('#div_tarefa').remove();
}

function criar_tarefa_confirmar(){


var id_disciplina = `<?php echo $_POST['id_disciplina']?>`, titulo = $('#titulo_tarefa').val(), descricao = $('#comentario_tarefa').val();
//acao: 0 = incluir atividade
$.post("http://localhost/servidor/professor_atividade.php", {
  acao:0,
  id_disciplina: id_disciplina,
  titulo: titulo,
  descricao: descricao
});

alert('tarefa_criada');

criar_tarefa_cancelar();
$('#tarefas').empty();
$('#tarefas').append("<h1>Tarefas da disciplina</h1>");
update_tarefas();
}

function editar_tarefa(id_tarefa){

  $('#tarefas').hide();

$.post('http://localhost/servidor/professor_atividade.php',   // url
   {
     acao: 3,//buscar tarefa
     id_tarefa: id_tarefa
 }, // data to be submit
 function(data, status, jqXHR) {// success callback

   console.log(data.buscar_atividades);

   data.buscar_atividades.forEach(item => {
     console.log("Aqui"+item);

     $('body').append(`<div id=\"div_tarefa\"> <h1> Editar tarefa</h1>\

     Titulo: <input type=\"text\" name=\"titulo\" id=\"titulo_tarefa\" value=\"`+item.titulo +`"\"><br>\

     Comentario: <textarea id=\"comentario_tarefa\" name=\"comentario\" rows=\"4\" cols=\"50\"> `+item['descricao']+`</textarea><br>\

  <button class=\"button\" id=\"editar_tarefa\" onclick=\"editar_tarefa_confirmar('${item.id_atividade}')\">Confirmar</button>\
   <button class=\"button\" id=\"cancelar_tarefa\" onclick=\"criar_tarefa_cancelar()\">Cancelar</button>\
     </div>`);

   });


 });

}

function editar_tarefa_confirmar(id_tarefa){

  var novo_titulo = $('#titulo_tarefa').val(), nova_descricao = $('#comentario_tarefa').val();

  //
  $.post('http://localhost/servidor/professor_atividade.php',   // url
     {
       acao: 4,//editar tarefa
       id_tarefa: id_tarefa,
       novo_titulo: novo_titulo,
       nova_descricao: nova_descricao
   }
 );

   criar_tarefa_cancelar();
   $('#tarefas').empty();
   $('#tarefas').append("<h1>Tarefas da disciplina</h1>");
   update_tarefas();
}

function remover_tarefa(id_tarefa){
alert("tarefa removida");

$.post('http://localhost/servidor/professor_atividade.php',   // url
   {
     acao: 2,
     id_tarefa: id_tarefa
 }
);
$('#tarefas').empty();
$('#tarefas').append("<h1>Tarefas da disciplina</h1>");
update_tarefas();
}

function corrigir_tarefa(id_tarefa){

  $('body').append(`<div id=\"div_tarefa\">`);

  $.post('http://localhost/servidor/professor_atividade.php',   // url
     {
       acao: 6,//buscar tarefa join aluno_atividades
       id_tarefa: id_tarefa,
       id_disciplina: ` <?php echo "".$_POST['id_disciplina']; ?> `
   }, // data to be submit
   function(data, status, jqXHR) {// success callback

     console.log(data.buscar_atividades);

     data.buscar_atividades.forEach(item => {

       $('#div_tarefa').append(item['nome']+`<textarea id=\"comentario_tarefa\" name=\"comentario\" rows=\"4\" cols=\"50\"> `+item['resposta']+`</textarea><br>\

       nota: <input type=\"text\" name=\"resposta\" id=\"nota_tarefa`+item['id_aluno']+`\" value=\"`+item['nota']+`\"><br>\

     <button class=\"button\" id=\"corrigr_tarefa_confirmar\" onclick=\"corrigir_tarefa_confirmar('${item.id_aluno}', '${item.id_atividade}')\">Confirmar</button>\
     <button class=\"button\" id=\"cancelar_tarefa\" onclick=\"criar_tarefa_cancelar()\">Cancelar</button> </br>`);

     });
   });

$('body').append(`</div>`);

}


function corrigir_tarefa_confirmar(id_aluno, id_atividade){

var nota = $('#nota_tarefa'+id_aluno).val();

console.log(nota+"; "+id_aluno+"; "+id_atividade);

$.post('http://localhost/servidor/professor_atividade.php',   // url
   {
     acao: 5,//avaliar tarefa
     id_atividade: id_atividade,
     id_aluno: id_aluno,
     nota: nota
 });

 criar_tarefa_cancelar();
 $('#tarefas').empty();
 $('#tarefas').append("<h1>Tarefas da disciplina</h1>");
 update_tarefas();

}

</script>
</head>

<body>

  <h1> Bem-vindo professor!</h1> </br>

<button class="button" id="logout" onclick="fazer_logout()">Fazer logout?</button>
<button class="button" id="registrar_presenca" onclick="registrar_presenca()">Registar presença</button>
<button class="button" id="verificar_presenca" onclick="verificar_presenca()">Verificar presenças</button>
<button class="button" id="criar_tarefa" onclick="criar_tarefa()">Criar tarefa</button>

<div id="tarefas">
<h2>Tarefas da disciplina</h2>

</div>



<!--
Cada atividade deve conter esses dois botoes
 <button class="button" id="logout" onclick="fazer_logout()">Editar tarefa</button>
<button class="button" id="logout" onclick="fazer_logout()">Avaliar tarefas</button>
 -->

</body>


</html>
