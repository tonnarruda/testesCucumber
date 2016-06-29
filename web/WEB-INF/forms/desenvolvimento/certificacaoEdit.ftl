<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
	<@ww.head/>
		<#if certificacao.id?exists>
			<title>Editar Certificação</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Certificação</title>
			<#assign formAction="insert.action"/>
		</#if>
	
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
		<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
		
		<script type="text/javascript">
			var cursosChecados;
			var avaliacoesPraticasChecados;
			var certificacaoPreRequisitoId;
			$(function() {
				cursosChecados = $('input:checkbox[name=cursosCheck]:checked').map(function() { return this.value; }).get();
				avaliacoesPraticasChecados = $('input:checkbox[name=avaliacoesPraticasCheck]:checked').map(function() { return this.value; }).get();
				certificacaoPreRequisitoId = $('#certificacaoPreRequisitoId').val();
			});
			
			function verificaAlteracaoCertificacao(){
				alterou = false;
				
				if(($('input:checkbox[name=cursosCheck]:checked').size() != cursosChecados.length) || ($('input:checkbox[name=avaliacoesPraticasCheck]:checked').size() != avaliacoesPraticasChecados.length)){
					$('#alterouCertificacao').val(true);
					return;
				}
				
				$('input:checkbox[name=cursosCheck]:checked').each(function(){
					if(cursosChecados.indexOf(this.value) < 0)
						alterou = true;
				});
				
				$('input:checkbox[name=avaliacoesPraticasCheck]:checked').each(function(){
					if(avaliacoesPraticasChecados.indexOf(this.value) < 0)
						alterou = true;
				});
				
				if(certificacaoPreRequisitoId != $('#certificacaoPreRequisitoId').val())
					alterou = true;
				
				$('#alterouCertificacao').val(alterou);
			}
			
			function processaSubmit(){
				$('#periodicidade').css('background-color','#fff');
				if($('#periodicidade').val() != "" && $('#periodicidade').val() == 0){
					jAlert('A periodicidade não pode ser 0 (zero)');
					$('#periodicidade').css('background-color','rgb(255, 238, 194)');
					return;
				}
				
				if(validaFormulario('form', new Array('nome', '@cursosCheck'), null, true)){
					<#if empresaSistema.controlarVencimentoPorCertificacao && certificacao.id?exists>
						if($('#alterouCertificacao').val() == 'true'){
							msg = 'Esta operação irá descertificar todos os colaboradores e certificá-los novamente de acordo com as novas regras pré-estabelecidas, removendo todos os históricos vinculados à certificação.</br></br>'
									+ 'Obs: Essa operação pode demorar, dependendo da quantidades de dados em seu sistema.</br></br>'
									+ 'Tem certeza que deseja efetuar essa alteração?';
							$('<div>'+ msg +'</div>').dialog({	
											title: 'Alerta!',
											modal: true, 
											height: 250,
											width: 500,
											buttons: [ 	{ text: "Sim", click: function() {$(this).dialog("close"); return submit();}},
											    		{ text: "Não", click: function() {$(this).dialog("close"); } } ] 
							});
						}else
							return submit();
					
					<#else>
						return submit();
					</#if>
				}
			}
			
			function submit(){
				processando('${urlImgs}');
				document.form.submit();
			}
			
		</script>
	
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="processaSubmit()" method="POST">
			<@ww.textfield label="Nome" id="nome" name="certificacao.nome"  cssStyle="width:500px" maxLength="100" required="true"/>
			<@ww.textfield label="Periodicidade em meses" name="certificacao.periodicidade" id="periodicidade" cssStyle="width:30px; text-align:right;" maxLength="4" onkeypress = "return(somenteNumeros(event,''));"/>
			<@ww.select Label="Certificação pré-requisito" name="certificacao.certificacaoPreRequisito.id" id="certificacaoPreRequisitoId" list="certificacoes" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." cssStyle="width: 500px;" onchange="verificaAlteracaoCertificacao()"/>
        	<@frt.checkListBox label="Cursos" name="cursosCheck" list="cursosCheckList" filtro="true" onClick="verificaAlteracaoCertificacao()" required="true"/>
       		<@frt.checkListBox label="Avaliações Práticas" name="avaliacoesPraticasCheck" list="avaliacoesPraticasCheckList" filtro="true" onClick="verificaAlteracaoCertificacao()"/>
			
			<@ww.hidden name="certificacao.id" />
			<@ww.hidden name="alterouCertificacao" id="alterouCertificacao" value="false"/>
			
			<@ww.token/>
		</@ww.form>
		
		<div class="buttonGroup">
			<button onclick="processaSubmit();" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnCancelar"></button>
		</div>
	</body>
</html>