/**
 * @ngdoc service
 * @name app.commonService
 * @description 公共service服务
 * @author z11595
 * @date 2016-12-09
 */
app.factory('commonService', ['$http', '$q', '$uibModal','$interval', function ($http, $q, $uibModal,$interval) {
    var service = {};
    var cityType = '';
    var t_year = new Date().getFullYear();
    /**
     * 当前用户信息
     * @type {{userName:用户名； rolName：角色名称}}
     */
    var userInfo = {};
    userInfo.userName = '';
    userInfo.rolName = '';
    userInfo.roleid='';
    userInfo.userId='';

    service.getUserid=function(){
        return userInfo.userId;
    };

    service.getUserName = function () {
        return userInfo.userName;
    };
    service.getroleid = function () {
        return userInfo.roleid;
    };
    service.getRolName = function () {
        return userInfo.rolName;
    };
    service.getUserCityType = function () {
        return cityType;
    };

    /**
     * @ngdoc
     * @name app.commonService#getAsideMenu
     * @methodOf app.commonService
     * @description 获取当前二级|三级菜单
     * @requires $http $q
     * @returns {httpPromise} resolve with fetched data, or fails with error description.
     */
    service.getAsideMenu = function () {
        var deferred = $q.defer();
        $http({
            method: 'GET',
            url: 'assets/data/menu.json'
        }).success(function (data) {
            deferred.resolve(data);
        }).error(function () {
            deferred.reject('There was an error');
        });
        return deferred.promise;
    };

    /**
     * 获取侧边栏菜单
     */
    service.getAsideMenu2 = function (url) {
        var deferred = $q.defer();
        $http({
            method: 'GET',
            url: url
        }).success(function (data) {
            deferred.resolve(data);
        }).error(function () {
            deferred.reject('There was an error');
        });
        return deferred.promise;
    };

    /**
     * @ngdoc
     * @name app.commonService#getDict
     * @methodOf app.commonService
     * @description 获取当前数据字典JSON集合
     * @requires $http $q
     * @returns {httpPromise} resolve with fetched data, or fails with error description.
     */
    service.getDict = function () {
        var deferred = $q.defer();
        $http({
            method: 'GET',
            url: 'assets/data/dictionary.json'
        }).success(function (data) {
            deferred.resolve(data);
        }).error(function () {
            deferred.reject('There was an error');
        });
        return deferred.promise;
    };
    /**
     * @ngdoc
     * @name app.commonService#queryData
     * @methodOf app.commonService
     * @description POST请求不传参
     * @requires $http $q
     * @param {string} url 请求的链接地址
     * @returns {httpPromise} resolve with fetched data, or fails with error description.
     */
    service.queryData = function (url) {
        var deferred = $q.defer();
        $http({
            method: 'GET',
            url: url
        }).success(function (data) {
            deferred.resolve(data);
        }).error(function () {
            deferred.reject('There was an error');
        });
        return deferred.promise;
    };
    /**
     * @ngdoc
     * @name app.commonService#queryData
     * @methodOf app.commonService
     * @description POST请求 新增内容
     * @requires $http $q
     * @param {string} url 请求的链接地址
     * @returns {httpPromise} resolve with fetched data, or fails with error description.
     */
    service.addNote = function (t) {
        var deferred = $q.defer();
        $http.post('chat/cnote', t).success(function (data) {
            deferred.resolve(data);
        }).error(function () {
            deferred.reject('There was an error');
        });
        return deferred.promise;
    };
    //当前用户信息
    service.queryUserInfo = function () {
        var deferred = $q.defer();
        $http({
            method: 'GET',
            url: 'sysMng/userInfo'
        }).success(function (data) {
            deferred.resolve(data);
        }).error(function () {
            deferred.reject('There was an error');
        });
        return deferred.promise;
    };


    /**
     * @ngdoc
     * @name app.commonService#queryDataByP
     * @methodOf app.commonService
     * @description POST请求传参
     * @requires $http $q
     * @param {string} url 请求的链接地址
     * @param {object} params 查询附带的参数
     * @returns {httpPromise} resolve with fetched data, or fails with error description.
     */
    service.queryDataByParam = function (url, mock, params) {
        service.checkPower(); /** 控制页面按钮权限 l13595 2017/2/15 10:17 **/
        var deferred = $q.defer();
        if (params.year == 2016) {
            $http({
                method: 'GET',
                url: mock
            }).success(function (data) {
                deferred.resolve(data);
            }).error(function () {
                deferred.reject('There was an error');
            });
        } else {
            $http({
                method: 'GET',
                url: url,
                params: params
            }).success(function (data) {
                deferred.resolve(data);
            }).error(function () {
                deferred.reject('There was an error');
            });
        }
        return deferred.promise;
    };
    /**
     * @ngdoc
     * @name app.commonService#queryDataByP
     * @methodOf app.commonService
     * @description POST请求传参
     * @requires $http $q
     * @param {string} url 请求的链接地址
     * @param {object} params 查询附带的参数
     * @returns {httpPromise} resolve with fetched data, or fails with error description.
     */
    service.queryDataByP = function (url, params) {
        service.checkPower(); /** 控制页面按钮权限 l13595 2017/2/15 10:17 **/
        var deferred = $q.defer();
        $http({
            method: 'GET',
            url: url+'?'+Math.random(),
            params: params
        }).success(function (data) {
            deferred.resolve(data);
        }).error(function () {
            deferred.reject('There was an error');
        });
        return deferred.promise;
    };
    /**
     * 从后台获取当前用户的省级地市类型、用户账号、角色名、用户id
     * 根据角色名判断是否进行消息推送
     * @returns {*}
     */
    service.userCityType = function () {
        var deferred = $q.defer();
        $http({
            method: 'GET',
            url: 'sysMng/userInfo'
        }).success(function (data) {
            cityType = data.type;
            userInfo.userName = data.uname;
            userInfo.userId=data.id;
            userInfo.rolName = data.rolName;
            userInfo.roleid=data.roleid;
            if(data.rolName==data.securityRole){ /** 判断角色，进行安全审计消息推送 l13595 2017/2/15 15:52 **/
                queryMsgData();
            }
            if(data.roleid=='2'){ /** 分析师角色，进行业务量提醒  l13595 2017/3/8 17:44 */
                queryBussinessNum();
            }
            deferred.resolve(data);
        }).error(function () {
            deferred.reject('There was an error');
        });
        return deferred.promise;
    };
    /**
     * @description 加载右下角弹出消息监测栏
     * @Author l13608
     * @time 2017/2/15 9:42
     */
    var queryMsgData = function () {
        service.queryDataByP('sysMng/msgCount').then(function (data) {
            if(data>0){
                var pop=new Pop("","登录错误超标的IP记录有 "+data+" 条");
            }
        }, function (data) {
            console.info('error in response');
        });
    };

    /**
     * @description: 系统业务量少，对分析师用户发弹窗消息提醒
     * @author: l13595
     * @time: 2017/3/8 17:45
     */
    var queryBussinessNum = function(){
        service.queryDataByP('sysMonitor/busWarning').then(function (data) {
            if(null != data && data.businessNum=='0'){
                var pop=new Pop("",data.departMent +" 上个月业务量为" + data.businessNum + "件");
            }
        }, function (data) {
            console.info('error in response');
        });
    };

    /**
     * @description 查询当前根据URL删除item
     * @param url {string} 链接
     * @return {*}
     */
    service.delByUrl = function (url) {
        var deferred = $q.defer();
        $http.delete(url).success(function (data) {
            deferred.resolve(data);
        }).error(function () {
            deferred.reject('There was an error');
        });
        return deferred.promise;
    };


    /**
     * 根据当前用户的省级地市类型，隐藏页面的省级及地市的按钮
     */
    service.checkPower = function () {
        var butns = document.getElementsByTagName("button");
        for (var i = 0; i < butns.length; i++) {
            if (butns[i].innerHTML == '省级' && cityType == 'city') {
                butns[i].style.display = "none";
            } else if ((butns[i].innerHTML == '地市' || butns[i].innerHTML == '区县') && cityType == 'province') {
                if (userInfo.roleid != 5 ) {
                    butns[i].style.display = "none";
                }
            } else if (butns[i].innerHTML == '全省' && userInfo.roleid != 5 ) {
                butns[i].style.display = "none";
            } else if ((butns[i].innerHTML.indexOf('添加区划') != -1) && userInfo.roleid != 5 && cityType == 'city') {
                butns[i].style.display = "none";
            }
        }
    };

    service.queryDataForPost = function (url, params) {
        var deferred = $q.defer();
        $http({
            method: 'POST',
            url: url,
            params: params
        }).success(function (data) {
            deferred.resolve(data);
        }).error(function () {
            deferred.reject('There was an error');
        });
        return deferred.promise;
    };
    /**
     * @ngdoc
     * @name app.commonService#openModal
     * @methodOf app.commonService
     * @description 打开模态框
     * @requires $uibModal
     * @param {string} dtype 获取数据字典集合中属性值key(jc,mc,dm,des)
     * @param {string} category 类型(city:地市,county:区县)
     */
    service.openModal = function (dtype, category) {
        var url = category == 'province' ? './view/public/modalContent_SZ.html' : './view/public/modalContent.html';
        var ctrl = category == 'province' ? 'modalInstanceCtrl_SZ' : 'modalInstanceCtrl';
        var modalInstance = $uibModal.open({
            templateUrl: url,
            controller: ctrl,
            resolve: {
                dtype: function () {
                    return dtype;
                },
                category: function () {
                    return category;
                }
            }
        });
        modalInstance.opened.then(function () {//模态窗口打开之后执行的函数
        });
        return modalInstance;
    };

    /**
     * @description 确认框service
     * @param modalTitle 标题
     * @param modalContent 主题内容
     * @param modalInstance 实例
     * @return {*}
     */
    service.openConfirmWindow = function (modalTitle, modalContent, modalInstance) {
        var deferred = $q.defer();
        /*
         * modalInstance是在弹窗的基础上再弹出confirm确认框时从第一个弹窗中传进的$modalInstance,
         * 若是直接在页面上弹出confirm确认框，则不能传$modalInstance,否则会报错
         */
        var confirmModal = $uibModal.open({
            backdrop: 'static',
            templateUrl: './view/public/confirmModelTemplate.html',  // 指向确认框模板
            windowClass: "confirmModal",// 自定义modal上级div的class
            size: 'sm', //大小配置
            resolve: {
                data: function () {
                    return {title: modalTitle, content: modalContent};//surgeonSug: $scope.surgeonSug,
                }
            },
            controller: ['$scope', 'data', '$uibModalInstance', function ($scope, data, $uibModalInstance) {
                console.info(data);
                $scope.title = data.title;
                $scope.content = data.content;
                /**
                 * @ngdoc
                 * @name app.controller.modalInstanceCtrl#ok
                 * @methodOf app.controller.modalInstanceCtrl
                 * @description 点击确认传递当前用户选项
                 * @requires $uibModalInstance
                 * @callback 关闭当前模态框并且将参数传递给上级作用域
                 */
                $scope.ok = function () {
                    $uibModalInstance.close(true);
                };
                /**
                 * @ngdoc
                 * @name app.controller.modalInstanceCtrl#cancel
                 * @methodOf app.controller.modalInstanceCtrl
                 * @description 取消按钮的点击事件
                 * @callback 关闭当前模态框
                 */
                $scope.cancel = function () {
                    $uibModalInstance.dismiss('cancel');
                };
            }]
        });
        // 处理modal关闭后返回的数据
        confirmModal.result.then(function () {
            if (!!modalInstance) {
                modalInstance.dismiss('cancel');
            }
            deferred.resolve();
        }, function () {
        });
        return deferred.promise;
    };
    /**
     * @description 判断是否是今年
     * @param year 选择年
     */
    service.isThisYear = function (year) {
        if (!isNaN(year)) {
            return year == t_year;
        }
        return true;
    };
    return service;
}]);

