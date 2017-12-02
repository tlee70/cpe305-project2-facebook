package profile;

public interface FriendsListObserver {
	public void updateFriendsList();
	
	public void notifyFriendAdd(AccountModel acc);
	
	public void notifyFriendRemove(AccountModel acc);
}
