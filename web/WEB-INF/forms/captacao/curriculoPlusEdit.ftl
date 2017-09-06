<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/PessoaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CandidatoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/BairroDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
			
	<#if candidato?exists && candidato.id?exists>
		<#assign avisoAnexo = "Caso deseje substituir os arquivos existentes, selecione os arquivos novamente.<br>"/>
		<#assign obrigacaoAnexo = ""/>
		<#assign validaAnexo = "'nome'"/>
		<#assign titulo = "Editar Currículo"/>
	<#else>
		<#assign avisoAnexo = ""/>
		<#assign obrigacaoAnexo = "*"/>
		<#assign validaAnexo = "'nome','ocrTexto'"/>
		<#assign titulo = "Escanear Currículo"/>
	</#if>
	
	<title>${titulo}</title>

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/candidato.js?version=${versao}"/>"></script>

	<#if candidato?exists && candidato.pessoal.dataNascimento?exists>
		<#assign data = candidato.pessoal.dataNascimento?date/>
	<#else>
		<#assign data = ""/>
	</#if>
	

	<#include "../ftl/mascarasImports.ftl" />
	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/candidato.css?version=${versao}"/>" media="screen" type="text/css">
	<link rel="stylesheet" href="<@ww.url includeParams="none" value="/css/botoes.css?version=${versao}"/>" media="screen" type="text/css">
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
	</style>
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

			validaFormulario('form', new Array('@cargosCheck',${validaAnexo}), new Array('nascimento','cpf'));
		}

		var numero = 2;
		function addFileInput() {
		 	var d = document.createElement("div");
		 	var num = document.createElement("div");
		 	num.innerHTML = "Imagem da página "+numero+":";
		 	var file = document.createElement("input");
		 	file.setAttribute("type", "file");
		 	file.setAttribute("name", "imagemEscaneada");
		 	d.appendChild(file);
		 	document.getElementById("maisUploads").appendChild(num);
		 	document.getElementById("maisUploads").appendChild(d);
		 	numero++;
		}

		function compl()
		{
			var compl = document.getElementById("complementares");
			var img = document.getElementById("imgCompl");
			if(compl.style.display == "none")
			{
				compl.style.display = "";
				img.src = "<@ww.url includeParams="none" value="/imgs/arrow_up.gif"/>"
			}
			else
			{
				compl.style.display = "none";
				img.src = "<@ww.url includeParams="none" value="/imgs/arrow_down.gif"/>"
			}
		}
		function candidatosHomonimos()
		{
			var url = "<@ww.url value="/captacao/candidato/infoCandidato.action"/>"
			getCandidatosHomonimos(url);
		}
		

	</script>
<#assign validarCampos="return validaForm()"/>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	<@ww.form name="form" action="insertCurriculoPlus.action" onsubmit="${validarCampos}" validate="true" method="POST" enctype="multipart/form-data">
		<b>Preenchimento de Dados</b>
		<br><br>
		<@ww.textfield label="Nome" id="nome" name="candidato.nome" required="true" cssStyle="width:400px;" liClass="liLeft" onblur="candidatosHomonimos();" maxLength="70"/>
		<@ww.select label="Sexo" required="true" name="candidato.pessoal.sexo" list="sexos" cssStyle="width: 100px;" />
		<@ww.div id="homonimos" cssStyle="color:blue;display:none; ">
			Existe(m) candidato(s) homônimo(s):
			<@ww.div id="nomesHomonimos" cssStyle="color:red;">	</@ww.div>
		</@ww.div>
		<br>
		<@frt.checkListBox label="Cargo/Função Pretendida*" name="cargosCheck" list="cargosCheckList" filtro="true"/>
		<br>

		<@ww.textfield label="Indicado Por" name="candidato.pessoal.indicadoPor" id="indicado" cssStyle="width: 300px;" maxLength="100"/>

		<li>
			<a href="javascript:compl();" style="text-decoration: none"><img border="0" title="" id="imgCompl" src="<@ww.url includeParams="none" value="/imgs/arrow_down.gif"/>"> Dados complementares</a>
		</li>
		<li>
			<@ww.div id="complementares" cssStyle="display:none;" cssClass="divInfo">
				<ul>
					<@ww.datepicker label="Nascimento" name="candidato.pessoal.dataNascimento" id="nascimento"  liClass="liLeft" cssClass="mascaraData" value="${data}"/>
					<@ww.textfield label="CPF"  name="candidato.pessoal.cpf" id="cpf" liClass="liLeft" cssClass="mascaraCpf" onchange="verificaCpfDuplicado(this.value,${empresaId},false);" onblur="verificaCpfDuplicado(this.value,${empresaId},false);"/>
					<@ww.div id="msgCPFDuplicado" cssStyle="display:none;"></@ww.div>
					<@ww.select label="Colocação" name="candidato.colocacao" list="colocacaoList"/>
					<br>
					<@ww.select label="Estado" name="candidato.endereco.uf.id" id="uf" list="ufs"  liClass="liLeft" cssStyle="width: 45px;" listKey="id" listValue="sigla" headerKey="" headerValue=""/>
					<@ww.select label="Cidade" name="candidato.endereco.cidade.id" id="cidade"  list="cidades" listKey="id" listValue="nome" cssStyle="width: 245px;" headerKey="" headerValue=""  liClass="liLeft"/>
					<@ww.textfield label="Bairro" name="candidato.endereco.bairro" id="bairroNome" cssStyle="width: 300px;"  maxLength="60"/>
					<@ww.div id="bairroContainer"/>
				</ul>
			</@ww.div>
		</li>
		<br>
		<b>Escaneamento</b>
		<br>
		Escaneie o currículo gerando os arquivos de imagem e o arquivo com o texto <br> correspondente (OCR).
		<br><br>
		<b>Seleção de Arquivos</b>
		<br>

		${avisoAnexo}
		<br>
		Arquivo de texto gerado via OCR:${obrigacaoAnexo}
		<@ww.file name="ocrTexto" id="ocrTexto" accept="text/*"/>
		<br>
		<@ww.div liClass="liLeft">Imagem da página 1:</@ww.div>
		<@ww.file name="imagemEscaneada" id="imagemEscaneada"/>
		<div id="maisUploads"></div>
		<div id="maisUploadsLink"><a href="javascript:addFileInput();" style="text-decoration: none"><img border="0" title="" id="imgCompl" src="<@ww.url includeParams="none" value="/imgs/page_white_copy.gif"/>">  Adicionar mais páginas</a></div>
		<br>
		
		<@ww.hidden name="candidato.empresa.id" />
		<@ww.hidden name="candidato.id" />
		
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnSalvarArquivos"></button>
		<button onclick="window.location='list.action'" class="btnCancelar"></button>
	</div>

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/forms/geral/bairros.js?version=${versao}"/>'></script>
	
</body>
</html>