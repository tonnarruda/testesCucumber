<html>
<head>
<@ww.head/>
	<title>Editar Configurações Cadastro de Candidato(módulo externo)</title>
</head>
<body>
	<style type="text/css">
		.configCampos {
			margin: 8px 0;
			width: 600px;
			border: 1px solid #BBB;
			border-collapse: collapse;
		}
		.configCampos td {
			border: 1px solid #BBB;
			padding: 0 5px;
			width: 100px;
			text-align: center;
		}
		.configCampos td:first-child {
			width: 400px;
			text-align: left;
		}
	</style>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	
	<script type='text/javascript'>

		var camposCandidatoVisivel = '${parametrosDoSistema.camposCandidatoVisivel}';
		var camposCandidatoObrigatorio = '${parametrosDoSistema.camposCandidatoObrigatorio}';
		
		jQuery(function($) {
			camposCandidatoObrigatorio.split(',').each(function (campo){
			    jQuery('.configCampos input[name=camposCandidatoObrigatorios][value=' + campo + ']').attr('checked', true);
			});
			camposCandidatoVisivel.split(',').each(function (campo){
			    jQuery('.configCampos input[name=camposCandidatoVisivels][value=' + campo + ']').attr('checked', true);
			});
			
		});
		
		function enviaForm()
		{
			var abas = jQuery('input[name=camposCandidatoVisivels]:checked').parents('table');
			var abasStr = jQuery.map(abas, function (t){ return t.id; }).join(',');
			
			jQuery('#camposCandidatoTabs').val(abasStr);
			document.form.submit();		
		}
	
	</script>	
	<@ww.form name="form" action="updateCamposCandidato.action"  method="POST">
		<@ww.hidden id="camposCandidatoTabs" name="parametrosDoSistema.camposCandidatoTabs"/>
		<table id="abaDadosPessoais" cellspacing="0" class="configCampos">
		    <thead>		
			<tr>
				<th>Dados Pessoais</th>
				<th>Exibir</th>
				<th>Obrigatório</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td>Nome</td>
				<td><input type="checkbox" value="nome" name="camposCandidatoVisivels"  checked disabled/></td>
				<td><input type="checkbox" value="nome" name="camposCandidatoObrigatorios"  checked disabled/></td>
			</tr>
			<tr>
				<td>Nascimento</td>
				<td><input type="checkbox" value="nascimento" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="nascimento" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Naturalidade</td>
				<td><input type="checkbox" value="naturalidade" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="naturalidade" id="a" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Sexo</td>
				<td><input type="checkbox" value="sexo" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="sexo" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>CPF</td>
				<td><input type="checkbox" value="cpf" name="camposCandidatoVisivels"  checked disabled/></td>
				<td><input type="checkbox" value="cpf" name="camposCandidatoObrigatorios"  checked disabled/></td>
			</tr>
			<tr>
				<td>Escolaridade</td>
				<td><input type="checkbox" value="escolaridade" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="escolaridade" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Endereço</td>
				<td><input type="checkbox" value="endereco" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="endereco" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>E-mail</td>
				<td><input type="checkbox" value="email" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="email" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Telefone</td>
				<td><input type="checkbox" value="telefone" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="telefone" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Celular</td>
				<td><input type="checkbox" value="celular" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="celular" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Contato</td>
				<td><input type="checkbox" value="nomeContato" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="nomeContato" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Parentes/Amigos na empresa</td>
				<td><input type="checkbox" value="parentes" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="parentes" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Estado Civil</td>
				<td><input type="checkbox" value="estadoCivil" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="estadoCivil" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Qtd. Filhos</td>
				<td><input type="checkbox" value="qtdFilhos" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="qtdFilhos" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Nome do Cônjuge</td>
				<td><input type="checkbox" value="nomeConjuge" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="nomeConjuge" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Profissão do Cônjuge</td>
				<td><input type="checkbox" value="profConjuge" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="profConjuge" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Nome do Pai</td>
				<td><input type="checkbox" value="nomePai" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="nomePai" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Profissão do Pai</td>
				<td><input type="checkbox" value="profConjuge" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="profConjuge" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Nome da Mãe</td>
				<td><input type="checkbox" value="nomeMae" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="nomeMae" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Profissão da Mãe</td>
				<td><input type="checkbox" value="profConjuge" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="profConjuge" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Paga Pensão</td>
				<td><input type="checkbox" value="pensao" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="pensao" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Possui Veículo</td>
				<td><input type="checkbox" value="possuiVeiculo" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="possuiVeiculo" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Deficiência</td>
				<td><input type="checkbox" value="deficiencia" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="deficiencia" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Senha</td>
				<td><input type="checkbox" value="senha" name="camposCandidatoVisivels" checked disabled/></td>
				<td><input type="checkbox" value="senha" name="camposCandidatoObrigatorios" checked disabled/></td>
			</tr>
			</tbody>
		</table>
		<table id="abaFormacaoEscolar" cellspacing="0" class="configCampos">
		    <thead>		
			<tr>
				<th>Formação Escolar</th>
				<th>Exibir</th>
				<th>Obrigatório</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td>Formação Escolar</td>
				<td><input type="checkbox" value="formacao" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="formacao" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Idiomas</td>
				<td><input type="checkbox" value="idioma" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="idioma" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Outros Cursos</td>
				<td><input type="checkbox" value="desCursos" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="desCursos" name="camposCandidatoObrigatorios" /></td>
			</tr>
		</table>
		
		<table id="abaPerfilProfissional" cellspacing="0" class="configCampos">
		    <thead>		
			<tr>
				<th>Perfil Profissional</th>
				<th>Exibir</th>
				<th>Obrigatório</th>
			</tr>
			</thead>		
			<tr>
				<td>Cargo / Função Pretendida</td>
				<td><input type="checkbox" value="funcaoPretendida" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="funcaoPretendida" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Áreas de Interesse</td>
				<td><input type="checkbox" value="areasInteresse" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="areasInteresse" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Conhecimentos</td>
				<td><input type="checkbox" value="conhecimentos" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="conhecimentos" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Colocação</td>
				<td><input type="checkbox" value="colocacao" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="colocacao" name="camposCandidatoObrigatorios" /></td>
			</tr>
		</table>
		<table id="abaExperiencias" cellspacing="0" class="configCampos">
		    <thead>		
			<tr>
				<th>Experiências</th>
				<th>Exibir</th>
				<th>Obrigatório</th>
			</tr>
			</thead>			
			<tr>
				<td>Experiência Profissional</td>
				<td><input type="checkbox" value="expProfissional" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="expProfissional" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Informações Adicionais</td>
				<td><input type="checkbox" value="infoAdicionais" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="infoAdicionais" name="camposCandidatoObrigatorios" /></td>
			</tr>
		</table>
		<table id="abaDocumentos" cellspacing="0" class="configCampos">
		    <thead>		
			<tr>
				<th>Documentos</th>
				<th>Exibir</th>
				<th>Obrigatório</th>
			</tr>
			</thead>			
			<tr>
				<td>Identidade</td>
				<td><input type="checkbox" value="identidade" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="identidade" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Carteira de Habilitação</td>
				<td><input type="checkbox" value="cartairaHabilitacao" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="cartairaHabilitacao" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Título Eleitoral</td>
				<td><input type="checkbox" value="tituloEleitoral" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="tituloEleitoral" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>Certificado Militar</td>
				<td><input type="checkbox" value="certificadoMilitar" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="certificadoMilitar" name="camposCandidatoObrigatorios" /></td>
			</tr>
			<tr>
				<td>CTPS - Carteira de Trabalho e Previdência Social</td>
				<td><input type="checkbox" value="ctps" name="camposCandidatoVisivels" /></td>
				<td><input type="checkbox" value="ctps" name="camposCandidatoObrigatorios" /></td>
			</tr>
		</table>
	</@ww.form>
	<div class="buttonGroup">
		<button onclick="enviaForm()" class="btnGravar" ></button>
	</div>
</body>
</html>