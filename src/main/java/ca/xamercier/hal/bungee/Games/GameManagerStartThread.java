package ca.xamercier.hal.bungee.Games;

import ca.xamercier.hal.bungee.Games.Threads.HikaBrain1v1Thread;
import ca.xamercier.hal.bungee.Games.Threads.HikaBrain2v2Thread;
import ca.xamercier.hal.bungee.Games.Threads.HubThread;

public class GameManagerStartThread extends Thread{

	public void run() {
        HubThread hubThread = new HubThread();
        hubThread.start();
        
		HikaBrain1v1Thread HikaBrain1v1Thread = new HikaBrain1v1Thread();
		HikaBrain1v1Thread.start();
        
		HikaBrain2v2Thread HikaBrain2v2Thread = new HikaBrain2v2Thread();
		HikaBrain2v2Thread.start();
	}
 	
}
