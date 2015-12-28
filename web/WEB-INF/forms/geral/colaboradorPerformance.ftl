<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
<#assign urlImg><@ww.url includeParams="none" value="/imgs"/></#assign>

<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	.grade
	{
		border:1px solid #BEBEBE;
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
		border: 1px solid #BEBEBE;
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
		border: 1px solid #BEBEBE;
		background: #EBECF1;
		width: 100%;		
		height: 23px;
	}

	.painelConfiguracao a:hover {
		text-decoration: none;
		color: #000;
	}
	
	.grade_field a {
		font-family: verdana,sans-seriff;
		font-size: 13px !important;
	}
</style>

<title>Performance Profissional</title>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign usuarioId><@authz.authentication operation="id"/></#assign>
	<#include "../ftl/showFilterImports.ftl" />

	<script type="text/javascript" src='<@ww.url includeParams="none" value="/dwr/interface/ConfiguracaoPerformanceDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/configuracaoPerformance.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>
	<script type="text/javascript">
		$(function() {
			$('.tooltipHelp').qtip({
				content: 'Não foi possível obter o salário. Verifique se o índice/faixa possui histórico nesta data.'
			});
		});
		
		var configPerformanceBoxes = ${configPerformanceBoxes};		
	</script>
</head>
<body>
<@ww.actionmessage/>
<#if colaboradoresMesmoCpf?exists && 1 < colaboradoresMesmoCpf?size>
	<br>
	<span id="exibir">
		<strong>
			Registros de colaboradores com mesmo CPF em outras empresas
		</strong>
	</span>
	<div class='grade'>
		<div style="padding:10px">
			<ul>
				<#list colaboradoresMesmoCpf as colab>
					<#if colab.id != colaborador.id>
						<li>
							<a href="preparePerformanceFuncional.action?colaborador.id=${colab.id}">
								${colab.nome} na empresa ${colab.empresa.nome}
							</a>
						</li>
					</#if>
				</#list>
			</ul>
		</div>
	</div>
</#if>
<br>

<span id="exibir">
	<strong>
		Dados Pessoais
		<#if colaborador.empresa.nome?exists>
			(Empresa ${colaborador.empresa.nome})
		</#if>
	</strong>
