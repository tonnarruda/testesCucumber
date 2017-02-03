<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Currículo Digitado</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/PessoaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CandidatoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/BairroDWR.js?version=${versao}"/>'></script>

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/candidato.js?version=${versao}"/>"></script>

	<#include "../ftl/mascarasImports.ftl" />
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>

	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/candidato.css?version=${versao}"/>" media="screen" type="text/css">
	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/botoes.css?version=${versao}"/>" media="screen" type="text/css">
	<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');</style>
	<#assign empresaId><@authz.authentication operation="empresaId"/></#assign>
	
	<script type="text/javascript">
		function validaForm()
		{
			<#if maxCandidataCargo?exists && 0 < maxCandidataCargo>
				if(qtdeChecksSelected(document.getElementsByName('form')[0],'cargosCheck') > ${maxCandidataCargo})
				{
					jAlert("Não é permitido selecionar mais de ${maxCandidataCargo} cargos (Cargo / Função Pretendida)");
					return false;
				}
			</#if>

			validaFormulario('form', new Array('nome', 'sexo', '@cargosCheck', 'ocrTexto'), new Array('nascimento','cpf'));
		}

		function populaCidades()
		{
			dwr.util.useLoadingMessage('Carregando...');
			CidadeDWR.getCidades(document.getElementById("uf").value, createListCidades);
		}

		function createListCidades(data)
		{
			dwr.util.removeAllOptions("cidade");
			dwr.util.addOptions("cidade", data);
		}

		function getCandidatosHomonimos()
		{
			if(document.getElementById("nome").value.length >= 3)
			{
				dwr.util.useLoadingMessage('Carregando...');
				CandidatoDWR.getCandidatosHomonimos(document.getElementById("nome").value, createListCandidatosHomonimos);
			}
			else
				document.getElementById("homonimos").style.display = "none";
		}

		function createListCandidatosHomonimos(data)
		{
			var nomes = "";
			for (var prop in data)
			{
				var url = "<@ww.url value='/captacao'/>";
				nomes += " - <a title=\"Ver Informação\" style=\"color:red;text-decoration: none\" href=\"javascript:popup('"+url+"/candidato/infoCandidato.action?palavras=&forma=&candidato.id=" + prop + "', 580, 750)\">" + data[prop] + "</a><br>";
			}

			if(nomes != "")
			{
				document.getElementById("homonimos").style.display = "";
				document.getElementById("nomesHomonimos").innerHTML = nomes;
			}
			else
				document.getElementById("homonimos").style.display = "none";
		}

		var bairros = [${bairros}];
		$(function() {
			$("#bairroNome").autocomplete(bairros);
		});
	</script>
	<#assign validarCampos="return validaForm()"/>
	</head>
	<body>
		<@ww.actionmessage />
		<@ww.actionerror />
		<@ww.form name="form" action="insertCurriculoTexto.action" onsubmit="${validarCampos}" validate="true" method="POST" enctype="multipart/form-data">
			<@ww.textfield label="Nome" id="nome" name="candidato.nome" required="true" cssStyle="width:396px;" liClass="liLeft" onblur="getCandidatosHomonimos();"/>
			<@ww.select label="Sexo" id="sexo" required="true" name="candidato.pessoal.sexo" list="sexos" cssStyle="width: 100px;" />
			
			<@ww.div id="homonimos" cssStyle="color:blue;display:none; ">
				Existe(m) candidato(s) homônimo(s):
				<@ww.div id="nomesHomonimos" cssStyle="color:red;">	</@ww.div>
			</@ww.div>
			
			<@frt.checkListBox label="Cargo/Função Pretendida*" name="cargosCheck" list="cargosCheckList" filtro="true"/>
	
			
			<@ww.datepicker label="Nascimento" name="candidato.pessoal.dataNascimento" id="nascimento"  liClass="liLeft" cssClass="mascaraData"/>
			<@ww.textfield label="CPF"  name="candidato.pessoal.cpf" id="cpf" liClass="liLeft" cssClass="mascaraCpf" onchange="verificaCpfDuplicado(this.value,${empresaId},false);" onblur="verificaCpfDuplicado(this.value,${empresaId},false);"/>
			<@ww.div id="msgCPFDuplicado" cssStyle="display:none; "></@ww.div>
			<@ww.select label="Colocação" name="candidato.colocacao" list="colocacaoList" liClass="liLeft" />
			<@ww.textfield label="Indicado Por:" name="candidato.pessoal.indicadoPor" id="indicadoPor" cssStyle="width: 195px;"  maxLength="100"/>
			
			<@ww.select label="Estado" name="candidato.endereco.uf.id" id="uf" list="ufs"  liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue="" onchange="javascript:populaCidades()"/>
			<@ww.select label="Cidade" name="candidato.endereco.cidade.id" id="cidade"  list="cidades" listKey="id" listValue="nome" cssStyle="width: 245px;" headerKey="" headerValue="" liClass="liLeft"/>
			<@ww.textfield label="Bairro" name="candidato.endereco.bairro" id="bairroNome" cssStyle="width: 197px;"  maxLength="85"/>
	
			<@ww.textarea label="Currículo" name="candidato.ocrTexto" id="ocrTexto" required="true" cssStyle="width: 758px;height: 300px"/>
		</@ww.form>
	
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnCancelar"></button>
		</div>
	</body>
</html>