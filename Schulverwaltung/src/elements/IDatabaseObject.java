package elements;

public interface IDatabaseObject<T> {
	
	/**
	 * Methode zum Anlegen in der Datenbank
	 * */
	void addToDb();

	T load();

	/**
	 * Methode zum Löschen aus der Datenbank
	 * */
	void removeFromDb();
	
	/**
	 * Methode zum Aktualisieren in der Datenbank
	 * */
	void save();
	
	T setId(int id);
}
