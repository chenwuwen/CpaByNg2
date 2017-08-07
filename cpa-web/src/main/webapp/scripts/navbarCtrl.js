// 定义navbarCtrl控制器，对应路由中设置的controller,控制显示菜单
app.controller('navbarCtrl', function ($scope) {
        $scope.isAdmin=function () {
            var role = $scope.name=localStorage.getItem("roles");
            if(role=='admin'){
                return true;
            }else{
                return false;
            }
        }
    }
);