</span>
<div class='grade' style="margin-bottom: 12px;">
	<#if colaborador.nome?exists>
		<div class='grade_field'><strong>Nome:</strong> ${colaborador.nome}</div>
	</#if>
		
	<#if colaborador.foto?exists>
		<div class='grade_field_foto'>
			<img src="<@ww.url includeParams="none" value="showFoto.action?colaborador.id=${colaborador.id}"/>" style="display:inline" id="fotoImg" width="120px" height="120px">
		</div>
	</#if>
			
	<div class='grade_field'>
		<strong>Email:</strong>
		<#if colaborador.contato?exists && colaborador.contato.email?exists>
			<a title="enviar email" href="mailto:${colaborador.contato.email}">${colaborador.contato.email}</a>
		</#if>
	</div>
	
	<#if colaborador.pessoal?exists && colaborador.pessoal.cpf?exists>
		<div class='grade_field'><strong>CPF:</strong> ${colaborador.pessoal.cpfFormatado}</div>
	</#if>
	<#if colaborador.pessoal?exists && colaborador.pessoal.getEstadoCivilDic()?exists>
		<div class='grade_field'><strong>Estado Civil:</strong> ${colaborador.pessoal.getEstadoCivilDic()}</div>
	</#if>
	<#if colaborador.pessoal?exists && colaborador.pessoal.getEscolaridadeDic()?exists>
		<div class='grade_field'><strong>Escolaridade:</strong> ${colaborador.pessoal.getEscolaridadeDic()}</div>
	</#if>
	<#if colaborador.endereco?exists && colaborador.endereco.bairro?exists>
		<div class='grade_field'><strong>Bairro:</strong> ${colaborador.endereco.bairro}</div>
	</#if>
	<#if colaborador.endereco?exists && colaborador.endereco.enderecoFormatado?exists>
		<div class='grade_field'><strong>Endereço:</strong> ${colaborador.endereco.enderecoFormatado}</div>
	</#if>
	<#if colaborador.endereco?exists && colaborador.endereco.cidade?exists && colaborador.endereco.cidade.nome?exists>
		<div class='grade_field'><strong>Cidade/Estado:</strong> ${colaborador.endereco.cidade.nome} / ${colaborador.endereco.uf.sigla}</div>
	</#if>
	<#if colaborador.endereco?exists && colaborador.endereco.cepFormatado?exists>
		<div class='grade_field'><strong>CEP:</strong> ${colaborador.endereco.cepFormatado}</div>
	</#if>
	<#if colaborador.contato?exists && colaborador.contato.foneFixo?exists && colaborador.contato.ddd?exists>
		<div class='grade_field'><strong>Telefone:</strong> (${colaborador.contato.ddd}) ${colaborador.contato.foneFixoFormatado}</div>
	</#if>
	<#if colaborador.contato?exists && colaborador.contato.foneCelular?exists && colaborador.contato.ddd?exists>
		<div class='grade_field'><strong>Celular:</strong> (${colaborador.contato.ddd}) ${colaborador.contato.foneCelularFormatado}</div>
	</#if>
	<#if colaborador.pessoal?exists && colaborador.pessoal.conjuge?exists && colaborador.pessoal.conjuge != "">
		<div class='grade_field'><strong>Cônjuge:</strong> ${colaborador.pessoal.conjuge}</div>
		<div class='grade_field'><strong>Cônjuge Trabalha:</strong> <#if colaborador.pessoal.conjugeTrabalha>Sim<#else>Não</#if></div>
	</#if>
	<#if colaborador.pessoal?exists && colaborador.pessoal.deficienciaDescricao?exists>
		<div class='grade_field'><strong>Deficiência:</strong> ${colaborador.pessoal.deficienciaDescricao}</div>
	</#if>
	<br style='clear: both'>
</div>

<span id="exibir">
	<strong>
		Dados Funcionais
	</strong>
</span>
<div class='grade' style="margin-bottom: 12px;">
	<#if colaborador.dataAdmissao?exists>
		<div class='grade_field'><strong>Admissão:</strong> ${colaborador.dataAdmissao}</div>
	</#if>
	<div class='grade_field'><strong>Cargo Atual:</strong> <#if historicoColaborador?exists && historicoColaborador.faixaSalarial?exists && historicoColaborador.faixaSalarial.cargo?exists && historicoColaborador.faixaSalarial.cargo.nome?exists>${historicoColaborador.faixaSalarial.cargo.nome}</#if></div>
	<#if colaborador.vinculoDescricao?exists>
		<div class='grade_field'><strong>Vínculo:</strong> ${colaborador.vinculoDescricao}</div>
	</#if>
	<br style='clear: both'>
</div>


<span id="exibir">
	<strong>
		Documentos
	</strong>
