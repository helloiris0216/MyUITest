package com.helliris.taipei.myuitest;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;

import com.google.gson.Gson;
import com.helliris.taipei.myuitest.view.DetailActivity;
import com.helliris.taipei.myuitest.view.ListActivity;
import com.helliris.taipei.myuitest.view.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleInstrumentedTest {

    /**
     * E2E測試: 測試非同步執行
     */
    @Test
    public void testAsynchronous() {

        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);

        // 註冊 IdlingResource　　　
        IdlingRegistry.getInstance().register(Idling.getResource());

        // assert
        onView(withId(R.id.button2))                 // 點擊進入非同步呼叫
                .perform(click());

        onView(withId(R.id.textView))               // text view
                .check(matches(isDisplayed()));

        // 釋放 IdlingResource
        IdlingRegistry.getInstance().unregister(Idling.getResource());

        activityScenario.close();

    }

    @Test
    public void testDisplayUser() {

        // Arrange
        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);

        // 設定 API 回應的 JSON 字串
        String jsonString =
                "{" +
                        "\"message\":\"成功\"," +
                        "\"data\":[{\"id\":\"001\",\"name\":\"Iris\",\"level\":99},{\"id\":\"002\",\"name\":\"瑞瑞\",\"level\":30},{\"id\":\"003\",\"name\":\"寬寬\",\"level\":10},{\"id\":\"004\",\"name\":\"游游好正\",\"level\":200},{\"id\":\"005\",\"name\":\"搞搞\",\"level\":100}]}";

        String mockId = "001";
        String mockName = "Iris";
        String mockLevel = "99";

        // Act
        Intents.init();

        // 搜尋 button，並執行點擊，進入到 List activity
        onView(withId(R.id.button3)).perform(click());

        // 檢查 ListActivity 是否被開啟
        intended(hasComponent(ListActivity.class.getName()));

        // 註冊 IdlingResource，以等待非同步執行結果 (一進入就撈資料)
        IdlingRegistry.getInstance().register(Idling.getResource());

        // 設定 MockWebServer 的回應
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(new Gson().toJson(jsonString)));

        // Assert
        onView(withId(R.id.recyclerView))
                // 檢查 view 是否顯示
                .check(matches(isDisplayed()))
                // 用來執行onView元件的使用者動作: 點擊第0個 item 後進入到 detail activity
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // 檢查 DetailActivity 是否被開啟
        intended(hasComponent(DetailActivity.class.getName()));

        // 找到 mock response 的元件，並檢查是否顯示
        onView(withText(mockName)).check(matches(isDisplayed()));
        onView(withText("level is " + mockLevel)).check(matches(isDisplayed()));

        onView(withHint("nickname")).perform(typeText("HellIris"));
        onView(withText("Click")).perform(click());
        onView(withText("HellIris is level " + mockLevel)).check(matches(isDisplayed()));

        IdlingRegistry.getInstance().unregister(Idling.getResource());

        Intents.release();

        activityScenario.close();
    }


    private MockWebServer mockWebServer;


    @Before
    public void setUp() {

        mockWebServer = new MockWebServer();

        try {
            mockWebServer.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @After
    public void tearDown() {

        try {
            mockWebServer.shutdown();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * E2E測試: 使用 mock web server & mock api 測試非同步執行
     */
    @Test
    public void testGetJoke() {

        // Arrange
        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);

        // 設定 API 回應的 JSON 字串
        String jsonString =
                "{" +
                    "\"icon_url\":\"https://assets.chucknorris.host/img/avatar/chuck-norris.png\"," +
                    "\"id\":\"CFD6k_h4Tee_fEl_IO263w\"," +
                    "\"url\":\"\"," +
                    "\"value\":\"\"Chuck Norris invented death just so he could kill people\"" +
                "}";

        String mockResponse = "Chuck Norris invented death just so he could kill people";

        // 設定 MockWebServer 的回應
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(new Gson().toJson(jsonString)));

        // Act
        onView(withId(R.id.button2)).perform(click());

        IdlingRegistry.getInstance().register(Idling.getResource());

        // Assert
        onView(withId(R.id.textView))
                .check(matches(withText(mockResponse)));

        IdlingRegistry.getInstance().unregister(Idling.getResource());

        activityScenario.close();

    }

}