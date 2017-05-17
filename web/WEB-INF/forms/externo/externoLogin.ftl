<!--
  Autor: Robertson Freitas
  Requisito: RFA030
 -->
<html>
<head>
<@ww.head/>
	<title>Cadastro de Currículo</title>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/fortes.css"/>');
	</style>
	
	<style type="text/css">
		input { border: 1px solid #FFF !important; }
		a { color: blue; }
		.loginExternoForm { background-color: #E9E9E9; border: 1px solid #000000; width: 210px; }
		.loginExternoTable { border: 4px solid #E9E9E9; background-color: white; }
		.linkCadastro a { text-decoration: none; }
	</style>

	<#if empresaId?exists>
		<#assign urlServlet = "${request.contextPath}/externo/layout?empresaId=${empresaId}&tipo=" />
	<#else>
		<#assign urlServlet = "${request.contextPath}/externo/layout?tipo=" />
	</#if>

	<link rel="stylesheet" type="text/css" href="${urlServlet}layout" />
	<#include "../ftl/mascarasImports.ftl" />
</head>
<#assign validarCampos="return validaFormulario('form', new Array('cpfRH','senhaRH'), new Array('cpfRH'))"/>
<body>
	<@ww.actionerror />
	
<#if empresaId?exists>
	<@ww.form name="form" action="checaLogin.action?empresaId=${empresaId}" method="POST" onsubmit="${validarCampos}">
		<@ww.hidden name="solicitacao.id"/>
		<table class="loginExternoTable" cellpadding="5" style="margin: 0 auto; width: 470px">
			<tr>
				<td>
					<table cellpadding="8">
						<tr>
							<td align="center">
								<img class="loginExternoLogo" border="0" src="${urlServlet}logotipo"/>
							</td>
						</tr>
						<tr>
							<td>
								<#if mensagemLogin?exists && mensagemLogin != "">
									${mensagemLogin}
								</#if>
								<br /><br />
								<span class="linkCadastro">
									<a href="prepareInsert.action?moduloExterno=true&empresaId=${empresaId}">Quero me cadastrar</a><br />
									<a href="prepareListAnuncio.action?empresaId=${empresaId}">Visualizar oportunidades</a><br />
								</span>	
							</td>
						</tr>
					</table>
				</td>
				<td align="right">
					<table class="loginExternoForm" cellpadding="8">
						<tr>
							<td align="center">
								<img border="0" src="<@ww.url value="/imgs/liberar.gif"/>">&nbsp;<b>Identifique-se</b>
							</td>
						</tr>
						<tr>
							<td>
								CPF:<br />
								<@ww.textfield name="cpf" id="cpfRH" theme="simple" cssClass="mascaraCpf"/>
							</td>
						</tr>
						<tr>
							<td>
								Senha:<br />
								<@ww.password name="senha" id="senhaRH" theme="simple"/>
							</td>
						</tr>
						<tr>
							<td>
								<button onclick="${validarCampos};" accesskey="e" class="btnEntrar botao">Entrar</button>
							</td>
						</tr>
						<tr>
							<td>
								<span class="linkCadastro">
									<a href="prepareRecuperaSenha.action?empresaId=${empresaId}" class="linkbranco">Esqueci minha senha</a><br />
								</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<br><br>
				
	</@ww.form>

<#else>

	<h3><img align="absmiddle" border="0" src="<@ww.url value="/imgs/warning.gif"/>"> Atenção, empresa não identificada.</h3>

</#if>

	<#if msg?exists && msg != "">
		<script>
			jAlert('${msg?replace("%20"," ")}');
			document.getElementById('cpfRH').focus();
		</script>
	</#if>

</body>
</html>