package datawarehouse.service;

import datawarehouse.model.Movie;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by hongjiayong on 2017/1/1.
 */
public interface MovieService {
    int countMovieByTime(int year) throws SQLException;

    int countMovieByTime(int year, int month) throws SQLException;

    int countMovieByTime(int year, List<Integer> months) throws SQLException;

    int countMovieByTime(int year, int month, int day) throws SQLException;

    List<Movie> findMovieByName(String movieName) throws SQLException;

    List<Movie> findMovieByDirectorName(String directorName) throws SQLException;

    List<Map> findByActorName(String actorName) throws SQLException;

    List<Map> findByTypeName(String typeName) throws SQLException;

    int countByTypeName(String name) throws SQLException;
}
