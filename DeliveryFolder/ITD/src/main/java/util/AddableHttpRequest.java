package util;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

//class that manages the parameters of a request
public class AddableHttpRequest extends HttpServletRequestWrapper {

	public AddableHttpRequest(HttpServletRequest request) {
		super(request);
	}

	@SuppressWarnings("rawtypes")
	private HashMap params = new HashMap();

	// get request parameters
	public String getParameter(String name) {
		if (params.get(name) != null) {
			return (String) params.get(name);
		}
		HttpServletRequest req = (HttpServletRequest) super.getRequest();
		return req.getParameter(name);
	}

	// adds parameters to the request
	@SuppressWarnings("unchecked")
	public void addParameter(String name, String value) {
		params.put(name, value);
	}
}
