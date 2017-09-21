package utils;

public class ListFilters {
	
	public static String order(String order) {
		if(order == null) {
			return "asc";
		} else {
		if (order.equals("asc")) {
			return "asc";
			} else if (order.equals("dsc")) {
				return "desc";
			} else {
				return "asc";
			}
		} 
	}
	
	
	public static int numPage(int numPage) {
		if(numPage == 0) {
			numPage = 1;
		}
		return numPage-1;
	}
}
