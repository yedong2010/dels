/**
 * @ngdoc controller
 * @name app.controller.addAreaCtrl
 * @author l13608
 * @description 增加基本数据控制器
 * @date 2017/3/24 14:47
*/

app.controller('addAreaCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    var vm = $scope.vm = {};
    $scope.areaDivide = ['珠三角','粤东西北'];
    $scope.adDiv = ['行政区划','非行政区划'];
    /**
     * @description 拿到所有的辖区分布的城市以及他们的dssx的值
     */
    $scope.queryData = function () {
        commonService.queryDataByP('sysMng/getDsName', {
        }).then(function (data) {
            vm.items = data;
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };
    $scope.queryData();

    /**
     * @description: 返回区划基础数据列表页面
     * @author: l13595
     * @time: 2017/3/2 11:07
     */
    $scope.addAreaBack=function(){
        $state.go('sysMng.baseDateList');
    }
    /**
     * @description 增加辖区分布代码
     */
    $scope.addArea = function () {
        var XZQHDM = $scope.addXZQHDM;
        var XZQHQC = JSON.parse($scope.addAreaCity).SJMC+$scope.addAreaName;
        var SJDM = parseInt($scope.addXZQHDM.slice(2,4));
        var SJMC = JSON.parse($scope.addAreaCity).SJMC;
        var QYDM = parseInt($scope.addXZQHDM.slice(4,6));
        var QYMC = $scope.addAreaName;
        var QYJBDM = 4;
        var QYJBMC = '辖区分部';
        var qybs;
        var dssx = JSON.parse($scope.addAreaCity).dssx;
        var qxsx;
        if(XZQHDM==null || XZQHDM.length!=6){
            toastr.error('行政区划代码长度必须是6位');
            return;
        }
        if($scope.addAreaDivide == '珠三角'){
            qybs = 0;
        }else if ($scope.addAreaDivide == '粤东西北'){
            qybs = 1;
        }else {
            qybs = null;
        }

        if($scope.addAdDiv == '行政区划'){
            qxsx = -1;
        }else if ($scope.addAdDiv == '非行政区划'){
            qxsx = 0;
        }else {
            qxsx = null;
        }
        commonService.queryDataByP('sysMng/addBaseData', {
            XZQHDM:XZQHDM,
            XZQHQC:XZQHQC,
            SJDM:SJDM,
            SJMC:SJMC,
            QYDM:QYDM,
            QYMC:QYMC,
            QYJBDM:4,
            QYJBMC:'辖区分部',
            qybs:qybs,
            dssx:dssx,
            qxsx:qxsx
        }).then(function (data) {
            $state.go('sysMng.baseDateList');
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    }
}]);
