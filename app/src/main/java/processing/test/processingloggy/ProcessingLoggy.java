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
  SerialCommunicator serial;

  public void startSerial() {
  }

  public void setup() {
    log = new Log(this);
    log.append("hello");
    log.append("long long long long the quick brown fox jumps over the lazy dog");

    serial = new SerialCommunicator(log);

    serial.OpenConnection(this);


  }

  public void draw() {
    delay(10000);
    background(128);
    log.draw(this);

    byte buffer[] = { 'p', 'i', 'n', 'g' };
    int wroteBytes = serial.WriteBytes(buffer);
    log.append("wrote " + wroteBytes + " to serial");

    int readBytes = serial.ReadBytes(buffer);
    log.append("read " + readBytes + " bytes (" + new String(buffer) + ") from serial");

  }

  public void settings() {
    size(1200, 1600);
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
