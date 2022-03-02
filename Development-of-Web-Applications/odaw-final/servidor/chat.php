<?php //vamos fazer apenas um chat global, antes nos pensamos em um por disciplina, mas vai ser o mesmo
//obs a tabela chat se chama chat_global, para a gente saber que eh um chat global msm :)

$db = new mysqli("localhost","root","214336414","final_odaw");

if($db->connect_error){
  die("Conexão com o DB falhou " . $db->connect_error);
}

$result = array();
$mensagem = isset($_POST['mensagem']) ? $_POST['mensagem'] : null;
$from = isset($_POST['from']) ? $_POST['from'] : null;

if(!empty($mensagem) && !empty($from)){
  $sql = "INSERT INTO `chat_global` (`mensagem`, `from`) VALUES ('".$mensagem."', '".$from."')";
  $result['send_status'] = $db->query($sql);
}

$start = isset($_GET['start']) ? intval($_GET['start']) : 0;
$items = $db->query("SELECT * FROM `chat_global` WHERE `id` > ". $start);
while($row = $items->fetch_assoc()){
  $result['items'][]= $row;
}

$db->close();

header('Access-Crontrol-Allow-Origin: *');
header('Content-type: application/json');

echo json_encode($result);
//fonte: https://www.youtube.com/watch?v=HOcFFJr2YdE
