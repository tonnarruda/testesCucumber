<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url value="/css/formModal.css?version=${versao}"/>');
		#menuEleicao a.ativaComissao{border-bottom: 2px solid #5292C0;}
		#eleicao { height: 25px; }
		
	</style>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formModal.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

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
	</script>

	<title></title>

	<#assign validarCampos="return validaFormulario('form', new Array('@colaboradorsCheck'), null)"/>
</head>
<body>
	<#include "eleicaoLinks.ftl" />

	<div id="box">
		<span id="boxtitle"></span>
		<@ww.form name="formPesquisa" id="formPesquisa" action="" onsubmit="pesquisar();return false;" method="POST">
			<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" list="areasCheckList" filtro="true"/>
			<@ww.textfield label="Matrícula" name="matriculaBusca" id="matriculaBusca" cssStyle="width:80px;" liClass="liLeft"/>
			<@ww.textfield label="Nome" name="nomeBusca" id="nomeBusca" cssStyle="width:410px;" />

			<button onclick="pesquisar();return false;" class="btnPesquisar"></button>
		</@ww.form>

		<@ww.form name="form" id="form" action="insert.action" onsubmit="${validarCampos}" validate="true" method="POST">
			<@frt.checkListBox label="Colaboradores" name="colaboradorsCheck" list="colaboradorsCheckList" form="document.getElementById('form')" filtro="true"/>

			<@ww.hidden name="eleicao.id"/>
			<@ww.token/>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="closebox();" class="btnCancelar"></button>
		</div>
	</div>
	
<div id="eleicao">
<h3><b>Eleição: ${eleicao.descricao}</b></h3>
</div>	

	<@ww.form name="formList" id="formList" action="saveFuncao.action" method="POST">
		<@display.table name="comissaoEleicaos" id="comissaoEleicao" class="dados">
			<@display.column title="Ações" class="acao" style="width: 40px;">
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?comissaoEleicao.id=${comissaoEleicao.id}&eleicao.id=${eleicao.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			</@display.column>
			<@display.column property="colaborador.nome" title="Nome"/>
			<@display.column title="Função" style="width: 280px;text-align:center;">
		        <@ww.select name="funcaoComissaos" list="funcaoComissaoEleitoral" theme="simple" id="select${comissaoEleicao.id}" />
		        <@ww.hidden name="comissaoEleicaoIds" value="${comissaoEleicao.id}"/>
			</@display.column>
		</@display.table>

		<@ww.hidden name="eleicao.id"/>
	</@ww.form>
	<div class="buttonGroup">
		<button class="btnInserirMembro" onclick="limpaForm();openbox('Inserir Comissão', 'nomeBusca');" ></button>
		<br/>
		<button class="btnGravar" onclick="enviaForm();" ></button>
	</div>

	<#list comissaoEleicaos as comissao>
		<script type="text/javascript">
			document.getElementById("select" + ${comissao.id}).value = "${comissao.funcao}";
		</script>
	</#list>
</body>
</html>