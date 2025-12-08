package com.unsia.yukbelajar;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;

import com.unsia.yukbelajar.activity.MainActivity;
import com.unsia.yukbelajar.activity.DetailActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityTest {

    @Before
    public void setup() {
        Intents.init();
        ActivityScenario.launch(MainActivity.class);
    }

    @After
    public void teardown() {
        Intents.release();
    }

    @Test
    public void clickCardBuah_shouldOpenDetailActivity_withExtraBuah() {
        onView(withId(R.id.cardBuah)).perform(click());

        Intents.intended(IntentMatchers.hasComponent(DetailActivity.class.getName()));
        Intents.intended(IntentMatchers.hasExtra("type", "buah"));
    }

    @Test
    public void clickCardHewan_shouldOpenDetailActivity_withExtraHewan() {
        onView(withId(R.id.cardHewan)).perform(click());

        Intents.intended(IntentMatchers.hasComponent(DetailActivity.class.getName()));
        Intents.intended(IntentMatchers.hasExtra("type", "hewan"));
    }

    @Test
    public void clickCardBentuk_shouldOpenDetailActivity_withExtraBentuk() {
        onView(withId(R.id.cardBentuk)).perform(click());

        Intents.intended(IntentMatchers.hasComponent(DetailActivity.class.getName()));
        Intents.intended(IntentMatchers.hasExtra("type", "bentuk"));
    }
}
