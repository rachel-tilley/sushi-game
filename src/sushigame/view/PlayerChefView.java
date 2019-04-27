package sushigame.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import comp401sushi.AvocadoPortion;
import comp401sushi.CrabPortion;
import comp401sushi.EelPortion;
import comp401sushi.Ingredient;
import comp401sushi.IngredientPortion;
import comp401sushi.Nigiri;
import comp401sushi.Plate;
import comp401sushi.Plate.Color;
import comp401sushi.RedPlate;
import comp401sushi.RicePortion;
import comp401sushi.Roll;
import comp401sushi.Sashimi;
import comp401sushi.SeaweedPortion;
import comp401sushi.ShrimpPortion;
import comp401sushi.Sushi;
import comp401sushi.TunaPortion;
import comp401sushi.YellowtailPortion;

public class PlayerChefView extends JPanel implements ActionListener, ChangeListener {

	private List<ChefViewListener> listeners;
	private Sushi userRoll;
	private int belt_size;
	private IngredientPortion[] rollIngredients;
	private int counter;
	private String name;
	private double platePrice;
	private int platePosition;

	
	public PlayerChefView(int belt_size) {
		this.belt_size = belt_size;
		listeners = new ArrayList<ChefViewListener>();
		rollIngredients = new IngredientPortion[8];

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel sushiTitle = new JLabel("Create sashimi or nigiri");
		add(sushiTitle);
		
		String[] sushiOptions = new String[]{"Crab Sashimi", "Eel Sashimi", 
			"Shrimp Sashimi", "Tuna Sashimi", "Yellowtail Sashimi", "Crab Nigiri", 
			"Eel Nigiri", "Shrimp Nigiri", "Tuna Nigiri", "Yellowtail Nigiri" };
		JComboBox<String> sushiBox = new JComboBox<>(sushiOptions);
		add(sushiBox);
		sushiBox.setActionCommand("sushi_select");
		sushiBox.addActionListener(this);
		
		JLabel rollChoices = new JLabel("Select ingredients for a roll");
		add(rollChoices);
		
		JLabel avoLabel = new JLabel("Avocado amount");
		add(avoLabel);
		JSpinner avocadoSelector = new JSpinner(new SpinnerNumberModel(0.00, 0.00, 1.50, 0.01));
		avocadoSelector.setEnabled(true);
		add(avocadoSelector);
		avocadoSelector.addChangeListener(this);  
		avocadoSelector.setName("avocado");
		
		JLabel crabLabel = new JLabel("Crab Amount");
		add(crabLabel);
		JSpinner crabSelector = new JSpinner(new SpinnerNumberModel(0.00, 0.00, 1.50, 0.01));
		crabSelector.setEnabled(true);
		add(crabSelector);
		crabSelector.addChangeListener(this); 
		crabSelector.setName("crab");
		
		JLabel eelLabel = new JLabel("Eel Amount");
		add(eelLabel);
		JSpinner eelSelector = new JSpinner(new SpinnerNumberModel(0.00, 0.00, 1.50, 0.01));
		eelSelector.setEnabled(true);
		add(eelSelector);
		eelSelector.addChangeListener(this);
		eelSelector.setName("eel");
		
		JLabel riceLabel = new JLabel("Rice Amount");
		add(riceLabel);
		JSpinner riceSelector = new JSpinner(new SpinnerNumberModel(0.00, 0.00, 1.50, 0.01));
		riceSelector.setEnabled(true);
		add(riceSelector);
		riceSelector.addChangeListener(this);  
		riceSelector.setName("rice");
	
		JLabel seaLabel = new JLabel("Seaweed Amount");
		add(seaLabel);
		JSpinner seaweedSelector = new JSpinner(new SpinnerNumberModel(0.00, 0.00, 1.50, 0.01));
		seaweedSelector.setEnabled(true);
		add(seaweedSelector);
		seaweedSelector.addChangeListener(this); 
		seaweedSelector.setName("seaweed");
		
		JLabel shrLabel = new JLabel("Shrimp Amount");
		add(shrLabel);
		JSpinner shrimpSelector = new JSpinner(new SpinnerNumberModel(0.00, 0.00, 1.50, 0.01));
		shrimpSelector.setEnabled(true);
		add(shrimpSelector);
		shrimpSelector.addChangeListener(this); 
		shrimpSelector.setName("shrimp");
		
		JLabel tunaLabel = new JLabel("Tuna Amount");
		add(tunaLabel);
		JSpinner tunaSelector = new JSpinner(new SpinnerNumberModel(0.00, 0.00, 1.50, 0.01));
		tunaSelector.setEnabled(true);
		add(tunaSelector);
		tunaSelector.addChangeListener(this); 
		tunaSelector.setName("tuna");
		
		JLabel yelLabel = new JLabel("Yellowtail Amount");
		add(yelLabel);
		JSpinner yellowtailSelector = new JSpinner(new SpinnerNumberModel(0.00, 0.00, 1.50, 0.01));
		yellowtailSelector.setEnabled(true);
		add(yellowtailSelector);
		yellowtailSelector.addChangeListener(this);  
		yellowtailSelector.setName("yellowtail");
		
		
		JLabel nameSelectLabel = new JLabel("Name your new roll");
		add(nameSelectLabel);
		
		JComboBox<String> nameBox = new JComboBox<>();
		nameBox.setEditable(true);
		add(nameBox);
		nameBox.setActionCommand("name_select");
		nameBox.addActionListener(this);
		
		JButton finishRoll = new JButton("finished with roll");
		add(finishRoll);
		finishRoll.setActionCommand("finish_roll");
		finishRoll.addActionListener(this);
		
		
		JLabel posiLabel = new JLabel("Select Position for Plate");
		add(posiLabel);
		JSpinner positionSelector = new JSpinner(new SpinnerNumberModel(0.00, 0.00, (double) belt_size, 1.00));
		add(positionSelector);
		positionSelector.addChangeListener(this);
		positionSelector.setName("posi");
		
		JLabel goldSelectLabel = new JLabel("Pick a price if you want a gold plate");
		add(goldSelectLabel);
		
		JSpinner priceSelector = new JSpinner(new SpinnerNumberModel(5.00, 5.00, 10.00, 0.01));
		add(priceSelector);
		priceSelector.addChangeListener(this);
		priceSelector.setName("goldPlatePrice");
		
		JLabel colorSelector = new JLabel("Pick a color plate");
		add(colorSelector);
		
		Color[] plateOptions = new Color[] {Color.RED, Color.GREEN, Color.BLUE, Color.GOLD};
		JComboBox<Color> plateBox = new JComboBox<>(plateOptions);
		add(plateBox);
		plateBox.setActionCommand("plate_select");
		plateBox.addActionListener(this);
	

		
	}

