/**
 * Created by hongjiayong on 2016/12/30.
 */
var app = angular.module('myApp',[]);
app.controller("timeCtrl", ['$scope', '$http', function($scope, $http){
    var time_data = [];

    this.$onInit = function () {

    };

    // 绘制比较图表
    function makeChartTimeCompare(data, obj) {
        data.push(obj);

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
                var svg = d3.select('#compare svg').datum(data);
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
        makeChartTimeCompare(time_data, {
            key: 'test2',
            nonStackable: true,
            values:[
                {
                    x: '0',
                    y: '0.4'
                },
                {
                    x: '1',
                    y: '0.3'
                }
            ]
        });

        var data = [
            {key: "一月", y: 5, color: "#5F5"},
            {key: "二月", y: 2},
            {key: "三月", y: 9},
            {key: "四月", y: 7},
            {key: "五月", y: 4},
            {key: "六月", y: 3},
            {key: "七月", y: 8},
            {key: "八月", y: 50},
            {key: "九月", y: 53},
            {key: "十月", y: 5},
            {key: "十一月", y: 43},
            {key: "十二月", y: 32}
        ];

        makeChartTimeMonth(data);
    };


}]);