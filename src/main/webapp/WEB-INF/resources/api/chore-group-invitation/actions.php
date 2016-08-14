<?php
    require_once '../api-functions.php';

    function create_chore_group_invitation($chore_group_name, $recipient_email) {
        require_once '../db/connection.php';
        $db_conn = get_std_conn();
        try {
            $query = "INSERT INTO `chore_group_invitation` (`chore_group_id`, `recipient_user_id`, `status`)
                        values ((
                                SELECT `chore_group_id`
                                FROM `chore_group`
                                WHERE `chore_group_name`=?
                                AND `chore_group_owner`=?
                            ),
                            (
                                SELECT `user_id`
                                FROM `user_auth`
                                WHERE `email`=?
                            ),
                            'SENT')
                        ON DUPLICATE KEY UPDATE `status`= IF(`status`='REJECTED', 'SENT', `status`),
                            `last_updated`= IF(`status`='REJECTED', NOW(), last_updated)";


            $statement = $db_conn->prepare ( $query );
            $statement->execute ( array (
                $chore_group_name,
                $_SESSION['user_id'],
                strtolower($recipient_email)
            ) );

        } catch ( Exception $e ) {
            api_error($e->getMessage());
            //api_error('User email not found.');
        }
    }

    function get_chore_group_invitations_with_recipient($recipient_id) {
        require_once '../db/connection.php';
        $db_conn = get_std_conn();

        try {
            $query = "SELECT
                        invite.`chore_group_invitation_id` AS inviteId,
                        user.`email` AS inviter,
                        cgroup.`chore_group_name` AS choreGroupName,
                        cgroup.`chore_group_owner` AS choreGroupOwner,
                        invite.`last_updated` AS lastUpdated
                        FROM `chore_group` cgroup
                        JOIN `chore_group_invitation` invite
                            ON invite.`status` = 'SENT'
                            AND invite.`recipient_user_id` = ?
                            AND invite.`chore_group_id`=cgroup.`chore_group_id`
                        JOIN `user_auth` user
                            ON cgroup.`chore_group_owner`=user.`user_id`
                        ORDER BY lastUpdated";
            $statement = $db_conn->prepare ( $query );
            $statement->execute ( array (
                $recipient_id
            ) );

        } catch ( Exception $e ) {
            api_error($e->getMessage());
        }

        $chore_group_invite_rows = array ();
        while ( $r = $statement->fetch ( PDO::FETCH_ASSOC ) ) {
            $chore_group_invite_rows[] = $r;
        }
        return $chore_group_invite_rows;
    }

    function get_chore_group_invitations_with_sender($sender_id) {
        require_once '../db/connection.php';
        $db_conn = get_std_conn();

        try {
            $query = "SELECT
                        invite.`chore_group_invitation_id` AS inviteId,
                        receiver.`email` AS recipient,
                        cgroup.`chore_group_name` AS choreGroupName,
                        cgroup.`chore_group_owner` AS choreGroupOwner,
                        invite.`last_updated` AS lastUpdated,
                        IF(invite.`status`='ACCEPTED', 'ACCEPTED', 'SENT') AS status
                        FROM `chore_group` cgroup
                        JOIN `chore_group_invitation` invite
                            ON invite.`status` = 'SENT'
                            AND cgroup.`chore_group_owner` = ?
                            AND invite.`chore_group_id`=cgroup.`chore_group_id`
                        JOIN `user_auth` sender
                            ON sender.`user_id`=cgroup.`chore_group_owner`
                        JOIN `user_auth` receiver
                            ON receiver.`user_id`=invite.`recipient_user_id`
                        ORDER BY lastUpdated";
            $statement = $db_conn->prepare ( $query );
            $statement->execute ( array (
                $sender_id
            ) );

        } catch ( Exception $e ) {
            api_error($e->getMessage());
        }

        $chore_group_invite_rows = array ();
        while ( $r = $statement->fetch ( PDO::FETCH_ASSOC ) ) {
            $chore_group_invite_rows[] = $r;
        }
        return $chore_group_invite_rows;
    }

    function accept_invitation($invitation_id) {
                require_once '../db/connection.php';
        $db_conn = get_trans_conn();
        try {
            $db_conn->beginTransaction();
            $query = "UPDATE `chore_group_invitation`
                        SET `status` = 'ACCEPTED',
                            `last_updated` = NOW()
                        WHERE `status`='SENT'
                        AND `recipient_user_id`=?
                        AND `chore_group_invitation_id`=?";


            $statement = $db_conn->prepare ( $query );
            $statement->execute ( array (
                $_SESSION['user_id'],
                $invitation_id
            ) );

            $query = "INSERT INTO `chore_group_member` (`chore_group_id`, `user_id`)
                        values (
                        (
                            SELECT `chore_group_id`
                            FROM `chore_group_invitation`
                            WHERE `chore_group_invitation_id` = ?),
                        ?)";



            $statement = $db_conn->prepare ( $query );
            $statement->execute ( array (
                $invitation_id,
                $_SESSION['user_id']
            ) );

            $db_conn->commit();
        } catch ( Exception $e ) {
            $db_conn->rollback();
            api_error('Invitation not found.');
        }
    }

    function reject_invitation($invitation_id) {
                require_once '../db/connection.php';
        $db_conn = get_std_conn();
        try {
            $query = "UPDATE `chore_group_invitation`
                        SET `status` = 'REJECTED',
                            `last_updated` = NOW()
                        WHERE `status`='SENT'
                        AND `recipient_user_id`=?
                        AND `chore_group_invitation_id`=?";


            $statement = $db_conn->prepare ( $query );
            $statement->execute ( array (
                $_SESSION['user_id'],
                $invitation_id
            ) );

        } catch ( Exception $e ) {
            api_error('Invitation not found.');
        }
    }
?>
