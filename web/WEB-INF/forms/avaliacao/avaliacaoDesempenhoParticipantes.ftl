<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
	
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css"/>');
			@import url('<@ww.url value="/css/formModal.css"/>');
			
			<#if isAvaliados>
		    	#menuParticipantes a.ativaAvaliado{color: #FFCB03;}
			<#else>
		    	#menuParticipantes a.ativaAvaliador{color: #FFCB03;}
		    </#if>
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
		
		<#assign validarCamposModal="return validaFormulario('formModal', new Array('@colaboradorsCheck'), null)"/>
		
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formModal.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
		
		<script type="text/javascript">
		function pesquisar()
		{
			var nome = jQuery("#nomeBusca").val();
			var empresaId = jQuery("#empresa").val();
			var areasIds = getArrayCheckeds(document.getElementById('formPesquisa'), 'areasCheck');

			DWRUtil.useLoadingMessage('Carregando...');
			ColaboradorDWR.getColaboradoresByAreaNome(createListColaborador, areasIds, nome, empresaId);

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
			addChecks('areasCheck',data)
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
						<a href="#" onclick="if (confirm('Confirma exclusão?')) window.location='${deleteAction}?avaliacaoDesempenho.id=${avaliacaoDesempenho.id}&participanteIds=${participante.id}'"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
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
				<button onclick="javascript: if (confirm('Confirma exclusão das mensagens selecionadas?')) document.form.submit();" class="btnExcluir"></button>
			</#if>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
		
		<!--
		 Modal para Inserir Participantes
		-->
		
		<div id="box">
		<span id="boxtitle"></span>
		<@ww.form name="formPesquisa" id="formPesquisa" action="" onsubmit="pesquisar();return false;" method="POST">
			Empresa: <@ww.select theme="simple" label="Empresa" onchange="populaAreas(this.value);" name="empresaId" id="empresa" list="empresas" listKey="id" listValue="nome" cssStyle="width: 245px;" headerKey="" />
			<br>
			<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" list="areasCheckList" form="document.getElementById('formPesquisa')"/>
			Colaborador: <@ww.textfield theme="simple" label="Nome" name="nomeBusca" id="nomeBusca" cssStyle="width:410px;"/>
			<br>
			<button onclick="pesquisar();return false;" class="btnPesquisar"></button>
			<br><br>
		</@ww.form>

		<@ww.form name="formModal" id="formModal" action="${insertAction}" method="POST">
			<@frt.checkListBox label="Colaboradores" name="colaboradorsCheck" list="colaboradorsCheckList" form="document.getElementById('formModal')"/>
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
