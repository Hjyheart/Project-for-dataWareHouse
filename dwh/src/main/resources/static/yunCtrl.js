/**
 * Created by hongjiayong on 2017/1/6.
 */
app.controller('yunCtrl', ['$scope', '$http', function ($scope, $http) {
    var yun_data = [
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
    $scope.words =null;


    this.$onInit = function () {

    };

    // 绘制比较图表
    function makeChartTimeCompare(objS, objH) {
        yun_data[0].values.push(objS);
        yun_data[1].values.push(objH);

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
                var svg = d3.select('#compare svg').datum(yun_data);
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


    // 单词云查找
    $scope.yunSearch = function () {
        if ($('#movie-name').val() !== ''){
            $http({
                method: 'POST',
                url: 'http://localhost:8080/movie/movieWord',
                params:{
                    'name': $('#movie-name').val()
                }
            }).then( res=>{
                console.log(res.data);
                $scope.movies = res.data.movie;
                $scope.words = res.data.word;
                window.clearInterval(yunflag);
                mcList = [];
                var result = {
                    'name': $('#movie-name').val(),
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
    };

    // 展示单词云
    $scope.display = function () {
        start();
    }


}]);