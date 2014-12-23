package rnr.test.stringresprofile;

import android.os.AsyncTask;
import android.os.Debug;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // new ProfileStringRes().execute();
        new ProfileSQLite().execute();
    }

    private void profileSqlite() {
        new ProfileSQLite().execute();
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

    private class ProfileSQLite extends AsyncTask<Void, Void, Long> {

        @Override
        protected Long doInBackground(Void... params) {

            long getByName = 0;
            SQLApi api = new SQLApi(MainActivity.this);

            // populate table
            for(int i=1; i < 2000; i++) {
                if (i%1000 == 0) Log.d("INSERT >>>>>", "Progress:" + i);
                int id = getResources().getIdentifier("string_" + i, "string", getPackageName());
                String value  = getResources().getString(id);
                api.createRecords("string_" + i, value);
            }

            // populate table
            for(int i=1; i < 2000; i++) {
                if (i%1000 == 0) Log.d("SELECT >>>>>", "Progress:" + i);
                long startTime = System.nanoTime();
                String value  = api.getByName("string_" + i);
                getByName += System.nanoTime() - startTime;
            }
            Log.d(">>>>END SQL<<<<", "getByName:" + getByName);

            return 0L;
        }

        @Override
        protected void onPostExecute(Long result) {
            Toast.makeText(MainActivity.this, "RES DONE", Toast.LENGTH_SHORT).show();
        }
    }

    private class ProfileStringRes extends AsyncTask<Void, Void, Long> {

        @Override
        protected Long doInBackground(Void... params) {
            long getId = 0;
            long getById = 0;
            for(int i=1; i < 20000; i++) {
                if (i%1000 == 0) Log.d("RES >>>>>", "Progress:" + i);
                long startTime = System.nanoTime();
                int id = getResources().getIdentifier("string_" + i, "string", getPackageName());
                getId += System.nanoTime() - startTime;

                startTime = System.nanoTime();
                getResources().getString(id);
                getById += System.nanoTime() - startTime;
            }
            Log.d(">>>>END <<<<", "getId:" + getId + " getById:" + getById);

            return 0L;
        }

        @Override
        protected void onPostExecute(Long result) {
            Toast.makeText(MainActivity.this, "RES DONE", Toast.LENGTH_SHORT).show();
            profileSqlite();
        }
    }
}
