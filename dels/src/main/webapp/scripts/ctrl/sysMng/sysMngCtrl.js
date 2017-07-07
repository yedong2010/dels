'use strict';
/**
 * @ngdoc controller
 * @name app.controller.userCtrl
 * @author l13608
 * @description 用户管理控制器
 * @date 2017/3/24 15:24
*/
app.controller('userCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    //  排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
    $scope.desc = 0;
    $scope.col = '';
    var modal = $("#modPassPage");
    var delUsermodal = $("#delUserPage");
    //监听模态窗口拿到修改用户的id
    modal.on("show.bs.modal", function(e) {
        // 修改用户密码
        $scope.modPassUserid = $(e.relatedTarget).context.attributes.userId.value;
    });
    //监听模态窗口拿到修改用户的id
    delUsermodal.on("show.bs.modal", function(e){
        // 修改用户的id
        $scope.delUserid = $(e.relatedTarget).context.attributes.userId.value;
        $scope.delUserName = $(e.relatedTarget).context.attributes.userName.value;
        $('#delUserName').html($scope.delUserName);
    });

    if ($stateParams.type) {
        $scope.type = $stateParams.type;
    } else {
        $scope.type = 'province';
    }

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    $scope.ifshowOperation=function(roleid){
        if(roleid!='1' && roleid!='3'&&roleid!='4'){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @author l13608
     * @description 获取用户列表
     * @date 2017/3/24 15:25
    */
    $scope.queryData = function () {
        vm.items = null;
        commonService.queryDataByP('sysMng/userList', {
            uname:$scope.userName,
            roleName:$scope.roleName,
            department:$scope.roleDepartment
        }).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function () {
            toastr.error("返回信息失败，请联系管理员");
        });
    };
    $scope.queryData();

    /**
     * @author l13608
     * @description 跳转增加用户界面
     * @date 2017/3/24 15:26
    */
    $scope.addUserBtn = function (t) {
        $state.go('sysMng.addUser');
    };

    /**
     * @author l13608
     * @description 跳转编辑用户列表
     * @date 2017/3/24 15:26
    */
    $scope.editUserBtn = function (item) {
        console.info("phonenum:",item.phoneNumer);
        $state.go("sysMng.editUser",{
            userId:item.id,
            userName:item.uname,
            userRole:item.roleid,
            userCity:item.department,
            userEmail:item.email,
            userDesc:item.descr,
            passwd:item.passwd,
            phoneNumer:item.phoneNumer,
            cityType:item.type
        })
    };

    /**
     * @author l13608
     * @description 编辑用户状态
     * @param userId:要编辑的用户id，目前用户的状态
     * @date 2017/5/12 9:56
    */
     $scope.editUserStateBtn = function (userId,userName) {
         commonService.queryDataByP('sysMng/changeUserStatus', {
             userId:userId,
             userName:userName
         }).then(function (data) {
             if(null!=data && data.resultMsg!='ok'){
                 toastr.error(data.resultMsg);
                 return;
             }
             toastr.success('修改用户状态成功');
             $scope.queryData();
         }, function () {
             toastr.error('返回信息失败，请联系管理员');
         });
     };

    /**
     * @author l13608
     * @description 根据id删除用户
     * @date 2017/3/24 15:27
    */
    $scope.delUserById = function () {
        commonService.queryDataByP('sysMng/deleteUser', {
            uname:$scope.delUserName,
            id:$scope.delUserid
        }).then(function (data) {
            if(null!=data && data.resultMsg!='ok'){
                toastr.error(data.resultMsg);
                return;
            }
            toastr.success('删除用户成功');
            delUsermodal.modal('hide');
            $scope.queryData();
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };

    /**
     * @author l13608
     * @description 根据用户id修改密码
     * @date 2017/3/24 15:27
    */
    $scope.modPassById = function () {
        if($scope.modPass1 == $scope.modPass2 && $scope.oriPass!= undefined){
            var userPasswd='';
            var oldUserPasswd='';
            if(null != $scope.modPass1 && null != $scope.oriPass){
                var md5Pwd1=$.md5($scope.modPass1);
                userPasswd=$.md5(md5Pwd1+"user");

                var m2=$.md5($scope.oriPass);
                oldUserPasswd=$.md5(m2+"user");
            }

            commonService.queryDataByP('sysMng/updatePassword', {
                userName:$scope.loginName,
                oldPasswd:oldUserPasswd,
                userId:$scope.modPassUserid,
                newPass:userPasswd
             }).then(function (data) {
                if(null!=data && data.resultMsg!='ok'){
                    toastr.error(data.resultMsg);
                    return;
                }
                $scope.modPass1 = null;
                $scope.modPass2 = null;
                $scope.oriPass = null;
                modal.modal('hide');
                toastr.success('更新密码成功');
                $scope.queryData();
             }, function () {
                toastr.error('返回信息失败，请联系管理员');
             });
        }else if ($scope.modPass1 != $scope.modPass2){
            toastr.error("两次输入的新密码不一致");
        }else if ($scope.oriPass == undefined){
            toastr.error("请输入当前用户的密码");
        }
    };

     /**
      * @description: 密码传送后台前，先进行加密处理
      *             加密规则：原始密码进行MD5转码加密，获取密文后，添加user后缀，再次进行MD5加密，传送后台
      * @author: l13595
      * @time: 2017/5/11 15:00
      */
    function userLogin() {
        //md5加密传输
        var salt="user";
        var pwd= $('#passwds').val();
        var md5Pwd1=$.md5(pwd);
        var md5Pwd2=$.md5(md5Pwd1+salt);
        $("#passwd").val(md5Pwd2);
    }

    /*
     *@author l13608
     *@description 当摸态窗口隐藏时调用的方法把修改密码框变空
     *@date 22:58 2017/3/2
     */
    $scope.closeModPass = function () {
        $scope.modPass1 = null;
        $scope.modPass2 = null;
        $scope.oriPass = null;
        modal.modal('hide');
    };
}]);
'use strict';

/**
 * @ngdoc controller
 * @name app.controller.addUser
 * @author l13608
 * @description 增加用户控制器
 * @date 2017/3/24 15:28
*/
app.controller('addUser', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {

    var baseData={};
    commonService.queryDataByP('sysMng/baseDataList', {
    }).then(function (data) {
        baseData=data;
    }, function () {
        toastr.error('返回信息失败，请联系管理员');
    });


    $scope.CityOrDep = baseData.dsList;

    $scope.queryData = function () {
        commonService.queryDataByP('sysMng/roleList', {
        }).then(function (data) {
            $scope.roles=data;
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };
    $scope.queryData();


    var depType = "city";
    $scope.cityShow=function () {
        depType = "city";
        $scope.CityOrDep = baseData.dsList;
    };

    $scope.depShow=function () {
        depType = "province";
        $scope.CityOrDep=baseData.szList;
    };

    /**
     * 选择新增用户的角色，当角色为管理员，隐藏机构等信息，默认为 province
     */
    $scope.addRole = function(){
        if($scope.addUserRole!='2'){
            depType='province';
            $scope.addUserCityOrDep='1';
        }else {
            $scope.addUserCityOrDep='';
        }
    };

    /**
     * @author l13608
     * @description 增加用户
     * @date 2017/3/24 15:28
    */
    $scope.addUser = function () {

        var userPasswd="";
        if(null != $scope.addUserPass){
            var md5Pwd1=$.md5($scope.addUserPass);
            userPasswd=$.md5(md5Pwd1+"user");
        }

        commonService.queryDataForPost('sysMng/addUser', {
            type:depType,
            uname:$scope.addUserName,
            email: $scope.addUserEmail,
            descr: $scope.addUserDesc,
            phoneNumer:$scope.phoneNumer,
            passwd:userPasswd
        }).then(function (data) {
            if(null!=data && data.resultMsg=="ok"){
                toastr.success('增加用户成功');
                $state.go('sysMng.userList');
            }else if(null!=data && data.resultMsg!="ok"){
                toastr.error(data.resultMsg);
                return;
            }
            $state.go('sysMng.userList');
        }, function () {
            toastr.error('返回信息错误，请联系管理员');
        });
    };

    /**
     * @author l13608
     * @description 返回用户展示界面
     * @date 2017/3/24 15:28
    */
    $scope.addUserBack = function () {
        $state.go('sysMng.userList');
    }
}]);

/**
 * @ngdoc controller
 * @name app.controller.editUser
 * @author l13608
 * @description 编辑用户控制器
 * @date 2017/3/24 15:29
*/
app.controller('editUser', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {

    var baseData={};
    commonService.queryDataByP('sysMng/baseDataList', {
    }).then(function (data) {
        baseData=data;
    }, function () {
        toastr.error('返回信息失败，请联系管理员');
    });


    $scope.userName = $stateParams.userName;
    $scope.userEmail = $stateParams.userEmail;
    $scope.userDesc = $stateParams.userDesc;
    $scope.userRole = $stateParams.userRole;
    $scope.passwd = $stateParams.passwd;
    $scope.userCity = $stateParams.userCity;
    $scope.cityType = $stateParams.cityType;
    $scope.phoneNumer = $stateParams.phoneNumer;

    if($scope.userRole=='0'||$scope.userRole=='1'){
        $scope.userCity='1';
    }
    if ($scope.cityType == 'city'){
        $scope.CityOrDep =baseData.dsList;
    }else if ($scope.cityType == 'province'){
        $scope.CityOrDep =baseData.szList;
    }

    /**
     * @author l13608
     * @description 查询角色信息
     * @date 2017/3/24 15:29
    */
    $scope.queryData = function () {
        commonService.queryDataByP('sysMng/roleList', {
        }).then(function (data) {
            $scope.roles = data;
        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };
    $scope.queryData();

    /**
     * @author l13608
     * @description 编辑角色，分析师以外的角色默认为 province，不设置省级地市信息
     * @date 2017/3/24 15:29
    */
    $scope.editRole = function(){
        if($scope.userRole!='2'){  // 分析师以外的角色默认为 province，不设置省级地市信息
            $scope.cityType='province';
            $scope.userCity='1';
        }else {
            $scope.userCity='';
        }
    };

    $scope.cityShow=function () {
        $scope.CityOrDep =baseData.dsList;
    };

    $scope.depShow=function () {
        $scope.CityOrDep =baseData.szList;
    };

    /**
     * @author l13608
     * @description 编辑用户确定按钮
     * @date 2017/3/24 15:30
    */
    $scope.editUserSub = function () {
        var role_id='';
        if($scope.userName=='admin'){
            role_id='1';
        }else{
            role_id=$scope.userRole;
        }

        /**
         * @author l13608
         * @description 更新用户
         * @date 2017/3/24 15:30
        */
        commonService.queryDataByP('sysMng/updateUser', {
            id:$stateParams.userId,
            uname:$scope.userName,
            email:$scope.userEmail,
            descr:$scope.userDesc,
            phoneNumer:$scope.phoneNumer
        }).then(function (data) {
            toastr.success('编辑用户成功');
            $state.go('sysMng.userList');
        }, function () {
            toastr.error('返回信息错误，请联系管理员');
        });
    };

    /**
     * @author l13608
     * @description 返回用户展示列表
     * @date 2017/3/24 15:31
    */
    $scope.editUserBack = function () {
        $state.go('sysMng.userList');
    };
}]);

/**
 * @ngdoc controller
 * @name app.controller.roleListCtr
 * @author l13608
 * @description 角色展示控制器
 * @date 2017/3/24 15:31
*/
app.controller('roleListCtr', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    //  排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
    $scope.desc = 0;
    $scope.col = '';

    var delRolemodal = $("#delRoleModal");
    /**
     * @author l13608
     * @description 监听模态窗口拿到修改用户的id
     * @date 2017/3/24 15:32
    */
    delRolemodal.on("show.bs.modal", function(e) {
        /**
         * @author l13608
         * @description 删除角色的id和角色名
         * @date 2017/3/24 15:32
        */
        $scope.delRoleid = $(e.relatedTarget).context.attributes.roleId.value;
        $scope.delRoleName = $(e.relatedTarget).context.attributes.roleName.value;
        $('#delRole').html($scope.delRoleName);
    });

    if ($stateParams.type) {
        $scope.type = $stateParams.type;
    } else {
        $scope.type = 'province';
    }

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    /**
     * @author l13608
     * @description 拿到角色信息
     * @date 2017/3/24 15:33
    */
    $scope.queryData = function () {
        vm.items = null;
        commonService.queryDataByP('sysMng/roleList', {
        }).then(function (data) {
            for(var i=0; i<data.length; i++){
                data[i].index = i + 1;
            }
            vm.items = data;
        }, function () {
            toastr.error('返回信息错误，请联系管理员');
        });
    };
    $scope.queryData();

    /**
     * @author l13608
     * @description 跳转增加角色界面
     * @date 2017/3/24 15:33
    */
    $scope.addRole = function (t) {
        $state.go('sysMng.addRole');
    };

    /**
     * @author l13608
     * @description 跳转编辑角色界面
     * @date 2017/3/24 15:34
    */
    $scope.editRoleBtn = function (item) {
        $state.go("sysMng.editRole",{
            roleId:item.id,
            roleName:item.rol_name
        })
    };

    /**
     * @author l13608
     * @description 删除角色
     * @date 2017/3/24 15:34
    */
    $scope.delRole = function () {
        commonService.queryDataByP('sysMng/deleteRole', {
            id:$scope.delRoleid,
            rol_name:$scope.delRoleName
        }).then(function (data) {
            if(null!=data ){
                if(data.resultMsg!='ok'){
                    toastr.error(data.resultMsg);
                }else{
                    toastr.success('删除角色成功');
                }
            }else{
                toastr.error('删除角色失败，请联系管理员');
            }
            delRolemodal.modal('hide');
            $scope.queryData();
        }, function () {
            toastr.error('返回信息错误，请联系管理员');
        });
    };

}]);

/**
 * @ngdoc controller
 * @name app.controller.roleEditCtrl
 * @author l13608
 * @description 编辑角色控制器
 * @date 2017/3/24 15:35
*/
app.controller('roleEditCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    $scope.roleName = $stateParams.roleName;

    var zTreeObj;
    var setting = {
        check:{
            enable: true,
            chkStyle: "checkbox",
            chkboxType: {"Y": "ps", "N": "ps"},
            autoCheckTrigger:false
        },
        view:{
            showIcon:false
        },
        data:{
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parent_id",
                rootPId: 0
            }
        }
    };

    /**
     * @author l13608
     * @description 获取角色权限菜单并把用户管理设置不可选
     * @date 2017/3/24 15:36
    */
    $scope.queryData = function () {
        $scope.roleId = $stateParams.roleId;
        commonService.queryDataByP('sysMng/roleAddForm').then(function (data) {
            $(document).ready(function(){
                zTreeObj = $.fn.zTree.init($("#editRoleTree"), setting, data);
                commonService.queryDataByP('sysMng/roleMenuList', {
                    roleId:$stateParams.roleId,
                    time: Math.random()
                }).then(function (data) {
                    _.each(data,function (item,index) {
                        var node = zTreeObj.getNodeByParam("id",item);
                        zTreeObj.checkNode(node,true);
                    });
                    zTreeObj.expandAll(true);
                    var roleId = commonService.getroleid();
                    zTreeObj.setChkDisabled(zTreeObj.getNodeByParam('id','4'), true);  //角色管理
                    zTreeObj.setChkDisabled(zTreeObj.getNodeByParam('id','5'), true);  //用户管理
                    zTreeObj.setChkDisabled(zTreeObj.getNodeByParam('id','55'), true);  //角色分配
                    zTreeObj.setChkDisabled(zTreeObj.getNodeByParam('id','15'), true);  //日志

                }, function () {
                    toastr.error('返回信息错误，请联系管理员');
                });
            });
        }, function () {
            toastr.error('返回信息错误，请联系管理员');
        });
    };
    $scope.queryData();

    /**
     * @author l13608
     * @description 编辑角色按钮
     * @date 2017/3/24 15:37
    */
    $scope.editRoleSub = function (id) {
        /**
         * @author l13608
         * @description 拿到选中的节点
         * @date 2017/3/24 15:37
        */
        var selectedObjId = [];
        _.each(zTreeObj.getCheckedNodes(true),function (obj,index) {
            selectedObjId.push(obj.id);
        });
        /**
         * @author l13608
         * @description 根据角色id编辑角色
         * @date 2017/3/24 15:38
        */
        commonService.queryDataByP('sysMng/upadteRoleInfo', {
            roleId:$stateParams.roleId,
            role_name:$scope.roleName,
            role_Jurisdiction:selectedObjId
        }).then(function (data) {
            if(data.resultMsg!=null && data.resultMsg=='ok'){
                toastr.success('编辑角色成功');
                $state.go('sysMng.roleList');
            }else{
                toastr.error(data.resultMsg);
            }

        }, function () {
            toastr.error('返回信息失败，请联系管理员');
        });
    };

    /**
     * @author l13608
     * @description 返回角色展示界面
     * @date 2017/3/24 15:38
    */
    $scope.editRoleBack = function () {
        $state.go('sysMng.roleList');
    }
}]);
