/*
 *@author l13608
 *@description 主题分析控制器
 *@date 10:56 2017/3/19
*/
app.controller('themeCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    //  排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
    $scope.desc = 0;
    $scope.col = '';

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    //增加主题库的摸态窗口
    var addThemeLibModal = $('#addThemeLib');
    //编辑主题库的摸态窗口
    var editThemeLibModal = $('#editTheme');
    //删除主题库的摸态窗口
    var delThemeModal =$('#delThemeModal');

    //拿到所有的主题库
    $scope.queryData = function () {
        vm.items = null;
        // 查询当前数据
        commonService.queryDataByP('dataIndex/FXZTK', {
        }).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function () {
        });
    };
    $scope.queryData();

    //增加主题库
    $scope.addThemeLib = function () {
        commonService.queryDataByP('dataIndex/saveFXZTK', {
            theme:$scope.addThemeLibName//新增主题库的名称
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            toastr.success('增加主题库成功');
            $scope.addThemeLibName = null;
            addThemeLibModal.modal('hide');
            $scope.queryData();
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };

    //关闭增加主题库摸态窗口
    $scope.closeAddThemeLib = function () {
        $scope.addThemeLibName = null;
        addThemeLibModal.modal('hide');
    };

    //跳转展示主题界面
    $scope.showThemeLib = function (themeId) {
        $state.go('dataIndex.showTheme',{
            themeId:themeId
        });
    };

    //拿到要编辑的主题库id和名称
    $scope.getEditTheme = function (id,themeName) {
        $scope.editThemeId = id;
        $scope.editThemeName = themeName;
    };

    //编辑主题库
    $scope.editTheme = function () {
        commonService.queryDataByP('dataIndex/saveFXZTK', {
            themeId:$scope.editThemeId, //要编辑主题库的id
            theme:$scope.editThemeName //更新的主题名
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            toastr.success('编辑主题库成功');
            editThemeLibModal.modal('hide');
            $scope.queryData();
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };

    //拿到要删除主题库的id和名字
    $scope.getDelThemeLib = function (id,name) {
        $scope.delThemeId = id;
        $scope.delThemeName = name;
    };

    //根据id删除主题库
    $scope.delTheme = function () {
        commonService.queryDataByP('dataIndex/deleteFXZTK', {
            themeId:$scope.delThemeId //要删除主题库的id
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            toastr.success('删除主题库成功');
            delThemeModal.modal('hide');
            $scope.queryData();
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };
}]);

//展示主题库内容控制器
app.controller('showThemeCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    //  排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
    $scope.desc = 0;
    $scope.col = '';

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    //增加主题摸态窗口
    var addThemeModal = $('#addTheme');

    //删除主题摸态窗口
    var delThemeModal = $('#delThemeModal');

    //编辑主题的窗口
    var editThemeModal = $('#editTheme');

    //查看某个主题库的内容
    $scope.queryData = function () {
        vm.items = null;
        // 查询当前数据
        commonService.queryDataByP('dataIndex/FXZT', {
            themeId:$stateParams.themeId
        }).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function () {
        });
    };
    $scope.queryData();

    //返回主题库界面
    $scope.goBack = function () {
        $state.go('dataIndex.theme');
    };

    $scope.addTheme = function () {
        commonService.queryDataByP('dataIndex/saveFXZT', {
            tName:$scope.addThemeName, //要增加的主题名
            tContent:$scope.themeDes, //要增加的主题描述
            themeId:$stateParams.themeId
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            $scope.addThemeName = null;
            $scope.themeDes = null;
            toastr.success('增加主题成功');
            addThemeModal.modal('hide');
            $scope.queryData();
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };

    //关闭增加主题摸态窗口
    $scope.closeAddThemeModal = function () {
        $scope.addThemeName = null;
        $scope.themeDes = null;
        addThemeModal.modal('hide');
    };

    //跳转展示主题内容界面
    $scope.showThemeContent = function (themeName,themeContent) {
        $state.go('dataIndex.showThemeContent',{
            themeName:themeName,
            themeContent:themeContent,
            themeId:$stateParams.themeId
        });
    };
    //拿到要删除的主题id和名称
    $scope.getDelTheme = function (id,name) {
        $scope.delThemeId = id;
        $scope.delThemeName = name;
    };

    //根据id删除主题
    $scope.delTheme = function () {
        commonService.queryDataByP('dataIndex/deleteFXZT', {
            id:$scope.delThemeId
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            toastr.success('删除主题成功');
            delThemeModal.modal('hide');
            $scope.queryData();
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };

    //拿到要编辑主题的id和内容
    $scope.getEditTheme = function (id,name,content) {
        $scope.editThemeId = id;
        $scope.editThemeName = name;
        $scope.editThemeContent = content;
    };

    //编辑主题
    $scope.editTheme = function () {
        commonService.queryDataByP('dataIndex/saveFXZT', {
            id:$scope.editThemeId, //要编辑的主题id
            tName:$scope.editThemeName, //要编辑的主题名
            tContent:$scope.editThemeContent //要编辑的主题内容
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            toastr.success('编辑主题成功');
            editThemeModal.modal('hide');
            $scope.queryData();
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };
}]);

//展示主题内容控制器
app.controller('showThemeContentCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    $scope.themeName = $stateParams.themeName;
    $scope.themeContent = $stateParams.themeContent;

    //返回上一级目录
    $scope.goBack = function () {
        $state.go('dataIndex.showTheme',{
            themeId:$stateParams.themeId
        });
    };
}]);