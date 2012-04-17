package org.springframework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomSpringViewRenderer {

	public static void renderView(ModelAndView mv, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		mv.getView().render(mv.getModel(), request, response);
	}
}
