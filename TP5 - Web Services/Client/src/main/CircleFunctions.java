
package main;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "CircleFunctions", targetNamespace = "http://main/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface CircleFunctions {


    /**
     * 
     * @param arg0
     * @return
     *     returns double
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getArea", targetNamespace = "http://main/", className = "main.GetArea")
    @ResponseWrapper(localName = "getAreaResponse", targetNamespace = "http://main/", className = "main.GetAreaResponse")
    public double getArea(
        @WebParam(name = "arg0", targetNamespace = "")
        double arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns double
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCircumference", targetNamespace = "http://main/", className = "main.GetCircumference")
    @ResponseWrapper(localName = "getCircumferenceResponse", targetNamespace = "http://main/", className = "main.GetCircumferenceResponse")
    public double getCircumference(
        @WebParam(name = "arg0", targetNamespace = "")
        double arg0);

}
