<#if reajustesIndice?exists && (reajustesIndice?size > 0)>
	<table class="dados">
		<thead>
			<tr>
				<th>Ações</th>
				<th>Índice</th>
				<th>Situação Atual</th>
				<th>Situação Proposta</th>
				<th colspan="2">Diferença</th>
			</tr>
		</thead>
		
		<tbody>
			<#assign i = 0/>
			<#assign totalAtual = 0.0/>
			<#assign totalProposto = 0.0/>
			
			<#list reajustesIndice as reajusteIndice>
				<tr class="<#if i%2==0>odd<#else>even</#if>">
					<td align="center" width="60">
						<@authz.authorize ifAllGranted="ROLE_DISSIDIO_INDICE">
							<a href="javascript: executeLink('../reajusteIndice/prepareUpdate.action?reajusteIndice.id=${reajusteIndice.id}&tabelaReajusteColaborador.id=${tabelaReajusteColaborador.id}');" ><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif" border="0"/>"></a>
							<a href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){executeLink('../reajusteIndice/delete.action?reajusteIndice.id=${reajusteIndice.id}&tabelaReajusteColaborador.id=${tabelaReajusteColaborador.id}');});"><img border="0" title="Excluir" src="<@ww.url includeParams="none" value="/imgs/delete.gif" border="0"/>"></a>
						</@authz.authorize>
				  		<@authz.authorize ifNotGranted="ROLE_DISSIDIO_INDICE">
							<a><img border="0" src="<@ww.url includeParams="none" value="/imgs/edit.gif" border="0"/>" style="opacity:0.2;filter:alpha(opacity=20);"></a>
							<a><img border="0" src="<@ww.url includeParams="none" value="/imgs/delete.gif" border="0"/>" style="opacity:0.2;filter:alpha(opacity=20);"></a>
						</@authz.authorize>
					</td>
					<td>${reajusteIndice.indice.nome}</td>
					<td align="right" width="120">${reajusteIndice.valorAtual?string(",##0.00")}</td>
					<td align="right" width="120">${reajusteIndice.valorProposto?string(",##0.00")}</td>
					<td align="right" width="60">${(reajusteIndice.valorProposto - reajusteIndice.valorAtual)?string(",##0.00")}</td>
					<td align="right" width="60">
						<#if (reajusteIndice.valorAtual > 0) >
							${((((reajusteIndice.valorProposto - reajusteIndice.valorAtual) / reajusteIndice.valorAtual)) * 100)?string(",##0.00")}%
						<#else>
							0,00%
						</#if>
					</td>
				</tr>
				
				<#assign i = i + 1/>
				<#assign totalAtual = totalAtual + reajusteIndice.valorAtual/>
				<#assign totalProposto = totalProposto + reajusteIndice.valorProposto/>
			</#list>
		</tbody>
	
		<tfoot>
			<tr>
				<td colspan="2" align="right">Total</td>
				<td align="right">${totalAtual?string(",##0.00")}</td>
				<td align="right">${totalProposto?string(",##0.00")}</td>
				<td align="right">${(totalProposto - totalAtual)?string(",##0.00")}</td>
				<td align="right">${((((totalProposto - totalAtual) / totalAtual)) * 100)?string(",##0.00")}%</td>
			</tr>
		</tfoot>
	</table>
</#if>

<div class="buttonGroup">
	<button onclick="window.location='list.action'" class="btnVoltar"></button>
	
	<button onclick="executeLink('../reajusteIndice/prepareInsert.action');" class="btnInserir" style="margin-right:80px;"></button>
	
	<@authz.authorize ifAllGranted="ROLE_MOV_APLICARREALINHAMENTO">
		<#if tabelaReajusteColaborador.id?exists && reajustesIndice?exists && 0 < reajustesIndice?size>
			<button onclick="aplicarPorIndice()" class="btnAplicar"></button>
		</#if>
	</@authz.authorize>
</div>