<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/formModal.css"/>');
		#menuEleicao a.ativaCandidato{  border-bottom: 2px solid #5292C0;}
		#eleicao { height: 25px; }
	</style>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formModal.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<script type='text/javascript'>
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
			addChecks('candidatosCheck',data);
		}

		function limpaForm()
		{
			document.getElementById("nomeBusca").value = "";
			document.getElementById("listCheckBoxcandidatosCheck").style.background = "#FFF";
			addChecks('candidatosCheck', null);
		}
					
	</script>

	<title></title>

	<#assign validarCampos="return validaFormulario('form', new Array('@candidatosCheck'), null)"/>
</head>
<body>
	<#include "eleicaoLinks.ftl" />
	<#assign i = 1/>
	<div id = "eleicao">
		<h3><b>Eleição: ${eleicao.descricao}</b></h3>
	</div>
		<@display.table name="candidatoEleicaos" id="candidatoEleicao" class="dados">
		<@display.column title="Ações" class="acao" style="width: 40px;">
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?candidatoEleicao.id=${candidatoEleicao.id}&eleicao.id=${eleicao.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			<a href="imprimirComprovanteInscricao.action?candidatoEleicao.candidato.id=${candidatoEleicao.candidato.id}&eleicao.id=${eleicao.id}"><img border="0" title="Imprimir o comprovante de inscrição" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
		</@display.column>
		<@display.column title="Candidato">
			${i} - ${candidatoEleicao.candidato.nome}
		</@display.column>
		<@display.column property="candidato.areaOrganizacional.descricao" title="Área Organizacional"/>
		<@display.column property="candidato.faixaSalarial.descricao" title="Cargo" style="width: 200px;"/>
	
		<#assign i = i + 1/>
	
	</@display.table>

	<div class="buttonGroup">
		<button class="btnInserirCandidato" onclick="limpaForm();openbox('Inserir Candidatos', 'nomeBusca');" ></button>
	<#if (candidatoEleicaos?exists && candidatoEleicaos?size>0)>
		<button class="btnGerarCedulas" onclick="window.location='imprimirCedulas.action?eleicao.id=${eleicao.id}'" ></button>
	</#if>
	
	<#if (eleicao.votacaoIni?exists && eleicao.votacaoFim?exists)>
		<button class="btnListaFrequenciaVotacao" onclick="window.location='imprimirListaFrequencia.action?eleicao.id=${eleicao.id}'" ></button>
	</#if>
	
	</div>


	<div id="box">
		<span id="boxtitle"></span>

		<@ww.form name="formPesquisa" id="formPesquisa" action="" onsubmit="pesquisar();return false;" method="POST">
			<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" list="areasCheckList" filtro="true"/>
			<@ww.textfield label="Matrícula" name="matriculaBusca" id="matriculaBusca" cssStyle="width:80px;" liClass="liLeft"/>
			<@ww.textfield label="Nome" name="nomeBusca" id="nomeBusca" cssStyle="width:410px;" />

			<button onclick="pesquisar();return false;" class="btnPesquisar"></button>
		</@ww.form>

		<@ww.form name="form" id="form" action="insert.action" onsubmit="${validarCampos}" validate="true" method="POST">
			<@frt.checkListBox form="form" label="Colaboradores" name="candidatosCheck" list="candidatosCheckList" filtro="true"/>

			<@ww.hidden name="eleicao.id"/>
			<@ww.token/>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="closebox();" class="btnCancelar"></button>
		</div>
	</div>
	
</body>
</html>