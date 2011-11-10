<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<@ww.head/>
		<title> Extintores - Manutenção e Inspeção</title>
		<#assign validarCampos="return validaFormulario('form', new Array('estabelecimento','data'), new Array('data'))"/>
		
		<#assign date = ""/>
		<#if dataVencimento?exists>
			<#assign date = dataVencimento?date/>
		</#if>
		
		<#include "../ftl/mascarasImports.ftl" />
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		<@ww.form name="form" action="relatorioManutencaoAndInspecao.action" onsubmit="${validarCampos}" method="POST">
			<@ww.select label="Estabelecimento" id="estabelecimento" name="historicoExtintor.estabelecimento.id" list="estabelecimentos" listKey="id" listValue="nome" headerValue="Selecione..." headerKey=""  cssStyle="width:240px;" required="true"/>
			<@ww.datepicker label="Vencimentos até" id="data" name="dataVencimento" cssClass="mascaraData" value="${date}" required="true"/>
			
		<li>
			<fieldset class="fieldsetPadrao">
				<ul>
				<legend>Exibir Extintores com:</legend>
			
				<@ww.checkbox label="Inspeção Vencida" name="inspecaoVencida" labelPosition="left"/>
				<@ww.checkbox label="Carga Vencida" name="cargaVencida" labelPosition="left"/>
				<@ww.checkbox label="Teste Hidrostático Vencido" name="testeHidrostaticoVencido" labelPosition="left"/>
				</ul>
			</fieldset>
		</li>
			
		</@ww.form>
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnRelatorio" ></button>
		</div>
	</body>
</html>