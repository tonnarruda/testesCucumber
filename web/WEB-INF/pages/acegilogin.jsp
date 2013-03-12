<%@ taglib prefix='ww' uri='webwork' %>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter" %>
<%@ page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.acegisecurity.AuthenticationException" %>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
	<title>Login do Sistema</title>
	<style type="text/css">
		@import url('<ww:url includeParams="none" value="/css/login.css"/>');
	</style>
	<![endif]-->
	<script src='<ww:url includeParams="none" value="/js/functions.js"/>'></script>
	<script src='<ww:url includeParams="none" value="/dwr/interface/UsuarioDWR.js"/>'></script>
	<script src='<ww:url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script src='<ww:url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript'>
		function validaCampos()
		{
			return validaFormulario('form', null, null, true);
		}
		function customOnsubmit(){}
		
		function empresasUsuario()
		{
			UsuarioDWR.getEmpresaUsuario(createListEmpresa, $('#username').val());
		}
		
		function createListEmpresa(data)
		{
			DWRUtil.removeAllOptions("empresa");
			DWRUtil.addOptions("empresa", data);
		}
		
		$(function() {
			if ($('#username').val() != null)
				empresasUsuario();
		});

	</script>
</head>
<body>
<form name="form" action="<ww:url value='j_acegi_security_check'/>" onsubmit="validaCampos();" method="POST">
	<br><br><br>
	<% if("1".equals(request.getParameter("login_error"))) { %>
	<table width="344px" align="center">
		<tr>
			<td valign="top" width="40"><img src='<ww:url includeParams="none" value="/imgs/erro_msg.gif"/>'></td>
			<td class="msgErro" valign="top">
				<span class="txtErro">
				<%= ((AuthenticationException) session.getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>
				</span>
			</td>
		</tr>
	</table>
	<% } %>
	<table width="344px" align="center" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td class="topo" width="344px" height="71px" colspan="2">
			</td>
		</tr>
		<tr>
			<td class="corpo">
			</td>
			<td class="corpo">
				<%=request.getAttribute("msgRemprot")%><br>
				Usuário:<br>
				<input accesskey="u" type='text' id="username" onBlur="empresasUsuario();" name='j_username'<% if("1".equals(request.getParameter("login_error"))) { %>value='<%= session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY) %>'<% } %>>
				Senha:<br>
				<input accesskey="s" type='password' id="password" name='j_password'>
  				<ww:select label="Empresa" name="j_empresa" id="empresa" listKey="id" listValue="nome" list="empresas" cssClass="selectEmpresa"/>
  				<br>
			</td>
		</tr>
		<tr>
			<td width="109px" height="79px" class="logo"></td>
			<td class="rodape" valign="top">
				<input type="submit" name="submit" value=" " class="btnEntrar"/>
				<br>
				<p align="right"><a href="acesso/usuario/prepareRecuperaSenha.action" class="linkbranco">Esqueci minha senha</a>&nbsp;</p>
			</td>

		</tr>
	</table>
</form>
</body>
</html>