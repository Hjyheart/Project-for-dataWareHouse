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
public class MovieServiceHiveImpl implements MovieService {
    ////    @Override
////    public int countMovieByTime(int year) throws SQLException {
////        Connection connection = DataConnection.getConnection("hive");
////        String str = "select count(*) from movie, time where time.year=? and time.id = movie.time_id";
////        PreparedStatement statement = connection.prepareStatement(str);
////
////        statement.setInt(1, year);
////        ResultSet resultSet = statement.executeQuery();
////
////        int count = resultSet.getInt(1);
////        connection.close();
////        return count;
////    }
//    @Override
//    public int countMovieByTime(int year) throws SQLException {
//        Connection connection = DataConnection.getConnection("hive");
//        String str = "select count(*) from movies where movies.year=?";
//        PreparedStatement statement = connection.prepareStatement(str);
//
//        statement.setInt(1, year);
//        ResultSet resultSet = statement.executeQuery();
//
//        int count = resultSet.getInt(1);
//        connection.close();
//        return count;
//    }
//
//    @Override
//    public int countMovieByTime(int year, int month) throws SQLException {
//        Connection connection = DataConnection.getConnection("hive");
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
//
//    @Override
//    public int countMovieByTime(int year, List<Integer> months) throws SQLException {
//        Integer min = months.get(0);
//        Integer max = months.get(1);
//        Connection connection = DataConnection.getConnection("hive");
//        String str = "select count(*) from movie, time " +
//                "where time.year=? and time.month>? and this.month<? and time.id = movie.time_id";
//        PreparedStatement statement = connection.prepareStatement(str);
//
//        statement.setInt(1, year);
//        statement.setInt(2, min);
//        statement.setInt(3, max);
//        ResultSet resultSet = statement.executeQuery();
//
//        int count = resultSet.getInt(1);
//        connection.close();
//        return count;
//    }
//
//    @Override
//    public int countMovieByTime(int year, int month, int day) throws SQLException {
//        Connection connection = DataConnection.getConnection("hive");
//        String str = "select count(*) from movie, time " +
//                "where time.year=? and time.month=? and this.day=? and time.id = movie.time_id";
//        PreparedStatement statement = connection.prepareStatement(str);
//
//        statement.setInt(1, year);
//        statement.setInt(2, month);
//        statement.setInt(3, day);
//        ResultSet resultSet = statement.executeQuery();
//
//        int count = resultSet.getInt(1);
//        connection.close();
//        return count;
//    }
//
//    @Override
//    public List<Movie> findMovieByName(String movieName) throws SQLException {
//        Connection connection = DataConnection.getConnection("hive");
//        String str = "select title, score, runtime from movie where movie.title like ?";
//        PreparedStatement statement = connection.prepareStatement(str);
//
//        statement.setString(1, "%"+movieName+"%");
//        ResultSet resultSet = statement.executeQuery();
//
//        List<Movie> movieList = new ArrayList<>();
//
//        while (resultSet.next()) {
//            String title = resultSet.getString(1);
//            BigDecimal score = resultSet.getBigDecimal(2);
//            BigDecimal runTime = resultSet.getBigDecimal(3);
//
//            Movie movie = new Movie();
//            movieList.add(movie);
//        }
//        return movieList;
//    }
//
//    @Override
//    public List<Movie> findMovieByDirectorName(String directorName) throws SQLException {
//        Connection connection = DataConnection.getConnection("hive");
//        String str = "select movie.title, movie.score from director join movie on director.id = movie.director_id " +
//                "where movie.title=?";
//        PreparedStatement statement = connection.prepareStatement(str);
//
//        statement.setString(1, directorName);
//        ResultSet resultSet = statement.executeQuery();
//
//        List<Movie> movieList = new ArrayList<>();
//
//        while (resultSet.next()) {
//            String title = resultSet.getString(1);
//            BigDecimal score = resultSet.getBigDecimal(2);
////            BigDecimal runTime = resultSet.getBigDecimal(3);
//
//            Movie movie = new Movie();
//            movieList.add(movie);
//        }
//        return movieList;
//    }
//
//    @Override
//    public List<Map> findByActorName(String actorName) throws SQLException {
//        return null;
//    }
//
//    @Override
//    public List<Map> findByTypeName(String typeName) throws SQLException {
//        return null;
//    }
//
//    @Override
//    public List<Map> countByType() throws SQLException {
//        return null;
//    }
//
//    @Override
//    public int countByTypeName(String name) throws SQLException {
//        return 0;
//    }

