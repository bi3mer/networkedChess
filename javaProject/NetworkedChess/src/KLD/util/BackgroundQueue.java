package KLD.util;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import menus.Queue;
import model.ChessPlayerController;

public class BackgroundQueue extends Thread 
{
	public Boolean running = true;
	private Queue queue;
	private ChessPlayerController player;
	
	/**
	 * initialize thread
	 * @param queue
	 */
	public BackgroundQueue(Queue _queue)
	{
		this.queue = _queue;
	}
	
	/**
	 * End thread
	 */
	public void terminateThread()
	{
		this.running = false;
	}
	
	@Override
    public void run() 
	{
        while (this.running) 
        {
            try {
            	// Get update
				JSONObject update = ChessPlayerController.getInstance().getUpdate(false);
				
				// Test if update has valid info
				if(update.getInt("status") <= HttpURLConnection.HTTP_ACCEPTED && update.has("otherPlayer") && update.has("whitePlayer"))
				{
					// End thread
					this.running = false;
					
					// return info to queue
					this.queue.enterGame(update.getString("otherPlayer"), update.getBoolean("whitePlayer"));
					
					// End thread
					return;
				}
			} catch (JSONException | IOException | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
            try
            {	
            	// wait for 100 miliseconds to run again
                Thread.sleep(100);
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
        }
    }
}
