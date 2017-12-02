package profile;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;

public class StrangerProfileView extends AbstractProfileView {
	
	private JButton followBtn = new JButton("Follow");
	
	public StrangerProfileView() {
		super();
		
		displayFollowBtn();
		
		this.setContentPane(content);
		this.pack();
		
		this.setTitle("Facebook");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void displayFollowBtn() {
		GridBagConstraints followConstraints = new GridBagConstraints();
		followConstraints.gridx = 0;
		followConstraints.gridy = 1;
		followConstraints.weightx = 0;
		followConstraints.weighty = 0;
		followConstraints.anchor = GridBagConstraints.PAGE_START;
		followConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		content.add(followBtn, followConstraints);
	}
	
	public void setFollowListener(ActionListener listener) {
		followBtn.addActionListener(listener);
	}
}
