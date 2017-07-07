/**
 * @ngdoc controller
 * @name app.controller.baseDataListCtrl
 * @author l13608
 * @description 基础数据展示控制器
 * @date 2017/3/24 14:50
*/

app.controller('baseDataListCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    /**
     * @author l13608
     * @description 排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
     * @date 2017/3/24 14:50
    */

    $scope.desc = 0;
    $scope.col = '';

    var modGRegionModal = $("#modRegionPage");
    var delAreaModal = $("#delAreaModal");
    var originalQYMC;
    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };
    /**
     * @author l13608
     * @description 拿到要修改的区域名称
     * @param areaName:要修改的区域名
     * @date 2017/3/28 15:35
    */
     $scope.getArea = function (areaName) {
         $scope.modAreaNameModel = areaName;
         originalQYMC = areaName;
     };

    /**
     * @author l13608
     * @description 监听模态窗口拿到修改广州区域的XZQHDM
     * @date 2017/3/24 14:51
    */

    modGRegionModal.on("shown.bs.modal", function(e) {
        // 修改拿到基础数据的XZQHDM和XZQHQC，然后把XZQHQC传到模态窗口
        $scope.modGRegionXZQHDM = $(e.relatedTarget).context.attributes.XZQHDM.value;
        $scope.modGRegionXZQHQC = $(e.relatedTarget).context.attributes.XZQHQC.value;
        $scope.qybs = $(e.relatedTarget).context.attributes.qybs.value;
        $scope.qxsx = $(e.relatedTarget).context.attributes.qxsx.value;
        $scope.qymc = $(e.relatedTarget).context.attributes.qymc.value;
        //originalQYMC = $scope.qymc;
        $('#GRegion').html($scope.modGRegionXZQHQC);
        //$scope.modAreaNameModel = $scope.qymc;
        //$('#modAreaName').val($scope.qymc);
        if ($scope.qybs == 0){
            $('#modGRegion').val('珠三角');
        }else if ($scope.qybs == 1){
            $('#modGRegion').val('粤东西北');
        }else {
            toastr.error('地区区域错误');
        }
        if ($scope.qxsx == -1){
            $('#modDeveArea').val('行政区划');
        }else {
            $('#modDeveArea').val('非行政区划');
        }
    });

    /**
     * @author l13608
     * @description 全局获取数据
     * @date 2017/3/24 14:51
    */
    $scope.queryData = function () {
        vm.items = null;
        // 查询当前数据
        commonService.queryDataByP('sysMng/baseDateReginList', {
        }).then(function (data) {
            vm.items = data;
            angular.forEach(vm.items,function (data){
                if(data.qybs == 0){
                    data.qybs = '珠三角';
                }else if(data.qybs == 1) {
                    data.qybs = '粤东西北';
                }else {
                    toastr.error('基础数据出错');
                }
                if(data.qxsx != -1){
                    data.qxsx = '行政区划';
                }else if(data.qxsx == -1){
                    data.qxsx = '非行政区划';
                }else {
                    toastr.error('基础数据出错');
                }
            });
        }, function () {
        });
    };
    $scope.queryData();
    
    /**
    *
    * @description 
    * @author l13608
    * @return true:更新数据库里面的区域信息成功 false:更新数据库里面的区域信息失败
    * @time 2017/1/13 11:49 
    */
    $scope.modGRegionByXZQHDM = function () {
        var qybs;
        var qxsx;
        var QYMC;

        if($scope.modAreaNameModel != undefined){
            QYMC = $scope.modAreaNameModel;
        }else {
            QYMC = originalQYMC;
        }

        if ($('#modGRegion').val() == '珠三角'){
            qybs = 0;
        }else if ($('#modGRegion').val() == '粤东西北'){
            qybs = 1;
        }else {
            toastr.error('返回信息失败，请联系管理员');
        }

        if ($('#modDeveArea').val() == '行政区划'){
            qxsx = -1;
        }else if ($('#modDeveArea').val() == '非行政区划'){
            qxsx = 0;
        }else {
            toastr.error('返回信息失败，请联系管理员');
        }
        console.log('XZQHDM',$scope.modGRegionXZQHDM,'qybs',qybs,'qxsx',qxsx,'QYMC',QYMC,'originalQYMC',originalQYMC);
        commonService.queryDataByP('sysMng/baseDateRegionUpdate', {
            XZQHDM:$scope.modGRegionXZQHDM,
            qybs:qybs,
            qxsx:qxsx,
            QYMC:QYMC,
            originalQYMC:originalQYMC
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            toastr.success('更新成功');
            modGRegionModal.modal('hide');
            $scope.queryData();
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };

    /**
     * @author l13608
     * @description 跳转到增加辖区的页面
     * @date 2017/3/24 14:52
    */

    $scope.addAreaBtn = function (t) {
        $state.go('sysMng.addArea');
    };

    /**
     * @author l13608
     * @description 拿到要删除的区域名XZQHDM和行政区划代码XZQHQC
     * @date 2017/3/24 14:52
    */

    $scope.delAreaName = function (delAreaXZQHQC,delAreaXZQHDM) {
        $scope.delAreaXZQHDM = delAreaXZQHDM;
        $scope.delAreaXZQHQC = delAreaXZQHQC;
    };

    /**
     * @author l13608
     * @description 删除辖区分布区域
     * @date 2017/3/24 14:52
    */

    $scope.delArea = function () {
        commonService.queryDataByP('sysMng/delBaseData', {
            XZQHDM:$scope.delAreaXZQHDM
        }).then(function (data) {
            $scope.queryData();
            toastr.success('删除区域成功');
            delAreaModal.modal('hide');
        }, function () {
        });
    }
}]);