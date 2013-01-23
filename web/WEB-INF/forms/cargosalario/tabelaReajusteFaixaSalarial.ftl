<#if reajustesFaixaSalarial?exists && (reajustesFaixaSalarial?size > 0)>
	<table class="dados">
		<thead>
			<tr>
				<th>Ações</th>
				<th>Cargo/Faixa</th>
				<th>Situação Atual</th>
				<th>Situação Proposta</th>
				<th colspan="2">Diferença</th>
			</tr>
		</thead>
		
		<tbody>
			<#assign i = 0/>
			<#assign totalAtual = 0.0/>
			<#assign totalProposto = 0.0/>
			
			<#list reajustesFaixaSalarial as reajusteFaixa>
				<tr class="<#if i%2==0>odd<#else>even</#if>">
					<td align="center" width="60">
						<a href="../reajusteFaixaSalarial/prepareUpdate.action?reajusteFaixaSalarial.id=${reajusteFaixa.id}&tabelaReajusteColaborador.id=${tabelaReajusteColaborador.id}" ><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif" border="0"/>"></a>
						<a href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){window.location='../reajusteFaixaSalarial/delete.action?reajusteFaixaSalarial.id=${reajusteFaixa.id}&tabelaReajusteColaborador.id=${tabelaReajusteColaborador.id}'});"><img border="0" title="Excluir" src="<@ww.url includeParams="none" value="/imgs/delete.gif" border="0"/>"></a>
					</td>
					<td>${reajusteFaixa.faixaSalarial.descricao}</td>
					<td align="right" width="120">${reajusteFaixa.valorAtual?string(",##0.00")}</td>
					<td align="right" width="120">${reajusteFaixa.valorProposto?string(",##0.00")}</td>
					<td align="right" width="60">${(reajusteFaixa.valorProposto - reajusteFaixa.valorAtual)?string(",##0.00")}</td>
					<td align="right" width="60">
						<#if (reajusteFaixa.valorAtual > 0) >
							${((((reajusteFaixa.valorProposto - reajusteFaixa.valorAtual) / reajusteFaixa.valorAtual)) * 100)?string(",##0.00")}%
						<#else>
							0,00%
						</#if>
					</td>
				</tr>
				
				<#assign i = i + 1/>
				<#assign totalAtual = totalAtual + reajusteFaixa.valorAtual/>
				<#assign totalProposto = totalProposto + reajusteFaixa.valorProposto/>
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

	<button onclick="window.location='../reajusteFaixaSalarial/prepareInsert.action'" class="btnInserir" style="margin-right:80px;"></button>
	
	<#if tabelaReajusteColaborador.id?exists && reajustesFaixaSalarial?exists && 0 < reajustesFaixaSalarial?size>
		<button onclick="aplicarPorFaixaSalarial()" class="btnAplicar"></button>
	</#if>
</div>