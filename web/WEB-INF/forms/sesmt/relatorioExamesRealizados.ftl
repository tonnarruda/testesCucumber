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
		function filtrarOpcao(opcao)
		{
			if (opcao == 'CANDIDATO_COLABORADOR')
				document.getElementById('divEstabelecimento').style.display = 'none';
			else if (opcao == 'COLABORADOR')
				document.getElementById('divEstabelecimento').style.display = '';
		}

		function filtrarOpcaoRelatorioResumido()
		{
			$('#divRelatResumido').toggle();
			$('#divRelatResumidoResultado').toggle();
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
		
		<@ww.div id="divRelatResumido">
			<@ww.select label="Vínculo" id="vinculo" name="vinculo" list=r"#{'CANDIDATO':'Candidato', 'COLABORADOR':'Colaborador'}" onchange="filtrarOpcao(this.value);"/>
			
			<@ww.textfield label="Colaborador" name="nomeBusca" id="nomeBusca" cssStyle="width: 260px;"/>
			
			<@ww.div id="divEstabelecimento">
				<@frt.checkListBox name="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" />
			</@ww.div>
			
			<@ww.select label="Motivo do Atendimento" name="motivo" id="motivoExame" list="motivos" headerKey="" headerValue="Todos" />
		</@ww.div>
		
		<@ww.select label="Clínica" name="clinicaAutorizada.id" id="clinica" list="clinicas" listKey="id" listValue="nome" headerKey="" headerValue="Todas" />
		<@frt.checkListBox name="examesCheck" label="Exames" list="examesCheckList" />
		
		<@ww.div id="divRelatResumidoResultado">
			<@ww.select label="Resultado do Exame" id="resultado" name="resultado" list=r"#{'':'Todos','NORMAL':'Normal','ANORMAL':'Alterado','NAO_REALIZADO':'Não Informado'}" />
		</@ww.div>
		<@ww.checkbox label="Relatório resumido" id="relatorioExamesPrevistosResumido" name="relatorioExamesPrevistosResumido" labelPosition="left" onchange="filtrarOpcaoRelatorioResumido(this.value);" />
		<div class="buttonGroup">
			<input type="button" value="" onclick="validaFormularioEPeriodo('form',new Array('dataIni','dataFim'),new Array('dataIni','dataFim'));" class="btnRelatorio" />
		</div>

	</@ww.form>
	<script type="text/javascript">
		filtrarOpcao('CANDIDATO_COLABORADOR');
	</script>
</body>
</html>