	public void registerChefListener(ChefViewListener cl) {
		listeners.add(cl);
	}

	private void makeRedPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleRedPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGreenPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleGreenPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeBluePlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleBluePlateRequest(plate_sushi, plate_position);
		}
	}
	
	private void makeGoldPlateRequest(Sushi plate_sushi, int plate_position, double price) {
		for (ChefViewListener l : listeners) {
			l.handleGoldPlateRequest(plate_sushi, plate_position, price);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "sushi_select":
			JComboBox<String> sushisel = (JComboBox<String>) e.getSource();
	        String selectedSushi = (String) sushisel.getSelectedItem();
	        sushiSelect(selectedSushi);
			break;
		case "plate_select":
			JComboBox<Color> platesel = (JComboBox<Color>) e.getSource();
	        Color selectedPlate = (Color) platesel.getSelectedItem();
	        plateSelect(selectedPlate);
			break;
		case "sashimi_select":
			JComboBox<Sashimi> sashimi = (JComboBox<Sashimi>) e.getSource();
	        userRoll = (Sushi) sashimi.getSelectedItem();
			break;
		case "nigiri_select":
			JComboBox<Nigiri> nigiri = (JComboBox<Nigiri>) e.getSource();
	        userRoll = (Sushi) nigiri.getSelectedItem();
			break;
		case "price_select":
			platePrice = (double) e.getSource();
			break;
		case "name_select":
			JComboBox<String> namesel = (JComboBox<String>) e.getSource();
	        name = (String) namesel.getSelectedItem();
	        break;
		case "finish_roll":
			// count to see which parts off rollingredient aren't null
			for (int i = 0; i < rollIngredients.length; i++) {
				if (rollIngredients[i] != null) {
					counter++;
				}
			}
			
			//create new ingredientportion arrray without null values
			IngredientPortion[] finalIngred = new IngredientPortion[counter];
			int counter2 = 0;
			for (int i = 0; i < rollIngredients.length; i++) {
				if (rollIngredients[i] != null) {
					finalIngred[counter2] = rollIngredients[i];
					counter2++;
				}
			}
			userRoll = new Roll(name, finalIngred);
			
		}
		
		
	}
	
	private void sushiSelect(String sushi) {
		if (sushi.equals("Crab Sashimi")) {
			userRoll = new Sashimi(Sashimi.SashimiType.CRAB);
		} else if (sushi.equals("Eel Sashimi")) {
			userRoll = new Sashimi(Sashimi.SashimiType.EEL);
		} else if (sushi.equals("Shrimp Sashimi")) {
			userRoll = new Sashimi(Sashimi.SashimiType.SHRIMP);
		} else if (sushi.equals("Tuna Sashimi")) {
			userRoll = new Sashimi(Sashimi.SashimiType.TUNA);
		} else if (sushi.equals("Yellowtail Sashimi")) {
			userRoll = new Sashimi(Sashimi.SashimiType.YELLOWTAIL);
		} else if (sushi.equals("Crab Nigiri")) {
			userRoll = new Nigiri(Nigiri.NigiriType.CRAB);
		} else if (sushi.equals("Eel Nigiri")) {
			userRoll = new Nigiri(Nigiri.NigiriType.EEL);
		} else if (sushi.equals("Shrimp Nigiri")) {
			userRoll = new Nigiri(Nigiri.NigiriType.SHRIMP);
		} else if (sushi.equals("Tuna Nigiri")) {
			userRoll = new Nigiri(Nigiri.NigiriType.TUNA);
		} else if (sushi.equals("Yellowtail Nigiri")) {
			userRoll = new Nigiri(Nigiri.NigiriType.YELLOWTAIL);
		}
		
	}
	
	private void plateSelect(Color color) {
		switch(color) {
		case RED:
			makeRedPlateRequest(userRoll, platePosition);
			break;
		case GREEN:
			makeGreenPlateRequest(userRoll, platePosition);
			break;
		case BLUE:
			makeBluePlateRequest(userRoll, platePosition);
			break;
		case GOLD:
			makeGoldPlateRequest(userRoll, platePosition, platePrice);
			break;
		
		}
	}
	  
	public void stateChanged(ChangeEvent e) {
		 JSpinner spinner = (JSpinner) e.getSource();
         double value = (double)spinner.getValue();
         
         
         if (spinner.getName().equals("avocado")) {
        	if (rollIngredients[0] == null) {
        		rollIngredients[0] = new AvocadoPortion(value);
        	} else {
        		rollIngredients[0].combine(new AvocadoPortion(value));	
        	}
        	
 
         } else if (spinner.getName().equals("crab")) {
        	 if (rollIngredients[1] == null) {
         		rollIngredients[1] = new CrabPortion(value);
         	} else {
         		rollIngredients[1].combine(new CrabPortion(value));	
         	}

         } else if (spinner.getName().equals("eel")) {
        	 if (rollIngredients[2] == null) {
         		rollIngredients[2] = new EelPortion(value);
         	} else {
         		rollIngredients[2].combine(new EelPortion(value));	
         	}

         } else if (spinner.getName().equals("rice")) {
        	 if (rollIngredients[3] == null) {
         		rollIngredients[3] = new RicePortion(value);
         	} else {
         		rollIngredients[3].combine(new RicePortion(value));	
         	}

         } else if (spinner.getName().equals("seaweed")) {
        	 if (rollIngredients[4] == null) {
         		rollIngredients[4] = new SeaweedPortion(value);
         	} else {
         		rollIngredients[4].combine(new SeaweedPortion(value));	
         	}

         } else if (spinner.getName().equals("shrimp")) {
        	 if (rollIngredients[5] == null) {
         		rollIngredients[5] = new ShrimpPortion(value);
         	} else {
         		rollIngredients[5].combine(new ShrimpPortion(value));	
         	}
        	
         } else if (spinner.getName().equals("tuna")) {
        	 if (rollIngredients[6] == null) {
         		rollIngredients[6] = new TunaPortion(value);
         	} else {
         		rollIngredients[6].combine(new TunaPortion(value));	
         	}
   
         } else if (spinner.getName().equals("yellowtail")) {
        	 if (rollIngredients[7] == null) {
         		rollIngredients[7] = new YellowtailPortion(value);
         	} else {
         		rollIngredients[7].combine(new YellowtailPortion(value));	
         	}
        
         } else if (spinner.getName().equals("goldPlatePrice")) {
        	 platePrice = value;
         } else if (spinner.getName().equals("posi")) {
        	 platePosition = (int) Math.round(value);
         }
        	 
	}
}
