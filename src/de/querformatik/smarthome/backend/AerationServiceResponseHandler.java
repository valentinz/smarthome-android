package de.querformatik.smarthome.backend;

import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class AerationServiceResponseHandler implements
		HttpServiceResponseHandler {
	private static String TAG = "AerationServiceResponseHandler";
	private static int RESULT_AERATION = 1;
	private static int RESULT_ERROR = 4;
	private Handler listener = null;
	
	public Handler getListener() {
		return this.listener;
	}

	public void setListener(Handler listener) {
		this.listener = listener;
	}
		
	@Override
	public void handleException(Exception e) {
		sendResult(RESULT_AERATION, RESULT_ERROR);
	}

	@Override
	public void handleResult(String content) {
		Log.d(TAG, "Get result: " + content);
		try {
			JSONObject info = new JSONObject(content);
			int aerationLevel = info.getInt("aerationLevel");
			sendResult(RESULT_AERATION, aerationLevel);
		} catch (Exception e) {
			e.printStackTrace();
			sendResult(RESULT_AERATION, RESULT_ERROR);
		}
	}
	
	protected void sendResult(int arg1, int arg2) {
		if (listener != null) {
			Message m = new Message();
			m.arg1 = arg1;
			m.arg2 = arg2;
			m.setTarget(getListener());
			m.sendToTarget();
		}
	}
}
