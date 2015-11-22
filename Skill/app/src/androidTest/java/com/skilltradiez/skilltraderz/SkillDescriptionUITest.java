package com.skilltradiez.skilltraderz;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.mock.MockContext;
import android.widget.Button;


/**
 * Created by nweninge on 11/21/15.
 */
public class SkillDescriptionUITest extends ActivityInstrumentationTestCase2 {
    public SkillDescriptionUITest() {
        super(SkillDescriptionActivity.class);
    }

    public void testStart() throws Exception {
        SkillDescriptionActivity activity = (SkillDescriptionActivity)getActivity();
        activity.finish();
    }

    public void testAddRemoveSkill() throws Exception {
        MasterController mc = new MasterController();
        mc.initDB();
        mc.crazyDatabaseDeletion();
        mc.createNewUser("User", "Email");
        mc.makeNewSkill("Skill1Test", "testing", "see if can add new skill", true, new NullImage());
        Skill skill = ((Skill)(mc.getAllSkillz().toArray()[0]));
        // see if skill added
        assertTrue(mc.getCurrentUser().getInventory().hasSkill(skill));

        //now remove the skill and test if that works
        MockContext mockContext = new MockContext();
        Intent intenti = new Intent(mockContext, SkillDescriptionActivity.class);
        intenti.putExtra("skill_id", skill.getSkillID());
        setActivityIntent(intenti);
        SkillDescriptionActivity activity = (SkillDescriptionActivity)getActivity();
        final Button addButton = activity.getAddRemoveSkill();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        // check to make sure skill removed
        assertFalse(mc.getCurrentUser().getInventory().hasSkill(skill));

        activity.finish();
    }
}
