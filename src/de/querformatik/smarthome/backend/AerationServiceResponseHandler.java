package de.querformatik.smarthome.backend;

import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AerationServiceResponseHandler implements
		HttpServiceResponseHandler {
	private static String TAG = "AerationServiceResponseHandler";
	public static final int RESULT_AERATION = 1;
	public static final int RESULT_POWER_CONTROL = 2;

	private static int RESULT_ERROR = 4;

	private Handler listener = null;
	private int type;

	public AerationServiceResponseHandler(int type) {
		super();
		this.type = type;
	}

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

		switch (type) {
		case RESULT_AERATION:
			try {
				JSONObject info = new JSONObject(content);
				int aerationLevel = info.getInt("aerationLevel");
				sendResult(this.type, aerationLevel);
			} catch (Exception e) {
				e.printStackTrace();
				sendResult(this.type, RESULT_ERROR);
			}
			break;
			
		case RESULT_POWER_CONTROL:
			try {
				JSONObject info = new JSONObject(content);
				int powerControl = info.getInt("powerControl");
				int powerStatus = info.getInt("powerStatus");
				sendResult(this.type, powerControl*10+powerStatus);
			} catch (Exception e) {
				e.printStackTrace();
				sendResult(this.type, RESULT_ERROR);
			}
			break;
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
