'use strict';
/**
 * @description 数据分析-全流程事项分析
 * @author m13624
 * @date 2017-03-13
 */
app.controller('analysisProcessCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams', function ($scope, $state, commonService, $sce, $stateParams) {
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
        $scope.conformSum = 0;
        $scope.queryData();
    };

    /**
     * @description 全局获取数据,点击查询之后要
     * @param type（city：地市；county：区县；）
     */
    $scope.queryData = function () {
        commonService.queryDataByP('analysis/analysisprocess/' + $scope.choseYear,{

        }).then(function (data) {
            var conformSum = 0; //全流程符合部门数
            for(var i = 0; i<data.length; i++){
                if(parseInt(data[i].sjqlcsxs) + parseInt(data[i].wywqlcsxs) == parseInt(data[i].sbqlcsxs)){
                    conformSum ++;
                }
                data[i].index = i + 1; //每条数据添加序号
            }
            vm.items = data;
            $scope.conformSum = conformSum;
            $scope.conformSumRate = Math.round((conformSum/data.length)*100);

        }, function () {
            console.info('responce error,please call the administrator');
        });
    };

    $scope.queryData();

}]);