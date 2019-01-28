package com.mapr.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MapRDBController {
	
		private static final String welcomemsg = "LookUp Value is %s";
		
	   @GetMapping("/MapRDB/lookUp")
	    @ResponseBody
	    public String mapRDBLookUp(@RequestParam(name="lookUpId", required=true) String name) {
	        return String.format(welcomemsg, name);
	    }

}
