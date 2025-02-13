package com.solvd.swapi;

import com.solvd.swapi.models.Film;
import com.solvd.swapi.films.GetAllFilmsMethod;
import com.solvd.swapi.films.GetFilmByIdMethod;
import com.solvd.swapi.films.GetPaginatedFilmsMethod;
import com.zebrunner.carina.api.APIMethodPoller;
import com.zebrunner.carina.core.IAbstractTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FilmsTest implements IAbstractTest {

    @Test
    public void testGetAllFilms() {
        GetAllFilmsMethod api = new GetAllFilmsMethod();
        Response response = api.callAPIExpectSuccess();
        List<Film> allFilms = api.getAllFilms(response);
        SoftAssert sa = new SoftAssert();
        allFilms.forEach(film -> {
            sa.assertNotNull(film.getDirector());
            sa.assertNotNull(film.getTitle());
            sa.assertNotNull(film.getReleaseDate());
        });
        sa.assertAll();
    }

    @DataProvider(name = "getFilmByIdData", parallel = true)
    public Object[][] getFilmByIdData() {
        return new Object[][]{
                {"ZmlsbXM6Mw==", "Return of the Jedi"},
                {"", ""}
        };
    }

    @Test(dataProvider = "getFilmByIdData")
    public void testGetFilmById(String filmId, String filmTitle) {
        GetFilmByIdMethod api = new GetFilmByIdMethod();
        api.setId(filmId);
        Response response = api.callAPIExpectSuccess();
        String title = api.getFilmTitle(response);
        Assert.assertEquals(title, filmTitle, "Film name does not match");
    }

    @DataProvider(name = "paginationData", parallel = true)
    public Object[][] paginationData() {
        return new Object[][]{
                {2, 4}, // test different combinations
                {5, 2}
        };
    }

    @Test(dataProvider = "paginationData")
    public void testFilmPagination(int itemsPerPage, int totalPages) {
        GetPaginatedFilmsMethod api = new GetPaginatedFilmsMethod();
        api.setFirst(itemsPerPage);
        AtomicInteger pageCount = new AtomicInteger(0);
        api.callAPIWithRetry()
                .peek(response -> {
                    pageCount.getAndIncrement();
                    api.setAfter(api.getEndCursor(response));
                })
                .until(response -> !api.hasNextPage(response))
                .withLogStrategy(APIMethodPoller.LogStrategy.ALL)
                .stopAfter(30, ChronoUnit.SECONDS)
                .execute();
        Assert.assertEquals(pageCount.get(), totalPages, "Page count does not match expected");
        api.validateResponseAgainstSchema("api/swapi/films/getPaginatedFilms/rs.schema");
    }
}
