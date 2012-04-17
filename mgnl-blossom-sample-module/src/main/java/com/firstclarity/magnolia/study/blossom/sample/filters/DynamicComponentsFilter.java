package com.firstclarity.magnolia.study.blossom.sample.filters;

import info.magnolia.cms.core.AggregationState;
import info.magnolia.cms.core.Content;
import info.magnolia.cms.core.MgnlNodeType;
import info.magnolia.cms.filters.OncePerRequestAbstractMgnlFilter;
import info.magnolia.cms.util.ContentUtil;
import info.magnolia.context.MgnlContext;
import info.magnolia.module.blossom.preexecution.PreexecutionContextHolder;
import info.magnolia.objectfactory.Components;
import info.magnolia.rendering.engine.RenderingEngine;
import info.magnolia.rendering.engine.ResponseOutputProvider;
import info.magnolia.repository.RepositoryConstants;

import java.io.IOException;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedIOException;
import org.springframework.web.servlet.CustomSpringViewRenderer;
import org.springframework.web.servlet.ModelAndView;

import com.firstclarity.magnolia.study.blossom.sample.ajax.DynamicComponentUtil;

/**
* Uses the CachePolicy to determine the cache behavior. Uses then the
* CacheConfiguration to get the executors to be executed.
*
* @author gjoseph
* @version $Revision: $ ($Author: $)
*/
public class DynamicComponentsFilter extends OncePerRequestAbstractMgnlFilter {
	
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String dynCompLoadParam = request.getParameter(DynamicComponentUtil.DYNAMIC_COMPONENT_LOAD_PARAMETER);
        if (dynCompLoadParam == null) {        	
            // If this is not a pre-execution request then let it pass through.
        	/*
        	try {
				if(executeView(request, response)){ 	
	            	return;
				}
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	*/
            chain.doFilter(request, response);
            return;
        }else{
	        // Find the target content.
	        Node content;
	        try {
	            Session session = MgnlContext.getSystemContext().getJCRSession(RepositoryConstants.WEBSITE);
	            content = session.getNodeByIdentifier(dynCompLoadParam);
	        } catch (RepositoryException e) {
	            logger.error("Content for pre-execution [" + dynCompLoadParam + "] not found in repository");
	            return;
	        }
	        
	        try {
	        	request.setAttribute(DynamicComponentUtil.DYNAMIC_COMPONENT_LOAD_PARAMETER, true);	
	            executeHandler(content, response);
            	request.setAttribute(DynamicComponentUtil.DYNAMIC_COMPONENT_LOAD_PARAMETER, false);
            	
            	try {
                    response.flushBuffer();
                }
                catch (IOException e) {
                    // don't log at error level since tomcat typically throws a
                    // org.apache.catalina.connector.ClientAbortException if the user stops loading the page
                	logger.debug("Exception flushing response " + e.getClass().getName() + ": " + e.getMessage(), e);
                }
            	
            	return;
	            
	
	        } catch (RepositoryException e) {
	            throw new ServletException(e);
	        } finally {
	            PreexecutionContextHolder.remove(request);
	        }
        }        
    }
    
    private void executeHandler(Node content, HttpServletResponse response) throws IOException, RepositoryException {

        AggregationState state = getAggregationStateSafely();
        Content orgMainContent = null;
        Content orgCurrentContent = null;

        if (state != null) {
            orgMainContent = state.getMainContent();
            orgCurrentContent = state.getCurrentContent();

            state.setCurrentContent(ContentUtil.asContent(content));
            if (orgMainContent == null) {
                state.setMainContent(ContentUtil.asContent(findMainContent(content)));
            }
        }
        try {

            try {
                Components.getComponent(RenderingEngine.class).render(content, new ResponseOutputProvider(response));
            } catch (info.magnolia.rendering.engine.RenderException e) {
                throw new NestedIOException("Error pre-executing handler", e);
            }

        } finally {
            if (state != null) {
                state.setMainContent(orgMainContent);
                state.setCurrentContent(orgCurrentContent);
            }
        }
    }
    
    private boolean executeView(HttpServletRequest request, HttpServletResponse response) throws IOException, RepositoryException {
       
    	AggregationState state = getAggregationStateSafely();
        Content orgMainContent = null;
        Content orgCurrentContent = null;

        if (state != null) {
            orgMainContent = state.getMainContent();
            orgCurrentContent = state.getCurrentContent();
            
            if(orgCurrentContent.getTemplate().equals("mymodule:components/ajaxText")
            		|| orgCurrentContent.getTemplate().equals("mymodule:components/book")){

	            /*state.setCurrentContent(ContentUtil.asContent(content));
	            if (orgMainContent == null) {
	                state.setMainContent(ContentUtil.asContent(findMainContent(content)));
	            }*/
	        
		        try {
		
		            try {
		            	CustomSpringViewRenderer.renderView(new ModelAndView(DynamicComponentUtil.DYNAMIC_COMPONENT_VIEW), request, response);
		            } catch (Exception e) {
		                throw new NestedIOException("Error pre-executing handler", e);
		            }
		
		        } finally {
		            /*
		        	if (state != null) {
		                state.setMainContent(orgMainContent);
		                state.setCurrentContent(orgCurrentContent);
		            }
		            */
		        }
		        
				try {
                    response.flushBuffer();
                }
                catch (IOException e) {
                    // don't log at error level since tomcat typically throws a
                    // org.apache.catalina.connector.ClientAbortException if the user stops loading the page
                	logger.debug("Exception flushing response " + e.getClass().getName() + ": " + e.getMessage(), e);
                }
		        return true;
            }
        }
        return false;
    }

    protected Node findMainContent(Node content) throws RepositoryException {
        while (content.getDepth() != 0) {
            if (content.getPrimaryNodeType().getName().equals(MgnlNodeType.NT_CONTENT)) {
                return content;
            }
            content = content.getParent();
        }
        return null;
    }

    protected AggregationState getAggregationStateSafely() {
        if (MgnlContext.isWebContext()) {
            return MgnlContext.getAggregationState();
        }
        return null;
    }

    @Override
    public void destroy() {
    }
}
