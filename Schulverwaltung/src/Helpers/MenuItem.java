package Helpers;
public class MenuItem
{
	private String link ="";
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	private String displayName ="";
	
	public MenuItem(String name, String link)
	{
		this.displayName = name;
		this.link = link;
	}
}