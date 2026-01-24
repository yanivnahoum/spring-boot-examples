package com.att.training.springboot.examples;

import com.att.training.springboot.examples.services.actor.Actor;
import com.att.training.springboot.examples.services.actor.ActorService;
import com.att.training.springboot.examples.services.actor.FilmographyEntry;
import com.att.training.springboot.examples.services.actor.FilmographyService;
import com.att.training.springboot.examples.services.movie.Genre;
import com.att.training.springboot.examples.services.movie.GenreService;
import com.att.training.springboot.examples.services.movie.Movie;
import com.att.training.springboot.examples.services.movie.MovieService;
import com.att.training.springboot.examples.services.review.AverageRating;
import com.att.training.springboot.examples.services.review.RatingService;
import com.att.training.springboot.examples.services.review.Review;
import com.att.training.springboot.examples.services.review.ReviewService;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest
@EnableWireMock({
        @ConfigureWireMock(name = HttpInterfacesWireMockTest.ACTOR_SERVICE, baseUrlProperties = "spring.http.serviceclient.actor.base-url"),
        @ConfigureWireMock(name = HttpInterfacesWireMockTest.MOVIE_SERVICE, baseUrlProperties = "spring.http.serviceclient.movie.base-url"),
        @ConfigureWireMock(name = HttpInterfacesWireMockTest.REVIEW_SERVICE, baseUrlProperties = "spring.http.serviceclient.review.base-url")
})
class HttpInterfacesWireMockTest {
    static final String ACTOR_SERVICE = "actor-service";
    static final String MOVIE_SERVICE = "movie-service";
    static final String REVIEW_SERVICE = "review-service";

    @Autowired
    private ActorService actorService;
    @Autowired
    private FilmographyService filmographyService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private RatingService ratingService;

    @InjectWireMock(ACTOR_SERVICE)
    private WireMockServer actorMock;
    @InjectWireMock(MOVIE_SERVICE)
    private WireMockServer movieMock;
    @InjectWireMock(REVIEW_SERVICE)
    private WireMockServer reviewMock;

    @Test
    void testGetMovieById() {
        movieMock.stubFor(get("/movies/1")
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody("""
                                {
                                    "id": 1,
                                    "title": "The Matrix",
                                    "director": "The Wachowskis",
                                    "releaseYear": 1999
                                }
                                """)));

        Movie movie = movieService.getMovieById(1L);

        assertThat(movie).isNotNull();
        assertThat(movie.id()).isEqualTo(1L);
        assertThat(movie.title()).isEqualTo("The Matrix");
        assertThat(movie.director()).isEqualTo("The Wachowskis");
        assertThat(movie.releaseYear()).isEqualTo(1999);
    }

    @Test
    void testGetGenreById() {
        movieMock.stubFor(get("/genres/1")
                .willReturn(okJson("""
                        {"id": 1, "name": "Sci-Fi", "description": "Science Fiction"}
                        """)));

        Genre genre = genreService.getGenreById(1L);

        assertThat(genre.id()).isEqualTo(1L);
        assertThat(genre.name()).isEqualTo("Sci-Fi");
        assertThat(genre.description()).isEqualTo("Science Fiction");
    }

    @Test
    void testGetActorById() {
        actorMock.stubFor(get("/actors/1")
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody("""
                                {
                                    "id": 1,
                                    "name": "Keanu Reeves",
                                    "birthCountry": "Lebanon",
                                    "birthDate": "1964-09-02"
                                }
                                """)));

        Actor actor = actorService.getActorById(1L);

        assertThat(actor).isNotNull();
        assertThat(actor.id()).isEqualTo(1L);
        assertThat(actor.name()).isEqualTo("Keanu Reeves");
        assertThat(actor.birthCountry()).isEqualTo("Lebanon");
        assertThat(actor.birthDate()).isEqualTo(LocalDate.of(1964, 9, 2));
    }

    @Test
    void testGetActorFilmography() {
        actorMock.stubFor(get("/filmography/actor/1")
                .willReturn(okJson("""
                        [
                            {"id": 101, "actorId": 1, "movieId": 1, "role": "Neo", "yearOfRelease": 1999}
                        ]
                        """)));

        List<FilmographyEntry> filmography = filmographyService.getActorFilmography(1L);

        assertThat(filmography).hasSize(1);
        FilmographyEntry entry = filmography.getFirst();
        assertThat(entry.id()).isEqualTo(101L);
        assertThat(entry.actorId()).isEqualTo(1L);
        assertThat(entry.movieId()).isEqualTo(1L);
        assertThat(entry.role()).isEqualTo("Neo");
        assertThat(entry.yearOfRelease()).isEqualTo(1999);
    }

    @Test
    void testGetReviewByMovie() {
        reviewMock.stubFor(get("/reviews/movie/1")
                .willReturn(okJson("""
                        [
                            {"id": 10, "userId": 50, "movieId": 1, "title": "Great!", "comment": "Loved it", "createdDate": "2023-01-01T10:00:00"}
                        ]
                        """)));

        List<Review> reviews = reviewService.getReviewsByMovie(1L);

        assertThat(reviews).isNotEmpty();
        Review review = reviews.getFirst();
        assertThat(review.id()).isEqualTo(10L);
        assertThat(review.userId()).isEqualTo(50L);
        assertThat(review.movieId()).isEqualTo(1L);
        assertThat(review.title()).isEqualTo("Great!");
        assertThat(review.comment()).isEqualTo("Loved it");
        assertThat(review.createdDate()).isEqualTo(LocalDateTime.of(2023, 1, 1, 10, 0, 0));
    }

    @Test
    void testGetAverageRating() {
        reviewMock.stubFor(get("/ratings/movie/1/average")
                .willReturn(okJson("""
                        {"targetId": 1, "targetType": "MOVIE", "averageScore": 9.5, "totalRatings": 100}
                        """)));

        AverageRating rating = ratingService.getMovieAverageRating(1L);

        assertThat(rating.targetId()).isEqualTo(1L);
        assertThat(rating.targetType()).isEqualTo("MOVIE");
        assertThat(rating.averageScore()).isEqualTo(9.5);
        assertThat(rating.totalRatings()).isEqualTo(100);
    }
}
