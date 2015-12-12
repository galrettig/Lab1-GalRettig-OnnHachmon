package webserver;

// TODO: Delete

public enum ImageTypes {
	BMP("bmp"),
	GIF("gif"),
	PNG("png"),
	JPG("jpg");
	
	private String type;
	
	private ImageTypes(String type) 
	{
		this.type = type;
	}
	
	public static String getExtestion(String t)
	{
		for (ImageTypes image : values()) {
			if (image.type.equals(t))
			{
				return t;
			}
		}
		return null;
	}
}
