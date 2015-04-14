package osman.zaingz.com.lollipop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import osman.zaingz.com.lollipop.model.ReadingModel;


public class Reading extends Activity {
    ArrayList<ReadingModel> playersList = new ArrayList<ReadingModel>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reading);



// set an exit transition


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // specify an adapter (see also next example)
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");

        new ApiConnector().execute("https://player-data.herokuapp.com/players/"+id+"/readings.json");




    }
    class ApiConnector extends AsyncTask<String,Void, String> {
        ProgressDialog pd;

        protected void onPreExecute() {
            // NOTE: You can call UI Element here.
            pd = new ProgressDialog(Reading.this);
            pd.setMessage("Loading...");
            pd.show();

        }
        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(params[0]);
            HttpResponse response=null;
            try {
                Log.i("zaingz", "executing response");
                response = client.execute(get);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String json = null;
            try {
                Log.i("zaingz", "got response");

                json = EntityUtils.toString(response.getEntity());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(String s) {

            
            try {
                Log.i("zaingz", "parsing json");
                JSONArray allPlayers = new JSONArray(s);
                for (int i=0; i< allPlayers.length();i++){
                    JSONObject player = allPlayers.getJSONObject(i);
                    ReadingModel p = new ReadingModel ( player.getString("key"), player.getString("value") );

                    playersList.add(p);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pd.dismiss();

            mAdapter = new MyAdapterReading(playersList);

            mRecyclerView.setAdapter(mAdapter);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reading, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_refresh:
                playersList.clear();
                new ApiConnector().execute("https://player-data.herokuapp.com/players/" + id + "/readings.json");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
