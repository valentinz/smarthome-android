package de.querformatik.smarthome.gui;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class AerationSeekBarChangeListener implements OnSeekBarChangeListener {
	public TextView getAerationStatus() {
		return aerationStatus;
	}

	public void setAerationStatus(TextView aerationStatus) {
		this.aerationStatus = aerationStatus;
	}

	private TextView aerationStatus;
	
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromUser) {
		progress = (int)Math.round(((float)progress)/2);
		seekBar.setProgress(progress*2);
		aerationStatus.setText(progress + "");

	}
}
