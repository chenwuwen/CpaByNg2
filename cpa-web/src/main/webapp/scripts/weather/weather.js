"use strict";
app.controller("BaiduWeatherCtrl", ["$scope", "weatherService", "locationService", function (e, n, t) {
    var r = undefined;
    e.errorMessage = "", t.getLocation().then(function (n) {
        0 === n.status ? r = n.content.address_detail.city : e.errorMessage = n.message
    }, function (e) {
        console.warn(e)
    }).then(function () {
        n.getWeather(r).then(function (n) {
            var t = n.status;
            "success" === t ? e.weathers = n.results : e.warningMessage = t
        }, function (e) {
            console.warn(e)
        })
    }, function (e) {
        console.warn(e)
    })
}]);