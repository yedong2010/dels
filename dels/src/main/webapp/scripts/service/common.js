/**
 * @ngdoc service
 * @name app.commonService
 * @description 公共service服务
 * @author MaytoMarry
 * @date 2017-07-12
 */
app.factory('commonService', ['$http', '$q', '$uibModal','$interval', function ($http, $q) {
    var service = {};

    service.queryByParam = function (url, param) {
        var deferred = $q.defer();
        $http.post(url, param).success(function (data) {
            deferred.resolve(data);
        }).error(function () {
            deferred.reject('There was an error (post)');
        });
        return deferred.promise;
    };

    service.getRequest = function (url) {
        var deferred = $q.defer();
        $http.get(url).success(function (data) {
            deferred.resolve(data);
        }).error(function () {
            deferred.reject('There was an error (get)');
        });
        return deferred.promise;
    };

    return service;
}]);