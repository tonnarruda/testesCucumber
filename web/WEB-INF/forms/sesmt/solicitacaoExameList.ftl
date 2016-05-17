<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/SolicitacaoExameDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

	<style type="text/css">
	<#-- não modifique os nomes. -->
	.NORMAL
	{
		color: #002EB8 !important;
	}
	.ANORMAL
	{
		color: #EF030F !important;
	}
	
	#previews {
		height: 150px;
   		width: 295px;
   		margin: 10px auto;
	}
	
	#previews .box-type {
		height: 170px;
		text-align: center;
		float: left;
		margin-right: 15px;
		margin-bottom: 10px;
	}
	
	#previews input {
		margin-bottom: 10px;
	}
	
	#previews .box-type:hover, #previews .box-type.selected {
		color: #5292C0;
		cursor: pointer;
	}
	
	#previews .box-type:hover .paper, #previews .box-type.selected .paper {
		border: 1px solid #5292C0;
	}
	
	#previews .paper {
		width: 70px;
	    height: 99px;
	    border: 1px solid #e7e7e7;
	    padding: 5px;
	    box-shadow: 1px 1px 0px #e7e7e7;
	    margin-bottom: 10px;
	}
	
	#previews .box-type-disabled:hover {
		color: #000 !important;
	}
	
	#previews .box-type-disabled:hover .paper {
		border: 1px solid #e7e7e7 !important;
		cursor: default !important;
	}
	
	#previews .box-type-disabled .paper {
		background-color: #EEEEEE !important;
	}
	
	#previews .paper .x1 {
		width: 68px;
    	height: 97px;
    	border: 1px solid #C3C3C3;
	}
	#previews .paper .x2 {
		width: 66px;
	    height: 46px;
	    margin: 1px;
	    margin-bottom: 2px;
	    border: 1px solid #C3C3C3;
	}
	#previews .paper .x4 {
		width: 31px;
	    height: 46px;
	    float: left;
	    margin: 1px;
	    border: 1px solid #C3C3C3;
	}
	
	.buttons {
		width: 205px;
		margin: 0 auto;
	}
	
	.ui-dialog{
		position: fixed !important;
	}
	</style>
	
	<#include "../ftl/mascarasImports.ftl" />

	<script>
		$(function(){
			$(".box-type").click(function(){
				selectPrintType(this);
			});
		})
		
		function selectPrintType(box) {
			$(".box-type").removeClass("selected");
			$(box).addClass("selected");
			$(box).find("input").attr("checked", "checked");
		}
	
		function filtrarOpcao()
		{
			value = document.getElementById('vinculo').value;
			if (value == 'CANDIDATO')
				document.getElementById('divMatricula').style.display = 'none';
			else if (value == 'COLABORADOR')
				document.getElementById('divMatricula').style.display = '';
			else if (value == 'TODOS')
				document.getElementById('divMatricula').style.display = 'none';
		}
		
		function marcarComoNormal(solicitacaoExameId)
		{
			newConfirm('Marcar o resultado de todos os exames não informados como normal?', function(){
				SolicitacaoExameDWR.marcarNaoInformadosComoNormal(mudaImagem, solicitacaoExameId);
			});
		}
		
		function mudaImagem(data)
		{
			if(data != null)
			{
				for(i = 0; i < data.length; i++)
				{
					document.getElementById(data[i]).style.color = "#002EB8";
				}
			}
		}
		
		function selecionarRiscoPor(solicitacaoExameId){
			$('#solicitacaoExameId').val(solicitacaoExameId);
			$('#considerarRiscoPorDialog').dialog({ title: 'Imprimir ASO</br>' + $("#colab_" + solicitacaoExameId).text(), modal: true, width: 550, height: 165 });
		}
		
		function addPequenosModelos(){
			$('#previews').append('' +
				'<div id="x2" class="box-type">' +
					'<input type="radio" value="dividida" name="tipoDeImpressao" />' +
					'<div class="paper">' +
						'<div class="x2"></div>' +
						'<div class="x2"></div>' +
					'</div>' +
					'<div>Dividida</div>' +
				'</div>' +
				'<div id="x4" class="box-type">' +
					'<input type="radio" value="economica" name="tipoDeImpressao" />' +
					'<div class="paper">' +
						'<div class="x4"></div>' +
						'<div class="x4"></div>' +
						'<div class="x4"></div>' +
						'<div class="x4"></div>' +
					'</div>' +
					'<div>Econômica</div>' +
				'</div>' +
			'');
			
			$(".box-type").click(function(){
				selectPrintType(this);
			});
		}
		
		function addPequenosModelosBlock(){
			$('#previews').append('' +
				'<div id="x2" class="box-type box-type-disabled">' +
					'<input type="radio" value="dividida" name="tipoDeImpressao" disabled="disabled"/>' +
					'<div class="paper" >' +
						'<div class="x2"></div>' +
						'<div class="x2"></div>' +
					'</div>' +
					'<div>Dividida</div>' +
				'</div>' +
				'<div id="x4" class="box-type box-type-disabled">' +
					'<input type="radio" value="economica" name="tipoDeImpressao" disabled="disabled" />' +
					'<div class="paper" >' +
						'<div class="x4"></div>' +
						'<div class="x4"></div>' +
						'<div class="x4"></div>' +
						'<div class="x4"></div>' +
					'</div>' +
					'<div>Econômica</div>' +
				'</div>' +
			'');
		}
		
		function imprimirSolicitacaoExame(solicitacaoExameId) {
			$('#printSolicitacaoExameId').val(solicitacaoExameId);

			$('#x2,#x4').remove();
			$('#span').remove();
			if($('#muitasClinicas_' + solicitacaoExameId).val() == 'true'){
				addPequenosModelosBlock()
				heightSize = 335;
				$('#printSolicitacaoExame').append('<span id="span">OBS: Alguns modelos de impressão estão bloqueados, pois existe pelo menos uma clinica com mais de 8 exames.</span>');
			}else{
				addPequenosModelos();
				heightSize = 285;
			}

			selectPrintType($('input[name=tipoDeImpressao]:eq(0)').parent() );
			$('#printSolicitacaoExame form').attr("action", "imprimirSolicitacaoExames.action");
			$('#printSolicitacaoExame').dialog({ title: 'Imprimir Solicitação de Exames</br>' + $("#colab_" + solicitacaoExameId).text(), modal: true, width: 410, height: heightSize });
		}
		
	</script>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign validarCampos="return validaFormulario('form', null, new Array('dataIni','dataFim'), true)"/>

	<#include "../ftl/showFilterImports.ftl" />

	<#if dataIni?exists >
		<#assign dateIni = dataIni?date/>
	<#else>
		<#assign dateIni = ""/>
	</#if>
	
	<#if dataFim?exists>
		<#assign dateFim = dataFim?date/>
	<#else>
		<#assign dateFim = ""/>
	</#if>

	<title>Solicitações/Atendimentos Médicos</title>

