'use strict';
/**
 * @ngdoc controller
 * @name app.controller.messageCtrl
 * @description 父级作业控制器
 * @author MaytoMarry
 * @date 2017-07-11
 */
app.controller('messageCtrl', ['$scope', '$state', '$location', 'commonService', '$rootScope','toastr', function ($scope, $state, $location, commonService, $rootScope,toastr) {

    $scope.content = '';

    $scope.commit = false;

    var vm = $scope.vm ={};

    vm.page = {
        size: 10,
        index: 1
    };

    $scope.init = function(){
        $scope.getMessages();
    }

    $scope.message = function () {
        if($scope.content == '' || $scope.content.trim().length == 0){
            return;
        }
        $scope.commit = true;
        var param ={};
        param.content = $scope.content;
        commonService.queryByParam('index/insercontent', param).then(function (data) {
            if(data.msg == 'success'){
                toastr.success('评论成功.');
                $scope.getMessages();
                $scope.content = '';
            }else{
                toastr.error('评论请求失败.')
            }
            $scope.commit = false;
        }, function () {
            toastr.error('评论请求失败.');
            $scope.commit = false;
        })
    }

    $scope.getMessages = function () {
        commonService.getRequest('index/querycontents').then(function (data) {
            vm.items = data;
        }, function () {
            toastr.error('获取评论列表失败.');
        })
    }

    $scope.init();

}]);