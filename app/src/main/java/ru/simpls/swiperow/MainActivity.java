package ru.simpls.swiperow;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();
        ArrayList<String> children1 = new ArrayList<String>();
        children1.add("Template 1");
        children1.add("Template 2");
        children1.add("Template 3");
        children1.add("Template 4");
        children1.add("Template 5");
        groups.add(children1);
        TemplatesListAdapter templatesListAdapter = new TemplatesListAdapter(this, groups);
        final ExpandableListView expandableListView = (ExpandableListView)findViewById(R.id.paymentTemplatesList);
        expandableListView.setAdapter(templatesListAdapter);
        expandableListView.setChildDivider(getResources().getDrawable(R.color.bankproducts_divider));
        expandableListView.setDividerHeight(1);
        expandableListView.expandGroup(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
