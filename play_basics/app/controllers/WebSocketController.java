package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.Logger;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;

public class WebSocketController extends Controller{

	static Map<WebSocket<String>, WebSocket.Out<String>> clients = new HashMap<>();
	static class WebSocketClient{
		WebSocket<String> ws;
		

	}

	
	public static Result index(){
		return ok(views.html.websocket.websocket.render("Websockets Beispiel"));
	}
	
	public static Result wsgame(){
		return ok(views.html.websocket.wsgame.render());
	}
	
	public static WebSocket<String> ws() {
	    return new WebSocket<String>() {

	        // Called when the Websocket Handshake is done.
	        public void onReady(WebSocket.In<String> in, final WebSocket.Out<String> out) {
	        	Logger.debug("onReady clients" );
	            // For each event received on the socket,
	            in.onMessage(new Callback<String>() {
	                public void invoke(String event) {
	                	Logger.debug("onMessage: " +event);
	                    // Log events to the console
	                    System.out.println(event);
	                    out.write("Message: " +event);
	                    
	                    for(WebSocket<String> ws : clients.keySet()){
	                    	clients.get(ws).write("" +event);
	                    }

	                }
	            });

	            // When the socket is closed.
	            in.onClose(new Callback0() {
	                public void invoke() {
	                	clients.remove(this);
	                    Logger.debug("onClose. clients: " +clients.size());
	                	System.out.println("Disconnected");

	                }
	            });

	            // Send a single 'Hello!' message
	            out.write("Hello!");
	           
	            clients.put(this, out);
	            
	            Logger.debug("clients: " +clients.size());

	        }

	    };
	}
}
