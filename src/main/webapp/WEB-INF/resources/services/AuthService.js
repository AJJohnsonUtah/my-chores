/*global angular*/

angular.module('myChoresApp').factory('authService', ['$http', 'userService', '$location',
    function ($http, userService, $location) {
        'use strict';
        return {
            login: function (username, password) {
                var url, postData, promise;
                url = 'http://' + $location.host() + '/api/user/login';
                postData = {
                    username: username,
                    password: password
                };

                promise = $http.post(url, postData);
                return promise;
            },
            logout: function () {
                var url, promise;
                url = 'http://' + $location.host() + '/api/user/logout';
                promise = $http.get(url);
                userService.setUser(null);
                return promise;
            },
            getCurrentSessionUser: function () {
                var url, promise;
                url = 'http://' + $location.host() + '/api/user/current';
                promise = $http.get(url);
                return promise;
            }
        };
    }]);

angular.module('myChoresApp').run(['authService', 'userService', '$location', function (authService, userService, $location) {
    'use strict';
    authService.getCurrentSessionUser().then(function (response) {
        if(response.data) {
            userService.setUser(response.data);
        } else {
            userService.setUser(null);
            $location.path('/register-login');
        }
    });
}]);
