'use strict';
/**
 * @ngdoc controller
 * @name app.controller.replyDetailCtrl
 * @requires $scope作用域,$state路由,$timeout延时执行,$stateParams路由参数控制,commonService
 * @description 评论管理--点击某一个主题，显示该主题下的所有评论
 * @author m13624
 * @date 2017-03-09
 */
app.controller('replyDetailCtrl', ['$scope', 'commonService', 'toastr', '$state','$stateParams', function ($scope, commonService, toastr, $state, $stateParams) {
    /**
     * @description 主题名字
     */
    $scope.theme = $stateParams.theme;
    /**
     * @description 主题ID
     */
    $scope.themeID = $stateParams.id;
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
        commonService.queryData('chat/replyList' + '/' + $scope.themeID).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function (error) {
            console.log('error in responce!');
        });
    };
    query();

    /**
     * @description 根据ID删除当前评论信息
     * @param id {number} ID信息
     */
    $scope.delete = function (id) {
        commonService.openConfirmWindow('确认删除', '是否确认删除该评论？').then(function () {
            commonService.delByUrl('chat/delReply/' + id).then(function () {
                toastr.success('删除评论成功!');
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
     * @description 根据ID发布当前评论信息
     * @param id {number} ID信息
     */
    $scope.approve = function (id) {
        commonService.openConfirmWindow('确认审核', '是否确认审核通过该评论？').then(function () {
            commonService.delByUrl('chat/approveReply/' + id).then(function () {
                toastr.success('审核评论成功!');
                var index = 0;//记录当前元素下标
                angular.forEach(vm.items, function (item) {
                    if (item.id === id) {
                        //vm.items.splice(index, 1);//删除当前下标的选项卡元素
                        vm.items[index].status = '1';//当前评论的状态改为已发布
                    }
                    index++;
                });
            });
        });
    };

    /**
     * @description 返回到评论管理
     */
    $scope.goParent = function () {
        $state.go('chat.reply');
    };

}]);