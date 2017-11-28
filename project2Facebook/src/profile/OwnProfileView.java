package profile;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
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
		wallContent.setLayout(new FlowLayout());
		wallContent.add(wallField);
		wallContent.add(postBtn);
		wallContent.add(scrollWall);
		
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
		content.setLayout(new FlowLayout());
		content.add(namePicLbl);
		content.add(buttonContent);
		content.add(wallContent);
		content.add(scrollFeed);
		content.add(scrollFriends);
		
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
	
	public Image selectPicture() {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("~/sbh"));
		int result = fc.showOpenDialog(new JFrame());
		
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fc.getSelectedFile();
			try {
				Image image = ImageIO.read(selectedFile);
				return image;
			}
			catch (IOException exception) {
				System.out.println(exception.getStackTrace());
			}
		}
		
		showMessage("Error in select new profile picture");
		return null;
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
}