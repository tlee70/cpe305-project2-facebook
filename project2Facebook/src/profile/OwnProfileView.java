package profile;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

import java.util.List;
import java.util.Iterator;

public class OwnProfileView extends AbstractProfileView {

	private static final Dimension FEED_SIZE = new Dimension(100,150);
	private static final Dimension FRIENDS_SIZE = new Dimension(150,200);
	
	private DefaultComboBoxModel<AccountModel> friendsModel;
	private JTextArea wallArea;
	private JTextField wallField = new JTextField(20);
	private JButton postBtn = new JButton("Post");
	private JTextArea feedArea;
	private JList<AccountModel> friendsJList;
	private JButton friendPostBtn = new JButton("Friend Post");
	
	public OwnProfileView() {
		super();
		
		friendsModel = new DefaultComboBoxModel<AccountModel>();

		displayWall();
		displayFeed();
		displayFriends();
		displayFriendPostBtn();
		
		this.setContentPane(content);
		this.pack();
		
		this.setTitle("Facebook");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		friendsJList = new JList<AccountModel>(friendsModel);
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
		buttonConstraints.anchor = GridBagConstraints.CENTER;
		buttonConstraints.weightx = 0;
		buttonConstraints.weighty = 0;
		content.add(friendPostBtn, buttonConstraints);
	}
	
	public void setFriendsList(List<AccountModel> friendsList) {
		Iterator<AccountModel> iterator = friendsList.iterator();
		while (iterator.hasNext()) {
			friendsModel.addElement(iterator.next());
		}
	}
	
	public void addWallListener(ActionListener listener) {
		postBtn.addActionListener(listener);
		wallField.addActionListener(listener);
	}
	
	public void addFriendsListener(ListSelectionListener listener) {
		friendsJList.addListSelectionListener(listener);
	}
	
	public void addFriendPostListener(ActionListener listener) {
		friendPostBtn.addActionListener(listener);
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
	
	public DefaultComboBoxModel<AccountModel> getFriendsDefaultComboBoxModel() {
		return friendsModel;
	}
	
}
