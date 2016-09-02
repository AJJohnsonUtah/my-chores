angular.module('myChoresApp').controller('userController', ['authService', '$scope', '$location', function (authService, $scope, $location) {
    'use strict';
    angular.extend($scope, {
        authData: authService.getAuthServiceData(),
        logout: function () {
            authService.logout().then(function (response) {
                $location.path('/register-login');
            });
        }        
    });
}]);
