<?php
    require_once '../api-functions.php';

    function create_chore_group($chore_group_name) {
        require_once '../db/connection.php';
        $db_conn = get_trans_conn();
        try {
            $db_conn->beginTransaction();

            $query = "INSERT INTO `chore_group` (`chore_group_name`, `chore_group_owner`) values (?,?)";

            $statement = $db_conn->prepare ( $query );
            $statement->execute ( array (
                    $chore_group_name,
                    $_SESSION['user_id']
            ) );

            $query = "INSERT INTO `chore_group_member` (`chore_group_id`, `user_id`) values ((SELECT `chore_group_id` FROM `chore_group` WHERE `chore_group_owner` = ? AND `chore_group_name` = ?),?)";

            $statement = $db_conn->prepare ( $query );
            $statement->execute ( array (
                    $_SESSION['user_id'],
                    $chore_group_name,
                    $_SESSION['user_id']
            ) );
            $db_conn->commit();
        } catch ( Exception $e ) {
            $db_conn->rollback();
            api_error($e->getMessage());
        }
    }

    function get_chore_groups_with_owner($owner_id) {
        require_once '../db/connection.php';
        $db_conn = get_std_conn();

        try {
            $query = "SELECT
                        `chore_group_id` AS choreGroupId,
                        `chore_group_name` AS choreGroupName,
                        `chore_group_owner` AS choreGroupOwner
                        FROM `chore_group`
                        WHERE `chore_group_owner`=?
                        ORDER BY `choreGroupName`";
            $statement = $db_conn->prepare ( $query );
            $statement->execute ( array (
                $owner_id
            ) );

        } catch ( Exception $e ) {
            api_error($e->getMessage());
        }

        $chore_group_rows = array ();
        while ( $r = $statement->fetch ( PDO::FETCH_ASSOC ) ) {
            $chore_group_rows[] = $r;
        }
        return $chore_group_rows;
    }

    function get_chore_groups_with_user($user_id) {
        require_once '../db/connection.php';
        $db_conn = get_std_conn();

        try {
            $query = "SELECT
                        a.`chore_group_id` AS choreGroupId,
                        a.`chore_group_name` AS choreGroupName,
                        a.`chore_group_owner` AS choreGroupOwner
                        FROM `chore_group` a
                        JOIN `chore_group_member` b
                        ON a.`chore_group_id` = b.`chore_group_id`
                        AND b.`user_id` = ?
                        ORDER BY `chore_group_owner`";
            $statement = $db_conn->prepare ( $query );
            $statement->execute ( array (
                $user_id
            ) );

        } catch ( Exception $e ) {
            api_error($e->getMessage());
        }

        $chore_group_rows = array ();
        while ( $r = $statement->fetch ( PDO::FETCH_ASSOC ) ) {
            $chore_group_rows[] = $r;
        }
        return $chore_group_rows;
    }

?>
