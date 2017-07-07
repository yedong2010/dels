'use strict';
/**
 * @ngdoc controller
 * @name app.controller.MessMonitorCtrl
 * @author l13608
 * @description 安全审计控制器
 * @date 2017/3/24 15:01
*/

app.controller('MessMonitorCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    /**
     * @author l13608
     * @description 排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
     * @date 2017/3/24 15:02
    */
    $scope.desc = 0;
    $scope.col = '';

    var modal = $("#editLoginTimeModal");

    if ($stateParams.type) {
        $scope.type = $stateParams.type;
    } else {
        $scope.type = 'province';
    }

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    /**
     * @author l13608
     * @description 根据输入的错误次数查询错误登录
     * @date 2017/3/24 15:05
    */
    $scope.queryData = function () {
        if($scope.startNum!=null&&$scope.startNum!=''&&isNaN($scope.startNum)){
            toastr.error("请输入数字！");
            return ;
        }
        if($scope.endNum!=null&&$scope.endNum!=''&&isNaN($scope.endNum)){
            toastr.error("请输入数字！");
            return ;
        }
        vm.items = null;
        commonService.queryDataByP('sysMng/msgList', {
            ip:$scope.ip,
            startNum:$scope.startNum,
            endNum:$scope.endNum,
            startTime:$scope.startTime,
            endTime:$scope.endTime
        }).then(function (data) {
            for(var i=0; i<data.list.length; i++){
                data.list[i].index = i + 1;
            }
            vm.items = data.list;
            $scope.loginFreq=data.msgNum;
        }, function () {
            toastr.error("返回信息失败，请联系管理员");
        });
    };
    $scope.queryData();

    /**
     * @author l13608
     * @description 根据id更新安全登陆次数
     * @date 2017/3/24 15:07
    */
    $scope.editLoginTime = function () {
        commonService.queryDataByP('sysMng/updateMsgNum', {
            num:$scope.loginFreq
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            toastr.success('更新安全登陆次数成功');
            modal.modal('hide');
            $scope.queryData();
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };

    $scope.ifNum=false;
    $scope.checkNum=function(){
        if($scope.startNum!=null&&$scope.startNum!=''&&isNaN($scope.startNum)){
            $scope.ifNum=true;
            return;
        }else{
            $scope.ifNum=false;
        }
        if($scope.endNum!=null&&$scope.endNum!=''&&isNaN($scope.endNum)){
            $scope.ifNum=true;
            return;
        }else{
            $scope.ifNum=false;
        }
    }
}]);