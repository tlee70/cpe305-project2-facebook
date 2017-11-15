package facebook;

public class OwnProfileView implements BasicInformationObserver, MessageBoardObserver {
	
	private Account ownAccount;
	
	public OwnProfileView(Account ownAccount) {
		this.ownAccount = ownAccount;
		
		ownAccount.addBasicInformationObserver(this);
		ownAccount.addMessageWallObserver(this);
	}
	
	@Override
	public void updateMessageBoard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBasicInformation() {
		// TODO Auto-generated method stub
		
	}

}
