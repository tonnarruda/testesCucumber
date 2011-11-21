<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>
	<title>Excluir Registros (código AC)</title>
</head>
<body>

	Cidades
	<@display.table name="cidades" id="cidade" class="dados" >
		<@display.column title="<input type='checkbox' id='md' onclick='atualizaChecks(\"checkCidade\", this.checked);' />" style="width: 30px; text-align: center;">
			<input type="checkbox" class="checkCidade" value="${cidade.id}" name="cidadeIds" />
		</@display.column>
		
		<@display.column property="id" title="ID" style="width: 30px;text-align: right;"/>
		<@display.column property="nome" title="Cidade"/>
	</@display.table>
	
	Estabelecimentos
	<@display.table name="estabelecimentos" id="estabelecimento" class="dados" >
		<@display.column title="<input type='checkbox' id='md' onclick='atualizaChecks(\"checkEstabelecimento\", this.checked);' />" style="width: 30px; text-align: center;">
			<input type="checkbox" class="checkEstabelecimento" value="${estabelecimento.id}" name="estabelecimentoIds" />
		</@display.column>
		
		<@display.column property="id" title="ID" style="width: 30px;text-align: right;"/>
		<@display.column property="empresa.nome" title="Empresa"/>
		<@display.column property="nome" title="Estabelecimento"/>
	</@display.table>
	
	Áreas Organizacionais
	<@display.table name="areaOrganizacionals" id="areaOrganizacional" class="dados" >
		<@display.column title="<input type='checkbox' id='md' onclick='atualizaChecks(\"checkArea\", this.checked);' />" style="width: 30px; text-align: center;">
			<input type="checkbox" class="checkArea" value="${areaOrganizacional.id}" name="areasIds" />
		</@display.column>
		
		<@display.column property="id" title="ID" style="width: 30px;text-align: right;"/>
		<@display.column property="empresa.nome" title="Empresa"/>
		<@display.column property="areaMae.nome" title="Área Mãe"/>
		<@display.column property="nome" title="Área"/>
	</@display.table>

	Colaboradores
	<@display.table name="colaboradors" id="colaborador" class="dados" >
		<@display.column title="<input type='checkbox' id='md' onclick='atualizaChecks(\"checkColaborador\", this.checked);' />" style="width: 30px; text-align: center;">
			<input type="checkbox" class="checkColaborador" value="${colaborador.id}" name="colaboradorIds" />
		</@display.column>
		
		<@display.column property="id" title="ID" style="width: 30px;text-align: right;"/>
		<@display.column property="empresa.nome" title="Empresa"/>
		<@display.column property="nome" title="Colaborador"/>
	</@display.table>
	
	Faixas Salariais
	<@display.table name="faixaSalarials" id="faixaSalarial" class="dados" >
		<@display.column title="<input type='checkbox' id='md' onclick='atualizaChecks(\"checkFaixaSalarial\", this.checked);' />" style="width: 30px; text-align: center;">
			<input type="checkbox" class="checkFaixaSalarial" value="${faixaSalarial.id}" name="faixaSalarialIds" />
		</@display.column>
		
		<@display.column property="id" title="ID" style="width: 30px;text-align: right;"/>
		<@display.column property="empresaNome" title="Empresa"/>
		<@display.column property="nome" title="Faixas Salariais"/>
	</@display.table>

	
	<div class="buttonGroup">
	<button class="btnInserir" onclick="window.location='prepareInsert.action'" >
	</button>
</div>
</body>
</html>