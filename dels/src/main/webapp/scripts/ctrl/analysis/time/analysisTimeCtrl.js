'use strict';
/**
 * @description 数据分析-业务办理时长分析
 * @author m13624
 * @date 2017-03-03
 */
app.controller('analysisTimeCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams', function ($scope, $state, commonService, $sce, $stateParams) {
    /**
     * @description 列表数据放在vm中，默认分页是10条一页
     */
    var vm = $scope.vm = {};
    vm.page = {
        size: 5,
        index: 1
    };

    /**
     * @define 定义当前事项类型(全部、行政审批、公共服务)；默认为全部
     * @type {string[]}
     */
    $scope.sxtypes = ['全部', '行政审批', '公共服务'];
    $scope.sxlxmcvalue = $scope.sxtypes[0];  //下拉选项开始默认为全部

    /**
     * @description 年份选择只能从2015年到现在，默认为当前年份
     */
    var now = new Date();
    $scope.years = _.range(2015, now.getFullYear() + 1);
    $scope.choseYear = now.getFullYear() - 1;

    /**
     * @description 切换数据视角
     * @param type（city：地市；county：区县；）
     */
    $scope.togglePerspective = function(){
        vm.items = null;
        $scope.queryData();
    };

    /**
     * @description 全局获取数据,点击查询之后要
     * @param type（city：地市；county：区县；）
     */
    $scope.queryData = function () {
        commonService.queryDataByP('analysis/analysistime/' + $scope.choseYear,{

        }).then(function (data) {
            var saveSum = 0;
            var cSum = 0;
            var avsaveSum = 0;
            var onTimeNum = 0; //记录准时的事项数
            for(var i=0; i<100; i++){
                data[i].promised_period = parseInt(data[i].promised_period) //承诺办理天数转换成整数类型
                data[i].av = Math.round(data[i].av*100)/100; //平均办理时间转换为2位小数的number型
                data[i].save = Math.round( (parseInt(data[i].promised_period) - data[i].av)*100)/100; //平均节约的时间
                saveSum = saveSum + data[i].save * parseInt(data[i].c); // 总共节约时间：平均节约时间*业务量
                cSum = cSum + parseInt(data[i].c); //总共业务量
                avsaveSum = avsaveSum + data[i].save; //总共节约天数 ： 节约天数相加
                data[i].index = i + 1; //每条数据添加序号
                if(data[i].save >= 0){
                    onTimeNum++;
                }
            }
            $scope.cSum = cSum; //总业务量
            $scope.saveSum = Math.round(saveSum); //总共节约天数
            $scope.avSave = Math.round(saveSum/cSum*100)/100; //平均每笔业务节约天数
            $scope.onTimeNum = onTimeNum;
            vm.items = data;

        }, function () {
            console.info('responce error,please call the administrator');
        });
    };

    $scope.queryData();
}]);