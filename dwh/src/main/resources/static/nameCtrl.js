/**
 * Created by hongjiayong on 2017/1/1.
 */
app.controller('nameCtrl', ['$scope', '$http', function ($scope, $http) {
    var name_data = [];

    this.$onInit = function () {

    };

    // 绘制比较图表
    function makeChartNameCompare(data, obj) {
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

    // 按名称查找
    $scope.nameSearch = function () {
        makeChartNameCompare(name_data, {
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
    };

}]);