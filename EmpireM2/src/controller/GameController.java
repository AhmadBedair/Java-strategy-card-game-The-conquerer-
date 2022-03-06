package controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import engine.*;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Status;
import units.Unit;
import view.*;

public class GameController implements ActionListener {
	private GameView view;
	private JPanel startGameWindow;
	private JPanel chooseCity;
	private JTextField nameField;
    private Game game;
    //buttons
    private ArrayList<JButton>btnsMarket;
    private ArrayList<JButton> btnsUncontroledCities;
    private ArrayList<JButton>btnsControlledCities;
    private ArrayList<JButton>btnsControlledArmies;
    private JButton chooseCityRome;
    private JButton chooseCityCairo;
    private JButton chooseCitySparta;
    private JButton btnEndTurn;
    private JButton btnMarket;
    private JButton btnInitiateArmy;
	private JButton btnPlayerCities;//in multi panel amd might be removed
	private JButton btnWorldViewUncontrolledCity;
	private JMenuItem menuMainMenu;
	private JMenuItem menuplayerCities;
	private JButton btnControlledCity;
	private JButton btnMainMenu;

  	 
	private String playerName;
	private String cityName1;
	 //ArrayLists
	/* private ArrayList<City>availableCities;
	 private ArrayList<City>controlledCities;
	 private ArrayList<Army>defendingArmies;
	 private ArrayList<Army>controlledArmies;
	 */
	 
	
	

	
public GameController(){
	 view = new GameView();
	 this.displayStartGameWindow();
	 
	 //Buttons
	 chooseCityRome  = new JButton("Rome");
	    chooseCityCairo  = new JButton("Cairo");
		chooseCitySparta  = new JButton("Sparta");
		chooseCityRome.addActionListener(this);
		chooseCityCairo.addActionListener(this);
		chooseCitySparta.addActionListener(this);
	 btnMainMenu = new JButton("Main Menu");
	 this.btnMarket = new JButton("MARKET");
	 btnMarket.addActionListener(this);
	    btnMainMenu.addActionListener(this);
	 btnPlayerCities = new JButton("Controlled Cities");
		btnPlayerCities.addActionListener(this);
		btnsUncontroledCities = new ArrayList();
		btnsControlledCities = new ArrayList();
		btnsControlledArmies = new ArrayList();
		this.btnEndTurn = new JButton("End Turn");
		btnEndTurn.addActionListener(this);
		btnInitiateArmy = new JButton("INITIATE ARMY");
		btnInitiateArmy.addActionListener(this);
		this.btnsMarket = new ArrayList();
		


	 //textFields
	
	 
 }






public void  displayStartGameWindow(){
	view.getContentPane().removeAll();
	JPanel panel = new JPanel();
	panel.setVisible(true);
	view.add(panel);

	JButton startButton = new JButton("Start Game");
	startButton.addActionListener(this);
    startButton.setBackground(Color.BLUE);
    panel.add(startButton);
	JLabel nameLabel = new JLabel("ENTER NAME");
	panel.add(nameLabel);
	nameLabel.setBounds(10, 20,80,100);
    this.nameField = new JTextField(20);
	nameField.setBounds(100,20,165,25);
	panel.add(nameField);
	panel.setBackground(Color.DARK_GRAY);
    view.revalidate();
    view.repaint();
    panel.revalidate();
    panel.repaint();
    startButton.revalidate();
    startButton.repaint();
}
public void displayAvailableCitiesView(){
	view.getContentPane().removeAll();
	JPanel panel = new JPanel();
	panel.setVisible(true);
	view.add(panel);
	panel.setLayout(new GridLayout(0,3));
	panel.add(chooseCityRome);
	panel.add(chooseCityCairo);
	panel.add(chooseCitySparta);
	view.revalidate();
	view.repaint();
	panel.revalidate();
	panel.repaint();

	}
public void defaultTextArea(){   //********should be refreshed***********
	JTextArea defaultTextArea = new JTextArea();
	defaultTextArea.setVisible(true);
	defaultTextArea.setEditable(false);
	String s = "";
	s+="PLAYER NAME: ";
	s+=this.playerName;
	s+="\n---------\n";
	s+="TURNS USED:";
	s+=this.game.getCurrentTurnCount()+"/50";
	s+="\n---------\n";
	s+="FOOD:";
	s+=this.game.getPlayer().getFood();
	s+="\n---------\n";
	s+="Treasury:";
	s+=this.game.getPlayer().getTreasury();
	s+="\n---------------------------------------------------------------------------------------\n";
	s+="IDLING ARMIES:"+"\n---------\n";;
	int c=1;
	for(Army a :this.game.getPlayer().getControlledArmies()){
		if (a.getCurrentStatus()==Status.IDLE){
			s+="Army"+c;
			c++;
			s+="DISTANCE TO TARGET:"+a.getDistancetoTarget()+"\n---------\n";
			s+="MAX TO HOLD:"+a.getMaxToHold()+"\n---------\n";
			s+="TARGET:"+a.getTarget()+"\n---------\n";
			s+="UNITS INFO;";
			for(Unit u:a.getUnits()){
				String type="";
				if(u instanceof Archer)
					s = "ARCHER";
				else if(u instanceof Infantry)
					s = "INFANTRY";
				else if(u instanceof Cavalry)
					s = "CAVALRY";
				
				s+="CURRENT SOLDIER COUNT:"+u.getCurrentSoldierCount()+"\n---------\n";
				s+="IDLE UPKEEP:"+u.getIdleUpkeep()+"\n---------\n";
				s+="LEVEL:"+u.getLevel()+"\n---------\n";
				s+="MARCHING UPKEEP"+u.getMarchingUpkeep()+"\n---------\n";
				s+="MAX SOLDIER COUNT"+u.getMaxSoldierCount()+"\n---------\n";
				s+="PARENT ARMY"+u.getParentArmy().getCurrentLocation()+"\n---------\n";
				s+="siege UPKEEP:"+type;
				
			}
			
		}
		s+="\n---------------------------------------------------------------------------------------";
		s+="MARCHING ARMIES:"+"\n---------\n";	
		if (a.getCurrentStatus()==Status.MARCHING){
			s+="Army"+c;
			c++;
			s+="DISTANCE TO TARGET:"+a.getDistancetoTarget()+"\n---------\n";
			s+="MAX TO HOLD:"+a.getMaxToHold()+"\n---------\n";
			s+="TARGET:"+a.getTarget()+"\n---------\n";
			s+="UNITS INFO;";
			for(Unit u:a.getUnits()){
				String type="";
				if(u instanceof Archer)
					s = "ARCHER";
				else if(u instanceof Infantry)
					s = "INFANTRY";
				else if(u instanceof Cavalry)
					s = "CAVALRY";
				
				s+="CURRENT SOLDIER COUNT:"+u.getCurrentSoldierCount()+"\n---------\n";
				s+="IDLE UPKEEP:"+u.getIdleUpkeep()+"\n---------\n";
				s+="LEVEL:"+u.getLevel()+"\n---------\n";
				s+="MARCHING UPKEEP"+u.getMarchingUpkeep()+"\n---------\n";
				s+="MAX SOLDIER COUNT"+u.getMaxSoldierCount()+"\n---------\n";
				s+="PARENT ARMY"+u.getParentArmy().getCurrentLocation()+"\n---------\n";
				s+="siege UPKEEP:"+type;
				
			}
			
		}
		s+="\n---------------------------------------------------------------------------------------";
		s+="BESIEGING ARMIES:"+"\n---------\n";
		if (a.getCurrentStatus()==Status.BESIEGING){
			s+="Army"+c;
			c++;
			s+="DISTANCE TO TARGET:"+a.getDistancetoTarget()+"\n---------\n";
			s+="MAX TO HOLD:"+a.getMaxToHold()+"\n---------\n";
			s+="TARGET:"+a.getTarget()+"\n---------\n";
			s+="UNITS INFO;";
			for(Unit u:a.getUnits()){
				String type="";
				if(u instanceof Archer)
					s = "ARCHER";
				else if(u instanceof Infantry)
					s = "INFANTRY";
				else if(u instanceof Cavalry)
					s = "CAVALRY";
				
				s+="CURRENT SOLDIER COUNT:"+u.getCurrentSoldierCount()+"\n---------\n";
				s+="IDLE UPKEEP:"+u.getIdleUpkeep()+"\n---------\n";
				s+="LEVEL:"+u.getLevel()+"\n---------\n";
				s+="MARCHING UPKEEP"+u.getMarchingUpkeep()+"\n---------\n";
				s+="MAX SOLDIER COUNT"+u.getMaxSoldierCount()+"\n---------\n";
				s+="PARENT ARMY"+u.getParentArmy().getCurrentLocation()+"\n---------\n";
				s+="siege UPKEEP:"+type;
				
			}
			
		}
	}
	
	s+="\n---------\n";
	
	defaultTextArea.setText(s);
	view.add(defaultTextArea, BorderLayout.EAST);
	view.revalidate();
	view.repaint();
	

    }
public void displayWorldMapView(){
	view.getContentPane().removeAll();
	this.defaultTextArea();
	this.multiPanel();
	JPanel panel = new JPanel();
	JPanel panel2 = new JPanel();
	panel.setVisible(true);
	panel2.setVisible(true);
	panel.setLayout(new GridLayout());
	panel2.setLayout(new GridLayout());
	view.add(panel,BorderLayout.CENTER); 
	view.add(panel2,BorderLayout.WEST);
	for(Army a:this.game.getPlayer().getControlledArmies()){
		
	}
	for(City c:this.game.getAvailableCities()){
		if(!this.game.getPlayer().getControlledCities().contains(c)){
			JButton b = new JButton(c.getName()+"\n (Uncontrolled)");
			panel.add(b);
			b.addActionListener(this);
			this.btnsUncontroledCities.add(b);
		}
		

	}
	
	
	
	
}
public void initiateGame(){
	try{
	this.game = new Game(this.nameField.getText(),this.cityName1);
	}
	catch(IOException e){
		System.out.print("File not found");
	}
	
}
public void multiPanel(){
	JPanel panel = new JPanel();
	panel.setVisible(true);
	view.add(panel,BorderLayout.SOUTH);
	panel.add(btnEndTurn);
	panel.add(btnPlayerCities);
	panel.add(btnMainMenu);
	panel.add(this.btnInitiateArmy);
	view.revalidate();
	view.repaint();
	panel.revalidate();
	panel.repaint();
	
}
public void multiPanel2(){
	JPanel panel = new JPanel();
	panel.setVisible(true);
	view.add(panel,BorderLayout.SOUTH);
	
	
	panel.add(btnEndTurn);
	panel.add(btnMarket);
	panel.add(btnPlayerCities);
	panel.add(btnMainMenu);
	panel.add(this.btnInitiateArmy);
	view.revalidate();
	view.repaint();
	panel.revalidate();
	panel.repaint();
	
}



public void displayControlledCities(){
	view.getContentPane().removeAll();
	this.multiPanel();
	JPanel panel = new JPanel();
	panel.setVisible(true);
	panel.setLayout(new GridLayout());
	view.add(panel);
	for(City c:this.game.getAvailableCities()){
		if(this.game.getPlayer().getControlledCities().contains(c)){
			JButton b = new JButton(c.getName());
			panel.add(b);
			b.addActionListener(this);
			this.btnsControlledCities.add(b);
		}
		

	}
	view.revalidate();
	view.repaint();
	panel.revalidate();
	panel.repaint();
	
	
}
public void displayCityView(String cityName){
	view.getContentPane().removeAll();
	this.cityViewText(cityName);
	JPanel panel = new JPanel();
	view.add(panel);
	this.multiPanel2();
	panel.setLayout(new GridLayout());
	panel.setVisible(true);
	view.revalidate();
	view.repaint();
	panel.revalidate();
	panel.repaint();
	
	
}
public void cityViewText(String cityName){
	for(City c:this.game.getPlayer().getControlledCities()){
		if(c.getName().toLowerCase()==cityName.toLowerCase()){
			
		}
	}
	
}
public void displayMarketView(){
	view.getContentPane().removeAll();
	JPanel panel = new JPanel();
	this.multiPanel();
	view.add(panel,BorderLayout.CENTER);
	panel.setVisible(true);
	panel.revalidate();
	panel.repaint();
	view.revalidate();
	view.repaint();
;	panel.setLayout(new GridLayout(0,3));
	for(City c:this.game.getPlayer().getControlledCities()){
		String s = c.getName();
		if(s=="Sparta"){
			JButton buildMarket= new JButton("SPARTA BUILD MARKET"); 
			buildMarket.addActionListener(this);
			this.btnsMarket.add(buildMarket);
			JButton upgadeMarket= new JButton("SPARTA UPGRADE MARKET");
			upgadeMarket.addActionListener(this);
			this.btnsMarket.add(upgadeMarket);
			JButton buildFarm= new JButton("SPARTA BUILD FARM");
			buildFarm.addActionListener(this);
			this.btnsMarket.add(buildFarm);
			JButton upgadeFarm= new JButton("SPARTA UPGRADE FARM");
			upgadeFarm.addActionListener(this);
			this.btnsMarket.add(upgadeFarm);
			JButton buildStable= new JButton("SPARTA BUILD STABLE");
			buildStable.addActionListener(this);
			this.btnsMarket.add(buildStable);
			JButton upgradeStable= new JButton("SPARTA UPGRADE STABLE");
			upgradeStable.addActionListener(this);
			this.btnsMarket.add(upgradeStable);
			JButton buildArcheryRange= new JButton("SPARTA BUILD ARCHERYRANGE");
			buildArcheryRange.addActionListener(this);
			this.btnsMarket.add(buildArcheryRange);
			JButton upgradeArcheryRange= new JButton("SPARTA UPGRADE ARCHERYRANGE");
			upgradeArcheryRange.addActionListener(this);
			this.btnsMarket.add(upgradeArcheryRange);
			JButton buildBarracks= new JButton("SPARTA BUILD BARRACKS");
			buildBarracks.addActionListener(this);
			this.btnsMarket.add(buildBarracks);
			JButton upgradeBarracks= new JButton("SPARTA UPGRADE BARRACKS");
			upgradeBarracks.addActionListener(this);
			this.btnsMarket.add(upgradeBarracks);
			view.revalidate();
			view.repaint();
			panel.revalidate();
			panel.repaint();
			
		}
		else if(s=="Cairo"){
			JButton buildMarket= new JButton("Cairo BUILD MARKET"); 
			buildMarket.addActionListener(this);
			this.btnsMarket.add(buildMarket);
			JButton upgadeMarket= new JButton("Cairo UPGRADE MARKET");
			upgadeMarket.addActionListener(this);
			this.btnsMarket.add(upgadeMarket);
			JButton buildFarm= new JButton("Cairo BUILD FARM");
			buildFarm.addActionListener(this);
			this.btnsMarket.add(buildFarm);
			JButton upgadeFarm= new JButton("Cairo UPGRADE FARM");
			upgadeFarm.addActionListener(this);
			this.btnsMarket.add(upgadeFarm);
			JButton buildStable= new JButton("Cairo BUILD STABLE");
			buildStable.addActionListener(this);
			this.btnsMarket.add(buildStable);
			JButton upgradeStable= new JButton("Cairo UPGRADE STABLE");
			upgradeStable.addActionListener(this);
			this.btnsMarket.add(upgradeStable);
			JButton buildArcheryRange= new JButton("Cairo BUILD ARCHERYRANGE");
			buildArcheryRange.addActionListener(this);
			this.btnsMarket.add(buildArcheryRange);
			JButton upgradeArcheryRange= new JButton("Cairo UPGRADE ARCHERYRANGE");
			upgradeArcheryRange.addActionListener(this);
			this.btnsMarket.add(upgradeArcheryRange);
			JButton buildBarracks= new JButton("Cairo BUILD BARRACKS");
			buildBarracks.addActionListener(this);
			this.btnsMarket.add(buildBarracks);
			JButton upgradeBarracks= new JButton("Cairo UPGRADE BARRACKS");
			upgradeBarracks.addActionListener(this);
			this.btnsMarket.add(upgradeBarracks);
			view.revalidate();
			view.repaint();
			panel.revalidate();
			panel.repaint();
		}
		else if(s=="Rome"){
			JButton buildMarket= new JButton("Rome BUILD MARKET"); 
			buildMarket.addActionListener(this);
			this.btnsMarket.add(buildMarket);
			JButton upgadeMarket= new JButton("Rome UPGRADE MARKET");
			upgadeMarket.addActionListener(this);
			this.btnsMarket.add(upgadeMarket);
			JButton buildFarm= new JButton("Rome BUILD FARM");
			buildFarm.addActionListener(this);
			this.btnsMarket.add(buildFarm);
			JButton upgadeFarm= new JButton("Rome UPGRADE FARM");
			upgadeFarm.addActionListener(this);
			this.btnsMarket.add(upgadeFarm);
			JButton buildStable= new JButton("Rome BUILD STABLE");
			buildStable.addActionListener(this);
			this.btnsMarket.add(buildStable);
			JButton upgradeStable= new JButton("Rome UPGRADE STABLE");
			upgradeStable.addActionListener(this);
			this.btnsMarket.add(upgradeStable);
			JButton buildArcheryRange= new JButton("Rome BUILD ARCHERYRANGE");
			buildArcheryRange.addActionListener(this);
			this.btnsMarket.add(buildArcheryRange);
			JButton upgradeArcheryRange= new JButton("Rome UPGRADE ARCHERYRANGE");
			upgradeArcheryRange.addActionListener(this);
			this.btnsMarket.add(upgradeArcheryRange);
			JButton buildBarracks= new JButton("Rome BUILD BARRACKS");
			buildBarracks.addActionListener(this);
			this.btnsMarket.add(buildBarracks);
			JButton upgradeBarracks= new JButton("Rome UPGRADE BARRACKS");
			upgradeBarracks.addActionListener(this);
			this.btnsMarket.add(upgradeBarracks);
			panel.revalidate();
			panel.repaint();
			view.revalidate();
			view.repaint();
		}
		
		
	}
	for(JButton btn:this.btnsMarket)
		panel.add(btn);
	view.revalidate();
	view.repaint();
	panel.revalidate();
	panel.repaint();
}

@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	JButton btn = (JButton) (e.getSource());
	
