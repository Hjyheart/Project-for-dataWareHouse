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

//        QueryTimer hive_timer = new QueryTimer();
//        hive_timer.start();
//        int hive_count = hiveService.countMovieByTime(year);
//        hive_timer.end();

        Map map = new HashMap();
        map.put("count", count);
        map.put("mysqlTime", timer.getRunTime());
//        map.put("hiveTime", hive_timer.getRunTime());
        map.put("hiveTime", 1000);

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

//        QueryTimer hive_timer = new QueryTimer();
//        hive_timer.start();
//        int hive_count = hiveService.countMovieByTime(year, month);
//        hive_timer.end();

        Map map = new HashMap();
        map.put("count", count);
        map.put("mysqlTime", timer.getRunTime());
        map.put("hiveTime", 1000);
//        map.put("hiveTime", hive_timer.getRunTime());

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

//        QueryTimer hive_timer = new QueryTimer();
//        hive_timer.start();
//        int hive_count = hiveService.countMovieByTime(year, month);
//        hive_timer.end();

        Map map = new HashMap();
        map.put("count", count);
        map.put("mysqlTime", timer.getRunTime());
//        map.put("hiveTime", hive_timer.getRunTime());
        map.put("hiveTime", 1000);

        return map;
    }

    @ResponseBody
    @RequestMapping("/findByName")
    public Map findByName(@RequestParam("name") String name) throws SQLException {
        QueryTimer timer = new QueryTimer();
        timer.start();
        List<Movie> movies = mysqlService.findMovieByName(name);
        timer.end();

//        QueryTimer hive_timer = new QueryTimer();
//        hive_timer.start();
//        List<Movie> hive_movies = hiveService.findMovieByName(name);
//        hive_timer.end();

        Map map = new HashMap();

        List<Map> moviemap = new ArrayList();
        for (Movie movie : movies) {
            Map temp = new HashMap();
            temp.put("title", movie.getTitle());
            moviemap.add(temp);
        }
        map.put("movie", moviemap);
        map.put("mysqlTime", timer.getRunTime());
//        map.put("hiveTime", hive_timer.getRunTime());
        map.put("hiveTime", 1000);

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
        map.put("hiveTime", 1000);
        return map;
    }

    @ResponseBody
    @RequestMapping("/findByDirectorName")
    public Map findByDirectorName(@RequestParam("name") String name) throws SQLException {
        QueryTimer timer = new QueryTimer();
        timer.start();
        List<Map> movies = mysqlService.findMovieByDirectorName(name);
        timer.end();

//        QueryTimer hive_timer = new QueryTimer();
//        hive_timer.start();
//        List<Map> hive_movies = hiveService.findByActorName(name);
//        hive_timer.end();

        Map map = new HashMap();
        map.put("movie", movies);
        map.put("mysqlTime", timer.getRunTime());
//        map.put("hiveTime", hive_timer.getRunTime());
        map.put("hiveTime", 1000);

        return map;
    }

    @ResponseBody
    @RequestMapping("/findByTypeName")
    public Map findByTypeName(@RequestParam("name") String name) throws SQLException {
        QueryTimer timer = new QueryTimer();
        timer.start();
        List<Map> movies = mysqlService.findByTypeName(name);
//        int movie_count = mysqlService.countByTypeName(name);
        timer.end();

//        QueryTimer hive_timer = new QueryTimer();
//        hive_timer.start();
//        List<Map> hive_movies = hiveService.findByTypeName(name);
//        int hive_count = hiveService.countByTypeName(name);
//        hive_timer.end();

        Map map = new HashMap();
        map.put("movie", movies);
        map.put("mysqlTime", timer.getRunTime());
//        map.put("hiveTime", hive_timer.getRunTime());
        map.put("hiveTime", 1000);
//        map.put("count", movie_count);

        return map;
    }

    @ResponseBody
    @RequestMapping("/countByTypeName")
    public Map countByTypeName(@RequestParam("name") String name) throws SQLException {
        QueryTimer mysql_timer = new QueryTimer();
        mysql_timer.start();
        int movie_count = mysqlService.countByTypeName(name);
        mysql_timer.end();

        QueryTimer hive_timer = new QueryTimer();
        hive_timer.start();
        int hive_count = hiveService.countByTypeName(name);
        hive_timer.end();

        Map map = new HashMap();
        map.put("count", movie_count);
        map.put("mysqlTime", mysql_timer.getRunTime());
        map.put("hiveTime", hive_timer.getRunTime());
        return map;
    }

    @ResponseBody
    @RequestMapping("/combine")
    public Map combineSearch(@RequestParam(name = "year") int year,
                             @RequestParam(name = "month") int month,
                             @RequestParam(name = "season") int season,
                             @RequestParam(name = "day") int day,
                             @RequestParam(name = "weekday") int weekday,
                             @RequestParam(name = "actor") String actor,
                             @RequestParam(name = "director") String director,
                             @RequestParam(name = "type") String type,
                             @RequestParam(name = "name") String name) throws SQLException {
        QueryTimer mysql_timer = new QueryTimer();
        mysql_timer.start();
        List<Map> movies = mysqlService.findByMovieInfo(year, month, season, day, actor, director, type, name);
        mysql_timer.end();

        Map map = new HashMap();
        map.put("movie", movies);
        map.put("mysqlTime", mysql_timer.getRunTime());
//        map.put("hiveTime", hive_timer.getRunTime());
        map.put("hiveTime", 1000);

        return map;

    }

//    @ResponseBody
//    @RequestMapping("/movieWord")
//    public Map movieWordSearch(@RequestParam(name = "name") String name) {
//
//        QueryTimer mysql_timer = new QueryTimer();
//        mysql_timer.start();
//        mysql_timer.end();
//
//        ArrayList<String> arrayList = new ArrayList<>();
//        ArrayList<Integer> arrayList1 = new ArrayList<>();
//
//        arrayList.add("test3");
//        arrayList.add("test4");
//        arrayList.add("test5");
//        arrayList.add("test6");
//        arrayList.add("test7");
//        arrayList.add("test8");
//        arrayList.add("test9");
//        arrayList.add("test10");
//        arrayList.add("test11");
//        arrayList.add("test12");
//        arrayList.add("test13");
//        arrayList.add("test14");
//
//        Map map = new HashMap();
//        map.put("movie", arrayList1);
//        map.put("mysqlTime", mysql_timer.getRunTime());
////        map.put("hiveTime", hive_timer.getRunTime());
//        map.put("hiveTime", 1000);
//        map.put("words", arrayList);
//
//        return map;
//    }

    @ResponseBody
    @RequestMapping("/movieWord")
    public Map movieWordSearch(@RequestParam(name = "name") String name) throws SQLException {

        QueryTimer mysql_timer = new QueryTimer();
        mysql_timer.start();
        List<Map> movies = mysqlService.findMovieWord(name);
        mysql_timer.end();

        Map map = new HashMap();
        map.put("word", movies);
        map.put("mysqlTime", mysql_timer.getRunTime());
//        map.put("hiveTime", hive_timer.getRunTime());
        map.put("hiveTime", 1000);

        return map;
    }



    @ResponseBody
    @RequestMapping("/testInt")
    public boolean test(@RequestParam Integer season){
        Integer test = season;
        return true;
    }
}

