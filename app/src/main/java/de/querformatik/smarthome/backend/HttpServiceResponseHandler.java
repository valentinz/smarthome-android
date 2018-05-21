package de.querformatik.smarthome.backend;

interface HttpServiceResponseHandler {
	public void handleException(Exception e);
	public void handleResult(String content);
}
