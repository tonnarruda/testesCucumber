<html>
<head>

	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="Content-Language" content="pt-br" />
	<meta http-equiv="Cache-Control" content="no-cache, no-store" />
	<meta http-equiv="Pragma" content="no-cache, no-store" />
	<meta http-equiv="Expires" content="0" />

	<@ww.head/>
	<#if medicoCoordenador.id?exists>
		<title>Editar Médico Coordenador</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
	<#else>
		<title>Inserir Médico Coordenador</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
	</#if>
	
	<#assign inicio = ""/>
	<#if medicoCoordenador?exists && medicoCoordenador.inicio?exists>
		<#assign inicio = medicoCoordenador.inicio/>
	</#if>
	
	<#assign fim = ""/>
	<#if medicoCoordenador?exists && medicoCoordenador.fim?exists>
		<#assign fim = medicoCoordenador.fim/>
	</#if>
	
	<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('nome','inicio','crm'), new Array('inicio','fim'))"/>

	<#include "../ftl/mascarasImports.ftl" />

	<script type="text/javascript">
		function mostraAssinatura()
		{
			mostrar(document.getElementById('assinaturaUpLoad'));
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST" enctype="multipart/form-data">
		<@ww.textfield id="nome" label="Nome" name="medicoCoordenador.nome" maxLength="100" required="true" cssClass="inputNome"/>
		<@ww.datepicker id="inicio" label="Início do contrato" name="medicoCoordenador.inicio" required="true" value="${inicio}" cssClass="mascaraData validaDataIni"/>
		<@ww.datepicker id="fim" label="Inativar a partir de (fim do contrato)" name="medicoCoordenador.fim" value="${fim}" cssClass="mascaraData validaDataFim"/>
		<@ww.textfield id="crm" label="CRM" required="true" name="medicoCoordenador.crm" maxLength="20"/>
		<@ww.textfield id="nit" label="NIT" name="medicoCoordenador.nit" maxLength="15" />
		<@ww.textfield id="registro" label="Registro Medicina do Trabalho" name="medicoCoordenador.registro" maxLength="20"/>
		<@ww.textfield id="especialidade" label="Especialidade" name="medicoCoordenador.especialidade" maxLength="100" cssClass="inputNome"/>

		<#if medicoCoordenador.assinaturaDigital?exists >
			<div id="divAssinatura">
				<table style="border:0px;">
					<tr>
						<td>
							<#if medicoCoordenador.id?exists>
								<img src="<@ww.url includeParams="none" value="showAssinatura.action?medicoCoordenador.id=${medicoCoordenador.id}"/>" style="display:inline" id="assinaturaImg" width="250px" height="50px">
							</#if>
						</td>
						<td>
							<@ww.checkbox label="Manter assinatura" name="manterAssinatura" onclick="mostraAssinatura()" value="true" labelPosition="left" checked="checked"/>
							<div id="assinaturaUpLoad" style="display:none;">
								<@ww.file label="Nova Assinatura [250 x 50]" name="medicoCoordenador.assinaturaDigital" id="assinaturaUpload"/>
							</div>
						</td>
					</tr>
				</table>
				<hr>
			</div>
        <#else>
			<@ww.file label="Assinatura Digital [250 x 50]" name="medicoCoordenador.assinaturaDigital" id="assinaturaFile"/>
        </#if>

		<@ww.hidden name="medicoCoordenador.id"/>
		<@ww.token/>
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnGravar" accesskey="${accessKey}">
		</button>
		<button onclick="javascript: executeLink('list.action');" class="btnCancelar" accesskey="V">
		</button>
	</div>
</body>
</html>