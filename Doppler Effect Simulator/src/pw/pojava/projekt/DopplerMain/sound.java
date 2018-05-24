package pw.pojava.projekt.DopplerMain;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class sound implements Runnable {
	
	Thread production;

	double freq=440;
	double volume=4200;
	final double sampleRate = 44100.0;
	SourceDataLine line; //wazna rzecz
	boolean status=false; //status odtwarzania
	long c=0; //licznik sampli
	ArrayList<Float> greatBuffer; //bufor przechowujacy dzwiek do zapisu
	
	public sound() throws LineUnavailableException {
		final AudioFormat format=new AudioFormat(44100f,16,2,true,true); //wazne rzeczy do robienia dzwieku
		greatBuffer = new ArrayList<Float>();
	    line=AudioSystem.getSourceDataLine(format);
		line.open(format);
	}
	
	public void setSound(double f) { //zmienia tylko czestotliwosc
		freq = f;
	}
	
	 /*public void setSound(double f, double v) { //zmienia czestotliwosc i amplitude
		freq = f;
		volume = v;
	} */
	
	public void stop() {
		status=false;
	}
	
	public void newSound() { //tworzy nowy watek
		production = new Thread(this);
	}
	
	public void save(String name) {  //zapis do pliku
		final byte[] byteBuffer = new byte[greatBuffer.size() * 2]; //bufor do zapisu
	    int bufferIndex = 0;
	    for (int i = 0; i < byteBuffer.length; i++) { //przepisuje dane z wielkiego bufora do bufora zapisujcego... 
	    final int x = (int) (greatBuffer.get(bufferIndex++)*1);
	    byteBuffer[i] = (byte) (x>>>8); // ...w postaaci bitow
	    i++;
	    byteBuffer[i] = (byte) (x & 0xff); //analogicznie jak w run()
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
	    
	    c=0; //reset ilosci sampli
		greatBuffer.clear(); //usuwa poprzedni zapis
	}
	
	@Override
	public void run() {
		System.out.println("uruchomiono watek dzwieku");
		byte[] buffer=new byte[100]; //bufor do ciaglego odtwarzania
	    int bufferposition=0; //licznik pozycji w buforze
	    
	    while(status){
		      short sample=(short) (Math.sin(2*Math.PI*c/(sampleRate/freq))*volume);
		      greatBuffer.add((float) sample);

		      buffer[bufferposition]=(byte) (sample>>>8); //rozdziela sample na 2 bity i wrzuca do bufora
		      bufferposition++;
		      buffer[bufferposition]=(byte) (sample & 0xff);
		      bufferposition++;

		      if(bufferposition>=buffer.length){ //jesli bufor sie wypelni, wysyla dzwiek do glosnikow
		        line.write(buffer,0,buffer.length);
		        line.start();

		        bufferposition=0; //reset bufora
		      }
		      c++; //sample+1
		    }  
	}
	
	
}
