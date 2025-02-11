package com.solvd.swapi;

import com.solvd.swapi.films.Film;
import com.solvd.swapi.films.GetAllFilmsMethod;
import com.solvd.swapi.films.GetFilmByIdMethod;
import com.zebrunner.carina.core.IAbstractTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class FilmsTest implements IAbstractTest {

    @Test
    public void testGetAllFilms(){
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

    @Test
    public void testGetFilmById(){
        GetFilmByIdMethod api = new GetFilmByIdMethod();
        api.setId("ZmlsbXM6Mw==");
        Response response = api.callAPIExpectSuccess();
        String filmTitle = api.getFilmTitle(response);
        Assert.assertEquals(filmTitle,"Return of the Jedi", "Film name does not match");
    }
}
