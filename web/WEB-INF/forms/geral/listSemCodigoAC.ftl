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
	<div class="buttonGroup">
	<button class="btnInserir" onclick="window.location='prepareInsert.action'" >
	</button>
</div>
</body>
</html>