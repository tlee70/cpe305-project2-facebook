package profile;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FriendProfileView extends AbstractProfileView {
	
	private JButton unfollowBtn = new JButton("Unfollow");
	private JTextArea wallArea;
	
	
	public FriendProfileView() {
		super();
		
		displayUnfollowBtn();
		displayWall();

		this.pack();
		
		this.setTitle("Facebook");
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void displayUnfollowBtn() {
		GridBagConstraints unfollowConstraints = new GridBagConstraints();
		unfollowConstraints.gridx = 0;
		unfollowConstraints.gridy = 2;
		unfollowConstraints.weightx = 0;
		unfollowConstraints.weighty = 0;
		unfollowConstraints.anchor = GridBagConstraints.PAGE_START;
		unfollowConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		content.add(unfollowBtn, unfollowConstraints);
	}
	
	private void displayWall() {
		wallArea = new JTextArea();
		wallArea.setEditable(false);
		wallArea.setLineWrap(true);
		wallArea.setWrapStyleWord(true);
		
		JScrollPane scrollWall = new JScrollPane(wallArea); 
		scrollWall.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollWall.setPreferredSize(WALL_SIZE);
				
		GridBagConstraints wallConstraints = new GridBagConstraints();
		wallConstraints.gridx = 1;
		wallConstraints.gridy = 1;
		wallConstraints.gridwidth = 2;
		wallConstraints.gridheight = 3;
		wallConstraints.anchor = GridBagConstraints.PAGE_START;
		wallConstraints.fill = GridBagConstraints.BOTH;
		wallConstraints.weightx = 1.0;
		wallConstraints.weighty = 1.0;
		content.add(scrollWall, wallConstraints);
	}
	
	public void setUnfollowListener(ActionListener listener) {
		unfollowBtn.addActionListener(listener);
	}
	
	public void setWall(String text) {
		wallArea.setText(text);
	}
	
	public void appendWall(String text) {
		wallArea.append(text + "\n");
	}
}
