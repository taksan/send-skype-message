package taksan;

import com.skype.Chat;
import com.skype.Skype;
import com.skype.SkypeException;


public class SendMessageToChat implements Runnable {

	private final String chatTitle;
	private final String message;

	public SendMessageToChat(String chatTitle, String message) {
		this.chatTitle = chatTitle;
		this.message = message;
	}

	public void run() {
		try {
			Chat target = getTargetChat();
			if (target ==null) {
				throw new IllegalArgumentException("Chat " + chatTitle + "not found.");
			}
			target.send(message);
			
		} catch (SkypeException e) {
			throw new FailedDueToSkypeException();
		}
	}

	private Chat getTargetChat() throws SkypeException {
		Chat[] chats = Skype.getAllChats();
		for (Chat chat : chats) {
			if (chat.getWindowTitle().trim().equalsIgnoreCase(chatTitle)) {
				return chat;
			}
		}
		return null;
	}

}
