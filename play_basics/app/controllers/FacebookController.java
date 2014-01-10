package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import play.libs.F.Function;
import play.libs.WS;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.FacebookException;

import views.html.facebook.*;

public class FacebookController extends Controller {

    public static Result index() {
        //return ok(index.render("Your new application is ready."));
        return ok(fblogin.render());
    }

    public static Result status() {
        return ok(fbstatus.render());
    }

    public static Result user() {
        return ok(fbuser.render());
    }

    public static Result loggedin() {
    	Facebook facebook = new FacebookFactory().getInstance();
    	String appId = "642364099161855";
    	String appSecret = "SECRET";
		facebook.setOAuthAppId(appId, appSecret);
		facebook.setOAuthPermissions("email");

		String callbackUrl = facebook.getOAuthAuthorizationURL("http://localhost:9000/facebook/loggedin");
		//facebook.setOAuthAccessToken(new AccessToken(accessToken, null));

    	Logger.warn("facebook callback. URL: " +callbackUrl);
    	final Set<Map.Entry<String,String[]>> entries = request().queryString().entrySet();
        for (Map.Entry<String,String[]> entry : entries) {
            final String key = entry.getKey();
            final String value = Arrays.toString(entry.getValue());
            Logger.warn(key + " " + value);

            if(key.equals("code")){
            	String redirect = "https://graph.facebook.com/oauth/access_token?client_id=642364099161855"
            		+"&redirect_uri=http://localhost:9000/loggedin"
            		+"&client_secret=SECRET"
            		+"&code=" +value;

            	Logger.warn("Getting: " +redirect);	
            	try {
            		facebook.getOAuthAccessToken(value);
             	Logger.warn("got access token: ");	
        		} catch (FacebookException e) {
             	Logger.warn("got exception " +e);	
        		}
        		return ok(fbloggedin.render());
            //	return redirect(redirect);
            }
        }
			
        return redirect(callbackUrl);
    }

}
