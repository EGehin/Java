
/**
 * @author      Elliot Gehin <xt016916@reading.ac.uk>
 * @version     2.2
 * @since       2014-11-29
 */
public abstract class AbstractBug {
	
	
	static char[][] map = new char[25][25]; //2 character array map
	static int bugs = 5; // number of bugs in array
	static ABug[]bug = new ABug[bugs]; //Array of bugs
	
	
	//The following variables are attributes for bugs.
	
	private String species;
	private String name;
	private char symbol;
	private int hori;
	private int vert;
	private int energy;
	private int uid;
	
	/** Handy method for quickly adding a bug with only a symbol and horizonal and vertical coordinate
	 * 
	 * @param symbol
	 * @param hori
	 * @param vert
	 */
	public void setSomeBugs(char symbol, int hori, int vert)
	{
		this.symbol = symbol;
		this.hori = hori;
		this.vert = vert;
	}
	
	/** Returns text formatted symbol, horizonal and vertical coordinate for a bug
	 * 
	 * @param symbol
	 * @param hori
	 * @param vert
	 * @return
	 */
	public String getSomeBugs(char symbol, int hori, int vert){
		return ("Symbol: " + symbol + "\nCoordinates: " + hori + ":"+ vert + "\n");
	}
	
	/**
	 * 
	 * @return Bug species
	 */
	public String getSpecies() {
		return species;
	}
	
	/** Sets bug's species
	 * 
	 * @param species
	 */
	public void setSpecies(String species) {
		this.species = species;
	}
	
	/**
	 * 
	 * @return Bug's name
	 */
	public String getName() {
		return name;
	}
	/** Sets bug's name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @return Bug's symbol
	 */
	public char getSymbol() {
		return symbol;
	}
	/** Sets bug's symbol
	 * 
	 * @param symbol
	 */
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	/**
	 * 
	 * @return Bug's horizonal coordinate
	 */
	public int getHori() {
		return hori;
	}
	
	/** Sets horizonal coordinate
	 * 
	 * @param hori
	 */
	public void setHori(int hori) {
		this.hori = hori;
	}
	
	/**
	 * 
	 * @return Bug's vertical coordinate
	 */
	public int getVert() {
		return vert;
	}
	
	/** Sets vertical coordinate
	 * 
	 * @param vert
	 */
	public void setVert(int vert) {
		this.vert = vert;
	}
	
	/**
	 * 
	 * @return Bug's energy level
	 */
	public int getEnergy() {
		return energy;
	}

	/** Sets energy
	 * 
	 * @param energy
	 */
	public void setEnergy(int energy) {
		this.energy = energy;
	}

	/**
	 * 
	 * @return Bug's unique ID
	 */
	public int getUid() {
		return uid;
	}

	/** Sets unique ID
	 * 
	 * @param uid
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}


}
