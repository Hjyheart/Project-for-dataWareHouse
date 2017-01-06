/**
 * Created by hongjiayong on 2017/1/1.
 */
app.controller('typeCtrl', ['$scope', '$http', function ($scope, $http) {
    var type_data = [
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
    $scope.movies = null;
    $scope.typeName = '';

    this.$onInit = function () {

    };

    // 绘制比较图表
    function makeChartTimeCompare(objS, objH) {
        type_data[0].values.push(objS);
        type_data[1].values.push(objH);

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
                var svg = d3.select('#compare svg').datum(type_data);
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

    // 按类型查找
    $scope.typeSearch = function () {
        if ($('#type-name').val() !== ''){
            $http({
                method: 'POST',
                url: 'http://localhost:8080/movie/findByTypeName',
                params:{
                    'name': $('#type-name').val()
                }
            }).then( res=>{
                console.log(res.data);
                $scope.movies = res.data.movie;
                var result = {
                    'name': $('#type-name').val(),
                    'count': res.data.movie.length,
                    'mysqlTime': res.data.mysqlTime,
                    'hiveTime' : res.data.hiveTime
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
                    }
                );
                $scope.movieName = $('#movie-name').val();
            }).catch( err=>{
                console.log(err);
            })
        }
    }
}]);