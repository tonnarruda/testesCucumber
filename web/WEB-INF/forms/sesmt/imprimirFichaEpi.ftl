<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/fortes.js?version=${versao}"/>'></script>

	<#include "../ftl/mascarasImports.ftl" />

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<#assign formAction="imprimirFicha.action" />

	<#if (colaboradors?exists && colaboradors?size > 0)>
		<#assign headerValue="Selecione..."/>
	<#else>
		<#assign headerValue="Utilize o Filtro acima."/>
	</#if>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/SolicitacaoEpiDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>

	<script type='text/javascript'>
		
		function mensagemSemEpis()
	    {
			if($("#colaborador").find(":selected").val() == "0"){
				return "Selecione um colaborador para popular as solicitações de EPI.";
			} else {
				return "O colaborador selecionado não possui solicitação de EPI.";
			}
	    }
		    
		$(document).ready(function(){
			$('#listCheckBoxsolicitacoesEpiCheck').append('<div class="info"> <ul> <li>Selecione um colaborador para popular as solicitações de EPI.</li> </ul> </div>');
		});
		
		function buscaSolicitacao(colaboradorId)
		{
			$('#listCheckBoxsolicitacoesEpiCheck').css("background", "#FFFFFF");
			DWRUtil.useLoadingMessage('Carregando...');
			SolicitacaoEpiDWR.getByColaboradorId(listSolicitacoes, colaboradorId);
		}

		function listSolicitacoes(solicitacoesEpi)
		{
			$('#listCheckBoxsolicitacoesEpiCheck').empty();
			$("#listCheckBoxsolicitacoesEpiCheck > .info").remove();

			if(solicitacoesEpi.length == 0){
				$('#listCheckBoxsolicitacoesEpiCheck').append('<div class="info"> <ul> <li>'+mensagemSemEpis()+'</li> </ul> </div>');
			}else{
				addChecksByCollection('solicitacoesEpiCheck',solicitacoesEpi);
			}
		}
		
		function imprimir()
		{
			if($("input[name='solicitacoesEpiCheck']").length == 0){
				$('#listCheckBoxsolicitacoesEpiCheck').css("background", "#FFEEC2");
				return false;
			} else {
				return validaFormulario('formRelatorio',new Array('colaborador','@solicitacoesEpiCheck'),null);
			}
		}
		
	</script>

	<title>Ficha de EPI</title>

</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />

	<@ww.form name="form" action="filtroImprimirFicha.action" method="POST" >
	
			<li>
				<@ww.div cssClass="divInfo" cssStyle="width: 592px;">
					<ul>
						<span id="divColaborador">
							<@ww.textfield label="Nome" name="colaborador.nome" id="nomeColaborador" cssStyle="width: 300px;"/>
							<@ww.textfield label="Matrícula" name="colaborador.matricula" id="matriculaBusca" liClass="liLeft" cssStyle="width: 60px;"/>
							<@ww.textfield label="CPF" name="colaborador.pessoal.cpf" id="cpfColaborador" cssClass="mascaraCpf"/>
						</span>
	
						<input type="submit" value="" class="btnPesquisar grayBGDivInfo" />
					</ul>
				</@ww.div>
			</li>
		<br/>
	</@ww.form>

	<@ww.form id="formRelatorio" name="formRelatorio" action="${formAction}" method="post">
		<br/>
		<@ww.select label="Colaborador" name="colaborador.id" id="colaborador" list="colaboradors" required="true" listKey="id" listValue="nomeCpf" headerKey="0" headerValue="${headerValue}" onchange="buscaSolicitacao(this.value);" cssStyle="width: 605px;"/>
		
		<@frt.checkListBox name="solicitacoesEpiCheck" id="solicitacoesEpiCheck" label="Solicitações de EPI" list="solicitacoesEpiCheckList" width="600" form="document.getElementById('formRelatorio')" filtro="true" required="true"/>	

		<@ww.checkbox label="Imprimir verso" id="imprimirVerso" name="imprimirVerso" labelPosition="left"/>
		
		<div class="buttonGroup">
			<button class="btnImprimirPdf" onclick="return imprimir();"></button>
		</div>

		<@ww.hidden name="colaborador.nome" />
		<@ww.hidden name="colaborador.pessoal.cpf" />
		<@ww.hidden name="colaborador.matricula" />
	</@ww.form>

</body>
</html>