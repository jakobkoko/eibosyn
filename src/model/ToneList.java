package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ddf.minim.ugens.Frequency;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a list of tones
 * @author Nick Hafkemeyer, Jakob Kohlhas, Paul Schade
 * 
 */
public class ToneList implements Iterable<Tone>{

	private ObservableList<Tone> tones;
	private List<Tone> list;
	private int listIndex;
	
	/**
	 * Creates a default instance of ToneList
	 */
	public ToneList() {
		this.list = new ArrayList<Tone>();
		tones = FXCollections.observableArrayList(list);
		listIndex = 0;
	}
	
	/**
	 * Adds a tone to the list
	 * @param t
	 */
	public void addTone(Tone t) {
		list.add(t);
	}
	
	/**
	 * Removes a tone from the list
	 * @param t
	 */
	public void removeTone(Tone t) {
		list.remove(t);
	}
	
	/**
	 * Returns the next Tone in the list 
	 * and moves the index cursor one step forward
	 * @return
	 */
	public Tone getNextTone() {
		listIndex++;
		if (listIndex >= list.size()) {
			listIndex = 0;
			return list.get(listIndex);
		}
		return list.get(listIndex);
	}
	
	/**
	 *  Returns the previous Tone in the list
	 *  and moves the index cursor one step backwards
	 * @return
	 */
	public Tone getPreviousTone() {
		listIndex--;
		if (listIndex < 0) {
			listIndex = list.size();
			return list.get(listIndex);
		}
		return list.get(listIndex);
	}

	/**
	 * Sets the frequency of the tone at the given index to the given String value
	 * @param index
	 * @param note
	 */
	public void setTone(int index, String note) {
		list.get(index).setFrequency(Frequency.ofPitch(note));
	}
	
	/**
	 * Returns the current tone the index cursor points at
	 * @return
	 */
	public Tone getCurrentTone() {
		return list.get(listIndex);
	}

	/**
	 * Returns the list of tones
	 * @return
	 */
	public List<Tone> getList() {
		return this.list;
	}

	/**
	 * Returns an observable list of tones
	 * @return
	 */
	public ObservableList<Tone> getTones() {
		return tones;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
