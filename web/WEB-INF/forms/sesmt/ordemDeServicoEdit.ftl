<html>
	<head>
	
		<#include "../ftl/mascarasImports.ftl" />
		<@ww.head/>
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
			<@ww.hidden name="ordemDeServico.id" />
			<@ww.hidden name="colaborador.id" />
			<@ww.token/>
			
			<table>
				<tr>
					<td width="370"> <span style="font-weight: bold;">Colaborador:</span> ${colaborador.nome} </td>
					<td width="370"> <span style="font-weight: bold;">Data de Admissão:</span> ${colaborador.dataAdmissaoFormatada} </td>
				</tr>
				<tr>
					<td> <span style="font-weight: bold;">Função:</span> ${colaborador.funcaoNome} </td>
					<td> <span style="font-weight: bold;">Código CBO</span> ${colaborador.cargoCodigoCBO} </td>
				</tr>
			</table>
			
			<@ww.textfield label="Nº Revisão" name="ordemDeServico.revisao" id="revisaoOS" disable="true" liClass="liLeft" cssStyle="width:180px;" maxLength="30"/>
			<@ww.datepicker label="Data da Ordem de Serviço" name="ordemDeServico.data" value="${data}" id="dataOS" required="true" cssClass="mascaraData"/>
			<@ww.textarea label="Atividades Desenvolvidas" name="ordemDeServico.atividades" value="${colaborador.funcao.historicoAtual.descricao}" id="atividadesOS" cssStyle="width:800px;height:150px;"/>
			<@ww.textarea label="Riscos da Operação" name="ordemDeServico.riscos" id="riscosOS" value="${riscosDaOperacao}" cssStyle="width:800px;height:150px;"/>
			<@ww.textarea label="Epi's - Uso Obrigatório" name="ordemDeServico.epis" id="episOS" value="${epis}" cssStyle="width:800px;height:150px;"/>
			<@ww.textarea label="Medidas Preventivas" name="ordemDeServico.medidasDePrevencao" id="medidasPreventivasOS"  value="${medidasPreventivas}" cssStyle="width:800px;height:150px;"/>
			<@ww.textarea label="Treinamentos Necessários" name="ordemDeServico.treinamentos" id="treinamentosOS" value="${treinamentos}" cssStyle="width:800px;height:150px;"/>
			<@ww.textarea label="Normas Internas" name="ordemDeServico.normasInternas" id="normasInternasOS" value="${empresaSistema.normasInternas}" disabled="true" cssStyle="width:800px;height:451px; background-color: #ececec;"/>
			<@ww.textarea label="Procedimento em Caso de Acidente de Trabalho" name="ordemDeServico.procedimentoEmCasoDeAcidente" value="${empresaSistema.procedimentoEmCasoDeAcidente}" id="procedimentoEmCasoDeAcidenteOS" disabled="true" cssStyle="width:800px;height:150px; background-color: #ececec;"/>
			<@ww.textarea label="Termo de Responsabilidade" name="ordemDeServico.termoDeResponsabilidade" value="${empresaSistema.termoDeResponsabilidade}" id="termoDeResponsabilidadeOS"  disabled="true" cssStyle="width:800px;height:150px;background-color: #ececec;"/>
			
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
