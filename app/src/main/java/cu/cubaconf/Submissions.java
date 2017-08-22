package cu.cubaconf;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import cu.cubaconf.adapter.SubmissionAdapter;
import cu.cubaconf.model.Submission;

public class Submissions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submissions);

        ActionBar bar = getSupportActionBar();
        bar.setTitle("");
        bar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_TITLE);
        bar.setHomeButtonEnabled(true);

        Gson GSON = new Gson();

        ListView submissionsListView = (ListView) findViewById(R.id.submissionsList);
        try {
            Reader reader = new InputStreamReader(getAssets().open("data/submissions.json"));
            Submission[] submissions = GSON.fromJson(reader, Submission[].class);
            submissionsListView.setAdapter(new SubmissionAdapter(this, submissions));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
