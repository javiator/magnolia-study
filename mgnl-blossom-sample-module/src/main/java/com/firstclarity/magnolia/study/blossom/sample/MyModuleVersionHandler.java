package com.firstclarity.magnolia.study.blossom.sample;

import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.ModuleFilesExtraction;
import info.magnolia.module.delta.Task;

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

}
