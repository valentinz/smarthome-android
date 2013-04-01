package de.querformatik.smarthome.backend;

import android.os.Handler;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class AerationService extends Handler {
	private String soapApi;
	
	public String getSoapApi() {
		return soapApi;
	}
	
	public void setSoapApi(String soapApi) {
		this.soapApi = soapApi;
	}
	
	protected void httpRequest(List<NameValuePair>params) {
		String paramString = URLEncodedUtils.format(params, "utf-8");

		HttpService http = new HttpService();
		AerationServiceResponseHandler response = new AerationServiceResponseHandler();
		
		http.setResponse(response);
		http.setUrl(getSoapApi() + "?" + paramString);
		http.start();
	}
	
	public int getCurrent() {
		return 2;
	}
	
	public void setCurrent(int current) {
	    List<NameValuePair> params = new LinkedList<NameValuePair>();
	    params.add(new BasicNameValuePair("setAerationLevel", String.valueOf(current)));
		httpRequest(params);
	}
}
