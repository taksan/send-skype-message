package taksan;

import com.skype.Friend;
import com.skype.Skype;
import com.skype.SkypeException;

public class PrintContactList implements Runnable {

	@Override
	public void run() {
		try {
			Friend[] allFriends = Skype.getContactList().getAllFriends();
			for (Friend friend : allFriends) {
				String fullName = friend.getFullName();
				if (fullName.isEmpty())
					fullName = friend.getId();
				System.out.println(String.format("%-35s |  %s",friend.getId(),fullName));
			}
		} catch (SkypeException e) {
			throw new FailedDueToSkypeException();
		}
	}

}
