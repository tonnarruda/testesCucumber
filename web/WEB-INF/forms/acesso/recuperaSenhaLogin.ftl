<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Recuperar senha do usuário</title>
	<#assign accessKey="E"/>
	<#assign formAction="recuperaSenha.action"/>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign validarCampos="return validaFormulario('form', new Array('cpf'), new Array('cpf'))"/>

	<#if moduloExterno?exists && moduloExterno>
		<style type="text/css">
			.btnEnviar{
				background-image:url(../imgs/btnEnviarExterno.gif) !important;
				width: 81px !important;
			}
			input,textarea,select,fieldset {
				border: 1px solid #BFB6B3 !important;
			}
			.cxSenha{
				width:690px;
				padding: 20px;
				border: 2px solid #BFB6B3 !important;
				height: 200px;
			}
			.cxSenhaEsq{
				padding-top: -10px;
				text-align:center;
				float: left;
				width:240px;
				height: 180px;
			}
			.cxSenhaDir{
				padding-top: 10px;
				margin-right: 40px;
				text-align:center;
				float: right;
				width: 400x;
				height: 180px;
				background: #E9E9E9;
			}
	
		</style>
	</#if>

</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />

<div class="cxSenha">
	<div class="cxSenhaDir">
		<b>
		&nbsp;&nbsp;<br>
		&nbsp;&nbsp;<br>
		&nbsp;&nbsp;<br>
		&nbsp;&nbsp;<br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		Para solicitar uma nova senha por email  
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<br>
		preencha o campo ao lado com o seu CPF. 
		</b>
	</div>
	<div class="cxSenhaEsq">
		<@ww.form name="form" action="${formAction}"  onsubmit="${validarCampos}" validate="true" method="POST">
			<img border="0" src="<@ww.url value="/imgs/enviarEmail.gif"/>"><br>
			
			<@ww.textfield label="Digite seu CPF" name="cpf" id="cpf" cssClass="mascaraCpf"/>
			<#if !empresaId?exists >
			 	<@ww.select label="Empresa" name="empresa.id" id="empresa" listKey="id" listValue="nome" list="empresas" cssClass="selectEmpresa"/>
			 <#else>
			 	 <@ww.hidden name="empresaId" id="empresaId" />
			 </#if>
		<@ww.token/>
		</@ww.form>
		
		<#if msg?exists && msg != "">
			<script>
				alert('${msg}');
				document.getElementById('cpfRH').focus();
			</script>
		</#if>
		
		<div class="buttonGroup">
			<button  onclick="${validarCampos};" class="btnEnviar" accesskey="${accessKey}"></button>
		</div>
	</div>
</div>
</body>
</html>