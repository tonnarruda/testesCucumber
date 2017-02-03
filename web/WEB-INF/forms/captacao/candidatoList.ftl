<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		#deleteDialog { display: none; }
	</style>
	<title>Candidatos</title>

	<#include "../ftl/mascarasImports.ftl" />
	<#include "../ftl/showFilterImports.ftl" />
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CandidatoDWR.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	<script type="text/javascript">
		$(function() {
			$('#help_exibeExterno').qtip({
				content: '<strong>Módulo externo</strong><br />Esse campo filtrara apenas os currículos que foram cadastrados através do site, pelo link Trabalhe Conosco.'
				, style: { width: '100px' }
			});
		});
		
		function checaDependenciasExclusao(candidatoId, empresaId, nome)
		{
			$('#deleteDialog').empty();
		
			CandidatoDWR.montaMensagemExclusao(candidatoId, empresaId, function(msg) {
				if (msg != '')
				{
					$('#deleteDialog').html(msg).dialog({ 	modal: true, 
															width: 700,
															maxHeight: 360,
															buttons: 
															[
															    {
															        text: "Confirmar",
															        click: function() { excluir(candidatoId, empresaId); }
															    },
															    {
															        text: "Cancelar",
															        click: function() { $(this).dialog("close"); }
															    }
															],
															open: function() {
														        $(this).closest('.ui-dialog').find('.ui-dialog-buttonpane button:eq(1)').focus(); 
															}
														});
				
				} else {
					newConfirm('Deseja realmente excluir o candidato ' + nome +'?', function(){
						excluir(candidatoId, empresaId);
					});
				}
			});
		}
		
		function excluir(candidatoId, empresaId)
		{
			window.location='delete.action?candidato.id=' + candidatoId + '&candidato.empresa.id=' + empresaId; 
		}
	</script>
	
	<#if dataCadIni?exists>
		<#assign dataValueIni = dataCadIni?date/>
	<#else>
		<#assign dataValueIni = ""/>
	</#if>
	<#if dataCadFim?exists>
		<#assign dataValueFim = dataCadFim?date/>
	<#else>
		<#assign dataValueFim = ""/>
	</#if>

	<#assign validarCampos="return validaFormulario('formBusca', null, new Array('dataIni','dataFim'), true)"/>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	
	<#assign visualizarDocumentos=false/>
	<@authz.authorize ifAllGranted="ROLE_CAND_LIST_DOCUMENTOANEXO">
		<#assign visualizarDocumentos=true/>
	</@authz.authorize>
	
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<#if msgImporta?exists>
		<center><font color=red>${msgImporta}</font></center>
	</#if>

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" id="formBusca" action="list.action" onsubmit="${validarCampos}" validate="true" method="POST">
			<@ww.select label="Empresa" id="empresaId" name="empresaId" list="empresas" listKey="id" listValue="nome" headerKey="-1" headerValue="Todas" disabled="!compartilharCandidatos"/>
			<@ww.textfield label="Nome" name="nomeBusca" liClass="liLeft" cssStyle="width: 350px;" value="${nomeBusca}"/>
			<@ww.textfield label="CPF" id="cpfBusca" name="cpfBusca" value="${cpfBusca}" cssClass="mascaraCpf"/>
			
			<@ww.textfield label="DDD" name="ddd" id="ddd" onkeypress="return somenteNumeros(event,'');" cssStyle="width: 25px;" maxLength="2"  liClass="liLeft"/>
			<@ww.textfield label="Telefone" name="foneFixo" id="fone" onkeypress="return somenteNumeros(event,'');"  cssStyle="width: 80px;" maxLength="9"  liClass="liLeft" />
			<@ww.textfield label="Celular" id="celular" name="foneCelular" onkeypress = "return somenteNumeros(event,'');" cssStyle="width: 80px;" maxLength="9" liClass="campo"/>
			
			<@ww.textfield label="Indicado por" name="indicadoPor" />
			<@ww.textfield label="Observações do RH" name="observacaoRH"  cssStyle="width: 240px;"/>

			<@ww.select id="visualizacao" label="Visualizar" name="visualizar" list=r"#{'T':'Todos','D':'Disponíveis','I':'Indisponíveis'}"  liClass="liLeft"/>

			<@ww.datepicker label="Atualização" name="dataCadIni" id="dataIni" value="${dataValueIni}" cssClass="mascaraData" liClass="liLeft"/>
			<@ww.datepicker label="" name="dataCadFim" id="dataFim" value="${dataValueFim}" cssClass="mascaraData"/>
			<@ww.hidden id="pagina" name="page"/>
			
			<@ww.checkbox label="Exibir também contratados" id="exibeContratados" name="exibeContratados" labelPosition="left"/>
			<@ww.checkbox label="" id="exibeExterno" name="exibeExterno" labelPosition="left" theme="simple" />
			Exibir somente cadastrados pelo módulo externo
			<span class="hotspot" id="help_exibeExterno" ><img src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" /></span><br>

			<input type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>

	<p>
		<span style="background-color: #454C54;">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Disponíveis&nbsp;&nbsp;<span style="background-color: #F00;">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Indisponíveis
	</P>

	<@display.table name="candidatos" id="candidato" class="dados">

		<#if candidato.disponivel = true>
			<#assign color="#454C54"/>
		<#else>
			<#assign color="#F00"/>
		</#if>

		<@display.column title="Ações" media="html" style="text-align:center; width: 140px;" >
	    	<#if candidato.disponivel = true>
				<#assign nomeFormatado=stringUtil.removeApostrofo(candidato.nome)>
				<a href="javascript:newConfirm('Deseja realmente contratar o candidato ${nomeFormatado}?', function(){window.location='<@ww.url includeParams="none" value="/geral/colaborador/prepareContrata.action?candidato.id=${candidato.id}"/>'});"><img border="0" title="Contratar Candidato" src="<@ww.url includeParams="none" value="/imgs/contrata_colab.gif"/>"></a>
			<#else>
				<img border="0" title="Candidato já contratado" src="<@ww.url includeParams="none" value="/imgs/contrata_colab.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			</#if>
			<a href="javascript:popup('infoCandidato.action?candidato.id=${candidato.id}&origemList=CA', 580, 750)"><img border="0" title="Visualizar Currículo" src="<@ww.url includeParams="none" value="/imgs/page_curriculo.gif"/>"></a>
			<a href="prepareUpdate.action?candidato.id=${candidato.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<a href="javascript:checaDependenciasExclusao(${candidato.id}, ${candidato.empresa.id}, '${candidato.nome}');"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
			<a href="prepareUpdateCurriculo.action?candidato.id=${candidato.id}"><img border="0" title="Currículo Escaneado" src="<@ww.url includeParams="none" value="/imgs/cliper.gif"/>"></a>
			<#if visualizarDocumentos = true>
				<a href="../../geral/documentoAnexo/listCandidato.action?documentoAnexo.origem=C&documentoAnexo.origemId=${candidato.id}"><img border="0" title="Documentos do Candidato" src="<@ww.url includeParams="none" value="/imgs/anexos.gif"/>"></a>
			</#if>
			<@authz.authorize ifAllGranted="ROLE_MOV_SOLICITACAO">
				<a href="../../captacao/solicitacao/verSolicitacoes.action?candidato.id=${candidato.id}&statusCandSol=I"><img border="0" title="Incluir em Solicitação" src="<@ww.url includeParams="none" value="/imgs/usuarios.gif"/>"></a>
			</@authz.authorize>
			
		</@display.column>

		<@display.column title="Nome" style="color: ${color}">
			${candidato.nome}
			<#if candidato.pessoal?exists && candidato.pessoal.indicadoPor?exists && candidato.pessoal.indicadoPor?trim != "">
				<span href=# style="cursor: hand;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'Indicado por: <br>${candidato.pessoal.indicadoPor?j_string}');return false">
					<img border="0" src="<@ww.url includeParams="none" value="/imgs/favourites.gif"/>">
				</span>
			</#if>
			<#if candidato.contratado>
				<span href=# style="cursor: hand;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'Este candidato é colaborador');return false">
					<img border="0" src="<@ww.url includeParams="none" value="/imgs/colaborador.png"/>">
				</span>
			<#elseif candidato.jaFoiColaborador>
				<span href=# style="cursor: hand;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'Este candidato já foi colaborador ou existe um colaborador com o mesmo CPF em uma das empresas.');return false">
					<img border="0" src="<@ww.url includeParams="none" value="/imgs/jaFoiColaborador.png"/>">
				</span>
			</#if>
		</@display.column>

		<@display.column property="dataCadastro" format="{0,date,dd/MM/yyyy}" title="Data Cadastro" style="text-align:center;width:80px;color: ${color}"/>
		<@display.column property="dataAtualizacao" format="{0,date,dd/MM/yyyy}" title="Data Atualização" style="text-align:center;width:80px;color: ${color}"/>

	</@display.table>

	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="formBusca"/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I"></button>
		<button class="btnInserirCurriculoEscaneado" onclick="window.location='prepareInsertCurriculoPlus.action'" accesskey="P"></button>
		<button class="btnInserirCurriculoDigitado" onclick="window.location='prepareInsertCurriculoTexto.action'" ></button>
		<button class="btnTriagem" onclick="window.location='prepareBusca.action'"></button>
	</div>
	
	<div id="deleteDialog" title="Confirmar exclusão"></div>
</body>
</html>