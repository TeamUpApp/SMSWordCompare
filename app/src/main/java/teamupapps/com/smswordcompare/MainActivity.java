package teamupapps.com.smswordcompare;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        int friendCount;
        int userCount;

        Thread user;
        Thread friend;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

           final ListView listView = (ListView) rootView.findViewById(R.id.listView);

            final Map<String, String> countMap;
             countMap = Utils.getContactList(getActivity());
            final String names[];

            Set<String> keys = countMap.keySet();

            names = keys.toArray(new String[0]);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, names);

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    int itemPosition     = position;
                    String  itemValue    = (String) listView.getItemAtPosition(position);

                    DatePicker datePicker = (DatePicker) getActivity().findViewById(R.id.datePicker1);
                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth() + 1;
                    int year = datePicker.getYear();

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, datePicker.getMonth(), day);

                    Date dateNow = calendar.getTime();
                    SimpleDateFormat dateformatJava = new SimpleDateFormat("dd-MMM-yyyy");
                    String date_to_string = dateformatJava.format(dateNow);

                    TextView heading = (TextView) getActivity().findViewById(R.id.tBox);

                String number = Utils.getContactNumber(names[position],countMap);

                    user = Utils.sortUserWords(getActivity(), number);
                    friend = Utils.sortFriendsWords(getActivity(),number);


                   heading.setText("Them:  "+friend.getWordCountfromDate(date_to_string)+"  You: "+user.getWordCountfromDate(date_to_string));
                }

            });

            return rootView;
        }
    }
}
