/**
 * @ngdoc controller
 * @name app.controller.userCtrl
 * @author l13608
 * @description 编辑用户控制器
 * @date 2017/3/24 15:24
 */
app.controller('roleAllotCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {
    //  排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
    $scope.desc = 0;
    $scope.col = '';

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };

    /**
     * @author l13608
     * @description 获取用户列表
     * @date 2017/5/17 17:39
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
     * @description 跳转编辑用户列表
     * @date 2017/3/24 15:26
     */
    $scope.roleAllot = function (item) {
        $state.go("sysMng.roleAllotPage",{
            userId:item.id,
            userRole:item.roleid,
            userCity:item.department,
            cityType:item.type
        })
    };
}]);

/**
 * @ngdoc controller
 * @name app.controller.roleAllotPageCtrl
 * @author l13608
 * @description 分配角色
 * @date 2017/3/24 15:24
 */
 app.controller('roleAllotPageCtrl', ['$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($scope, $state, commonService, $sce, $stateParams,toastr) {

     var baseData={};
     commonService.queryDataByP('sysMng/baseDataList', {
     }).then(function (data) {
         baseData=data;

         if ($stateParams.cityType == 'city'){
             $scope.CityOrDep =baseData.dsList;
         }else if ($stateParams.cityType == 'province'){
             $scope.CityOrDep =baseData.szList;
         }
     }, function () {
         toastr.error('返回信息失败，请联系管理员');
     });


     $scope.userRole = $stateParams.userRole;
     $scope.userCity = $stateParams.userCity;
     $scope.cityType = $stateParams.cityType;
    /*
          if(null== $scope.userCity){
          $scope.userCity='1';
          }*/
   /*  if ($scope.cityType == 'city'){
        // $scope.CityOrDep = ['广州市','韶关市','深圳市','珠海市','汕头市','佛山市','江门市','湛江市','茂名市','肇庆市','惠州市','梅州市','汕尾市','河源市','阳江市','清远市','东莞市','中山市','潮州市','揭阳市','云浮市','顺德区'];
         $scope.CityOrDep =baseData.dsList;
     }else if ($scope.cityType == 'province'){
        // $scope.CityOrDep = ['广东省密码管理局','广东省人力资源和社会保障厅','广东省知识产权局','广东省商务厅','广东省国家安全厅','广东省公安厅','广东省食品药品监督管理局','广东省残疾人联合会','广东省财政厅','广东省科学技术厅','广东省文化厅','广东省经济和信息化委员会','广东省旅游局','广东省交通运输厅','广东省体育局','广东省人民政府法制办公室','广东省人民政府地方志办公室','广东省地方税务局','广东省农业厅','广东省人防办','广东省发展和改革委员会','广东省水利厅','广东省档案局','广东省社会保险基金管理局','广东省海洋与渔业局','广东省妇女联合会','广东省住房和城乡建设厅','广东省工商行政管理局','广东省盐务局','广东省气象局','广东省统计局','广东省林业厅','广东省民政厅','广东省国土资源厅','广东省民族宗教事务委员会','广东省司法厅','广东省机构编制委员会办公室','广东省环境保护厅','广东省质量技术监督局','广东省审计厅','广东省新闻出版广电局','广东省教育厅','广东省中医药局','广东省安全生产监督管理局','广东省人民政府国有资产监督管理委员会','广东省人民政府金融工作办公室','广东省卫生计生委','广东省地震局'];
         $scope.CityOrDep =baseData.szList;

     }/!*else {
     toastr.error('检查用户组织机构失败');
     }*!/
*/
     /**
     * @author l13608
     * @description 查询角色信息
     * @date 2017/3/24 15:29
     */
     $scope.queryData = function () {
         commonService.queryDataByP('sysMng/roleLists', {
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
         }
         $scope.userCity='';
     };

     $scope.cityShow=function () {
         // $scope.CityOrDep = ['广州市','韶关市','深圳市','珠海市','汕头市','佛山市','江门市','湛江市','茂名市','肇庆市','惠州市','梅州市','汕尾市','河源市','阳江市','清远市','东莞市','中山市','潮州市','揭阳市','云浮市','顺德区'];
         $scope.CityOrDep =baseData.dsList;
     };


     $scope.depShow=function () {
         // $scope.CityOrDep = ['广东省密码管理局','广东省人力资源和社会保障厅','广东省知识产权局','广东省商务厅','广东省国家安全厅','广东省公安厅','广东省食品药品监督管理局','广东省残疾人联合会','广东省财政厅','广东省科学技术厅','广东省文化厅','广东省经济和信息化委员会','广东省旅游局','广东省交通运输厅','广东省体育局','广东省人民政府法制办公室','广东省人民政府地方志办公室','广东省地方税务局','广东省农业厅','广东省人防办','广东省发展和改革委员会','广东省水利厅','广东省档案局','广东省社会保险基金管理局','广东省海洋与渔业局','广东省妇女联合会','广东省住房和城乡建设厅','广东省工商行政管理局','广东省盐务局','广东省气象局','广东省统计局','广东省林业厅','广东省民政厅','广东省国土资源厅','广东省民族宗教事务委员会','广东省司法厅','广东省机构编制委员会办公室','广东省环境保护厅','广东省质量技术监督局','广东省审计厅','广东省新闻出版广电局','广东省教育厅','广东省中医药局','广东省安全生产监督管理局','广东省人民政府国有资产监督管理委员会','广东省人民政府金融工作办公室','广东省卫生计生委','广东省地震局'];
         $scope.CityOrDep =baseData.szList;
     };

     /**
     * @author l13608
     * @description 编辑用户确定按钮
     * @date 2017/3/24 15:30
     */
     $scope.editUserSub = function () {

         /**
         * @author l13608
         * @description 更新用户角色配置
         * @date 2017/3/24 15:30
         */
        commonService.queryDataByP('sysMng/updateUserRole', {
             id:$stateParams.userId,
             department:$scope.userCity,
             type:$scope.cityType,
             role_id:$scope.userRole
         }).then(function (data) {
             toastr.success('编辑用户成功');
            $state.go("sysMng.roleAllot");
         }, function () {
             toastr.error('返回信息错误，请联系管理员');
         });
     };

     $scope.editUserBack = function () {
         $state.go("sysMng.roleAllot");
     }


 }]);