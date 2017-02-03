<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
<@ww.head/>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>

	<title>Atualizar Modelo de Avaliação</title>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<script type="text/javascript">
		function populaColaboradores()
		{
			dwr.util.useLoadingMessage('Carregando...');
			var areasIds = getArrayCheckeds(document.forms[0], 'areaCheck');
			var estabelecimentoIds = getArrayCheckeds(document.forms[0], 'estabelecimentoCheck');
			
			ColaboradorDWR.getByAreaEstabelecimentoEmpresas(areasIds, estabelecimentoIds, <@authz.authentication operation="empresaId"/>, null, "A", false, createListcolaborador);
		}

		function createListcolaborador(data)
		{
			addChecksByMap('colaboradorCheck',data);
		}
		
		function populaArea()
		{
			dwr.util.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresa(<@authz.authentication operation="empresaId"/>, createListArea);
		}

		function createListArea(data)
		{
			addChecksByMap('areaCheck', data, 'populaColaboradores()');
		}
		
		function populaChecks()
		{
			$('#wwctrl_areaCheck [type="checkbox"]').attr('checked', false);
			$('#wwctrl_estabelecimentoCheck [type="checkbox"]').attr('checked', false);
			
			dwr.engine.setAsync(false);
			
			populaArea();
			populaColaboradores();
		}
		
		$(function() 
		{
			<#if avaliacoes?exists>
				<#list avaliacoes as avaliacao>
					<#if avaliacao.periodoExperiencia?exists && avaliacao.periodoExperiencia.id?exists>
				    	$('#modeloPeriodo' + ${avaliacao.periodoExperiencia.id}).append("<option value='${avaliacao.id}'>${avaliacao.titulo}</option>");
				    	$('#modeloPeriodoGestor' + ${avaliacao.periodoExperiencia.id}).append("<option value='${avaliacao.id}'>${avaliacao.titulo}</option>");
					</#if>
				</#list>
			</#if>
			
			populaChecks();
			
			
		});
	
		function validarCampos()
		{
			if(validaFormulario('form', new Array('@colaboradorCheck'), null, true))
			{
				var qtdItensSelecionados = $("select[name^='colaboradorAvaliacoes'] option:selected[value!='']").size();
			
				if(qtdItensSelecionados == 0)
				{
					aletaItensNaoSelecionados();
				}else {
					$('#form').submit();
				}
			}
		}
		
		function aletaItensNaoSelecionados()
		{
			var msg = "Nenhum acompanhamento do período de experiência foi selecionado. </br>" +
						"Deseja realmente remover todos as configurações do acompanhamento do período de experiência para os colaboradores selecionados?";
			
			$('<div>'+ msg +'</div>').dialog({title: "Confirmação de configuração do acompanhamento do período de experiência",
													modal: true, 
													height: 170,
													width: 700,
													buttons: [
													    {
													        text: "Sim",
													        click: function() { $('#form').submit(); }
													    },
													    {
													        text: "Não",
													        click: function() { $(this).dialog("close"); }
													    }
													] 
											});
			
		}
		
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
		
	<@ww.form id="form" action="atualizarModeloAvaliacao" name="form" method="POST">
	
		<@frt.checkListBox name="estabelecimentoCheck" label="Estabelecimento" list="estabelecimentoCheckList" width="718" height="120" onClick="populaColaboradores($('#empresa').val());" filtro="true"/>
		<@frt.checkListBox name="areaCheck" label="Área Organizacional" list="areaCheckList" width="718" height="120" onClick="populaColaboradores($('#empresa').val());" filtro="true" selectAtivoInativo="true"/>
		<@frt.checkListBox id="colaboradorCheck" name="colaboradorCheck" label="Colaborador*" list="colaboradorCheckList" width="718" height="180" filtro="true"/>

		<div>
			<fieldset style="width:700px;">
				<legend>Colaborador</legend>
				<#assign i = 0 />
				<@display.table name="periodoExperiencias" id="periodoExperiencia" class="dados" style="width:700px;">
					<@display.column title="Dias" property="dias" style="width:80px;" />
					<@display.column title="Modelo do Acompanhamento do Período de Experiência">
						<@ww.hidden name="colaboradorAvaliacoes[${i}].periodoExperiencia.id" value="${periodoExperiencia.id}" />
						<@ww.select theme="simple" name="colaboradorAvaliacoes[${i}].avaliacao.id" class="modeloPeriodo" id="modeloPeriodo${periodoExperiencia.id}" headerKey="" headerValue="Selecione" cssStyle="width: 650px;"/>
					</@display.column>
					
					<#assign i = i + 1 />
				</@display.table>
			</fieldset>

			<br />

			<fieldset style="width:700px;">
				<legend>Gestor</legend>
				<#assign i = 0 />
				<@display.table name="periodoExperiencias" id="periodoExperiencia" class="dados" style="width:700px;">
					<@display.column title="Dias" property="dias" style="width:80px;" />
					<@display.column title="Modelo do Acompanhamento do Período de Experiência">
						<@ww.hidden name="colaboradorAvaliacoesGestor[${i}].periodoExperiencia.id" value="${periodoExperiencia.id}" />
						<@ww.select theme="simple" name="colaboradorAvaliacoesGestor[${i}].avaliacao.id" class="modeloPeriodoGestor" id="modeloPeriodoGestor${periodoExperiencia.id}" headerKey="" headerValue="Selecione" cssStyle="width: 650px;"/>
					</@display.column>
					
					<#assign i = i + 1 />
				</@display.table>
			</fieldset>

	    </div>
    	<div class="buttonGroup">
			<button id="gravar" class="btnGravar" onclick="validarCampos();" type="button"> </button>
		</div>
	    
	</@ww.form>
</body>
</html>