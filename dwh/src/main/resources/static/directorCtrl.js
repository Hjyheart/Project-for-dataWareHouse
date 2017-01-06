/**
 * Created by hongjiayong on 2017/1/1.
 */
app.controller('directorCtrl', ['$scope', '$http', function ($scope, $http) {
    var director_data = [
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
    $scope.directorName = '';

    this.$onInit = function () {

    };

    // 绘制比较图表
    function makeChartTimeCompare(objS, objH) {
        director_data[0].values.push(objS);
        director_data[1].values.push(objH);

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
                var svg = d3.select('#compare svg').datum(director_data);
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

    // 按导演查询
    $scope.directorSearch = function () {
        if ($('#director-name').val() !== ''){
            $http({
                method: 'GET',
                url: 'http://localhost:8080/movie/findByDirectorName',
                params:{
                    'name': $('#director-name').val()
                }
            }).then( res=>{
                console.log(res.data);
                $scope.movies = res.data.movie;
                var result = {
                    'name': $('#director-name').val(),
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
                $scope.directorName = $('#director-name').val();
            }).catch( err=>{
                console.log(err);
            })
        }
    }
}]);