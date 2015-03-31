package com.github.rkmk.binder.dao;

import com.github.rkmk.binder.BaseTest;
import com.github.rkmk.binder.BindObject;
import com.github.rkmk.binder.model.Director;
import com.github.rkmk.binder.model.Movie;
import com.github.rkmk.binder.model.MovieWithOutNameSpace;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.*;
import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class DaoTest extends BaseTest {

    private Dao dao;

    @Before
    public void setUp() throws Exception {
        dao = handle.attach(Dao.class);
    }

    @Test
    public void shouldInsertWithAllTheFields() {
        Movie jeans = Movie.builder().movieId(1).movieName("Jeans").ratings(TEN).build();

        dao.insertMovie(jeans);

        List<Map<String, Object>> movies = select("movie");
        assertThat(movies.size(), is(1));
        assertMovie(movies.get(0), jeans);
    }

    @Test
    public void shouldInsertWithSqlBatch() {
        Movie jeans = Movie.builder().movieId(1).movieName("Jeans").ratings(TEN).build();
        Movie billa = Movie.builder().movieId(2).movieName("Billa").ratings(valueOf(12)).build();
        Movie anegan = Movie.builder().movieId(3).movieName("anegan").ratings(ONE).build();

        dao.insertMovies(asList(jeans,billa,anegan));

        List<Map<String, Object>> movies = handle.select("select * from movie order by movie_id");
        assertThat(movies.size(), is(3));
        assertMovie(movies.get(0), jeans);
        assertMovie(movies.get(1), billa);
        assertMovie(movies.get(2), anegan);
    }

    @Test
    public void shouldBindNestedObjects() {
        Director shankar = Director.builder().directorId(8).directorName("Shankar").build();
        Movie jeans = Movie.builder().movieId(1).movieName("Jeans").ratings(TEN).director(shankar).build();

        dao.insertMovieWithDirector(jeans);

        List<Map<String, Object>> movies = select("movie");
        assertThat(movies.size(), is(1));
        Map<String, Object> actual = movies.get(0);
        assertMovie(actual, jeans);
        assertThat(actual.get("director_id"), is(shankar.getDirectorId()));
        assertThat(actual.get("director_name"), is(shankar.getDirectorName()));
    }

    @Test
    public void shouldBindEvenIfTheNestedObjectIsNull() {
        Movie jeans = Movie.builder().movieId(1).movieName("Jeans").ratings(TEN).director(null).build();

        dao.insertMovieWithDirector(jeans);

        List<Map<String, Object>> movies = select("movie");
        assertThat(movies.size(), is(1));
        Map<String, Object> actual = movies.get(0);
        assertMovie(actual, jeans);
        assertNull(actual.get("director_id"));
        assertNull(actual.get("director_name"));
    }

    @Test
    public void shouldBindNestedObjectsWithoutNameSpace() {
        Director shankar = Director.builder().directorId(8).directorName("Shankar").build();
        MovieWithOutNameSpace jeans = MovieWithOutNameSpace.builder().movieId(1).movieName("Jeans").ratings(TEN).director(shankar).build();

        dao.insertMovieWithDirectorWithoutNameSpace(jeans);

        List<Map<String, Object>> movies = select("movie");
        assertThat(movies.size(), is(1));
        Map<String, Object> actual = movies.get(0);
        assertThat(actual.get("movie_id"), is(jeans.getMovieId()));
        assertThat(actual.get("movie_name"), is(jeans.getMovieName()));
        assertThat(actual.get("ratings"), is(jeans.getRatings()));
        assertThat(actual.get("director_id"), is(shankar.getDirectorId()));
        assertThat(actual.get("director_name"), is(shankar.getDirectorName()));
    }

    private void assertMovie(Map<String, Object> actual, Movie expected) {
        assertThat(actual.get("movie_id"), is(expected.getMovieId()));
        assertThat(actual.get("movie_name"), is(expected.getMovieName()));
        assertThat(actual.get("ratings"), is(expected.getRatings()));
    }


    public interface Dao {
        String insertMovie = "insert into movie(movie_id, movie_name, ratings) values(:movieId, :movieName, :ratings)";

        @SqlUpdate(insertMovie)
        public void insertMovie(@BindObject Movie movie);

        @SqlBatch(insertMovie)
        public void insertMovies(@BindObject List<Movie> movie);

        @SqlUpdate("insert into movie(movie_id, movie_name, ratings, director_id, director_name) " +
                "values(:movieId, :movieName, :ratings, :d.directorId, :d.directorName)")
        public void insertMovieWithDirector(@BindObject Movie movie);

        @SqlUpdate("insert into movie(movie_id, movie_name, ratings, director_id, director_name) " +
                "values(:movieId, :movieName, :ratings, :directorId, :directorName)")
        void insertMovieWithDirectorWithoutNameSpace(@BindObject MovieWithOutNameSpace movie);
    }

}
