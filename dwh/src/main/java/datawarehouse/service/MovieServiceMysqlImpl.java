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
        Connection connection = DataConnection.getConnection("mysql");
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
    public List<Movie> findMovieByDirectorName(String directorName) throws SQLException {
        Connection connection = DataConnection.getConnection("mysql");
        String str = "select movies.title from directors join movies on directors.id = movies.director_id " +
                "where movies.title=?";
        PreparedStatement statement = connection.prepareStatement(str);

        statement.setString(1, directorName);
        ResultSet resultSet = statement.executeQuery();

        List<Movie> movieList = new ArrayList<>();

        while (resultSet.next()) {
            String title = resultSet.getString(1);
//            BigDecimal score = resultSet.getBigDecimal(2);
//            BigDecimal runTime = resultSet.getBigDecimal(3);

            Movie movie = new Movie();
            movieList.add(movie);
        }
        connection.close();
        return movieList;
    }

    @Override
    public List<Map> findByActorName(String actorName) throws SQLException {
        Connection connection = DataConnection.getConnection("mysql");
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
        Connection connection = DataConnection.getConnection("mysql");
        String str = "select movies.title from movies where wayfinding_1=?";
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
    public int countByTypeName(String name) throws SQLException {
        Connection connection = DataConnection.getConnection("mysql");
        String str = "select count(*) from movie_type join type on type.id=movie_type.typeid where type.name=?";
        PreparedStatement statement = connection.prepareStatement(str);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();

        resultSet.next();
        int count = resultSet.getInt(1);
        connection.close();
        return count;
    }
}

