/**
 * @ngdoc controller
 * @name app.controller.fileCtrl
 * @author l13608
 * @description 文件管理控制器
 * @date 2017/3/24 14:55
*/

app.controller('fileCtrl', ['$http', '$scope', '$state', 'commonService', '$sce', '$stateParams','toastr', function ($http, $scope, $state, commonService, $sce, $stateParams,toastr) {
    /**
     * @author l13608
     * @description  排序需要设定的参数   desc:true 为升序；desc:false 为降序 ，col为排序的表格字段
     * @date 2017/3/24 14:56
    */
    $scope.desc = 0;
    $scope.col = '';
    var uploadFileModal = $('#uploadFileModal');
    $scope.ifAdmin=false;

    //判断是否为系统管理员，否则不显示上传和删除文件的按钮
    $http({
        method: 'GET',
        url: 'sysMng/userInfo'
    }).success(function (data) {
        if(data.roleid == '1'){
            $scope.ifAdmin=true;
        }else{
            $scope.ifAdmin=false;
        }
    }).error(function () {
        deferred.reject('There was an error');
    });
    /*$scope.upShow=false;
    $scope.uploade=function(){
        if($scope.upShow==true){
            $scope.upShow=false;
        }else{
            $scope.upShow=true;
        }
    };*/

    /**
     * @author l13608
     * @description 查询文件类型列表
     * @date 2017/3/24 14:56
    */
    $scope.queryFileType = function () {
        commonService.queryDataByP('sysMng/fileTypeList', {
        }).then(function (data) {
            $scope.typeList=data;
        }, function () {
            toastr.error("获取文件类型失败，请联系管理员");
        });
    };
    $scope.queryFileType();

    var vm = $scope.vm = {};
    vm.page = {
        size: 10,
        index: 1
    };
    /**
     * @description 监听删除文件的摸态窗口
     * @author l13608
     * @date 2017-2-16
     */
    var delFileModal = $("#delFileModal");

    /**
     * @ngdoc controller
     * @description 拿到所有文件信息
     * @author l13608
     * @date 2017-2-16
     */
    $scope.queryData = function () {
        var roleid = commonService.getroleid();
        if(roleid=='1'){
            $scope.ifAdmin=true;
        }
        vm.items = null;
        // 查询当前数据
        commonService.queryDataByP('sysMng/fileInfoList', {
            fileName:$scope.fileName
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
     * @ngdoc controller
     * @description 通过id下载文件
     * @author l13608
     * @date 2017-2-16
     */
    $scope.downloadFile = function (fileId) {
        commonService.queryDataByP('sysMng/ifDownloadFile',{
            id:fileId
        }).then(function(data){
            console.info(data);
            if(data){
                window.location.href = 'sysMng/downloadFile?id=' + fileId;
                /*commonService.queryDataByP('sysMng/downloadFile', {
                    id:fileId.id
                });*/
            }else{
                toastr.error('文件不存在');
            }
        },function(){
            toastr.error("返回信息失败，请联系管理员");
        });


    };

    /**
     * @description 点击删除按钮的时候拿到要删除文件的id和文件名
     * @author l13608
     * @date 2017-2-20
     */
    $scope.showDeleteFileModel = function (fileId,fileName) {
        $scope.delFileName = fileName;
        $scope.fileId = fileId;
    };


    /**
     * @description 通过id删除文件
     * @author l13608
     * @date 2017-2-16
     */
    $scope.delFileFun = function () {
        commonService.queryDataByP('sysMng/delFile', {
            id:$scope.fileId
        }).then(function (data) {
            $scope.queryData();
            delFileModal.modal('hide');
            if(data.resultMsg=='ok'){
                toastr.success('删除成功');
            }else{
                toastr.success(data.resultMsg);
            }
        }, function () {
            toastr.error("返回信息失败，请联系管理员");
        });
    };

    /**
     * @author l13608
     * @description 通过form上传文件提交
     * @date 2017/3/24 14:57
     */
    $scope.submit2 = function() {
        var options = {
            url: 'sysMng/fileUpload?t=' + Math.random(),
            success: function (data) {
                if(data=='ok'){
                    toastr.success('上传成功');
                    $scope.queryData();
                    uploadFileModal.modal('hide');
                }else{
                    toastr.error(data);
                }
            },
            resetForm: true,
            clearForm: true
        };
        $("#upload-file").ajaxSubmit(options);
    };

    /*$scope.submit = function() {
        var delFile;
        if ($scope.form.file.$valid && $scope.file) {
            var fileName = $scope.file.name;
            var ext=fileName.substr(fileName.lastIndexOf('.')+1);
            var fileSize = $scope.file.size;
            console.log('文件信息',fileName,fileSize);
            if(fileSize <= 104857600&&(ext == 'doc'||ext=='docx'||ext=='xls'||ext=='xlsx'||ext=='pdf')){
                delFile = $scope.file;
                $scope.upload(delFile);
            }else if(fileSize > 104857600){
                toastr.error('文件大小超出100M');
                $scope.file = null;
            }else {
                toastr.error('不支持该文件类型');
                $scope.file = null;
            }
        }
    };*/

    /**
     * @author l13608
     * @description 关闭上传文件的摸态窗口
     * @date 2017/4/19 11:46
    */
    $scope.closeUpFileModal = function () {
        $('#upload-file').resetForm();
        uploadFileModal.modal('hide');
    };

    /**
     * @author l13608
     * @description 上传文件方法
     * @date 2017/3/24 14:58
    */
    /*$scope.upload = function (file) {
        var typeId = $scope.selectedFileType;
        if ((typeId != undefined )&&(typeId != '' )&&(typeId != null) ){
            Upload.upload({
                url: 'sysMng/uploadFile?typeId='+typeId,
                data: {file: file}
            }).then(function (resp) {
                if(resp.data.resultMsg=='ok'){
                    toastr.success('上传成功');
                    $scope.queryData();
                    uploadFileModal.modal('hide');
                    $scope.selectedFileType = '';
                    $scope.file = null;
                }else{
                    toastr.success(resp.data.resultMsg);
                }
            }, function (resp) {
                toastr.error("上传失败");
            });
        }else {
            toastr.error('请选择一个文件类型');
        }
    };*/

    /*
     *@author l13608
     *@description 跳转编辑文件类型
     *@param
     *@date 11:05 2017/2/28
    */

    $scope.fileTypeEdit = function () {
        $state.go('sysMng.editFileType');
    }
}]);
