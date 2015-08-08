package processing.test.processingloggy;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException;

public class ProcessingLoggy extends PApplet {

  Log log;

  public void startSerial() {
  }

  public void setup() {
    log = new Log(this);
    log.append("hello");

  }

  public void draw() {
    log.draw(this);
  }

  public void settings() {
    size(800, 1600);
  }



  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[]{"ProcessingLoggy"};
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