/**
 * @ngdoc controller
 * @name app.controller.modalInstanceCtrl
 * @description 模态框控制controller
 * @requires $scope $uibModalInstance commonService dtype category
 * @author z11595
 */
app.controller('modalInstanceCtrl', ['$scope', '$uibModalInstance', 'commonService', 'dtype', 'category', function ($scope, $uibModalInstance, commonService, dtype, category) {
    $scope.type = "0";
    $scope.category = _.isEmpty(category) ? 'city' : category;
    var dict = [];
    /**
     * @description 判断类别属于地市还是区县，如果是地市返回XZQHMC
     * @type {string}
     */
    var dict_type = $scope.category == 'city' ? "XZQHMC" : "XZQHQX";
    commonService.getDict().then(function (data) {
        dict = data;
        $scope.items = data[dict_type]["0"];
        $scope.oitems = data[dict_type]["1"];
        $scope.selection = _.pluck($scope.items, dtype);
    });

    /**
     * @description 监听type的变更，触发当前默认选项的更新
     */
    $scope.$watch('type', function (newValue, oldValue) {
        if (_.contains(_.keys(dict[dict_type]), newValue)) {
            $scope.items = dict[dict_type][newValue];
            $scope.oitems = dict[dict_type][oldValue];
            $scope.selection = _.pluck($scope.items, dtype);
        }
    });


    /**
     * @ngdoc
     * @name app.controller.modalInstanceCtrl#updateSelection
     * @methodOf app.controller.modalInstanceCtrl
     * @description 切换行政区划更新默认地市选项
     * @param {object} item 数据模型
     * @callback 更新当前selection用户对地市名称的选择集
     */
    $scope.updateSelection = function (item) {
        if (_.indexOf($scope.selection, item[dtype]) != -1) {
            $scope.selection.splice(_.indexOf($scope.selection, item[dtype]), 1);
        } else {
            $scope.selection.push(item[dtype]);
        }
    };

    /**
     * @ngdoc
     * @name app.controller.modalInstanceCtrl#ok
     * @methodOf app.controller.modalInstanceCtrl
     * @description 点击确认传递当前用户选项
     * @requires $uibModalInstance
     * @param {array} selection 数据模型
     * @param {string} type 当前类型(city:地市,county:区县)
     * @callback 关闭当前模态框并且将参数传递给上级作用域
     */
    $scope.ok = function () {
        if ($scope.category == 'city') {
            $uibModalInstance.close($scope.selection);
        } else {
            $uibModalInstance.close({type: $scope.type, select: $scope.selection});
        }
    };
    /**
     * @ngdoc
     * @name app.controller.modalInstanceCtrl#cancel
     * @methodOf app.controller.modalInstanceCtrl
     * @description 取消按钮的点击事件
     * @callback 关闭当前模态框
     */
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
}]);

