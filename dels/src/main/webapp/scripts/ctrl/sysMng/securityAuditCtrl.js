/**
 * @ngdoc controller
 * @name app.controller.securityAuditCtrl
 * @author l13608
 * @description 修改安全审计次数控制器
 * @date 2017/3/24 15:23
*/
app.controller('securityAuditCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    /**
     * @ngdoc controller
     * @description 拿到所有角色信息
     * @author l13608
     * @date 2017-2-16
     */
    commonService.queryDataByP('sysMng/roleList', {
    }).then(function (data) {
        $scope.roles=data;
    }, function () {
        toastr.error('返回信息失败，请联系管理员');
    });
    /**
     * @ngdoc controller
     * @description 修改能查看消息的角色和安全登陆次数
     * @author l13608
     * @date 2017-2-16
     */
    $scope.modSecurityTime = function () {
        commonService.queryDataByP('sysMng/updateMsgNum', {
            num:$scope.securityTime,
            role_id:$scope.selectedRole
        }).then(function (data) {
            if(data.resultMsg!=null && data.resultMsg=='ok'){
                toastr.success('更新成功！');
                $scope.queryDate();
            }else{
                toastr.error(data.resultMsg);
            }
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };

    /**
     * @author l13608
     * @description 拿到角色和安全登陆次数
     * @date 2017/3/24 15:24
    */
    $scope.queryDate = function () {
        commonService.queryDataByP('sysMng/securityTime', {
        }).then(function (data) {
            $scope.selectedRole=data.role_id;
            $scope.securityTime=data.num;
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };
    $scope.queryDate();

}]);