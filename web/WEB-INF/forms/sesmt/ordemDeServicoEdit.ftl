<html>
	<head>
		<@ww.head/>
		<#include "../ftl/mascarasImports.ftl" />
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/OrdemDeServicoDWR.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
		<script type="text/javascript">
			function repopularOrdemDeServico()
			{
				OrdemDeServicoDWR.recarregaDadosOrdemDeServico(repopularOrdemDeServicoByDados, ${ordemDeServico.id}, ${ordemDeServico.colaborador.id}, ${empresaSistema.id}, $('#dataOS').val());
			}
			
			function repopularOrdemDeServicoByDados(dados){
				$('#nomeColaboradorOS').text(dados["nomeColaborador"]);
				$('#dataAdmissaoFormatadaOS').text(dados["dataAdmisaoColaboradorFormatada"]);
				$('#nomeFuncaoOS').text(dados["nomeFuncao"]);
				$('#codigoCBOOS').text(dados["codigoCBO"]);
				$('#atividadesOS').text(dados["atividades"]);
				$('#riscosOS').text(dados["riscos"]);
				$('#episOS').text(dados["epis"]);
				$('#medidasPreventivasOS').text(dados["medidasPreventivas"]);
				$('#treinamentosOS').text(dados["treinamentos"]);
				$('#normasInternasOS').text(dados["normasInternas"]);
				$('#procedimentoEmCasoDeAcidenteOS').text(dados["procedimentoEmCasoDeAcidente"]);
				$('#termoDeResponsabilidadeOS').text(dados["termoDeResponsabilidadeOS"]);
			}
			
		</script>		
		
		
		
		<#if ordemDeServico.id?exists>
			<title>Editar Ordem de Serviço</title>
			<#assign formAction="update.action"/>
			<#assign data = ordemDeServico.data?date/>
		<#else>
			<title>Inserir Ordem de Serviço</title>
			<#assign formAction="insert.action"/>
			<#assign data = ""/>
		</#if>
		
		
		<#assign validarCampos="return validaFormulario('form', new Array())"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="colaborador.id" value="${ordemDeServico.colaborador.id}" />
			<@ww.hidden name="ordemDeServico.id" />
			<@ww.hidden name="ordemDeServico.colaborador.id" value="${ordemDeServico.colaborador.id}" />
			<@ww.hidden name="ordemDeServico.nomeColaborador" />
			<@ww.hidden name="ordemDeServico.dataAdmisaoColaborador" />
			<@ww.hidden name="ordemDeServico.nomeFuncao" />
			<@ww.hidden name="ordemDeServico.codigoCBO" />
			<@ww.hidden name="ordemDeServico.normasInternas" />
			<@ww.hidden name="ordemDeServico.procedimentoEmCasoDeAcidente" />
			<@ww.hidden name="ordemDeServico.termoDeResponsabilidade" />
			<@ww.token/>
			
			<table>
				<tr>
					<td width="370"> <span style="font-weight: bold;">Colaborador:</span> <span id="nomeColaboradorOS">${ordemDeServico.nomeColaborador}</span> </td>
					<td width="370"> <span style="font-weight: bold;">Data de Admissão:</span> <span id="dataAdmissaoFormatadaOS">${ordemDeServico.dataAdmisaoColaboradorFormatada}</span> </td>
				</tr>
				<tr>
					<td> <span style="font-weight: bold;">Função:</span> <span id="nomeFuncaoOS">${ordemDeServico.nomeFuncao}</span> </td>
					<td> <span style="font-weight: bold;">Código CBO</span> <span id="codigoCBOOS">${ordemDeServico.codigoCBO}</span> </td>
				</tr>
			</table>
			
			<@ww.textfield label="Nº Revisão" name="ordemDeServico.revisao" id="revisaoOS" disable="true" liClass="liLeft" cssStyle="width:180px;" maxLength="30"/>
			<@ww.datepicker label="Data da Ordem de Serviço" name="ordemDeServico.data" value="${data}" id="dataOS" required="true" cssClass="mascaraData" onchange="repopularOrdemDeServico();" />
			<@ww.textarea label="Atividades Desenvolvidas" name="ordemDeServico.atividades" id="atividadesOS" required="true" cssStyle="width:800px;height:150px;"/>
			<@ww.textarea label="Riscos da Operação" name="ordemDeServico.riscos" id="riscosOS" required="true" cssStyle="width:800px;height:150px;"/>
			<@ww.textarea label="Epi's - Uso Obrigatório" name="ordemDeServico.epis" id="episOS" required="true" cssStyle="width:800px;height:150px;"/>
			<@ww.textarea label="Medidas Preventivas" name="ordemDeServico.medidasPreventivas" required="true" id="medidasPreventivasOS" cssStyle="width:800px;height:150px;"/>
			<@ww.textarea label="Treinamentos Necessários" name="ordemDeServico.treinamentos" required="true" id="treinamentosOS" cssStyle="width:800px;height:150px;"/>
			<@ww.textarea label="Normas Internas" name="ordemDeServico.normasInternas" id="normasInternasOS" disabled="true" cssStyle="width:800px;height:451px; background-color: #ececec;"/>
			<@ww.textarea label="Procedimento em Caso de Acidente de Trabalho" name="ordemDeServico.procedimentoEmCasoDeAcidente" id="procedimentoEmCasoDeAcidenteOS" disabled="true" cssStyle="width:800px;height:150px; background-color: #ececec;"/>
			<@ww.textarea label="Informações Adicionais" name="ordemDeServico.informacoesAdicionais" id="informacoesAdicionaisOS" required="false" cssStyle="width:800px;height:150px;"/>
			<@ww.textarea label="Termo de Responsabilidade" name="ordemDeServico.termoDeResponsabilidade" id="termoDeResponsabilidadeOS"  disabled="true" cssStyle="width:800px;height:150px;background-color: #ececec;"/>
			
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action?colaborador.id=${ordemDeServico.colaborador.id}'" class="btnVoltar"></button>
		</div>
	</body>
</html>
