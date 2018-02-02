package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ddf.minim.ugens.Frequency;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ToneList implements Iterable<Tone>{

	private ObservableList<Tone> tones;
	private List<Tone> list;
	private int listIndex;
	
	public ToneList() {
		this.list = new ArrayList<Tone>();
		tones = FXCollections.observableArrayList(list);
		listIndex = 0;
	}
	
	public void addTone(Tone t) {
		list.add(t);
	}
	
	public void removeTone(Tone t) {
		list.remove(t);
	}
	
	public Tone getNextTone() {
		listIndex++;
		if (listIndex >= list.size()) {
			listIndex = 0;
			return list.get(listIndex);
		}
		return list.get(listIndex);
	}
	
	public Tone getPreviousTone() {
		listIndex--;
		if (listIndex < 0) {
			listIndex = list.size();
			return list.get(listIndex);
		}
		return list.get(listIndex);
	}

	public void setTone(int index, String note) {
		list.get(index).setFrequency(Frequency.ofPitch(note));
	}
	
	public Tone getCurrentTone() {
		return list.get(listIndex);
	}

	public List<Tone> getList() {
		return this.list;
	}

	public ObservableList<Tone> getTones() {
		return tones;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
