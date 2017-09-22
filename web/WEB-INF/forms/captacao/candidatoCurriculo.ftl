<html>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="Content-Language" content="pt-br" />
	<meta http-equiv="Cache-Control" content="no-cache, no-store" />
	<meta http-equiv="Pragma" content="no-cache, no-store" />
	<meta http-equiv="Expires" content="0" />
	<@ww.head />
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/curriculo.css?version=${versao}"/>');
	</style>
</head>
<body>
<div id="containerCv">

<table width="700px" cellspacing="0">
	<tr>
		<td>
			<table class="foto"  width="100%">
			<tr>
				<td  valign="top">
					<li class="liLeft" style="width: 400px;"><span>Nome:</span><#if candidato.nome?exists> <label id="candidatoNomeAssinatura">${candidato.nome}</label></#if></li>
					<li class="liLeft"><span>Colocação:</span>
						<#if candidato.colocacao?exists>
							<#if candidato.colocacao == 'E'>
								Emprego
							<#elseif candidato.colocacao == 'A'>
								Aprendiz
							<#else>
								Estágio
							</#if>
						<#else>
							Não Informado
						</#if>
					</li>
					<li class="liLeft" style="width: 400px;">
						<span>Data de nascimento:</span>
						<#if candidato.pessoal?exists> ${candidato.pessoal.dataNascimentoFormatada}</#if>
					</li>
					<#--li class="liLeft" style="width: 245px;"><span>Idade:</span><#if idade?exists> ${idade}</#if></li-->

					<li class="liLeft"><span>Pretensão salarial:</span>
						<#if candidato.pretencaoSalarial?exists> ${candidato.pretencaoSalarial?string(",##0.00")}</#if>
					</li>
					<li class="liLeft" style="width: 400px;"><span>Cargo pretendido:</span>
						<#if candidato.cargosDesc?exists>
							${candidato.cargosDesc}
						</#if>
					</li>
					<li class="liLeft" ><span>Escolaridade:</span>
						<#if candidato.pessoal?exists && candidato.pessoal.escolaridadeDescricao?exists> ${candidato.pessoal.escolaridadeDescricao}</#if>
					</li>
					<li class="liLeft" style="width: 400px;"><span>Parentes/Amigos:</span>
						<#if candidato.pessoal?exists && candidato.pessoal.parentesAmigos?exists> ${candidato.pessoal.parentesAmigos}</#if>
					</li>
					<li class="liLeft" ><span>Naturalidade:</span><#if candidato.pessoal?exists && candidato.pessoal.naturalidade?exists> ${candidato.pessoal.naturalidade}</#if></li>
					<br/>
					<li class="liLeft" style="width: 400px;"><span>Indicado por:</span>
						<#if candidato.pessoal?exists && candidato.pessoal.indicadoPor?exists> ${candidato.pessoal.indicadoPor}</#if>
					</li>
					<li class="liLeft"><span>Sexo:</span>
					<#if candidato.pessoal?exists && candidato.pessoal.sexo?exists>
						<#if candidato.pessoal.sexo == 'M'>
							Masculino
						<#else>
							Feminino
						</#if>
					</#if>
					</li>
					<li class="liLeft" style="width: 400px;"><span>Conhecimentos:</span>
						<#if candidato.conhecimentosDescricao?exists> ${candidato.conhecimentosDescricao}</#if>
					</li>
				</td>
				<td align="right">
					<#if candidato.foto?exists>
						<img src="<@ww.url includeParams="none" value="/captacao/candidato/showFoto.action?candidato.id=${candidato.id}"/>" width="100px" height="100px">
					<#else>
						Candidato sem foto
					</#if>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<th>DADOS PESSOAIS</th>
	</tr>
	<tr>
		<td>
		<ul>
			<li class="liLeft" style="width: 400px;"><span>Estado civil:</span><#if candidato.pessoal?exists && candidato.pessoal.estadoCivilDescricao?exists> ${candidato.pessoal.estadoCivilDescricao}</#if></li>
			<li><span>Quantidade de filhos:</span> <#if candidato.pessoal?exists && candidato.pessoal.qtdFilhos?exists> ${candidato.pessoal.qtdFilhos}</#if></li>

			<li class="liLeft" style="width: 400px;"><span>Cônjuge:</span>
				<#if candidato.pessoal?exists && candidato.pessoal.conjuge?exists> ${candidato.pessoal.conjuge}</#if>
			</li>
			<li><span>Profissão:</span>
				<#if candidato.pessoal?exists && candidato.pessoal.profissaoConjuge?exists> ${candidato.pessoal.profissaoConjuge}</#if>
			</li>

			<li class="liLeft" style="width: 400px;"><span>Pai:</span>
				<#if candidato.pessoal?exists && candidato.pessoal.pai?exists> ${candidato.pessoal.pai}</#if>
			</li>
			<li><span>Profissão:</span>
				<#if candidato.pessoal?exists && candidato.pessoal.profissaoPai?exists> ${candidato.pessoal.profissaoPai}</#if>
			</li>

			<li class="liLeft" style="width: 400px;"><span>Mãe:</span>
				<#if candidato.pessoal?exists && candidato.pessoal.mae?exists> ${candidato.pessoal.mae}</#if>
			</li>
			<li><span>Profissão:</span>
				<#if candidato.pessoal?exists && candidato.pessoal.profissaoMae?exists> ${candidato.pessoal.profissaoMae}</#if>
			</li>

			<li>&nbsp;</li>

			<li class="liLeft" style="width: 400px;"><span>Logradouro:</span> <#if candidato.endereco?exists && candidato.endereco.logradouro?exists> ${candidato.endereco.logradouro}</#if></li>
			<li class="liLeft" style="width: 150px;"><span>Número:</span> <#if candidato.endereco?exists && candidato.endereco.numero?exists> ${candidato.endereco.numero}</#if></li>
			<li><span>CEP:</span> <#if candidato.endereco?exists && candidato.endereco.cep?exists> ${candidato.endereco.cep}</#if></li>

			<li class="liLeft" style="width: 400px;"><span>Complemento:</span><#if candidato.endereco?exists && candidato.endereco.complemento?exists> ${candidato.endereco.complemento}</#if></li>
			<li><span>Bairro:</span><#if candidato.endereco?exists && candidato.endereco.bairro?exists> ${candidato.endereco.bairro}</#if></li>

			<li class="liLeft" style="width: 400px;">
				<span>Cidade/UF:</span><#if candidato.endereco?exists && candidato.endereco.cidade?exists && candidato.endereco.cidade.nome?exists> ${candidato.endereco.cidade.nome}</#if>
				<#if candidato.endereco?exists && candidato.endereco.uf?exists && candidato.endereco.uf.sigla?exists>/ ${candidato.endereco.uf.sigla}</#if>
			</li>
			<li>
			  <span>Fone:</span>
				<#if candidato.contato?exists && candidato.contato.foneContatoFormatado?exists> ${candidato.contato.foneContatoFormatado}</#if>
			</li>

			<li class="liLeft" style="width: 400px;"><span>E-mail:</span><#if candidato.contato?exists && candidato.contato.email?exists> ${candidato.contato.email}</#if></li>
			<li><span>Contato:</span>
				<#if candidato.contato?exists && candidato.contato.nomeContato?exists> ${candidato.contato.nomeContato}</#if>
			</li>
			<li><span>Deficiência:</span>
				<#if candidato.pessoal?exists && candidato.pessoal.deficiencia?exists> ${candidato.pessoal.deficienciaDescricao}</#if>
			</li>

			<li>&nbsp;</li>

			<li class="liLeft" style="width: 400px;"><span>RG:</span>
				<#if candidato.pessoal?exists && candidato.pessoal.rg?exists> ${candidato.pessoal.rg}</#if>
			</li>
			<li><span>CPF:</span>
				<#if candidato.pessoal?exists && candidato.pessoal.cpf?exists> ${candidato.pessoal.cpfFormatado}</#if>
			</li>

			<li class="liLeft" style="width: 400px;"><span>Habilitação:</span>
				<#if candidato.habilitacao?exists && candidato.habilitacao.numeroHab?exists> ${candidato.habilitacao.numeroHab}</#if>
			</li>
			<li class="liLeft" style="width: 140px;"><span>Emissão:</span>
				<#if candidato.habilitacao?exists && candidato.habilitacao.emissao?exists> ${candidato.habilitacao.emissao}</#if>
			</li>
			<li style="width: 150px;"><span>Vencimento:</span>
				<#if candidato.habilitacao?exists && candidato.habilitacao.vencimento?exists> ${candidato.habilitacao.vencimento}</#if>
			</li>
			<li class="liLeft" style="width: 400px;"><span>Registro:</span>
				<#if candidato.habilitacao?exists && candidato.habilitacao.registro?exists> ${candidato.habilitacao.registro}</#if>
			</li>
			<li style="width: 100px;"><span>Categoria:</span>
				<#if candidato.habilitacao?exists && candidato.habilitacao.categoria?exists> ${candidato.habilitacao.categoria}</#if>
			</li>
			<li style="width: 100px;"><span>UF:</span>
				<#if candidato.habilitacao?exists && candidato.habilitacao.ufHab?exists && candidato.habilitacao.ufHab.sigla?exists> ${candidato.habilitacao.ufHab.sigla}</#if>
			</li>
		</ul>
		</td>
	</tr>

	<tr>
		<th>INFORMAÇÕES SÓCIO-ECONÔMICAS</th>
	</tr>
	<tr>
		<td>
			<li class="liLeft" style="width: 150px;"><span>Possui veículo:</span>
				<#if candidato.socioEconomica.possuiVeiculo?exists && candidato.socioEconomica.possuiVeiculo>
					Sim
				<#else>
					Não
				</#if>
			</li>
			<li class="liLeft" style="width: 150px;"><span>Paga pensão:</span>
				<#if candidato.socioEconomica?exists && candidato.socioEconomica.pagaPensao>
					Sim
				<#else>
					Não
				</#if>
			</li>
			<li class="liLeft" style="width: 150px;"><span>Quantidade:</span>
				<#if candidato.socioEconomica?exists && candidato.socioEconomica.quantidade?exists> ${candidato.socioEconomica.quantidade}</#if>
			</li>
			<li><span>Valor:</span>
				<#if candidato.socioEconomica?exists && candidato.socioEconomica.valor?exists> ${candidato.socioEconomica.valor?string(",##0.00")}</#if>
			</li>
		</td>
	</tr>

	<#if candidato.formacao?exists && candidato.formacao?size != 0 >
	<tr>
		<th>FORMAÇÃO ESCOLAR</th>
	</tr>
	    	<#list candidato.formacao as formac>
	<tr>
		<td>
	  	<ul>
	    	<li class="liLeft" style="width: 400px;"><span>Área de formação:</span> <#if formac.areaFormacao?exists && formac.areaFormacao.nome?exists> ${formac.areaFormacao.nome}</#if></li>
	       	<li><span>Curso:</span> <#if formac.curso?exists> ${formac.curso}</#if></li>

	       	<li class="liLeft" style="width:400px;"><span>Instituição de ensino:</span><#if formac.local?exists> ${formac.local}</#if></li>
	       	<li style="width:200px;"><span>Tipo:</span>
	       		<#if formac.tipo?exists>
		        	<#if formac.tipo == 'T'>
						 Técnico
					</#if>
					<#if formac.tipo == 'G'>
						Graduação
					</#if>
		        	<#if formac.tipo == 'M'>
						Mestrado
					</#if>
					<#if formac.tipo == 'E'>
						Especialização
					</#if>
					<#if formac.tipo == 'D'>
						Doutorado
					</#if>
					<#if formac.tipo == 'P'>
						Pós Doutorado
					</#if>
	       		</#if>
	       	</li>
	       	<li class="liLeft" style="width:400px;"><span>Situação:</span>
		        <#if formac.situacao?exists>
		        	<#if formac.situacao == 'C'>
						Completo
					</#if>
					<#if formac.situacao == 'A'>
						Em Andamento
					</#if>
		        	<#if formac.situacao == 'I'>
						Incompleto
					</#if>
		        </#if>
			</li>
	       	<li><span>Conclusão:</span><#if formac.conclusao?exists> ${formac.conclusao}</#if></li>
	 	<ul>
		</td>
	</tr>
	    </#list>

	</#if>

	<#if candidato.candidatoIdiomas?exists && candidato.candidatoIdiomas?size != 0>
		<tr>
			<th>IDIOMAS</th>
		</tr>
		<tr>
		    <td>
		    <#list candidato.candidatoIdiomas as idioma>
		    <ul>
		        <li class="liLeft" style="width:400px;"><span>Nome:</span><#if idioma.idioma?exists && idioma.idioma.nome?exists> ${idioma.idioma.nome}</#if></li>
		        <li><span>Nível:</span>
		        	<#if idioma.nivel == 'A'>
						Avançado
					</#if>
					<#if idioma.nivel == 'I'>
						Intermediário
					</#if>
		        	<#if idioma.nivel == 'B'>
						Básico
					</#if>
		        </li>
		 	</ul>
		 	</#list>
		 	</td>
		</tr>
	</#if>

	<#if candidato.cursos?exists && candidato.cursos != "">
		<tr>
			<th>CURSOS</th>
		</tr>
		<tr>
		    <td>
		    	<ul>
					<li>${candidato.cursos}</li>
				</ul>
		 	</td>
		</tr>
	</#if>

	<#if candidato.experiencias?exists && candidato.experiencias?size != 0>
	<tr>
		<th>EXPERIÊNCIAS PROFISSIONAIS</th>
	</tr>
	    	<#list candidato.experiencias as exp>
	<tr>
		<td>
	  	<ul>
	    	<li><span>Empresa:</span> <#if exp.empresa?exists> ${exp.empresa}</#if></li>
	    	<li><span>Cargo/Função:</span><#if exp.nomeFuncao?exists> ${exp.nomeFuncao}</#if></li>
	    	<li style="width: 500px;">
	    		<span>Período:</span>
	    		<#if exp.dataAdmissao?exists> ${exp.dataAdmissao}
		    		<#if exp.dataDesligamento?exists> a ${exp.dataDesligamento} <#else>até o momento</#if>
		    		${exp.tempoExperiencia}
	    		</#if>
	    	</li>
	    	<li><span>Motivo da saída:</span> <#if exp.motivoSaida?exists> ${exp.motivoSaida}</#if></li>
	    	<li><span>Salário:</span> <#if exp.salario?exists> ${exp.salarioFormatado}</#if></li>
	    	
	       	<li><span>Observações:</span> <#if exp.observacao?exists> ${exp.observacao}</#if></li>
	 	</ul>
		</td>
	</tr>
	    </#list>

	</#if>

	<#if candidato.observacao?exists && candidato.observacao != "">
	<tr>
		<th>INFORMAÇÕES ADICIONAIS</th>
	</tr>
	<tr>
		<td>
	    	<li>${candidato.observacao}</li>
		</td>
	</tr>
	</#if>
	
	<#if candidato.observacaoRH?exists && candidato.observacaoRH != "">
	<tr>
		<th>OBSERVAÇÕES DO RH</th>
	</tr>
	<tr>
		<td>
	    	<li>${candidato.observacaoRH}</li>
		</td>
	</tr>
	</#if>
	
	<#if empresaSistema.campoExtraCandidato && camposExtras?exists>
		<tr>
			<th>EXTRA</th>
		</tr>
		<#list configuracaoCampoExtras as configuracaoCampoExtra>
			<tr>
				<td>
					<li>
						<span>${configuracaoCampoExtra.titulo}:</span>
						<#if configuracaoCampoExtra.nome == "texto1" && camposExtras.texto1?exists>
							${camposExtras.texto1}
						<#elseif configuracaoCampoExtra.nome == "texto2" && camposExtras.texto2?exists>
							${camposExtras.texto2}
						<#elseif configuracaoCampoExtra.nome == "texto3" && camposExtras.texto3?exists>
							${camposExtras.texto3}
						<#elseif configuracaoCampoExtra.nome == "texto4" && camposExtras.texto4?exists>
							${camposExtras.texto4}
						<#elseif configuracaoCampoExtra.nome == "texto5" && camposExtras.texto5?exists>
							${camposExtras.texto5}
						<#elseif configuracaoCampoExtra.nome == "texto6" && camposExtras.texto6?exists>
							${camposExtras.texto6}
						<#elseif configuracaoCampoExtra.nome == "texto7" && camposExtras.texto7?exists>
							${camposExtras.texto7}
						<#elseif configuracaoCampoExtra.nome == "texto8" && camposExtras.texto8?exists>
							${camposExtras.texto8}
						<#elseif configuracaoCampoExtra.nome == "textolongo1" && camposExtras.textolongo1?exists>
							${camposExtras.textolongo1}
						<#elseif configuracaoCampoExtra.nome == "textolongo2" && camposExtras.textolongo2?exists>
							${camposExtras.textolongo2}
						<#elseif configuracaoCampoExtra.nome == "data1" && camposExtras.data1?exists>
							${camposExtras.data1}
						<#elseif configuracaoCampoExtra.nome == "data2" && camposExtras.data2?exists>
							${camposExtras.data2}
						<#elseif configuracaoCampoExtra.nome == "data3" && camposExtras.data3?exists>
							${camposExtras.data3}
						<#elseif configuracaoCampoExtra.nome == "valor1" && camposExtras.valor1?exists>
							${camposExtras.valor1?string('###,###,###.00')}
						<#elseif configuracaoCampoExtra.nome == "valor2" && camposExtras.valor2?exists>
							${camposExtras.valor2?string('###,###,###.00')}
						<#elseif configuracaoCampoExtra.nome == "numero1" && camposExtras.numero1?exists>
							${camposExtras.numero1}
						</#if>
					 </li>
				</td>
			</tr>
		</#list>
	</#if>

</table>

<!--style>
	*{border:1px solid red}
</style-->

</body>
</html>