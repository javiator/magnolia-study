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
package com.firstclarity.magnolia.study.blossom.sample;

import info.magnolia.cms.core.Content;
import info.magnolia.module.blossom.annotation.TabFactory;
import info.magnolia.module.blossom.annotation.Template;
import info.magnolia.module.blossom.annotation.TemplateDescription;
import info.magnolia.module.blossom.dialog.TabBuilder;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.firstclarity.magnolia.study.blossom.sample.service.Book;
import com.firstclarity.magnolia.study.blossom.sample.service.SalesApplicationWebService;

/**
 * Component that renders a list of the books in a configurable category. The available categories
 * are fetched from the SalesApplicationWebService and the editor can then select which one should be
 * displayed.
 */
@Template(title = "Book category", id = "mymodule:components/bookCategory")
@TemplateDescription("A list of the books for a certain category.")
@Controller
public class BookCategoryComponent {

    @Autowired
    private SalesApplicationWebService salesApplicationWebService;

    @RequestMapping("/bookcategory")
    public String handleRequest(ModelMap model, Content content) {

        String category = content.getNodeData("category").getString();

        List<Book> books = salesApplicationWebService.getBooksInCategory(category);

        model.put("books", books);

        return "mymodule/components/bookCategory.jsp";
    }

    @TabFactory("Content")
    public void contentTab(TabBuilder tab) {
        Collection<String> categories = salesApplicationWebService.getBookCategories();
        tab.addSelect("category", "Category", "", categories);
    }
}
