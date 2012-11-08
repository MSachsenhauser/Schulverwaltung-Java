package Elements;

public interface IDatabaseObject {
	
	/**
	 * Methode zum Anlegen in der Datenbank
	 * */
	void addToDb();

	/**
	 * Methode zum L�schen aus der Datenbank
	 * */
	void removeFromDb();

	/**
	 * Methode zum Aktualisieren in der Datenbank
	 * */
	void save();
	
	void load();
}
