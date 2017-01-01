package datawarehouse.service;

import datawarehouse.model.Movie;
import datawarehouse.util.DataConnection;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hongjiayong on 2017/1/1.
 */
@Service
public class MovieServiceHiveImpl implements MovieService {
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
        ResultSet resultSet = statement.executeQuery();

        int count = resultSet.getInt(1);
        connection.close();
        return count;
    }

    @Override
    public int countMovieByTime(int year, int month) throws SQLException {
        Connection connection = DataConnection.getConnection("hive");
        String str = "select count(*) from movie, time " +
                "where time.year=? and time.month=? and time.id = movie.time_id";
        PreparedStatement statement = connection.prepareStatement(str);

        statement.setInt(1, year);
        statement.setInt(2, month);
        ResultSet resultSet = statement.executeQuery();

        int count = resultSet.getInt(1);
        connection.close();
        return count;
    }

    @Override
    public int countMovieByTime(int year, List<Integer> months) throws SQLException {
        Integer min = months.get(0);
        Integer max = months.get(1);
        Connection connection = DataConnection.getConnection("hive");
        String str = "select count(*) from movie, time " +
                "where time.year=? and time.month>? and this.month<? and time.id = movie.time_id";
        PreparedStatement statement = connection.prepareStatement(str);

        statement.setInt(1, year);
        statement.setInt(2, min);
        statement.setInt(3, max);
        ResultSet resultSet = statement.executeQuery();

        int count = resultSet.getInt(1);
        connection.close();
        return count;
    }

    @Override
    public int countMovieByTime(int year, int month, int day) throws SQLException {
        Connection connection = DataConnection.getConnection("hive");
        String str = "select count(*) from movie, time " +
                "where time.year=? and time.month=? and this.day=? and time.id = movie.time_id";
        PreparedStatement statement = connection.prepareStatement(str);

        statement.setInt(1, year);
        statement.setInt(2, month);
        statement.setInt(3, day);
        ResultSet resultSet = statement.executeQuery();

        int count = resultSet.getInt(1);
        connection.close();
        return count;
    }

    @Override
    public List<Movie> findMovieByName(String movieName) throws SQLException {
        Connection connection = DataConnection.getConnection("hive");
        String str = "select title, score, runtime from movie where movie.title like ?";
        PreparedStatement statement = connection.prepareStatement(str);

        statement.setString(1, "%"+movieName+"%");
        ResultSet resultSet = statement.executeQuery();

        List<Movie> movieList = new ArrayList<>();

        while (resultSet.next()) {
            String title = resultSet.getString(1);
            BigDecimal score = resultSet.getBigDecimal(2);
            BigDecimal runTime = resultSet.getBigDecimal(3);

            Movie movie = new Movie();
            movieList.add(movie);
        }
        return movieList;
    }

    @Override
    public List<Movie> findMovieByDirectorName(String directorName) throws SQLException {
        Connection connection = DataConnection.getConnection("hive");
        String str = "select movie.title, movie.score from directors join movie on directors.id = movie.director_id " +
                "where movie.title=?";
        PreparedStatement statement = connection.prepareStatement(str);

        statement.setString(1, directorName);
        ResultSet resultSet = statement.executeQuery();

        List<Movie> movieList = new ArrayList<>();

        while (resultSet.next()) {
            String title = resultSet.getString(1);
            BigDecimal score = resultSet.getBigDecimal(2);
//            BigDecimal runTime = resultSet.getBigDecimal(3);

            Movie movie = new Movie();
            movieList.add(movie);
        }
        return movieList;
    }

    @Override
    public List<Map> findByActorName(String actorName) throws SQLException {
        return null;
    }

    @Override
    public List<Map> findByTypeName(String typeName) throws SQLException {
        return null;
    }
}

