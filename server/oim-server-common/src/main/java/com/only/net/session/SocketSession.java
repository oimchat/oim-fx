package com.only.net.session;

public interface SocketSession {

	public void setKey(String key);

	public String getKey();

	public void write(Object object);

	public void close();

	public void addAttribute(Object key, Object value);

	public <T> T getAttribute(Object key);
	
	public void setAuth(boolean auth);
	
	public boolean isAuth();
	
	public String getRemoteAddress();
	
}
