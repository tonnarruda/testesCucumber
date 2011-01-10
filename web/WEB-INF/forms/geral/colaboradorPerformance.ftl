<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
<#assign urlImg><@ww.url includeParams="none" value="/imgs"/></#assign>

<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	.grade
	{
		border:1px solid #7E9DB9;
		width: 100%;
	}
	.grade td {
		line-height: 20px;
		vertical-align: top;	
	}
	.grade .grade_field {
		float: left;
		width: 32%;
		padding: 2px 4px;
	}	
	.grade .grade_field_foto {
		float: right;
		width: 13.5%;
		padding: 10px 0px;
	}	
	ul#sortable li {
		margin: 20px 0;
		background: #FFF;
	}
	ul#sortable li h4 {
		margin: 0;
		padding: 0;
	}
	ul#sortable li h4 a {
		display: block;
		margin-bottom: -1px;
		padding: 5px;
		padding-left: 20px;
		background: #EBECF1 url('${urlImg}/baixo.gif') no-repeat 2px 3px;
		border: 1px solid #7E9DB9;
	}
	ul#sortable li h4 a.fechado {
		background: #EBECF1 url('${urlImg}/proxima.gif') no-repeat 2px 3px;
	}
	ul#sortable li h4 a:hover {
		text-decoration: none;
		color: #000;
	}
	.salvar, .exibir, .ocultar {
		float: left;
		display: block;
		margin: 3px;
		margin-right: 20px;
		padding-left: 22px;
		line-height: 18px;
	}
	.salvar{
		background: url('${urlImg}/save.gif') no-repeat;
	}
	.exibir{
		background: url('${urlImg}/baixo.gif') no-repeat;
	}
	.ocultar{
		background: url('${urlImg}/proxima.gif') no-repeat;
	}
	.painelConfiguracao{
		border: 1px solid #7E9DB9;
		background: #EBECF1;
		width: 100%;		
		height: 23px;
	}

	.painelConfiguracao a:hover {
		text-decoration: none;
		color: #000;
	}
</style>

<title>Performance Profissional</title>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign usuarioId><@authz.authentication operation="id"/></#assign>
	<#include "../ftl/showFilterImports.ftl" />

	<script type="text/javascript" src='<@ww.url includeParams="none" value="/dwr/interface/ConfiguracaoPerformanceDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/configuracaoPerformance.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
	<script type="text/javascript">
		var configPerformanceBoxes = ${configPerformanceBoxes};		
	</script>
</head>
<body>

<br>
<span id="exibir">
	<strong>Identificação do Colaborador</strong>
