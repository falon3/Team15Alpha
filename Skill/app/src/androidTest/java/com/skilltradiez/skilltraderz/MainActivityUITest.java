package com.skilltradiez.skilltraderz;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Cole on 2015-11-17.
 */
public class MainActivityUITest extends ActivityInstrumentationTestCase2 {

    private Activity mMainActivity;
    private EditText mTopEditText;
    private EditText mBottomEditText;
    private Button onlyButtonHere;

    //Initial constructor
    //Obtains all user interface elements and assigns them to a variable that I will manipulate
    public MainActivityUITest(Class activityClass) {
        super(activityClass);
        setActivityInitialTouchMode(true);
        mMainActivity = getActivity(); //Diffferent from tutorial. Needed to specify activity
        mTopEditText = (EditText) mMainActivity.findViewById(R.id.makeUserName);
        mBottomEditText = (EditText) mMainActivity.findViewById(R.id.emailField);
        onlyButtonHere = (Button) mMainActivity.findViewById(R.id.beginApp);
    }


    //Three tests below testing layout position of each UI element
    //Are they where we expect them to be or not?
    public void testTopEditTextLayout(){
        final View decorView = mMainActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, mTopEditText);

        final ViewGroup.LayoutParams layoutParamsTET = mTopEditText.getLayoutParams();
        assertNotNull(layoutParamsTET); //This value better not be null.
        assertEquals(layoutParamsTET.width, WindowManager.LayoutParams.WRAP_CONTENT);
        assertEquals(layoutParamsTET.height, WindowManager.LayoutParams.WRAP_CONTENT);

        //Ensure it is visible
        assertTrue(View.VISIBLE == mTopEditText.getVisibility());

    }

    public void testBottomEditTextLayout(){
        final View decorView = mMainActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, mBottomEditText);

        final ViewGroup.LayoutParams layoutParamsBET = mBottomEditText.getLayoutParams();
        assertNotNull(layoutParamsBET); //This value better not be null.
        assertEquals(layoutParamsBET.width, WindowManager.LayoutParams.WRAP_CONTENT);
        assertEquals(layoutParamsBET.height, WindowManager.LayoutParams.WRAP_CONTENT);

        //Ensure it is visible
        assertTrue(View.VISIBLE == mBottomEditText.getVisibility());

    }

    public void testButtonLayout(){
        final View decorView = mMainActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, onlyButtonHere);

        final ViewGroup.LayoutParams layoutParamsBUT = onlyButtonHere.getLayoutParams();
        assertNotNull(layoutParamsBUT); //This value better not be null.
        assertEquals(layoutParamsBUT.width, WindowManager.LayoutParams.WRAP_CONTENT);
        assertEquals(layoutParamsBUT.height, WindowManager.LayoutParams.WRAP_CONTENT);

        //Ensure it is visible
        assertTrue(View.VISIBLE == onlyButtonHere.getVisibility());
    }


    //The next function is going to be dealing with more functional details of the buttons
    public void testOnlyButton(){

    }



}
