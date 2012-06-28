package taksan;

public class FailedDueToSkypeException extends RuntimeException {
	private static final long serialVersionUID = 3531805026771000616L;

	public FailedDueToSkypeException() {
		super("Failed to send message. Is skype up and running?");
	}
}
