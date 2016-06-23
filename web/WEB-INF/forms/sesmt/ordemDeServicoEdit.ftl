<#assign ww=JspTaglibs["/WEB-INF/tlds/webwork.tld"] />
<html>
	<head>
		<@ww.head/>
		
		<#include "../ftl/mascarasImports.ftl" />
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/OrdemDeServicoDWR.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
		
		<script type="text/javascript">
			var ultimaDataValida = "";

			$(function(){
				ultimaDataValida = $('#dataOS').val();
			});

			function repopularOrdemDeServico()
			{
				var dataValida =  validaDate($('#dataOS')[0]) && $('#dataOS').val() != "  /  /    " &&  $('#dataOS').val() != "";
				if(dataValida){
					DWREngine.setErrorHandler(errorRecarregaDadosOrdemDeServico);
					OrdemDeServicoDWR.recarregaDadosOrdemDeServico(repopularOrdemDeServicoByDados, ${ordemDeServico.colaborador.id}, ${empresaSistema.id}, $('#dataOS').val());
				}
				else
					jAlert("Informe uma data válida");
			}
			
			function repopularOrdemDeServicoByDados(dados){
				ultimaDataValida = $('#dataOS').val();
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
			
			function errorRecarregaDadosOrdemDeServico(msg){
				$('#dataOS').val(ultimaDataValida);
				jAlert(msg);
			}
		</script>	
						
		<style type="text/css">
			.divTitulo{
				width: 960px;
			}
			.titulo{
				font-weight: bold; 
				font-size: 13px;
			}
			.wwctrl textarea{
				width: 960px;
				height:150px;
				border: 1px solid;
				border-color: #D9D9D9;
			}
		</style>
			
		<#if ordemDeServico.id?exists>
			<title>Editar Ordem de Serviço</title>
			<#assign formAction="update.action"/>
			<#assign dataOS = ordemDeServico.data?date/>
		<#else>
			<title>Inserir Ordem de Serviço</title>
			<#assign formAction="insert.action"/>
			<#assign dataOS = dataDoDia?date>
		</#if>
		
		<#assign validarCampos="return validaFormulario('form', new Array('atividadesOS','riscosOS','episOS','medidasPreventivasOS','treinamentosOS','normasInternasOS','procedimentoEmCasoDeAcidenteOS','termoDeResponsabilidadeOS'), new Array('dataOS'));"/>
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
			<@ww.hidden name="ordemDeServico.revisao" value="${revisao}"/>
			<@ww.token/>
			
			<table>
				<tr>
					<td><@ww.datepicker label="Data da Ordem de Serviço" name="ordemDeServico.data" value="${dataOS}" id="dataOS" required="true" cssClass="mascaraData" onchange="repopularOrdemDeServico();" onblur="repopularOrdemDeServico();" /></td>
				</tr>
				<tr>	
					<td> <span style="font-weight: bold;">Revisão: </span> <span style="margin-left: 4px;" id="nomeColaboradorOS">${revisao}</span> </td>
				</tr>
				<tr>
					<td width="250"> <span style="font-weight: bold;">Código CBO:</span> <span style="margin-left: 40px;" id="codigoCBOOS">${ordemDeServico.codigoCBO}</span> </td>
					<td> <span style="font-weight: bold;">Colaborador:</span> <span style="margin-left: 4px;" id="nomeColaboradorOS">${ordemDeServico.nomeColaborador}</span> </td>
				</tr>
				<tr>
					<td width="250"> <span style="font-weight: bold;">Data de Admissão:</span> <span style="margin-left: 4px;" id="dataAdmissaoFormatadaOS">${ordemDeServico.dataAdmisaoColaboradorFormatada}</span> </td>
					<td> <span style="font-weight: bold;">Função:</span> <span style="margin-left: 34px;" id="nomeFuncaoOS">${ordemDeServico.nomeFuncao}</span> </td>
				</tr>
			</table>

			</br><div class="divTitulo" align="center"><span class="titulo">ATIVIDADES DESENVOLVIDAS*</span></div>
			<@ww.textarea name="ordemDeServico.atividades" id="atividadesOS" required="true"/>

			</br><div class="divTitulo" align="center"><span class="titulo">RISCO DA OPERAÇÃO*</span></div>
			<@ww.textarea name="ordemDeServico.riscos" id="riscosOS" required="true"/>
			
			</br><div class="divTitulo" align="center"><span class="titulo">EQUIPAMENTOS DE PROTEÇAO INDIVIDUAL (EPI'S) - USO OBRIGATÓRIO*</span></div>
			<@ww.textarea name="ordemDeServico.epis" id="episOS" required="true"/>
			
			</br><div class="divTitulo" align="center"><span class="titulo">MEDIDAS PREVENTIVAS*</span></div>
			<@ww.textarea name="ordemDeServico.medidasPreventivas" required="true" id="medidasPreventivasOS"/>
			
			</br><div class="divTitulo" align="center"><span class="titulo">TREINAMENTO(S) NECESSÁRIO(S)*</span></div>
			<@ww.textarea name="ordemDeServico.treinamentos" required="true" id="treinamentosOS"/>
			
			</br><div class="divTitulo" align="center"><span class="titulo">NORMAS INTERNAS*</span></div>
			<@ww.textarea name="ordemDeServico.normasInternas" id="normasInternasOS" required="true"/>
			
			</br><div class="divTitulo" align="center"><span class="titulo">PROCEDIMENTO EM CASO DE ACIDENTE DE TRABALHO*</span></div>
			<@ww.textarea name="ordemDeServico.procedimentoEmCasoDeAcidente" id="procedimentoEmCasoDeAcidenteOS" required="true"/>
			
			</br><div class="divTitulo" align="center"><span class="titulo">INFORMAÇÕES ADICIONAIS</span></div>
			<@ww.textarea name="ordemDeServico.informacoesAdicionais" id="informacoesAdicionaisOS"/>
			
			</br><div class="divTitulo" align="center"><span class="titulo">TERMO DE RESPONSABILIDADE*</span></div>
			<@ww.textarea name="ordemDeServico.termoDeResponsabilidade" id="termoDeResponsabilidadeOS" required="true"/>
			
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action?colaborador.id=${ordemDeServico.colaborador.id}'" class="btnVoltar"></button>
		</div>
	</body>
</html>
