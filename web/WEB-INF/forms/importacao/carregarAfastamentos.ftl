<html>
<head>
<@ww.head/>

	<title>Importação de Afastamentos</title>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>
	
	<#assign validarCampos="return validaFormulario('form', new Array('arquivo'), null)"/>

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
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form name="form" action="importarAfastamentos.action" validate="true" onsubmit="${validarCampos}" method="POST" enctype="multipart/form-data">
		<@ww.hidden name="nomeArquivo"/>
		
		<#assign i = 0/>
		<table class="dados">
			<thead>
				<tr>
					<th>Descrição do Afastamento no TRU</th>
					<th>Descrição do Afastamento no RH</th>
				</tr>
			</thead>
			<tbody>
				<#list motivos as motivo>
					<tr class="<#if i % 2 == 0>odd<#else>even</#if>">
						<td>${motivo}</td>
						<td>
							<@ww.select theme="simple" name="afastamentos[${i}]" listKey="id" listValue="descricao" headerKey="" headerValue="${motivo}" list="afastamentos"/>
						</td>
					</tr>
					<#assign i = i + 1/>
				</#list>
			</tbody>
		</table>
		
		<@ww.token/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnImportar" accesskey="I">
		</button>
	</div>
</body>
</html>