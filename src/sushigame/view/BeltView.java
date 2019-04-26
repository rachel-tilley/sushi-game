package sushigame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.StringJoiner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import comp401sushi.Ingredient;
import comp401sushi.IngredientPortion;
import comp401sushi.Plate;
import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;

public class BeltView extends JPanel implements BeltObserver, MouseListener {

	private Belt belt;
	private JButton[] belt_labels;

	public BeltView(Belt b) {
		this.belt = b;
		belt.registerBeltObserver(this);
		setLayout(new GridLayout(belt.getSize(), 1));
		belt_labels = new JButton[belt.getSize()];
		for (int i = 0; i < belt.getSize(); i++) {
			JButton button = new JButton();
			button.setText("No Plate");
			button.addMouseListener(this);
			add(button);
			belt_labels[i] = button;
		}
		refresh();
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {	
		refresh();
	}

	private void refresh() {
		for (int i=0; i<belt.getSize(); i++) {
			Plate p = belt.getPlateAtPosition(i);
			JButton button = belt_labels[i];

			if (p == null) {
				button.setText("No Plate");
				button.setBackground(Color.GRAY);
			} else {
				button.setText(belt.getPlateAtPosition(i).getContents().getName());
				switch (p.getColor()) {
				case RED:
					button.setBackground(Color.RED); break;
				case GREEN:
					button.setBackground(Color.GREEN); break;
				case BLUE:
					button.setBackground(Color.BLUE); break;
				case GOLD:
					button.setBackground(Color.YELLOW); break;
				}
				
				 
				}
				
			
			}
		}
	
	public void mousePressed(MouseEvent e) {
		int b = 0;
		
		for (int i = 0; i < belt_labels.length; i++) {
			if(belt_labels[i].equals( e.getSource())) {
				b = i;
			}
		}
		
			String name = belt.getPlateAtPosition(b).getContents().getName();
			String chefName = belt.getPlateAtPosition(b).getChef().getName();
			int plateAge = belt.getAgeOfPlateAtPosition(b);
			int position = b;
			// prints ingredients if a roll
			if (!name.contains("sashimi") || !name.contains("nigiri")) {
				
				// convert ingredient array to string
				IngredientPortion[] ingredients = belt.getPlateAtPosition(position).getContents().getIngredients();
				StringJoiner ingredString = new StringJoiner(" ");
				for (int z = 0; z < ingredients.length; z++) {
					ingredString.add(ingredients[z].getName());
					
				}
				
				String ingred = ingredString.toString();
				JOptionPane.showMessageDialog(null, "name: " + name + "\nchef name: " +
				chefName + "\ningredients: " + ingred+ "\nplate age: " + plateAge, "information", JOptionPane.PLAIN_MESSAGE);
				
				
			// doesn't print ingredients for sashimi and nigiri
			} else {
				JOptionPane.showMessageDialog(null, "name: " + name + "\nchef name: " +
				chefName + "\nplate age: " + plateAge, "information", JOptionPane.PLAIN_MESSAGE);
			
			}
		}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// 
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// 
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// 
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// 
		
	}
		
}
	

	
	
	
