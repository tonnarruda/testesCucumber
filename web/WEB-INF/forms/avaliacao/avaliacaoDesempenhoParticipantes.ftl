<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
	
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css"/>');
			@import url('<@ww.url value="/css/formModal.css"/>');
			
			<#if isAvaliados>
		    	#menuParticipantes a.ativaAvaliado{border-bottom: 2px solid #5292C0;}
			<#else>
		    	#menuParticipantes a.ativaAvaliador{border-bottom: 2px solid #5292C0;}
		    </#if>
		    
		    #box { height: 500px; }
	  	</style>
	  	
		<@ww.head/>
		<title>Participantes - ${avaliacaoDesempenho.titulo}</title>
		
		<#if isAvaliados>
			<#assign insertAction="insertAvaliados.action"/>
			<#assign deleteAction="deleteAvaliado.action"/>
			<#assign tituloModal="Inserir Avaliado"/>
			<#assign tituloLista="Avaliados"/>
		<#else>
			<#assign insertAction="insertAvaliadores.action"/>
			<#assign deleteAction="deleteAvaliador.action"/>
			<#assign tituloModal="Inserir Avaliador"/>
			<#assign tituloLista="Avaliadores"/>
		</#if>
		
		<#assign gerarAutoAvaliacoesEmLoteAction="gerarAutoAvaliacoesEmLote.action"/>
		
		<#assign validarCamposModal="return validaFormulario('formModal', new Array('@colaboradorsCheck'), null)"/>
		
		<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formModal.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
		
		<script type="text/javascript">
		$(function() {
			$('#tooltipHelp').qtip({
				content: 'Gera uma nova avaliação para cada um dos colaboradores desta avaliação, na qual ele irá avaliar apenas a si próprio.'
			});
		});
		
		function pesquisar()
		{
			var matricula = $("#matriculaBusca").val();
			var nome = $("#nomeBusca").val();
			var empresaId = $("#empresa").val();
			var areasIds = getArrayCheckeds(document.getElementById('formPesquisa'), 'areasCheck');

			DWRUtil.useLoadingMessage('Carregando...');
			ColaboradorDWR.getColaboradoresByAreaNome(createListColaborador, areasIds, nome, matricula, empresaId);

			return false;
		}

		function createListColaborador(data)
		{
			addChecks('colaboradorsCheck',data);
		}

		function populaAreas(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresa(createListAreas, empresaId);
		}

		function createListAreas(data)
		{
			addChecks('colaboradorsCheck');
			addChecks('areasCheck', data);
		}
		
		function excluir()
		{
			newConfirm('Confirma exclusão dos colaboradores selecionados?', function(){ 
				document.form.action = "${deleteAction}";
				document.form.submit();
			});
		}
		
		function gerarAutoAvaliacoesEmLote()
		{
			$.alerts.okButton = '&nbsp;Sim&nbsp;';
			$.alerts.cancelButton = '&nbsp;Não&nbsp;';
			newConfirm('Esta ação irá criar uma nova avaliação para cada um dos colaboradores desta avaliação, na qual ele irá avaliar apenas a si próprio. Deseja continuar?', function(){
				document.form.action = "${gerarAutoAvaliacoesEmLoteAction}";
				document.form.submit();
			});
		}
		</script>
	</head>
	<body>
	
		<#include "avaliacaoDesempenhoLinks.ftl" />
	
		<@ww.actionerror />
		<@ww.form name="form" action="${deleteAction}" method="POST">
			<@display.table name="participantes" id="participante" class="dados">
			
				<#if avaliacaoDesempenho.liberada>
					<@display.column title="">
						<img border="0" src="<@ww.url value="/imgs/no_check.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
					</@display.column>
				<#else>
					<@display.column title="<input type='checkbox' id='md' onclick='atualizaChecks(\"checkParticipante\", this.checked);' />" style="width: 26px; text-align: center;">
						<input type="checkbox" class="checkParticipante" value="${participante.id}" name="participanteIds" />
					</@display.column>
				</#if>
			
				<@display.column title="Ações" class="acao" style="width:40px;">
					<#if avaliacaoDesempenho.liberada>
						<img border="0" title="" src="<@ww.url value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
					<#else>
						<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='${deleteAction}?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}&participanteIds=${participante.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
					</#if>
				</@display.column>
				<@display.column title="${tituloLista}" property="nome" style="width:330px;"/>
				<@display.column title="Cargo" property="faixaSalarial.descricao" style="width:300px;"/>
				<@display.column title="Área Organizacional" property="areaOrganizacional.nome" style="width:300px;"/>
			</@display.table>

			<@ww.hidden name="avaliacaoDesempenho.id"/>
		</@ww.form>
		
		<div class="buttonGroup">
			<#if avaliacaoDesempenho.liberada>
				<button class="btnInserirDesabilitado" disabled="disabled" onmouseover="cursor:pointer;" ></button>
				<button class="btnExcluirDesabilitado" disabled="disabled" onmouseover="cursor:pointer;" ></button>
			<#else>
				<button onclick="openbox('${tituloModal}', 'nomeBusca');" class="btnInserir"></button>
				<button onclick="javascript: excluir();" class="btnExcluir"></button>
			</#if>
			
			<#if !avaliacaoDesempenho.liberada && isAvaliados && (!avaliadors?exists || (avaliadors?exists && avaliadors?size == 0))>
				<button onclick="gerarAutoAvaliacoesEmLote();" class="btnAutoAvaliacoesEmLote"></button>
				<img id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -22px" />
			<#elseif isAvaliados>	
				<button class="btnAutoAvaliacoesEmLoteDesabilitado" onclick="javascript: jAlert('Não é possível gerar autoavaliações em lote a partir de avaliações onde já foram definidos avaliadores.');" style="cursor:pointer;" ></button>
			</#if>
			
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
		
		<!--
		 Modal para Inserir Participantes
		-->
		
		<div id="box">
		<span id="boxtitle"></span>
		<@ww.form name="formPesquisa" id="formPesquisa" action="" onsubmit="pesquisar();return false;" method="POST">
			Empresa: <@ww.select theme="simple" label="Empresa" onchange="populaAreas(this.value);" name="empresaId" id="empresa" list="empresas" listKey="id" listValue="nome" cssStyle="width: 245px;" headerKey="" disabled="!compartilharColaboradores" />
			<br>
			<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" list="areasCheckList" form="document.getElementById('formPesquisa')" filtro="true" selectAtivoInativo="true"/>
			<@ww.textfield label="Matrícula" name="matriculaBusca" id="matriculaBusca" liClass="liLeft" cssStyle="width:80px;"/>
			<@ww.textfield label="Nome" name="nomeBusca" id="nomeBusca" cssStyle="width:410px;"/>
			<button onclick="pesquisar();return false;" class="btnPesquisar"></button>
			<br><br>
		</@ww.form>

		<@ww.form name="formModal" id="formModal" action="${insertAction}" method="POST">
			<@frt.checkListBox label="Colaboradores" name="colaboradorsCheck" list="colaboradorsCheckList" form="document.getElementById('formModal')" filtro="true"/>
			<@ww.hidden name="avaliacaoDesempenho.id"/>
			<@ww.hidden name="isAvaliados"/>
			<@ww.hidden name="empresaId"/>
			<@ww.token/>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="${validarCamposModal};" class="btnGravar"></button>
			<button onclick="closebox();" class="btnCancelar"></button>
		</div>
	</div>
		
	</body>
</html>
