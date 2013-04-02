package de.querformatik.smarthome.gui;

import de.querformatik.smarthome.backend.AerationService;
import android.content.res.Resources;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class AerationSeekBarChangeListener implements OnSeekBarChangeListener {
	private SeekBar seekBar;
	private Resources resources;
	private AerationService aerationService;
	
	public SeekBar getSeekBar() {
		return seekBar;
	}

	public void setSeekBar(SeekBar seekBar) {
		this.seekBar = seekBar;
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
