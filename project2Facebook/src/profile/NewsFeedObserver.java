package profile;

/**
 * ObserverPattern used to notify observers when their subject's news feed changes
 * @author Tim
 *
 */
public interface NewsFeedObserver {
	/*
	 * Updates Observers with the given String from the news feed
	 */
	public void updateFeed(String message);
}
