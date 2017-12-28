package com.inec.android.inec;

import com.inec.android.inec.util.NetworkUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.net.URL;

import static org.junit.Assert.*;

//Esses testes cobrem 2 metodos (getJSONFromAPI e convertInputStreamToString)e 27 linhas de código
public class NetworkConnectionTest {
    @Test
    public void connectCorrectURLTest() throws Exception {
        String url = "https://api.github.com/users/torvalds";
        String response;
        response = NetworkUtils.getJSONFromAPI(url);
        assertTrue(response.length() > 0);
    }

    @Test
    public void connectMalformedURLTest() throws Exception {
        String url = "https://pasdpamdpasmdpas/users/torvalds";
        String response;
        response = NetworkUtils.getJSONFromAPI(url);
        assertTrue(response.isEmpty());
    }

    @Test
    public void connectEmptyURLTest() throws Exception {
        String url = "";
        String response;
        response = NetworkUtils.getJSONFromAPI(url);
        assertTrue(response.isEmpty());
    }

    @Test
    public void connectNullURLTest() throws Exception {
        String response;
        response = NetworkUtils.getJSONFromAPI(null);
        assertTrue(response.isEmpty());
    }
}