<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css"/>');
</style>
<title>Solicitações de Desligamento</title>
</head>
<body>
	<@display.table name="colaboradores" id="colaborador" class="dados" >
		<@display.column title="Ações" class="acao">
			<@frt.link verifyRole="ROLE_COLAB_LIST_DESLIGAR" href="visualizarSolicitacaoDesligamento.action?colaborador.id=${colaborador.id}" imgTitle="Visualizar solicitação de desligamento" imgName="desliga_colab.gif"/>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
		<@display.column property="estabelecimento.nome" title="Estabelecimento"/>
		<@display.column property="areaOrganizacional.nome" title="Área Organizacional"/>
		<@display.column property="cargoFaixa" title="Cargo/Faixa"/>
	</@display.table>
</body>
</html>