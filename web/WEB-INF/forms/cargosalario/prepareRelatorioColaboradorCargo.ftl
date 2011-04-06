<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Colaboradores por Cargo</title>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EstabelecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript'>
		var empresaIds = new Array();
		<#if empresaIds?exists>
			<#list empresaIds as empresaId>
				empresaIds.push(${empresaId});
			</#list>
		</#if>
		
		function populaEstabelecimento(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			EstabelecimentoDWR.getByEmpresas(createListEstabelecimento, empresaId, empresaIds);
		}

		function createListEstabelecimento(data)
		{
			addChecks('estabelecimentosCheck',data);
		}
		
		function populaCargo(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			CargoDWR.getByEmpresas(createListCargo, empresaId, empresaIds);
		}

		function createListCargo(data)
		{
			addChecks('cargosCheck',data);
		}
		
		function populaArea(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			AreaOrganizacionalDWR.getByEmpresas(createListArea, empresaId, empresaIds);
		}

		function createListArea(data)
		{
			addChecks('areaOrganizacionalsCheck',data);
		}
		
		function compl()
		{
			var compl = document.getElementById("complementares");
			var img = document.getElementById("imgCompl");
			if(compl.style.display == "none")
			{
				compl.style.display = "";
				img.src = "<@ww.url includeParams="none" value="/imgs/arrow_up.gif"/>"
			}
			else
			{
				compl.style.display = "none";
				img.src = "<@ww.url includeParams="none" value="/imgs/arrow_down.gif"/>"
			}
		}
		
		function desabilitaOutroCheck(esseCheck , outroCheck)
		{
			$("#" + outroCheck).attr('disabled', $("#" + esseCheck).attr("checked"));
		}
		
		$(document).ready(function($)
		{
			var empresa = $('#empresa').val();
			
			populaArea(empresa);
			populaCargo(empresa);
			populaEstabelecimento(empresa);
		});
	</script>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<#include "../ftl/mascarasImports.ftl" />
	
	<#assign validarCampos="return validaFormulario('form', new Array('data'), new Array('data'))"/>
	<#if data?exists>
		<#assign dataTemp = data?date/>
	<#else>
		<#assign dataTemp = ""/>
	</#if>

	<@ww.form name="form" action="relatorioColaboradorCargo.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.select label="Empresa" name="empresa.id" id="empresa" list="empresas" listKey="id" listValue="nome" headerValue="Todas" headerKey="" onchange="populaEstabelecimento(this.value);populaCargo(this.value);"/>
		
		<@ww.datepicker label="Data de Referência" id="data" name="data" required="true" cssClass="mascaraData" value="${dataTemp}"/><br>
		
		<li>
			<a href="javascript:compl();" style="text-decoration: none"><img border="0" title="" id="imgCompl" src="<@ww.url includeParams="none" value="/imgs/arrow_down.gif"/>"> Filtro complementar</a>
		</li>
		<li>
			<@ww.div id="complementares" cssStyle="display: ;" cssClass="divInfo">
				<ul>
					<@ww.checkbox label="" name="exibColabAdmitido" theme="simple"/> 
					Exibir apenas colaboradores admitidos a mais de
					<@ww.textfield theme="simple" name="qtdMeses" id="qtdMeses" cssStyle="width:30px; text-align:right;" maxLength="4" onkeypress = "return(somenteNumeros(event,''));"/> 
					meses considerando a data 
					<@ww.select theme="simple" name="opcaoFiltro" list=r"#{'0':'Atual','1':'de Referência'}"/>
					<br><br>
					
					<@ww.checkbox label="" name="exibColabDesatualizado" labelPosition="left" theme="simple"/>
					Exibir apenas colaboradores com dados desatualizados a mais de
					<@ww.textfield theme="simple" name="qtdMesesDesatualizacao" id="qtdMesesAtualizacao" cssStyle="width:30px; text-align:right;" maxLength="4" onkeypress = "return(somenteNumeros(event,''));"/> 
					meses.  
					
				</ul>
			</@ww.div>
		</li>
		<br>
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimento" list="estabelecimentosCheckList" />
		<@frt.checkListBox name="areaOrganizacionalsCheck" id="areaOrganizacionalsCheck" label="Áreas Organizacionais" list="areaOrganizacionalsCheckList" width="500" />
		<@frt.checkListBox name="cargosCheck" id="cargosCheck" label="Cargos" list="cargosCheckList" />
		
		<@ww.checkbox label="Exibir relatório resumido" name="relatorioResumido" id="resumido" labelPosition="left" onchange="desabilitaOutroCheck('resumido', 'exibirSalario')"/>
		<@ww.checkbox label="Exibir Salario" name="exibirSalario" id="exibirSalario" labelPosition="left" onchange="desabilitaOutroCheck('exibirSalario', 'resumido')"/>
	</@ww.form>

	<div class="buttonGroup">
		<button class="btnRelatorio" onclick="${validarCampos};"></button>
	</div>
</body>
</html>
