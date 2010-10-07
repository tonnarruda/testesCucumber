package com.fortes.rh.test.web.action;

import java.util.HashMap;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;

public abstract class BaseActionTest extends TestCase
{
    protected static XmlWebApplicationContext ctx;
    protected MockHttpServletRequest request = new MockHttpServletRequest();

    static
    {
        String[] paths = {"applicationContext*.xml",
        		"/com/fortes/rh/dao/acesso/applicationContext-dao.xml",
        		"/com/fortes/rh/dao/geral/applicationContext-dao.xml",
        		"/com/fortes/rh/dao/cargosalario/applicationContext-dao.xml",
        		"/com/fortes/rh/dao/captacao/applicationContext-dao.xml",
        		"/com/fortes/rh/dao/desenvolvimento/applicationContext-dao.xml",
        		"/com/fortes/rh/dao/sesmt/applicationContext-dao.xml",

        		"/com/fortes/rh/dao/hibernate/acesso/applicationContext-dao-hibernate.xml",
        		"/com/fortes/rh/dao/hibernate/geral/applicationContext-dao-hibernate.xml",
        		"/com/fortes/rh/dao/hibernate/captacao/applicationContext-dao-hibernate.xml",
        		"/com/fortes/rh/dao/hibernate/cargosalario/applicationContext-dao-hibernate.xml",
        		"/com/fortes/rh/dao/hibernate/desenvolvimento/applicationContext-dao-hibernate.xml",
        		"/com/fortes/rh/dao/hibernate/sesmt/applicationContext-dao-hibernate.xml",

        		"/com/fortes/rh/business/acesso/applicationContext-business.xml",
        		"/com/fortes/rh/business/geral/applicationContext-business.xml",
        		"/com/fortes/rh/business/captacao/applicationContext-business.xml",
        		"/com/fortes/rh/business/cargosalario/applicationContext-business.xml",
        		"/com/fortes/rh/business/desenvolvimento/applicationContext-business.xml",
        		"/com/fortes/rh/business/sesmt/applicationContext-business.xml",

        		"/com/fortes/rh/test/web/action/applicationContext-web.xml"};

        ctx = new XmlWebApplicationContext();
        ctx.setConfigLocations(paths);
        ctx.setServletContext(new MockServletContext(""));
        ctx.refresh();
    }

    protected void setUp() throws Exception
    {
        ActionContext.getContext().setSession(new HashMap());
        ServletActionContext.setRequest(request);
    }

    protected void tearDown() throws Exception
    {
        ActionContext.getContext().setSession(null);
    }
}