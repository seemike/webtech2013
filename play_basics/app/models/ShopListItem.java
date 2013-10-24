package models;

import play.data.validation.Constraints;


public class ShopListItem {
	@Constraints.Required
	public int amount;
	
	@Constraints.Required
	public String name;
	
	@Override
	public String toString() {
		return "" +amount +" " +name;
	}
}
