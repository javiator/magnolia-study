/**
 * This file Copyright (c) 2010-2011 Magnolia International
 * Ltd.  (http://www.magnolia-cms.com). All rights reserved.
 *
 *
 * This file is dual-licensed under both the Magnolia
 * Network Agreement and the GNU General Public License.
 * You may elect to use one or the other of these licenses.
 *
 * This file is distributed in the hope that it will be
 * useful, but AS-IS and WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE, TITLE, or NONINFRINGEMENT.
 * Redistribution, except as permitted by whichever of the GPL
 * or MNA you select, is prohibited.
 *
 * 1. For the GPL license (GPL), you can redistribute and/or
 * modify this file under the terms of the GNU General
 * Public License, Version 3, as published by the Free Software
 * Foundation.  You should have received a copy of the GNU
 * General Public License, Version 3 along with this program;
 * if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * 2. For the Magnolia Network Agreement (MNA), this file
 * and the accompanying materials are made available under the
 * terms of the MNA which accompanies this distribution, and
 * is available at http://www.magnolia-cms.com/mna.html
 *
 * Any modifications to this file must keep this entire header
 * intact.
 *
 */
package com.firstclarity.magnolia.study.blossom.sample.ajax;

import info.magnolia.cms.core.Content;
import info.magnolia.module.blossom.annotation.TabFactory;
import info.magnolia.module.blossom.annotation.Template;
import info.magnolia.module.blossom.annotation.TemplateDescription;
import info.magnolia.module.blossom.dialog.TabBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Component that displays information about a book. The book is selected by the
 * editor in a dialog and read from the SalesApplicationWebService at display
 * time.
 */
@Template(title = "AjaxText", id = "mymodule:components/ajaxText")
@TemplateDescription("Ajax Text Display")
@Controller
public class AjaxTextComponent {

	@RequestMapping("/ajaxText")
	public String handleRequest(ModelMap model, HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			Content content) {
		
		model.addAttribute(DynamicComponentUtil.VIEW_NAME_PARAM, "mymodule/components/ajaxText.jsp");
		
		if(null == request.getParameter(DynamicComponentUtil.DYNAMIC_COMPONENT_LOAD_PARAMETER)){				
			return DynamicComponentUtil.DYNAMIC_COMPONENT_VIEW;
		}
		String articleIdStr = request.getParameter("articleId");
		Integer articleId = 0;
		
		if (null != articleIdStr) {
			articleId = new Integer(articleIdStr);
		}

		try {
			switch (articleId) {
				case 0:
					model.put("sampleText",	"There is no article selected - showing default 0");
					return DynamicComponentUtil.DYNAMIC_COMPONENT_VIEW;
				case 1:	
					model.put("sampleText",	"This is article 1");	
					return DynamicComponentUtil.DYNAMIC_COMPONENT_VIEW;
				case 2:
					model.put("sampleText",	"This is article 2");
					return DynamicComponentUtil.DYNAMIC_COMPONENT_VIEW;
				case 4:
					model.put("sampleText",	"This is article 4");
					return DynamicComponentUtil.DYNAMIC_COMPONENT_VIEW;
				case 5:
					model.put("sampleText",	"This is article 5");
					return DynamicComponentUtil.DYNAMIC_COMPONENT_VIEW;

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return DynamicComponentUtil.DYNAMIC_COMPONENT_VIEW;
	}

	@TabFactory("Content")
	public void contentTab(TabBuilder tab) {
		tab.addFckEditor("body", "Text", "");
	}
}
