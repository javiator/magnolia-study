package com.firstclarity.magnolia.study.blossom.sample;

import info.magnolia.module.ModuleLifecycle;
import info.magnolia.module.ModuleLifecycleContext;
import info.magnolia.module.blossom.module.BlossomModuleSupport;

/**
 * This is the configuration bean of your Magnolia module. It has to be registered in the module descriptor file 
 * under src/main/resources/META-INF/magnolia/mymodule.xml.
 * 
 * The bean properties used in this class will be initialized by Content2Bean which means that properties of in the 
 * node config:/modules/mymodule/config/* are populated to this bean when the module is initialized.
 */
public class MyModule extends BlossomModuleSupport implements ModuleLifecycle {
    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MyModule.class);
    
    @Override
    public void start(ModuleLifecycleContext moduleLifecycleContext) {
        initRootWebApplicationContext("classpath:/applicationContext.xml");
        initBlossomDispatcherServlet("blossom", "classpath:/blossom-servlet.xml");
    }

    @Override
    public void stop(ModuleLifecycleContext moduleLifecycleContext) {
        destroyDispatcherServlets();
        closeRootWebApplicationContext();
    }
}
