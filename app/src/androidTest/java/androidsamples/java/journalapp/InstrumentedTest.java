package androidsamples.java.journalapp;

import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class InstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @BeforeClass
    public static void enableAccessibilityChecks() {
        AccessibilityChecks.enable();
        AccessibilityChecks.enable().setRunChecksFromRootView(true);
    }

    @Test
    public void goToInfo() {
        onView(withId(R.id.menu_info));
    }

    @Test
    public void goBackToListView() {
        onView(withId(R.id.btn_go_back));
    }

    @Test
    public void goToDetails() {
       onView(withId(R.id.btn_add_entry));
    }
}