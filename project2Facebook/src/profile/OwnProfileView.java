package profile;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;

import java.util.List;
import java.util.Iterator;

public class OwnProfileView extends JFrame {
	
	private static final int PICTURE_WIDTH = 150;
	private static final int PICTURE_HEIGHT = 150;
	private static final Dimension WALL_SIZE = new Dimension(100,150);
	private static final Dimension FEED_SIZE = new Dimension(100,150);
	private static final Dimension FRIENDS_SIZE = new Dimension(150,200);
	
	private JPanel content;
	private JLabel namePicLbl;
	private JTextArea wallArea;
	private JTextField wallField = new JTextField(20);
	private JButton postBtn = new JButton("Post");
	private JTextArea feedArea;
	private JList<AccountModel> friendsJList;
	private JButton settingsBtn = new JButton("Settings");
	private JButton logoutBtn = new JButton("Logout");
	private JButton friendPostBtn = new JButton("Friend Post");
	// private JComboBox searchBox;
	
	public OwnProfileView() {
		content = new JPanel();
		content.setLayout(new GridBagLayout());
		displayNamePicLbl();
		displayButtons();
		displayWall();
		displayFeed();
		displayFriends();
		displayFriendPostBtn();
		
		this.setContentPane(content);
		this.pack();
		
		this.setTitle("Facebook");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	private void displayNamePicLbl() {
		namePicLbl = new JLabel("placeholder name", null, JLabel.CENTER);
		namePicLbl.setFont(new Font("Serif", Font.PLAIN, 20));
		namePicLbl.setHorizontalTextPosition(JLabel.CENTER);
		namePicLbl.setVerticalTextPosition(JLabel.BOTTOM);
		namePicLbl.setPreferredSize(new Dimension(PICTURE_WIDTH, PICTURE_HEIGHT+50));
		
		GridBagConstraints labelConstraints = new GridBagConstraints();
		labelConstraints.gridx = 0;
		labelConstraints.gridy = 0;
		labelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		labelConstraints.fill = GridBagConstraints.BOTH;
		labelConstraints.gridheight = 2;
		labelConstraints.weightx = 0;
		labelConstraints.weighty = 0;
		content.add(namePicLbl, labelConstraints);
	}
	
	private void displayButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(settingsBtn);
		buttonPanel.add(logoutBtn);
		
		GridBagConstraints buttonConstraints = new GridBagConstraints();
		buttonConstraints.gridx = 2;
		buttonConstraints.gridy = 0;
		buttonConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		buttonConstraints.weightx = 0;
		buttonConstraints.weighty = 0;
		content.add(buttonPanel, buttonConstraints);
	}
	
	private void displayWall() {
		wallArea = new JTextArea();
		wallArea.setEditable(false);
		wallArea.setLineWrap(true);
		wallArea.setWrapStyleWord(true);
		
		JScrollPane scrollWall = new JScrollPane(wallArea); 
		scrollWall.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollWall.setPreferredSize(WALL_SIZE);
		
		
		JPanel wallPanel = new JPanel();
		wallPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints wallFieldConstraints = new GridBagConstraints();
		wallFieldConstraints.gridx = 0;
		wallFieldConstraints.gridy = 0;
		wallFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
		wallFieldConstraints.weightx = 1.0;
		wallFieldConstraints.weighty = 0;
		wallPanel.add(wallField, wallFieldConstraints);
		
		GridBagConstraints postBtnConstraints = new GridBagConstraints();
		postBtnConstraints.gridx = 1;
		postBtnConstraints.gridy = 0;
		postBtnConstraints.weightx = 0.0;
		wallPanel.add(postBtn, postBtnConstraints);
		
		GridBagConstraints scrollWallConstraints = new GridBagConstraints();
		scrollWallConstraints.gridx = 0;
		scrollWallConstraints.gridy = 1;
		scrollWallConstraints.gridwidth = 2;
		scrollWallConstraints.fill = GridBagConstraints.BOTH;
		scrollWallConstraints.weightx = 1.0;
		scrollWallConstraints.weighty = 1.0;
		wallPanel.add(scrollWall, scrollWallConstraints);
		
		
		GridBagConstraints wallConstraints = new GridBagConstraints();
		wallConstraints.gridx = 1;
		wallConstraints.gridy = 1;
		wallConstraints.gridwidth = 2;
		wallConstraints.gridheight = 3;
		wallConstraints.anchor = GridBagConstraints.PAGE_START;
		wallConstraints.fill = GridBagConstraints.BOTH;
		wallConstraints.weightx = 1.0;
		wallConstraints.weighty = 1.0;
		content.add(wallPanel, wallConstraints);
	}
	
