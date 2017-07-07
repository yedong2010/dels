/*
 *@author l13608
 *@description 数据预处理控制器
 *@date 11:42 2017/3/17
*/
app.controller('dataIndexPretCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    //  排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
    $scope.desc = 0;
    $scope.col = '';

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    var delRuleModal = $('#delRuleModal');

    var editRuleModal = $('#editRule');

    // 全局获取数据
    $scope.queryData = function () {
        vm.items = null;
        // 查询当前数据
        commonService.queryDataByP('dataIndex/YCLGZ', {
        }).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function () {
        });
    };
    $scope.queryData();

    //跳转新增规则界面
    $scope.addDataPretRole = function () {
        $state.go('dataIndex.addPretreatment');
    };
    
    //跳转新增分组界面
    $scope.goGroup = function () {
      $state.go('dataIndex.group');
    };

    //拿到要删除规则的id和名称
    $scope.getDelRule = function (id,ruleName) {
      $scope.delRuleId = id;
      $scope.delRuleName = ruleName;
    };

    //根据id删除规则
    $scope.delRule = function () {
        commonService.queryDataByP('dataIndex/deleteYCLGZ', {
            id:$scope.delRuleId//分组的id
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            toastr.success('删除成功');
            delRuleModal.modal('hide');
            $scope.queryData();
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };

    //查询分组列表
    $scope.queryGroupData = function () {
        // 查询当前数据
        commonService.queryDataByP('dataIndex/YCLFZ', {
        }).then(function (data) {
            $scope.rules = data;
        }, function () {
        });
    };
    $scope.queryGroupData();
    //拿到要编辑规则的id，名称，描述和分组
    $scope.getEditRule = function (id,name,content,groupId) {
        $scope.editRuleId = id;
        $scope.editRuleName = name;
        $scope.editRuleContent = content;
        $scope.editRuleGroupId = groupId;
    };

    editRuleModal.on("show.bs.modal", function(e) {
        // 拿到分组信息
        commonService.queryDataByP('dataIndex/YCLFZ', {
        }).then(function (data) {
            $scope.rules = data;
        }, function () {
        });
    });
    //点击确定编辑更新规则
    $scope.editRule = function () {
        commonService.queryDataByP('dataIndex/saveYCLGZ', {
                id:$scope.editRuleId,
                rname:$scope.editRuleName,
                rcontent:$scope.editRuleContent,
                groupId:$scope.editRuleGroupId
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            toastr.success('编辑成功');
            editRuleModal.modal('hide');
            $scope.queryData();
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };

    //跳转展示规则界面
    $scope.showRuleContent = function (name,content) {
        $state.go('dataIndex.showRule',{
            ruleName:name,
            ruleContent:content
        });
    };
}]);

/*
 *@author l13608
 *@description 增加数据预处理控制器
 *@date 15:39 2017/3/17
*/
app.controller('addPretreatmentCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    //  排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
    $scope.desc = 0;
    $scope.col = '';

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    $scope.goBack = function () {
        $state.go('dataIndex.pretreatment');
    };

    /*
     *@author l13608
     *@description 拿到所有的分组
     *@date 16:40 2017/3/17
    */
    $scope.queryData = function () {
        vm.items = null;
        // 查询当前数据
        commonService.queryDataByP('dataIndex/YCLFZ', {
        }).then(function (data) {
            $scope.rules = data;
        }, function () {
        });
    };
    $scope.queryData();

    /**
     * @description 增加规则
     */
    $scope.addRuleSub = function () {
        commonService.queryDataByP('dataIndex/saveYCLGZ', {
            rname:$scope.ruleName,//增加规则的名称
            rcontent:$scope.ruleDes,//增加规则的描述
            groupId:$scope.selectedRule//分组的id
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            toastr.success('增加规则成功');
            $state.go('dataIndex.pretreatment');
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };
}]);

/*
 *@author l13608
 *@description 增加分组控制器
 *@date 16:27 2017/3/17
*/
app.controller('groupCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    //  排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
    $scope.desc = 0;
    $scope.col = '';

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    //删除分组弹窗
    var delGroupModal = $("#delGroupModal");
    //增加分组弹框
    var addRuleModal = $("#addGroup");

    // 全局获取数据
    $scope.queryData = function () {
        vm.items = null;
        // 查询当前数据
        commonService.queryDataByP('dataIndex/YCLFZ', {
        }).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function () {
        });
    };
    $scope.queryData();

    //返回预处理配置界面
    $scope.goBack = function () {
        $state.go('dataIndex.pretreatment');
    };

    //拿到要删除的分组的id和名称
    $scope.getDelGroup = function (id,groupName) {
        $scope.delGroupId = id;
        $scope.delGroupName = groupName;
    };

    //根据分组id删除分组
    $scope.delRule = function () {
        commonService.queryDataByP('dataIndex/deleteYCLFZ', {
            id:$scope.delGroupId//分组的id
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            toastr.success('删除成功');
            delGroupModal.modal('hide');
            $scope.queryData();
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };

    //增加分组
    $scope.addGroup = function () {
        commonService.queryDataByP('dataIndex/saveYCLFZ', {
            groupName:$scope.addGroupName//新建的分组名称
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            toastr.success('新建分组成功');
            $scope.addGroupName = null;
            addRuleModal.modal('hide');
            $scope.queryData();
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };

    //关闭增加分组摸态窗口
    $scope.closeAddGroupModal = function () {
        $scope.addGroupName = null;
        addRuleModal.modal('hide');
    };
}]);

//展示规则内容控制器
app.controller('showRuleCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    $scope.ruleName = $stateParams.ruleName;
    $scope.ruleContent = $stateParams.ruleContent;

    //返回上一级目录
    $scope.goBack = function () {
        $state.go('dataIndex.pretreatment');
    };
}]);
