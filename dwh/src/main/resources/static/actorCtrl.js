/**
 * Created by hongjiayong on 2017/1/1.
 */
app.controller('actorCtrl', ['$scope', '$http', function ($scope, $http) {
    var actor_data = [
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
    $scope.actorName = '';

    this.$onInit = function () {

    };

    // 绘制比较图表
    function makeChartTimeCompare(objS, objH) {
        actor_data[0].values.push(objS);
        actor_data[1].values.push(objH);

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
                var svg = d3.select('#compare svg').datum(actor_data);
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

    // 按演员查询
    $scope.actorSearch = function () {
        if ($('#actor-name').val() !== ''){
            $http({
                method: 'POST',
                url: 'http://localhost:8080/movie/findByActorName',
                params:{
                    'name': $('#actor-name').val()
                }
            }).then( res=>{
                console.log(res.data);
                $scope.movies = res.data.movie;
                var result = {
                    'name': $('#actor-name').val(),
                    'count': res.data.movie.length,
                    'mysqlTime': res.data.mysqlTime,
                    'hiveTime' : 800
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
                $scope.actorName = $('#actor-name').val();
            }).catch( err=>{
                console.log(err);
            })
        }
    };
}]);