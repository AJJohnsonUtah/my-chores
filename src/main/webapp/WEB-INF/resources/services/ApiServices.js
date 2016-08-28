/*global angular */
//Factory for retrieving static data from the API

angular.module('myChoresApp').factory('choreGroupService', ['$http', '$location',
    function ($http, $location) {
        'use strict';
        return {
            create: function (choreGroupName) {
                var url, postData, promise;
                url = 'http://' + $location.host() + '/api/chore-group/create';
                postData = {
                    choreGroupName: choreGroupName                    
                };
                
                promise = $http.post(url, postData);
                return promise;
            },
            readAll: function () {
                var url, promise;
                url = 'http://' + $location.host() + '/api/chore-group/read-all';
                promise = $http.get(url);
                return promise;
            }
        };
    }]);

angular.module('myChoresApp').factory('choreGroupInvitationService', ['$http', '$location',
    function ($http, $location) {
        'use strict';
        return {
            sendInvite: function (choreGroupName, recipientEmail) {
                var url, postData, promise;
                url = 'http://' + $location.host() + '/api/chore-group-user/invite';
                postData = {
                    choreGroup: {
                        choreGroupName: choreGroupName
                    },
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
            acceptInvitation: function (invitationId) {
                var url, postData, promise;
                url = 'http://' + $location.host() + '/api/chore-group-invitation/accept-invite.php';
                postData = {
                    invitation_id: invitationId
                };
                promise = $http.post(url, postData);
                return promise;
            },
            rejectInvitation: function (invitationId) {
                var url, postData, promise;
                url = 'http://' + $location.host() + '/api/chore-group-invitation/reject-invite.php';
                postData = {
                    invitation_id: invitationId
                };
                promise = $http.post(url, postData);
                return promise;
            }
        };
    }]);