	private void displayFeed() {
		feedArea = new JTextArea(); 
		feedArea.setEditable(false);
		feedArea.setLineWrap(true);
		feedArea.setWrapStyleWord(true);
		JScrollPane scrollFeed = new JScrollPane(feedArea);
		scrollFeed.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollFeed.setPreferredSize(FEED_SIZE);
		
		JLabel feedLbl = new JLabel("News feed");
		feedLbl.setFont(new Font("Serif", Font.PLAIN, 16));
		feedLbl.setHorizontalTextPosition(JLabel.CENTER);
		
		JPanel feedPanel = new JPanel();
		feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.PAGE_AXIS));
		feedPanel.add(feedLbl);
		feedPanel.add(scrollFeed);
		
		GridBagConstraints feedC = new GridBagConstraints();
		feedC.gridx = 1;
		feedC.gridy = 4;
		feedC.gridwidth = 2;
		feedC.gridheight = 2;
		feedC.anchor = GridBagConstraints.PAGE_START;
		feedC.fill = GridBagConstraints.BOTH;
		feedC.weighty = 0.5;
		content.add(feedPanel, feedC);
	}
	
	private void displayFriends() {
		friendsJList = new JList<AccountModel>();
		friendsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		friendsJList.setLayoutOrientation(JList.VERTICAL);
		friendsJList.setVisibleRowCount(-1);
		JScrollPane scrollFriends = new JScrollPane(friendsJList);
		scrollFriends.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollFriends.setPreferredSize(FRIENDS_SIZE);
		
		GridBagConstraints friendsConstraints = new GridBagConstraints();
		friendsConstraints.gridx = 0;
		friendsConstraints.gridy = 3;
		friendsConstraints.gridwidth = 1;
		friendsConstraints.gridheight = 2;
		friendsConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		friendsConstraints.fill = GridBagConstraints.BOTH;
		friendsConstraints.weighty = 0.5;
		content.add(scrollFriends, friendsConstraints);
	}
	
	private void displayFriendPostBtn() {
		GridBagConstraints buttonConstraints = new GridBagConstraints();
		buttonConstraints.gridx = 0;
		buttonConstraints.gridy = 2;
		buttonConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		buttonConstraints.weightx = 0;
		buttonConstraints.weighty = 0;
		content.add(friendPostBtn, buttonConstraints);
	}
	
	public void setProfileName(String name) {
		namePicLbl.setText(name);
	}
	
	public void setProfilePic(Image image) {
		Image dimg = image.getScaledInstance(PICTURE_WIDTH, PICTURE_HEIGHT,
				Image.SCALE_SMOOTH);
		namePicLbl.setIcon(new ImageIcon(dimg));
	}
	
	public void setFriendsList(List<AccountModel> friendsList) {
		DefaultListModel<AccountModel> friendsListModel = new DefaultListModel<AccountModel>();
		friendsJList.setModel(friendsListModel);
		
		Iterator<AccountModel> iterator = friendsList.iterator();
		while (iterator.hasNext()) {
			friendsListModel.addElement(iterator.next());
		}
		
	}
	
	public void addSettingsListener(ActionListener sal) {
		settingsBtn.addActionListener(sal);
	}

	public void addLogoutListener(ActionListener lal) {
		logoutBtn.addActionListener(lal);
	}
	
	public void addWallListener(ActionListener wal) {
		postBtn.addActionListener(wal);
		wallField.addActionListener(wal);
	}
	
	public void addFriendsListener(ListSelectionListener fal) {
		friendsJList.addListSelectionListener(fal);
	}
	
	public void addFriendPostListener(ActionListener fpal) {
		friendPostBtn.addActionListener(fpal);
	}
	
	public String getPost() {
		String post = wallField.getText();
		wallField.setText("");
		
		return post;
	}
	
	public void setWall(String text) {
		wallArea.setText(text);
	}
	
	public void appendWall(String text) {
		wallArea.append(text + "\n");
	}
	
	public void setFeed(String text) {
		feedArea.setText(text);
	}
	
	public void appendFeed(String text) {
		feedArea.append(text + "\n");
	}
	
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	public Object[] simulateFriendPost() {
		JScrollPane scrollFriends = new JScrollPane(friendsJList);
		scrollFriends.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollFriends.setPreferredSize(new Dimension(100,75));
		JTextField field = new JTextField();
		
		Object[] message = {scrollFriends, field};
		
		int option = JOptionPane.showConfirmDialog(this, message, "Post as a friend", JOptionPane.OK_CANCEL_OPTION);
		
		if (option == JOptionPane.OK_OPTION) {
		    String text = field.getText();
		    if (text != null && text.length() > 0 ) {
		    	AccountModel friend = friendsJList.getSelectedValue();
		    	if (friend != null) {
		    		Object[] values = {friend, text};
		    		return values;
		    	}
		    }	
		} 
		
		return null;
	}
}
