package io.swagger.client.api;

import io.swagger.client.ApiClient;
import io.swagger.client.model.CommonProblem;
import io.swagger.client.model.MapFilters;
import io.swagger.client.model.ViolationProblem;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for MapApi
 */
public class MapApiTest {

    private MapApi api;

    @Before
    public void setup() {
        api = new ApiClient().createService(MapApi.class);
    }

    /**
     * Słownik krajów
     *
     * Status użytkownika: Niezarejestrowany
     */
    @Test
    public void countryCodesTest() {
        // Map<String, String> response = api.countryCodes();

        // TODO: test validations
    }
    /**
     * Pobranie dostęnych filtrów wyszukiwania.
     *
     * Status użytkownika: Niezarejstrowany
     */
    @Test
    public void filtersTest() {
        // MapFilters response = api.filters();

        // TODO: test validations
    }
}
