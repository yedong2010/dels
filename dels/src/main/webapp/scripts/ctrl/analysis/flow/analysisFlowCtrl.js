'use strict';
/**
 * @description 数据分析-业务办理时长分析
 * @author m13624
 * @date 2017-03-03
 */
app.controller('analysisFlowCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams', function ($scope, $state, commonService, $sce, $stateParams) {
    /**
     * @description 列表数据放在vm中，默认分页是10条一页
     */
    var vm = $scope.vm = {};
    vm.page = {
        size: 5,
        index: 1
    };

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
        commonService.queryDataByP('analysis/analysisflow/' + $scope.choseYear,{

        }).then(function (data) {
            var flowSum = 0; //总共审批次数
            var ywlSum = 0; //总业务量
            var liwaiNum = 0; //审批环节例外的事项数
            for(var i=0; i<100; i++){
                data[i].sp = Math.round(data[i].sp*100)/100; //平均审批次数转换为2位小数的number型
                flowSum = flowSum + data[i].sp * parseInt(data[i].ywl); // 总共审批次数：平均审批次数*业务量
                ywlSum = ywlSum + parseInt(data[i].ywl); //总业务量
                data[i].index = i + 1; //每条数据添加序号
                if(data[i].liwai > 0){
                    liwaiNum++;
                }
            }
            $scope.ywlSum = ywlSum; //总业务量
            $scope.flowSum = Math.round(flowSum); //总共审批次数
            $scope.avsp = Math.round(flowSum/ywlSum*100)/100; //平均每笔业务审批次数
            $scope.liwaiNum = liwaiNum;
            vm.items = data;

        }, function () {
            console.info('responce error,please call the administrator');
        });
    };
    $scope.queryData();
}]);