<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	<title>Análise de Tabela Salarial</title>
</head>
<body>

	<@display.table name="cargos" id="cargo" class="dados">
		<@display.column property="nome" title="Cargo" style="width:400px;"/>	
		
		<@display.column title="Faixas">
			<table class="dados">
				<thead>
					<tr>
						<th>Faixa</th>
						<th>Tipo</th>
						<th>Valor</th>
						<th>Obs.</th>
					</tr>
				</thead>
				<tbody>
					<#assign classZebra="even"/>
					<#list faixaSalarialHistoricos as faixaSalarialHistorico>
						<#if cargo.id == faixaSalarialHistorico.faixaSalarial.cargo.id>
							<#if classZebra == "even">
								<#assign classZebra="odd"/>
							<#else>
								<#assign classZebra="even"/>
							</#if>
							
							<tr class="${classZebra}">
								<td style="width:150px;">
									${faixaSalarialHistorico.faixaSalarial.nome}
								</td>
								<td style="text-align:center; width:50px;">
									${faixaSalarialHistorico.tipoSalarioDescricao}
								</td>
								<td style="text-align:right; width:100px;">
									<#if faixaSalarialHistorico.valor?exists>
										${faixaSalarialHistorico.valor?string(",##0.00")}
									</#if>
								</td>
								<td style="text-align:center; width:10px;">
									<#if faixaSalarialHistorico.tipo == tipoAplicacaoIndice.getIndice()>
										<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${faixaSalarialHistorico.descricaoIndice}');return false">...</span>
									</#if>
									<#if faixaSalarialHistorico.tipo == 0>
										<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'Não existe histórico para a faixa na data informada.');return false">...</span>
									</#if>
								</td>
							</tr>
						</#if>
				      </#list>
	      		</tbody>
	      	</table>
		</@display.column>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnVoltar" onclick="window.location='analiseTabelaSalarialFiltro.action'" >
		</button>
	</div>
</body>
</html>