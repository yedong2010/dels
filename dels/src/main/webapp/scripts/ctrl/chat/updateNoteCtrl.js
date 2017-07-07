'use strict';
/**
 * @ngdoc controller
 * @name app.controller.updateNoteCtrl
 * @requires $scope作用域,$state路由,$timeout延时执行,$stateParams路由参数控制,commonService
 * @description 楼主发布帖--主题帖发布和编辑
 * @author z11595
 * @date 2017-1-12
 */
app.controller('updateNoteCtrl', ['$scope', 'textAngularManager', '$http', 'toastr', '$state', '$stateParams', function ($scope, textAngularManager, $http, toastr, $state, $stateParams) {
    var o = $stateParams.item;
    $scope.theme = o.theme;
    $scope.content = o.content;
    $scope.author = o.author;
    $scope.user = o.author;
    /**
     * @description 设置当前内容编辑修改
     */
    $scope.saveNote = function (status) {
        if( ($scope.theme != null && $scope.theme != '' && $scope.theme != undefined) && ($scope.content != null && $scope.content != '') ) {
            $http.post('chat/upt', {
                id: o.id,
                theme: $scope.theme,
                content: $scope.content,
                utime: new Date(),
                status: status
            }).success(function () {
                if (status == '0') {
                    toastr.success('当前内容保存成功，请在主题管理中进行查看。');
                } else if (status == '1') {
                    toastr.success('当前内容发布成功，请在主题管理或主题列表中进行查看。');
                }
                $state.go('chat.list');
            }).error(function () {
                toastr.error('当前内容发布失败，请及时联系管理员!');
            });
        }else{
            toastr.warning('主题和内容不允许为空!')
        }
    };

    /**
     * @description 返回上级内容列表
     * @author z11595
     */
    $scope.goParent = function () {
        $state.go('chat.list');
    }

}])
;