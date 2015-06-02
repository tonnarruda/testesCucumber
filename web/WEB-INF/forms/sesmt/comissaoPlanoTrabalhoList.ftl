<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/formModal.css"/>');
	</style>
	<style type="text/css">#menuComissao a.ativaPlano{border-bottom: 2px solid #5292C0;}</style>
	<title></title>

	<script src='<@ww.url includeParams="none" value="/js/formModal.js"/>'></script>
	<script src='<@ww.url includeParams="none" value="/js/functions.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ComissaoPlanoTrabalhoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<title></title>

	<script type="text/javascript">
		function limpaForm()
		{
			resetFormulario(new Array('prazo','descricao','detalhes','responsavelId','comissaoPlanoTrabalhoId'));
			document.getElementById('prioridade').value  = "M";
			document.getElementById('parecer').value  = "E";
			document.getElementById('situacao').value  = "NAO_INICIOU";
			document.form.action="insert.action"
		}

		function preparaDadosUpdate(comissaoId)
		{
			DWREngine.setErrorHandler(errorPreparaDados);
			ComissaoPlanoTrabalhoDWR.prepareDados(carregaDados,comissaoId)
		}

		function errorPreparaDados(msg)
		{
			jAlert(msg);
		}

		function carregaDados(data)
		{
			//campos do form e hidden
			document.getElementById("prazo").value = data.prazo;
			document.getElementById("descricao").value = data.descricao;
			document.getElementById("prioridade").value = data.prioridade;
			document.getElementById("parecer").value = data.parecer;
			document.getElementById("detalhes").value = data.detalhes;
			document.getElementById("situacao").value = data.situacao;
			document.getElementById("responsavelId").value = data.responsavelId;
			document.getElementById("comissaoPlanoTrabalhoId").value = data.comissaoPlanoTrabalhoId;
			//action
			document.form.action="update.action"
			openbox('Editar Plano de Trabalho', 'descricao');
		}
	</script>

	<#assign validarCampos="return validaFormulario('form',new Array('prazo'),new Array('prazo'));"/>
</head>
<body>
	<#include "comissaoLinks.ftl" />
	<#include "../ftl/mascarasImports.ftl" />

	<@display.table name="comissaoPlanoTrabalhos" id="comissaoPlanoTrabalho" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="#" onclick="preparaDadosUpdate(${comissaoPlanoTrabalho.id});"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?comissaoPlanoTrabalho.id=${comissaoPlanoTrabalho.id}&comissao.id=${comissao.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="prazo" title="Prazo" format="{0,date,dd/MM/yyyy}" style="width:40px;"/>
		<@display.column property="descricao" title="Descrição" style="width:480px;"/>
		<@display.column property="situacaoDic" title="Situação" style="width:90px;"/>
		<@display.column property="responsavel.nome" title="Responsável" />
	</@display.table>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="limpaForm(); openbox('Inserir Plano de Trabalho', 'descricao');" accesskey="N">
		</button>
		<button class="btnImprimirPdf" onclick="window.location='imprimirPlanoTrabalho.action?comissao.id=${comissao.id}'" accesskey="N">
		</button>
	</div>

	<#--
		 Modal de edição
	-->
	<div id="filter"></div>
	<div id="box">
		<span id="boxtitle"></span>
		<@ww.form name="form" action="insert.action" method="POST">
			<@ww.textfield label="Descrição" id="descricao" name="comissaoPlanoTrabalho.descricao" value="" maxlength="100" cssStyle="width:400px;" />
			<@ww.textarea label="Detalhes" id="detalhes" name="comissaoPlanoTrabalho.detalhes" cssStyle="width: 400px;height:100px;"/>
			<@ww.datepicker label="Prazo" id="prazo" name="comissaoPlanoTrabalho.prazo" cssClass="mascaraData" required="true"/>
			<@ww.select label="Situação" id="situacao" name="comissaoPlanoTrabalho.situacao" list=r"#{'NAO_INICIOU':'Não Iniciado','ANDAMENTO':'Em andamento','CONCLUIDO':'Concluído'}" required="true"/>
			<@ww.select label="Prioridade" id="prioridade" name="comissaoPlanoTrabalho.prioridade" list=r"#{'A':'Alta','M':'Média','B':'Baixa'}" required="true"/>
			<@ww.select label="Parecer da empresa" id="parecer" name="comissaoPlanoTrabalho.parecer" list=r"#{'E':'Em estudo','I':'Inviável','V':'Viável'}" required="true"/>
			<@ww.select label="Responsável" name="comissaoPlanoTrabalho.responsavel.id" id="responsavelId" listKey="id" listValue="nome" list="colaboradors" headerKey="" headerValue="" cssStyle="width: 280px;"/>

			<@ww.hidden name="comissaoPlanoTrabalho.id" id="comissaoPlanoTrabalhoId" value=""/>
			<@ww.hidden name="comissao.id" id="comissaoId" />
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="${validarCampos}" class="btnGravar"></button>
			<button onclick="closebox();" class="btnCancelar"></button>
		</div>
	</div>
</body>
</html>