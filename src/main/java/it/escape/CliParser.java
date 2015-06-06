package it.escape;

import it.escape.strings.StringRes;
import it.escape.utils.LogHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Simple Command line arguments parser
 * @author michele
 *
 */
public class CliParser {
	
	protected static final Logger LOG = Logger.getLogger( CliParser.class.getName() );
	
	private List<String> rawOptions;
	
	private GlobalSettings globals;
	
	// options in the format "-key parameter"
	private List<String> doubleOptions = Arrays.asList(
			StringRes.getString("cliparser.option.long.gmtimeout"),
			StringRes.getString("cliparser.option.long.serverport"),
			StringRes.getString("cliparser.option.long.defaultserver")
			);
	
	// options in the format "-switch"
	private List<String> singleOptions = Arrays.asList(
			StringRes.getString("cliparser.option.long.textclient"),
			StringRes.getString("cliparser.option.long.textserver")
			);

	public CliParser(String[] arrayOptions) {
		LogHelper.setDefaultOptions(LOG);
		this.rawOptions = new ArrayList<String>(Arrays.asList(arrayOptions));
	}
	
	public void parse(GlobalSettings globals) {
		this.globals = globals;
		LOG.finer("Parsing command line parameters");
		parseSingles();
		parseCouples();
	}
	
	private void parseSingles() {
		List<String> copy = new ArrayList<String>(rawOptions);
		for (String s : rawOptions) {
			if (singleOptions.contains(s)) {
				processSwitch(s);
				copy.remove(s);
			}
		}
		rawOptions = copy;
	}
	
	private void processSwitch(String opt) {
		if (opt.equals(StringRes.getString("cliparser.option.long.textclient"))) {
			globals.setStartInTextClient(true);
		} else if (opt.equals(StringRes.getString("cliparser.option.long.textserver"))) {
			globals.setStartInTextServer(true);
		}
	}
	
	private void parseCouples() {
		List<String> copy = new ArrayList<String>(rawOptions);
		for (String s : rawOptions) {
			if (doubleOptions.contains(s)) {
				int next = rawOptions.indexOf(s) + 1;
				if (rawOptions.size() > next) {
					String value = rawOptions.get(next);
					processKeyValue(s, value);
					copy.remove(s);
					copy.remove(value);
				}
			}
		}
		rawOptions = copy;
	}
	
	private void processKeyValue(String key, String value) {
		if (key.equals(StringRes.getString("cliparser.option.long.gmtimeout"))) {
			globals.setGameMasterTimeout(Integer.parseInt(value));
		} else if (key.equals(StringRes.getString("cliparser.option.long.serverport"))) {
			globals.setServerPort(Integer.parseInt(value));
		} else if (key.equals(StringRes.getString("cliparser.option.long.defaultserver"))) {
			globals.setDestinationServerAddress(value);
		}
	}
}
