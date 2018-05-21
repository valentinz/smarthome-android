package de.querformatik.smarthome.gui;

import de.querformatik.smarthome.R;
import de.querformatik.smarthome.backend.AerationService;
import de.querformatik.smarthome.backend.AerationServiceResponseHandler;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class AerationActivity extends Activity {

	protected static final String TAG = "AerationActivity";
	private AerationService aerationService;
	private AerationSeekBarChangeListener aerationListener;
	private SeekBar aeration;
	private TextView aerationStatus;
	private SharedPreferences prefs;

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String message = "";
			int messageDuration = -1;
			switch (msg.arg1) {
			case AerationServiceResponseHandler.RESULT_AERATION:

				if (msg.arg2 >= 0 && msg.arg2 <= 3) {
					message = getResources().getString(
							R.string.text_set_level_success_prefix)
							+ " "
							+ getStatusText(msg.arg2)
							+ " "
							+ getResources().getString(
									R.string.text_set_level_success_suffix);
					messageDuration = Toast.LENGTH_SHORT;
					aerationListener.setStatus(msg.arg2);
					aerationStatus.setText(getStatusText(msg.arg2));
				} else {
					message = getResources().getString(
							R.string.text_set_level_fail);
					messageDuration = Toast.LENGTH_LONG;
					aerationStatus.setText(getResources().getString(
							R.string.text_network_failed));
				}
				break;

			case AerationServiceResponseHandler.RESULT_POWER_CONTROL:
				int checkboxNum = msg.arg2 / 10;
				int checkboxStatus = msg.arg2 % 10;
				Log.d(TAG, "Status " + checkboxNum + " is " + checkboxStatus);

				int checkboxId = -1;
				switch (checkboxNum) {
				case 1:
					checkboxId = R.id.checkBox1;
					break;
				case 2:
					checkboxId = R.id.checkBox2;
					break;
				case 3:
					checkboxId = R.id.checkBox3;
					break;
				}

				if (checkboxId != -1 && checkboxStatus >= 0
						&& checkboxStatus <= 1) {
					CheckBox checkBox = (CheckBox) findViewById(checkboxId);
					checkBox.setChecked(checkboxStatus == 1);
				}
			}

			if (messageDuration >= 0) {
				Toast t = Toast.makeText(getApplicationContext(), message,
						messageDuration);
				t.show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aeration);

		prefs = PreferenceManager.getDefaultSharedPreferences(this);

		aerationService = new AerationService();
		aerationService.setSoapApi(prefs.getString("api_url", getResources()
				.getString(R.string.pref_api_url_default)));
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

		setCheckboxOption(R.id.checkBox1, 1);
		setCheckboxOption(R.id.checkBox2, 2);
		setCheckboxOption(R.id.checkBox3, 3);

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

	private void setCheckboxOption(int checkboxId, final int button) {
		CheckBox checkbox = (CheckBox) findViewById(checkboxId);

		aerationService.getPowerControl(button);

		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Log.d(TAG, "Set button " + button + " to " + isChecked);
				aerationService.setPowerControl(button, isChecked ? 1 : 0);

			}
		});
	}
}
