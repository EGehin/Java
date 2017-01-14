import java.util.Random;
/**
 * @author      Elliot Gehin <xt016916@reading.ac.uk>
 * @version     15
 * @since       2014-11-29
 */
public class AWorld extends AbstractBug{

	private int MaxSensingDistance = 2;
	private static int cycles = 2;
	static boolean runOnce = true; //variable used so blank spaces are added to map once
	static boolean addFoodOnce = true; //used so food is only added once
	static boolean addMushroomOnce = true; //used so mushrooms are added only once


  /** Used to change the number of times EACH BUG will move
   * 
   * @param newCycles		Variable used for the number of cycles
   */
	public static void updateCycles(int newCycles){
		cycles = newCycles;
	}
	/**Constructs world by running the drawMap() method
	 * 
	 * @param map		2D character array map
	 * @param bug		Array of bugs
	 */
	
	public AWorld(char[][]map, ABug[]bug){
		drawMap(map, bug);
	}
	
	/**
	 * 
	 * @param map		2D character array map
	 */
	public AWorld(char[][]map){
		
	}

	/** Returns useful directions and creates enum of type Direction
	 * 
	 * @author Elliot
	 *
	 */
	public enum Direction {

		NORTH, EAST, SOUTH, WEST;
		private static Random rnd = new Random();
		static public Direction randomDirection() {
			return Direction.values()[rnd.nextInt(4)];
		}
		static public Direction randNotNorth(){
			return Direction.values()[rnd.nextInt(3)+1];
		}
		static public Direction randNotEast(){
			Direction r = Direction.values()[rnd.nextInt(4)];
			if (r!=Direction.EAST) return r;
			else return Direction.SOUTH;
		}
		static public Direction randNotSouth(){
			Direction r = Direction.values()[rnd.nextInt(4)];
			if (r!=Direction.SOUTH) return r;
			else return Direction.WEST;
		}
		static public Direction randNotWest(){
			Direction r = Direction.values()[rnd.nextInt(4)];
			if (r!=Direction.WEST) return r;
			else return Direction.NORTH;
		}
	}

