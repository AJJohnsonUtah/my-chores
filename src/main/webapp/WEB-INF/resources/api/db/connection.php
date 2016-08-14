<?php
    function get_std_conn () {
        $db_username = 'web_chores';
        $db_password = 'S1r_cho0res^4L0T';
        $database = 'chores';
        /* Connect to the DB */
        $db_conn = new PDO ( 'mysql:host=localhost;dbname=' . $database, $db_username, $db_password );
        // Report PDO errors loudly
        $db_conn->setAttribute ( PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION );
        return $db_conn;
    }
    function get_trans_conn () {
        $db_username = 'web_chores';
        $db_password = 'S1r_cho0res^4L0T';
        $database = 'chores';
        /* Connect to the DB */
        $db_conn = new PDO ( 'mysql:host=localhost;dbname=' . $database, $db_username, $db_password );
        // Report PDO errors loudly
        $db_conn->setAttribute ( PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION );
        $db_conn->setAttribute ( PDO::ATTR_PERSISTENT, true );
        return $db_conn;
    }
?>
