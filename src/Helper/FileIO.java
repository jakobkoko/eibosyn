package Helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ddf.minim.ugens.Frequency;
import model.Player;
import model.Tone;
import model.ToneCol;
import view.CenterContainer;
import view.ToneButton;

public class FileIO {

	private BufferedWriter bw;
	private BufferedReader br;
	private CenterContainer center;

	/**
	 * Creates a default instance of FileIO. 
	 * It offers you access to IO-methods for presetFiles
	 */
	public FileIO(CenterContainer center) {
		this.bw = null;
		this.br = null;
		this.center = center;
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
				Frequency freq = player.getToneList().getList().get(i).getFrequency();
				System.out.println(freq + "\n");
				bw.write("#" + i);
				bw.newLine();
				bw.write("freq " + freq.asHz());
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
			
			ArrayList<ToneCol> toneSequence = center.getSequencePane().getToneSequence();
			for (int i = 0; i < toneSequence.size(); i++)  {
				ToneCol toneCol = toneSequence.get(i);
				for (ToneButton toneButton : toneCol.getToneButtons()) {
					toneCol.deselectButton(toneButton);
				}
			}
			
			while ((line = br.readLine()) != null) {
				toneNumber = parseLine(line, toneNumber, toneSequence, player);
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
	private int parseLine(String line, int toneNumber, ArrayList<ToneCol> toneSequence, Player player) {
		if (toneNumber == -1) {
			line.charAt(0);
			//Set number of tones shown in sequencePane -> missing
			return ++toneNumber;
		}
		if (toneNumber >= 0 || toneNumber <= 8) {
			if (line.startsWith("#"))
				return toneNumber;
			else if (line.startsWith("freq")) {
				ToneCol currentToneCol = toneSequence.get(toneNumber);
				Tone t = player.getToneList().getList().get(toneNumber);
				float f;
				// Setting the frequency of the Tone at index toneNumber
				switch (line.length()) {
				case 10: f = Float.parseFloat(line.substring(5, 10));
				break;
				case 11: f = Float.parseFloat(line.substring(5, 11));
				break;
				case 12: f = Float.parseFloat(line.substring(5, 12));
				break;
				case 13: f = Float.parseFloat(line.substring(5, 13));
				break;
				case 14: f = Float.parseFloat(line.substring(5, 14));
				break;
				default: System.out.println("Error while reading frequency of tone: " + toneNumber);
				System.out.println(line.length());
				return ++toneNumber;
				}
				Frequency freq = Frequency.ofHertz(f);
				t.setFrequency(freq);
				t.unmute();
				
				for (ToneButton b : currentToneCol.getToneButtons()) {
					Frequency currentFrequency = Frequency.ofPitch(b.getTone());
					
					// Hier buttonFreq auf vier Nachkommastellen kürzen!!!!!!!!
					float buttonFreq = currentFrequency.asHz();
					if(freq.asHz() == buttonFreq)
						currentToneCol.selectButton(b);
				}
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
