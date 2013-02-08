package helpers;
public class MenuItem
{
	private String displayName ="";
	private String link ="";
	public MenuItem(String name, String link)
	{
		this.displayName = name;
		this.link = link;
	}
	public String getDisplayName() {
		return displayName;
	}
	public String getLink() {
		return link;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
}