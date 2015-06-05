<html>
	<head>
	
		<@ww.head/>
		<#if avaliacaoDesempenho?exists && avaliacaoDesempenho.id?exists>
			<title>Editar Avaliação de Desempenho</title>
			<#assign formAction="update.action"/>
			<#assign classBotao="btnGravar"/>
		<#else>
			<title>Inserir Avaliação de Desempenho</title>
			<#assign formAction="insert.action"/>
			<#assign classBotao="btnAvancar"/>
		</#if>
	
		<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('titulo','inicio','fim','anonima','permiteAutoAvaliacao','modelo'), new Array('inicio','fim'))"/>
	
		<#include "../ftl/mascarasImports.ftl" />
		
		<script type="text/javascript" >
			$(function() {
				habilitaExibirPerformanceProfissional();
			});
			
			function habilitaExibirPerformanceProfissional()
			{
				$('#exibirPerformanceProfissional').attr('disabled', $('#anonima').val()=='true');
			}
		</script>
		
	</head>
	<body>
	
		<#if avaliacaoDesempenho?exists && avaliacaoDesempenho.id?exists && avaliacaoDesempenho.inicio?exists>
			<#assign inicio=avaliacaoDesempenho.inicio?date />
		<#else>
			<#assign inicio="" />
		</#if>
		<#if avaliacaoDesempenho?exists && avaliacaoDesempenho.id?exists && avaliacaoDesempenho.fim?exists>
			<#assign fim=avaliacaoDesempenho.fim?date />
		<#else>
			<#assign fim="" />
		</#if>
	
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" method="POST">
			<@ww.textfield label="Título" name="avaliacaoDesempenho.titulo" id="titulo" cssClass="inputNome" maxLength="100" required="true" />
			
			<@ww.datepicker label="Período" required="true" value="${inicio}" name="avaliacaoDesempenho.inicio" id="inicio" cssClass="mascaraData validaDataIni" after="a" liClass="liLeft"/>
			<@ww.datepicker label="" value="${fim}" name="avaliacaoDesempenho.fim" id="fim" cssClass="mascaraData validaDataFim"/>
			
			
			<#if temParticipantesAssociados>
				<#assign desabilita="true"/>
				<@ww.hidden name="avaliacaoDesempenho.permiteAutoAvaliacao" />
				<@ww.hidden name="avaliacaoDesempenho.anonima" />
				<@ww.hidden name="avaliacaoDesempenho.avaliacao.id" />
			<#else>
				<#assign desabilita="false"/>
			</#if>
			
			<@ww.select label="Modelo da Avaliação" name="avaliacaoDesempenho.avaliacao.id" id="modelo" required="true" list="avaliacaos" listKey="id" listValue="titulo" cssStyle="width: 450px;" headerKey="" headerValue="Selecione..." disabled="${desabilita}" />
			<@ww.select label="Anônima" name="avaliacaoDesempenho.anonima" id="anonima" list=r"#{true:'Sim',false:'Não'}" disabled="${desabilita}" required="true" headerKey="" headerValue="" onchange="habilitaExibirPerformanceProfissional();"/>
			<@ww.checkbox label="Exibir em Performance Profissional" id="exibirPerformanceProfissional" name="avaliacaoDesempenho.exibirPerformanceProfissional"  labelPosition="left"/>
			<@ww.select label="Permitir autoavaliação" name="avaliacaoDesempenho.permiteAutoAvaliacao" disabled="${desabilita}" id="permiteAutoAvaliacao" list=r"#{true:'Sim',false:'Não'}" required="true" headerKey="" headerValue=""/>
			
			<@ww.hidden name="avaliacaoDesempenho.id" />
			<@ww.hidden name="avaliacaoDesempenho.liberada" />
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="${classBotao}"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
