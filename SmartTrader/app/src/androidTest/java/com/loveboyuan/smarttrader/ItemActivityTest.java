package com.loveboyuan.smarttrader;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

/**
 * Created by Michael on 2015-11-05.
 */

public class ItemActivityTest
        extends ActivityInstrumentationTestCase2<ItemActivity> {

    private ItemActivity mItemActivity;
    private Button mClickAddButton;
    private Button mClickCancelButton;
    // private RadioGroup
    private RadioButton quality_LightlyUsed;
    private RadioButton quality_Old;
    private RadioButton quality_New;
    private RadioButton privacy_Public;
    private RadioButton privacy_Private;
    private EditText description;
    private EditText itemName;
    private Spinner catSpinner;
    private SpinnerAdapter mCategoryData;
    private String mSelection;
    private int mPos;


    public static final int INITIAL_POSITION = 0;
    public static final int TEST_POSITION = 5;


    // private TextView mFirstTestText;

    public ItemActivityTest() {
        super(ItemActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mItemActivity = getActivity();

        setActivityInitialTouchMode(true);

        mItemActivity = getActivity();
        mClickAddButton = (Button) mItemActivity.findViewById(R.id.addButton);
        mClickCancelButton = (Button) mItemActivity.findViewById(R.id.cancelButton);
        catSpinner = (Spinner) mItemActivity.findViewById(R.id.categorySpinner);
        mCategoryData = catSpinner.getAdapter();


        /* mFirstTestText =
                (TextView) mFirstTestActivity
                        .findViewById(R.id.my_first_test_text_view);
                        */
    }

    // Test for the correct layout of the Add button
    @MediumTest
    public void testmClickAddButton_layout() {
        final View decorView = mItemActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, mClickAddButton);

        final ViewGroup.LayoutParams layoutParams =
                mClickAddButton.getLayoutParams();
        assertNotNull(layoutParams);
//        assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    // Test for the correct layout of the Cancel button
    @MediumTest
    public void testmClickCancelButton_layout() {
        final View decorView = mItemActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, mClickCancelButton);

        final ViewGroup.LayoutParams layoutParams =
                mClickCancelButton.getLayoutParams();
        assertNotNull(layoutParams);
//        assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }



    // Test the Spinner
    public void testSpinnerUI() {
        mItemActivity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        catSpinner.requestFocus();
                        catSpinner.setSelection(INITIAL_POSITION);
                    } // end of run() method definition
                } // end of anonymous Runnable object instantiation
        ); // end of invocation of runOnUiThread

        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
        for (int i = 1; i <= TEST_POSITION; i++) {
            this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
        } // end of for loop

        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);


        // We may wish to add a Text View, say SpinnerResult, that displays the User's selection.
        /*
        mPos = catSpinner.getSelectedItemPosition();
        mSelection = (String) catSpinner.getItemAtPosition(mPos);
        TextView resultView =
                (TextView) mItemActivity.findViewById(R.id.SpinnerResult);

        String resultText = (String) resultView.getText();

        assertEquals(resultText, mSelection);
        */

    } // end of testSpinnerUI() method definition
}



/* Since Java doesn't support multiple inheritance, and LaunchItemActivityTest and ItemActivityTest
 * extend different base classes, we have an issue with running functional and activity tests
  * concurrently.
  * */
/*
public class LaunchItemActivityTest extends ActivityUnitTestCase<ItemActivity> {

    private Intent mLaunchIntent;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), ItemActivity.class);
        startActivity(mLaunchIntent, null, null);
        final Button launchNextButton =
                (Button) getActivity()
                        .findViewById(R.id.addButton);
    }

    @MediumTest
    public void testNextActivityWasLaunchedWithIntent() {
        startActivity(mLaunchIntent, null, null);
        final Button launchNextButton =
                (Button) getActivity()
                        .findViewById(R.id.addButton);
        launchNextButton.performClick();

        final Intent launchIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", launchIntent);
        assertTrue(isFinishCalled());

        final String payload =
                launchIntent.getStringExtra(InventoryActivity.EXTRAS_PAYLOAD_KEY);
        assertEquals("Payload is empty", ItemActivity.STRING_PAYLOAD, payload);
    }
}
*/