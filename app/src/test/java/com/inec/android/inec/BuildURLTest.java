package com.inec.android.inec;

import com.inec.android.inec.util.NetworkUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.net.URL;
import static org.junit.Assert.assertTrue;

@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class BuildURLTest {
    @Test
    public void buildCorrectUrl() throws Exception {
        String user = "torvalds";
        String url = "https://api.github.com/users/torvalds";
        URL response;
        response = NetworkUtils.buildUrl(user);
        assertTrue(response.toString().equals(url));
    }

    @Test
    public void buildUrlNullUser() throws Exception {
        String user = null;
        String url = "https://api.github.com/users/";
        URL response;
        response = NetworkUtils.buildUrl(user);
        assertTrue(response == null);
    }

    @Test
    public void buildUrlNotExistUser() throws Exception {
        String user = "jsapidjsajdpasjdpsajdp";
        String url = "https://api.github.com/users/jsapidjsajdpasjdpsajdp";
        URL response;
        response = NetworkUtils.buildUrl(user);
        assertTrue(response.toString().equals(url));
    }

    @Test
    public void buildUrlLongUser() throws Exception {
        String user = "jsapidjsajdpasjdpsajdpkpoasjdsaopdjsapdjaspjdpsajdpasjdsapjdsapdjaspodjaspojdaspojdpaosjdpaosjdpoasjdpa";
        String url = "https://api.github.com/users/jsapidjsajdpasjdpsajdpkpoasjdsaopdjsapdjaspjdpsajdpasjdsapjdsapdjaspodjaspojdaspojdpaosjdpaosjdpoasjdpa";
        URL response;
        response = NetworkUtils.buildUrl(user);
        assertTrue(response.toString().equals(url));
    }
}
