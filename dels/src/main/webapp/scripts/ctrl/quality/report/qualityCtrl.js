'use strict';
/**
 * @ngdoc controller
 * @name app.controller.qualityCtrl
 * @description 数据质量检测-数据质量统计控制器
 * @author z11595
 * @date 2016-12-13
 */
app.controller('qualityCtrl', ['$scope', 'commonService', '$state', '$stateParams', function ($scope, commonService, $state, $stateParams) {
    /**
     * @type {number} 升序|降序标识符
     */
    $scope.desc = 0;
    /**
     * @type {string} 当前排序字段
     */
    $scope.col = '';
    /**
     * @description 判断当前路由中key为type的参数是否存在;判断用户类型
     * @default province 省级部门
     */
    $scope.rolId = commonService.getroleid();
    if($stateParams.lasttype == null || $stateParams.lasttype == '' || $stateParams.lasttype == undefined){
        $scope.type = commonService.getUserCityType();
    }else{
        $scope.type = $stateParams.lasttype;
    }
    /**
     *
     * @type {Date} 获取当前日期、年份、月份
     * @description 用来判断，如果是当前年份，月份只能到当前月份
     */
    var now = new Date();
    $scope.nowmonth = now.getMonth() + 1;
    $scope.maxmonth = now.getMonth() + 1;
    /**
     * @define years 选择年份数组从2014年到当前年份
     */
    $scope.years = _.range(2016, now.getFullYear() + 1);
    /**
     *
     * @type {number} 默认选择年份为当前年份
     */
    $scope.choseyear = now.getFullYear();
    $scope.lastyear = now.getFullYear() - 1 ;
    /**
     *
     * @default {number} 默认选择月份为1月
     */
    $scope.smonth = 1;
    /**
     *
     * @default {number} 默认选择月份为当前月
     */
    $scope.emonth = now.getMonth() + 1;
    /**
     *
     * @type {{}} 定义当前vm 用于控制数据、分页
     */
    var vm = $scope.vm = {};
    /**
     * @description 每页显示10条数据，默认显示第1页(首页)
     * @default size 10
     * @default index 1
     * @type {{size: number, index: number}}
     */
    vm.page = {
        size: 10,
        index: 1
    };


    /**
     * @description 切换数据视角
     * @param {string} type（all：全省；province：省级部门；city：地市）
     */
    $scope.togglePerspective = function (type) {
        vm.items = null;
        $scope.type = type;
        $scope.queryQuality();
    };
    /**
     * @description 查询当前上传记录数的质量报告信息
     */
    $scope.queryQuality = function () {
        /**
         * @description 清空表格数据
         * @type {null}
         */
        vm.items = null;
        /**
         * @description 设置当前数据质量信息
         * @param {string} type 类型（province：省级部门；city：地市）
         * @param {{year: number, smonth: number, emonth: number, cparams: array}} 查询年份、开始月份、结束月份、地市名称数组
         * @callback 遍历返回集合强制转化为number，更新表格数据
         */
        commonService.queryDataByP('quality/sjzl/' + $scope.type, {
            year: $scope.choseyear,
            smonth: $scope.smonth,
            emonth: $scope.emonth,
            cparams: $scope.selection
        }).then(function (data) {
            //将后台给的字符串形式的数据转换成number
            for (var i = 0; i < data.length; i++) {
                data[i].zl = parseInt(data[i].zl);
                data[i].zq = parseInt(data[i].zq);
                data[i].cw = parseInt(data[i].cw);
                data[i].zql = parseFloat(data[i].zql);
                data[i].index = i + 1; //每条数据添加序号
            }
            vm.items = data;
        }, function () {
            console.info('error in responce!');
        });

    };
    /**
     * @description 点击进入错误记录数详情（错误类别）
     * @param {object} item 传入质量数据模型，路由传参：省级|地市名称，当前类型(province|city),查询年份，开始月份，结束月份
     */
    $scope.goDetail = function (item) {
        $state.go('quality.report.detail', {
            name: item.name,
            type: $scope.type,
            year: $scope.choseyear,
            smonth: $scope.smonth,
            emonth: $scope.emonth,
            cw: item.cw
        });
    };
    $scope.queryQuality();
    /**
     * @requires commonService eg:openModal('mc','province')
     * @description 模态框选择地市
     * @callback 获取当前用户选择的地市列表
     */
    $scope.open = function () {
        var modalInstance = commonService.openModal('jc', $scope.type);
        modalInstance.result.then(function (result) {
            $scope.selectedNum = _.size(result);
            $scope.selection = result;
        }, function (reason) {
            console.log(reason);//点击空白区域，总会输出backdrop click，点击取消，则会暑促cancel
        });
    };
    /**
     * @description 设置变更年份是当前默认起止月份
     */
    $scope.defaultMonth = function () {
        if ($scope.choseyear == now.getFullYear()) {
            $scope.smonth = 1;
            $scope.emonth = now.getMonth() + 1;
            $scope.maxmonth = $scope.nowmonth;
        } else {
            $scope.smonth = 1;
            $scope.emonth = 12;
            $scope.maxmonth = 12;
        }
    };
    /**
     * @description 清空当前选择的起止月份和筛选地市|区县并重新查询业务办理页面的数据
     * @define smonth 开始查询时间为空
     * @define emonth 结束查询时间为空
     * @define selectedNum 设置选择地市数量为空
     * @define selection 清空选择的地市
     */
    $scope.clearParam = function () {
        /*$scope.smonth = null;
        $scope.emonth = null;*/
        $scope.selectedNum = null;
        $scope.selection = [];
        //$scope.queryQuality();
    };
}]);