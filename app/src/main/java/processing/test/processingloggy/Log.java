package processing.test.processingloggy;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;

/**
 * Created by damian on 08/08/15.
 */
public class Log {
    static final int FONT_SIZE = 46;
    static final int MAX_LOG_LENGTH = 20;

    ArrayList<String> log = new ArrayList<String>();
    PFont font;

    public Log(PApplet applet) {
        font = applet.createFont("Helvetica", FONT_SIZE, true);
    }

    public void append(String message) {
        log.add(message);

        while (log.size() > MAX_LOG_LENGTH) {
            log.remove(0);
        }
    }

    public void draw(PApplet applet) {
        applet.textFont(font);
        int y = FONT_SIZE + 5;
        int lineHeight = 20;
        for (int i = 0; i < log.size(); i++) {
            applet.text(log.get(i), 10, y);
            y += lineHeight;
        }
    }
}
