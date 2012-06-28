package taksan;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class SendSkypeMessageMain {
	public static void main(String[] args) {
		System.setProperty("skype.api.impl", "dbus");
		CommandLine cmd = null;
		try {
			cmd = parseCommandLine(args);
		} catch (ParseException e) {
			printAndExitHelp();
		}
		
		Runnable c = getCommandToPerform(cmd);
		try {
			c.run();
		}catch(IllegalArgumentException|IllegalStateException e) {
			System.out.println(e.getMessage());
		}
	}

	private static Runnable getCommandToPerform(CommandLine cmd) {
		String message = cmd.getOptionValue("m");
		if (cmd.hasOption("u")) {
			String user = cmd.getOptionValue("u");
			return new SendMessageToUser(user, message);
		}
		if (cmd.hasOption("c")) {
			String user = cmd.getOptionValue("c");
			return new SendMessageToChat(user, message);
		}
		throw new IllegalArgumentException("Missing either user or chat title");
	}

	private static void printAndExitHelp() {
		HelpFormatter helpFormatter = new HelpFormatter();
		String syntax = "javar -jar send-skype-message-1.0-SNAPSHOT.jar (-u <user>| -c <chat title>) -m <message>";
		helpFormatter.printHelp(syntax, getOptions());
		System.exit(-1);
	}

	private static CommandLine parseCommandLine(String[] args) throws ParseException {
		CommandLine cmd = null;
		CommandLineParser parser = new PosixParser();
		Options options = getOptions();
		
		cmd = parser.parse( options, args);
		
		return cmd;
	}

	private static Options getOptions() {
		Options options = new Options();
		OptionGroup destiny = new OptionGroup()
				.addOption(new Option("u", true, "send to user name"))
				.addOption(new Option("c", true, "send to chat with given window title"));
				
		options.addOptionGroup(destiny);
		options.addOption(makeMandatoryOption("m", true, "message to send"));
		return options;
	}

	private static Option makeMandatoryOption(String opt, boolean hasArg, String description) {
		Option message = new Option(opt, hasArg, description);
		message.setRequired(true);
		return message;
	}
}
