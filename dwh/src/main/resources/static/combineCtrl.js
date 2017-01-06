/**
 * Created by hongjiayong on 2017/1/4.
 */
app.controller('combineCtrl', ['$scope', '$http', function ($scope, $http) {
    var combine_data = [
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

    this.$onInit = function () {

    };

    // 绘制比较图表
    function makeChartTimeCompare(objS, objH) {
        combine_data[0].values.push(objS);
        combine_data[1].values.push(objH);

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
                var svg = d3.select('#compare svg').datum(combine_data);
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

    // 混合查找
    $scope.combineSearch = function () {
        $http({
            method: 'POST',
            url: 'http://localhost:8080/movie/combine',
            params:{
                'year': $('#year').val() === '' ? -1 : $('#year').val(),
                'month': $('#month').val() === '' ? -1 : $('#month').val(),
                'day': $('#day').val() === '' ? -1 : $('#day').val(),
                'season': $('#season').val() === '' ? -1 : $('#season').val(),
                'weekday': $('#week').val() === '' ? -1 : $('#week').val(),
                'director': $('#director-name').val(),
                'actor': $('#actor-name').val(),
                'name': $('#movie-name').val(),
                'type': $('#category-name').val()
            }
        }).then( res=>{
            console.log(res.data);
            $scope.movies = res.data.movie;
            var result = {
                'mysqlTime': res.data.mysqlTime,
                'hiveTime' : res.data.hiveTime,
                'date': $('#year').val() + '/' + $('#month').val() + '/' + $('#day').val(),
                'count': res.data.movie.length
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

}]);