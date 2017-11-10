package com.example.td.phonegellary;
import android.app.Instrumentation;
import android.content.Intent;
import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.EspressoKey;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
/**
 * Created by cheyu920926 on 2017/10/2.
 */
@RunWith(AndroidJUnit4.class)
public class MyApplicationEspressoTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);
    /*@Test
    public void ensureTextChangesWork() {
        // Type text and then press the button.
        onView(withId(R.id.edit_message)).perform(typeText("HELLO"), closeSoftKeyboard());
        onView(withId(R.id.send_message)).perform(click());
        // Check that the text was changed.
        onView(withId(R.id.test_message)).check(matches(withText("HELLO")));
        //onView(withContentDescription("Navigate up")).perform(click());
    }*/
    /*public void ensureTakePhotosWork(){
        onView(withId(R.id.btn_Camera)).perform(click());
        android.content.Intent resultData=new Intent();
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        //onView()
    }*/
    @Test
    public void ensureCheckWord(){

        onView(withId(R.id.btn_PhotoM)).perform(click());
        onView(withId(R.id.textConditionDisp)).perform(typeText("2017-10-31"), closeSoftKeyboard());
        onView(withId(R.id.btn_search)).perform(click());
        //onView(withId(R.id.imageView1)).check((R.id.imageView1)!=null)
    }
}
