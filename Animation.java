import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * @author      Elliot Gehin <xt016916@reading.ac.uk>
 * @version     11
 * @since       2015-01-05
 */
public class Animation extends Application {

	
	//Objects to access other classes
	AbstractBug b;
	AWorld a;

	@Override 
	/** Sets the stage
	 * 
	 */
	public void start(final Stage stage) throws Exception {

		//Displays a popup window with infomation on how to use the simulation
        final Popup popup = new Popup();
        popup.setX(600);
        popup.setY(400);
        
        TextArea textArea = new TextArea();
        textArea.setPrefRowCount(4);            
        
        //Text to explain to the user how to operate the simulation
        
        textArea.setText("Press 'Generate' to create the map with default settings.\n"
        		+ "The simulation will run indefinately and can be controlled with 'Play' and 'Pause'.\n"
        		+ "The default settings can be loaded by pressing 'Reset'.\n"
        		+ "Press 'Hide' to remove this message.");
        
        popup.getContent().addAll(textArea);
		
    	Button btnFile = new Button("File"); //Create new window with these options
    	Button btnView = new Button("View");
    	Button btnEdit = new Button("Edit");
    	Button btnSim = new Button("Generate");
    	Button btnReset = new Button("Reset");
    	Button btnPlay = new Button("Play");
    	Button btnPause = new Button("Pause");
    	Button btnShowInfo = new Button("Info");
    	Button btnHideInfo = new Button("Hide");
        
		final GridPane grid = new GridPane();

		GridPane.setConstraints(btnFile, 26, 0); //adds buttons to the gridPane
		GridPane.setConstraints(btnView, 26, 3);
		GridPane.setConstraints(btnEdit, 26,1);
		GridPane.setConstraints(btnSim, 26, 2);
		GridPane.setConstraints(btnReset, 26, 3);
		GridPane.setConstraints(btnPlay, 26, 4);
		GridPane.setConstraints(btnPause, 26, 5);
		GridPane.setConstraints(btnShowInfo, 26, 6);
		GridPane.setConstraints(btnHideInfo, 26, 7);


		grid.getChildren().addAll(btnFile, btnEdit, btnSim, btnView, btnReset,btnPause,btnPlay,btnShowInfo ,btnHideInfo );

		final ABug[]bug = new ABug[b.bugs];
		for(int i=0;i<bug.length;i++){
			bug[i] = new ABug();
		}


		//Import images

		final Image ladybug = new Image("ladybug.png");
		final Image greenbug = new Image("greenbug.png");
		final Image orangebug = new Image("orangebug.png");
		final Image spider = new Image("spider.png");
		final Image bluebug = new Image("bluebug.png");
		final Image grass = new Image("grass.png");
		final Image food = new Image("mcdonalds.png");
		final Image rock = new Image("rock.png");
		final Image mushroom = new Image("mushroom.png");

		//ImageView for images for use with GridPane

		final ImageView orangeIV = new ImageView(orangebug);
		final ImageView spiderIV = new ImageView(spider);
		final ImageView bluebugIV = new ImageView(bluebug);
		final ImageView ladybugIV = new ImageView(ladybug);
		final ImageView greenbugIV = new ImageView(greenbug);
		final ImageView mushroomIV = new ImageView(mushroom);
		final ImageView []foodIV = new ImageView[16];


		//Creates array for food elements
		for(int i =0;i<16;i++){
			foodIV[i] = new ImageView(food);
		}

		//Adds grass to entire map
		for(int i = 0;i<25;i++){
			for(int j = 0;j<25;j++){
				grid.add(new ImageView(grass), i, j);
			}
		}

		// btnShowInfo and btnHideInfo show and hide the infomation box with instructions on how the program runs
		btnShowInfo.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				

	               popup.show(stage);	               
	            
			}
			});
		
		// btnShowInfo and btnHideInfo show and hide the infomation box with instructions on how the program runs
		btnHideInfo.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				
	                popup.hide();
	            
			}
			});

		btnSim.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {

				AWorld.main(null); //Runs the main method in AWorld which generates the world

				grid.add(mushroomIV, 12, 12);

				int c =0; //Used to access each element in food array

				//Runs through the map, uses getObjects and getFood to determine correct coordinates 
				for(int i = 0;i<b.map.length;i++)
				{
					for(int j =0;j<b.map.length;j++)
					{
						if(AWorld.getObjects(b.map, j, i)==true)
							grid.add(new ImageView(rock), i,j );
						if(AWorld.getFood(b.map, j, i)==true)
							grid.add(foodIV[c++], i,j );
					}
				}		
			}

		});


		btnEdit.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				//String newcycles = JOptionPane.showInputDialog("Enter number of cycles: ");
				//int intcycles = Integer.parseInt(newcycles);
				//AWorld.updateCycles(intcycles);

			}
		});


		
		int speed = 300;
		KeyFrame frame = new KeyFrame(Duration.millis(speed), new EventHandler<ActionEvent>(){

			@Override			
			public void handle(ActionEvent t){
				//Removes all bugs from the maps
				grid.getChildren().remove(orangeIV);
				grid.getChildren().remove(spiderIV);
				grid.getChildren().remove(bluebugIV);
				grid.getChildren().remove(ladybugIV);
				grid.getChildren().remove(greenbugIV);

				//Remoces all food from the map
				for(int b =0;b<16;b++){
					grid.getChildren().remove(foodIV[b]);
				}

				AWorld.run2(b.bug, AWorld.run(b.map, bug));				

				//Readds the bugs to the map with new coordinates
				grid.add(orangeIV, a.bug[0].getVert(),a.bug[0].getHori() );
				grid.add(spiderIV, a.bug[1].getVert(), a.bug[1].getHori() );
				grid.add(bluebugIV, a.bug[2].getVert(),a.bug[2].getHori() );
				grid.add(ladybugIV, a.bug[3].getVert() ,a.bug[3].getHori());
				grid.add(greenbugIV,a.bug[4].getVert(), a.bug[4].getHori() );

				int d=0; //Variable to loop through food in array

				for(int i = 0;i<b.map.length;i++) //Checks again for food and adds it again
				{
					for(int j =0;j<b.map.length;j++)
					{	
						if(AWorld.getFood(b.map, j, i)==true){
							System.out.println("Food at: " + j + ":" + i);

							grid.add(foodIV[d++], i,j );
						}
					}
				}
			}
		
		});
		final Timeline timeline = new Timeline(frame);
		timeline.setCycleCount(javafx.animation.Animation.INDEFINITE); //timeline is set to run indefinately by default
		

		btnPlay.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				//Plays/resumes the timeline
				timeline.play();

			}
		});
		
		
		btnPause.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0){

			// Pauses the timeline
			timeline.pause();	
			
			}
		});
		
		btnReset.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				AWorld.addFoodOnce = true; //addFoodOnce remains false once food is added, this adds it again

				for(int i=0;i<bug.length;i++){
					bug[i] = new ABug();
				}

				b.bug[0].setSomeBugs('0', 14, 5);
				b.bug[1].setSomeBugs('1', 19, 19);
				b.bug[2].setSomeBugs('2', 19, 11);
				b.bug[3].setSomeBugs('3', 6, 13);
				b.bug[4].setSomeBugs('4', 9, 6);

				
				timeline.pause();
				JOptionPane.showMessageDialog(null, "Press Play To Start Again.", "Bugs and Food Reset", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		
		Scene scene = new Scene(grid,1040,1000);
		stage.setScene(scene);
		stage.show();

	}
	
	/** Main method for Animation
	 * 
	 * @param args Array of command-line arguments passed to this method 
	 */
	public static void main(String[] args) {
		launch(args);

	}
}
