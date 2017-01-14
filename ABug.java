import java.io.FileReader;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author      Elliot Gehin <xt016916@reading.ac.uk>
 * @version     3
 * @since       2014-11-29
 */
public class ABug extends AbstractBug {

	/** Constructor for ABug
	 * 
	 */
	public ABug(){	
	}
	
	AbstractBug ab;
	
	/**
	 * 
	 * @param ab	Object from AbstractBug
	 */
	public ABug(AbstractBug ab){
		this.ab = ab;
	}
	


	static ABug[]bug = new ABug[bugs];
	static Properties properties = new Properties();
	String file = "bugs.properties";

	/** Used to edit the properties file
	 * 
	 * @param properties 	.properties file
	 */
	void editConfig(Properties properties){
		int menu = 0;

		System.out.print("Enter 1 to edit current life form\nEnter 2 to delete current life form\n");
		Scanner scan = new Scanner(System.in);
		menu = scan.nextInt();

		switch(menu){
		case 1:  System.out.print("1");
		case 2:System.out.print("2");
		}


	}

	/** Displays properties file
	 * 
	 * @param .properties file
	 */
	void displayConfig(Properties properties){

		//Reads .properties file
		try(FileReader reader = new FileReader(file)){
			properties.load(reader);

			System.out.println(properties.size() + " properties for " + bugs + " bugs.\n");

			
			//Splits the file to read properties
			
			String[]symbol = properties.getProperty("bugs.symbol","").split(",");
			String[]hori = properties.getProperty("bugs.hori","").split(",");
			String[]vert = properties.getProperty("bugs.vert","").split(",");
			String mapHori = properties.getProperty("map.hori");
			String mapVert = properties.getProperty("map.vert");

			int h = Integer.parseInt(mapHori);
			int v = Integer.parseInt(mapVert);

			for(int x = 0;x<bugs;x++){
				char c = symbol[x].charAt(0);
				bug[x].setSymbol(c);
			}

			for(int x = 0;x<bugs;x++){
				int i = Integer.parseInt(hori[x]);
				bug[x].setHori(i);
			}

			for(int x = 0;x<bugs;x++){
				int i = Integer.parseInt(vert[x]);
				bug[x].setVert(i);
			}



		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/** Main method for ABug, application entry point
	 * 
	 * @param args Array of command-line arguments passed to this method 
	 */
	public static void main(String[] args) {

		AbstractBug ab = null;
		ABug config = new ABug();

		for(int i=0;i<bug.length;i++){
			bug[i] = new ABug();
		}

		int menu = 0;
		int menu2 =35;
		Scanner scan = new Scanner(System.in);

		do{

			System.out.println("1: File - does not exist yet\n2: View\n3: Edit\n4: Simulation - does not exist yet\n5: Help");
			menu = scan.nextInt();

			if(menu ==1){
				break;
			}
			else if(menu ==2){				
				System.out.println("1: View config - does not exist yet\n2: View bugs\n3: View map info\n4: Back to main menu\n");
				menu2 = scan.nextInt();

				if(menu2 == 1){
					break;
				} //do something
				else if(menu2 ==2){
					config.displayConfig(properties);


					for(int x = 0;x<bugs;x++){
						System.out.println("Bug " + x + " - Symbol: "+ bug[x].getSymbol());
						System.out.println("Horizonal coordinate: " + bug[x].getHori());
						System.out.println("Vertical coordinate: " + bug[x].getVert() + "\n");

					}
				}
				else if (menu2 ==3){
					System.out.println(ab.map.length + ":" + ab.map[0].length);
					System.out.println("Food = " + null);
					System.out.println("Objects = " + null + "\n");

				}
				else if (menu2 == 0){
					break;
				}

			}
			else if(menu ==3){
				config.editConfig(properties);
			}
			else if(menu == 5)
			{
				System.out.println("\nHelp! I need somebody\nHelp! Not just anybody\nHelp! You know I need someone\nHelppp!\n");
			}

		}while(menu !=0);
 
	}
}
