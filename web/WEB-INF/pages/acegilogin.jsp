<%@ taglib prefix='ww' uri='webwork' %>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
	<title>Login do Sistema</title>
	<style type="text/css">
		@import url('<ww:url includeParams="none" value="/css/login.css"/>');
	</style>
	<script src='<ww:url includeParams="none" value="/js/functions.js"/>'></script>
	<script src='<ww:url includeParams="none" value="/js/fortes.js"/>'></script>
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
			addOptionsByMap("empresa", data);
		}
		
		function checkSOS(){
			if($('#username').val().toUpperCase() == "SOS"){
				if($('#SOSSeed').val() == "SOS")
					sosNumber();
			}else{
				$('#senha').text("Senha:");
				$('#SOSSeed').val("SOS");
			}	
		}
		
		function sosNumber(){
			var sosNumero = Math.floor((Math.random() * 10)).toString() + Math.floor((Math.random() * 10)).toString() + Math.floor((Math.random() * 10)).toString() + Math.floor((Math.random() * 10)).toString();
			$('#senha').text("Contra Senha (" + sosNumero + "):");
			$('#SOSSeed').val(sosNumero);
		}
		
		$(function() {
			if ($('#username').val() != ""){
				empresasUsuario();
				checkSOS();
			}
		});

	</script>
</head>
<body>
<form name="form" action="<ww:url value='j_spring_security_check'/>" onsubmit="validaCampos();" method="POST">
	<br><br><br>
	<% if("1".equals(request.getParameter("login_error"))) { %>
	<table width="344px" align="center">
		<tr>
			<td valign="top" width="40"><img src='<ww:url includeParams="none" value="/imgs/erro_msg.gif"/>'></td>
			<td class="msgErro" valign="top">
				<span class="txtErro">
				<%--= ((AuthenticationException) session.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage() --%>
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
			<td class="corpo logo-sistema">
			</td>
			<td class="corpo">
				<%=request.getAttribute("msgRemprot")%><br>
				Usuário:<br>
<<<<<<< HEAD
				<input accesskey="u" type='text' id="username" onBlur="empresasUsuario();checkSOS();" name='j_username'<% if("1".equals(request.getParameter("login_error"))) { %>value='<%= session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY) %>'<% } %>/>
				<label id="senha">Senha:</label><br>
				<input accesskey="s" type='password' id="password" name='j_password'/>
=======
				<input accesskey="u" type='text' id="username" onBlur="empresasUsuario();" name='j_username'<% if("1".equals(request.getParameter("login_error"))) { %>value=''<% } %>>
				Senha:<br>
				<input accesskey="s" type='password' id="password" name='j_password'>
>>>>>>> Atualização Spring e Hibernate
  				<ww:select label="Empresa" name="j_empresa" id="empresa" listKey="id" listValue="nome" list="empresas" cssClass="selectEmpresa"/>
  				<input type="hidden" id="SOSSeed" name="j_SOSSeed" value="SOS"/>
  				<br>
			</td>
		</tr>
		<tr>
			<td width="109px" height="60px" class="logo"></td>
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