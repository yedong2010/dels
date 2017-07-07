'use strict';
/**
 * 事项进驻情况-已进驻部门详情控制器
 * @author m13624
 * @date 2016-12-27
 */
app.controller('enterDepartmentDetailCtrl', ['$scope', '$state', '$stateParams', 'commonService', function ($scope, $state, $stateParams, commonService) {
    var vm = $scope.vm = {};
    $scope.name = $stateParams.name;
    $scope.type = $stateParams.type;
    vm.page = {
        size: 10,
        index: 1
    };

    $scope.queryDepartmentDetail = function () {
        vm.items = null;
        commonService.queryDataByP('rate/sxjzbmxq/' + $scope.type + '/', {name: $scope.name}).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function () {
            console.info('error in responce!');
        });
    };
    $scope.queryDepartmentDetail();

    $scope.goParent = function () {
        $state.go('indicators.enterSituation.index', {type: $scope.type, lasttype: $scope.type});
    };
}]);
