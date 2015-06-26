<html>
<head>
<@ww.head/>

	<title>Correlação de Motivos de Afastamentos</title>
	
	<#assign validarCampos="return validaFormulario('form', null, null)"/>

	<#if dataDe?exists>
		<#assign dataDeTmp = dataDe?date/>
	<#else>
		<#assign dataDeTmp = ""/>
	</#if>
	<#if dataAte?exists>
		<#assign dataAteTmp = dataAte?date/>
	<#else>
		<#assign dataAteTmp = ""/>
	</#if>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		
		.info { margin-bottom: 10px; }
		.info ul { margin-left: 15px; }
		.info ul li { list-style-type: disc; }
		.afastamentos { width: 440px; }
	</style>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<div class="info">
		<ul>
			<li>Selecione o Motivo de Afastamento no RH que corresponda a cada motivo contido no arquivo importado;</li>
			<li>Caso nenhuma opção seja selecionada, o sistema tentará relacionar automaticamente buscando pela descrição;</li>
			<li>Se nenhum afastamento for encontrado, o sistema criará um novo motivo de afastamento com a descrição informada no TRU.</li>
		<ul>
	</div>
	
	<@ww.form name="form" action="importarAfastamentos.action" validate="true" onsubmit="${validarCampos}" method="POST" enctype="multipart/form-data">
		<@ww.hidden name="pathArquivo"/>
		
		<table class="dados">
			<thead>
				<tr>
					<th>Descrição do Afastamento no TRU</th>
					<th>Descrição do Afastamento no RH</th>
				</tr>
			</thead>
			<tbody>
				<#assign i = 0/>
				<#list afastamentos?keys as afastamento>
					<tr class="<#if i % 2 == 0>odd<#else>even</#if>">
						<td width="50%">${afastamento}</td>
						<td>
							<@ww.select theme="simple" name="afastamentos['${afastamento}']" cssClass="afastamentos" listKey="id" listValue="descricao" headerKey="" headerValue="< relacionar automaticamente >" list="afastamentosRH"/>
						</td>
					</tr>
					<#assign i = i + 1/>
				</#list>
			</tbody>
		</table>
		
		<@ww.token/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnImportar" accesskey="I"></button>
	</div>
</body>
</html>