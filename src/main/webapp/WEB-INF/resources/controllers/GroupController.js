/*global angular, alert */
function registerGroupController($scope, api, $routeParams) {
    "use strict";
    angular.extend($scope, {
        choreGroupUserId: $routeParams.choreGroupUserId        
    });        
}

angular.module('myChoresApp').controller('groupController', ['$scope', 'apiService', '$routeParams', registerGroupController]);