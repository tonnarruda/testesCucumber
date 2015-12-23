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
	
		<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('titulo','inicio','fim','anonima','permiteAutoAvaliacao'), new Array('inicio','fim'))"/>
		<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('titulo','inicio','fim','anonima','permiteAutoAvaliacao','modelo'), new Array('inicio','fim'))"/>
	
		<#include "../ftl/mascarasImports.ftl" />
		
		<script type="text/javascript" >
			$(function() {
				habilitaExibirPerformanceProfissional();
				if($('#avaliacaoDesempnhoId').val() > '0' && !$('#modelo').val() > '0'){
					$('#avaliarSomenteCompetencias').attr('checked', 'checked');
					$('#avaliarSomenteCompetencias').attr('disabled','true');
					exibirModeloAvaliacao()
				}
			});
			
			function habilitaExibirPerformanceProfissional()
			{
				$('#exibirPerformanceProfissional').attr('disabled', $('#anonima').val()=='true');
			}
			
			function exibirModeloAvaliacao(){
				if($('#avaliarSomenteCompetencias').is(':checked')){
					$('#modelo').attr('disabled','true');
					$('#modelo').parent().parent().find('.req').hide();
				}
				else{
					$('#modelo').attr('disabled',false);
					$('#modelo').parent().parent().find('.req').show();
				}
			}
			function enviarForm(){
				if($('#avaliarSomenteCompetencias').is(':checked'))
					return validaFormularioEPeriodo('form', new Array('titulo','inicio','fim','anonima','permiteAutoAvaliacao'), new Array('inicio','fim'));
				else
					return validaFormularioEPeriodo('form', new Array('titulo','inicio','fim','anonima','permiteAutoAvaliacao', 'modelo'), new Array('inicio','fim'));
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
			
			<#if temAvaliacoesRespondidas>
				<#assign desabilita="true"/>
				<@ww.hidden name="avaliacaoDesempenho.permiteAutoAvaliacao" />
				<@ww.hidden name="avaliacaoDesempenho.anonima" />
				<#if avaliacaoDesempenho.avaliacao?exists && avaliacaoDesempenho.avaliacao.id?exists>
					<@ww.hidden name="avaliacaoDesempenho.avaliacao.id" />
				</#if>
			<#else>
				<#assign desabilita="false"/>
			</#if>
			
			<@ww.checkbox label="Exibir resultado ao colaborador após autoavaliação" id="exibeResultadoAutoAvaliacao" name="avaliacaoDesempenho.exibeResultadoAutoAvaliacao" labelPosition="left"/>
			<@ww.checkbox label="Avaliar somente as competências exigidas pelo cargo" id="avaliarSomenteCompetencias" name="" labelPosition="left" onchange="exibirModeloAvaliacao()"/>
			<@ww.select label="Modelo da Avaliação" name="avaliacaoDesempenho.avaliacao.id" id="modelo" required="true" list="avaliacaos" listKey="id" listValue="titulo" cssStyle="width: 450px;" headerKey="" headerValue="Selecione..." disabled="${desabilita}" />
			<@ww.checkbox label="Exibir em Performance Profissional" id="exibirPerformanceProfissional" name="avaliacaoDesempenho.exibirPerformanceProfissional"  labelPosition="left"/>
			<@ww.select label="Permitir autoavaliação" name="avaliacaoDesempenho.permiteAutoAvaliacao" disabled="${desabilita}" id="permiteAutoAvaliacao" list=r"#{true:'Sim',false:'Não'}" required="true" headerKey="" headerValue=""/>
			<@ww.select label="Anônima" name="avaliacaoDesempenho.anonima" id="anonima" list=r"#{true:'Sim',false:'Não'}" disabled="${desabilita}" required="true" headerKey="" headerValue="" onchange="habilitaExibirPerformanceProfissional();"/>
			
			
			<@ww.hidden id="avaliacaoDesempnhoId" name="avaliacaoDesempenho.id" />
			<@ww.hidden name="avaliacaoDesempenho.liberada" />
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="enviarForm()" class="${classBotao}"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
