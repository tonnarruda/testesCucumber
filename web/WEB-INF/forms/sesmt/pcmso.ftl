<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<html>
<head>
<@ww.head/>

	<title>PCMSO - Programa de Controle Médico de Saúde Ocupacional</title>
	
	<#assign formAction="gerarRelatorio.action"/>
	
	<#assign date = "" />
    <#if data?exists>
		<#assign date = data?date/>
	</#if>
	
	<#if dataIni?exists >
		<#assign inicio = dataIni?date/>
	<#else>
		<#assign inicio = ""/>
	</#if>
	<#if dataFim?exists>
		<#assign fim = dataFim?date/>
	<#else>
		<#assign fim = ""/>
	</#if>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('dataIni','dataFim','estabelecimento'), new Array('dataIni','dataFim'))"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}"  method="POST">
		Período:*<br>
		<@ww.datepicker name="dataIni" id="dataIni" value="${inicio}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="dataFim" id="dataFim" value="${fim}" cssClass="mascaraData validaDataFim" />
		
		<@ww.select label="Estabelecimento" id="estabelecimento" name="estabelecimento.id" required="true" list="estabelecimentos" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="" cssStyle="width:240px;"/>

		<li>
			<fieldset class="fieldsetPadrao">
				<ul>
					<legend>Exibir:</legend>
					<@ww.checkbox label="Agenda" name="exibirAgenda" labelPosition="left"/>
					<@ww.checkbox label="Distribuição de Colaboradores por Setor" name="exibirDistColaboradorSetor" labelPosition="left"/>
					<@ww.checkbox label="Riscos Ambientais" name="exibirRiscos" labelPosition="left"/>
					<@ww.checkbox label="EPIs por Função" name="exibirEpis" labelPosition="left"/>
					<@ww.checkbox label="Tabela de Exames Realizados" name="exibirExames" labelPosition="left"/>
					<@ww.checkbox label="Acidentes de Trabalho" name="exibirAcidentes" labelPosition="left"/>
					<@ww.checkbox label="Composição do SESMT" name="exibirComposicaoSesmt" labelPosition="left"/>
				</ul>
			</fieldset>
		</li>

	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio"></button>
	</div>
</body>
</html>