</head>
<body>

	<#include "../util/topFiltro.ftl" />
	<@ww.form name="form" id="form" action="list.action" onsubmit="${validarCampos}" method="POST">
		
		Período:<br>
		<@ww.datepicker name="dataIni" id="dataIni"  value="${dateIni}" liClass="liLeft" cssClass="mascaraData"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="dataFim" id="dataFim" value="${dateFim}" cssClass="mascaraData" />

		<@ww.select label="Vínculo" id="vinculo" name="vinculo" list=r"#{'TODOS':'Todos','CANDIDATO':'Candidato','COLABORADOR':'Colaborador'}" onchange="filtrarOpcao();"/>

		<@ww.div id="divMatricula">
			<@ww.textfield label="Matrícula" name="matriculaBusca" id="matriculaBusca" cssStyle="width: 60px;"/>
		</@ww.div>
		
		<@ww.textfield label="Nome" name="nomeBusca" id="nomeBusca" cssStyle="width: 260px;"/>

		<@ww.select label="Motivo do Atendimento" name="motivo" id="motivoExame" list="motivos" headerKey="" headerValue="Selecione..." />
		
		<@frt.checkListBox name="examesCheck" label="Exames" list="examesCheckList" filtro="true"/>
		
		<@ww.select label="Resultado do Exame" id="resultado" name="resultado" list=r"#{'':'Todos','NORMAL':'Normal','ANORMAL':'Alterado','NAO_REALIZADO':'Não Informado'}" />
		
		<@ww.hidden id="pagina" name="page"/>
		<input type="submit" value="" onclick="document.getElementById('pagina').value = 1;" class="btnPesquisar grayBGE" />
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br/>

	<@ww.actionerror />
	<@ww.actionmessage />
	
	&nbsp;&nbsp;<span style='background-color: #002EB8;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Normal
	&nbsp;&nbsp;<span style='background-color: #EF030F;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Alterado
	&nbsp;&nbsp;<span style='background-color: #555;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Não Informado
	
	<br/><br/>

	<@display.table name="solicitacaoExames" id="solicitacaoExame" class="dados" >

		<#if solicitacaoExame.colaborador?exists>
			<#assign nomeColaborador = solicitacaoExame.colaborador.nomeComercialDesligado />
		<#elseif solicitacaoExame.candidato?exists>
			<#assign nomeColaborador = solicitacaoExame.candidato.nome />
		</#if>
	
		<@display.column title="Ações" class="acao" style="vertical-align: top;width: 140px;">
			
			<@ww.hidden id="muitasClinicas_${solicitacaoExame.id}" value="${solicitacaoExame.muitasClinicas?string}"/>
			<#if solicitacaoExame.semExames>
				<img border="0" title="Não há exames para esta solicitação/atendimento" src="<@ww.url includeParams="none" value="/imgs/cliper.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			<#else>
				<a href="prepareResultados.action?solicitacaoExame.id=${solicitacaoExame.id}&solicitacaoExame.colaboradorNome=${nomeColaborador}"><img border="0" title="Resultados" src="<@ww.url value="/imgs/cliper.gif"/>"></a>
			</#if>
			<a href="prepareUpdate.action?solicitacaoExame.id=${solicitacaoExame.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?solicitacaoExame.id=${solicitacaoExame.id}&solicitacaoExame.data=${solicitacaoExame.data}&&solicitacaoExame.ordem=${solicitacaoExame.ordem}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			
			<#if solicitacaoExame.semExames>
				<img border="0" title="Não há exames para esta solicitação/atendimento" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				<img border="0" title="Não há exames para esta solicitação/atendimento" src="<@ww.url includeParams="none" value="/imgs/check.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			<#else>
				<a href="#" onclick="imprimirSolicitacaoExame(${solicitacaoExame.id});"><img border="0" title="Imprimir Solicitação de Exames" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
				<a><img onclick="marcarComoNormal('${solicitacaoExame.id}');" border="0" title="Marcar o resultado de todos os exames não informados como normal" src="<@ww.url includeParams="none" value="/imgs/check.gif"/>" style="cursor:pointer;"></a>
			</#if>
			
			<#if solicitacaoExame.motivo != motivoCONSULTA && solicitacaoExame.motivo != motivoATESTADO>
				<a href="#" onclick="selecionarRiscoPor('${solicitacaoExame.id}')"><img border="0" title="Imprimir ASO" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
			<#else>
				<img border="0" title="Não é possível imprimir ASO para este tipo de atendimento" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">				
			</#if>
		</@display.column>
		
		<@display.column property="dataFormatada" style="width: 70px; vertical-align: top;" title="Data"/>
		
		<@display.column property="ordem" style="width: 30px; vertical-align: top; text-align: right;" title="Ordem"/>

		<@display.column style="width: 300px;vertical-align: top;" title="Nome">
			<span id="colab_${solicitacaoExame.id}">${nomeColaborador}</span>
		</@display.column>

		<@display.column title="Vínculo/Cargo" style="vertical-align: top;">
			<#if solicitacaoExame.colaborador?exists && solicitacaoExame.cargoNome?exists >
				${solicitacaoExame.cargoNome}
			</#if>
			<#if solicitacaoExame.candidato?exists>
				Candidato
			</#if>
		</@display.column>

		<@display.column property="motivoDic" title="Motivo" style="width: 140px;vertical-align: top;"/>
		<@display.column title="Exames" style="width: 200px;vertical-align: top;">
			<#list solicitacaoExame.exameSolicitacaoExames as exameSolicitacaoExame>
				<span id="${solicitacaoExame.id}_${exameSolicitacaoExame.exame.id}" class="${exameSolicitacaoExame.resultado}">
					${exameSolicitacaoExame.exame.nome}<br/>
				</span>
			</#list>
		</@display.column>
		
		<@display.footer>
		  	<tr>
				<td colspan="7" >Total : ${totalSize} solicitações</td>
	  		</tr>
		</@display.footer>
	</@display.table>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="form"/>

	<div class="buttonGroup">
		<button onclick="window.location='prepareInsert.action?nomeBusca='+escape($('#nomeBusca').val()) + '&vinculo=' +$('#vinculo').val()+'&primeiraExecucao=true'" accesskey="N" class="btnInserir"></button>
	</div>
	
	<div id="considerarRiscoPorDialog" style="display: none;">
		<@ww.form name="formConsiderarRiscoPor" id="formConsiderarRiscoPor" action="../exame/imprimirAso.action" method="" >

			<@ww.hidden name="solicitacaoExame.id" id="solicitacaoExameId"/>
			<@ww.select label="Imprimir ASO considerando risco por" name="imprimirASOComRiscoPor" list="tiposRiscoSistema" cssStyle="width: 200px; margin-top: 6px;" />
			
			<button type="submit" class="btnImprimir grayBG" onclick="$('#considerarRiscoPorDialog').dialog('close');"></button>
			<button type="button" onclick="$('#considerarRiscoPorDialog').dialog('close'); $('#considerarRiscoPorDialog input').val(''); $('#considerarRiscoPorDialog select').val('');" class="btnCancelar grayBG">	</button>
		</@ww.form>
	</div>
	
	<div id="printSolicitacaoExame" style="display: none;">
		<@ww.form name="printSolicitacaoExame" id="formPrintSolicitacaoExame" action="" method="" >
			<@ww.hidden name="solicitacaoExame.id" id="printSolicitacaoExameId"/>
			<div id="previews">
				<div id="x1" class="box-type">
					<input type="radio" value="inteira" name="tipoDeImpressao"/>
					<div class="paper">
						<div class="x1"></div>
					</div>
					<div>Inteira</div>
				</div>
				<div id="x2" class="box-type">
					<input type="radio" value="dividida" name="tipoDeImpressao"/>
					<div class="paper">
						<div class="x2"></div>
						<div class="x2"></div>
					</div>
					<div>Dividida</div>
				</div>
				<div id="x4" class="box-type">
					<input type="radio" value="economica" name="tipoDeImpressao"/>
					<div class="paper">
						<div class="x4"></div>
						<div class="x4"></div>
						<div class="x4"></div>
						<div class="x4"></div>
					</div>
					<div>Econômica</div>
				</div>
			</div>

			<div class="buttons">
				<button type="submit" class="btnImprimir grayBG" onclick="$('#printSolicitacaoExame').dialog('close');"></button>
				<button type="button" onclick="$('#printSolicitacaoExame').dialog('close'); $('#printSolicitacao input').val(''); $('#printSolicitacaoExame select').val('');" class="btnCancelar grayBG">	</button>
			</div>
		</@ww.form>
	</div>
	
	<script>
		filtrarOpcao();
	</script>
</body>
</html>