		if(btn.getActionCommand().equals("Start Game")&&!this.nameField.getText().isEmpty())
			this.displayAvailableCitiesView();
		else if(btn==this.chooseCityCairo){
			this.cityName1 = "Cairo";
			this.playerName = this.nameField.getText();
			this.initiateGame();
			this.displayWorldMapView();
			}
		else if(btn==this.chooseCityRome){
			this.playerName = this.nameField.getText();
			this.cityName1 = "Rome";
			this.initiateGame();
			this.displayWorldMapView();
		}
		else if(btn==this.chooseCitySparta){
			this.playerName = this.nameField.getText();
			this.cityName1 = "Sparta";
			this.initiateGame();
			this.displayWorldMapView();
		}
		
		else if(e.getSource()==this.btnPlayerCities)
			this.displayControlledCities();
		else if(btn==this.btnMainMenu)
			this.displayWorldMapView();
		else if(this.btnsControlledCities.contains(btn))
			this.displayCityView(btn.getActionCommand());
		else if(btn==this.btnEndTurn){
			this.game.endTurn();
			view.revalidate();
			view.repaint();
			if(this.game.getCurrentTurnCount()==50){
				JOptionPane.showMessageDialog(null,"Game over you ran out of turns","", JOptionPane.PLAIN_MESSAGE);
				this.initiateGame();
				this.displayStartGameWindow();

				
			}
			
		}
		else if(btn==this.btnMarket){
			this.displayMarketView();
		}
		else if(this.btnsUncontroledCities.contains(btn)){
			if(this.game.getPlayer().getControlledArmies().size()==0)
				JOptionPane.showMessageDialog(null,"You have no army initiate one!!","", JOptionPane.PLAIN_MESSAGE);
			
			}
		
			
			
			
		
	
		
	
}






public static void main(String[]args){
	GameController controller = new GameController();

}


}
