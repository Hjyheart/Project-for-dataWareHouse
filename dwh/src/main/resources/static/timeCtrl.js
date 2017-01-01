/**
 * Created by hongjiayong on 2016/12/30.
 */
var app = angular.module('myApp',[]);
app.controller("timeCtrl", ['$scope', '$http', function($scope, $http){
    var time_data = [
        {
            key: 'mysql',
            nonStackable: true,
            values: []
        },
        {
            key: 'hive',
            nonStackable: true,
            values: []
        }
    ];
    $scope.results = [];

    this.$onInit = function () {

    };

    // 绘制比较图表
    function makeChartTimeCompare(objS, objH) {
        time_data[0].values.push(objS);
        time_data[1].values.push(objH);

        nv.addGraph({
            generate: function() {
                var width = $('#compare').clientWidth - 80,
                    height = $('#compare').clientHeight;
                var chart = nv.models.multiBarChart()
                        .width(width)
                        .height(height)
                        .stacked(true)
                    ;
                chart.dispatch.on('renderEnd', function(){
                    console.log('Render Complete');
                });
                var svg = d3.select('#compare svg').datum(time_data);
                console.log('calling chart');
                svg.transition().duration(0).call(chart);
                return chart;
            },
            callback: function(graph) {
                nv.utils.windowResize(function() {
                    var width = nv.utils.windowSize().width;
                    var height = nv.utils.windowSize().height;
                    graph.width(width).height(height);
                    d3.select('#compare svg')
                        .attr('width', width)
                        .attr('height', height)
                        .transition().duration(0)
                        .call(graph);
                });
            }
        });
    };

    // 绘制月份饼图
    function makeChartTimeMonth(data) {
        nv.addGraph(function() {
            var chart = nv.models.pieChart()
                .x(function(d) { return d.key })
                .y(function(d) { return d.y })
                .width($('#chart1').clientWidth - 80)
                .height($('#chart1').clientHeight)
                .showTooltipPercent(true);

            d3.select("#chart1 svg")
                .datum(data)
                .transition().duration(1200)
                .attr('width', $('#chart1').clientWidth - 80)
                .attr('height', $('#chart1').clientHeight)
                .call(chart);

            return chart;
        });

    }

    // 按时间查找
    $scope.timeSearch = function () {
        if ($('#year').val() !== '' && $('#month').val() !== '' && $('#day').val() !== ''){
            $http({
                method: 'GET',
                url: 'http://localhost:8080/movie/findByDay',
                params:{
                    'year': $('#year').val(),
                    'month': $('#month').val(),
                    'day': $('#day').val()
                }
            }).then( res=>{
                console.log(res.data);
                var result = {
                    'count': res.data.count,
                    'mysqlTime': res.data.mysqlTime,
                    'hiveTime' : 800,
                    'date': $('#year').val() + '/' + $('#month').val() + '/' + $('#day').val()
                };
                $scope.results.push(result);
                makeChartTimeCompare(
                    {
                        x: $scope.results.length,
                        y: result.mysqlTime
                    },
                    {
                        x: $scope.results.length,
                        y: result.hiveTime
                    });
            }).catch( err=>{
                console.log(err);
            })
        }else if($('#year').val() !== '' && $('#month').val() !== ''){
            $http({
                method: 'GET',
                url: 'http://localhost:8080/movie/findByMonth',
                params:{
                    'year': $('#year').val(),
                    'month': $('#month').val(),
                }
            }).then( res=>{
                console.log(res.data);
                var result = {
                    'count': res.data.count,
                    'mysqlTime': res.data.mysqlTime,
                    'hiveTime' : 800,
                    'date': $('#year').val() + '/' + $('#month').val()
                };
                $scope.results.push(result);
                makeChartTimeCompare(
                    {
                        x: $scope.results.length,
                        y: result.mysqlTime
                    },
                    {
                        x: $scope.results.length,
                        y: result.hiveTime
                    });
            }).catch( err=>{
                console.log(err);
            })
        }else if($('#year').val() !== ''){
            $http({
                method: 'GET',
                url: 'http://localhost:8080/movie/findByYear',
                params:{
                    'year': $('#year').val(),
                }
            }).then( res=>{
                console.log(res.data);
                var result = {
                    'count': res.data.count,
                    'mysqlTime': res.data.mysqlTime,
                    'hiveTime' : 800,
                    'date': $('#year').val()
                };
                $scope.results.push(result);
                makeChartTimeCompare(
                    {
                        x: $scope.results.length,
                        y: result.mysqlTime
                    },
                    {
                        x: $scope.results.length,
                        y: result.hiveTime
                    });

                var months = [
                    {key: "一月", y: res.data.months[0], color: "#5F5"},
                    {key: "二月", y: res.data.months[1]},
                    {key: "三月", y: res.data.months[2]},
                    {key: "四月", y: res.data.months[3]},
                    {key: "五月", y: res.data.months[4]},
                    {key: "六月", y: res.data.months[5]},
                    {key: "七月", y: res.data.months[6]},
                    {key: "八月", y: res.data.months[7]},
                    {key: "九月", y: res.data.months[8]},
                    {key: "十月", y: res.data.months[9]},
                    {key: "十一月", y: res.data.months[10]},
                    {key: "十二月", y: res.data.months[11]}
                ];
                makeChartTimeMonth(months)

            }).catch( err=>{
                console.log(err);
            })
        }



    };


}]);