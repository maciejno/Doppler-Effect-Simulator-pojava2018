package pw.pojava.projekt.DopplerMain;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.SwingWorker;

import pw.pojava.projekt.DopplerMain.ObserverAnimationPanel.DataToSimulate;
import pw.pojava.projekt.DopplerMain.ObserverAnimationPanel.ObserverSwingWorker;

public class sound implements Runnable	{
	

	double freq=440;
	double volume=4200;
	final double sampleRate = 44100.0;
	SourceDataLine line; //wazna rzecz
	boolean status=false; //status odtwarzania
	ArrayList<Float> greatBuffer = new ArrayList<Float>();
    long c=0;

	AudioFormat format; //do dzwieku
	
	public  void newSound() throws LineUnavailableException {
		greatBuffer.clear();
		format=new AudioFormat(44100f,16,2,true,true); //wazne rzeczy do robienia dzwieku
	    line=AudioSystem.getSourceDataLine(format);
		line.open(format);
		new Thread(this).start();

	}
	
	public void setSound(double f) { //zmienia tylko czestotliwosc
		freq = Math.abs(f);
	}
	
		
	
	public void save(String name) {  //zapis do pliku
		final byte[] byteBuffer = new byte[greatBuffer.size() * 2]; //bufor do zapisu
	    int bufferIndex = 0;
	    for (int i = 0; i < byteBuffer.length; i++) { //przepisuje dane z wielkiego bufora do bufora zapisujcego... 
	    final int x = (int) (greatBuffer.get(bufferIndex++)*1);
	    byteBuffer[i] = (byte) (x>>>8); // ...w postaaci bitow
	    i++;
	    byteBuffer[i] = (byte) (x & 0xff);
	    }
	    File out = new File(name); //tworzy plik
	    boolean bigEndian = true; //kolejnosc zapisu bajtow
	    boolean signed = true; //parametry pliku .wav
	    int bits = 16;
	    int channels = 2; //2 kanaly, ale dzwiek mono
	    AudioFormat format;
	    format = new AudioFormat((float)sampleRate, bits, channels, signed, bigEndian);
	    ByteArrayInputStream bais = new ByteArrayInputStream(byteBuffer);
	    AudioInputStream audioInputStream;
	    audioInputStream = new AudioInputStream(bais, format,greatBuffer.size()); //strumien wyjsciowy
	    try {
			AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, out); //zapisuje do pliku
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			audioInputStream.close(); // zamyka plik
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //reset ilosci sampli
	}
	

	@Override
	public void run() {
	    byte[] buffer=new byte[4];
	    int bufferposition=0;
		while(status){ 
			//generuje sample
			short sample = 0;
				sample=(short) (Math.sin(2*Math.PI*c/(sampleRate/freq))*volume);
		      
		      greatBuffer.add((float) sample);

		      //dzieli sample na 2 i przechowuje w buforze
		      buffer[bufferposition]=(byte) (sample>>>8);
		      bufferposition++;
		      buffer[bufferposition]=(byte) (sample & 0xff);
		      bufferposition++;

		      //jesli bufor jest pelen, wysyla zawartosc na glosniki
		      if(bufferposition>=buffer.length){
		        line.write(buffer,0,buffer.length);
		        line.start();
		        bufferposition=0;
		      }
		      c++;
		}
		c=0;
	}
	
      

}	
	
