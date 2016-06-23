<html>
	<head>
		<@ww.head/>
		<title>Ordem de Serviço</title>

		<#include "../ftl/mascarasImports.ftl" />
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/OrdemDeServicoDWR.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
		
		<style type="text/css">
			table {
			    border-collapse: collapse;
			    width: 960px;
			    height: 19px;
			}
		</style>
		
	</head>
	<body>
		<table border='1'>
			<tr >
				<td border="1px" colspan = "2" align="center" valign="middle">
					<span style="font-weight: bold; font-size: 15px;">ORDEM DE SERVIÇO</span>
				</td>
			</tr>
			<tr>
				<td>
					<span style="font-weight: bold;">Código CBO: </span> <span id="codigoCBOOS">${ordemDeServico.codigoCBO}</span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
					<span style="font-weight: bold;">Colaborador: </span> <span id="nomeColaboradorOS">${ordemDeServico.nomeColaborador}</span> 
					</br>
					<span style="font-weight: bold;">Data de Admissão: </span> <span id="dataAdmissaoFormatadaOS">${ordemDeServico.dataAdmisaoColaboradorFormatada}</span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span style="font-weight: bold;">Função: </span> <span id="nomeFuncaoOS">${ordemDeServico.nomeFuncao}</span>
				</td>
			</tr>
		</table>
		</br>
		<table>
			<tr>
				<td border="1px" colspan = "2" align="center" valign="middle">
					<span style="font-weight: bold; font-size: 15px;">ATIVIDADES DESENVOLVIDAS</span>
				</td>
			</tr>
		</table>
		<table border='1'>
			<tr>
				<td colspan = "2">
					${ordemDeServico.atividades?replace('\n','</br>')}
				</td>
			</tr>
		</table>
		</br>
		<table>
			<tr>
				<td border="1px" colspan = "2" align="center" valign="middle">
					<span style="font-weight: bold; font-size: 15px;">RISCO DA OPERAÇÃO</span>
				</td>
			</tr>
		</table>
		<table border='1'>
			<tr>
				<td colspan = "2">
					${ordemDeServico.riscos?replace('\n','</br>')}
				</td>
			</tr>
		</table>
		</br>
		<table>
			<tr>
				<td border="1px" colspan = "2" align="center" valign="middle">
					<span style="font-weight: bold; font-size: 15px;">EPI’S - USO OBRIGATÓRIO</span>
				</td>
			</tr>
		</table>
		<table border='1'>
			<tr>
				<td colspan = "2">
					${ordemDeServico.epis?replace('\n','</br>')}
				</td>
			</tr>
		</table>
		</br>
		<table>
			<tr>
				<td border="1px" colspan = "2" align="center" valign="middle">
					<span style="font-weight: bold; font-size: 15px;">MEDIDAS PREVENTIVAS</span>
				</td>
			</tr>
		</table>
		<table border='1'>
			<tr>
				<td colspan = "2">
					${ordemDeServico.medidasPreventivas?replace('\n','</br>')}
				</td>
			</tr>
		</table>
		</br>
		<table>
			<tr>
				<td border="1px" colspan = "2" align="center" valign="middle">
					<span style="font-weight: bold; font-size: 15px;">TREINAMENTO(S) NECESSÁRIO(S)</span>
				</td>
			</tr>
		</table>
		<table border='1'>
			<tr>
				<td colspan = "2">
					${ordemDeServico.treinamentos?replace('\n','</br>')}
				</td>
			</tr>
		</table>
		</br>
		<table>
			<tr>
				<td border="1px" colspan = "2" align="center" valign="middle">
					<span style="font-weight: bold; font-size: 15px;">NORMAS INTERNAS</span>
				</td>
			</tr>
		</table>
		<table border='1'>
			<tr>
				<td colspan = "2">
					${ordemDeServico.normasInternas?replace('\n','</br>')}
				</td>
			</tr>
		</table>	
		</br>
		<table>
			<tr>
				<td border="1px" colspan = "2" align="center" valign="middle">
					<span style="font-weight: bold; font-size: 15px;">PROCEDIMENTO EM CASO DE ACIDENTE DE TRABALHO</span>
				</td>
			</tr>
		</table>
		<table border='1'>
			<tr>
				<td colspan = "2">
					${ordemDeServico.procedimentoEmCasoDeAcidente?replace('\n','</br>')}
				</td>
			</tr>
		</table>
		</br>
		<table>
			<tr>
				<td border="1px" colspan = "2" align="center" valign="middle">
					<span style="font-weight: bold; font-size: 15px;">INFORMAÇÕES ADICIONAIS</span>
				</td>
			</tr>
		</table>
		<table border='1'>
			<tr>
				<td colspan = "2">
					${ordemDeServico.informacoesAdicionais?replace('\n','</br>')}
				</td>
			</tr>
		</table>
		</br>
		<table>
			<tr>
				<td border="1px" colspan = "2" align="center" valign="middle">
					<span style="font-weight: bold; font-size: 15px;">TERMO DE RESPONSABILIDADE</span>
				</td>
			</tr>
		</table>
		<table border='1'>
			<tr>
				<td colspan = "2">
					${ordemDeServico.termoDeResponsabilidade?replace('\n','</br>')}
				</td>
			</tr>
		</table>
		</br>
		<table border='1'>
			<tr>
				<td border="1px" style ="width: 480px;">
					<span style="font-weight: bold;">Revisão: </span> <span id="codigoCBOOS">${ordemDeServico.revisao}</span>
				</td>
				<td border="1px" style ="width: 480px;">
					<span style="font-weight: bold;">Data: </span> <span id="codigoCBOOS">${ordemDeServico.dataFormatada}</span>
				</td>
			</tr>
		</table>		
		<div class="buttonGroup">
			<button onclick="window.location='list.action?colaborador.id=${ordemDeServico.colaborador.id}'" class="btnVoltar"></button>
		</div>
	</body>
</html>
