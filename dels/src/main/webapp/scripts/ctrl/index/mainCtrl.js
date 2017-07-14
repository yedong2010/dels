'use strict';
/**
 * @ngdoc controller
 * @name app.controller.mainCtrl
 * @description 父级作业控制器
 * @author MaytoMarry
 * @date 2017-07-11
 */
app.controller('mainCtrl', ['$scope', '$state', '$location', 'commonService', '$rootScope','toastr', function ($scope, $state, $location, commonService, $rootScope,toastr) {

    $scope.content = '';

    var vm = $scope.vm ={};

    $scope.query = false;

    $scope.search = function () {
        if($scope.content == '' || $scope.content.trim().length == 0){

            return;
        }
        $scope.query = true;
        var param ={};
        param.acid = $scope.content;
        commonService.queryByParam('index/queryaccbyid', param).then(function (data) {
            vm.items = data;
            $scope.query = false;
        }, function () {
            toastr.error('查询请求失败.');
            $scope.query = false;
        })
    }

}]);