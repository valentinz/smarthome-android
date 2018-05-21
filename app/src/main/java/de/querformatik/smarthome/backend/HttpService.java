package de.querformatik.smarthome.backend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpService extends Thread {
	private HttpServiceResponseHandler response; 
	private String url;
	
	public HttpServiceResponseHandler getResponse() {
		return this.response;
	}
	
	public void setResponse(HttpServiceResponseHandler response) {
		this.response = response;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void run() {
		try {
			URLConnection conn = (new URL(getUrl())).openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String content = "";
			while ((line = rd.readLine()) != null) {
				content += line;
			}
			getResponse().handleResult(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			getResponse().handleException(e);
		}

	}
}
