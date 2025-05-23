package com.example.StarGroup.QuizUP;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf; // For hasExtra

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    // Rule to launch MainActivity before each test
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        // Initialize Espresso-Intents before each test
        Intents.init();
    }

    @After
    public void tearDown() {
        // Release Espresso-Intents after each test
        Intents.release();
    }

    @Test
    public void viewsAreDisplayed() {
        onView(withId(R.id.textView)).check(matches(isDisplayed())); // QuizUp Title
        onView(withId(R.id.editName)).check(matches(isDisplayed()));
        onView(withId(R.id.button)).check(matches(withText("Start"))); // Start Button
        onView(withId(R.id.button)).check(matches(isDisplayed()));
        onView(withId(R.id.button2)).check(matches(withText("About"))); // About Button
        onView(withId(R.id.button2)).check(matches(isDisplayed()));
    }

    @Test
    public void startButton_withName_launchesQuestionsActivity_withCorrectIntent() {
        String testName = "Espresso User";

        // Type text into the EditText
        onView(withId(R.id.editName))
                .perform(typeText(testName), closeSoftKeyboard());

        // Click the start button
        onView(withId(R.id.button)).perform(click());

        // Verify that QuestionsActivity is launched
        intended(hasComponent(QuestionsActivity.class.getName()));

        // Verify that the intent has the correct extra
        intended(allOf(
            hasComponent(QuestionsActivity.class.getName()),
            hasExtra("myname", testName)
        ));
    }
    
    @Test
    public void startButton_withoutName_launchesQuestionsActivity_withEmptyNameInIntent() {
        // Click the start button without typing a name
        onView(withId(R.id.button)).perform(click());

        // Verify that QuestionsActivity is launched
        intended(hasComponent(QuestionsActivity.class.getName()));

        // Verify that the intent has an empty string for "myname"
        intended(allOf(
            hasComponent(QuestionsActivity.class.getName()),
            hasExtra("myname", "")
        ));
    }

    @Test
    public void aboutButton_launchesDeveloperActivity() {
        // Click the about button
        onView(withId(R.id.button2)).perform(click());

        // Verify that DeveloperActivity is launched
        intended(hasComponent(DeveloperActivity.class.getName()));
    }
}
