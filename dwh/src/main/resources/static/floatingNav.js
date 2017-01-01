/**
 * Created by hongjiayong on 2016/11/22.
 */
$(document)
    .ready(function() {
        // fix menu when passed
        $('.masthead')
            .visibility({
                once: false,
                onBottomPassed: function() {
                    $('.fixed.menu').transition('fade in');
                },
                onBottomPassedReverse: function() {
                    $('.fixed.menu').transition('fade out');
                }
            });

        // show dropdown on hover
        $('#mainDropdown').dropdown({
            on: 'hover'
        });
        $('#floatDropdown').dropdown({
            on: 'hover'
        });

        // for head image
        $('.special.cards .image').dimmer({
            on: 'hover'
        });

        // for change guide
        $('.special.images .image').dimmer({
            on: 'hover'
        });

        // for profile
        $('.accordion').accordion({

        });

        // for search
        $('.ui .search')
            .search({
                type          : 'category',
                minCharacters : 3,
                apiSettings   : {
                    onResponse: function(githubResponse) {
                        var response = {
                                results : {}
                            };
                        var competitions = githubResponse.competitions;
                        var courses = githubResponse.courses;
                        var datasets = githubResponse.datasets;
                        var dataers = githubResponse.dataers;
                        $.each(competitions, (index, item) => {
                            response.results['competitions'] = {
                                name: 'competitions',
                                results: []
                            }
                            response.results['competitions'].results.push({
                                title: item.name,
                                url: `/competition/${item.id}/detail`
                            })
                        });

                        $.each(datasets, (index, item) => {
                            response.results['datasets'] = {
                                name: 'datasets',
                                results: []
                            }
                            response.results['datasets'].results.push({
                                title: item.name,
                                url: `/dataset/${item.name}/detail`
                            })
                        });

                        // translate GitHub API response to work with search
                        $.each(dataers, (index, item) => {
                            response.results['dataers'] = {
                                name: 'dataers',
                                results: []
                            }
                            response.results['dataers'].results.push({
                                title: item.name,
                                url: `/other/${item.name}`
                            })
                        });
                        $.each(courses, (index, item) => {
                            response.results['courses'] = {
                                name: 'courses',
                                results: []
                            }
                            response.results['courses'].results.push({
                                title: item.name,
                                url: `/mooc/${item.id}/${item.name}/detail`
                            })
                        });
                        return response;
                    },
                    url: 'http://localhost:8080/search/{query}'
                }
            })
        ;

    });