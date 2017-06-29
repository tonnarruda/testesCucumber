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
				width:755px;
				padding: 20px;
				border: 2px solid #BFB6B3 !important;
				height: 200px;
			}
			.cxSenhaEsq{
				padding-top: -10px;
				margin-left: 40px;
				text-align:center;
				float: left;
				width:240px;
				height: 180px;
			}
			.cxSenhaDir{
				margin-rigth: 40px;
				margin-left: 40px;
				width: 400px !important;
				height: 200px;
				background: #E9E9E9;
			}
			.cxSenhaDirtext {
			    margin-left: 20px;
			    margin-rigth: 20px;
			    line-height: 200px;
			    height: 300px;
			    text-align: center;
			}
			.cxSenhaDirtext p {
			    line-height: 1.5;
			    display: inline-block;
			    vertical-align: middle;
			    font-weight: bold; 
			}
	
		</style>
	</#if>

</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />

<div class="cxSenha">
	<table>
		<tr>
			<td>
				<div class="cxSenhaDir">
					<div class="cxSenhaDirtext">
						<p>Para solicitar uma nova senha por email</br>  
						preencha o campo ao lado com o seu CPF.</p>
					</div> 
				</div>
			</td>
			<td>
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
							jAlert('${msg}');
							document.getElementById('cpfRH').focus();
						</script>
					</#if>
					
					<div class="buttonGroup">
						<button  onclick="${validarCampos};" class="btnEnviar" accesskey="${accessKey}"></button>
					</div>
				</div>
			</td>
		</tr>
	</table>
</div>
</body>
</html>