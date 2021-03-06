<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	</style>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>
	<script type="text/javascript">
		$(function() {
			$('.tooltipHelp').qtip({
				content: 'Não foi possível obter o salário. Verifique se o índice/faixa possui histórico nesta data.'
			});
		});
		$(function() {
			$('.tooltipHelpHistoricoFaixa').qtip({
				content: 'Não foi possível obter o histórico da faixa salarial. Verifique se a mesma possui histórico nesta data.'
			});
		});
	</script>
	
	<title>Editar Situações do Colaborador - ${colaborador.nome}</title>
	
	<#if empresaIntegradaEAderiuAoESocial>
		<#assign empresaEstaIntegradaEAderiuAoESocial=true/>
	<#else>
		<#assign empresaEstaIntegradaEAderiuAoESocial=false/>
	</#if>
	
</head>
<body>
	<@ww.actionerror/>
	<@ww.actionmessage/>
	<@display.table name="historicoColaboradors" id="historicoColaborador" class="dados" defaultsort=2 >
		<@display.column title="Ações" style="text-align:center;width:40px">
			<#if historicoColaboradors?size == 1 && colaborador.codigoAC?exists && colaborador.codigoAC == "" && !colaborador.naoIntegraAc>
				<img border="0" title="Edição da primeira situação pode ser realizada no cadastro de colaborador." src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">			
			<#else>
				<a href="prepareUpdate.action?historicoColaborador.id=${historicoColaborador.id}&colaborador.id=${colaborador.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			</#if>
			
			<#if (historicoColaboradors?size == 1)>
				<img border="0" title="Não é possível excluir a primeira situação." src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.5;filter:alpha(opacity=50);">
			<#elseif !empresaEstaIntegradaEAderiuAoESocial || colaborador.naoIntegraAc || historicoColaborador.status == 3 >
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?historicoColaborador.id=${historicoColaborador.id}&colaborador.id=${colaborador.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			<#else>
				<img border="0" title="Devido as adequações ao eSocial, não é possível excluir a situação do colaborador no Fortes RH." src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.5;filter:alpha(opacity=50);">
			</#if>
			
		</@display.column>
		<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width:60px"/>
		<@display.column title="Estabelecimento">
			${historicoColaborador.estabelecimento.nome}
		</@display.column>
		<@display.column title="Cargo/Faixa Salarial">
			${historicoColaborador.faixaSalarial.descricao}
		</@display.column>
		<@display.column property="areaOrganizacional.descricao" title="Área Organizacional" style="width:250px"/>

		<@display.column property="descricaoTipoSalario" title="Tipo" style="width:100px;"/>
		<@display.column title="Salário" style="text-align:right; width:80px;">
			<#if historicoColaborador.salarioCalculado?exists>
				${historicoColaborador.salarioCalculado?string.currency}
			<#else>
				<div style="width: 100%; text-align: center;">
					<img class="tooltipHelp" src="<@ww.url value="/imgs/iconWarning.gif"/>" />
				</div>
			</#if>
		</@display.column>
		
		<#if integradoAC>
			<@display.column title="Status no AC" style="width: 50px;text-align: center;">
				<#if historicoColaborador.tipoSalario == 1>
					<#if historicoColaborador.faixaSalarial.faixaSalarialHistoricoAtual.status?exists>
						<#if (historicoColaborador.faixaSalarial.faixaSalarialHistoricoAtual.status == 1 && historicoColaborador.status == 1) >
							<img border="0" title="${statusRetornoAC.getDescricao(historicoColaborador.status)}" 
							src="<@ww.url includeParams="none" value="/imgs/"/>${statusRetornoAC.getImg(1)}">
						<#else>
							<img border="0" title="Histórico do colaborador: ${statusRetornoAC.getDescricao(historicoColaborador.status)}
Histórico da faixa salarial: ${statusRetornoAC.getDescricao(historicoColaborador.faixaSalarial.faixaSalarialHistoricoAtual.status)}"
								<#if historicoColaborador.faixaSalarial.faixaSalarialHistoricoAtual.status != 1 >
									src="<@ww.url includeParams="none" value="/imgs/"/>${statusRetornoAC.getImg(2)}">
								<#else>
									src="<@ww.url includeParams="none" value="/imgs/"/>${statusRetornoAC.getImg(historicoColaborador.status)}">
								</#if>
						</#if>
					<#else>
						<div style="width: 100%; text-align: center;">
							<img class="tooltipHelpHistoricoFaixa" src="<@ww.url value="/imgs/iconWarning.gif"/>" />
						</div>
					</#if>
				<#else>
					<img border="0" title="${statusRetornoAC.getDescricao(historicoColaborador.status)}"
					src="<@ww.url includeParams="none" value="/imgs/"/>${statusRetornoAC.getImg(historicoColaborador.status)}">
				</#if>
			</@display.column>
		</#if>
	</@display.table>

	<div class="buttonGroup">
		<button onclick="window.location='prepareInsert.action?colaborador.id=${colaborador.id}&novoHistorico=true'" class="btnInserir"></button>
		<button onclick="window.location='list.action?colaborador.id=${colaborador.id}'" class="btnVoltar"></button>
	</div>
</body>
</html>