/*global angular, alert */
function registerLoginController($http, $scope, $location, authService) {
    "use strict";
    angular.extend($scope, {
        newEmailText: '',
        newPasswordText: '',
        newPasswordVerifyText: '',
        emailText: '',
        passwordText: '',
        errorMessage: '',
        newErrorMessage: '',
        newUserDataIsValid: function () {
            if ($scope.newPasswordText.length < 8 || $scope.newPasswordText !== $scope.newPasswordVerifyText) {
                return false;
            }
            return true;
        },
        handleSuccessfulCreation: function (data) {
            $scope.submitLogin($scope.newEmailText, $scope.newPasswordText);            
        },
        handleSuccessfulLogin: function (data) {            
            $location.path('/');
            authService.setIsLoggedIn(true);            
        },
        handleUnsuccessfulLogin: function (response) {
            $scope.errorMessage = response.data.message;
        },
        handleUnsuccessfulCreation: function (response) {
            $scope.newErrorMessage = response.data.message;
        },
        submitNewUser: function () {
            var url, postData;
            url = '/api/user/create';
            postData = {
                "email": $scope.newEmailText,
                "password": $scope.newPasswordText
            };
            if (!$scope.newUserDataIsValid()) {
                return;
            }
            
            $http.post(url, postData).then($scope.handleSuccessfulCreation, $scope.handleUnsuccessfulCreation);
        },
        submitLogin: function (em, pass) {
            var url, postData;
            url = '/api/user/login';
            if (em && pass) {
                postData = {
                    "email": em,
                    "password": pass
                };
            } else {
                postData = {
                    "email": $scope.emailText,
                    "password": $scope.passwordText
                };
            }
            
            $http.post(url, postData).then($scope.handleSuccessfulLogin, $scope.handleUnsuccessfulLogin);
        }
    });

    authService.checkLoginStatus().then(function (response) {
        if (response.data.status === 'Okay') {
            $location.path('/');
        }
    });
}

angular.module('myChoresApp').controller('registerLoginController', ['$http', '$scope', '$location', 'authService', registerLoginController]);
