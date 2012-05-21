<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<title>Exames Realizados</title>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>

	<#include "../ftl/mascarasImports.ftl" />

	<@ww.head/>

	<#if inicio?exists>
		<#assign dateIni = inicio?date/>
	<#else>
		<#assign dateIni = ""/>
	</#if>
	<#if fim?exists>
		<#assign dateFim = fim?date/>
	<#else>
		<#assign dateFim = ""/>
	</#if>

	<script type="text/javascript">
		$(function() {
			filtrarOpcao('CANDIDATO');
			filtrarOpcaoRelatorioResumido();
		});
	
		function filtrarOpcao(opcao)
		{
			$('#divEstabelecimento').toggle(opcao == 'COLABORADOR');
			$("label[for='nomeBusca']").text(opcao=='COLABORADOR' ? 'Colaborador:' : 'Candidato:');	
		}

		function filtrarOpcaoRelatorioResumido()
		{
			var relatorioResumido = $('#relatorioExamesPrevistosResumido').is(':checked');
		
			$('.divRelatResumido').toggle( !relatorioResumido );
			$('.divRelatResumidoResultado').toggle( !relatorioResumido );
		}
	</script>

<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="relatorioExamesRealizados.action" onsubmit="enviaForm();" method="POST" id="formBusca">

		Período:*<br>
		<@ww.datepicker name="inicio" id="dataIni" value="${dateIni}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="fim" id="dataFim" value="${dateFim}" cssClass="mascaraData validaDataFim" />
		
		<@ww.checkbox label="Relatório resumido" id="relatorioExamesPrevistosResumido" name="relatorioExamesPrevistosResumido" labelPosition="left" onchange="filtrarOpcaoRelatorioResumido();" />
		
		<div class="divRelatResumido">
			<@ww.select label="Vínculo" id="vinculo" name="vinculo" list=r"#{'CANDIDATO':'Candidato', 'COLABORADOR':'Colaborador'}" onchange="filtrarOpcao(this.value);"/>
			
			<@ww.textfield label="Candidato" name="nomeBusca" id="nomeBusca" cssStyle="width: 260px;"/>
			
			<@ww.select label="Motivo do Atendimento" name="motivo" id="motivoExame" list="motivos" headerKey="" headerValue="Todos" />
		</div>
		
		<@ww.select label="Clínica" name="clinicaAutorizada.id" id="clinica" list="clinicas" listKey="id" listValue="nome" headerKey="" headerValue="Todas" />
		
		<div class="divRelatResumido">
			<@ww.div id="divEstabelecimento">
				<@frt.checkListBox name="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" />
			</@ww.div>
		</div>
		
		<@frt.checkListBox name="examesCheck" label="Exames" list="examesCheckList" />
		
		<@ww.div class="divRelatResumidoResultado">
			<@ww.select label="Resultado do Exame" id="resultado" name="resultado" list=r"#{'':'Todos','NORMAL':'Normal','ANORMAL':'Alterado','NAO_REALIZADO':'Não Informado'}" />
		</@ww.div>
		
		<div class="buttonGroup">
			<input type="button" value="" onclick="validaFormularioEPeriodo('form',new Array('dataIni','dataFim'),new Array('dataIni','dataFim'));" class="btnRelatorio" />
		</div>
	</@ww.form>
</body>
</html>