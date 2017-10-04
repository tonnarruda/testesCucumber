<html>
	<head>
		<@ww.head/>
		<title>Ordem de Serviço</title>

		<#include "../ftl/mascarasImports.ftl" />
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/OrdemDeServicoDWR.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
		
		<style type="text/css">
			.bordas {
			    border-collapse: collapse;
			    border: 1px solid;
			    border-color: #D9D9D9;
			}
			.titulo{
				font-weight: bold; 
				font-size: 13px;
			}
			
			table{
			    width: 960px;
			    height: 19px;
			}
			.dados_pessoais {
				float: left;
				margin-top: 5px;	
			}
			.dados_pessoais_field {
				width: 470px;
				padding: 2px 4px;
				float: left;
			}	
		</style>
		
	</head>
	<body>
		<table class="bordas" border="1">

			<tr >
				<td border="1px" colspan = "2" align="center" valign="middle">
					<span style="font-weight: bold; font-size: 15px;">${ordemDeServico.nomeEmpresa} </span><br>
					<span style="font-size: 13px;">${ordemDeServico.nomeEstabelecimento} - ${ordemDeServico.cnpjFormatado}</span><br>
					<span style="font-size: 13px;">${ordemDeServico.estabelecimentoEndereco}</span><br>
				</td>
			</tr>
		</table>
		</br>
		<table class="bordas" border="1">
			<tr >
				<td border="1px" colspan = "2" align="center" valign="middle">
					<span style="font-weight: bold; font-size: 15px;">ORDEM DE SERVIÇO</span>
				</td>
			</tr>
			<tr>
				<td>
					<div class='dados_pessoais' style="border-bottom: #D9D9D9 solid 1px;">
						<div class='dados_pessoais_field'><strong>Colaborador:</strong> ${ordemDeServico.nomeColaborador}</div>
						<div class='dados_pessoais_field'><strong>Data de Admissão:</strong> ${ordemDeServico.dataAdmisaoColaboradorFormatada}</div>
					</div>
					
					<div class='dados_pessoais' style="border-bottom: #D9D9D9 solid 1px;">
						<div class='dados_pessoais_field'><strong>Cargo:</strong> ${ordemDeServico.nomeCargo}</div>
						<#if ordemDeServico.codigoCBOCargo?exists>
							<div class='dados_pessoais_field'><strong>CBO:</strong> ${ordemDeServico.codigoCBOCargo}</div>
						<#else>
							<div class='dados_pessoais_field'><strong>CBO:</strong> Cargo sem código CBO</div>
						</#if>
					</div>
					
					<div class='dados_pessoais'">
						<div class='dados_pessoais_field'><strong>Função:</strong> ${ordemDeServico.nomeFuncao}</div>
						<#if ordemDeServico.codigoCBOFuncao?exists>
							<div class='dados_pessoais_field'><strong>CBO:</strong> ${ordemDeServico.codigoCBOFuncao}</div>
						<#else>
							<div class='dados_pessoais_field'><strong>CBO:</strong> Função sem código CBO</div>
						</#if>
					</div>
				</td>
			</tr>
		</table>
		</br>
		<table>
			<tr>
				<td colspan = "2" align="center" valign="middle">
					<span class="titulo">ATIVIDADES DESENVOLVIDAS</span>
				</td>
			</tr>
		</table>
		<table class="bordas" >
			<tr>
				<td colspan = "2">
					${ordemDeServico.atividades?replace('\n','</br>')}
				</td>
			</tr>
		</table>
		</br>
		<table>
			<tr>
				<td colspan = "2" align="center" valign="middle">
					<span class="titulo">RISCO DA OPERAÇÃO</span>
				</td>
			</tr>
		</table>
		<table class="bordas" >
			<tr>
				<td colspan = "2">
					${ordemDeServico.riscos?replace('\n','</br>')}
				</td>
			</tr>
		</table>
		</br>
		<table>
			<tr>
				<td colspan = "2" align="center" valign="middle">
					<span class="titulo">EQUIPAMENTOS DE PROTEÇAO INDIVIDUAL (EPI'S) - USO OBRIGATÓRIO</span>
				</td>
			</tr>
		</table>
		<table class="bordas" >
			<tr>
				<td colspan = "2">
					${ordemDeServico.epis?replace('\n','</br>')}
				</td>
			</tr>
		</table>
		</br>
		<table>
			<tr>
				<td colspan = "2" align="center" valign="middle">
					<span class="titulo">MEDIDAS PREVENTIVAS</span>
				</td>
			</tr>
		</table>
		<table class="bordas" >
			<tr>
				<td colspan = "2">
					${ordemDeServico.medidasPreventivas?replace('\n','</br>')}
				</td>
			</tr>
		</table>
		</br>
		<table>
			<tr>
				<td colspan = "2" align="center" valign="middle">
					<span class="titulo">TREINAMENTO(S) NECESSÁRIO(S)</span>
				</td>
			</tr>
		</table>
		<table class="bordas" >
			<tr>
				<td colspan = "2">
					${ordemDeServico.treinamentos?replace('\n','</br>')}
				</td>
			</tr>
		</table>
		</br>
		<table>
			<tr>
				<td colspan = "2" align="center" valign="middle">
					<span class="titulo">NORMAS INTERNAS</span>
				</td>
			</tr>
		</table>
		<table class="bordas" >
			<tr>
				<td colspan = "2">
					${ordemDeServico.normasInternas?replace('\n','</br>')}
				</td>
			</tr>
		</table>	
		</br>
		<table>
			<tr>
				<td colspan = "2" align="center" valign="middle">
					<span class="titulo">PROCEDIMENTO EM CASO DE ACIDENTE DE TRABALHO</span>
				</td>
			</tr>
		</table>
		<table class="bordas" >
			<tr>
				<td colspan = "2">
					${ordemDeServico.procedimentoEmCasoDeAcidente?replace('\n','</br>')}
				</td>
			</tr>
		</table>
		</br>
		
		<#if ordemDeServico.informacoesAdicionais?exists && ordemDeServico.informacoesAdicionais != "">
			<table>
				<tr>
					<td colspan = "2" align="center" valign="middle">
						<span class="titulo">INFORMAÇÕES ADICIONAIS</span>
					</td>
				</tr>
			</table>
			<table class="bordas" >
				<tr>
					<td colspan = "2">
						${ordemDeServico.informacoesAdicionais?replace('\n','</br>')}
					</td>
				</tr>
			</table>
			</br>
		</#if>
		<table>
			<tr>
				<td colspan = "2" align="center" valign="middle">
					<span class="titulo">TERMO DE RESPONSABILIDADE</span>
				</td>
			</tr>
		</table>
		<table class="bordas" >
			<tr>
				<td colspan = "2">
					${ordemDeServico.termoDeResponsabilidade?replace('\n','</br>')}
				</td>
			</tr>
		</table>
		</br>
		<table class="bordas" border="1px">
			<tr>
				<td border="1px" style ="width: 480px;">
					<span style="font-weight: bold;">Revisão: </span> <span id="revisaoOS">${ordemDeServico.revisao}</span>
				</td>
				<td border="1px" style ="width: 480px;">
					<span style="font-weight: bold;">Data: </span> <span id="dataOS">${ordemDeServico.dataFormatada}</span>
				</td>
			</tr>
		</table>		
		<div class="buttonGroup">
			<button onclick="window.location='list.action?colaborador.id=${ordemDeServico.colaborador.id}'" class="btnVoltar"></button>
		</div>
	</body>
</html>