</span>
<div class='grade'>
	<div class='grade_field'><strong>Nome:</strong> ${colaborador.nome}</div>
	<div class='grade_field'><strong>Admissão:</strong> ${colaborador.dataAdmissao}</div>
		
	<#if colaborador.foto?exists>
		<div class='grade_field_foto'>
			<img src="<@ww.url includeParams="none" value="showFoto.action?colaborador.id=${colaborador.id}"/>" style="display:inline" id="fotoImg" width="120px" height="120px">
		</div>
	</#if>
			
	<div class='grade_field'><strong>Cargo Atual:</strong> <#if historicoColaborador?exists && historicoColaborador.faixaSalarial?exists && historicoColaborador.faixaSalarial.cargo?exists>${historicoColaborador.faixaSalarial.cargo.nome}</#if></div>
	<div class='grade_field'><strong>Estado Civil:</strong> ${colaborador.pessoal.getEstadoCivilDic()}</div>
	<div class='grade_field'><strong>Escolaridade:</strong> ${colaborador.pessoal.getEscolaridadeDic()}</div>
	<div class='grade_field'><strong>Bairro:</strong> ${colaborador.endereco.bairro}</div>
	<div class='grade_field'><strong>Endereço:</strong> ${colaborador.endereco.enderecoFormatado}</div>
	<#if colaborador.endereco?exists && colaborador.endereco.cidade?exists && colaborador.endereco.cidade.nome?exists>
		<div class='grade_field'><strong>Cidade/Estado:</strong> ${colaborador.endereco.cidade.nome} / ${colaborador.endereco.uf.sigla}</div>
	</#if>
	<div class='grade_field'><strong>CEP:</strong> ${colaborador.endereco.cepFormatado}</div>
	<#if colaborador.contato?exists && colaborador.contato.foneFixo?exists>
		<div class='grade_field'><strong>Telefone:</strong> (${colaborador.contato.ddd}) ${colaborador.contato.foneFixoFormatado}</div>
	</#if>
	<#if colaborador.contato?exists && colaborador.contato.foneCelular?exists>
		<div class='grade_field'><strong>Celular:</strong> (${colaborador.contato.ddd}) ${colaborador.contato.foneCelularFormatado}</div>
	</#if>
	<#if colaborador.pessoal?exists && colaborador.pessoal.conjuge?exists && colaborador.pessoal.conjuge != "">
		<div class='grade_field'><strong>Cônjuge:</strong> ${colaborador.pessoal.conjuge}</div>
		<div class='grade_field'><strong>Cônjuge Trabalha:</strong> <#if colaborador.pessoal.conjugeTrabalha>Sim<#else>Não</#if></div>
	</#if>
	<div class='grade_field'><strong>Deficiência:</strong> ${colaborador.pessoal.deficienciaDescricao}</div>
	<div class='grade_field'><strong>Vínculo:</strong> ${colaborador.vinculoDescricao}</div>
	
	<#assign i = 0/>
	<#list configuracaoCampoExtras as configuracaoCampoExtra>
	<div class='grade_field'><strong>${configuracaoCampoExtras[i].titulo}: </strong>  
				
		<#if colaborador.camposExtras.texto1?exists && configuracaoCampoExtras[i].nome == "texto1" >
			${colaborador.camposExtras.texto1}<br>
		</#if>
		
		<#if colaborador.camposExtras.texto2?exists && configuracaoCampoExtras[i].nome == "texto2">
			${colaborador.camposExtras.texto2}<br>
		</#if>
		
		<#if colaborador.camposExtras.texto3?exists && configuracaoCampoExtras[i].nome == "texto3">
			${colaborador.camposExtras.texto3}<br>
		</#if>
		
		<#if colaborador.camposExtras.texto4?exists && configuracaoCampoExtras[i].nome == "texto4">
			${colaborador.camposExtras.texto4}<br>
		</#if>
		
		<#if colaborador.camposExtras.texto5?exists && configuracaoCampoExtras[i].nome == "texto5">
			${colaborador.camposExtras.texto5}<br>
		</#if>
		
		<#if colaborador.camposExtras.texto6?exists && configuracaoCampoExtras[i].nome == "texto6">
			${colaborador.camposExtras.texto6}<br>
		</#if>
		
		<#if colaborador.camposExtras.texto7?exists && configuracaoCampoExtras[i].nome == "texto7">
			${colaborador.camposExtras.texto7}<br>
		</#if>
		
		<#if colaborador.camposExtras.texto8?exists && configuracaoCampoExtras[i].nome == "texto8">
			${colaborador.camposExtras.texto8}<br>
		</#if>
		
		<#if colaborador.camposExtras.texto9?exists && configuracaoCampoExtras[i].nome == "texto9">
			${colaborador.camposExtras.texto9}<br>
		</#if>
		
		<#if colaborador.camposExtras.texto10?exists && configuracaoCampoExtras[i].nome == "texto10">
			${colaborador.camposExtras.texto10}<br>
		</#if>
		
		<#if colaborador.camposExtras.data1?exists && configuracaoCampoExtras[i].nome == "data1">
			${colaborador.camposExtras.data1}<br>
		</#if>
		
		<#if colaborador.camposExtras.data2?exists && configuracaoCampoExtras[i].nome == "data2">
			${colaborador.camposExtras.data2}<br>
		</#if>
		
		<#if colaborador.camposExtras.data3?exists && configuracaoCampoExtras[i].nome == "data3">
			${colaborador.camposExtras.data3}<br>
		</#if>
		
		<#if colaborador.camposExtras.valor1?exists && configuracaoCampoExtras[i].nome == "valor1">
			${colaborador.camposExtras.valor1}<br>
		</#if>
		
		<#if colaborador.camposExtras.valor2?exists && configuracaoCampoExtras[i].nome == "valor2">
			${colaborador.camposExtras.valor2}<br>
		</#if>
		
		<#if colaborador.camposExtras.numero1?exists && configuracaoCampoExtras[i].nome == "numero1">
			${colaborador.camposExtras.numero1}<br>
		</#if>
	</div>
	<#assign i = i + 1/>
	</#list>
	
	<br style='clear: both'>
</div>

