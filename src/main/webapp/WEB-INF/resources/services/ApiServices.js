/*global angular */
//Factory for retrieving static data from the API

angular.module('myChoresApp').factory('choreGroupService', ['$http', '$location',
    function ($http, $location) {
        'use strict';
        return {
            create: function (choreGroup) {
                var url, promise;
                url = 'http://' + $location.host() + '/api/chore-group/create';                
                promise = $http.post(url, choreGroup);
                return promise;
            },            
            update: function (choreGroup) {
                var url, promise, postData;
                url = 'http://' + $location.host() + '/api/chore-group/update';                
                postData = {
                    choreGroupName: choreGroup.choreGroupName,
                    id: choreGroup.id
                };
                promise = $http.post(url, choreGroup);
                return promise;
            },
            readAll: function () {
                var url, promise;
                url = 'http://' + $location.host() + '/api/chore-group/read-all';
                promise = $http.get(url);
                return promise;
            },
            getActiveMembers: function (choreGroup) {
                var url, promise;
                url = 'http://' + $location.host() + '/api/chore-group/active-members';
                promise = $http.post(url, choreGroup);
                return promise;
            },
            getAllMembers: function (choreGroup) {
                var url, promise;
                url = 'http://' + $location.host() + '/api/chore-group/members';
                promise = $http.post(url, choreGroup);
                return promise;
            }
        };
    }]);

angular.module('myChoresApp').factory('choreGroupInvitationService', ['$http', '$location',
    function ($http, $location) {
        'use strict';
        return {
            sendInvite: function (choreGroup, recipientEmail) {
                var url, postData, promise;
                url = 'http://' + $location.host() + '/api/chore-group-user/invite';
                postData = {
                    choreGroup: choreGroup,
                    choreUser: {
                        email: recipientEmail
                    }                    
                };

                promise = $http.post(url, postData);
                return promise;
            },
            getSentInvitations: function () {
                var url, promise;
                url = 'http://' + $location.host() + '/api/chore-group-user/find-all-sent';
                promise = $http.get(url);
                return promise;
            },
            getReceivedInvitations: function () {
                var url, promise;
                url = 'http://' + $location.host() + '/api/chore-group-user/find-all-received';
                promise = $http.get(url);
                return promise;
            },
            acceptInvitation: function (invitation) {
                var url, postData, promise;
                url = 'http://' + $location.host() + '/api/chore-group-user/accept';
                postData = invitation;
                promise = $http.post(url, postData);
                return promise;
            },
            declineInvitation: function (invitation) {
                var url, postData, promise;
                url = 'http://' + $location.host() + '/api/chore-group-user/decline';
                postData = invitation;
                promise = $http.post(url, postData);
                return promise;
            },
            removeChoreGroupUser: function (choreGroupUser) {
                var url, postData, promise;
                url = 'http://' + $location.host() + '/api/chore-group-user/remove';
                postData = choreGroupUser;
                promise = $http.post(url, postData);
                return promise;
            },
            updateChoreGroupUserRole: function (choreGroupUser) {
                var url, postData, promise;
                url = 'http://' + $location.host() + '/api/chore-group-user/update-role';
                postData = {
                    id: choreGroupUser.id,
                    choreGroupUserRole: choreGroupUser.choreGroupUserRole
                };
                promise = $http.post(url, postData);
                return promise;
            }
        }; 
    }]);
