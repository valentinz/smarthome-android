package de.querformatik.smarthome.backend;

import android.os.Handler;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class AerationService extends Handler {
	private String soapApi;
	private Handler listener;
	
	public String getSoapApi() {
		return soapApi;
	}
	
	public void setSoapApi(String soapApi) {
		this.soapApi = soapApi;
	}
	
	public Handler getListener() {
		return this.listener;
	}
	
	public void setListener(Handler listener) {
		this.listener = listener;
	}
	
	protected void httpRequest(int type, List<NameValuePair>params) {
		String paramString = URLEncodedUtils.format(params, "utf-8");

		HttpService http = new HttpService();
		AerationServiceResponseHandler response = new AerationServiceResponseHandler(type);
		response.setListener(getListener());
		
		http.setResponse(response);
		http.setUrl(getSoapApi() + "?" + paramString);
		http.start();
	}
	
	public void requestCurrent() {
	    List<NameValuePair> params = new LinkedList<NameValuePair>();
	    params.add(new BasicNameValuePair("getAerationLevel", String.valueOf(1)));
		httpRequest(AerationServiceResponseHandler.RESULT_AERATION, params);
	}
	
	public void setCurrent(int current) {
	    List<NameValuePair> params = new LinkedList<NameValuePair>();
	    params.add(new BasicNameValuePair("setAerationLevel", String.valueOf(current)));
		httpRequest(AerationServiceResponseHandler.RESULT_AERATION, params);
	}
	
	
	public void getPowerControl(int num) {
		List<NameValuePair> params = new LinkedList<NameValuePair>();
	    params.add(new BasicNameValuePair("getPowerControl", String.valueOf(num)));
		httpRequest(AerationServiceResponseHandler.RESULT_POWER_CONTROL, params);
	}
	
	public void setPowerControl(int num, int status) {
		List<NameValuePair> params = new LinkedList<NameValuePair>();
	    params.add(new BasicNameValuePair("setPowerControl", String.valueOf(num)));
	    params.add(new BasicNameValuePair("setPowerStatus", String.valueOf(status)));
		httpRequest(AerationServiceResponseHandler.RESULT_POWER_CONTROL, params);
	}
}
