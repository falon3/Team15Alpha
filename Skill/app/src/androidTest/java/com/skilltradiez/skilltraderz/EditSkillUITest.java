package com.skilltradiez.skilltraderz;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by nweninge on 11/21/15.
 */
public class EditSkillUITest  extends ActivityInstrumentationTestCase2 {
    public EditSkillUITest() {
        super(EditSkillActivity.class);
    }

    public void testStart() throws Exception {
         EditSkillActivity activity = (EditSkillActivity)getActivity();
    }

    public void testCreateSkill() throws Exception {
        EditSkillActivity activity = (EditSkillActivity)getActivity();
        final EditText nameField = activity.getSkillName();
        final EditText descField = activity.getSkillDescription();
        final EditText categoryField = activity.getSkillCategory();
        final CheckBox visibilityField = activity.getVisible();
        final Button addButton = activity.getAddSkillToDB();

        activity.getMasterController().initDB();
        activity.getMasterController().crazyDatabaseDeletion();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                nameField.setText("Skill name");
                descField.setText("Skill description");
                categoryField.setText("Skill category");
                visibilityField.setChecked(true);
            }
        });
        getInstrumentation().waitForIdleSync();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        Skill skill = (Skill) activity.getMasterController().getAllSkillz().toArray()[0];
        assertEquals("Skill name", skill.getName());
        assertEquals("Skill description", skill.getDescription());
        assertEquals("Skill category", skill.getCategory());
        assertEquals(true, skill.isVisible());
    }
}
