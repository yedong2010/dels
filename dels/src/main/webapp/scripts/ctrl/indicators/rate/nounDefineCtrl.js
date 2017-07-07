'use strict';
app.controller('nounDefineCtrl', ['$scope', '$location', '$anchorScroll', function ($scope, $location, $anchorScroll) {
    $scope.ifshow=false;

    $scope.lookMenu= function () {
        if($scope.ifshow){
            $scope.ifshow=false;
        }else{
            $scope.ifshow=true;
        }
    }

    $scope.gotoAnchor=function(elm){
        $location.hash(elm);
        $anchorScroll();
    }
}]);