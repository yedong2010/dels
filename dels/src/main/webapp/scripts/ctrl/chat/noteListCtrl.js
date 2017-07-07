'use strict';
/**
 * @ngdoc controller
 * @name app.controller.noteListCtrl
 * @requires $scope作用域,$state路由,$timeout延时执行,$stateParams路由参数控制,commonService
 * @description 楼主发布帖--主题帖发布和编辑列表
 * @author z11595
 * @date 2017-1-12
 */
app.controller('noteListCtrl', ['$scope', 'commonService', 'toastr', '$state', function ($scope, commonService, toastr, $state) {
    /**
     * @description 获取数据字典
     */
    commonService.getDict().then(function (data) {
        $scope.dict = data;
    });
    /**
     * @description 获取当前用户信息，判断是哪种用户类型
     */
    $scope.rolId = commonService.getroleid();
    /**
     *
     * @type {{}} 定义当前vm 用于控制数据、分页
     */
    var vm = $scope.vm = {};
    /**
     *
     * @type {{size: number, index: number}} 每页显示10条数据，默认显示第1页(首页)
     */
    vm.page = {
        size: 10,
        index: 1
    };
    /**
     * @description 查询当前内容编辑发帖列表
     * @return 如果是系统管理员，返回的是所有主题列表，如果不是，则获取的是自己发布的主题列表
     */
    var query = function () {
        vm.items = null;
        if($scope.rolId == 5){
            commonService.queryData('chat/list').then(function (data) {
                for(var i=0; i<data.length; i++){
                    data[i].index = i + 1;
                }
                vm.items = data;
            }, function (error) {
                console.log('error in responce!');
            });
        }else{
            commonService.queryData('chat/self').then(function (data) {
                vm.items = data;
            }, function (error) {
                console.log('error in responce!');
            });
        }
    };
    query();
    /**
     * @description 根据ID删除当前帖信息
     * @param id {number} ID信息
     */
    $scope.delete = function (id) {
        commonService.openConfirmWindow('确认删除', '是否确认删除当前编辑内容？').then(function () {
            commonService.delByUrl('chat/del/' + id).then(function () {
                toastr.success('删除当前内容成功!');
                var index = 0;//记录当前元素下标
                angular.forEach(vm.items, function (item) {
                    if (item.id === id) {
                        vm.items.splice(index, 1);//删除当前下标的选项卡元素
                    }
                    index++;
                });
            });
        });
    };
    /**
     * @description 跳转当前新增发帖的界面
     * @author z11595
     */
    $scope.addNote = function () {
        $state.go('chat.addNote');
    };
}]);