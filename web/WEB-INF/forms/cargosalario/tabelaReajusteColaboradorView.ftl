<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>
	<title>Planejamento de Realinhamentos</title>
	<script language='javascript'>
		function prepareUpdate()
		{
			document.forms[0].action = "<@ww.url includeParams="none" value="/cargosalario/tabelaReajusteColaborador/visualizar.action"/>";
			document.forms[0].submit();
		}

		function filtrarOpt()
		{
			value = document.getElementById('optFiltro').value;
			if(value == "1")
			{
				document.getElementById('areaOrganizacional').style.display = "";
				document.getElementById('grupoOcupacional').style.display = "none";
			}
			else if(value == "2")
			{
				document.getElementById('areaOrganizacional').style.display = "none";
				document.getElementById('grupoOcupacional').style.display = "";

			}
		}
		
		function aplicar(existemDesligados)
		{
			if (existemDesligados)
			{
				jAlert('Colaboradores desligados não podem receber reajuste\nFavor ajustar antes de aplicar');
				return false;
			}
			
			newConfirm('Deseja realmente aplicar o reajuste?', function(){window.location='aplicar.action?tabelaReajusteColaborador.id=${tabelaReajusteColaborador.id}&tabelaReajusteColaborador.data=${tabelaReajusteColaborador.data}&tabelaReajusteColaborador.dissidio=${tabelaReajusteColaborador.dissidio?string}'});
		}
	</script>

	<#include "../ftl/showFilterImports.ftl" />


 	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<#if tabelaReajusteColaborador.id?exists>
		<p>Promoção/Reajuste<br> <b>${tabelaReajusteColaborador.nome}</b></p>
		<p>A Partir de<br> <b>${tabelaReajusteColaborador.data}</b></p>
		<p>A Tipo de Reajuste:<br> <b>${tipoReajusteString}</b></p>
		<br />

		<#include "../util/topFiltro.ftl" />
			<@ww.form name="form" action="" method="POST">
				<@ww.hidden name="tabelaReajusteColaborador.id" id="id"/>
				<@ww.hidden name="tabelaReajusteColaborador.data" id="id"/>

				<!-- Filtro -->
				<@ww.select id="optFiltro" label="Filtrar Por" name="filtro" required="true"  list=r"#{'1':'Área Organizacional', '2':'Grupo Ocupacional'}" onchange="filtrarOpt();" headerKey="1"/>

				<@ww.div id="areaOrganizacional">
					<@frt.checkListBox name="areaOrganizacionalsCheck" id="areaOrganizacionalsCheck" label="Áreas Organizacionais" list="areaOrganizacionalsCheckList" width="600" />
				</@ww.div>

				<@ww.div id="grupoOcupacional">
					<@frt.checkListBox name="grupoOcupacionalsCheck" id="grupoOcupacionalsCheck" label="Grupos Ocupacionais" list="grupoOcupacionalsCheckList" width="600" />
				</@ww.div>

				<button onclick="prepareUpdate();" class="btnPesquisar grayBGE" accesskey="F">
				</button>
				<br>
			</@ww.form>
			<script>
				document.getElementById('grupoOcupacional').style.display = "none";
			</script>

		<#include "../util/bottomFiltro.ftl" />
	</#if>

	<@ww.div id="divColab">
		<#include "../cargoSalario/tabelaReajusteColaborador.ftl" />
	</@ww.div>

	<div class="buttonGroup">
		<button onclick="window.location='list.action'" class="btnVoltar" accesskey="V">
		</button>
		<#if tabelaReajusteColaborador.id?exists && reajustes?exists && 0 < reajustes?size>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<button onclick="aplicar(${existemDesligados?string})" class="btnAplicar" accesskey="P">
			</button>
		</#if>
	</div>
</body>
</html>