<html>
<head>
<@ww.head/>


  <style type="text/css">
    #menuEleicao a.ativaComunicado{border-bottom: 2px solid #5292C0;}
    #eleicao { height: 20px; }
  </style>

  <title></title>

	<script type='text/javascript'>
	
		function validaForm(imprima)
		{
			$('#imprimaRelatorio').val(imprima);
			return validaFormularioEPeriodo('form', new Array(), new Array('inscricaoCandidatoIni','inscricaoCandidatoFim','votacaoIni','votacaoFim','apuracao'));
		}
	</script>
	
</head>
<body>
	<#include "../ftl/mascarasImports.ftl" />
  	<#include "eleicaoLinks.ftl" />
  	
  	<#if eleicao?exists && eleicao.inscricaoCandidatoIni?exists>
      <#assign dataInscInicio = eleicao.inscricaoCandidatoIni?date/>
    <#else>
      <#assign dataInscInicio = ""/>
    </#if>
    <#if eleicao?exists && eleicao.inscricaoCandidatoFim?exists>
      <#assign dataInscFim = eleicao.inscricaoCandidatoFim?date/>
    <#else>
      <#assign dataInscFim = ""/>
    </#if>
   	<#if eleicao?exists && eleicao.apuracao?exists>
		<#assign dataApuracao = eleicao.apuracao?date/>
	<#else>
		<#assign dataApuracao = ""/>
	</#if>
    <#if eleicao?exists && eleicao.votacaoIni?exists>
		<#assign dataVotacaoIni = eleicao.votacaoIni?date/>
	<#else>
		<#assign dataVotacaoIni = ""/>
	</#if>
    <#if eleicao?exists && eleicao.votacaoFim?exists>
		<#assign dataVotacaoFim = eleicao.votacaoFim?date/>
	<#else>
		<#assign dataVotacaoFim = ""/>
	</#if>
		
	<div id = "eleicao">
		<h3><b>Eleição: ${eleicao.descricao}</b></h3>
	</div>
	
	<@ww.form name="form" action="updateImprimir.action" validate="true" method="POST">
		Período de Inscrição dos Candidatos:
		<br/>
	    <@ww.datepicker label="Início" name="eleicao.inscricaoCandidatoIni" id="inscricaoCandidatoIni" value="${dataInscInicio}"  cssClass="mascaraData validaDataIni" liClass="liLeft"/>
	    <@ww.datepicker label="Fim" name="eleicao.inscricaoCandidatoFim" id="inscricaoCandidatoFim" value="${dataInscFim}"  cssClass="mascaraData validaDataFim"/>

	    <@ww.textfield label="Local de inscrição" name="eleicao.localInscricao" id="localInscricao"  cssClass="inputNome" maxLength="100" />
		<@ww.textarea label="Texto para edital de inscrição" name="eleicao.textoEditalInscricao" cssStyle="width:600px;"/>

	<div class="buttonGroup">
		<button onclick="return validaForm('localInscricao');" class="btnImprimirEdital"></button>
	</div><br>


		Período da Votação:*<br>
		<@ww.datepicker label="" name="eleicao.votacaoIni" id="votacaoIni" theme="simple" cssClass="mascaraData" value="${dataVotacaoIni}" />
		a
		<@ww.datepicker label="" name="eleicao.votacaoFim" id="votacaoFim" theme="simple" cssClass="mascaraData" value="${dataVotacaoFim}" />

		<br>Horário da Votação:*<br>
		<@ww.textfield label="" name="eleicao.horarioVotacaoIni" id="horarioVotacaoIni" theme="simple" cssStyle="width: 38px;" maxLength="5" onkeypress="return(somenteNumeros(event,':'));"/>
		 a
		<@ww.textfield label="" name="eleicao.horarioVotacaoFim" id="horarioVotacaoFim" theme="simple" cssStyle="width: 38px;" maxLength="5" onkeypress="return(somenteNumeros(event,':'));"/>

	    <@ww.textfield label="Local da Votação" name="eleicao.localVotacao" id="localVotacao"  cssClass="inputNome" maxLength="100" />

		<@ww.textarea label="Texto para chamado da eleição" name="eleicao.textoChamadoEleicao" cssStyle="width:600px;"/>
	<div class="buttonGroup">
		<button onclick="return validaForm('localVotacao');" class="btnImprimirChamadoEleicao"></button>
	</div><br>

		<@ww.textfield label="Sindicato" name="eleicao.sindicato" id="sindicato" />
		<@ww.datepicker label="Apuração dos votos" name="eleicao.apuracao" id="apuracao" cssClass="mascaraData" value="${dataApuracao}" />

		<@ww.textfield label="Horário da Apuração" name="eleicao.horarioApuracao" id="horarioApuracao"   cssStyle="width: 38px;" maxLength="5" onkeypress="return(somenteNumeros(event,':'));"/>
		<@ww.textfield label="Local da Apuração" name="eleicao.localApuracao" id="localApuracao"  cssClass="inputNome" maxLength="100" />

		<@ww.textarea label="Texto para comunicado ao sindicato (Utilize as tags: #EMPRESA# #DATA_VOTACAOINI# #DATA_VOTACAOFIM#, caso queira exibir o nome da empresa ou data da votação)" name="eleicao.textoSindicato" cssStyle="width:600px;"/>
	<div class="buttonGroup">
		<button onclick="return validaForm('chamadoEleicao');" class="btnImprimirSindicato"></button>
	</div><br>
	
		<@ww.textarea label="Texto para Ata da Eleição" name="eleicao.textoAtaEleicao" cssStyle="width:600px;"/>
	
	<div class="buttonGroup">
		<button onclick="return validaForm('ataEleicao');" class="btnImprimirAtaEleicao"></button>
	</div>

	    <@ww.hidden name="eleicao.id"/>
	    <@ww.hidden name="eleicao.empresa.id"/>
	    <@ww.hidden name="eleicao.posse"/>
	    <@ww.hidden name="eleicao.qtdVotoNulo"/>
	    <@ww.hidden name="eleicao.qtdVotoBranco"/>
		<@ww.hidden name="eleicao.descricao"/>
		<@ww.hidden name="eleicao.estabelecimento.id"/>
		<@ww.hidden name="imprimaRelatorio" id="imprimaRelatorio"/>
  	
		<@ww.textarea label="Texto para DRT(Utilize as tags: #EMPRESA# #ESTABELECIMENTO# #ENDERECO# #TOTAL_EMPREGADOS# #ATIVIDADE# #RISCO#)" name="eleicao.textoDRT" cssStyle="width:600px;"/>
		<div class="buttonGroup">
			<button onclick="return validaForm('ataDRT');" class="btnImprimirAtaDRT"></button>
		</div>
	</@ww.form>
  	

</body>
</html>