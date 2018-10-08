package theoneandonly.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity3 extends AppCompatActivity {

    String[] listItems = {"Child In Seat", "Location", "Check For Temperature"};
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mListView = (ListView) findViewById(R.id.listView);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);

        final Intent force = new Intent(MainActivity3.this, ForceActivity.class);
        final Intent location = new Intent(MainActivity3.this, MapsActivity.class);
        final Intent temp = new Intent(MainActivity3.this, TempActivity.class);
        final Intent main = new Intent(MainActivity3.this, Main4Activity.class);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    startActivity(main);
                } else if (i == 1) {
                    startActivity(force);
                } else if (i == 2) {
                    startActivity(location);
                } else if (i == 3) {
                    startActivity(temp);
                }
            }
        });
    }
}