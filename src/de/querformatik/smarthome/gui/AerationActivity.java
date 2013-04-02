package de.querformatik.smarthome.gui;

import de.querformatik.smarthome.R;
import de.querformatik.smarthome.backend.AerationService;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class AerationActivity extends Activity {

	private AerationService aerationService;
	private AerationSeekBarChangeListener aerationListener;
	private SeekBar aeration;
	private TextView aerationStatus;
	
	final Handler handler = new Handler() {
	    public void handleMessage(Message msg) {
	    	String message = "";
	    	int messageDuration = 3;
	    	if (msg.arg1 == 1 && msg.arg2 >= 0 && msg.arg2 <= 3) {
		    	message = getResources().getString(R.string.text_set_level_success_prefix) + " " +
		    			getStatusText(msg.arg2) + " " +
		    			getResources().getString(R.string.text_set_level_success_suffix);
		    	aerationListener.setStatus(msg.arg2);
		    	aerationStatus.setText(getStatusText(msg.arg2));
	    	} else if (msg.arg1 == 1) {
	    		message = getResources().getString(R.string.text_set_level_fail);
	    		messageDuration = 10;
	    		aerationStatus.setText(getResources().getString(R.string.text_network_failed));
	    	}
	    	
	    	Toast t = Toast.makeText(getApplicationContext(), message, messageDuration);
	    	t.show();
	    }
    };
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aeration);
        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        
        aerationService = new AerationService();
        aerationService.setSoapApi(prefs.getString("api_url", getResources().getString(R.string.pref_api_url_default)));
        aerationService.setListener(handler);
        
        aerationStatus = (TextView) findViewById(R.id.aerationStatus);

        aeration = (SeekBar) findViewById(R.id.aerationSeekbar);

        
        aerationListener = new AerationSeekBarChangeListener();
        aerationListener.setAerationService(aerationService);
        aerationListener.setSeekBar(aeration);
        aerationListener.setResources(getResources());
        
        // TODO: Use AerationListener
        aerationService.requestCurrent();
        
        aeration.setOnSeekBarChangeListener(aerationListener);
        

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_aeration, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
        case R.id.menu_settings:
        	Intent intent = new Intent(this, SettingsActivity.class);
        	startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
	protected String getStatusText(int status) {
		String statusText = "";
		
		switch (status) {
			case 0:
				statusText = getResources().getString(R.string.text_stop);
				break;
			case 1:
				statusText = getResources().getString(R.string.text_level1);
				break;
			case 2:
				statusText = getResources().getString(R.string.text_level2);
				break;
			case 3:
				statusText = getResources().getString(R.string.text_level3);
				break;
		}
		return statusText;
	}

}
