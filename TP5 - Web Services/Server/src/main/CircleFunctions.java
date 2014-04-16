package main;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class CircleFunctions {

	@WebMethod
	public double getArea(double r) {
		return Math.PI * r * r;
	}
	
	@WebMethod
	public double getCircumference(double r) {
		return 2 * Math.PI * r;
	}
	
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8080/WebServiceExample/circlefunctions", new CircleFunctions());
	}
}