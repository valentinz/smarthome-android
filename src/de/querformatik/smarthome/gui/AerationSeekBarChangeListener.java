package de.querformatik.smarthome.gui;

import de.querformatik.smarthome.R;
import de.querformatik.smarthome.backend.AerationService;
import android.content.res.Resources;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class AerationSeekBarChangeListener implements OnSeekBarChangeListener {
	private SeekBar seekBar;
	private TextView textView;
	private Resources resources;
	private AerationService aerationService;
	
	public SeekBar getSeekBar() {
		return seekBar;
	}

	public void setSeekBar(SeekBar seekBar) {
		this.seekBar = seekBar;
	}

	public TextView getTextView() {
		return textView;
	}

	public void setTextView(TextView textView) {
		this.textView = textView;
	}
	
	public Resources getResources() {
		return resources;
	}
	
	public void setResources(Resources resources) {
		this.resources = resources;
	}
	
	public AerationService getAerationService() {
		return aerationService;
	}
	
	public void setAerationService(AerationService aerationService) {
		this.aerationService = aerationService;
	}
	
	public void setStatus(int status) {
		getSeekBar().setProgress(status*2);
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
		
		getTextView().setText(statusText);
	}
	
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		this.aerationService.setCurrent(seekBar.getProgress()/2);		
	}
	
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromUser) {
		this.setStatus((int)Math.round(((float)progress)/2));
	}
}
