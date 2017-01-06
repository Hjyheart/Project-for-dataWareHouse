package datawarehouse.service;

import datawarehouse.model.Movie;
import datawarehouse.util.DataConnection;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hongjiayong on 2017/1/1.
 */
@Service
public class MovieServiceMysqlImpl implements MovieService {
//    @Override
//    public int countMovieByTime(int year) throws SQLException {
//        Connection connection = DataConnection.getConnection("mysql");
//        String str = "select count(*) from movie, time where time.year=? and time.id = movie.time_id";
//        PreparedStatement statement = connection.prepareStatement(str);
//
//        statement.setInt(1, year);
//        ResultSet resultSet = statement.executeQuery();
//
//        int count = resultSet.getInt(1);
//        connection.close();
//        return count;
//    }

    @Override
    public int countMovieByTime(int year) throws SQLException {
        Connection connection = DataConnection.getConnection("mysql");
        String str = "select count(*) from movie, time where time.year=? and time.id=movie.time_id";
        PreparedStatement statement = connection.prepareStatement(str);

        statement.setInt(1, year);
        statement.executeQuery();
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        int count = resultSet.getInt(1);
        connection.close();
        return count;
    }

//    @Override
//    public int countMovieByTime(int year, int month) throws SQLException {
//        Connection connection = DataConnection.getConnection("mysql");
//        String str = "select count(*) from movie, time " +
//                "where time.year=? and time.month=? and time.id = movie.time_id";
//        PreparedStatement statement = connection.prepareStatement(str);
//
//        statement.setInt(1, year);
//        statement.setInt(2, month);
//        ResultSet resultSet = statement.executeQuery();
//
//        int count = resultSet.getInt(1);
//        connection.close();
//        return count;
//    }

    @Override
    public int countMovieByTime(int year, int month) throws SQLException {
        Connection connection = DataConnection.getConnection("mysql");
        String str = "select count(*) from movie, time where time.year=? and time.month=? and time.id=movie.time_id";
        PreparedStatement statement = connection.prepareStatement(str);

        statement.setInt(1, year);
        statement.setInt(2, month);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        int count = resultSet.getInt(1);
        connection.close();
        return count;
    }

    @Override
    public int countMovieByTime(int year, List<Integer> months) throws SQLException {
        Integer min = months.get(0);
        Integer max = months.get(1);
        Connection connection = DataConnection.getConnection("mysql");
        String str = "select count(*) from movies " +
                "where movies.year=? and movies.month>? and movies.month<?";
        PreparedStatement statement = connection.prepareStatement(str);

        statement.setInt(1, year);
        statement.setInt(2, min);
        statement.setInt(3, max);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        int count = resultSet.getInt(1);
        connection.close();
        return count;
    }

    @Override
    public int countMovieByTime(int year, int month, int day) throws SQLException {
        Connection connection = DataConnection.getConnection("mysql");
        String str = "select count(*) from movie, time " +
                "where time.year=? and time.month=? and time.day=? and time.id=movie.time_id";
        PreparedStatement statement = connection.prepareStatement(str);

        statement.setInt(1, year);
        statement.setInt(2, month);
        statement.setInt(3, day);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        int count = resultSet.getInt(1);
        connection.close();
        return count;
    }

    @Override
    public List<Movie> findMovieByName(String movieName) throws SQLException {
        Connection connection = DataConnection.getConnection("mysql");
//        String str = "select title, score, runtime from movies where movie.title like ?";
        String str = "select title from movie where movie.title like ?";
        PreparedStatement statement = connection.prepareStatement(str);

        statement.setString(1, "%"+movieName+"%");
        ResultSet resultSet = statement.executeQuery();

        List<Movie> movieList = new ArrayList<>();

        while (resultSet.next()) {
            String title = resultSet.getString(1);
//            BigDecimal score = resultSet.getBigDecimal(2);
//            BigDecimal runTime = resultSet.getBigDecimal(3);

            Movie movie = new Movie();
            movie.setTitle(title);
            movieList.add(movie);
        }
        connection.close();
        return movieList;
    }

    @Override
    public List<Map> findMovieByDirectorName(String directorName) throws SQLException {
        Connection connection = DataConnection.getConnection("mysql");
        String str = "select movie.title, movie.score, director.name from director join movie on director.movieid = movie.id " +
                "where director.name LIKE ?";
        PreparedStatement statement = connection.prepareStatement(str);

        statement.setString(1, "%"+directorName+"%");
        ResultSet resultSet = statement.executeQuery();

        List<Map> movieList = new ArrayList<>();

        while (resultSet.next()) {
            String title = resultSet.getString(1);
            float score = resultSet.getFloat(2);
            String dname = resultSet.getString(3);
//            BigDecimal score = resultSet.getBigDecimal(2);
//            BigDecimal runTime = resultSet.getBigDecimal(3);
            Map map = new HashMap();
            map.put("title", title);
            map.put("score", score);
            map.put("director", dname);
            movieList.add(map);
        }
        connection.close();
        return movieList;
    }

