package profile;

/**
 * ObserverPattern has implementing classes be notified by subject when wall changes
 * @author Tim
 *
 */
public interface WallObserver {
	
	/**
	 * Tells the observer that subject's wall has been appended with the message
	 * @param message the addition to the wall
	 */
	public void updateWall(String message);
}
