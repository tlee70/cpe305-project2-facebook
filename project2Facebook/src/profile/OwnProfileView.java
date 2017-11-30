package profile;

import java.awt.FlowLayout;
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

import java.util.List;
import java.util.Iterator;

public class OwnProfileView extends JFrame {
	
	private static final int PICTURE_WIDTH = 200;
	private static final int PICTURE_HEIGHT = 200;
	private static final Dimension WALL_SIZE = new Dimension(300,300);
	private static final Dimension FEED_SIZE = new Dimension(300,300);
	private static final Dimension FRIENDS_SIZE = new Dimension(150,300);
	
	private JLabel namePicLbl;
	private JTextArea wall;
	private JTextField wallField = new JTextField(20);
	private JButton postBtn = new JButton("Post");
	private JTextArea feed;
	private JList<AccountModel> friends;
	private JButton settingsBtn = new JButton("Settings");
	private JButton logoutBtn = new JButton("Logout");
	// private JComboBox searchBox;
	
	public OwnProfileView() {
		namePicLbl = new JLabel("placeholder name", null, JLabel.CENTER);
		namePicLbl.setFont(new Font("Serif", Font.PLAIN, 20));
		namePicLbl.setHorizontalTextPosition(JLabel.CENTER);
		namePicLbl.setVerticalTextPosition(JLabel.BOTTOM);
		namePicLbl.setPreferredSize(new Dimension(PICTURE_WIDTH, PICTURE_HEIGHT+50));
		
		JPanel buttonContent = new JPanel();
		buttonContent.setLayout(new FlowLayout());
		buttonContent.add(settingsBtn);
		buttonContent.add(logoutBtn);
		
		wall = new JTextArea();
		wall.setEditable(false);
		wall.setLineWrap(true);
		wall.setWrapStyleWord(true);
		JScrollPane scrollWall = new JScrollPane(wall); 
		scrollWall.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollWall.setPreferredSize(WALL_SIZE);
		JPanel wallContent = new JPanel();
		wallContent.setLayout(new GridBagLayout());
		GridBagConstraints wallFieldC = new GridBagConstraints();
		wallFieldC.gridx = 0;
		wallFieldC.gridy = 0;
		wallFieldC.fill = GridBagConstraints.HORIZONTAL;
		wallFieldC.weightx = 1.0;
		wallFieldC.weighty = 0;
		wallContent.add(wallField, wallFieldC);
		GridBagConstraints postC = new GridBagConstraints();
		postC.gridx = 1;
		postC.gridy = 0;
		postC.weightx = 0.0;
		wallContent.add(postBtn, postC);
		GridBagConstraints scrollWallC = new GridBagConstraints();
		scrollWallC.gridx = 0;
		scrollWallC.gridy = 1;
		scrollWallC.gridwidth = 2;
		scrollWallC.fill = GridBagConstraints.BOTH;
		scrollWallC.weightx = 1.0;
		scrollWallC.weighty = 1.0;
		wallContent.add(scrollWall, scrollWallC);
		
		feed = new JTextArea(); 
		feed.setEditable(false);
		feed.setLineWrap(true);
		feed.setWrapStyleWord(true);
		JScrollPane scrollFeed = new JScrollPane(feed);
		scrollFeed.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollFeed.setPreferredSize(FEED_SIZE);
		
		friends = new JList<AccountModel>();
		friends.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		friends.setLayoutOrientation(JList.VERTICAL);
		friends.setVisibleRowCount(-1);
		JScrollPane scrollFriends = new JScrollPane(friends);
		scrollFeed.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollFriends.setPreferredSize(FRIENDS_SIZE);
		
		// Search box?
		
		JPanel content = new JPanel();
		content.setLayout(new GridBagLayout());
		GridBagConstraints labelC = new GridBagConstraints();
		labelC.gridx = 0;
		labelC.gridy = 0;
		labelC.anchor = GridBagConstraints.FIRST_LINE_START;
		labelC.fill = GridBagConstraints.BOTH;
		labelC.gridheight = 2;
		labelC.weightx = 0;
		labelC.weighty = 0;
		content.add(namePicLbl, labelC);
		GridBagConstraints buttonC = new GridBagConstraints();
		buttonC.gridx = 2;
		buttonC.gridy = 0;
		buttonC.anchor = GridBagConstraints.FIRST_LINE_END;
		buttonC.weightx = 0;
		buttonC.weighty = 0;
		content.add(buttonContent, buttonC);
		GridBagConstraints wallC = new GridBagConstraints();
		wallC.gridx = 1;
		wallC.gridy = 1;
		wallC.gridwidth = 2;
		wallC.gridheight = 3;
		wallC.anchor = GridBagConstraints.PAGE_START;
		wallC.fill = GridBagConstraints.BOTH;
		wallC.weightx = 1.0;
		wallC.weighty = 1.0;
		content.add(wallContent, wallC);
		GridBagConstraints feedC = new GridBagConstraints();
		feedC.gridx = 1;
		feedC.gridy = 4;
		feedC.gridwidth = 2;
		feedC.gridheight = 2;
		feedC.anchor = GridBagConstraints.PAGE_START;
		feedC.fill = GridBagConstraints.BOTH;
		feedC.weighty = 0.5;
		content.add(scrollFeed, feedC);
		GridBagConstraints friendsC = new GridBagConstraints();
		friendsC.gridx = 0;
		friendsC.gridy = 3;
		friendsC.gridwidth = 1;
		friendsC.gridheight = 2;
		friendsC.anchor = GridBagConstraints.FIRST_LINE_START;
		friendsC.fill = GridBagConstraints.BOTH;
		friendsC.weighty = 0.5;
		content.add(scrollFriends, friendsC);
		
		this.setContentPane(content);
		this.pack();
		
		this.setTitle("Facebook");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		friends.setModel(friendsListModel);
		
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
		friends.addListSelectionListener(fal);
	}
	
	public String getPost() {
		String post = wallField.getText();
		wallField.setText("");
		
		return post;
	}
	
	public void setWall(String text) {
		wall.setText(text);
	}
	
	public void appendWall(String text) {
		wall.append(text + "\n");
	}
	
	public void setFeed(String text) {
		feed.setText(text);
	}
	
	public void appendFeed(String text) {
		feed.append(text + "\n");
	}
	
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	public void simulateFriendWall() {
		//JOptionPane
	}
}