    @Override
    public List<Map> findByActorName(String actorName) throws SQLException {
        Connection connection = DataConnection.getConnection("mysql");
        String str = "select movie.title, actor.name from movie, actor, actor_movie " +
                "where actor.name LIKE ? and actor.id=actor_movie.actorid and actor_movie.movieid=movie.id";
        PreparedStatement statement = connection.prepareStatement(str);

        statement.setString(1, "%"+actorName+"%");
        ResultSet resultSet = statement.executeQuery();

        List<Map> movies = new ArrayList<>();
        while (resultSet.next()) {
            String title = resultSet.getString(1);
            String actor = resultSet.getString(2);

            Map map = new HashMap();
            map.put("movie_title", title);
            map.put("actor_name", actor);
            movies.add(map);
        }
        connection.close();
        return movies;
    }

    @Override
    public List<Map> findByTypeName(String typeName) throws SQLException {
        Connection connection = DataConnection.getConnection("mysql");
        String str = "select movie.title, type.type, movie.score from movie, type, movie_type " +
                "where type.type=? and movie_type.typeid=type.id and movie.id=movie_type.movieid";
        PreparedStatement statement = connection.prepareStatement(str);

        statement.setString(1, typeName);
        ResultSet resultSet = statement.executeQuery();

        List<Map> movies = new ArrayList<>();
        while (resultSet.next()) {
            String title = resultSet.getString(1);
            String tname = resultSet.getString(2);
            Float score = resultSet.getFloat(3);

            Map map = new HashMap();
            map.put("title", title);
            map.put("type", tname);
            map.put("score", score);
            movies.add(map);
        }
        connection.close();
        return movies;
    }

    @Override
    public Map countByType(String name) throws SQLException {
        Connection connection = DataConnection.getConnection("mysql");
        String str = "select type.name, count(*) from type join movie_type on movie_type.type=type.id " +
                "where type.type=? group by movie_type.typeid";
        PreparedStatement statement = connection.prepareStatement(str);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        String tname = resultSet.getString(1);
        Float count = resultSet.getFloat(2);
        Map map = new HashMap();
        map.put("name", tname);
        map.put("count", count);
        connection.close();

        return map;
    }

    @Override
    public int countByTypeName(String name) throws SQLException {
        Connection connection = DataConnection.getConnection("mysql");
        String str = "select count(*) from type join movie_type on movie_type.typeid=type.id " +
                "where type.type=? group by movie_type.typeid";
        PreparedStatement statement = connection.prepareStatement(str);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        return count;
    }

    @Override
    public List<Map> findByMovieInfo(Integer year, Integer month, Integer season, Integer day,
                                     String actor, String director, String type, String name) throws SQLException {
        Connection connection = DataConnection.getConnection("mysql");
        Boolean flag = false;
        String sql = "select movie.title, movie.score from movie";
        if (!actor.equals(""))
            sql += ", actor, actor_movie";
        if (!director.equals(""))
            sql += ", director";
        if (!type.equals(""))
            sql += ", type, movie_type";
        if (year != -1 || month != -1 || day != -1) {
            sql += ", time";
        }
        sql += " where";

        if (!name.equals("")) {
            sql += " movie.title=?";
            flag = true;
        }
        if (year != -1 || month != -1 || day != -1) {
            sql += flag ? " and" : "";
            flag = true;
            sql += " time.id = movie.time_id";
        }
        if (year != -1) {
            sql += flag ? " and" : "";
            flag = true;
            sql += " time.year=?";
        }
        if (month != -1) {
            sql += flag ? " and" : "";
            flag = true;
            sql += " time.month=?";
        }
        if (day != -1) {
            sql += flag ? " and" : "";
            flag = true;
            sql += " time.day=?";
        }
        if (!actor.equals("")) {
            sql += flag ? " and" : "";
            flag = true;
            sql += " actor.name=? and actor_movie.movieid=movie.id and actor_movie.actorid=actor.id";
        }
        if (!director.equals("")) {
            sql += flag ? " and" : "";
            flag = true;
            sql += " director.name=? and director.movieid=movie.id";
        }
        if (!type.equals("")) {
            sql += flag ? " and" : "";
            flag = true;
            sql += " type.type=? and type.id=movie_type.typeid and movie.id=movie_type.movieid";
        }

        PreparedStatement statement = connection.prepareStatement(sql);
        int count = 1;
        if (!name.equals(""))
            statement.setString(count++, name);
        if (year != -1)
            statement.setInt(count++, year);
        if (month != -1)
            statement.setInt(count++, month);
        if (day != -1)
            statement.setInt(count++, day);
        if (!actor.equals(""))
            statement.setString(count++, actor);
        if (!director.equals(""))
            statement.setString(count++, director);
        if (!type.equals(""))
            statement.setString(count++, type);

        ResultSet resultSet = statement.executeQuery();

        List<Map> result = new ArrayList<>();
        while (resultSet.next()) {
            String title = resultSet.getString(1);
            Float score = resultSet.getFloat(2);
            Map temp = new HashMap();
            temp.put("title", title);
            temp.put("score", score);
            result.add(temp);
        }

        connection.close();
        return result;
    }

    @Override
    public List<String> findMovieWord(String name) throws SQLException {
        Connection connection = DataConnection.getConnection("mysql");

        String sql = "select allcomments.text from movie join allcomments on allcomments.productId=movie.code where movie.title=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String title = resultSet.getString(1);
        }

        connection.close();
        return null;
    }
}

