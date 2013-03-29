package de.querformatik.smarthome.gui;

import de.querformatik.smarthome.R;
import de.querformatik.smarthome.R.id;
import de.querformatik.smarthome.R.layout;
import de.querformatik.smarthome.R.menu;
import de.querformatik.smarthome.backend.AerationService;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class AerationActivity extends Activity {

	private AerationService aerationService;
	private AerationSeekBarChangeListener aerationListener;
	private SeekBar aeration;
	private TextView aerationStatus;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aeration);
        
        aerationService = new AerationService();

        aerationStatus = (TextView) findViewById(R.id.aerationStatus);

        
        aerationListener = new AerationSeekBarChangeListener();
        aerationListener.setAerationStatus(aerationStatus);
        
        aeration = (SeekBar) findViewById(R.id.aerationSeekbar);
        // TODO: Use AerationListener
        aeration.setProgress(aerationService.getCurrent());
        
        aeration.setOnSeekBarChangeListener(aerationListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_aeration, menu);
        return true;
    }
}
