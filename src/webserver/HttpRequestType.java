package webserver;

public enum HttpRequestType {
	GET("GET"),
	POST("POST"),
	HTTP_HEAD("HTTP HEAD"),
	TRACE("TRACE");

	private String displayName;

	HttpRequestType(String displayName) {
		this.displayName = displayName;
	}

	public String displayName() { return displayName; }
}
