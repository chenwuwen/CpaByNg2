/**
 * Created by KANYUN on 2017/4/17.
 */

app.controller('UnitExamCtrl',function($scope, $stateParams, $http){
    $scope.typeCode = $stateParams.typeCode;
    var typeCode = $stateParams.typeCode;
    $http({
        method: 'GET',
        url: '/unitExam/getUnitExam/'+typeCode,
        // params:{typeCode:typeCode}
    }).then(function onSuccess(response) {
        // 请求成功执行代码
        $scope.exams = response.data;
    }).catch(function onError(response) {
        // 请求失败执行代码
        alert('error');
    });
    $scope.submitUnitExam = function(){
        var pData = {authData:$scope.exam.id,reqData:$scope.options.selectData};
        alert("dsad");
        console.log(pData)
    };
});
app.filter('CovertKey',function () { //可以注入依赖 ,前台通过{{key|CovertKey}}即可转换
    return function (x) {
        switch (x){
            case 0:
                return 'A';
            case 1:
                return 'B';
            case 2:
                return 'C';
            case 3:
                return 'D';
            case 4:
                return 'E';
            case 5:
                return 'F';
            case 6:
                return 'G';
            case 7:
                return 'H';
        }
    }
});
// app.controller('zaxiang',function submitUnitExam($scope,$http){
//
// })

    

