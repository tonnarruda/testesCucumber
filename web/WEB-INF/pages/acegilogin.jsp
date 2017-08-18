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
		
		#alertaCaptcha {
		    color: #9F6000;
		    background-color: #FEEFB3;
		    border: 1px solid transparent;
		    padding: 0px 20px 0px 0px;
		    background-repeat: no-repeat;
		    -moz-border-radius:.5em;
			-webkit-border-radius:.5em;
			border-radius:.5em;
			display: block;
			margin: auto;
			margin-top: 20px;
			margin-bottom: 0px;
			text-decoration: none;
			font: inherit !important; color: inherit; 
			font-size: 15px !important;
			height: 70px;
    		width: 750px;
		}
	</style>
	<script src='<ww:url includeParams="none" value="/js/functions.js"/>'></script>
	<script src='<ww:url includeParams="none" value="/js/fortes.js"/>'></script>
	<script src='<ww:url includeParams="none" value="/dwr/interface/UsuarioDWR.js"/>'></script>
	<script src='<ww:url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script src='<ww:url includeParams="none" value="/dwr/util.js"/>'></script>
	<script src='https://www.google.com/recaptcha/api.js?hl=pt-BR'></script>
	
	<script type='text/javascript'>
		function validaCampos()
		{
			return validaFormulario('form', null, null, true);
		}

		function checkNetConnection(){
			var xhr = new XMLHttpRequest();
			var file = "https://www.blablacar.com.br/images/logo-blabla.png";
			http://fortespae.com.br/rh/img/fortesrh.png
			var r = Math.round(Math.random() * 900000);
			xhr.open('HEAD', file + "?subins=" + r, false);
			try {
				xhr.send();
				if (xhr.status >= 200 && xhr.status < 304) {
					return true;
				} else {
					return false;
				}
			}catch (e) {
				return false;
			}
		}

		function captcha()
		{
			UsuarioDWR.utilizaCaptchaNoLogin(function (utilizaCaptcha){
				if(utilizaCaptcha){
					if(!checkNetConnection()){
						$('.g-recaptcha').remove();
						$('#alertaCaptcha').show();
					}else{
						$('#alertaCaptcha').remove();				
					}
				}else{
					$('.g-recaptcha').remove();
					$('#alertaCaptcha').remove();
				}
			});
		}
		
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
			
			captcha();
		});

	</script>
</head>
<body>

<div align="center" style="display:none" id="alertaCaptcha">
	<ul>
		<li>
			O sistema está sem acesso a internet e está configurado para utilizar a validação do login com captcha.<br>  Para efetuar o login restabeleça a conexão com a internet.
		</li>
	</ul>
</div>
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
			<td class="corpo logo-sistema">
			</td>
			<td class="corpo">
				<%=request.getAttribute("msgRemprot")%><br>
				Usuário:<br>
				<input accesskey="u" type='text' id="username" onBlur="empresasUsuario();checkSOS();" name='j_username'<% if("1".equals(request.getParameter("login_error"))) { %>value='<%= session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY) %>'<% } %>/>
				<label id="senha">Senha:</label><br>
				<input accesskey="s" type='password' id="password" name='j_password'/>
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
	</br>
	<div align="center" class="g-recaptcha" data-sitekey="6LedXywUAAAAAHctiQvKnX24EmCWknKuXrvhdE-k"></div>
</form>

</body>
</html>