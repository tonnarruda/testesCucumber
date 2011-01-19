<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/formModal.css"/>');
	</style>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formModal.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ComissaoPeriodoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<script type="text/javascript">
		function pesquisar()
		{
			var nome = document.getElementById("nomeBusca").value;
			var empresaId = <@authz.authentication operation="empresaId"/>;
			var areasIds = getArrayCheckeds(document.getElementById('formPesquisa'), 'areasCheck');

			DWRUtil.useLoadingMessage('Carregando...');
			ColaboradorDWR.getColaboradoresByAreaNome(createListColaborador, areasIds, nome, empresaId);

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
		
		function validaPeriodo(aPartirDe)
		{
			ComissaoPeriodoDWR.validaDataDaComissao(processaValidacao, aPartirDe, ${comissaoPeriodo.id});
		}
		function processaValidacao(data)
		{
			if (!data)
			{
				jAlert('Data inválida, ou fora do período válido da comissão.');
				document.formComissaoPeriodo.aPartirDe.value = '  /  /    ';
			}
		}
	</script>

	<#if comissaoPeriodo.id?exists>
		<title>Editar Período da Comissão</title>
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
	<#assign validarCampos="return validaFormulario('formComissaoPeriodo',new Array('aPartirDe'),new Array('aPartirDe'))"/>
	<#assign validarCamposModal="return validaFormulario('form', new Array('@colaboradorsCheck'), null)"/>

</head>
<body>
	<@ww.actionerror />

	<div id="box">
		<span id="boxtitle"></span>
		<@ww.form name="formPesquisa" id="formPesquisa" action="" onsubmit="pesquisar();return false;" method="POST">
			<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" list="areasCheckList"/>
			<@ww.textfield label="Nome" name="nomeBusca" id="nomeBusca" cssStyle="width:250px;" />

			<button onclick="pesquisar();return false;" class="btnPesquisar"></button>
		</@ww.form>

		<@ww.form name="form" id="form" action="insertComissaoMembro.action" method="POST">
			<@frt.checkListBox label="Colaboradores" name="colaboradorsCheck" list="colaboradorsCheckList"/>
			<@ww.hidden name="comissaoPeriodo.id"/>
			<@ww.hidden name="comissao.id"/>
			<@ww.token/>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="${validarCamposModal};" class="btnGravar"></button>
			<button onclick="closebox();" class="btnCancelar"></button>
		</div>
	</div>

	<@ww.form name="formComissaoPeriodo" action="${formAction}" method="POST">
		<@ww.datepicker label="A partir de" required="true" value="${date}" id="aPartirDe" name="comissaoPeriodo.aPartirDe" cssClass="mascaraData" onchange="validaPeriodo(this.value)"/>
		<br/>Membros da comissão:
		<@display.table name="comissaoMembros" id="comissaoMembro" class="dados">
			<@display.column title="Ações" class="acao" style="width: 40px;">
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='deleteComissaoMembro.action?comissaoMembro.id=${comissaoMembro.id}&comissaoPeriodo.id=${comissaoPeriodo.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
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
		<button onclick="${validarCampos}" class="btnGravar"></button>
		<button onclick="window.location='list.action?comissao.id=${comissao.id}'" class="btnVoltar" accesskey="V"></button>
	</div>

	<#list comissaoMembros as comissaoM>
		<script type="text/javascript">
			document.getElementById("selectFuncao" + ${comissaoM.id}).value = "${comissaoM.funcao}";
			document.getElementById("selectTipo" + ${comissaoM.id}).value = "${comissaoM.tipo}";
		</script>
	</#list>
</body>
</html>