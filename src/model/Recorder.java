package model;

import ddf.minim.AudioOutput;
import ddf.minim.AudioRecorder;
import ddf.minim.Recordable;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
            recording = true;
        } else if (recording) {
            endRecord();
            recorder.save();
            recording = false;
        }
    }

    private void beginRecord() {
        recorder.beginRecord();
    }

    private void endRecord() {
        recorder.endRecord();
    }

    private void save() {
        chooser.setTitle("Save Recording");
        chooser.setInitialDirectory(new File("audioOutput/"));
        File file = chooser.showSaveDialog(null);
        if(file != null) {
            recorder.save();
        }
    }
}