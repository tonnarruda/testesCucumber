<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
		.apto
		{
			color: #F00 !important;
		}
		.contratado
		{
			color: #008000 !important;
		}
		.btnTriagem, .btnInserirEtapasEmGrupo, .btnResultadoAvaliacao, .btnVoltar{
			margin: 5px 5px 0px 0px;
		}
	</style>

	<script>
		function marcarDesmarcar(frm)
		{
			var vMarcar;
	
			if (document.getElementById('md').checked)
			{
				vMarcar = true;
			}
			else
			{
				vMarcar = false;
			}
	
			with(frm)
			{
				for(i = 0; i < elements.length; i++)
				{
					if(elements[i].name == 'candidatosId' && elements[i].type == 'checkbox' )
					{
						elements[i].checked = vMarcar;
					}
				}
			}
		}
	
		function verificaMarcados(frm)
		{
			var resultadoCand = false;
			var resultadoSol  = false;
	
			with(frm)
			{
				for(i = 0; i < elements.length; i++)
				{
					if(elements[i].name == 'candidatosId' && elements[i].type == 'checkbox' )
					{
						if (elements[i].checked && resultadoCand == false)
						{
							resultadoCand = true;
						}
					}
					else if(elements[i].name == 'solicitacao.id' && elements[i].type == 'radio' )
					{
						if (elements[i].checked && resultadoSol == false)
						{
							resultadoSol = true;
						}
					}
				}
			}
	
			if (resultadoCand && resultadoSol)
				return true;
			else
			{
				jAlert("Informe pelo menos um candidato e uma solicitação!");
				return false;
			}
		}
	</script>

	<title>Mover Candidatos da Seleção</title>
</head>
<body>
	<table width="100%">
		<tr>
			<td>Área: <#if solicitacao.areaOrganizacional?exists && solicitacao.areaOrganizacional.nome?exists>${solicitacao.areaOrganizacional.nome}</#if></td>
			<td>Cargo: <#if solicitacao.faixaSalarial?exists && solicitacao.faixaSalarial.cargo?exists && solicitacao.faixaSalarial.cargo.nome?exists>${solicitacao.faixaSalarial.cargo.nome}</#if></td>
			<td>Solicitante: <#if solicitacao.solicitante?exists && solicitacao.solicitante.nome?exists>${solicitacao.solicitante.nome}</#if></td>
			<td>Vagas: <#if solicitacao.quantidade?exists>${solicitacao.quantidade}</#if></td>
		</tr>
	</table>

	<br/>
	<@ww.form name="formCand" action="mover.action" method="POST" >
		<div id="legendas" align="right"></div>
		<@display.table name="candidatoSolicitacaos" id="candidatoSolicitacao" class="dados" defaultsort=2>
		
			<#if candidatoSolicitacao.aptoBoolean>
				<#assign classe=""/>
			<#else>
				<#assign classe="apto"/>
			</#if>
				
			<#if candidatoSolicitacao.status == 'P' || candidatoSolicitacao.status == 'C'>
				<#assign classe="contratado"/>
			</#if>
		
			<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(document.formCand);' />" style="width: 30px; text-align: center;">
				<input type="checkbox" value="${candidatoSolicitacao.id}" name="candidatosId" />
			</@display.column>
			<@display.column property="candidato.nome" title="Nome" class="${classe}"/>
			<@display.column property="etapaSeletiva.nome" title="Etapa" class="${classe}"/>
			<@display.column property="responsavel" title="Responsável" class="${classe}"/>
			<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}"  style="text-align: center;" class="${classe}"/>
			<@display.column title="Obs."  style="text-align: center;" class="${classe}">
				<#if candidatoSolicitacao.observacao?exists && candidatoSolicitacao.observacao?trim != "">
					<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${candidatoSolicitacao.observacao?j_string}');return false">...</span>
				</#if>
			</@display.column>
		</@display.table>

		<h3>:: Solicitações em aberto</h3>

		<@display.table name="solicitacaos" id="sol" class="dados" >
			<@display.column title="" style="width: 30px; text-align: center;">
				<input type="radio" value="${sol.id}" name="solicitacao.id"/>
			</@display.column>
			<@display.column property="faixaSalarial.cargo.nome" title="Cargo" />
			<@display.column property="empresa.nome" title="Empresa" />
			<@display.column property="areaOrganizacional.nome" title="Área" />
			<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" />
			<@display.column property="solicitante.nome" title="Solicitante" />
			<@display.column property="quantidade" title="Vagas" style="text-align: right;"/>
		</@display.table>
	</@ww.form>
	<div class="buttonGroup">
		<button class="btnGravar" onclick="if(verificaMarcados(formCand)) { document.formCand.submit(); }" accesskey="G">
		</button>

		<button onclick="window.location='list.action?solicitacao.id=${solicitacao.id}'" class="btnVoltar" accesskey="V">
		</button>
	</div>
	
	<script>
		var obj = document.getElementById("legendas");
		obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #555;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Aptos/Indiferente";
		obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #F00;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Não Aptos";
		obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #008000;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Contratados/Promovidos";
	</script>
</body>
</html>