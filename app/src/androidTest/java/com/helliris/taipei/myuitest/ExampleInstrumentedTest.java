package com.helliris.taipei.myuitest;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
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

        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);

        Intents.init();

        // 搜尋 button，並執行點擊，進入到 List activity
        onView(withId(R.id.button3)).perform(click());

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