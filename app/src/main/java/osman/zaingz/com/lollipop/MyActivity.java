package osman.zaingz.com.lollipop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.melnykov.fab.FloatingActionButton;

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

import osman.zaingz.com.lollipop.model.Player;


public class MyActivity extends Activity {
    ArrayList<Player> playersList = new ArrayList<Player>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my);



// set an exit transition


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);




        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // specify an adapter (see also next example)

        new ApiConnector().execute("http://player-data.herokuapp.com/players.json/");




    }
    class ApiConnector extends AsyncTask<String,Void, String> {
        ProgressDialog pd;

        protected void onPreExecute() {
            // NOTE: You can call UI Element here.
            pd = new ProgressDialog(MyActivity.this);
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
                 Player p = new Player( player.getString("name"), player.getInt("age"),player.getInt("id") );

                 playersList.add(p);
             }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pd.dismiss();

            mAdapter = new MyAdapter(playersList);

            mRecyclerView.setAdapter(mAdapter);
            fab.attachToRecyclerView(mRecyclerView);
            fab.show();


        }
    }

}
