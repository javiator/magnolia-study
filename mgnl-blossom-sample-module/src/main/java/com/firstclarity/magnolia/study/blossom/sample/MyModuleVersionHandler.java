package com.firstclarity.magnolia.study.blossom.sample;

import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.BootstrapSingleResource;
import info.magnolia.module.delta.ModuleFilesExtraction;
import info.magnolia.module.delta.OrderNodeBeforeTask;
import info.magnolia.module.delta.Task;
import info.magnolia.repository.RepositoryConstants;

import java.util.ArrayList;
import java.util.List;

/*
 * This class is used to handle installation and updates of your module.
 */
public class MyModuleVersionHandler extends DefaultModuleVersionHandler {

	@Override
	protected List<Task> getStartupTasks(InstallContext installContext) {
		final List<Task> tasks = new ArrayList<Task>();

		tasks.add(new ModuleFilesExtraction());

		return tasks;
	}
	
    @Override
    protected List<Task> getBasicInstallTasks(InstallContext installContext) {
        List<Task> tasks = new ArrayList<Task>();

        tasks.add(new BootstrapSingleResource(
                "New filter",
                "Bootstraps the DynamicComponents Execution Filter.",
                "/mgnl-bootstrap/mymodule/config.server.filters.cms.dynamicComponentExecution.xml"));
        tasks.add(new OrderNodeBeforeTask(
                "Order DynamicComponentsFilter filter",
                "",
                RepositoryConstants.CONFIG,
                "/server/filters/cms/dynamicComponentExecution",
                "blossom"));        
        tasks.add(new BootstrapSingleResource(
                "Sample Website",
                "Bootstraps the Sample Website Pages",
                "/mgnl-bootstrap/mymodule/website.home.xml"));

        return tasks;
    }

}
