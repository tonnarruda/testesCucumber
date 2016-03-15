package com.fortes.test.web.tags;

import mockit.Mockit;

import org.jmock.MockObjectTestCase;

import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.web.tags.LinkTag;

public class LinkTagTest extends MockObjectTestCase
{
	protected void tearDown() throws Exception
    {
        MockSecurityUtil.verifyRole = false;
    }
	
	public void testMontaLink()
	{
		StringBuffer link = new StringBuffer("");
		LinkTag linkTag = new LinkTag();
		
		assertEquals("<a ></a>", linkTag.montaLink(link).toString());
	}

	public void testMontaLinkComParametros()
	{
		StringBuffer link = new StringBuffer("");
		LinkTag linkTag = new LinkTag();
		linkTag.setHref("#");
		linkTag.setImgName("edit.git");
		linkTag.setImgTitle("Editar");
		linkTag.setOnclick("newConfirm('Edite?', function(){window.location='edite'});");
		
		assertEquals("<a href=\"#\" onclick=\"newConfirm('Edite?', function(){window.location='edite'});\" ><img border=\"0\"  title=\"Editar\"  src=\"/"+ArquivoUtil.getContextName()+"/imgs/edit.git\" ></a>", linkTag.montaLink(link).toString());
	}
	
	public void testMontaLinkComParametrosRole()
	{
		MockSecurityUtil.verifyRole = true;
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		
		StringBuffer link = new StringBuffer("");
		LinkTag linkTag = new LinkTag();
		linkTag.setHref("#");
		linkTag.setImgName("edit.git");
		linkTag.setImgTitle("Editar");
		linkTag.setOnclick("newConfirm('Edite?', function(){window.location='edite'});");
		linkTag.setVerifyRole("ROLE_COLAB_LIST_EDITAR");
				
		assertEquals("<a href=\"#\" onclick=\"newConfirm('Edite?', function(){window.location='edite'});\" ><img border=\"0\"  title=\"Editar\"  src=\"/"+ArquivoUtil.getContextName()+"/imgs/edit.git\" ></a>", linkTag.montaLink(link).toString());
	}
}
