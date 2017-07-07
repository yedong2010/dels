'use strict';
/**
 * @ngdoc controller
 * @name app.controller.indexCtrl
 * @description 父级作业控制器
 * @author z11595
 * @date 2016-12-09
 */
app.controller('indexCtrl', ['$scope', '$state', '$location', 'commonService', '$document', '$rootScope','toastr', function ($scope, $state, $location, commonService, $document, $rootScope,toastr) {
    /**
     * @description 获取当前url中路由state状态
     * @example 'http:localhost:8080/dms/#/indicators/threeRate' state=indicators
     * @type {*}
     */
        //var state = $location.path().split('/')[1];
    var state = '';
    /**
     * @description  保存初次获取的菜单内容，后面使用不需要再次从数据库获取
     * @Author l13595
     * @time 2017/1/12 9:41
     */
    var menuDate = {};
    /**
     * @description 判断当前url是否在数据指标统计的模块中，便于加载左侧二级、三级菜单
     * @default 如果state为空默认state=indicators
     */
    /* if (_.isEmpty(state)) {
     state = 'indicators';
     }*/
    /**
     * @description 加载commonservice中的方法，获取用户省级地市类型、用户名、角色名称
     * @Author l13595
     * @time 2017/1/12 9:38
     */
    commonService.userCityType();

    var modal = $("#userModPassPage");
    /**
     * @requires commonService
     * @description 加载导航栏父菜单
     * @Author l13595
     * @time 2017/1/12 9:42
     */
    $scope.queryData = function () {
        commonService.queryDataByP('sysMng/ParentMenu', {}).then(function (data) {
            $scope.parentMenu = data.menu;
            $scope.loginName = data.loginName;
            $rootScope.uname = data.loginName;
            if(sessionStorage.url != '' && sessionStorage.url != null && sessionStorage.url != undefined){
                state = sessionStorage.url.split('.')[0];
            }else{
                state = data.menu[0].url;
            }
            commonService.getAsideMenu2('sysMng/menuInfo').then(function (data2) {
                $scope.item = data2[state];
                menuDate = data2;
                //在点击F5刷新页面的时候，直接跳转当前页面。如果是第一次登陆，则进入到总体统计
                if(sessionStorage.url != '' && sessionStorage.url != null && sessionStorage.url != undefined) {
                    $state.go(sessionStorage.url);
                }else{
                    $state.go($scope.item[0].url);
                }
            }, function (data2) {
                console.info('error in response');
            });
        }, function () {
            console.info('responce error,please call the administrator');
        });
    };
    $scope.queryData();

    /**
     * @description 监听路由跳转控制左侧二级
     *  目前只支持二级子菜单  l13595 2017/1/14 14:23
     */
    $scope.$on('to-menu', function (d, data) {
        state = data.state;
        if('sysError'==state){
            $state.go('sysError');
        }else{
            var s;
            if (data.state.indexOf('.') > -1) {
                s = data.state.split('.')[0];
            } else {
                s = data.state;
            }
            $scope.item = menuDate[s];
            $state.go($scope.item[0].url);
        }
    });


    /**
     * @requires commonService
     * @description 获取当前左侧菜单结构数据
     * @callback 根据当前state的状态，按需装载左侧二级|三级菜单
     */
    /*  commonService.getAsideMenu().then(function (data) {
     if (_.contains(_.keys(data), state)) {
     $scope.item = data[state];
     } else {
     $scope.item = data['indicators'];
     }
     }, function (data) {
     console.info('error in response');
     });*/
    /**
     * @description 监听路由跳转控制左侧二级|三级菜单加载
     */
    /*  $scope.$on('to-menu', function (d, data) {
     var s;
     if (data.state.indexOf('.') > -1) {
     s = data.state.split('.')[0];
     } else {
     s = data.state;
     }
     commonService.getAsideMenu().then(function (data) {
     $scope.item = data[s];
     }, function (data) {
     console.info('error in response');
     });
     });
     */
    /**
     * @description 监听当前文档中元素的class为sidebar-toggle的点击事件
     * @callback 传递当前重新刷新时间，让子级controller中的highchart图表reflow
     */
    $document.on('click', '.sidebar-toggle', function () {
        $rootScope.$broadcast('reflow', 'click toggleBtn');
    });

    /**
     *@author l13608
     *@description 根据用户id修改密码
     *@date 20:31 2017/3/2
     */
    $scope.modPassById1 = function () {
        var userId = commonService.getUserid();
        if($scope.modPass3 == $scope.modPass4 && $scope.oriPass1!= undefined){
            var userPasswd='';
            var oldUserPasswd='';
            var md5Pwd1=$.md5($scope.modPass3);
            userPasswd=$.md5(md5Pwd1+"user");

            var m2=$.md5($scope.oriPass1);
            oldUserPasswd=$.md5(m2+"user");

            commonService.queryDataByP('sysMng/updatePassword', {
                userName:$scope.loginName,
                oldPasswd:oldUserPasswd,
                userId:userId,
                newPass:userPasswd
            }).then(function (data) {
                if(null!=data && data.resultMsg!='ok'){
                    toastr.error(data.resultMsg);
                    return;
                }
                toastr.success('更新密码成功');
                modal.modal('hide');
                $scope.queryData();
            }, function () {
                toastr.error('返回信息失败，请联系管理员');
            });
        }else if ($scope.modPass3 != $scope.modPass4){
            toastr.error("两次输入的新密码不一致");
        }else if ($scope.oriPass1 == undefined){
            toastr.error("请输入当前用户的密码");
        }
    };

    /**
     *@author l13608
     *@description 当摸态窗口隐藏时调用的方法把修改密码框变空
     *@date 22:58 2017/3/2
     */
    modal.on('hidden.bs.modal', function () {
        $('#modPass3').val("");
        $('#modPass4').val("");
        $('#oriPass1').val("");
    });

    $scope.ifShow = true;
    if(commonService.getroleid()=='2'){
        $scope.ifShow = false;
    }

    $scope.logout = function(){
        sessionStorage.url = '';  //先清空session
        window.location.href = 'j_spring_security_logout';
    }
}]);