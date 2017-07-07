'use strict';
/**
 * @ngdoc controller
 * @name app.controller.rateCtrl
 * @description 数据指标统计-三率一数统计控制器
 * @author z11595
 * @date 2016-12-18
 */
app.controller('rateCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams', '$http', '$q', function ($scope, $state, commonService, $sce, $stateParams, $http, $q) {
    /**
     * @default {string} 默认不选中不包含新区，也就是area = 1
     */
    $scope.area = '1';
    /**
     *
     * @type {number} 升序|降序标识符
     */
    $scope.desc = 0;

    /**
     *
     * @type {string} 当前排序字段
     */
    $scope.col = '';

    /**
     * @description 判断当前路由中key为type的参数是否存在;判断当前用户的类型
     * @default province 省级部门
     */
    var rolName = commonService.getRolName();
    var types = ['success', 'info', 'warning', 'danger'];
    var names = ['0次跑动', '1次跑动', '2次跑动', '超过2次'];
    $scope.rolId = commonService.getroleid();
    if($stateParams.lasttype == null || $stateParams.lasttype == '' || $stateParams.lasttype == undefined){
        $scope.type = commonService.getUserCityType();
    }else{
        $scope.type = $stateParams.lasttype;
    }

    /**
     * @description 创建当前页面主要对象控制页面数据、筛选和分页
     * @type {{}} vm为空对象
     */
    var vm = $scope.vm = {};
    /**
     * @description 定义当前页面每页显示10条记录，默认显示第1页
     * @define size 5
     * @define index 1
     * @type {{size: number, index: number}}
     */
    vm.page = {
        size: 5,
        index: 1
    };

    /**
     *
     * @type {Date} 获取当前日期、年份、月份、可以选择的最大月份
     * @description 用来判断，如果是当前年份，可选的最大月份只能是当前月份
     */
    var now = new Date();
    $scope.nowmonth = now.getMonth() + 1;
    $scope.maxmonth = now.getMonth() + 1;
    /**
     * @define years 选择年份数组从2016年到当前年份
     */
    $scope.years = _.range(2016, now.getFullYear() + 1);
    /**
     *
     * @default {number} 默认选择年份为当前年份
     */
    $scope.choseyear = now.getFullYear();
    //$scope.lastyear = now.getFullYear();
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
     * @define workflowpercentWay 网上全流程办理率计算方式：网上全流程办理的事项数 / 进驻网上办事大厅事项总数
     * @type {string}
     */
    $scope.workflowpercentWay = '网上全流程办理的事项数 / 进驻网上办事大厅事项总数';
    /**
     * @define onlinepercentWay 上网受理率：上网办理业务量 / 总业务量
     * @type {string}
     */
    $scope.onlinepercentWay = '上网办理业务量 / 总业务量';
    /**
     * @define onlineCompletePercentWay 网上办结率：网上全流程办理并办结业务量 / 总业务量
     * @type {string}
     */
    $scope.onlineCompletePercentWay = '网上全流程办理并办结业务量 / 总业务量';
    /**
     * @description 定义当前跑动数据的模拟数据
     * @type {*[]}
     */
    $scope.bars = [
        {value: "15", type: "success", name: "0次"},
        {value: "45", type: "info", name: "1次"},
        {value: "30", type: "warning", name: "2次"},
        {value: "10", type: "danger", name: "超过2次"}
    ];


    /**
     * @description 切换数据视角
     * @param {string} type（all：全省；province：省级部门；city：地市；county：区县；county)
     * @constructor 清空表格数据和筛选地市|区县选择，清空当前行政区划类别重新查询数据
     */
    $scope.togglePerspective = function (type) {
        $scope.cancel();
        vm.items = null;
        $scope.selectedNum = null;
        $scope.selection = [];
        $scope.category = '';
        $scope.area = '1';
        $scope.type = type;
        $scope.queryData();
    };

    /**
     * @description 全局获取数据
     */
    $scope.queryData = function () {
        /**
         * @define items null 清空当前页面表格数据
         * @type {null}
         */
        vm.items = null;
        /**
         * @define canceller 获取发生的http请求
         * @type {null}
         */
        var canceller = $q.defer();
        /**
         * @description 查询当前三率一数表格明细数据
         *              这里采用angular的$http，而非commonService中的方法，因为需要$q.defer用来终止请求（该列表请求耗时较长，在切换其他type的时候需要终止该请求，否则会覆盖其他type列表页）
         * @param type 类型(province:全省,city:地市,county:区县)
         * @param year 查询年份
         * @param smonth 开始月份
         * @param emonth 结束月份
         * @param cparams 地市|区县选择集合
         * @param category 行政区划类别(0:珠三角，1：粤东西北)
         * @param newArea 是否包含新区(0:不包含，1：包含)
         * @param timeout 用来终止http请求
         * @callback 强制遍历数据转化为number，更新页面数据
         */
        $http({
            method: 'get',
            url: 'rate/slys/' + $scope.type,
            params: {
                year: $scope.choseyear,
                smonth: $scope.smonth,
                emonth: $scope.emonth,
                cparams: $scope.selection,
                category: $scope.category,
                area: $scope.area
            },
            timeout: canceller.promise
        }).success(function (data) {
            //console.log(data);
            //将后台给的字符串形式的数据转换成number
            for (var i = 0; i < data.length; i++) {
                data[i].wsqlcsxs = parseInt(data[i].wsqlcsxs);
                data[i].sxzs = parseInt(data[i].sxzs);
                data[i].wsqlcbll = parseFloat(data[i].wsqlcbll);
                data[i].swblzs = parseInt(data[i].swblzs);
                data[i].bjzs = parseInt(data[i].bjzs);
                data[i].swbll = parseFloat(data[i].swbll);
                data[i].wsqlcbjzs = parseInt(data[i].wsqlcbjzs);
                data[i].wsbjl = parseFloat(data[i].wsbjl);
                data[i].pdcs0 = parseInt(data[i].pdcs0);
                data[i].zb0 = parseFloat(data[i].zb0);
                data[i].pdcs1 = parseInt(data[i].pdcs1);
                data[i].zb1 = parseFloat(data[i].zb1);
                data[i].pdcs2 = parseInt(data[i].pdcs2);
                data[i].zb2 = parseFloat(data[i].zb2);
                data[i].pdcs3 = parseInt(data[i].pdcs3);
                data[i].zb3 = parseFloat(data[i].zb3);
                data[i].shfwswblzs = parseInt(data[i].shfwswblzs);
                data[i].shfwbjzs = parseInt(data[i].shfwbjzs);
                data[i].shfwsxzs = parseInt(data[i].shfwsxzs);
                data[i].index = i + 1; //添加一个序号
            }
            vm.items = data;
            var d_ = filterDate(data);
            getRate(d_);
            getTime(d_);
        });
        /**
         * @description 终止该http请求
         */
        $scope.cancel = function (){
            canceller.resolve("user cancelled");
        };
    };

    /**
     * @description 立即执行当前查询数据
     */
    $scope.queryData();

    /**
     * @description 根据当前获取的数据设置当前三率
     * @param data 总计数据
     */
    var getRate = function (data) {
        if (!_.isEmpty(data)) {
            $scope.wsqlcblv = data.wsqlcbll;
            $scope.wsbjl = data.wsbjl;
            $scope.swblv = data.swbll;
        } else {
            $scope.wsqlcblv = '--';
            $scope.wsbjl = '--';
            $scope.swblv = '--';
        }
    };
    /**
     * @description 根据当前获取的数据设置跑动次数
     * @param data 总计数据
     */
    var getTime = function (data) {
        if (!_.isEmpty(data)) {
            var str = '';
            var d = [];
            for (var i = 0; i < 4; i++) {
                str += '<b>' + i + '次</b>' + ' ' + ' 事项数: ' + data["pdcs" + i] + ' 占比: ' + data["zb" + i].toFixed(2) + ' %<br/>';
                d.push({
                    value: data["zb" + i].toFixed(2),
                    type: types[i],
                    name: names[i]
                });
            }
            $scope.pdcs = $sce.trustAsHtml(str);
            $scope.bars = d;
        } else {
            $scope.pdcs = $sce.trustAsHtml('');
            $scope.bars = null;
        }
    };
    /**
     * @description 筛选总计数据
     * @param data
     * @return {*}
     */
    var filterDate = function (data) {
        if (_.size(data) == 1) {
            return data[0];
        } else if (_.size(data) > 1) {
            return _.findWhere(data, {name: "总计"});
        } else {
            return null;
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
        /*$scope.smonth = 1;
        $scope.emonth = null;*/
        $scope.selectedNum = null;
        $scope.selection = [];
        $scope.category = '';
        $scope.area = '';
    };
    /**
     * @requires commonService eg:openModal('mc','province')
     * @description 模态框选择地市|区县集合
     * @callback 获取当前用户选择的地市|区县列表、数据长度、行政区划类别
     */
    $scope.open = function () {
        var modalInstance = commonService.openModal('mc', $scope.type);
        modalInstance.result.then(function (result) {
            $scope.selectedNum = $scope.type == 'city' ? _.size(result) : _.size(result.select);
            $scope.selection = $scope.type == 'city' ? result : result.select;
            $scope.category = $scope.type == 'city' ? null : result.type;
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

     * @description 判断是否选择包含新区的复选框
     * @return 如果选中$scope.area = 0 ,如果没有选中$scope.area = 1
     */
    $scope.chk = false;
    $scope.check = function (val) {
        !val ? $scope.area = '1' : $scope.area = '0';
    };

    /**
     * @description 拿到所有的方案内容，为页面可以进行方案选择
     */
    $scope.getPlans = function () {
        /**
         * @description 查询所有的方案信息
         * @callback 更新方案选择框
         */
        commonService.queryDataByP('sysMng/planList', {}).then(function (data) {
            $scope.plans = data;
        }, function (data) {
            console.info('error in response');
        });
    };
    $scope.getPlans();

    //当选择的方案改变的时候，更新比率信息
    $scope.changePlan = function (eve) {
        /**
         * @description 查询所有的方案信息
         * @callback 更新方案选择框
         */
        if(eve.selectedPlan != null){
            commonService.queryDataByP('sysMng/getPlanByName', {
                name: eve.selectedPlan.name
            }).then(function (data) {
                 $scope.wsqlcbllDB = data.wsqlcbll;
                 $scope.swbllDB = data.swbll;
                 $scope.wsbjlDB = data.wsbjl;
                 $scope.zb0DB = data.zb0;
                 $scope.zb1DB = data.zb1;
                 $scope.zb2DB = data.zb2;
                 $scope.zb3DB = data.zb3;
            }, function (data) {
                console.info('error in response');
            });
        }else {
            $scope.wsqlcbllDB = null;
            $scope.swbllDB = null;
            $scope.wsbjlDB = null;
            $scope.zb0DB = null;
            $scope.zb1DB = null;
            $scope.zb2DB = null;
            $scope.zb3DB = null;
        }
    };
}]);