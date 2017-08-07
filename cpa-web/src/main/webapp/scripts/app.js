//该Js为采用anjular-ui-router组件，来进行页面转换

//定义自己的module(app),括号中的是这个module的依赖
var app = angular.module('myapp', ['ui.router','ui.check']);
app.config(function ($stateProvider, $urlRouterProvider) {
    $stateProvider
        .state('index', {
            url: '/index',
            views: {
                '': {
                    templateUrl: 'index.html'
                },
                'navbar@index': {
                    templateUrl: 'navbar.html'
                },
                'welcome@index': {
                    templateUrl: 'welcome.html'
                    // controller: 'welcomeCtrl'
                }
            }
        })
        .state('index.unitExam', { //路由状态
            // abstract: true, //abstract属性是用来定义抽象路由的，在路由的嵌套定义的时候会使用到，不涉及路由嵌套时，须去掉该设置，否则会造成模板不加载
            // params:{typeCode:null},  // 定义一个空对象，接收数据，同样也可以传递普通参数，但都不会在url上显示
            url: '/unitExam/:typeCode', //路由路径
            // templateUrl: 'unitExam.html', //路由填充的模板
            // controller: 'UnitExamCtrl' //此处若写controller,则必须指定一个存在的组件,否则页面可能不正常显示
            views: {
                'welcome@index': {
                    templateUrl: 'unitExam.html'
                }
        }})
    // 默认路径，在status中匹配不到时执行
    $urlRouterProvider.otherwise('/index');
});

app.controller('welcomeCtrl',function($scope){
    $scope.name=localStorage.getItem("userName");
});



