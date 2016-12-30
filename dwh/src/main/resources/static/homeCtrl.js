/**
 * Created by hongjiayong on 2016/12/30.
 */
var app = angular.module('myApp',[]);
app.controller("homeCtrl", ['$scope', '$http', function($scope, $http){
    this.$onInit = function () {

    };

    // 按时间查询
    $scope.toTime =  function(){
        $http({
            method: 'GET',
            url: 'http://localhost:8080/time'
        }).then( res=>{
            $('#content').replaceWith("<div id='content'>" + res.data + "</div>");
            $('.mdui-list-item-active').removeClass('mdui-list-item-active');
            $('#time').addClass('mdui-list-item-active');
        }).catch( err=>{
            console.log(err);
        });
    };

    // 按名称查询
    $scope.toName = function () {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/name'
        }).then( res=>{
            $('#content').replaceWith("<div id='content'>" + res.data + "</div>");
            $('.mdui-list-item-active').removeClass('mdui-list-item-active');
            $('#name').addClass('mdui-list-item-active');
        }).catch( err=>{
            console.log(err);
        });
    };

    // 按导演查询
    $scope.toDirector = function () {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/director'
        }).then( res=>{
            $('#content').replaceWith("<div id='content'>" + res.data + "</div>");
            $('.mdui-list-item-active').removeClass('mdui-list-item-active');
            $('#director').addClass('mdui-list-item-active');
        }).catch( err=>{
            console.log(err);
        });
    };

    // 按演员查询
    $scope.toActor = function () {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/actor'
        }).then( res=>{
            $('#content').replaceWith("<div id='content'>" + res.data + "</div>");
            $('.mdui-list-item-active').removeClass('mdui-list-item-active');
            $('#actor').addClass('mdui-list-item-active');
        }).catch( err=>{
            console.log(err);
        });
    };

    // 按类别查询
    $scope.toCategory = function () {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/category'
        }).then( res=>{
            $('#content').replaceWith("<div id='content'>" + res.data + "</div>");
            $('.mdui-list-item-active').removeClass('mdui-list-item-active');
            $('#category').addClass('mdui-list-item-active');
        }).catch( err=>{
            console.log(err);
        });
    };

    // 按组合查询
    $scope.toCombine = function () {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/combine'
        }).then( res=>{
            $('#content').replaceWith("<div id='content'>" + res.data + "</div>");
            $('.mdui-list-item-active').removeClass('mdui-list-item-active');
            $('#combine').addClass('mdui-list-item-active');
        }).catch( err=>{
            console.log(err);
        });
    };

}]);