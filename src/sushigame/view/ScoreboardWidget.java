package sushigame.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;
import sushigame.model.Chef;
import sushigame.model.SushiGameModel;

public class ScoreboardWidget extends JPanel implements BeltObserver, ActionListener {

	private SushiGameModel game_model;
	private JLabel display;
	private boolean balance;
	private boolean spoiled;
	private boolean consumed;
	private JComboBox<String> scoreBox;

	public ScoreboardWidget(SushiGameModel gm) {
		game_model = gm;
		game_model.getBelt().registerBeltObserver(this);
		
		spoiled = false;
		consumed = false;
		balance = false;
		
		
		display = new JLabel();
		display.setVerticalAlignment(SwingConstants.TOP);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel score = new JLabel("How would you like the scoreboard to be sorted?");
		add(score);
		String[] scoreOptions = new String[]{"Current balance", "Food spoiled", "Food consumed"};
		scoreBox = new JComboBox<>(scoreOptions);
		scoreBox.setMaximumSize( scoreBox.getPreferredSize() );
		add(scoreBox);
		scoreBox.addActionListener(this);
		
		add(display);
		display.setText(makeScoreboardHTML());
	}

	private String makeScoreboardHTML() {
		String sb_html = "<html>";
		sb_html += "<h1>Scoreboard</h1>";
		


		// Create an array of all chefs and sort by balance.
		Chef[] opponent_chefs= game_model.getOpponentChefs();
		Chef[] chefs = new Chef[opponent_chefs.length+1];
		chefs[0] = game_model.getPlayerChef();
		for (int i=1; i<chefs.length; i++) {
			chefs[i] = opponent_chefs[i-1];
		}
		if (spoiled == true) {
			Arrays.sort(chefs, new HighToLowSpoiled());
		} else if (consumed == true) {
			Arrays.sort(chefs, new HighToLowConsumed());
		} else {
			Arrays.sort(chefs, new HighToLowBalanceComparator());
		}
	
		
		if (spoiled == true) {
			for (Chef c : chefs) {
				sb_html += c.getName() + " (" + Math.round(c.getSpoiledWeight()*100.0)/100.0 + ") oz <br>";
			}
			
		} else if (consumed == true) {
			for (Chef c : chefs) {
				sb_html += c.getName() + " (" + Math.round(c.getConsumedWeight()*100.0)/100.0 + ") oz <br>";
			}
			
		} else {
			for (Chef c : chefs) {
				sb_html += c.getName() + " (" + Math.round(c.getBalance()*100.0)/100.0 + ") <br>";
			}
			
		}
		return sb_html;
	}

	public void refresh() {
		display.setText(makeScoreboardHTML());		
	}
	
	@Override
	public void handleBeltEvent(BeltEvent e) {
		if (e.getType() == BeltEvent.EventType.ROTATE) {
			refresh();
		}		
	}
	
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> namesel = (JComboBox<String>) e.getSource();
        String name = (String) namesel.getSelectedItem();
        
        if (name.equals("Current balance")) {
        	balance = true;
        	spoiled = false;
        	consumed = false;
      
        } else if(name.equals("Food spoiled")) {
        	spoiled = true;
        	balance = false;
        	consumed = false;
 
        } else if (name.equals("Food consumed")) {
        	consumed = true;
        	balance = false;
        	spoiled = false;
        	
        }
        
        
	
	}

}
