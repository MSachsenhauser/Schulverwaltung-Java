package Elements;

public interface IDatabaseObject<T> {
	
	/**
	 * Methode zum Anlegen in der Datenbank
	 * */
	void addToDb();

	/**
	 * Methode zum Löschen aus der Datenbank
	 * */
	void removeFromDb();

	/**
	 * Methode zum Aktualisieren in der Datenbank
	 * */
	void save();
	
	T load();
	
	T setId(int id);
}
