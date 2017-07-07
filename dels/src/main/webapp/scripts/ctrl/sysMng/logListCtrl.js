'use strict';
/**
 * @ngdoc controller
 * @name app.controller.logListCtrl
 * @author l13608
 * @description 日志列表控制器
 * @date 2017/3/24 14:59
*/

app.controller('logListCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams', function ($scope, $state, commonService, $sce, $stateParams) {
    //  排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
    $scope.desc = 0;
    $scope.col = '';

    var vm = $scope.vm = {};

    /**
     * @description 默认当前查询的是今天日期——上月1号的数据，格式为'2017-01-01'
     */
    var now = new Date();
    var today = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate();
    if( now.getMonth() == 0 ){
        var lastMonthNo1 = (now.getFullYear() - 1)+"-12-01";//上月1号
    }else{
        var lastMonthNo1 = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-01";//上月1号
    }

    $scope.startTime = today;
    $scope.endTime = today;

    /**
     * @author l13608
     * @description 配置分页基本参数
     * @date 2017/3/24 14:59
    */
    vm.page = {
        size: 10,
        index: 1
    };

    /**
     * @author l13608
     * @description 获取日志内容
     * @date 2017/3/24 15:00
    */

    $scope.queryData = function () {
        vm.items = null;
        // 查询当前数据
        commonService.queryDataByP('sysMng/logList', {
            size:vm.page.size,
            index:vm.page.index,
            startTime:$scope.startTime,
            endTime:$scope.endTime,
            param:$scope.selectContent
        }).then(function (data) {
            for(var i=0; i<data.list.length; i++){
                data.list[i].index = i + 1;
            }
            vm.items = data.list;
            vm.num=data.size;
            $scope.selectContent = '';
        }, function () {
        });
    };
    $scope.queryData();
    
    /*$scope.queryDataByDate = function () {
        console.log("===================",$("#dtp_input1").val(),"===================",$("#dtp_input2").val(),"===================",$scope.selectContent);
    }*/

    /**
     * @author l13608
     * @description 配置日期插件参数
     * @date 2017/3/24 15:00
    */
    $.fn.datetimepicker.dates['zh'] = {
        days:       ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六","星期日"],
        daysShort:  ["日", "一", "二", "三", "四", "五", "六","日"],
        daysMin:    ["日", "一", "二", "三", "四", "五", "六","日"],
        months:     ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月","十二月"],
        monthsShort:  ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"],
        meridiem:    ["上午", "下午"],
        today:       "今天"
    };
    $("#endTimeCtrl").datetimepicker({
        minView: "month",
        format: "yyyy-mm-dd",
        language:  'zh-CN',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left",
        endDate: new Date(),
        startDate: lastMonthNo1  //默认截止日期的可选项需要小于起始日期，即上个月1号
    }).on('changeDate',function(){
        var endTime = $("#endTime").val();
        $("#startTimeCtrl").datetimepicker('setEndDate',endTime);
    });

    $("#startTimeCtrl").datetimepicker({
        minView: "month",
        format: "yyyy-mm-dd",
        language:  'zh-CN',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left",
        endDate: new Date(),
        startDate: '2017-01-01'   //不允许选择2016年的
    }).on('changeDate',function(){
        var startTime = $("#startTime").val();
        $("#endTimeCtrl").datetimepicker('setStartDate',startTime);
    });

    /**
     * @author l13608
     * @description 跳转分析消息界面
     * @date 2017/5/23 15:38
    */
    $scope.analysisMess = function (){
        $state.go('sysMng.logAnalysis');
    };
}]);

