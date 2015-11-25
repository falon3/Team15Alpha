package com.skilltradiez.skilltraderz;
/*
 *    Team15Alpha
 *    AppName: SkillTradiez (Subject to change)
 *    Copyright (C) 2015  Stephen Andersen, Falon Scheers, Elyse Hill, Noah Weninger, Cole Evans
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class GeneralMenuActivity extends ActionBarActivity {

    private Context generalContext = this;

    public MasterController masterController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_ham, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.All_Skillz:
                intent = new Intent(generalContext, SearchScreenActivity.class);
                intent.putExtra("All_search", 0);
                startActivity(intent);
                return true;
            case R.id.All_Users:
                intent = new Intent(generalContext, SearchScreenActivity.class);
                intent.putExtra("All_search", 1);
                startActivity(intent);
                return true;
            case R.id.My_Friends:
                return true;
            case R.id.My_Inventory:
                intent = new Intent(generalContext, ProfileActivity.class);
                intent.putExtra("user_name_for_profile", masterController.getCurrentUserUsername());
                startActivity(intent);
                return true;
            case R.id.Create_Skill:
                intent = new Intent(generalContext, EditSkillActivity.class);
                startActivity(intent);
                return true;
        }
        return true;
    }

    public MasterController getMasterController() {
        return masterController;
    }


}
