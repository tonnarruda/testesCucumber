<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/formModal.css"/>');
		#menuEleicao a.ativaCalendario{  border-bottom: 2px solid #5292C0;}
		#eleicao{height: 25px; }
	</style>
	<script src='<@ww.url includeParams="none" value="/js/functions.js"/>'></script>
	<script src='<@ww.url includeParams="none" value="/js/formModal.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EtapaProcessoEleitoralDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<title></title>

	<#assign validarCampos="return validaFormulario('form',new Array('etapaNome','etapaPrazo'),new Array('etapaPrazo'));"/>

	<script>
		function limpaForm()
		{
			resetFormulario(new Array('etapaNome','etapaPrazoLegal','etapaPrazo','etapaId'));
			document.form.action="insertCalendarioEleicao.action"
		}

		function preparaDadosUpdate(etapaId)
		{
			DWREngine.setErrorHandler(errorPreparaDados);
			EtapaProcessoEleitoralDWR.prepareDadosEtapa(carregaDados,etapaId)
		}

		function errorPreparaDados(msg)
		{
			jAlert(msg);
		}

		function carregaDados(data)
		{
			//campos do form e hidden
			document.getElementById("etapaNome").value = data.etapaNome;
			document.getElementById("etapaPrazoLegal").value = data.etapaPrazoLegal;
			document.getElementById("etapaPrazo").value = data.etapaPrazo;
			document.getElementById("etapaId").value = data.etapaId;
			document.getElementById("empresaId").value = data.empresaId;
			//action
			document.form.action="updateCalendarioEleicao.action"
			openbox('Editar Etapa', 'etapaNome');
		}
	</script>

</head>
<body>
	<#include "eleicaoLinks.ftl" />
	<#include "../ftl/mascarasImports.ftl" />

	<div id="eleicao">
	<h3><B>Eleição: ${eleicao.descricao}</B></h3>
	</div>
	<@display.table name="etapaProcessoEleitorals" id="etapaProcessoEleitoral" class="dados" >
		<@display.column title="Ações" class="acao">
			<a href="#" onclick="preparaDadosUpdate(${etapaProcessoEleitoral.id});"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='deleteCalendarioEleicao.action?etapaProcessoEleitoral.id=${etapaProcessoEleitoral.id}&eleicao.id=${eleicao.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Etapa" style="width:320px;"/>
		<@display.column property="prazoLegal" title="Prazo Legal" style="width:250px;"/>
		<@display.column property="dataFormatadaPrazo" title="Data" style="width:250px;"/>
	</@display.table>

	<div class="buttonGroup">
		<button onclick="limpaForm(); openbox('Inserir Etapa', 'etapaNome');" accesskey="N" class="btnInserir">
		</button>
		<#if (etapaProcessoEleitorals?exists && etapaProcessoEleitorals?size>0)>
			<button class="btnImprimirPdf" onclick="window.location='imprimirCalendario.action?eleicao.id=${eleicao.id}'" ></button>
		</#if>
	</div>

	<#--
		 Modal de edição da Etapa da eleição
	-->
	<div id="filter"></div>
	<div id="box">
		<span id="boxtitle"></span>
		<@ww.form name="form" action="insertCalendarioEleicao.action" method="POST">
			<@ww.textfield label="Nome" id="etapaNome" name="etapaProcessoEleitoral.nome" value="" required="true" maxlength="100" cssStyle="width:400px;" />
			<@ww.textfield label="Prazo Legal" id="etapaPrazoLegal" value="" name="etapaProcessoEleitoral.prazoLegal" cssStyle="width:400px;"/>
			<@ww.datepicker label="Prazo" id="etapaPrazo" name="etapaProcessoEleitoral.data" cssClass="mascaraData" required="true"/>

			<@ww.hidden name="etapaProcessoEleitoral.id" id="etapaId" value=""/>
			<@ww.hidden name="etapaProcessoEleitoral.empresa.id" id="empresaId" />
			<@ww.hidden name="etapaProcessoEleitoral.eleicao.id" value="${eleicao.id}"/>
			<@ww.hidden name="eleicao.id" />
		</@ww.form>
			<div class="buttonGroup">
				<button onclick="${validarCampos}" class="btnGravar"></button>
				<button onclick="return closebox();" class="btnCancelar"></button>
			</div>
	</div>

</body>
</html>