'use strict';
/**
 * @ngdoc controller
 * @name app.controller.busRankCtrl
 * @description 数据指标统计-办结业务量排名
 * @author m13624
 * @date 2017-03-24
 */
app.controller('busRankCtrl', ['$scope', '$state','$stateParams', '$timeout', 'commonService', '$templateCache', function ($scope, $state, $stateParams, $timeout, commonService, $templateCache) {
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
        size: 10,
        index: 1
    };
    /**
     * @description 定义数据类型('all':全省,'province':全省,'city':地市,'county':区县),
     * @default  判断当前用户的type类型和用户id，确定默认加载的数据
     * @type {string}
     */
    $scope.userType = commonService.getUserCityType();
    $scope.rolId = commonService.getroleid();
    if($stateParams.type == null || $stateParams.type == '' || $stateParams.type == undefined){
        if( $scope.rolId == 5 ){
            $scope.type = 'all';
        }else{
            $scope.type = $scope.userType;
        }
    }else{
        $scope.type = $stateParams.type;
    }

    /**
     * @description 默认当前查询的是今天日期——上月1号的数据，格式为'2017-01-01'
     */
    var now = new Date();
    var today = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate();
    if( now.getMonth() == 0 ){
        var MonthNo1 = (now.getFullYear() - 1)+"-12-01";//如果现在是1月份，就取去年12月1日
    }else{
        var MonthNo1 = now.getFullYear()+"-01-01";//如果现在不是1月份，就取今年1月1日
    }

    $scope.startTime = MonthNo1;
    $scope.endTime = today;

    /**
     * @description 查询当前业务量变化趋势和三率 按照当前选择的年份和月份
     */
    $scope.queryByParam = function () {
        /**
         * @description 用来判断表格的列，如果2016年，全省没有事项类型这一列
         */
        $scope.ifyear = $scope.choseyear;
        /**
         * @description 清空当前表格数据
         */
        vm.items = null;
        /**
         * @description 查询当前类型下的总业务量排名（全省显示事项排名）
         * @param {string} type 类型('all':全省,'province':全省,'city':地市,'county':区县)
         * @param {number} startTime 开始日期
         * @param {number} endTime 结束日期
         * @param {array} cparams 地市名称集合
         * @param {string} category 行政区划类别(0:珠三角,1:粤东西北)
         * @callback 获取数组，显示与table中
         */
        commonService.queryDataByP('totality/bjywlph/' + $scope.type,  {
            startTime: $scope.startTime,
            endTime: $scope.endTime,
            cparams: $scope.selection,
            category: $scope.category,
            area: $scope.area
        }).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function (data) {
            console.info('error in response');
        });
    };
    $scope.queryByParam();

    /**
     * @description 切换数据视角
     * @param {string} type（all：全省；province：省级部门；city：地市；county：区县；）
     */
    $scope.togglePerspective = function (type) {
        /**
         * @description 清空当前表格数据
         * @type {null}
         */
        vm.items = null;
        $scope.type = type;
        $scope.selectedNum = null;
        $scope.selection = [];
        $scope.category = '';
        $scope.area = '1';
        $scope.queryByParam();
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
     * @description 判断是否选择包含新区的复选框
     * @return 如果选中$scope.area = 0 ,如果没有选中$scope.area = 1
     * @default {number} 默认不选中不包含新区，也就是area = 1
     */
    $scope.area = '1';
    $scope.chk = false;
    $scope.check = function (val) {
        !val ? $scope.area = '1' : $scope.area = '0';
    };

    /**
     * @description 进去业务量排名详情页
     * @param type（province：省级；city：地市）
     * @param item
     */
    $scope.goDetail = function (obj) {
        if($scope.type == 'province'){
            $scope.keyId = obj.id;
        }else{
            $scope.keyId = obj.name;
        }
        $state.go('indicators.busRankDetail', {
            type: $scope.type,
            keyId: $scope.keyId,
            startTime: $scope.startTime,
            endTime: $scope.endTime,
            name: obj.name
        });
    };

    /**
     * @description 日期选择器配置参数
     */
    $.fn.datetimepicker.dates['zh-CN'] = {
        days:       ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六","星期日"],
        daysShort:  ["日", "一", "二", "三", "四", "五", "六","日"],
        daysMin:    ["日", "一", "二", "三", "四", "五", "六","日"],
        months:     ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月","十二月"],
        monthsShort:  ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"],
        meridiem:    ["上午", "下午"],
        today:       "今天"
    };

    $("#startTimeCtrl").datetimepicker({
        minView: "month",
        format: "yyyy-mm-dd",
        language:  'zh-CN',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left",
        endDate: new Date(),
        startDate: '2016-01-01'   //不允许选择2016年的
    }).on('changeDate',function(){
        var startTime = $("#startTime").val();
        $("#endTimeCtrl").datetimepicker('setStartDate',startTime);
    });

    $("#endTimeCtrl").datetimepicker({
        minView: "month",
        format: "yyyy-mm-dd",
        language:  'zh-CN',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left",
        endDate: new Date(),
        startDate: MonthNo1  //默认截止日期的可选项需要小于起始日期，即上个月1号
    }).on('changeDate',function(){
        var endTime = $("#endTime").val();
        $("#startTimeCtrl").datetimepicker('setEndDate',endTime);
    });

}]);