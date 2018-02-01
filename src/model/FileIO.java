package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {

	private BufferedWriter bw;
	private BufferedReader br;

	/**
	 * Creates a default instance of FileIO. 
	 * It offers you access to IO-methods for presetFiles
	 */
	public FileIO() {
		this.bw = null;
		this.br = null;
	}

	/**
	 * Saves the current status of all tones and effects in a presetFile
	 * @param file
	 * @param player
	 */
	public void saveFile(File file, Player player) {
		
		int listSize = player.getToneList().getList().size();
		
		try {
			bw = new BufferedWriter(new FileWriter(file));

			bw.write(Integer.toString(listSize));
			bw.newLine();

			for (int i = 0; i < 8; i++) {
				float freq = player.getToneList().getList().get(i).getFrequency().asHz();
				System.out.println(freq + "\n");
				bw.write("#" + i);
				bw.newLine();
				bw.write("freq " + Float.toString(freq));
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Opens up a saved presetFile
	 * @param file
	 * @param player
	 */
	public void openFile(File file, Player player) {
		try {
			this.br = new BufferedReader(new FileReader(file));
			String line;
			int toneNumber = -1;
			
			while ((line = br.readLine()) != null) {
				toneNumber = parseLine(line, toneNumber, player);
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch(IOException e) {
				
			}
		}
	}

	/**
	 * Parses one line of a presetFile
	 * @param line
	 * @param toneNumber
	 * @param player
	 * @return toneNumber
	 */
	private int parseLine(String line, int toneNumber, Player player) {
		if (toneNumber == -1) {
			line.charAt(0);
			//Set number of tones shown in sequencePane -> missing
			return ++toneNumber;
		}
		if (toneNumber >= 0 || toneNumber <= 8) {
			if (line.startsWith("#"))
				return toneNumber;
			else if (line.startsWith("freq")) {
				// Setting the frequency of the Tone at index toneNumber
				player.getToneList().getList().get(toneNumber).setFrequency(Float.parseFloat((line.substring(5, 12))));
				return ++toneNumber;
			}
			else if (line.startsWith("fx")) {
				// Loading effects still not implemented
				return toneNumber;
			}
			else {
				System.out.println("Error while parsing the file");
			}
		}
		return 0;
	}
}
