/**
 * @ngdoc controller
 * @name app.controller.planListCtrl
 * @author l13608
 * @description 展示方案内容列表
 * @date 2017/3/24 15:11
*/
app.controller('planListCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    /**
     * @author l13608
     * @description 排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
     * @date 2017/3/24 15:11
    */
    $scope.desc = 0;
    $scope.col = '';

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };
    var delPlanModal = $("#delPlanModal");

    /**
     * @author l13608
     * @description 监听模态窗口拿到要删除的方案的id和名称
     * @date 2017/3/24 15:12
    */
    delPlanModal.on("show.bs.modal", function(e) {
        /**
         * @author l13608
         * @description 修改拿到基础数据的XZQHDM和XZQHQC，然后把XZQHQC传到模态窗口
         * @date 2017/3/24 15:12
        */
        $scope.planId = $(e.relatedTarget).context.attributes.planId.value;
        $scope.planName = $(e.relatedTarget).context.attributes.planName.value;
        $('#delPlan').html($scope.planName);
    });

    /**
     * @author l13608
     * @description 获取方案列表
     * @date 2017/3/24 15:13
    */
    $scope.queryData = function () {
        vm.items = null;
        commonService.queryDataByP('sysMng/planList', {
        }).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function () {
            toastr.error("返回信息失败，请联系管理员");
        });
    };
    $scope.queryData();

    /**
     * @author l13608
     * @description 跳转增加方案列表
     * @date 2017/3/24 15:13
    */
    $scope.addPlanBtn = function () {
        $state.go("sysMng.addPlan");
    };

    /**
     * @author l13608
     * @description 跳转展示方案具体内容列表
     * @date 2017/3/24 15:14
    */
    $scope.showPlanBtn = function (item){
        $state.go("sysMng.showPlan",{
            planId:item.id
        });
    };

    /**
     * @author l13608
     * @description 根据id删除方案
     * @date 2017/3/24 15:14
    */
    $scope.delPlan = function () {
        commonService.queryDataByP('sysMng/deletePlan', {
            id:$scope.planId,
            name:$scope.planName
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            toastr.success('删除方案成功');
            delPlanModal.modal('hide');
            $scope.queryData();
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    }
}]);

/**
 * @ngdoc controller
 * @name app.controller.addPlanCtrl
 * @author l13608
 * @description 增加方案控制器
 * @date 2017/3/24 15:15
*/
app.controller('addPlanCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    $scope.addPlanSub = function () {
        var planDescText = $('#planDesc')[0].value;//方案描述信息
        var rates = $('.rates');//所有的利率信息
        var ratesLength = rates.length;

        /**
         * @author l13608
         * @description 增加方案
         * @date 2017/3/24 15:16
        */
        commonService.queryDataByP('sysMng/addPlan', {
            name:$scope.planName,
            descr:planDescText
        }).then(function (data) {
            var planId=data.planId;
            for (var i=0;i<ratesLength;i++){
                commonService.queryDataByP('sysMng/addPlanInfo', {
                    colName:rates[i].getAttribute('datatype'),
                    commentName:rates[i].getAttribute('CName'),
                    passLevel:rates[i].value,
                    planId:planId
                }).then(function(data){
                });
            }
            $state.go("sysMng.planList");
        });
    };

    /**
     * @author l13608
     * @description 跳转方案展示界面
     * @date 2017/3/24 15:16
    */
    $scope.addPlanBack = function () {
        $state.go("sysMng.planList");
    };
}]);

/**
 * @ngdoc controller
 * @name app.controller.showPlanCtrl
 * @author l13608
 * @description 展示方案控制器
 * @date 2017/3/24 15:17
*/
app.controller('showPlanCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    //  排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
    $scope.desc = 0;
    $scope.col = '';

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };
    //修改方案的模态窗口
    var modPlanModal = $("#modRateValPage");
    //删除比率的模态窗口
    var delRateModal = $('#delRatePage');

    /**
     * @author l13608
     * @description 拿到要修改的利率值
     * @param
     * @date 2017/3/28 17:32
    */
     $scope.getRateValue = function (value) {
         $scope.modRateVal = value;
     };
    /**
     * @author l13608
     * @description 监听模态窗口拿到要编辑的比率的id和名称
     * @date 2017/3/24 15:17
    */
    modPlanModal.on("show.bs.modal", function(e) {
        $scope.rateId = $(e.relatedTarget).context.attributes.rateId.value;
        $scope.rateName = $(e.relatedTarget).context.attributes.rateName.value;
        $scope.rateValue = $(e.relatedTarget).context.attributes.rateValue.value;
        $('#ARate').html($scope.rateName);
        //$('#modRateVal').val($scope.rateValue);
    });

    /**
     * @author l13608
     * @description 监听模态窗口拿到要删除的比率的id和名称
     * @date 2017/3/24 15:19
    */
    delRateModal.on("show.bs.modal", function(e) {
        $scope.delRateId = $(e.relatedTarget).context.attributes.rateId.value;
        $scope.delRateName = $(e.relatedTarget).context.attributes.rateName.value;
        $('#delRateName').html($scope.delRateName);
    });

    /**
     * @author l13608
     * @description 查看方案的内容
     * @date 2017/3/24 15:19
    */
    $scope.queryData = function () {
        vm.items = null;
        /**
         * @author l13608
         * @description 通过id拿到所要查看方案的内容
         * @date 2017/3/24 15:19
        */
        commonService.queryDataByP('sysMng/planInfoList', {
            planId: $stateParams.planId
        }).then(function (data) {
            vm.items = data;
        }, function () {

        });
    };
    $scope.queryData();

    /**
     * @author l13608
     * @description 修改比率的值
     * @date 2017/3/24 15:20
    */
    $scope.modRateSub = function () {
        /**
         * @author l13608
         * @description 通过id去修改比率的值
         * @date 2017/3/24 15:20
        */
        if ($scope.modRateVal != undefined){
            commonService.queryDataByP('sysMng/editPlanInfo', {
                id:$scope.rateId,
                passLevel:$scope.modRateVal
            }).then(function (data) {
                if(null!=data && data.resultMsg!='ok'){
                    toastr.error(data.resultMsg);
                    return;
                }
                toastr.success('更新成功');
                modPlanModal.modal('hide');
                $scope.queryData();
            });
        }else {
            toastr.error('请输入一个比率值');
        }
    };

    /**
     * @author l13608
     * @description 通过id删除方案比率
     * @date 2017/3/24 15:20
    */
    $scope.delRateById = function () {
        commonService.queryDataByP('sysMng/editPlanInfo', {
            id:$scope.delRateId,
            passLevel:null
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            toastr.success('删除成功');
            delRateModal.modal('hide');
            $scope.queryData();
        });
    };

    /**
     * @author l13608
     * @description 返回方案展示列表
     * @date 2017/3/24 15:21
    */
    $scope.backPlanList = function () {
        $state.go('sysMng.planList');
    };
}]);