/* Visiting this page will log the user out of the application */

<?php
    session_start();
    session_unset();
    session_destroy();
    $_SESSION = array();
    header('Location: /register-login.php');
?>