/*global angular, alert */
function registerGroupController($scope, choreGroupService, choreGroupInvitationService, userService, $routeParams) {
    "use strict";
    angular.extend($scope, {
        choreGroupUserId: $routeParams.choreGroupUserId
    });    
    
}

angular.module('myChoresApp').controller('groupController', ['$scope', 'choreGroupService', 'choreGroupInvitationService', 'userService', '$routeParams', registerGroupController]);