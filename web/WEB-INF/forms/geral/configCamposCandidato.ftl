<script type='text/javascript'>
		var camposVisivel = '${parametrosDoSistema.camposCandidatoVisivel}';
		var camposObrigatorio = '${parametrosDoSistema.camposCandidatoObrigatorio}';
		
		$(function() {
			var $obrigatorios = $('.configCampos input[name=camposObrigatorios]');
			var $visiveis = $('.configCampos input[name=camposVisivels]');
			
			abas(1);
			
			$(camposObrigatorio.split(',')).each(function (){
			    $('[value=' + this + ']').attr('checked', true);
			});
			
			$(camposVisivel.split(',')).each(function (){
			    $visiveis.filter('[value=' + this + ']').attr('checked', true);
			});
			
			$visiveis.change(function(){
                $obrigatorios.filter('[value=' + this.value + '][class!=desabilitado]').attr('disabled', !this.checked);
                $obrigatorios.filter('[class=' + this.value + '][class!=desabilitado]').attr('disabled', !this.checked);
            });

			$visiveis.filter(':not(:checked)').each(function(){
				$obrigatorios.filter('[value=' + this.value + ']').attr('disabled', true);
				$obrigatorios.filter('[class=' + this.value + '][class!=desabilitado]').attr('disabled', true);
			});
			
			$('#bairroNome').click(function(e) {
				if ($(this).attr('checked'))
					$('#cidade').attr('checked', true);
			});
			
			$('#cidade').click(function(e) {
				if ($(this).attr('checked') == false)
					$('#bairroNome').removeAttr('checked');
			});
			
			$('.marcarTodos').click(function(e) {
				var marcado = $(this).attr('checked');
				
				$(this).parents("div[class*=content]").find("input[name='camposVisivels']:enabled").each(function() {
					$(this).attr('checked', marcado).change();
				}); 
				
				$("#marcador").text(marcado ? "Desmarcar Todos" : "Marcar Todos");
			});
			
			$("#comfirmaSenhaObr").toggleDisabled( !$("#senhaObr").is(":checked") );
			
			$("#senhaObr").change(function(){
				$("#comfirmaSenhaObr").toggleDisabled();
			});
		
			$('.checkCampoComposto').each(function(index,elem){
				validaCampoCompostoObrigatorio(elem);
			});
			
		});
</script>

<div class="abas">
	<div class="option-aba1 abaDadosPessoais" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(246, 246, 246); background: rgb(246, 246, 246);"><a href="javascript: abas(1)">Dados Pessoais</a></div>
	<div class="option-aba2 abaFormacaoEscolar" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(204, 204, 204); background: rgb(217, 217, 217);"><a href="javascript: abas(2)">Formação Escolar</a></div>
	<div class="option-aba3 abaPerfilProfissional" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(204, 204, 204); background: rgb(217, 217, 217);"><a href="javascript: abas(3)">Perfil Profissional</a></div>
	<div class="option-aba4 abaExperiencias" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(204, 204, 204); background: rgb(217, 217, 217);"><a href="javascript: abas(4)">Experiências</a></div>
	<div class="option-aba5 abaDocumentos" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(204, 204, 204); background: rgb(217, 217, 217);"><a href="javascript: abas(5)">Documentos</a></div>
</div>

