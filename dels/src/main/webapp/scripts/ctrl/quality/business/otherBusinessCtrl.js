'use strict';
/**
 * @ngdoc controller
 * @name app.controller.otherBusinessCtrl
 * @description 数据质量监测-进驻事项数据版本-省级、地市、区县
 * @author m13624
 * @date 2017-01-05
 */
app.controller('otherBusinessCtrl', ['$scope', '$state', '$stateParams', 'commonService', function ($scope, $state, $stateParams, commonService) {
    /**
     * @description $stateParam 路由参数控制
     * @description 通过路由传参获取类型('province':省级,'city':地市,'county':区县)
     */
    $scope.place = $stateParams.place;

    $scope.type = commonService.getUserCityType();

    var vm = $scope.vm = {};
    vm.page = {
        size: 5,
        index: 1
    };

    /**
     * @define 定义当前事项类型(全部、行政审批、公共服务)
     * @type {string[]}
     */
    $scope.sxtypes = ['全部', '行政审批', '公共服务'];
    $scope.sxlxmcvalue = $scope.sxtypes[0];  //下拉选项开始默认为全部

    /**
     * @define 定义当前差异类型(全部、新增、删除)
     * @type {string[]}
     */
    $scope.cytypes = ['全部', '新增', '删除'];
    $scope.diffTypeValue = $scope.cytypes[0];  //下拉选项开始默认为全部

    /**
     * @description 默认版本一是昨日事项数，版本二是今日事项数
     */
    $scope.startVersion = '昨日进驻';
    $scope.endVersion = '今日进驻';

    if($stateParams.place == 'province'){
        $scope.typeName = '省级';
    }else if($stateParams.place == 'city'){
        $scope.typeName = '地市';
    }else if($stateParams.place == 'county'){
        $scope.typeName = '区县';
    }

    /**
     * @description 查询获取今天日期和昨天日期，格式为'2016-01-01'
     */
    var now = new Date();
    var today = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate();

    var yes = new Date(now.getTime()-60*60*24*1000);
    var yesterday = yes.getFullYear()+"-"+((yes.getMonth()+1)<10?"0":"")+(yes.getMonth()+1)+"-"+(yes.getDate()<10?"0":"")+yes.getDate();

    /**
     * @description 默认自定义的不同日期版本查询是从昨天到今天
     */
    $scope.startTime = yesterday;
    $scope.endTime = today;

    /**
     * @requires commonService
     * @description 查询版本一的全省进驻事项数
     */
    $scope.queryAllBusStartTime = function (choseDate) {
        /**
         * @description 查询指定日期的全省进驻事项数
         * @param {string} choseDate 指定日期如'2016-01-01'
         * @callback 将昨天的数据与$scope.startTimejzsxs绑定
         */
        if(choseDate == yesterday){
            $scope.startVersion = '昨日进驻';
        }else{
            $scope.startVersion = '版本一';
        }
        commonService.queryDataByP('quality/qtjzsxs/'+ $scope.place + '/' + choseDate).then(function (data) {
            $scope.startTimejzsxs = data[0];
        }, function () {
            console.info('error in responce!');
        });
    };
    $scope.queryAllBusStartTime(yesterday); //默认版本一是昨日事项数

    /**
     * @requires commonService
     * @description 查询版本二的全省进驻事项数
     */
    $scope.queryAllBusEndTime = function (choseDate) {
        /**
         * @description 查询指定日期的全省进驻事项数
         * @param {string} choseDate 指定日期如'2016-01-01'
         * @callback  将今天的数据与$scope.endTimejzsxs绑定
         */
        if(choseDate == today){
            $scope.endVersion = '今日进驻';
        }else{
            $scope.endVersion = '版本二';
        }
        commonService.queryDataByP('quality/qtjzsxs/'+ $scope.place + '/' + choseDate).then(function (data) {
            $scope.endTimejzsxs = data[0];
        }, function () {
            console.info('error in responce!');
        });
    };
    $scope.queryAllBusEndTime(today); //默认版本二是今日事项数

    /**
     * @description 查询日期1版本和日期2版本的全省进驻事项数差异列表
     * @callback
     */
    $scope.queryBusCompare = function(){
        /**
         * @define items null 清空当前页面表格数据
         */
        vm.items = null;

        $scope.comStartTime = $scope.startTime;
        $scope.comEndTime = $scope.endTime;

        commonService.queryDataByP('quality/qtjzsxsdb/'+ $scope.place + '/' + $scope.startTime + '/' + $scope.endTime).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            $scope.vm.items = data;
        }, function () {
            console.info('error in responce!');
        });
    };
    $scope.queryBusCompare();

    /**
     * @requires $state
     * @description 全省、省级、地市、区县的页面跳转（全省的是单独页面，省级、地市、区县共用同一页面）
     * @param {string} place 类型('all':全省,'province':省级,'city':地市',county:'区县')
     */
    $scope.goWhere = function(place){
        if(place == 'all'){
            $state.go('quality.business.index');
        }else{
            $state.go('quality.business.other', {place: place});
        }
    };

    /**
     * @description 点击查询，刷新页面数据
     */
    $scope.queryRefresh = function(){
        $scope.queryAllBusStartTime($scope.startTime);
        $scope.queryAllBusEndTime($scope.endTime);
        $scope.queryBusCompare();
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
    $(".form_datetime").datetimepicker({
        minView: "month",
        format: "yyyy-mm-dd",
        language:  'zh-CN',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left",
        endDate: new Date(),
        startDate:'2017-01-01'
    });
}]);