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
                create: function (chore) {
                    var url = BASE_API_URL + API_URL_PATHS.chore + '/create';
                    return $http.post(url, chore);
                },
                update: function (chore) {
                    var url = BASE_API_URL + API_URL_PATHS.chore + '/update';                
                    return $http.post(url, chore);
                },
                getActiveChoresForChoreGroupUser: function (choreGroupUser) {                    
                    var url = BASE_API_URL + API_URL_PATHS.chore + '/chore-group-user/' + choreGroupUser.id;
                    return $http.get(url);
                },
                getActiveChoresForCurrentUser: function () {
                    var url = BASE_API_URL + API_URL_PATHS.chore + '/current-user';
                    return $http.get(url);
                },
                getActiveChoresForChoreGroup: function (choreGroup) {
                    var url = BASE_API_URL + API_URL_PATHS.chore + '/chore-group/' + choreGroup.id;
                    return $http.get(url);
                },
                getCompletedChoresForCurrentUser: function () {
                    var url = BASE_API_URL + API_URL_PATHS.chore + '/current-user/completed';
                    return $http.get(url);
                }
            },
            choreSpecService: {
                create: function (choreSpec) {
                    var url = BASE_API_URL + API_URL_PATHS.choreSpec + '/create';
                    return $http.post(url, choreSpec);
                },
                update: function (choreSpec) {
                    var url = BASE_API_URL + API_URL_PATHS.choreSpec + '/update';                
                    return $http.post(url, choreSpec);
                },
                find: function (choreSpec) {
                    var url = BASE_API_URL + API_URL_PATHS.choreSpec + '/find/' + choreSpec.id;
                    return $http.get(url);
                },
                findChoresOfSpec: function (choreSpec) {
                    var url = BASE_API_URL + API_URL_PATHS.choreSpec + '/' + choreSpec.id + '/chores';
                    return $http.get(url);
                }
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
                },
                getChoreSpecs: function (choreGroup) {
                    var url = BASE_API_URL + API_URL_PATHS.choreGroup + '/chore-specs/' + choreGroup.id;
                    return $http.get(url);
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
                    var url = BASE_API_URL + API_URL_PATHS.choreGroupUser + '/accept/' + invitation.id;
                    return $http.get(url);
                },
                declineInvitation: function (invitation) {
                    var url = BASE_API_URL + API_URL_PATHS.choreGroupUser + '/decline/' + invitation.id;
                    return $http.get(url);
                },
                removeChoreGroupUser: function (choreGroupUser) {
                    var url = BASE_API_URL + API_URL_PATHS.choreGroupUser + '/remove/' + choreGroupUser.id;                    
                    return $http.get(url);
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