<@ww.form name="form" action="updateCamposCandidato.action"  method="POST">
	<@ww.hidden id="camposTabs" name="camposTabs"/>
	
	<div class="content1">
		<table id="abaDadosPessoais" cellspacing="0" class="configCampos">
		    <thead>
			<tr>
				<th>
					<input id="marcarTodosCandidato1" class="marcarTodos check-visivel" type="checkbox" />
    				<label for="marcarTodosCandidato1" class="label-visivel"></label>
				</th>
				<th>Campos</th>
				<th>Obrigatório</th>
			</tr>
			</thead>
			<tbody>
			<tr class="desabilitado">
				<td>
					<input id="visivel-candidato-nome" class="check-visivel" name="camposVisivels" value="nome" type="checkbox" checked disabled />
    				<label for="visivel-candidato-nome" class="label-visivel"></label>
				</td>
				<td class="campo">Nome</td>
				<td><input type="checkbox" value="nome" name="camposObrigatorios" checked disabled class="desabilitado"/></td>
				
				<input type="hidden" id="nomeObr" value="nome" name="camposObrigatorios"/>
				<input type="hidden" id="nomeVis" value="nome" name="camposVisivels" />
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-nascimento" class="check-visivel" name="camposVisivels" value="nascimento" type="checkbox"/>
    				<label for="visivel-candidato-nascimento" class="label-visivel"></label>
				</td>
				<td class="campo">Nascimento</td>
				<td><input type="checkbox" value="nascimento" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-naturalidade" class="check-visivel" name="camposVisivels" value="naturalidade" type="checkbox" />
    				<label for="visivel-candidato-naturalidade" class="label-visivel"></label>
				</td>
				<td class="campo">Naturalidade</td>
				<td><input type="checkbox" value="naturalidade" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-sexo" class="check-visivel" name="camposVisivels" value="sexo" type="checkbox" />
    				<label for="visivel-candidato-sexo" class="label-visivel"></label>
				</td>
				<td class="campo">Sexo</td>
				<td><input type="checkbox" value="sexo" name="camposObrigatorios" checked disabled class="desabilitado"/></td>
				<input type="hidden"  id="sexo" value="sexo" name="camposObrigatorios" />
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-cpf" class="check-visivel" name="camposVisivels" value="cpf" type="checkbox" />
    				<label for="visivel-candidato-cpf" class="label-visivel"></label>
				</td>
				<td class="campo">CPF</td>
				<td><input type="checkbox" value="cpf" name="camposObrigatorios"/></td>
			</tr>
			<tr class="desabilitado">
				<td>
					<input id="visivel-candidato-escolaridade" class="check-visivel" name="camposVisivels" value="escolaridade" type="checkbox" checked disabled />
    				<label for="visivel-candidato-escolaridade" class="label-visivel"></label>
				</td>
				<td class="campo">Escolaridade</td>
				<td><input type="checkbox" value="escolaridade" name="camposObrigatorios" checked disabled class="desabilitado"/></td>
				
				<input type="hidden"  id="escolaridadeObr" value="escolaridade" name="camposObrigatorios" />
				<input type="hidden"  id="escolaridadeVis_" value="escolaridade" name="camposVisivels" />
			</tr>
			
			<tr>
				<td id="endRight">
					<input id="visivel-candidato-endereco" class="check-visivel" name="camposVisivels" value="endereco" type="checkbox" />
    				<label for="visivel-candidato-endereco" class="label-visivel"></label>
				</td>
				<td class="campo">Endereço</td>
				<td id="endLeft"></td>
			</tr>
			<tr>
				<td></td>
				<td class="campo" class="grupoObrigatorio" >CEP</td>
				<td>
					<input name="camposObrigatorios" value="cep" type="checkbox" class="endereco"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td class="campo" class="grupoObrigatorio">Logradouro</td>
				<td>
					<input name="camposObrigatorios" value="ende" type="checkbox" class="endereco"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td class="campo" class="grupoObrigatorio">Nº</td>
				<td>
					<input name="camposObrigatorios" value="num" type="checkbox" class="endereco"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td class="campo" class="grupoObrigatorio">Complemento</td>
				<td>
					<input name="camposObrigatorios" value="complemento" type="checkbox" class="endereco"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td class="campo" class="grupoObrigatorio">Bairro</td>
				<td>
					<input name="camposObrigatorios" value="bairroNome" type="checkbox" class="endereco"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td class="campo" class="grupoObrigatorio">Estado/Cidade</td>
				<td>
					<input class="endereco" type="checkbox"  name="camposObrigatorios" value="cidade" />
		
					<input type="hidden" name="camposObrigatorios" value="uf"/>
				</td>
			</tr>

			<tr>
				<td>
					<input id="visivel-candidato-email" class="check-visivel" name="camposVisivels" value="email" type="checkbox"/>
    				<label for="visivel-candidato-email" class="label-visivel"></label>
				</td>
				<td class="campo">E-mail</td>
				<td><input type="checkbox" value="email" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-fone" class="check-visivel" name="camposVisivels" value="fone" type="checkbox"/>
    				<label for="visivel-candidato-fone" class="label-visivel"></label>
				</td>
				<td class="campo">Telefone</td>
				<td><input type="checkbox" value="fone" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-celular" class="check-visivel" name="camposVisivels" value="celular" type="checkbox"/>
    				<label for="visivel-candidato-celular" class="label-visivel"></label>
				</td>
				<td class="campo">Celular</td>
				<td><input type="checkbox" value="celular" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-nomeContato" class="check-visivel" name="camposVisivels" value="nomeContato" type="checkbox"/>
    				<label for="visivel-candidato-nomeContato" class="label-visivel"></label>
				</td>
				<td class="campo">Contato</td>
				<td><input type="checkbox" value="nomeContato" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-parentes" class="check-visivel" name="camposVisivels" value="parentes" type="checkbox"/>
    				<label for="visivel-candidato-parentes" class="label-visivel"></label>
				</td>
				<td class="campo">Parentes/Amigos na empresa</td>
				<td><input type="checkbox" value="parentes" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-indicado" class="check-visivel" name="camposVisivels" value="indicado" type="checkbox"/>
    				<label for="visivel-candidato-indicado" class="label-visivel"></label>
				</td>
				<td class="campo">Indicado Por</td>
				<td><input type="checkbox" value="indicado" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-estadoCivil" class="check-visivel" name="camposVisivels" value="estadoCivil" type="checkbox"/>
    				<label for="visivel-candidato-estadoCivil" class="label-visivel"></label>
				</td>
				<td class="campo">Estado Civil</td>
				<td><input type="checkbox" value="estadoCivil" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-qtdFilhos" class="check-visivel" name="camposVisivels" value="qtdFilhos" type="checkbox"/>
    				<label for="visivel-candidato-qtdFilhos" class="label-visivel"></label>
				</td>
				<td class="campo">Qtd. Filhos</td>
				<td><input type="checkbox" value="qtdFilhos" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-nomeConjuge" class="check-visivel" name="camposVisivels" value="nomeConjuge" type="checkbox"/>
    				<label for="visivel-candidato-nomeConjuge" class="label-visivel"></label>
				</td>
				<td class="campo">Nome do Cônjuge</td>
				<td><input type="checkbox" value="nomeConjuge" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-profConjuge" class="check-visivel" name="camposVisivels" value="profConjuge" type="checkbox"/>
    				<label for="visivel-candidato-profConjuge" class="label-visivel"></label>
				</td>
				<td class="campo">Profissão do Cônjuge</td>
				<td><input type="checkbox" value="profConjuge" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-nomePai" class="check-visivel" name="camposVisivels" value="nomePai" type="checkbox"/>
    				<label for="visivel-candidato-nomePai" class="label-visivel"></label>
				</td>
				<td class="campo">Nome do Pai</td>
				<td><input type="checkbox" value="nomePai" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-profPai" class="check-visivel" name="camposVisivels" value="profPai" type="checkbox"/>
    				<label for="visivel-candidato-profPai" class="label-visivel"></label>
				</td>
				<td class="campo">Profissão do Pai</td>
				<td><input type="checkbox" value="profPai" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-nomeMae" class="check-visivel" name="camposVisivels" value="nomeMae" type="checkbox"/>
    				<label for="visivel-candidato-nomeMae" class="label-visivel"></label>
				</td>
				<td class="campo">Nome da Mãe</td>
				<td><input type="checkbox" value="nomeMae" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-profMae" class="check-visivel" name="camposVisivels" value="profMae" type="checkbox"/>
    				<label for="visivel-candidato-profMae" class="label-visivel"></label>
				</td>
				<td class="campo">Profissão da Mãe</td>
				<td><input type="checkbox" value="profMae" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-pensao" class="check-visivel" name="camposVisivels" value="pensao" type="checkbox"/>
    				<label for="visivel-candidato-pensao" class="label-visivel"></label>
				</td>
				<td class="campo">Paga Pensão</td>
				<td><input type="checkbox" value="pensao" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-possuiVeiculo" class="check-visivel" name="camposVisivels" value="possuiVeiculo" type="checkbox"/>
    				<label for="visivel-candidato-possuiVeiculo" class="label-visivel"></label>
				</td>
				<td class="campo">Possui Veículo</td>
				<td><input type="checkbox" value="possuiVeiculo" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-deficiencia" class="check-visivel" name="camposVisivels" value="deficiencia" type="checkbox"/>
    				<label for="visivel-candidato-deficiencia" class="label-visivel"></label>
				</td>
				<td class="campo">Deficiência</td>
				<td><input type="checkbox" value="deficiencia" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-comoFicouSabendoVaga" class="check-visivel" name="camposVisivels" value="comoFicouSabendoVaga" type="checkbox"/>
    				<label for="visivel-candidato-comoFicouSabendoVaga" class="label-visivel"></label>
				</td>
				<td class="campo">Como ficou sabendo da vaga</td>
				<td><input type="checkbox" value="comoFicouSabendoVaga" name="camposObrigatorios" /></td>
			</tr>
			<tr class="desabilitado">
				<td>
					<input id="visivel-candidato-senha" class="check-visivel" name="camposVisivels" checked disabled value="senha" type="checkbox"/>
    				<label for="visivel-candidato-senha" class="label-visivel"></label>
				</td>
				<td class="campo">Senha</td>
				<td><input type="checkbox" value="senha" name="camposObrigatorios" id="senhaObr" class="desabilitado"/></td>
				<input type="hidden"  id="comfirmaSenha_Vis_" value="comfirmaSenha" name="camposVisivels" />
				<input type="hidden"  id="comfirmaSenhaObr" value="comfirmaSenha" name="camposObrigatorios" />
				<input type="hidden"  id="senhaVis_" value="senha" name="camposVisivels" />
			</tr>
			</tbody>
		</table>
	</div>
	
	<div class="content2">
		<table id="abaFormacaoEscolar" cellspacing="0" class="configCampos">
		    <thead>		
			<tr>
				<th>
					<input id="marcarTodosCandidato2" class="marcarTodos check-visivel" type="checkbox" />
    				<label for="marcarTodosCandidato2" class="label-visivel"></label>
				</th>
				<th>Campos</th>
				<th>Obrigatório</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td>
					<input id="visivel-candidato-formacao" class="check-visivel" name="camposVisivels" value="formacao" type="checkbox"/>
    				<label for="visivel-candidato-formacao" class="label-visivel"></label>
				</td>
				<td class="campo">Formação Escolar</td>
				<td><input type="checkbox" value="formacao" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-idioma" class="check-visivel" name="camposVisivels" value="idioma" type="checkbox"/>
    				<label for="visivel-candidato-idioma" class="label-visivel"></label>
				</td>
				<td class="campo">Idiomas</td>
				<td><input type="checkbox" value="idioma" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-desCursos" class="check-visivel" name="camposVisivels" value="desCursos" type="checkbox"/>
    				<label for="visivel-candidato-desCursos" class="label-visivel"></label>
				</td>
				<td class="campo">Outros Cursos</td>
				<td><input type="checkbox" value="desCursos" name="camposObrigatorios" /></td>
			</tr>
		</table>
	</div>
	
	<div class="content3">
		<table id="abaPerfilProfissional" cellspacing="0" class="configCampos">
		    <thead>		
			<tr>
				<th>
					<input id="marcarTodosCandidato3" class="marcarTodos check-visivel" type="checkbox" />
    				<label for="marcarTodosCandidato3" class="label-visivel"></label>
				</th>
				<th>Campos</th>
				<th>Obrigatório</th>
			</tr>
			</thead>		
			<tr>
				<td>
					<input id="visivel-candidato-cargosCheck" class="check-visivel" name="camposVisivels" value="cargosCheck" type="checkbox"/>
    				<label for="visivel-candidato-cargosCheck" class="label-visivel"></label>
				</td>
				<td class="campo">Cargo / Função Pretendida</td>
				<td><input type="checkbox" value="cargosCheck" name="camposObrigatorios"/></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-areasCheck" class="check-visivel" name="camposVisivels" value="areasCheck" type="checkbox"/>
    				<label for="visivel-candidato-areasCheck" class="label-visivel"></label>
				</td>
				<td class="campo">Áreas de Interesse</td>
				<td><input type="checkbox" value="areasCheck" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-conhecimentosCheck" class="check-visivel" name="camposVisivels" value="conhecimentosCheck" type="checkbox"/>
    				<label for="visivel-candidato-conhecimentosCheck" class="label-visivel"></label>
				</td>
				<td class="campo">Conhecimentos</td>
				<td><input type="checkbox" value="conhecimentosCheck" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-colocacao" class="check-visivel" name="camposVisivels" value="colocacao" type="checkbox"/>
    				<label for="visivel-candidato-colocacao" class="label-visivel"></label>
				</td>
				<td class="campo">Colocação</td>
				<td><input type="checkbox" value="colocacao" name="camposObrigatorios" /></td>
			</tr>
		</table>
	</div>
	
	<div class="content4">
		<table id="abaExperiencias" cellspacing="0" class="configCampos">
		    <thead>		
			<tr>
				<th>
					<input id="marcarTodosCandidato4" class="marcarTodos check-visivel" type="checkbox" />
    				<label for="marcarTodosCandidato4" class="label-visivel"></label>
				</th>
				<th>Campos</th>
				<th>Obrigatório</th>
			</tr>
			</thead>			
			<tr>
				<td>
					<input id="visivel-candidato-expProfissional" class="check-visivel" name="camposVisivels" value="expProfissional" type="checkbox"/>
    				<label for="visivel-candidato-expProfissional" class="label-visivel"></label>
				</td>
				<td class="campo">Experiência Profissional</td>
				<td><input type="checkbox" value="expProfissional" name="camposObrigatorios"/></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-infoAdicionais" class="check-visivel" name="camposVisivels" value="infoAdicionais" type="checkbox"/>
    				<label for="visivel-candidato-infoAdicionais" class="label-visivel"></label>
				</td>
				<td class="campo">Informações Adicionais</td>
				<td><input type="checkbox" value="infoAdicionais" name="camposObrigatorios"/></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-obsrh" class="check-visivel" name="camposVisivels" value="obsrh" type="checkbox"/>
    				<label for="visivel-candidato-obsrh" class="label-visivel"></label>
				</td>
				<td class="campo">Observações do RH</td>
				<td><input type="checkbox" value="obsrh" name="camposObrigatorios"/></td>
			</tr>
		</table>
	</div>
	
	<div class="content5">
		<table id="abaDocumentos" cellspacing="0" class="configCampos">
		    <thead>		
			<tr>
				<th>
					<input id="marcarTodosCandidato5" class="marcarTodos check-visivel" type="checkbox" />
    				<label for="marcarTodosCandidato5" class="label-visivel"></label>
				</th>
				<th>Campos</th>
				<th>Obrigatório</th>
			</tr>
			</thead>			
			<tr>
				<td>
					<input id="visivel-candidato-identidade" class="check-visivel" name="camposVisivels" value="identidade" type="checkbox"/>
    				<label for="visivel-candidato-identidade" class="label-visivel"></label>
				</td>
				<td class="campo">Dados da Identidade</td>
				<td>
					<input id="checkIdentidade" type="checkbox" class='checkCampoComposto' name="camposObrigatorios" value="identidade" onchange="validaCampoCompostoObrigatorio(this)" />
					
					<input type="hidden" class="campo-hidden" value="rgOrgaoEmissor" />
				</td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-carteiraHabilitacao" class="check-visivel" name="camposVisivels" value="carteiraHabilitacao" type="checkbox"/>
    				<label for="visivel-candidato-carteiraHabilitacao" class="label-visivel"></label>
				</td>
				<td class="campo">Dados da Carteira de Habilitação</td>
				<td>
					<input id="checkCarteiraHabilitacao" class='checkCampoComposto' type="checkbox"  name="camposObrigatorios" value="carteiraHabilitacao" onchange="validaCampoCompostoObrigatorio(this)" />
					
					<input type="hidden"  class="campo-hidden" value="vencimento" />
					<input type="hidden"  class="campo-hidden" value="chCategoria"/>
				</td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-tituloEleitoral" class="check-visivel" name="camposVisivels" value="tituloEleitoral" type="checkbox"/>
    				<label for="visivel-candidato-tituloEleitoral" class="label-visivel"></label>
				</td>
				<td class="campo">Dados do Título Eleitoral</td>
				<td>
					<input id="checkTituloEleitoral" class="checkCampoComposto" type="checkbox" name="camposObrigatorios" value="tituloEleitoral" onchange="validaCampoCompostoObrigatorio(this)" />
					
					<input type="hidden" class="campo-hidden" value="titEleitZona"/>
					<input type="hidden" class="campo-hidden" value="titEleitSecao" />
				</td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-certificadoMilitar" class="check-visivel" name="camposVisivels" value="certificadoMilitar" type="checkbox"/>
    				<label for="visivel-candidato-certificadoMilitar" class="label-visivel"></label>
				</td>
				<td class="campo">Dados do Certificado Militar</td>
				<td>
					<input id="checkCertificadoMilitar" class="checkCampoComposto" type="checkbox" name="camposObrigatorios" value="certificadoMilitar" onchange="validaCampoCompostoObrigatorio(this)" />
						
					<input type="hidden" class="campo-hidden"  value="certMilTipo"/>	
					<input type="hidden" class="campo-hidden"  value="certMilSerie" />
				</td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-ctps" class="check-visivel" name="camposVisivels" value="ctps" type="checkbox"/>
    				<label for="visivel-candidato-ctps" class="label-visivel"></label>
				</td>
				<td class="campo">Dados da CTPS - Carteira de Trabalho e Previdência Social</td>
				<td>
					<input id="checkCTPS" class="checkCampoComposto" type="checkbox" name="camposObrigatorios" value="ctps" onchange="validaCampoCompostoObrigatorio(this)" />
					
					<input type="hidden" class="campo-hidden" value="ctpsSerie"/>
					<input type="hidden" class="campo-hidden" value="ctpsUf" />
				</td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidato-pis" class="check-visivel" name="camposVisivels" value="pis" type="checkbox"/>
    				<label for="visivel-candidato-pis" class="label-visivel"></label>
				</td>
				<td class="campo">PIS - Programa de Integração Social</td>
				<td><input type="checkbox" value="pis" name="camposObrigatorios" /></td>
			</tr>
		</table>
	</div>
</@ww.form>