<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Competências do Colaborador</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<b>Colaborador:</b> ${colaborador.nome}
	<div style="clear: both;"></div><br />
	
	<@display.table name="configuracaoNivelCompetenciaColaboradores" id="competencia" class="dados">
		<@display.column title="Ações" media="html" style="text-align:center; width:80px;" >
			<a href="prepareUpdateCompetenciasColaborador.action?configuracaoNivelCompetenciaColaborador.id=${competencia.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='deleteCompetenciasColaborador.action?colaborador.id=${colaborador.id}&configuracaoNivelCompetenciaColaborador.id=${competencia.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width: 75px;text-align: center;"/>
		<@display.column property="faixaSalarial.descricao" title="Cargo / Faixa Salarial"/>
		<@display.column property="colaboradorQuestionario.avaliacaoDesempenho.titulo" title="Avaliação de Desempenho"/>
		<@display.column property="avaliador.nome" title="Avaliador"/>
	</@display.table>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsertCompetenciasColaborador.action?colaborador.id=${colaborador.id}'"></button>
		<button onclick="window.location='../../geral/colaborador/list.action';" class="btnVoltar"></button>
	</div>
</body>
</html>