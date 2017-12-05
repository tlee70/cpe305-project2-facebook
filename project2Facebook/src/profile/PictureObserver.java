package profile;

/**
 * ObserverPatter used for updating interested observers when the subject's picture changes
 * @author Tim
 *
 */
public interface PictureObserver {
	/**
	 * Updated the observer with the specified picturePath
	 * @param picturePath a String representing a file path to an Image
	 */
	public void updatePic(String picturePath);

}