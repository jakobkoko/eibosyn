package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ToneList implements Iterable<Tone>{
	
	private List<Tone> list;
	private int listIndex;
	
	public ToneList() {
		this.list = new ArrayList<Tone>();
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
	
	public Tone getCurrentTone() {
		return list.get(listIndex);
	}

	public List<Tone> getList() {
		return this.list;
	}
	
	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
