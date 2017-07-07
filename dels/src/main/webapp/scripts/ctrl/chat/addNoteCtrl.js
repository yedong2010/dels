'use strict';
/**
 * @ngdoc controller
 * @name app.controller.addNoteCtrl
 * @requires $scope作用域,$state路由,$timeout延时执行,$stateParams路由参数控制,commonService
 * @description 楼主发布帖--主题帖发布和编辑
 * @author z11595
 * @date 2017-1-12
 */
app.controller('addNoteCtrl', ['$scope', 'textAngularManager', 'commonService', '$rootScope', 'toastr', '$state', function ($scope, textAngularManager, commonService, $rootScope, toastr, $state) {
    $scope.user = $rootScope.uname;
    $scope.saveNote = function (status) {
        if( ($scope.theme != null && $scope.theme != '' && $scope.theme != undefined) && ($scope.content != null && $scope.content != '')){
            var t = {
                theme: $scope.theme,
                content: $scope.content,
                ctime: new Date(),
                author: $scope.user,
                status: status
            };
            commonService.addNote(t).then(function () {
                if(status == '0'){
                    toastr.success('当前内容保存成功，请在主题管理中进行查看。');
                }else if(status == '1'){
                    toastr.success('当前内容发布成功，请在主题管理或主题列表中进行查看。');
                }
                $state.go('chat.list');
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