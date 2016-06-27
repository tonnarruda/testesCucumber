<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Auditoria do Sistema</title>

		<#include "../ftl/mascarasImports.ftl" />
		<style type="text/css">
			@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		</style>
	<#assign validarCampos="return validaFormulario('form', new Array('dataIni','dataFim'), new Array('dataIni','dataFim'), true)"/>
	<#if dataIni?exists>
		<#assign dataValueIni = dataIni?date/>
	<#else>
		<#assign dataValueIni = ""/>
	</#if>
	<#if dataFim?exists>
		<#assign dataValueFim = dataFim?date/>
	<#else>
		<#assign dataValueFim = ""/>
	</#if>

	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

</head>
<body>
	<@ww.actionerror />

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="form" action="listFiltro.action" method="POST" onsubmit="${validarCampos}"  id="formBusca">

			<table>
				<tr>
					<td>Período:*</td>
					<td>Usuário:</td>
					<td>Movimentação:</td>
					<td>Operação: <span id="loading" style="display:none;">Carregando..</span></td>
				</tr>
				<tr>
					<td width="300">
						<@ww.datepicker label="Período" name="dataIni" id="dataIni" 
							theme="simple" value="${dataValueIni}" cssClass="mascaraData" 
							language="pt" format="%d/%m/%Y" />
						 a
						<@ww.datepicker language="pt" label="" name="dataFim" id="dataFim" 
							theme="simple" value="${dataValueFim}" cssClass="mascaraData"
							language="pt" format="%d/%m/%Y"/>
					</td>
					<td>
						<@ww.select name="usuario.id" list="usuarios"  cssStyle="width: 45px;" listKey="id" listValue="nome" headerKey="-1" headerValue="Todos" theme="simple" cssStyle="width: 120px;"/>
					</td>
					<td>
						<@ww.select id="modulo" name="entidade" list="entidades" cssStyle="width: 250px;" theme="simple" />
						<script type="text/javascript">
							$(function() {
								// Ajax Loading Message
								$("#loading").ajaxStart(function(){
									$(this).show();
								}).ajaxStop(function(){
									$(this).hide();
								});
								// Evento para carregar combo de operações
								$("#modulo").change(function() {
									var moduloSelecionado = $(this).val();
									var url = '<@ww.url includeParams="none" value="/security/auditoria/listaOperacoes.action"/>';
									$("#blocoDeOperacoes").load(url + ' .operacao', {'entidade': moduloSelecionado});
								});
							});
						</script>
					</td>
					<td>
						<div id="blocoDeOperacoes">
							<!-- Se mudar aqui lembre-se de mudar a página "_listaDeOperacoes.ftl" -->
							<@ww.select id="operacao" name="operacao" list="operacoes" cssStyle="width: 250px;" theme="simple"/>
						</div>
					</td>
				</tr>
			</table>
			<@ww.textfield label="Dados" name="dados" id="dados" cssStyle="width: 300px;"/>
			
			<input id="btnPesquisar" type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
			<br><br>
			<@ww.hidden id="pagina" name="page"/>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>

	<@display.table name="auditorias" id="auditoria" class="dados" >
		<@display.column property="data" title="Data / Hora" format="{0,date,dd/MM/yyyy HH:mm}" style="text-align: center;width: 150px;"/>
		<@display.column property="usuario.nomeFormatado" title="Usuário"/>
		<@display.column property="entidade" title="Movimentação"/>
		<@display.column title="Operação" property="operacao" />
		<@display.column title="Detalhes" style="text-align: center;width:50px;">
			<a title="Ver Informação" href="javascript:popup('<@ww.url includeParams="none" value="viewAuditoria.action?internalToken=${internalToken}&auditoriaView.id=${auditoria.id?string?replace('.', '')}" />', 610, 750)">
				<img border="0" title="Visualizar" src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>">
			</a>
		</@display.column>
	</@display.table>

	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="formBusca"/>
</body>
</html>