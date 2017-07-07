/**
 * @ngdoc controller
 * @name app.controller.roleAddCtrl
 * @author l13608
 * @description 增加角色控制器
 * @date 2017/3/24 14:48
*/

app.controller('roleAddCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    var zTreeObj;
    /**
     * @author l13608
     * @description zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
     * @date 2017/3/24 14:48
    */
    var setting = {
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: {"Y": "ps", "N": "ps"},
            autoCheckTrigger: false
        },
        view: {
            showIcon: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parent_id",
                rootPId: 0
            }
        }
    };

    /**
     * @author l13608
     * @description
     * @param 获取菜单数据
     * @date 2017/3/24 14:48
    */

    $scope.queryData = function () {
        commonService.queryDataByP('sysMng/roleAddForm', {}).then(function (data) {
            $(document).ready(function () {
                zTreeObj = $.fn.zTree.init($("#jurisdictionTree"), setting, data);
                zTreeObj.expandAll(true);
                var roleId = commonService.getroleid();
                // var nodeUser = zTreeObj.getNodeByParam('id','4');
                // var nodeRole = zTreeObj.getNodeByParam('id','5');
                // zTreeObj.setChkDisabled(nodeUser, true);
                // zTreeObj.setChkDisabled(nodeRole, true);

                zTreeObj.setChkDisabled(zTreeObj.getNodeByParam('id','4'), true);  //角色管理
                zTreeObj.setChkDisabled(zTreeObj.getNodeByParam('id','5'), true);  //用户管理
                zTreeObj.setChkDisabled(zTreeObj.getNodeByParam('id','55'), true);  //角色分配
                zTreeObj.setChkDisabled(zTreeObj.getNodeByParam('id','15'), true);  //日志

            });
            $scope.addRole = function () {
                $state.go('sysMng.addRole');
            };
        }, function () {
            console.info('responce error,please call the administrator');
        });
    };
    $scope.queryData();

    /**
     * @author l13608
     * @description 增加角色：提交角色信息到后台，保存到数据库
     * @date 2017/3/24 14:49
    */

    $scope.submitData = function () {
        var selectedObjId = [];
        _.each(zTreeObj.getCheckedNodes(true), function (obj, index) {
            selectedObjId.push(obj.id);
        });
        if (selectedObjId.length == 0 || $scope.role_name == undefined || $scope.role_name == ''){
            toastr.error('没有书写用户名或没有选择权限内容');
        }else {
            commonService.queryDataByP('sysMng/saveRoleInfo', {
                role_name: $scope.role_name,
                role_Jurisdiction: selectedObjId
            }).then(function (data) {
                if (null != data && data.resultMsg == "ok") {
                    toastr.success('增加角色成功');
                    $state.go('sysMng.roleList');
                } else {
                    toastr.error(data.resultMsg);
                }
            });
        }
    };
    //$scope.queryData();
    /**
     * @author l13608
     * @description 返回角色展示界面
     * @date 2017/3/24 14:49
    */
    $scope.goBack = function () {
        $state.go('sysMng.roleList');
    }
}]);
