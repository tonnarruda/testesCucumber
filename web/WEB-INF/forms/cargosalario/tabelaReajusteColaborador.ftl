	<#include "../util/topFiltro.ftl" />
		<@ww.form name="form" action="" method="POST">
			<@ww.hidden name="tabelaReajusteColaborador.id" id="id"/>
			<@ww.hidden name="tabelaReajusteColaborador.data" id="id"/>

			<!-- Filtro -->
			<@ww.select id="optFiltro" label="Filtrar Por" name="filtro" required="true"  list=r"#{'1':'Área Organizacional', '2':'Grupo Ocupacional'}" onchange="filtrarOpt();" headerKey="1"/>

			<@ww.div id="areaOrganizacional">
				<@frt.checkListBox name="areaOrganizacionalsCheck" id="areaOrganizacionalsCheck" label="Áreas Organizacionais" list="areaOrganizacionalsCheckList" width="600" filtro="true" selectAtivoInativo="true"/>
			</@ww.div>

			<@ww.div id="grupoOcupacional">
				<@frt.checkListBox name="grupoOcupacionalsCheck" id="grupoOcupacionalsCheck" label="Grupos Ocupacionais" list="grupoOcupacionalsCheckList" width="600" filtro="true"/>
			</@ww.div>

			<button onclick="prepareUpdate();" class="btnPesquisar grayBGE" accesskey="F">
			</button>
			<br>
		</@ww.form>
		<script>
			document.getElementById('grupoOcupacional').style.display = "none";
		</script>

	<#include "../util/bottomFiltro.ftl" />


	<#assign areaOrganizacionalDesc=""/>

	<#if reajustes?exists && 0 < reajustes?size>
		<br>
		<p>
			<span style="background-color: #454C54;">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Ativos&nbsp;&nbsp;<span style="background-color: #F00;">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Desligados, não podem receber reajustes
		</P>
		<table class="dados">
			<#assign zebrar				=	true/>
			<#assign subTotalSalarioAtual    = 0/>
			<#assign subTotalSalarioProposto = 0/>
			<#assign totalSalarioAtual       = 0/>
			<#assign totalSalarioProposto    = 0/>
			<#assign existemDesligados    	 = false/>

			<#list reajustes as reajusteColaborador>
				<#if reajusteColaborador.tipoSalarioProposto == tipoAplicacaoIndice.getCargo() && reajusteColaborador.faixaSalarialProposta?exists && reajusteColaborador.faixaSalarialProposta.faixaSalarialHistoricoAtual?exists && reajusteColaborador.faixaSalarialProposta.faixaSalarialHistoricoAtual.tipo?exists && reajusteColaborador.faixaSalarialProposta.faixaSalarialHistoricoAtual.tipo == tipoAplicacaoIndice.getIndice()>
					<#assign salarioProposto = reajusteColaborador.faixaSalarialProposta.faixaSalarialHistoricoAtual.indice.indiceHistoricoAtual.valor * reajusteColaborador.faixaSalarialProposta.faixaSalarialHistoricoAtual.quantidade/>
				<#else>
					<#if reajusteColaborador.salarioProposto?exists>
						<#assign salarioProposto = reajusteColaborador.salarioProposto/>
					<#else>
						<#assign salarioProposto = 0/>
					</#if>
				</#if>

				<#if reajusteColaborador.tipoSalarioAtual == tipoAplicacaoIndice.getCargo() && reajusteColaborador.faixaSalarialAtual?exists && reajusteColaborador.faixaSalarialAtual.faixaSalarialHistoricoAtual?exists && reajusteColaborador.faixaSalarialAtual.faixaSalarialHistoricoAtual.tipo?exists && reajusteColaborador.faixaSalarialAtual.faixaSalarialHistoricoAtual.tipo == tipoAplicacaoIndice.getIndice()>
					<#assign salarioAtual = reajusteColaborador.faixaSalarialAtual.faixaSalarialHistoricoAtual.indice.indiceHistoricoAtual.valor * reajusteColaborador.faixaSalarialAtual.faixaSalarialHistoricoAtual.quantidade/>
				<#else>
					<#if reajusteColaborador.salarioAtual?exists>
						<#assign salarioAtual = reajusteColaborador.salarioAtual/>
					<#else>
						<#assign salarioAtual = 0/>
					</#if>
				</#if>

				<#if areaOrganizacionalDesc == "" || areaOrganizacionalDesc != reajusteColaborador.areaOrganizacionalProposta.descricao>
					<#if reajusteColaborador.areaOrganizacionalProposta?exists>
						<#assign areaOrganizacionalDesc = reajusteColaborador.areaOrganizacionalProposta.descricao/>
					<#else>
						<#assign areaOrganizacionalDesc = ""/>
					</#if>
					<#if (subTotalSalarioAtual > 0)>
						<tr bgcolor="#F3F3F3">
							<td></td>
							<td align="right"><b>SUB-TOTAL</b></td>
							<td colspan=3 align="right" width=360> <b>${subTotalSalarioAtual?string(",##0.00")}</b></td>
							<td colspan=3 align="right" width=360> <b>${subTotalSalarioProposto?string(",##0.00")}</b></td>
							<td align="right"> &nbsp; </td>
							<td align="right"> <b>${(subTotalSalarioProposto - subTotalSalarioAtual)?string(",##0.00")}</b></td>
							<td align="right"> <b>${((((subTotalSalarioProposto - subTotalSalarioAtual) / subTotalSalarioAtual)) * 100)?string(",##0.00")}%</b></td>
						</tr>
						</table>
						<br>
						<table class="dados">
					</#if>

					<#assign subTotalSalarioAtual    = salarioAtual/>
					<#assign subTotalSalarioProposto = salarioProposto/>

					<thead>
						<tr>
							<th colspan="11" class="sorted order1" align=left><b>&nbsp; ${areaOrganizacionalDesc}</b></th>
						</tr>
						<tr>
							<th class="sorted order1" style="background:#7BA6D3;" colspan=2>Colaborador</th>
							<th colspan=3 class="sorted order1" style="background:#7BA6D3;" width=360>Situação Atual</th>
							<th colspan=4 class="sorted order1" style="background:#7BA6D3;" width=360>Situação Proposta</th>
							<th colspan="2" align="center" class="sorted order1" style="background:#7BA6D3;">Diferença</th>
						</tr>
					</thead>
					<#assign zebrar=true/>
					<tbody>
		   			<tr>
						<th width=50 align=left>Ações</th>
						<th width=200 align=left>&nbsp;Nome</th>
						<th width=150 align=left>&nbsp;Cargo/Faixa</th>
						<th align=left>Tipo Salário</th>
						<th align=right>Salário&nbsp;</th>
						<th width=150 align=left>&nbsp;Cargo/Faixa</th>
						<th width=50 align=left>Tipo Salário</th>
						<th align=left>Salário</th>
						<th align=center>Obs.</th>
						<th align=right>(R$)</th>
						<th align=right>(%)</th>
					</tr>
				<#else>
					<#assign subTotalSalarioAtual    = subTotalSalarioAtual + salarioAtual/>
					<#assign subTotalSalarioProposto = subTotalSalarioProposto + salarioProposto/>
			    </#if>

				<#assign totalSalarioAtual    = totalSalarioAtual + salarioAtual/>
				<#assign totalSalarioProposto = totalSalarioProposto + salarioProposto/>

			 	<#if zebrar == true >
					<#assign class="even"/>
				<#else>
					<#assign class="odd"/>
				</#if>

				<#if reajusteColaborador.colaborador.dataDesligamento?exists && reajusteColaborador.colaborador.dataDesligamento <= tabelaReajusteColaborador.data>
					<#assign existemDesligados=true/>
					<#assign nomeColaborador="${reajusteColaborador.colaborador.nomeComercial} (Desligado)"/>
					<#assign color="#F00"/>
				<#else>
					<#assign nomeColaborador=reajusteColaborador.colaborador.nomeComercial/>
					<#assign color="#454C54"/>
				</#if>

				<tr class=${class}>
				  	<td>
				  		<@authz.authorize ifAllGranted="ROLE_DISSIDIO_COLABORADOR">
							<a href="javascript:executeLink('../reajusteColaborador/prepareUpdate.action?reajusteColaborador.id=${reajusteColaborador.id}&tabelaReajusteColaborador.id=${tabelaReajusteColaborador.id}');" ><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif" border="0"/>"></a>
							<a href="#" onclick="newConfirm('Confirma exclusão?', function(){executeLink('../reajusteColaborador/delete.action?reajusteColaborador.id=${reajusteColaborador.id}&tabelaReajusteColaborador.id=${tabelaReajusteColaborador.id}';});"><img border="0" title="Excluir" src="<@ww.url includeParams="none" value="/imgs/delete.gif" border="0"/>"></a>
						</@authz.authorize>
				  		<@authz.authorize ifNotGranted="ROLE_DISSIDIO_COLABORADOR">
							<a><img border="0" src="<@ww.url includeParams="none" value="/imgs/edit.gif" border="0"/>" style="opacity:0.2;filter:alpha(opacity=20);"></a>
							<a><img border="0" src="<@ww.url includeParams="none" value="/imgs/delete.gif" border="0"/>" style="opacity:0.2;filter:alpha(opacity=20);"></a>
						</@authz.authorize>
					</td>
				  	<td style="color: ${color}">${nomeColaborador}</td>
				  	<td style="color: ${color}"> ${reajusteColaborador.faixaSalarialAtual.descricao}</td>
				  	<td style="color: ${color}"> ${reajusteColaborador.descricaoTipoSalarioAtual}</td>
				  	<td style="color: ${color}" align="right"> ${salarioAtual?string(",##0.00")}</td>
				  	<td style="color: ${color}"> ${reajusteColaborador.faixaSalarialProposta.descricao}</td>
				  	<td style="color: ${color}"> ${reajusteColaborador.descricaoTipoSalarioProposto}</td>
				  	<td style="color: ${color}" align="right"> ${salarioProposto?string(",##0.00")}</td>
				  	<td style="color: ${color}" align="center">
						<#if reajusteColaborador.observacao?exists>
							<span href=# style="cursor: default;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'Obs.: ' + '${reajusteColaborador.observacao}');return false">...</span>
						</#if>
				  	</td>
				  	<td style="color: ${color}" align="right"> ${(salarioProposto - salarioAtual)?string(",##0.00")}</td>
					<td style="color: ${color}" align="right">
						<#if (salarioAtual > 0) >
							${((((salarioProposto - salarioAtual) / salarioAtual)) * 100)?string(",##0.00")}%
						<#else>
							0,00%
						</#if>
					</td>
				</tr>
			 	<#if zebrar == true >
			 	  	<#assign zebrar=false/>
				<#else>
					<#assign zebrar=true/>
				</#if>
			</#list>
			<tr bgcolor="#F3F3F3">
				<td></td>
				<td align="right"><b>SUB-TOTAL</b></td>
				<td colspan=3 align="right" width=360> <b>${subTotalSalarioAtual?string(",##0.00")}</b></td>
				<td colspan=3 align="right" width=360> <b>${subTotalSalarioProposto?string(",##0.00")}</b></td>
				<td align="right"> &nbsp; </td>
				<td align="right"> <b>${(subTotalSalarioProposto - subTotalSalarioAtual)?string(",##0.00")}</b></td>
				<#if (subTotalSalarioAtual > 0)>
					<td align="right"> <b>${((((subTotalSalarioProposto - subTotalSalarioAtual) / subTotalSalarioAtual)) * 100)?string(",##0.00")}%</b></td>
				<#else>
					<td align="right"> <b>0,00%</b></td>
				</#if>
			</tr>
			</tbody>
	 	</table>
		<table class="dados">
			<tr bgcolor="#F3F3F3">
				<td width=40></td>
				<td width=190 align="right"><b>TOTAL</b></td>
				<td colspan=3 align="right" width=360> <b>${totalSalarioAtual?string(",##0.00")}</b></td>
				<td colspan=3 align="right" width=360> <b>${totalSalarioProposto?string(",##0.00")}</b></td>
				<td align="right"> <b>${(totalSalarioProposto - totalSalarioAtual)?string(",##0.00")}</b></td>
				<#if (totalSalarioAtual > 0)>
					<td align="right"> <b>${((((totalSalarioProposto - totalSalarioAtual) / totalSalarioAtual)) * 100)?string(",##0.00")}%</b></td>
				<#else>
					<td align="right"> <b>0,00%</b></td>
				</#if>
			</tr>
	 	</table>
		<table class="dados">
			<tr bgcolor="#F3F3F3">
				<td width=40></td>
				<td width=190 align="right"><b>DIFERENÇA NA FOLHA</b></td>
				<td colspan=3 align="right" width=360> <b>${valorTotalFolha?string(",##0.00")}</b></td>
				<td colspan=3 align="right" width=360> <b>${(valorTotalFolha + (totalSalarioProposto - totalSalarioAtual))?string(",##0.00")}</b></td>
				<td align="right"> <b>${(totalSalarioProposto - totalSalarioAtual)?string(",##0.00")}</b></td>
				<#if (valorTotalFolha > 0)>
					<td align="right"> <b>${((((totalSalarioProposto - totalSalarioAtual) / valorTotalFolha)) * 100)?string(",##0.00")}%</b></td>
				<#else>
					<td align="right"> <b>0,00%</b></td>
				</#if>
			</tr>
	 	</table>
	</#if>
	
	<div class="buttonGroup">
		<button onclick="window.location='list.action'" class="btnVoltar" accesskey="V"></button>
		
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

		<@authz.authorize ifAllGranted="ROLE_MOV_APLICARREALINHAMENTO">
			<#if tabelaReajusteColaborador.id?exists && reajustes?exists && 0 < reajustes?size>
				<button onclick="aplicar(${existemDesligados?string})" class="btnAplicar" accesskey="P"></button>
			</#if>
		</@authz.authorize>
	</div>