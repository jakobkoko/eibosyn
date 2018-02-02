package model;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import Helper.Utility;
import ddf.minim.AudioRecorder;
import ddf.minim.Recordable;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.stage.FileChooser;

/**
 * Represents a Recorder
 * @author Nick Hafkemeyer, Jakob Kohlhas, Paul Schade
 * 
 */
public class Recorder {

    private AudioRecorder recorder;
    private FileChooser chooser;
    private SimpleMinim minim;
    private Recordable output;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss_yyy-MM-dd");
    private boolean recording;

    /**
     * constructs new Recorder
     * @param o Recordable, which serves as source for the Recorder
     * @param m SimpleMinim
     */
    public Recorder(Recordable o, SimpleMinim m) {
        minim = m;
        output = o;
        recorder = m.createRecorder(o, "default.wav");
        chooser = new FileChooser();
        recording = false;
    }

    /**
     * creates new AudioRecorder and begins recording, when currently not recording.
     * Ends recording and saves the audio ele
     */
    public void toggleRecord() {
        Timestamp t = new Timestamp(System.currentTimeMillis());
        String timestamp = sdf.format(t);
        if (!recording) {
            recorder = minim.createRecorder(output, "record_" + timestamp + ".wav");
            recorder.beginRecord();
            Utility.debug("begin recording");
            recording = true;
        } else if (recording) {
            endRecord();
            Utility.debug("end recording");
            recorder.save();
            recording = false;
        }
    }

    /**
     * Starts recording
     */
    private void beginRecord() {
        recorder.beginRecord();
    }

    /**
     * Ends recording
     */
    private void endRecord() {
        recorder.endRecord();
    }

    /**
     * Saves a recorded file
     */
    private void save() {
        chooser.setTitle("Save Recording");
        chooser.setInitialDirectory(new File("audioOutput/"));
        File file = chooser.showSaveDialog(null);
        if(file != null) {
            recorder.save();
        }
    }
}