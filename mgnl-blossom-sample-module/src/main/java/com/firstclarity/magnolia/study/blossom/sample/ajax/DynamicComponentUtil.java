package com.firstclarity.magnolia.study.blossom.sample.ajax;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.ModelMap;

public class DynamicComponentUtil {

	public static final String DYNAMIC_COMPONENT_VIEW = "mymodule/components/dynComponent.jsp";
	public static final String DYNAMIC_COMPONENT_LOAD_PARAMETER = "_ajxDyn";
	public static final String VIEW_NAME_PARAM = "_viewName";

	public static Map<String, String> dynamicComponentsMap = new HashMap<String, String>();

	public DynamicComponentUtil() {
		dynamicComponentsMap.put("mymodule:components/book", "");
		dynamicComponentsMap.put("mymodule:components/ajaxText", "");
	}

	public static String handleDynamicProgressCheck(ModelMap model,String viewName) {
		model.addAttribute(DynamicComponentUtil.VIEW_NAME_PARAM, viewName);
		return DynamicComponentUtil.DYNAMIC_COMPONENT_VIEW;

	}
}