	/** Used to simply output the map, does not actually affect any coordinates
	 * 
	 * @param map		2D character array map
	 * @param bug		Array of bugs
	 */
	public void draw(char[][]map, ABug[]bug){
		for(int i=0;i<map.length;i++)
		{
			for(int j=0;j<map[0].length;j++)
			{
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}
		
	/** Intially adds important characters to the 2d map array, such as boundaries. Run in the constructor.
	 * 
	 * @param map		2D character array map
	 * @param bug		Array of bugs
	 */
	public void drawMap(char[][]map, ABug[]bug){

		if(runOnce){
			
		for(int i=0;i<map.length;i++)
		{
			for(int j=0;j<map[0].length;j++)
			{
				map[i][j] = ' '; // it's important this line only runs once so that blank space doesn't overwrite important items on the map
				map[0][j] = '.';
				map[i][0] = '.';
				map[i][map[0].length-1] = '.';
				map[map.length-1][j] = '.';
			}
		}
		}
		runOnce = false;
		addFood(map);
		addObject(map);
		addBugs(map,bug);
		addShrooms(map);
	}

	/** Bugs added to the map with their corresponding symbols
	 * 
	 * @param map		2D character array map
	 * @param bug		Array of bugs
	 */
	public void addBugs(char[][]map, ABug[]bug){ //adds bug with their corresponding symbols

		for(int b=0;b<bugs;b++){
			map[bug[b].getHori()][bug[b].getVert()] = bug[b].getSymbol();
		}
	}

	/** Objects added to the map using the character 'o'
	 * 
	 * @param bug		Array of bugs
	 */
	public void addObject(char[][]map){ //add objects to the map
		for(int i=5;i<map.length-4;i+=5)
		{
			for(int j=2;j<map[0].length-3;j+=3)
			{
				map[i][j] = 'O';
			}

		}
		for(int i = 0;i<map.length;i++){ //additionally objects are added around the edge of the map to keep bugs from going out of bounds
		map[i][0] = 'O';
		map[0][i] = 'O';
		map[i][map.length-1] = 'O';
		map[map.length-1][i] = 'O';
		}
	}

	/** Used in Animation.java to check which coordinates have objects in them
	 * 
	 * @param map		2D char array map	
	 * @param i			Used for looping through coordinates
	 * @param j			Used for looping through coordinates
	 * @return 			Boolean which returns true when objects exist in certain coordinates
	 */
	public static boolean getObjects(char[][]map, int i, int j){

		if(map[i][j] == 'O'){
			return true;
		}
		else return false;
	}

	/** Used in Animation.java to check which coordinates still contain food
	 * 
	 * @param map		2D character array for map
	 * @param i			Used to loop through 2D array
	 * @param j			Used to loop through 2D array
	 * @return			Returns true if food is at a certain coordinate
	 */
	public static boolean getFood(char[][]map, int i, int j){

		if(map[i][j] == 'F'){
			return true;
		}
		else return false;
	}


	/** Adds poisonous mushroom to the map
	 * 
	 * @param map 		2D character array for the map
	 */
	public void addShrooms(char [][]map){
		if(addMushroomOnce){

				map[12][12] = 'X';
		}
		addMushroomOnce = false;
	}
	
	/** Adds other food to the map
	 * 
	 * @param map		2D character array for the map
	 */
	public void addFood(char[][]map){ //adds food
		
		if(addFoodOnce){
		
		for(int i=2;i<map.length-2;i+=6)
		{
			for(int j=3;j<map[0].length-2;j+=6)
			{

				map[i][j] = 'F';
			}
		}
		}
		addFoodOnce = false;

	}

	/** Returns true if food exists within 1 less than the max sensing distance
	 * 
	 * @param d			Takes in a direction (NESW)
	 * @param map		2D character array for the map
	 * @param bug		Array of bugs
	 * @param bugNum	Used to loop through all bugs
	 * @return			Returns boolean true if food is smelled within certain distance
	 */
	public boolean smellFood(Direction d, char[][]map, ABug[]bug, int bugNum){ //smellFood and smellFood2 check for food with 1 and 2 blocks
		boolean food = false;


		if(d == Direction.NORTH){
			if(map[(bug[bugNum].getHori())-(MaxSensingDistance-1)][bug[bugNum].getVert()] == 'F')
				food = true;
		}
		if(d == Direction.EAST){
			if(map[bug[bugNum].getHori()][(bug[bugNum].getVert())+(MaxSensingDistance-1)] == 'F')
				food = true;
		}
		if(d == Direction.SOUTH){
			if(map[(bug[bugNum].getHori())+(MaxSensingDistance-1)][bug[bugNum].getVert()]=='F')
				food =true;
		}		
		if(d == Direction.WEST){
			if(map[bug[bugNum].getHori()][(bug[bugNum].getVert())-(MaxSensingDistance-1)] == 'F')
				food = true;
		}

		return food;
	}

	/** Returns true if there is food within the max sensing distance
	 * 
	 * @param d			Takes in a direction (NESW)
	 * @param map		2D character array for the map
	 * @param bug		Array of bugs
	 * @param bugNum	Used to loop through all bugs
	 * @return			Returns boolean true if food is smelled within max sensing distance
	 */
	public boolean smellFood2(Direction d, char[][]map, ABug[]bug, int b){
		boolean food = false;


		if(d == Direction.NORTH){
			if(map[(bug[b].getHori())-(MaxSensingDistance)][bug[b].getVert()] == 'F')
				food = true;
		}
		if(d == Direction.EAST){
			if(map[bug[b].getHori()][(bug[b].getVert())+(MaxSensingDistance)] == 'F')
				food = true;
		}
		if(d == Direction.SOUTH){
			if(map[(bug[b].getHori())+(MaxSensingDistance)][bug[b].getVert()]=='F')
				food =true;
		}		
		if(d == Direction.WEST){
			if(map[bug[b].getHori()][(bug[b].getVert())-(MaxSensingDistance)] == 'F')
				food = true;
		}

		return food;
	}

	/** Uses smellFood and smellFood2 to return best direction for each bug
	 * *
	 * @param map 		2D char map array
	 * @param bug 		Array of bugs
	 * @param b 		Used for multiple bugs
	 * @return 			Enum of type Direction. North, east south or west
	 */
	public Direction getDirectionOfFood(char[][]map, ABug[]bug, int b){ //returns nearest food, checking within 1 block first

		if(smellFood(Direction.NORTH, map, bug, b)== true)
			return Direction.NORTH;
		else if(smellFood(Direction.EAST, map, bug, b)== true)
			return Direction.EAST;
		else if(smellFood(Direction.SOUTH, map, bug, b)== true)
			return Direction.SOUTH;
		else if(smellFood(Direction.WEST, map, bug, b)== true)
			return Direction.WEST;
		else if(smellFood2(Direction.NORTH, map, bug, b)== true)
			return Direction.NORTH;
		else if(smellFood2(Direction.EAST, map, bug, b)== true)
			return Direction.EAST;
		else if(smellFood2(Direction.SOUTH, map, bug, b)== true)
			return Direction.SOUTH;
		else if(smellFood2(Direction.WEST, map, bug, b)== true)
			return Direction.WEST;
		else return Direction.randomDirection();
	}

	/** Used to get bugs to avoid obstacles
	 * 
	 * @param d 		Takes in a direction
	 * @param map 		2D char array map
	 * @param bug 		Array of bugs
	 * @param b 		Used to loop through array
	 * @return 			Direction which is clear
	 */
	public Direction avoidObject(Direction d, char[][]map, ABug[]bug, int b){ //uses the boolean isObj to check if path is clear, if it's not a different direction is returned

		//returns a random direction unless the given direction is clear

		if(d ==Direction.NORTH){
			if(isObj(d, map, bug, b)==false){return Direction.randNotNorth();}
			else return Direction.NORTH;
		}
		else if(d ==Direction.EAST){
			if(isObj(d, map, bug, b)==false){return Direction.randNotEast();}
			else return Direction.EAST;

		}
		else if(d ==Direction.SOUTH){
			if(isObj(d, map, bug, b)==false){return Direction.randNotSouth();}
			else return Direction.SOUTH;

		}
		else{
			if(isObj(d, map, bug, b)==false){return Direction.randNotWest();}
			else return Direction.WEST;

		}

	}

	/** Used by avoidObject() to check if coordiate is clear
	 * 
	 * @param d			Takes in a direction (NESW)
	 * @param map		2D character array for the map
	 * @param bug		Array of bugs
	 * @param bugNum	Used to loop through all bugs
	 * @return			Returns boolean false if path is blocked
	 */
	public boolean isObj(Direction d, char map[][], ABug[]bug, int b){ //returns true if there is no object within 1 block, bug is clear to move

		boolean obj = false;

		if (d == Direction.NORTH){

			if(map[bug[b].getHori()- (MaxSensingDistance-1)][bug[b].getVert()] == ' ' || map[bug[b].getHori()- (MaxSensingDistance-1)][bug[b].getVert()] == 'F')
				obj = true;
		}

		else if (d == Direction.SOUTH){

			if(map[bug[b].getHori()+ (MaxSensingDistance-1)][bug[b].getVert()] == ' ' || map[bug[b].getHori()+ (MaxSensingDistance-1)][bug[b].getVert()] == 'F')
				obj = true;
		}

		else if (d == Direction.EAST){

			if(map[bug[b].getHori()][bug[b].getVert()+ (MaxSensingDistance-1)] == ' ' || map[bug[b].getHori()][bug[b].getVert()+ (MaxSensingDistance-1)] == 'F')
				obj = true;
		}

		else if (d == Direction.WEST){

			if(map[bug[b].getHori()][bug[b].getVert()- (MaxSensingDistance-1)] == ' ' || map[bug[b].getHori()][bug[b].getVert()- (MaxSensingDistance-1)] == 'F'){
				obj = true;
			}
		}

		return obj;
	}
	
	/** Run once in Animation.java to add bugs and their coordinates
	 * 
	 * @param map		2D ma char array	
	 * @param bug		Array of bugs
	 * @return			Returns world to be used by method run2() for bugs movement cycles
	 */
	public static AWorld run(char [][]map, ABug [] bug){ //run once to set up the world, add bugs etc
		
		
		
		for(int i=0;i<bug.length;i++){
			bug[i] = new ABug();
		}

		bug[0].setSomeBugs('0', 14, 5); //Convenient method to quickly bug a bugs symbol and coordinates
		bug[1].setSomeBugs('1', 19, 19);
		bug[2].setSomeBugs('2', 19, 11);
		bug[3].setSomeBugs('3', 6, 13);
		bug[4].setSomeBugs('4', 9, 6);
		
		
		AWorld world = new AWorld(map,bug);

		
		return world;
	}
	
	
	
	
	/** Run after each cycles to get each bugs next movement, uses getDirectionOfFood and avoidObject
	 * 
	 * @param bug		2D bug array
	 * @param world		type AWorld, returned by run() method
	 */
	public static void run2( ABug [] bug, AWorld world){ //each time run represents a cycle
		System.out.println();

		
		
		for(int b = 0; b< bugs;b++){
			//Runs through all bugs, using getDirectionOfFood to find nearest food (if any)
			Direction closestFood = world.getDirectionOfFood(map, bug, b);
			
			// realClosestFood avoids takes this direction and checks there is no object in the way
			Direction realClosestFood = world.avoidObject(closestFood, map, bug, b);

			
			if(realClosestFood == Direction.NORTH){
				AbstractBug.map[bug[b].getHori()][bug[b].getVert()] = ' '; //old coordinate replaced with blank space
				AbstractBug.bug[b].setHori(bug[b].getHori()-1);
				if(AbstractBug.bug[b].getHori()<=1) AbstractBug.bug[b].setHori(bug[b].getHori()+1); //stops bug from going out of bounds
			}
			if(realClosestFood == Direction.EAST){
				AbstractBug.map[bug[b].getHori()][bug[b].getVert()]= ' ';
				AbstractBug.bug[b].setVert(bug[b].getVert()+1);
				if(AbstractBug.bug[b].getVert()>=22) AbstractBug.bug[b].setVert(bug[b].getVert()-1);
			}
			if(realClosestFood == Direction.SOUTH){
				AbstractBug.map[bug[b].getHori()][bug[b].getVert()] = ' ';
				AbstractBug.bug[b].setHori(bug[b].getHori()+1);
				if(AbstractBug.bug[b].getHori()>=22) AbstractBug.bug[b].setHori(bug[b].getHori()-1);
			}
			if(realClosestFood == Direction.WEST){
				AbstractBug.map[bug[b].getHori()][bug[b].getVert()] = ' ';
				AbstractBug.bug[b].setVert(bug[b].getVert()-1);
				if(AbstractBug.bug[b].getVert()<=1) AbstractBug.bug[b].setVert(bug[b].getVert()+1);
			}
			System.out.println("bug" + b +" - " + bug[b].getHori() + ":" + bug[b].getVert());

		}
		System.out.println();

	}
	
	/** Main method for AWorld, application entry point.
	 * 
	 * @param args Array of command-line arguments passed to this method 
	 */
	public static void main(String[] args) {
		
		run(map, bug);
		System.out.println("World generated");

			
	}
	
	
}