<br>
<div class="painelConfiguracao">
	<a href="javascript:gravarConfiguracao(${usuarioId});" class="salvar"> Gravar layout das seções</a>
	<a href="javascript:jQuery('.cabecalhoBox').showBox()" class="exibir"> Expandir todas as seções</a>
	<a href="javascript:jQuery('.cabecalhoBox').hideBox()" class="ocultar"> Ocultar todas as seções</a>
</div>

<ul id="sortable">
	<li id="1" class="cabecalhoBox">
		<h4><a href="javascript:return false;"  class="linkBox">Histórico de Cursos de Formação Profissional</a></h4>
		<ul id="box1" class="box">
		<table class="grade">
			<tr>
				<td><strong>Formação</strong>
					<@display.table name="formacaos" id="formacao" class="dados" defaultsort=1 style="width:100%" >
						<@display.column property="curso" title="Curso" style="width:300px"/>
						<@display.column property="local" title="Instituição de ensino" style="width:200px"/>
						<@display.column property="tipoDescricao" title="Tipo" style="width:120px"/>
						<@display.column property="situacaoDescricao" title="Situação" style="width:80px"/>
						<@display.column property="conclusao" title="Conclusão" style="width:80px"/>
					</@display.table>
				</td>
			</tr>
			<tr>
				<td><strong>Idiomas</strong>
					<@display.table name="idiomasColaborador" id="idiomasColaborador" class="dados" defaultsort=1 style="width:100%" >
						<@display.column property="idioma.nome" title="Idioma" style="width:540px"/>
						<@display.column property="nivelDescricao" title="Nível" style="width:160px"/>
					</@display.table>
				</td>
			</tr>
		</table>
		</ul>
	</li>
	
	<li id="2" class="cabecalhoBox">
		<h4><a href="javascript:return false;"  class="linkBox">Avaliação de Desempenho</a></h4>
		<ul id="box2" class="box">
		<table class="grade">
			<tr>
				<td>
				<@display.table name="avaliacaoDesempenhos" id="avaliacaoDesempenho" class="dados">
						<@display.column property="dataMaisTempoPeriodoExperiencia" title="Data" style="width: 140px;"/>
						<@display.column property="avaliacao.titulo" title="Avaliação" />					
						<@display.column property="performanceFormatada" title="Performance" />
						<@display.column title="Obs." style="text-align: center;width: 50px">
							<#if avaliacaoDesempenhos.observacao?exists && avaliacaoDesempenhos.observacao?trim != "">
								<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${avaliacaoDesempenhos.observacao?j_string}');return false">...</span>
							</#if>
						</@display.column>
				</@display.table>
				</td>
			</tr>
		</table>
		</ul>
	</li>
	
	<li id="3" class="cabecalhoBox">
		<h4><a href="javascript:return false;"  class="linkBox">Avaliações do Período de Experiência</a></h4>
		<ul id="box3" class="box">
		<table class="grade">
			<tr>
				<td>
				<@display.table name="avaliacaoExperiencias" id="avaliacaoExperiencia" class="dados">
						<@display.column property="dataMaisTempoPeriodoExperiencia" title="Data" style="width: 140px;"/>
						<@display.column property="avaliacao.titulo" title="Avaliação" />
						<@display.column property="performanceFormatada" title="Performance" />
						<@display.column title="Obs." style="text-align: center;width: 50px">
							<#if avaliacaoExperiencia.observacao?exists && avaliacaoExperiencia.observacao?trim != "">
								<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${avaliacaoExperiencia.observacao?j_string}');return false">...</span>
							</#if>
						</@display.column>
				</@display.table>
				</td>
			</tr>
		</table>
		</ul>
	</li>
	
	<li  id="4" class="cabecalhoBox">
		<h4><a href="javascript:return false;"  class="linkBox">Participação de Eventos Educativos na Empresa</a></h4>
		<ul id="box4" class="box">
		<table class="grade">
			<tr>
				<td>
					<@display.table name="cursosColaborador" id="cursoColaborador" class="dados" style="width:100%">
						<@display.column property="curso.nome" title="Curso" />
						<@display.column title="Período" style="width:140px">
							<#if cursoColaborador.turma.dataPrevIni?exists && cursoColaborador.turma.dataPrevFim?exists>
								${cursoColaborador.turma.dataPrevIni} a ${cursoColaborador.turma.dataPrevFim}
							</#if>
						</@display.column>
						<@display.column property="turma.instrutor" title="Instrutor" />
						<@display.column property="aprovadoFormatado" title="Aprovado" style="width:70px;" />
						<@display.column property="valorAvaliacao" title="Nota" style="text-align:right;width:50px;" />
					</@display.table>
				</td>
			</tr>
		</table>
		</ul>
	</li>
	
	<li id="5" class="cabecalhoBox">
		<h4><a href="javascript:return false;"  class="linkBox">Trajetória Profissional na Empresa</a></h4>
		<ul id="box5" class="box">
		<table class="grade">
			<tr>
				<td>
					<@display.table name="historicoColaboradors" id="historicoColaboradors" class="dados" style="width:100%" >
						<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width:50px;"/>
						
						<@authz.authorize ifAllGranted="ROLE_COMPROU_SESMT">
							<@display.column property="funcao.nome" title="Função" />
						</@authz.authorize>
						
						<@display.column title="Cargo" >
						  ${historicoColaboradors.faixaSalarial.cargo.nome} ${historicoColaboradors.faixaSalarial.nome}
						</@display.column>
						<@display.column property="estabelecimento.nome" title="Estabelecimento" />
						<@display.column property="areaOrganizacional.descricao" title="Área Organizacional" />
						<@display.column property="descricaoTipoSalario" title="Tipo Salário" style="width:70px;"/>
						<@display.column property="salarioCalculado" title="Salário" format="{0,number,currency}" style="width:80px"/>
					</@display.table>
				</td>
			</tr>
		</table>
		</ul>
	</li>
	
	<li id="6" class="cabecalhoBox">
		<h4><a href="javascript:return false;"  class="linkBox">Ocorrências</a></h4>
		<ul id="box6" class="box">
		<table class="grade">
			<tr>
				<td>
					<@display.table name="ocorrenciasColaborador" id="ocorrencia" class="dados" >
						<@display.column property="ocorrencia.descricao" title="Ocorrência" style="width:240px"/>
						<@display.column title="Período" style="width:140px">
							<#if ocorrencia.dataIni?exists && ocorrencia.dataFim?exists>
								${ocorrencia.dataIni} a ${ocorrencia.dataFim}
							</#if>
						</@display.column>
						<@display.column property="observacao" title="Observação"/>
					</@display.table>
				</td>
			</tr>
		</table>
		</ul>
	</li>
	
	<li  id="7" class="cabecalhoBox">
		<h4><a href="javascript:return false;"  class="linkBox">Afastamentos</a></h4>
		<ul  id="box7" class="box">
		<table class="grade">
			<tr>
				<td>
					<@display.table name="afastamentosColaborador" id="afastamento" class="dados" >
						<@display.column property="afastamento.descricao" title="Motivo" style="width:240px"/>
						<@display.column title="Período" style="width:140px">
							<#if dataIni?exists && dataFim?exists>
								${afastamento.inicio} a ${afastamento.fim}
							</#if>
						</@display.column>
						<@display.column property="medicoNome" title="Médico"/>
						<@display.column property="medicoCrm" title="CRM"/>
						<@display.column property="observacao" title="Observação"/>
					</@display.table>
				</td>
			</tr>
		</table>
		</ul>
	</li>
	
	<li id="8" class="cabecalhoBox">	
		<h4><a href="javascript:return false;"  class="linkBox">Etapas Seletivas</a></h4>
		<ul id="box8" class="box">
		<table class="grade">
			<tr>
				<td>
					<@display.table name="historicosCandidatoByColaborador" id="historicoCandidato" class="dados" >
						<@display.column property="candidatoSolicitacao.solicitacao.descricao" title="Descrição" style="width:100px;"/>
						<@display.column title="Etapa">
							<#if historicoCandidato?exists && historicoCandidato.etapaSeletiva?exists>
								${historicoCandidato.etapaSeletiva.nome}
							</#if>
						</@display.column>
						<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width:50px;"/>
						<@display.column property="responsavel" title="Responsável"/>
						<@display.column property="aptoFormatado" title="Apto" style="width:50px;"/>
						<@display.column property="observacao" title="Observação"/>
					</@display.table>
				</td>
			</tr>
		</table>
		</ul>
	</li>
	
	<li id="9" class="cabecalhoBox">
		<h4><a href="javascript:return false;"  class="linkBox">Participações na CIPA</a></h4>
		<ul id="box9" class="box">
		<table class="grade">
			<tr>
				<td>
					<@display.table name="participacoesNaCipaColaborador" id="participacaoCipa" class="dados" >
						<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width:50px;"/>
						<@display.column property="descricao" title="Descrição"/>
						<@display.column property="funcao" title="Função"/>
					</@display.table>
				</td>
			</tr>
		</table>
		</ul>
	</li>
	
	<li id="10" class="cabecalhoBox">
		<h4><a href="javascript:return false;"  class="linkBox">Acidentes de Trabalho</a></h4>
		<ul id="box10" class="box">
		<table class="grade">
			<tr>
				<td>
					<@display.table name="catsColaborador" id="cat" class="dados" >
						<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width:50px;"/>
						<@display.column property="observacao" title="Descrição"/>
						<@display.column property="numeroCat" title="Número" style="width:80px;"/>
						<@display.column property="gerouAfastamentoFormatado" title="Gerou afastamento" style="width:120px;"/>
					</@display.table>
				</td>
			</tr>
		</table>
		</ul>
	</li>
	
	<li id="11" class="cabecalhoBox">
		<h4><a href="javascript:return false;" class="linkBox">Documentos</a></h4>
		<ul id="box11" class="box">
		<table class="grade">
			<tr>
				<td><strong>Documentos do Colaborador</strong><br>
					<@display.table name="documentoAnexosColaborador" id="documentoAnexo" class="dados" defaultsort=1 style="width:100%" >
						<@display.column title="Descrição" style="width:540px">
							<a href="../../geral/documentoAnexo/showDocumento.action?documentoAnexo.id=${documentoAnexo.id}" title="Visualizar documento" target="_blank">${documentoAnexo.descricao}</a>
						</@display.column>
						<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width:100px;"/>
						<@display.column title="Obs." style="text-align: center;width: 50px">
							<#if documentoAnexo.observacao?exists && documentoAnexo.observacao?trim != "">
								<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${documentoAnexo.observacao?j_string}');return false">...</span>
							</#if>
						</@display.column>
		
					</@display.table>
				</td>
			</tr>
			<tr>
				<td><strong>Documentos do Candidato</strong></br>
					<@display.table name="documentoAnexosCandidato" id="documentoAnexo" class="dados" defaultsort=1 style="width:100%" >
						<@display.column title="Descrição" style="width:540px">
							<a href="../../geral/documentoAnexo/showDocumento.action?documentoAnexo.id=${documentoAnexo.id}" title="Visualizar documento" target="_blank">${documentoAnexo.descricao}</a>
						</@display.column>
						<@display.column title="Fase" property="etapaSeletiva.nome" style="width:540px"  />
						<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width:100px;"/>
						<@display.column title="Obs." style="text-align: center;width: 50px">
							<#if documentoAnexo.observacao?exists && documentoAnexo.observacao?trim != "">
								<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${documentoAnexo.observacao?j_string}');return false">...</span>
							</#if>
						</@display.column>
					</@display.table>
				</td>
			</tr>
		</table>
		</ul>
	</li>
	
	<li id="12" class="cabecalhoBox">
		<#if colaborador.desligado>
			<h4><a href="javascript:return false;"  class="linkBox">Desligamento</a></h4>
			<ul id="box12" class="box">
			<table class="grade">
				<tr>
					<td style="width:540px">
						<strong>Motivo:</strong> <#if colaborador.motivoDemissao?exists && colaborador.motivoDemissao.motivo?exists>${colaborador.motivoDemissao.motivo}</#if>
					</td>
					<td style="width:160px">
						<strong>Data:</strong> ${colaborador.dataDesligamento}
					</td>
				</tr>
				<tr>
					<td>
						<strong>Observações:</strong><br>
						${colaborador.observacao}
					</td>
				</tr>
			</table>
			</ul>
		</#if>
	</li>
	
	<li id="13" class="cabecalhoBox">
		<#if colaborador.observacao?exists && colaborador.observacao!=''>
			<h4><a href="javascript:return false;" class="linkBox">Informações Adicionais</a></h4>
			<ul id="box13" class="box">
				<table class="grade">
					<tr>
						<td>
							${colaborador.observacao}
						</td>
					</tr>
				</table>
			</ul>
		</#if>
	</li>
</ul>
	<div class="buttonGroup">
		<button onclick="window.location='../../geral/colaborador/list.action'" class="btnVoltar" accesskey="V"></button>
		<button class="btnImprimirPdf" onclick="window.location='imprimirPerformanceFuncional.action?colaborador.id=${colaborador.id}'" id="btnImprimePerformance"></button>
	</div>
	
	
</body>
</html>