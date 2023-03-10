
package com.demo.core.servlets;

import com.day.cq.commons.jcr.JcrConstants;
import com.demo.core.bo.ProfileResponse;
import com.demo.core.bo.RequestModel;
import com.demo.core.services.ProfileService;
import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Servlet that writes some sample content into the response. It is mounted for
 * all resources of a specific Sling resource type. The
 * {@link SlingSafeMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the {@link SlingAllMethodsServlet}.
 */
@Component(service = { Servlet.class })
@SlingServletResourceTypes(
        resourceTypes="demo/components/content/profile",
        selectors = "profile",
        methods = {HttpConstants.METHOD_GET,HttpConstants.METHOD_POST,HttpConstants.METHOD_DELETE,HttpConstants.METHOD_PUT},
        extensions="json")
@ServiceDescription("Simple Demo Servlet")
public class ProfileServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Reference
    ProfileService profileService;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {
        final Resource resource = req.getResource();
        String profileResp="";
        // profileResp="{\"name\":\"Aravind A\",\"age\":\"23\"}";
        RequestModel requestModel = new RequestModel();
        requestModel.setIsDemo(req.getHeader("isDemo"));
        requestModel.setProfileId(req.getParameter("id"));
        profileResp=profileService.getProfileDetails(req, resp,requestModel);
        resp.setContentType("application/json");
        resp.getWriter().write(profileResp);
    }

    @Override
    protected void doPost(final SlingHttpServletRequest req,
    final SlingHttpServletResponse resp) throws ServletException, IOException {
        RequestModel requestModel = new RequestModel();
        requestModel.setIsDemo(req.getHeader("isDemo"));
        String profileResp=profileService.addProfile(req, resp, requestModel);
        resp.setContentType("application/json");
        resp.getWriter().write(profileResp);
        
    }

    @Override
    protected void doDelete(final SlingHttpServletRequest req,
    final SlingHttpServletResponse resp) throws ServletException, IOException {
        RequestModel requestModel = new RequestModel();
        requestModel.setIsDemo(req.getHeader("isDemo"));
        requestModel.setProfileId(req.getParameter("id"));
        String profileResp=profileService.deleteProfile(req, resp, requestModel);
        resp.setContentType("application/json");
        resp.getWriter().write(profileResp);
        
    }

    @Override
    protected void doPut(final SlingHttpServletRequest req,
    final SlingHttpServletResponse resp) throws ServletException, IOException {
        RequestModel requestModel = new RequestModel();
        requestModel.setIsDemo(req.getHeader("isDemo"));
        requestModel.setProfileId(req.getParameter("id"));
        String profileResp=profileService.editProfile(req, resp, requestModel);
        resp.setContentType("application/json");
        resp.getWriter().write(profileResp);
        
    }
}
