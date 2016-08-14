/*global angular */
//Factory for retrieving static data from the API

angular.module('myChoresApp').factory('choreGroupService', ['$http', '$location',
    function ($http, $location) {
        'use strict';
        return {
            create: function (choreGroupName) {
                var url, postData, promise;
                url = 'http://' + $location.host() + '/api/chore-group/create.php';
                postData = {
                    chore_group_name: choreGroupName
                };
                
                promise = $http.post(url, postData);
                return promise;
            },
            readAll: function () {
                var url, promise;
                url = 'http://' + $location.host() + '/api/chore-group/read-all.php';
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
                url = 'http://' + $location.host() + '/api/chore-group-invitation/send-invite.php';
                postData = {
                    chore_group_name: choreGroupName,
                    recipient_email: recipientEmail
                };

                promise = $http.post(url, postData);
                return promise;
            },
            getSentInvitations: function () {
                var url, promise;
                url = 'http://' + $location.host() + '/api/chore-group-invitation/read-all-sent.php';
                promise = $http.get(url);
                return promise;
            },
            getReceivedInvitations: function () {
                var url, promise;
                url = 'http://' + $location.host() + '/api/chore-group-invitation/read-all-received.php';
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
