/*global angular */
angular.module('myChoresApp').factory('apiService', ['$http', '$location',
    function ($http, $location) {
        'use strict';
        var BASE_API_URL = 'http://' + $location.host() + '/api';
        var API_URL_PATHS = {
            chore: '/chore',
            choreSpec: '/chore-spec',
            choreGroup: '/chore-group',
            choreGroupUser: '/chore-group-user'
        };
        
        return {
            choreService: {
                
            },
            choreSpecService: {
                
            },
            choreGroupService: {
                create: function (choreGroup) {
                    var url = BASE_API_URL + API_URL_PATHS.choreGroup + '/create';
                    return $http.post(url, choreGroup);
                },            
                update: function (choreGroup) {
                    var url = BASE_API_URL + API_URL_PATHS.choreGroup + '/update';                
                    return $http.post(url, choreGroup);
                },
                delete: function (choreGroup) {
                    var url = BASE_API_URL + API_URL_PATHS.choreGroup + '/delete/' + choreGroup.id;                
                    return $http.delete(url);                    
                },
                getActiveMembers: function (choreGroup) {
                    var url = BASE_API_URL + API_URL_PATHS.choreGroup + '/active-members';
                    return $http.post(url, choreGroup);
                },
                getAllMembers: function (choreGroup) {
                    var url = BASE_API_URL + API_URL_PATHS.choreGroup + '/members';
                    return $http.post(url, choreGroup);
                }
            },
            choreGroupUserService: {
                sendInvite: function (choreGroup, recipientEmail) {
                    var url, postData;
                    url = BASE_API_URL + API_URL_PATHS.choreGroupUser + '/invite';
                    postData = {
                        choreGroup: choreGroup,
                        choreUser: {
                            email: recipientEmail
                        }                    
                    };
                    return $http.post(url, postData);                    
                },
                getSentInvitations: function () {
                    var url = BASE_API_URL + API_URL_PATHS.choreGroupUser + '/find-all-sent';
                    return $http.get(url);
                },
                getReceivedInvitations: function () {
                    var url = BASE_API_URL + API_URL_PATHS.choreGroupUser + '/find-all-received';
                    return $http.get(url);
                },
                acceptInvitation: function (invitation) {
                    var url = BASE_API_URL + API_URL_PATHS.choreGroupUser + '/accept';
                    return $http.post(url, invitation);
                },
                declineInvitation: function (invitation) {
                    var url = BASE_API_URL + API_URL_PATHS.choreGroupUser + '/decline';
                    return $http.post(url, invitation);
                },
                removeChoreGroupUser: function (choreGroupUser) {
                    var url = BASE_API_URL + API_URL_PATHS.choreGroupUser + '/remove';                    
                    return $http.post(url, choreGroupUser);
                },
                updateChoreGroupUser: function (choreGroupUser) {
                    var url = BASE_API_URL + API_URL_PATHS.choreGroupUser + '/update-role';
                    return $http.post(url, choreGroupUser);
                },
                findAll: function () {
                    var url = BASE_API_URL + API_URL_PATHS.choreGroupUser + '/find-all';
                    return $http.get(url);
                }
            }
        };
    }]);