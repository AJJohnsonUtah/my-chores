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
            if (data.data.status !== 'Okay') {
                $scope.handleUnsuccessfulCreation(data);
            } else {
                $scope.submitLogin($scope.newEmailText, $scope.newPasswordText);
            }
        },
        handleSuccessfulLogin: function (data) {
            if (data.data.status !== 'Okay') {
                $scope.handleUnsuccessfulLogin(data);
                authService.setIsLoggedIn(false);
            } else {
                $location.path('/');
                authService.setIsLoggedIn(true);
            }
        },
        handleUnsuccessfulLogin: function (data) {
            $scope.errorMessage = data.data.reason;
        },
        handleUnsuccessfulCreation: function (data) {
            $scope.newErrorMessage = data.data.reason;
        },
        submitNewUser: function () {
            var url, postData;
            url = '/api/users/new-user.php';
            postData = {
                email: $scope.newEmailText,
                password: $scope.newPasswordText
            };
            if (!$scope.newUserDataIsValid()) {
                return;
            }
            
            $http.post(url, postData).then($scope.handleSuccessfulCreation, $scope.handleUnsuccessfulCreation);
        },
        submitLogin: function (em, pass) {
            var url, postData;
            url = '/api/users/login.php';
            if (em && pass) {
                postData = {
                    email: em,
                    password: pass
                };
            } else {
                postData = {
                    email: $scope.emailText,
                    password: $scope.passwordText
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
