package edu.uiuc.zenvisage.api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.uiuc.zenvisage.model.DynamicClass;
import edu.uiuc.zenvisage.service.ZvMain;

@Controller
public class ZvBasicAPI {

	@Autowired
	private ZvMain zvMain;

    public ZvBasicAPI(){

	}

	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public void fileUpload(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, InterruptedException, IOException, ServletException, SQLException {
		zvMain.fileUpload(request, response);
	}

	@RequestMapping(value = "/createClasses", method = RequestMethod.POST)
	@ResponseBody
	public String createClasses(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, InterruptedException, IOException, ServletException, SQLException {
	    StringBuilder stringBuilder = new StringBuilder();
	    Scanner scanner = new Scanner(request.getInputStream());
	    while (scanner.hasNextLine()) {
	    	stringBuilder.append(scanner.nextLine());
	    }
	    String body = stringBuilder.toString();
	    zvMain.runCreateClasses(body);
	    return new ObjectMapper().writeValueAsString(body);
	}
	
	/*
	 * /zv/getClassInfo
	 * {“dataset”: “real_estate”}
	 */
	@RequestMapping(value = "/getClassInfo", method = RequestMethod.POST)
	@ResponseBody
	public String getClassInfo(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, InterruptedException, IOException, ServletException, SQLException {
	    StringBuilder stringBuilder = new StringBuilder();
	    Scanner scanner = new Scanner(request.getInputStream());
	    while (scanner.hasNextLine()) {
	    	stringBuilder.append(scanner.nextLine());
	    }
	    String body = stringBuilder.toString();
	    return zvMain.runRetrieveClasses(body);
	}
	
	@RequestMapping(value = "/gettablelist", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<String> gettablelist() throws JsonGenerationException, JsonMappingException, IOException, InterruptedException, SQLException {
//		System.out.println(arg);
		return zvMain.getTablelist();
	}

//    /* Will be obsolete after separated calls*/
//	@RequestMapping(value = "/getdata", method = RequestMethod.GET)
//	@ResponseBody
//	public String getData(@RequestParam(value="query") String arg) throws InterruptedException, IOException {
//		//System.out.println(arg);
//		return zvMain.runDragnDropInterfaceQuery(arg);
//	}

	/* New Separated API calls
	 * Representative
	 * 	-Distance(Euc/DTW)
	 * Outlier
	 * 	-Distance(Euc/DTW)
	 * Similarity
	 *	-Distance(Euc/DTW)
	 *  -Similarity/Dis-similarity (true/false)
	 */
	@RequestMapping(value = "/postRepresentative", method = RequestMethod.POST)
	@ResponseBody
	public String postRepresentative(HttpServletRequest request, HttpServletResponse response) throws InterruptedException, IOException, SQLException {
		StringBuilder stringBuilder = new StringBuilder();
	    Scanner scanner = new Scanner(request.getInputStream());
	    while (scanner.hasNextLine()) {
	        stringBuilder.append(scanner.nextLine());
	    }

	    String body = stringBuilder.toString();
	    System.out.println("Representative:"+body);
		return zvMain.runDragnDropInterfaceQuerySeparated(body, "RepresentativeTrends");
	}

	@RequestMapping(value = "/postOutlier", method = RequestMethod.POST)
	@ResponseBody
	public String postOutlier(HttpServletRequest request, HttpServletResponse response) throws InterruptedException, IOException, SQLException {
		StringBuilder stringBuilder = new StringBuilder();
	    Scanner scanner = new Scanner(request.getInputStream());
	    while (scanner.hasNextLine()) {
	        stringBuilder.append(scanner.nextLine());
	    }

	    String body = stringBuilder.toString();

		return zvMain.runDragnDropInterfaceQuerySeparated(body, "Outlier");
	}

	@RequestMapping(value = "/postSimilarity", method = RequestMethod.POST)
	@ResponseBody
	public String postSimilarity(HttpServletRequest request, HttpServletResponse response) throws InterruptedException, IOException, SQLException {
		StringBuilder stringBuilder = new StringBuilder();
	    Scanner scanner = new Scanner(request.getInputStream());
	    while (scanner.hasNextLine()) {
	        stringBuilder.append(scanner.nextLine());
	    }

	    String body = stringBuilder.toString();


		return zvMain.runDragnDropInterfaceQuerySeparated(body, "SimilaritySearch");
	}

	@RequestMapping(value = "/getDissimilarity", method = RequestMethod.GET)
	@ResponseBody
	public String getDissimilarity(@RequestParam(value="query") String arg) throws InterruptedException, IOException, SQLException {
//		System.out.println(arg);
		return zvMain.runDragnDropInterfaceQuerySeparated(arg, "DissimilaritySearch");
	}

	@RequestMapping(value = "/getformdata", method = RequestMethod.GET)
	@ResponseBody
	public String getformdata(@RequestParam(value="query") String arg) throws JsonGenerationException, JsonMappingException, IOException, InterruptedException, SQLException {
//		System.out.println(arg);
		return zvMain.getInterfaceFomData(arg);
	}

//	@RequestMapping(value = "/getBaselineData", method = RequestMethod.GET)
//	@ResponseBody
//	public String getBaselineData(@RequestParam(value="query")String arg) throws JsonParseException, JsonMappingException, IOException, InterruptedException {
//		return zvMain.getBaselineData(arg);
//	}
//
//	@RequestMapping(value = "/getscatterplot", method = RequestMethod.GET)
//	@ResponseBody
//	public String getscatterplot(@RequestParam(value="query") String arg) throws JsonParseException, JsonMappingException, IOException {
//		return zvMain.getScatterPlot(arg);
//	}

	@RequestMapping(value = "/executeZQL", method = RequestMethod.GET)
	@ResponseBody
	public String executeZQL(@RequestParam(value="query") String arg) throws IOException, InterruptedException {
		//return zvMain.runZQLQuery(arg);
		return "";
	}

	@RequestMapping(value = "/executeZQLComplete", method = RequestMethod.GET)
	@ResponseBody
	public String executeZQLComplete(@RequestParam(value="query")  String arg) throws IOException, InterruptedException, SQLException {
		// return zvMain.runZQLCompleteQuery(arg);
		// for testing my query graph executor with zql.html
		// String outputExecutor = zvMain.runZQLCompleteQuery(arg);
		String outputGraphExecutor = zvMain.runQueryGraph(arg);

		// TODO change to graph executor
		return outputGraphExecutor;
	}

	@RequestMapping(value = "/executeScatter", method = RequestMethod.GET)
	@ResponseBody
	public String executeScatter(@RequestParam(value="query")  String arg) throws IOException, InterruptedException, SQLException {
		String outputGraphExecutor = zvMain.runScatterQueryGraph(arg);

		// TODO change to graph executor
		return outputGraphExecutor;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public String test(@RequestParam(value="query") String arg) {
		return "Test successful:" + arg;
	}

}