</span>
<div class='grade' style="margin-bottom: 12px;">
	<div style="margin-bottom: 12px;">
		<#if colaborador.pessoal?exists && colaborador.pessoal.rg?exists>
			<div style="margin:2px 0 0 5px;"><strong>Identidade</strong></div> 
			<#if colaborador.pessoal?exists && colaborador.pessoal.rg?exists>
				<div class='grade_field'><strong>Número:</strong> ${colaborador.pessoal.rg}</div>
			</#if>
			<#if colaborador.pessoal?exists && colaborador.pessoal.rgOrgaoEmissor?exists>
				<div class='grade_field'><strong>Orgão Emissor:</strong> ${colaborador.pessoal.rgOrgaoEmissor}</div>
			</#if>
			<#if colaborador.pessoal?exists && colaborador.pessoal.rgUf.id?exists>
				<div class='grade_field'><strong>Estado:</strong> ${colaborador.pessoal.rgUf.sigla}</div>
			</#if>	 
		</#if>
		<br style='clear: both'>
	</div>
	
	<div>
		<#if colaborador.pessoal?exists && colaborador.pessoal.tituloEleitoral.titEleitNumero?exists>
			<div style="margin-left: 5px;"><strong>Título Eleitoral</strong></div>
			<#if colaborador.pessoal?exists && colaborador.pessoal.tituloEleitoral?exists && colaborador.pessoal.tituloEleitoral.titEleitNumero?exists>
				<div class='grade_field'><strong>Número:</strong> ${colaborador.pessoal.tituloEleitoral.titEleitNumero}</div>
			</#if>
			
			<#if colaborador.pessoal?exists && colaborador.pessoal.tituloEleitoral?exists && colaborador.pessoal.tituloEleitoral.titEleitZona?exists>
				<div class='grade_field'><strong>Zona:</strong> ${colaborador.pessoal.tituloEleitoral.titEleitZona}</div>
			</#if>
			<#if colaborador.pessoal?exists && colaborador.pessoal.tituloEleitoral?exists && colaborador.pessoal.tituloEleitoral.titEleitSecao?exists>
				<div class='grade_field'><strong>Seção:</strong> ${colaborador.pessoal.tituloEleitoral.titEleitSecao}</div>
			</#if>
		</#if>
	</div>
<br style='clear: both'>
</div>	

<#if configuracaoCampoExtras?exists && (configuracaoCampoExtras.size() > 0) >
	<span id="exibir">
		<strong>
			Extra
		</strong>
	</span>
	<div class='grade' style="margin-bottom: 12px;">
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
					
					<#if colaborador.camposExtras.textolongo1?exists && configuracaoCampoExtras[i].nome == "textolongo1">
						${colaborador.camposExtras.textolongo1}<br>
					</#if>
					
					<#if colaborador.camposExtras.textolongo2?exists && configuracaoCampoExtras[i].nome == "textolongo2">
						${colaborador.camposExtras.textolongo2}<br>
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
						${colaborador.camposExtras.valor1?string('###,###,###.00')}
					</#if>
					
					<#if colaborador.camposExtras.valor2?exists && configuracaoCampoExtras[i].nome == "valor2">
						${colaborador.camposExtras.valor2?string('###,###,###.00')}<br>
					</#if>
					
					<#if colaborador.camposExtras.numero1?exists && configuracaoCampoExtras[i].nome == "numero1">
						${colaborador.camposExtras.numero1}<br>
					</#if>
				</div>
			<#assign i = i + 1/>
		</#list>
		<br style='clear: both'>
	</div>	
</#if>

<br>
<div class="painelConfiguracao">
	<a href="javascript:gravarConfiguracao(${usuarioId});" class="salvar"> Gravar layout das seções</a>
	<a href="javascript:$('.cabecalhoBox').showBox()" class="exibir"> Expandir todas as seções</a>
	<a href="javascript:$('.cabecalhoBox').hideBox()" class="ocultar"> Ocultar todas as seções</a>
</div>

