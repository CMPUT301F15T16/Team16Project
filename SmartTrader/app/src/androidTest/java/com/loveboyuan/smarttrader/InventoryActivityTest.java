package com.loveboyuan.smarttrader;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by Michael on 2015-11-05.
 */

public class InventoryActivityTest
        extends ActivityInstrumentationTestCase2<InventoryActivity> {

    private InventoryActivity mInventoryActivity;
    private Button mClickAddButton;
    // private TextView mFirstTestText;

    public InventoryActivityTest() {
        super(InventoryActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mInventoryActivity = getActivity();
        mClickAddButton = (Button) mInventoryActivity.findViewById(R.id.addButton);

        /* mFirstTestText =
                (TextView) mFirstTestActivity
                        .findViewById(R.id.my_first_test_text_view);
                        */
    }

    // Test for the correct layout of the Add button
    @MediumTest
    public void testmClickAddButton_layout() {
        final View decorView = mInventoryActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, mClickAddButton);

        final ViewGroup.LayoutParams layoutParams =
                mClickAddButton.getLayoutParams();
        assertNotNull(layoutParams);
//        assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
