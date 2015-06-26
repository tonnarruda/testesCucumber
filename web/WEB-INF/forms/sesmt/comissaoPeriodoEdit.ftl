<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url value="/css/formModal.css?version=${versao}"/>');
	</style>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formModal.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ComissaoPeriodoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

	<#assign validarCampos="return validaFormulario('formComissaoPeriodo',new Array('aPartirDe'),new Array('aPartirDe'))"/>

	<script type="text/javascript">
		function pesquisar()
		{
			var matricula = document.getElementById("matriculaBusca").value;
			var nome = document.getElementById("nomeBusca").value;
			var empresaId = <@authz.authentication operation="empresaId"/>;
			var areasIds = getArrayCheckeds(document.getElementById('formPesquisa'), 'areasCheck');

			DWRUtil.useLoadingMessage('Carregando...');
			ColaboradorDWR.getColaboradoresByAreaNome(createListColaborador, areasIds, nome, matricula, empresaId);

			return false;
		}

		function createListColaborador(data)
		{
			addChecks('colaboradorsCheck',data);
		}

		function limpaForm()
		{
			document.getElementById("nomeBusca").value = "";
			document.getElementById("listCheckBoxcolaboradorsCheck").style.background = "#FFF";
			addChecks('colaboradorsCheck', null);
		}

		function enviaForm()
		{
			validaFormulario('formList', null, null);
		}
		
		function validaPeriodoClonado(aPartirDe)
		{
			ComissaoPeriodoDWR.validaDataDaComissao(processaValidacao, aPartirDe, ${comissaoPeriodo.id});
		}
		function processaValidacao(data)
		{
			if (!data)
				jAlert('Data inválida, fora do período válido da comissão ou existe uma comissão com essa data.');
			else
				${validarCampos};
		}
	</script>
	
	<#if clonar>
		<#assign msgClonar="(clonado)"/>
	<#else>
		<#assign msgClonar=""/>
	</#if>	
	
	<#if comissaoPeriodo.id?exists>
		<title>Editar Período da Comissão ${msgClonar}</title>
		<#assign formAction="update.action"/>
	<#else>
		<title>Novo Período da Comissão</title>
		<#assign formAction="insert.action"/>
	</#if>

	<#include "../ftl/mascarasImports.ftl" />

	<#assign date = ""/>
	<#if comissaoPeriodo.aPartirDe?exists>
		<#assign date = comissaoPeriodo.aPartirDe?date/>
	</#if>
	<#assign validarCamposModal="return validaFormulario('form', new Array('@colaboradorsCheck'), null)"/>

</head>
<body>
	<@ww.actionerror />

	<div id="box">
		<span id="boxtitle"></span>
		<@ww.form name="formPesquisa" id="formPesquisa" action="" onsubmit="pesquisar();return false;" method="POST">
			<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" list="areasCheckList" filtro="true"/>
			<@ww.textfield label="Matrícula" name="matriculaBusca" id="matriculaBusca" cssStyle="width:80px;" liClass="liLeft"/>
			<@ww.textfield label="Nome" name="nomeBusca" id="nomeBusca" cssStyle="width:410px;" />

			<button onclick="pesquisar();return false;" class="btnPesquisar"></button>
		</@ww.form>

		<@ww.form name="form" id="form" action="insertComissaoMembro.action" method="POST">
			<@frt.checkListBox label="Colaboradores" name="colaboradorsCheck" list="colaboradorsCheckList"  filtro="true"/>
			<@ww.hidden name="comissaoPeriodo.id"/>
			<@ww.hidden name="comissao.id"/>
			<@ww.hidden name="clonar"/>
			<@ww.token/>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="${validarCamposModal};" class="btnGravar"></button>
			<button onclick="closebox();" class="btnCancelar"></button>
		</div>
	</div>

	<@ww.form name="formComissaoPeriodo" action="${formAction}" method="POST">
		<@ww.datepicker label="A partir de" required="true" value="${date}" id="aPartirDe" name="comissaoPeriodo.aPartirDe" cssClass="mascaraData"/>
		<br/>Membros da comissão:
		<@display.table name="comissaoMembros" id="comissaoMembro" class="dados">
			<@display.column title="Ações" class="acao" style="width: 40px;">
				<#if clonar || comissaoMembro.permitirExcluir>
					<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='deleteComissaoMembro.action?comissaoMembro.id=${comissaoMembro.id}&comissaoPeriodo.id=${comissaoPeriodo.id}&clonar=true'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
				<#else>
					<img border="0" title="Não é possível excluir o membro da comissão, pois o mesmo já participou de uma reuniao da comissão." src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				</#if>
			</@display.column>
			<@display.column property="colaborador.nome" title="Nome"/>
			<@display.column title="Função" style="width: 280px;text-align:center;">
		        <@ww.select name="funcaoComissaos" list="funcoes" theme="simple" id="selectFuncao${comissaoMembro.id}" />
			</@display.column>
			<@display.column title="Tipo" style="width: 280px;text-align:center;">
		        <@ww.select name="tipoComissaos" list="tipos" theme="simple" id="selectTipo${comissaoMembro.id}" />
		        <@ww.hidden name="comissaoMembroIds" value="${comissaoMembro.id}"/>
			</@display.column>
		</@display.table>
		<@ww.hidden name="comissao.id" />
		<@ww.hidden name="comissaoPeriodo.id" />
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnInserirMembro" onclick="limpaForm();openbox('Inserir Comissão', 'nomeBusca');"></button>
		<br/>
		<button onclick="validaPeriodoClonado($('#aPartirDe').val());" class="btnGravar"></button>

		<#if !clonar>
			<button onclick="window.location='list.action?comissao.id=${comissao.id}'" class="btnVoltar" accesskey="V"></button>
		</#if>
	</div>

	<#list comissaoMembros as comissaoM>
		<script type="text/javascript">
			document.getElementById("selectFuncao" + ${comissaoM.id}).value = "${comissaoM.funcao}";
			document.getElementById("selectTipo" + ${comissaoM.id}).value = "${comissaoM.tipo}";
		</script>
	</#list>
</body>
</html>