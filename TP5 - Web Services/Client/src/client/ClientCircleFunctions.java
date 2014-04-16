package client;

import main.CircleFunctions;
import main.CircleFunctionsService;

public class ClientCircleFunctions {

	public static void main(String[] args) {
		try {
			CircleFunctionsService service = new CircleFunctionsService();
			CircleFunctions port = service.getCircleFunctionsPort();
			double arg0 = 3.0;
			double result = port.getArea(arg0);
			System.out.println("Result = "+result);
		} catch(Exception e) {
			
		}
	}
}
