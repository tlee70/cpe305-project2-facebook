package profile;

/**
 * Observer in ObserverPattern for updating view whenever friends list updates
 * @author Tim
 *
 */
public interface FriendsListObserver {
	/**
	 * updates the entire friends list from scratch
	 */
	public void updateFriendsList();
	
	/**
	 * updates the friends list with the addition of the specified AccountModel
	 * @param acc the AccountModel to add
	 */
	public void notifyFriendAdd(AccountModel acc);
	
	/**
	 * updates the friends list with the removal of the specified AccountModel
	 * @param acc the AccountModel to remove
	 */
	public void notifyFriendRemove(AccountModel acc);
}
