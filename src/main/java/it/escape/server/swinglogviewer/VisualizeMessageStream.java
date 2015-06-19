package it.escape.server.swinglogviewer;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class VisualizeMessageStream extends OutputStream {

	private JTextArea textArea;
	
	public VisualizeMessageStream(JTextArea textArea) {
		super();
		this.textArea = textArea;
	}

	/**
	 * Writes a single byte to our text area
	 */
	@Override
	public void write(int b) throws IOException {
		textArea.append(String.valueOf((char)b));
        // scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
	}

	@Override
	public void write(byte[] bytes, int off, int len) throws IOException {
		for (int pos = off; pos < len; pos++) {
			write((int) bytes[pos]);
		}
	}


}
