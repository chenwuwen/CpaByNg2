"use strict";
app.factory("locationService", ["$http", function (t) {
    return {
        getLocation: function () {
            return t({
                method: "JSONP",
                url: "http://api.map.baidu.com/location/ip?ak=hBDoMmfaQvkxwifiKdsQij6s"
            }).then(function (t) {
                return t.data
            }, function (t) {
                return t.data
            })
        }
    }
}]);