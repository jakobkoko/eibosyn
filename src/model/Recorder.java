package model;

import ddf.minim.AudioOutput;
import ddf.minim.AudioRecorder;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Recorder {

    private AudioRecorder recorder;
    FileChooser chooser;
    SimpleMinim minim;
    AudioOutput output;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss_yyy-MM-dd");

    public Recorder(AudioOutput o, SimpleMinim m) {
        minim = m;
        output = o;
        recorder = m.createRecorder(o, "default.wav");
        chooser = new FileChooser();
    }

    public void toggleRecord() {
        Timestamp t = new Timestamp(System.currentTimeMillis());
        String timestamp = sdf.format(t);
        if (!recorder.isRecording()) {
            recorder = minim.createRecorder(output, "record_" + timestamp + ".wav");
            recorder.beginRecord();
        } else if (recorder.isRecording()) {
            endRecord();
            recorder.save();
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