'use strict';
/**
 * @ngdoc controller
 * @name app.controller.discussDetailCtrl
 * @requires $scope作用域,$state路由,$timeout延时执行,$stateParams路由参数控制,commonService
 * @description 楼主发布帖--主题帖发布和编辑
 * @author z11595
 * @date 2017-1-12
 */
app.controller('discussDetailCtrl', ['$scope', 'textAngularManager', 'commonService', '$rootScope', 'toastr', '$state', '$http', '$stateParams', function ($scope, textAngularManager, commonService, $rootScope, toastr, $state, $http, $stateParams) {
    commonService.queryData('chat/query/' + $stateParams.id).then(function (data) {
        $scope.item = data;
    });
    $scope.count = 100;
    $scope.addReply = function () {
        if($scope.speak != null && $scope.speak != ''){
            $http.post('chat/creply', {
                ctime: new Date(),
                content: $scope.speak,
                uname: $rootScope.uname,
                uid: $stateParams.id
            }).then(function () {
                toastr.success('评论成功，待管理员审核');
                $scope.speak = '';//清空当前评论
                commonService.queryData('chat/query/' + $stateParams.id).then(function (data) {
                    $scope.item = data;
                });
            });
        }else{
            toastr.success('回复内容不允许为空！');
        }
    };

    $scope.goParent = function () {
        $state.go('chat.discuss');
    };

    $scope.tolCount = function () {
        $scope.count = 100 - $scope.speak.length;
    };
}])
;