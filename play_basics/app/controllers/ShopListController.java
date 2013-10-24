package controllers;

import java.util.List;

import models.ShopList;
import models.ShopListItem;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class ShopListController extends Controller {
	
	public static Result addItem(){
		ShopList shopList = 	ShopList.get();
		
		
		Form<ShopListItem> form = Form.form(ShopListItem.class);
		form = form.bindFromRequest();
		
		if(form.hasErrors() || form.hasGlobalErrors()){
			return badRequest(views.html.shoplist.shoplist.render(shopList.items));
		}
		
		ShopListItem item = form.get();
		shopList.addItem(item);
		Logger.info("Zu Liste hinzugefügt: " +item +" Posten: " +shopList.items.size());
		return redirect("/shoplist/");
	}

	public static Result deleteItem(String itemname){
		ShopList shopList = ShopList.get();
		
		shopList.remove(itemname);
		
		
		Logger.info("Von Liste gelöscht: " +itemname);
		return redirect("/shoplist/");
	}
	
	public static Result list(){
		List<ShopListItem> items = ShopList.get().items;
		return ok(views.html.shoplist.shoplist.render(items));
		//return ok("Shoplist: " +ShopList.get().items);
	}
}
