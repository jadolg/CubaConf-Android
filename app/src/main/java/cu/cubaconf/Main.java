package cu.cubaconf;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class Main extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inicializa los iconos FA
        Iconify.with(new FontAwesomeModule());
        setContentView(R.layout.activity_main);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/bair.ttf");
        TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewTitle.setTypeface(type);
        preferences = getSharedPreferences("CubaConf", MODE_PRIVATE);
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


    public void openURI(String uri) {
        Intent callIntent = new Intent(Intent.ACTION_VIEW);
        callIntent.setData(Uri.parse(uri));
        startActivity(callIntent);
    }

    public void clickRegister(View view) {
        openURI("https://www.xing-events.com/BCPNNIA.html");
    }

    public void clickSendProp(View view) {
        openURI("https://www.papercall.io/cubaconf2017");
    }

    public void clickEmail(View view) {
        openURI("mailto:cubaconf@cubaconf.org");
    }

    public void clickTelegram(View view) {
        openURI("https://t.me/cubaconf");
    }

    public void clickTwitter(View view) {
        openURI("https://twitter.com/cubaconference");
    }

    public void clickFacebook(View view) {
        openURI("https://www.facebook.com/cubaconf/");
    }

    public void clickLocation(View view){
        if (!preferences.getBoolean("cancelled_mm_install", false) && !appInstalledOrNot("com.mapswithme.maps.pro")){
            showInstallAdminAlert();
        } else {
            Intent callIntent = new Intent(Intent.ACTION_VIEW);
            callIntent.setData(Uri.parse("geo:23.139729, -82.35103"));
            startActivity(callIntent);
        }
    }

    private void showInstallAdminAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.install_mapsme_txt));
        builder.setCancelable(true)
                .setPositiveButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                openURI("https://play.google.com/store/apps/details?id=com.mapswithme.maps.pro");
                            }
                        }).setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("cancelled_mm_install", true);
                        editor.commit();

                        Intent callIntent = new Intent(Intent.ACTION_VIEW);
                        callIntent.setData(Uri.parse("geo:23.139729, -82.35103"));
                        startActivity(callIntent);
                    }
                });

        AlertDialog alert = builder.create();
        alert.setTitle(getString(R.string.install_mapsme));
        alert.show();
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }
}