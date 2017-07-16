'use strict';
/**
 * @ngdoc controller
 * @name app.controller.indexCtrl
 * @description 父级作业控制器
 * @author MaytoMarry
 * @date 2017-07-11
 */
app.controller('DelsCtrl', ['$scope', '$window', function ($scope, $window) {
    $scope.$on('$viewContentLoaded', function () {
        var height = $window.innerHeight;
        angular.element('.cover-container.center').css('min-height', (height - 230) + 'px');
    });
}]);