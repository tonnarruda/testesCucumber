<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Exportar dados para o Fortes Pessoal</title>
	
	<#assign validarCampos="return validaFormulario('form', new Array('empresaId','grupaAC'))"/>
	<#assign msg = "Você deseja iniciar a exportação dos dados para o sistema Fortes Pessoal?</br> Se confirmar o sistema RH será bloqueado até concluir a primeira fase de exportação."/>
	
	 <script type="text/javascript">
	        $(function() {
	            $('#exportar').click(function(e) {
	                var dialog = $('<p>Voce deseja realizar o início da exportação para o Fortes Pessoal?</br> Se confirmar, o sistema RH será bloqueado até concluir a primeira fase de exportação para o sistema Fortes Pessoal.</p>').dialog({
	                    title: 'Exportar dados para o Fortes Pessoal',
	                    width:'auto',
	                    buttons: {
	                        "Sim": function() {validaFormulario('form', new Array('empresaId','grupaAC'));dialog.dialog('close');},
	                        "Não":  function() {dialog.dialog('close');}
	                        }
	                });
	            });
	        });
    </script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<#if usuarioLogado?exists && usuarioLogado.id == 1>
	
		<@ww.form name="form" action="exportarAC.action"  onsubmit="${validarCampos}" method="POST">
			<@ww.select label="Exportar empresa" name="empresaId" id="empresaId" listKey="id" listValue="nome" list="empresas" headerKey="" headerValue="Selecione..." cssStyle="width:150px"/>
			<@ww.select label="Para o AC" name="grupoAC" id="grupaAC" listKey="codigo" listValue="codigoDescricao" list="gruposACs" headerKey="" headerValue="Selecione..." cssStyle="width:150px"/>
		</@ww.form>
		
		<div class="buttonGroup">
			<#if emProcessoExportacaoAC>
				<button onclick="${validarCampos}" class="btnExportar"></button>
			<#else>
				<input type="button" value=" " id="exportar" class="btnExportar" />
			</#if>
		</div>
		
	<#else>
		<#if emProcessoExportacaoAC>
			<P>Se todas as pendências do controle de integração com o sistema RH no sistema Fortes Pessoal tiverem sido confirmadas, favor entrar em contato com o suporte do sistema RH para concluir a exportação dos dados.</p>
			<P>Suporte RH: (85) 4005.1127</P>   
		<#else>
			<p>Usuário sem permissão de acesso.<br />
			Uso exclusivo para o suporte técnico.</p>
		</#if> 
	</#if>
</body>
</html>