    //    @Override
//    public int countMovieByTime(int year) throws SQLException {
//        Connection connection = DataConnection.getConnection("hive");
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
        Connection connection = DataConnection.getConnection("hive");
        String str = "select count(*) from movies where movies.year=?";
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
//        Connection connection = DataConnection.getConnection("hive");
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
        Connection connection = DataConnection.getConnection("hive");
        String str = "select count(*) from movies where movies.year=? and movies.month=?";
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
        Connection connection = DataConnection.getConnection("hive");
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
        Connection connection = DataConnection.getConnection("hive");
        String str = "select count(*) from movies " +
                "where movies.year=? and movies.month=? and movies.day=?";
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
        Connection connection = DataConnection.getConnection("hive");
//        String str = "select title, score, runtime from movies where movie.title like ?";
        String str = "select title from movies where movies.title like ?";
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
        Connection connection = DataConnection.getConnection("hive");
        String str = "select movie.title, movie.score from director join movie on director.id = movie.director_id " +
                "where movie.title=?";
        PreparedStatement statement = connection.prepareStatement(str);

        statement.setString(1, directorName);
        ResultSet resultSet = statement.executeQuery();

        List<Map> movieList = new ArrayList<>();

        while (resultSet.next()) {
            String title = resultSet.getString(1);
//            BigDecimal score = resultSet.getBigDecimal(2);
//            BigDecimal runTime = resultSet.getBigDecimal(3);

            Map movie = new HashMap();
            movieList.add(movie);
        }
        connection.close();
        return movieList;
    }

    @Override
    public List<Map> findByActorName(String actorName) throws SQLException {
        Connection connection = DataConnection.getConnection("hive");
        String str = "select movies.title, actors.name from movies, actors, movie_actor " +
                "where actors.name LIKE ? and actors.id=movie_actor.actor_id and movie_actor.movie_id=movies.id";
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
        Connection connection = DataConnection.getConnection("hive");
        String str = "select count(*) from movie, type, movie_type " +
                "where movie_type.typeid=type.id and type.name=? and movie.id=movie_type.movie_id";
        PreparedStatement statement = connection.prepareStatement(str);

        statement.setString(1, typeName);
        ResultSet resultSet = statement.executeQuery();

        List<Map> movies = new ArrayList<>();
        while (resultSet.next()) {
            String title = resultSet.getString(1);

            Map map = new HashMap();
            map.put("title", title);
            movies.add(map);
        }
        connection.close();
        return movies;
    }

    @Override
    public Map countByType(String name) throws SQLException  {
        Connection connection = DataConnection.getConnection("hive");
        String str = "select count(*) from movie_type, type " +
                "where type.id=movie_type.type_id and type.name=? or";
        PreparedStatement statement = connection.prepareStatement(str);
//
//        statement.setString(1, typeName);
//        ResultSet resultSet = statement.executeQuery();
//
//        connection.close();
//        return movies;
        Map map = new HashMap();
        return map;
    }

    @Override
    public int countByTypeName(String name) throws SQLException {
        Connection connection = DataConnection.getConnection("hive");
        String str = "select count(*) from movie_type join type on type.id=movie_type.typeid where type.name=?";
        PreparedStatement statement = connection.prepareStatement(str);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();

        resultSet.next();
        int count = resultSet.getInt(1);
        connection.close();
        return count;
    }

    @Override
    public List<Map> findByMovieInfo(Integer year, Integer month, Integer season, Integer day, String actor, String director, String type, String name) throws SQLException {
        return null;
    }

    @Override
    public List<Map> findMovieWord(String name) {
        return null;
    }
}

