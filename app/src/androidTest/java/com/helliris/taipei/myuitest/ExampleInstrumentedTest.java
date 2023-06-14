package com.helliris.taipei.myuitest;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;

import org.junit.Test;


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

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleInstrumentedTest {

    private final String mockedResponse = "I am mocked response";

//    @Rule
//    public ActivityTestRule activityTestRule = new ActivityTestRule(MainActivity.class) {
//        @Override
//        public Activity launchActivity(@Nullable Intent startIntent) {
//
//            // 呼叫 MockWebServer 的實體並預先給一個假的response
//            MockResponse mockResponse = new MockResponse();
//
//            mockResponse.setResponseCode(200)
//                    .addHeader("Content-Type", "application/json;charset=utf-8")
//                    .addHeader("Cache-Control", "no-cache")
//                    .setBody(mockedResponse);
//
//
//            final MockWebServer server = new MockWebServer();
//            server.enqueue(mockResponse);
//
//            // mocked server 的 url
//            final  String url = server.url("").toString();
//
//            // 當程式呼叫 Constant.url 把它換成 MockWebServer 的 URL
//            try (MockedStatic<ServerHelper> ms = Mockito.mockStatic(ServerHelper.class)) {
//
//                when(ServerHelper.URL).thenReturn(url);
//
//            }
//
//            return super.launchActivity(startIntent);
//        }
//    };
//
//
//    @Test
//    public void testMockServer() {
//        // MainActivity 被 launch 後跟 server 連線時被導到 MockWebServer 我們預設的 response
//        // "I am mocked response"
//        // 因此 textView 會顯示到我們預設從 MockWebServer 來的 I am mocked response，所以測試通過
//        onView(withId(R.id.textView))
//                .check(matches(withText(mockedResponse)));
//    }
//
//
//    @Before
//    public void setUp()  {
//
//        MockitoAnnotations.openMocks(this);
//
//    }
//
//    @Test
//    public void useAppContext() {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        assertEquals("com.helliris.taipei.myuitest", appContext.getPackageName());
//    }

    @Test
    public void testDisplayUser() {

        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);

        Intents.init();

        // 搜尋 button，並執行點擊，進入到 List activity
        onView(withId(R.id.button)).perform(click());

        // 檢查 ListActivity 是否被開啟
        intended(hasComponent(ListActivity.class.getName()));

        // 搜尋 recycler view
        onView(withId(R.id.recyclerView))
                // 檢查 view 是否顯示
                .check(matches(isDisplayed()))
                // 用來執行onView元件的使用者動作: 點擊第0個 item 後進入到 detail activity
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // 檢查 DetailActivity 是否被開啟
        intended(hasComponent(DetailActivity.class.getName()));

        // 找到顯示"User001"的元件，並檢查是否顯示
        onView(withText("User001")).check(matches(isDisplayed()));
        onView(withText("level is 51")).check(matches(isDisplayed()));

        onView(withHint("nickname")).perform(typeText("Iris"));
        onView(withText("Click")).perform(click());
        onView(withText("Iris is level 51")).check(matches(isDisplayed()));

        Intents.release();

        activityScenario.close();
    }

}