<ul id="sortable">
	<li id="1" class="cabecalhoBox">
		<h4><a href="javascript:;"  class="linkBox">Histórico de Cursos de Formação Profissional</a></h4>
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
		<h4><a href="javascript:;"  class="linkBox">Avaliação de Desempenho</a></h4>
		<ul id="box2" class="box">
		<table class="grade">
			<tr>
				<td>
				<@display.table name="avaliacaoDesempenhos" id="avaliacaoDesempenho" class="dados">
						<@display.column property="dataMaisTempoPeriodoExperiencia" title="Data" style="width: 140px;"/>
						<#if avaliacaoDesempenho.avaliacaoDesempenho.exibirPerformanceProfissional>
							<@display.column title="Avaliação" >					
								<a href= "javascript:popup('../../pesquisa/colaboradorQuestionario/visualizarRespostasAvaliacaoDesempenhoEPeriodoExperiencia.action?colaboradorQuestionario.id=${avaliacaoDesempenho.id}', 600, 1100)">
										${avaliacaoDesempenho.avaliacaoDesempenho.titulo}
								</a>
							</@display.column>
						<#else>
							<@display.column title="Avaliação" >					
								<#if avaliacaoDesempenho.titulo?exists>
									${avaliacaoDesempenho.titulo}
								</#if>
							</@display.column>
						</#if>
						<@display.column property="performanceFormatada" title="Performance  Questionário" style="width:90px; text-align:right;"/>
						<@display.column property="performanceNivelCompetenciaFormatada" title="Performance Competência" style="width:90px; text-align:right;"/>
						<@display.column property="performanceFinal" title="Performance" style="width:90px; text-align:right;"/>
						<@display.column title="Obs." style="text-align: center;width: 50px">
							<#if avaliacaoDesempenho.observacao?exists && avaliacaoDesempenho.observacao?trim != "">
								<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${avaliacaoDesempenho.observacao?j_string?replace("'", "\\'")?replace("\"", "'")}');return false">...</span>
							</#if>
						</@display.column>
				</@display.table>
				</td>
			</tr>
		</table>
		</ul>
	</li>
	
	<li id="14" class="cabecalhoBox">
		<h4><a href="javascript:;"  class="linkBox">Pesquisas</a></h4>
		<ul id="box14" class="box">
		<table class="grade">
			<tr>
				<td>
				<@display.table name="pesquisas" id="pesquisa" class="dados">
					<@display.column property="questionario.periodoFormatado" title="Período" style="width: 90px;"/>
					<@display.column title="Título" style="width: 500px">
						<a href= "javascript:popup('../../pesquisa/colaboradorQuestionario/visualizarRespostasPorColaboradorPerformanceProfissional.action?questionario.id=${pesquisa.questionario.id}&colaboradorId=${colaborador.id}&ocultarBotaoVoltar=true', 600, 1000)">
							<#if pesquisa.questionario.titulo?exists>
								${pesquisa.questionario.titulo}
							</#if>
						</a>
					</@display.column>
				</@display.table>
				</td>
			</tr>
		</table>
		</ul>
	</li>
	
	<li id="3" class="cabecalhoBox">
		<h4><a href="javascript:;"  class="linkBox">Acompanhamento do Período de Experiência</a></h4>
		<ul id="box3" class="box">
		<table class="grade">
			<tr>
				<td>
				<@display.table name="avaliacaoExperiencias" id="avaliacaoExperiencia" class="dados">
						<@display.column property="dataMaisTempoPeriodoExperiencia" title="Data" style="width: 140px;"/>
						<@display.column title="Avaliação" >					
							<a href= "javascript:popup('../../pesquisa/colaboradorQuestionario/visualizarRespostasAvaliacaoDesempenhoEPeriodoExperiencia.action?colaboradorQuestionario.id=${avaliacaoExperiencia.id}', 600, 1100)">
								<#if avaliacaoExperiencia.avaliacao.titulo?exists>
									${avaliacaoExperiencia.avaliacao.titulo}
								</#if>
							</a>
						</@display.column>
						<@display.column property="performanceFormatada" title="Performance" />
						<@display.column title="Obs." style="text-align: center;width: 50px">
							<#if avaliacaoExperiencia.observacao?exists && avaliacaoExperiencia.observacao?trim != "">
								<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${avaliacaoExperiencia.observacao?j_string?replace("'", "\\'")?replace("\"", "'")}');return false">...</span>
							</#if>
						</@display.column>
				</@display.table>
				</td>
			</tr>
		</table>
		</ul>
	</li>
	
	<li  id="4" class="cabecalhoBox">
		<h4><a href="javascript:;"  class="linkBox">Participação em Cursos/Treinamentos</a></h4>
		<ul id="box4" class="box">
		<table class="grade">
			<tr>
				<td>
					<@display.table name="cursosColaborador" id="cursoColaborador" class="dados" style="width:100%">
						<@display.column property="curso.nome" title="Curso" />
						<@display.column title="Período" style="width:140px">
							<#if cursoColaborador.turma?exists && cursoColaborador.turma.dataPrevIni?exists && cursoColaborador.turma.dataPrevFim?exists>
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
		<h4><a href="javascript:;"  class="linkBox">Trajetória Profissional na Empresa</a></h4>
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
						  <#if historicoColaboradors.faixaSalarial?exists && historicoColaboradors.faixaSalarial.cargo?exists && historicoColaboradors.faixaSalarial?exists && historicoColaboradors.faixaSalarial.cargo.nome?exists && historicoColaboradors.faixaSalarial.nome?exists>
						  	${historicoColaboradors.faixaSalarial.cargo.nome} ${historicoColaboradors.faixaSalarial.nome}
						  </#if>
						</@display.column>
						<@display.column property="estabelecimento.nome" title="Estabelecimento" />
						<@display.column property="areaOrganizacional.descricao" title="Área Organizacional" />
						<@display.column property="descricaoTipoSalario" title="Tipo Salário" style="width:70px;"/>
						<@display.column title="Salário" format="{0,number,currency}" style="width:80px">
							<#if historicoColaboradors.salarioCalculado?exists>
								${historicoColaboradors.salarioCalculado?string.currency}
							<#else>
								<div style="width: 100%; text-align: center;">
									<img class="tooltipHelp" src="<@ww.url value="/imgs/iconWarning.gif"/>" />
								</div>
							</#if>
						</@display.column>
					</@display.table>
				</td>
			</tr>
		</table>
		</ul>
	</li>
	
	<li id="6" class="cabecalhoBox">
		<h4><a href="javascript:;"  class="linkBox">Ocorrências <#if pontuacao?exists && 0 < pontuacao>(${pontuacao} <#if pontuacao?exists && pontuacao == 1>ponto<#else>pontos</#if>)</#if></a></h4>
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
						<@display.column property="ocorrencia.pontuacao" title="Pontuação" style="width:75px;text-align:right;"/>
					</@display.table>
				</td>
			</tr>
		</table>
		</ul>
	</li>
	
	<li  id="7" class="cabecalhoBox">
		<h4><a href="javascript:;"  class="linkBox">Afastamentos</a></h4>
		<ul  id="box7" class="box">
		<table class="grade">
			<tr>
				<td>
					<@display.table name="afastamentosColaborador" id="afastamento" class="dados" >
						<@display.column property="afastamento.descricao" title="Motivo" style="width:240px"/>
						<@display.column title="Período" style="width:140px">
							<#if afastamento.inicio?exists>
								${afastamento.inicio}
							</#if>
							<#if afastamento.fim?exists>
								a ${afastamento.fim}
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
		<h4><a href="javascript:;"  class="linkBox">Etapas Seletivas</a></h4>
		<ul id="box8" class="box">
		<table class="grade">
			<tr>
				<td>
					<@display.table name="historicosCandidatoByColaborador" id="historicoCandidato" class="dados" >
						<@display.column title="Solicitação" style="width:150px;">
							${historicoCandidato.candidatoSolicitacao.solicitacao.id} - ${historicoCandidato.candidatoSolicitacao.solicitacao.descricao}
						</@display.column>
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
		<h4><a href="javascript:;"  class="linkBox">Participações na CIPA</a></h4>
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
		<h4><a href="javascript:;"  class="linkBox">Acidentes de Trabalho</a></h4>
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
		<h4><a href="javascript:;" class="linkBox">Documentos</a></h4>
		<ul id="box11" class="box">
		<table class="grade">
			<tr>
				<td><strong>Documentos do Colaborador</strong><br>
					<@display.table name="documentoAnexosColaborador" id="documentoAnexo" class="dados" defaultsort=1 style="width:100%" >
						<@display.column title="Descrição" style="width:340px">
							<#if documentoAnexo.descricao?exists>
								<a href="../../geral/documentoAnexo/showDocumento.action?documentoAnexo.id=${documentoAnexo.id}" title="Visualizar documento" target="_blank">${documentoAnexo.descricao}</a>
							</#if>
						</@display.column>
						<@display.column property="tipoDocumento.descricao" title="Tipo do Documento" style="width:150px;"/>
						<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width:70px; text-align: center;"/>
						<@display.column title="Obs." style="text-align: center;width: 50px">
							<#if documentoAnexo.observacao?exists && documentoAnexo.observacao?trim != "">
								<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${documentoAnexo.observacao?j_string?replace("'", "\\'")?replace("\"", "'")}');return false">...</span>
							</#if>
						</@display.column>
		
					</@display.table>
				</td>
			</tr>
			<tr>
				<td><strong>Documentos do Candidato</strong></br>
					<@display.table name="documentoAnexosCandidato" id="documentoAnexo" class="dados" defaultsort=1 style="width:100%" >
						<@display.column title="Descrição" style="width:540px">
							<#if documentoAnexo.descricao?exists>
								<a href="../../geral/documentoAnexo/showDocumento.action?documentoAnexo.id=${documentoAnexo.id}" title="Visualizar documento" target="_blank">${documentoAnexo.descricao}</a>
							</#if>
						</@display.column>
						<@display.column title="Fase" property="etapaSeletiva.nome" style="width:540px"  />
						<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width:100px;"/>
						<@display.column title="Obs." style="text-align: center;width: 50px">
							<#if documentoAnexo.observacao?exists && documentoAnexo.observacao?trim != "">
								<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${documentoAnexo.observacao?j_string?replace("'", "\\'")?replace("\"", "'")}');return false">...</span>
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
			<h4><a href="javascript:;"  class="linkBox">Desligamento</a></h4>
			<ul id="box12" class="box">
			<table class="grade">
				<tr>
					<td style="width:540px">
						<strong>Motivo:</strong> <#if colaborador.motivoDemissao?exists && colaborador.motivoDemissao.motivo?exists>${colaborador.motivoDemissao.motivo}</#if>
					</td>
					<td style="width:160px">
						<strong>Data:</strong> <#if colaborador.dataDesligamento?exists>${colaborador.dataDesligamento}</#if>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Observações:</strong><br>
						<#if colaborador?exists && colaborador.observacaoDemissao?exists>
							${colaborador.observacaoDemissao}
						</#if>
					</td>
				</tr>
			</table>
			</ul>
		</#if>
	</li>
	
	<li id="13" class="cabecalhoBox">
		<h4><a href="javascript:;" class="linkBox">Experiência Profissional</a></h4>
		<ul id="box13" class="box">
			<table class="grade">
				<tr>
					<td>
						<@display.table name="experiencias" id="exp" class="dados" defaultsort=4 defaultorder="descending">
							<@display.column property="empresa" title="Empresa"/>
							<@display.column property="nomeFuncao" title="Cargo/Função"/>
							<@display.column property="dataAdmissao" title="Admissão" format="{0,date,dd/MM/yyyy}" style="text-align: center"/>
							<@display.column property="dataDesligamento" title="Desligamento" format="{0,date,dd/MM/yyyy}" style="text-align: center"/>
						</@display.table>
					</td>
				</tr>
			</table>
		</ul>
	</li>
	
	<li id="14" class="cabecalhoBox">
		<#if colaborador.observacao?exists && colaborador.observacao!=''>
			<h4><a href="javascript:;" class="linkBox">Informações Adicionais</a></h4>
			<ul id="box14" class="box">
				<table class="grade">
					<tr>
						<td>
						<#if colaborador?exists && colaborador.observacao?exists>
							${colaborador.observacao}
						</#if>
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