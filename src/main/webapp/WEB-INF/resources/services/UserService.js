/*global angular*/

angular.module('myChoresApp').factory('userService', ['$interval', 'apiService',
    function ($interval, api) {
        'use strict';        

        var userServiceData = {'user': null, 'sentInvitations': null, 'receivedInvitations': null};

        // Poll for updates of user data every 30 seconds
        $interval(pollForUpdates, 30000);
        
        function pollForUpdates() {
            if(getUser() !== null) {
                reloadSentInvitations();
                reloadReceivedInvitations();
            }
        }       
        
        function reloadSentInvitations () {
            api.choreGroupUserService.getSentInvitations().success(function(responseData) {
                setSentInvitations(responseData);
            });
        }
        
        function reloadReceivedInvitations () {
            api.choreGroupUserService.getReceivedInvitations().success(function(responseData) {
                setReceivedInvitations(responseData);
            });
        }
        
        function isLoggedIn () {
            return getUser() !== null;
        }

        function getUser () {
            return userServiceData.user;
        }
        
        function setUser (user) {
            userServiceData.user = user;
            pollForUpdates();
        }
        
        function getSentInvitations () {
            return userServiceData.sentInvitations;
        }
        
        function setSentInvitations (sentInvitations) {
            userServiceData.sentInvitations = sentInvitations;
        }
        
        function getReceivedInvitations () {
            return userServiceData.receivedInvitations;
        }
        
        function setReceivedInvitations (receivedInvitations) {
            userServiceData.receivedInvitations = receivedInvitations;
        }
        
        function getUserServiceData () {
            return userServiceData;
        }

        return {
            isLoggedIn: isLoggedIn,
            getUser: getUser,
            setUser: setUser,
            getSentInvitations: getSentInvitations,
            setSentInvitations: setSentInvitations,
            getReceivedInvitations: getReceivedInvitations ,
            setReceivedInvitations: setReceivedInvitations,
            getUserServiceData: getUserServiceData,
            reloadSentInvitations: reloadSentInvitations,
            reloadReceivedInvitations: reloadReceivedInvitations
        };
    }]);