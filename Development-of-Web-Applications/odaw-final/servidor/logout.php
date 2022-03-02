<?php
session_start();
unset($_SESSION['username']);
unset($_SESSION['password']);
unset($_SESSION['permissao']);
unset($_SESSION['nome']);

unset($_SESSION['id']);

echo "clear: ". $_SESSION['username'];

header('Location: http://localhost/login.php');

 ?>
