package com.inec.android.inec;

import com.inec.android.inec.model.User;
import com.inec.android.inec.util.ProfileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

//RobolectricTestRunner simula condições reais do dispositivo
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class JsonParseTest {
    @Test
    public void correctJSONTest() throws Exception {
        String url = "https://api.github.com/users/torvalds";
        User response;
        response = ProfileUtils.parseJsonUser(url);
        assertEquals(response.getUser_login(), "torvalds");
    }

    @Test
    public void  notExistUserJSONTest() throws Exception {
        String url = "https://api.github.com/users/sjodisajodijasodjaod";
        User response;
        response = ProfileUtils.parseJsonUser(url);
        assertEquals(response, null);
    }

    @Test
    public void nullJSONTest() throws Exception {
        String url = null;
        User response;
        response = ProfileUtils.parseJsonUser(url);
        assertEquals(response, null);
    }
}