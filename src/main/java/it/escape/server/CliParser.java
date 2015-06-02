package it.escape.server;

import it.escape.strings.StringRes;
import it.escape.utils.LogHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class CliParser {
	
	protected static final Logger LOG = Logger.getLogger( CliParser.class.getName() );
	
	private List<String> rawOptions;
	
	// options in the format "-key parameter"
	private List<String> doubleOptions = Arrays.asList(
			StringRes.getString("cliparser.option.long.gmtimeout"),
			StringRes.getString("cliparser.option.long.serverport")
			);
	
	// options in the format "-switch"
	private List<String> singleOptions = Arrays.asList(
			);

	public CliParser(String[] arrayOptions) {
		LogHelper.setDefaultOptions(LOG);
		this.rawOptions = new ArrayList<String>(Arrays.asList(arrayOptions));
	}
	
	public void parse() {
		LOG.finer("Parsing command line parameters");
		parseSingles();
		parseCouples();
	}
	
	private void parseSingles() {
		for (String s : rawOptions) {
			if (singleOptions.contains(s)) {
				processSwitch(s);
				rawOptions.remove(s);
			}
		}
	}
	
	private void processSwitch(String opt) {
		
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
			GlobalSettings.setGameMasterTimeout(Integer.parseInt(value));
		} else if (key.equals(StringRes.getString("cliparser.option.long.serverport"))) {
			GlobalSettings.setServerPort(Integer.parseInt(value));
		}
	}
}