/**
 * @ngdoc controller
 * @name app.controller.modalInstanceCtrl_SZ
 * @description 模态框控制controller——省级部门选择
 * @requires $scope $uibModalInstance commonService dtype category
 * @author m13624
 */
app.controller('modalInstanceCtrl_SZ', ['$scope', '$uibModalInstance', 'commonService', 'dtype', 'category', function ($scope, $uibModalInstance, commonService, dtype, category) {
    $scope.category = category;
    $scope.selection = [];
    /**
     * @description json文件里关省级的键名称为SZBMDM，根据键名取出所有的省级部门信息
     * @type {string}
     */
    var dict_type = "SZBMDM";
    commonService.getDict().then(function (data) {
        $scope.items = data[dict_type]["ZZJGDM"];
    });

    /**
     * @ngdoc
     * @name app.controller.modalInstanceCtrl_SZ#updateSelection
     * @methodOf app.controller.modalInstanceCtrl_SZ
     * @description 当勾选省级部门时刷新selection数组中的省级部门
     * @param {object} item 数据模型
     * @callback 更新当前selection用户对地市名称的选择集
     */
    $scope.updateSelection = function (item) {
        if (_.indexOf($scope.selection, item[dtype]) != -1) {
            $scope.selection.splice(_.indexOf($scope.selection, item[dtype]), 1);
        } else {
            $scope.selection.push(item[dtype]);
        }
    };
    /**
     * @ngdoc
     * @name app.controller.modalInstanceCtrl_SZ#ok
     * @methodOf app.controller.modalInstanceCtrl_SZ
     * @description 点击确认传递当前用户选项
     * @requires $uibModalInstance
     * @param {array} selection 数据模型
     * @callback 关闭当前模态框并且将参数传递给上级作用域
     */
    $scope.ok = function () {
        $uibModalInstance.close($scope.selection);
    };
    /**
     * @ngdoc
     * @name app.controller.modalInstanceCtrl_SZ#cancel
     * @methodOf app.controller.modalInstanceCtrl_SZ
     * @description 取消按钮的点击事件
     * @callback 关闭当前模态框
     */
    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
}]);