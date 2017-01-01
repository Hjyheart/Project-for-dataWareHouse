package datawarehouse.controller;

import datawarehouse.model.Movie;
import datawarehouse.service.MovieService;
import datawarehouse.util.QueryTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hongjiayong on 2017/1/1.
 */
@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    @Qualifier("movieServiceMysqlImpl")
    MovieService mysqlService;

    @Autowired
    @Qualifier("movieServiceHiveImpl")
    MovieService hiveService;

    @ResponseBody
    @RequestMapping("/findByYear")
    public Map findByYear(@RequestParam("year") int year) throws SQLException {

        QueryTimer timer = new QueryTimer();
        timer.start();
        int count = mysqlService.countMovieByTime(year);
        timer.end();

        Map map = new HashMap();
        map.put("count", count);
        map.put("mysqlTime", timer.getRunTime());
        ArrayList<Integer> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++){
            months.add(mysqlService.countMovieByTime(year, i));
        }
        map.put("months", months);
        return map;
    }

    @ResponseBody
    @RequestMapping("/findByMonth")
    public Map findByMonth(@RequestParam("year") int year,
                           @RequestParam("month") int month) throws SQLException {
        QueryTimer timer = new QueryTimer();
        timer.start();
        int count = mysqlService.countMovieByTime(year, month);
        timer.end();

        Map map = new HashMap();
        map.put("count", count);
        map.put("mysqlTime", timer.getRunTime());

        return map;
    }

    @ResponseBody
    @RequestMapping("/findByDay")
    public Map findByDay(@RequestParam("year") int year,
                         @RequestParam("month") int month,
                         @RequestParam("day") int day) throws SQLException {
        QueryTimer timer = new QueryTimer();
        timer.start();
        int count = mysqlService.countMovieByTime(year, month, day);
        timer.end();

        Map map = new HashMap();
        map.put("count", count);
        map.put("mysqlTime", timer.getRunTime());

        return map;
    }

    @ResponseBody
    @RequestMapping("/findByName")
    public Map findByName(@RequestParam("name") String name) throws SQLException {
        QueryTimer timer = new QueryTimer();
        timer.start();
        List<Movie> movies = mysqlService.findMovieByName(name);
        timer.end();

        Map map = new HashMap();

        List<Map> moviemap = new ArrayList();
        for (Movie movie : movies) {
            Map temp = new HashMap();
            temp.put("title", movie.getTitle());
            moviemap.add(temp);
        }
        map.put("movie", moviemap);
        map.put("mysqlTime", timer.getRunTime());

        return map;
    }

    @ResponseBody
    @RequestMapping("/findByActorName")
    public Map findByActorName(@RequestParam("name") String name) throws SQLException {
        QueryTimer timer = new QueryTimer();
        timer.start();
        List<Map> movies = mysqlService.findByActorName(name);
        timer.end();

        Map map = new HashMap();
        map.put("movie", movies);
        map.put("mysqlTime", timer.getRunTime());
        return map;
    }

    @ResponseBody
    @RequestMapping("/findByDirectorName")
    public Map findByDirectorName(@RequestParam("name") String name) throws SQLException {
        QueryTimer timer = new QueryTimer();
        timer.start();
        List<Movie> movies = mysqlService.findMovieByDirectorName(name);
        timer.end();

        Map map = new HashMap();
        map.put("movie", movies);
        map.put("mysqlTime", timer.getRunTime());
        return map;
    }

    @ResponseBody
    @RequestMapping("/findByTypeName")
    public Map findByTypeName(@RequestParam("name") String name) throws SQLException {
        QueryTimer timer = new QueryTimer();
        timer.start();
        List<Map> movies = mysqlService.findByTypeName(name);
        timer.end();

        Map map = new HashMap();
        map.put("movie", movies);
        map.put("mysqlTime", timer.getRunTime());
        return map;
    }
}

