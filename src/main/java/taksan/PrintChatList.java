package taksan;

import com.skype.Chat;
import com.skype.Skype;
import com.skype.SkypeException;

public class PrintChatList implements Runnable {

	@Override
	public void run() {
		try {
			Chat[] allRecentChats = Skype.getAllRecentChats();
			for (Chat chat : allRecentChats) {
				System.out.println(chat.getWindowTitle());
			}
		} catch (SkypeException e) {
			throw new FailedDueToSkypeException();
		}
	}

}
