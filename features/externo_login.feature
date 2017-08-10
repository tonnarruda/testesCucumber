# language: pt

Funcionalidade: Login no modulo externo
 
  @dev
  Cenário: Cadastro de Candidato - Preenchimento de Todos os Campos
         Dado que exista um conhecimento "Cucumber"
         Dado que exista a área organizacional "DEV"
         Dado que exista o cargo "Desenvolvedor" na área organizacional "DEV"
       Quando eu acesso "externo/prepareLogin.action?empresaId=1"
            E eu clico "Quero me cadastrar"
        Então eu preencho "Nome" com "Boris Johnson"
            E eu preencho o campo (JS) "Nascimento" com "01/01/1987"
            E eu seleciono "Doutorado completo" de "Escolaridade"
            E eu preencho campo pelo id "naturalidade" com "São Paulo"
            E eu preencho o campo (JS) "CPF" com "123.213.623-91"
            E eu preencho "CEP" com "60811-690"
            E eu saio do campo "CEP"
            E eu espero 2 segundos
            E o campo "Logradouro" não deve conter "Rua Desembargador Floriano Benevides Magalhães"
            E o campo "Logradouro" deve conter "Rua Desembargador Floriano Benevides Mag"
            E eu preencho "CEP" com ""
            E eu preencho "Logradouro" com "Avenida João Pessoa"
            E eu preencho "num" com "4901"
            E eu preencho "Complemento" com "Apto 105"
            E eu seleciono "CE" de "Estado"
            E eu seleciono "Fortaleza" de "Cidade"
            E eu preencho "Bairro" com "Damas"
            E eu preencho "E-mail" com "email@teste.com.br"
            E eu preencho "DDD" com "85"
            E eu preencho "Telefone" com "88438383"
            E eu preencho "Celular" com "88438309"
            E eu preencho "Contato" com "Maria"
            E eu preencho "Parentes/Amigos na empresa" com "Pedro Junior"
            E eu seleciono "Solteiro" de "Estado Civil"
            E eu preencho "Qtd. Filhos" com "1"
            E eu preencho "Nome do Cônjuge" com "Pilar Nuñes"
            E eu preencho "Profissão do Cônjuge" com "Agente de Turismo"
            E eu preencho "Nome do Pai" com "Tony Blair"
            E eu preencho "Profissão do Pai" com "Administrador"
            E eu preencho "Nome da Mãe" com "Joanna Prado"
            E eu preencho "Profissão da Mãe" com "Advogada"
            E eu seleciono "Sim" de "Paga Pensão"
            E eu preencho campo pelo id "quantidadeId" com "2"
            E eu preencho "Valor" com "850,00"
            E eu seleciono "Sim" de "Possui Veículo"
            E eu seleciono "Reabilitado" de "Deficiência"
            E eu preencho "Senha" com "1234"
            E eu preencho "Confirmar Senha" com "1234"
        Então eu clico na aba "FORMAÇÃO ESCOLAR"
            E eu clico no botão de Id "inserirFormacao"
            E eu preencho "formacao.curso" com "Mestrado em Contabilidade Pública"
            E eu seleciono "Artes" de "formacao.areaFormacao.id"
            E eu preencho "Instituição de ensino" com "Universidade de Fortaleza"
            E eu seleciono "Doutorado" de "Tipo"
            E eu seleciono "Em andamento" de "Situação"
            E eu preencho "Conclusão" com "2017"
            E eu clico no botão de Id "frmFormacao_0"
            E eu clico no botão de Id "inserirIdioma"
            E eu seleciono "Inglês" de "Idioma"
            E eu seleciono "Avançado" de "Nível"
            E eu clico no botão de Id "frmIdioma_0"
            E eu preencho "Curso" com "Contabilidade Geral"
        Então eu clico no botão de Id "avancar"
            E eu marco "Cucumber"
            E eu seleciono "Sócio" de "Colocação"
            E eu preencho "Pretensão Salarial" com "3335,28"
        Então eu clico no botão de Id "avancar"
            E eu clico no botão "Inserir"                
            E eu preencho "Empresa" com "Fortes Tecnologia"
            E eu preencho campo pelo id "contatoEmpresa" com "8540051111"
            E eu seleciono "Desenvolvedor" de "Cargo"
            E eu preencho o campo (JS) "Data de Admissão" com "08/09/2008"
            E eu preencho "Observações" com "Nada a declarar"
            E eu clico no botão de Id "frmExperiencia_0"
            E eu preencho "Informações Adicionais" com "Nada a declarar"
        Então eu clico no botão de Id "avancar"
            E eu preencho "RG" com "06060722334"
            E eu preencho "Órgão Emissor" com "SSPDC"
            E eu seleciono "CE" de "Estado"
            E eu preencho o campo (JS) "Data de Expedição" com "08/09/2008"
        Então eu clico no botão de Id "avancar"
            E eu preencho "Descrição do Currículo" com "Descrição do Currículo"
        Então eu clico no botão "Gravar"
           E eu devo ver o alert "Dados cadastrados com sucesso." e clico no ok

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Cadastro de Candidato - Validar Campos Obrigatórios
       Quando eu acesso "externo/prepareLogin.action?empresaId=1"
            E eu clico "Quero me cadastrar"
            E eu clico na aba "CURRÍCULO"
        Então eu clico no botão "Gravar"
            E eu devo ver o alert "Senha obrigatória para acesso ao modulo exteno." e clico no ok
        E eu clico na aba "DADOS PESSOAIS"
            E eu preencho "Senha" com "1234"
            E eu clico na aba "CURRÍCULO"
            E eu clico no botão "Gravar"
            E eu devo ver o alert "Confirmação de senha não confere com a senha inserida." e clico no ok
            E eu clico na aba "DADOS PESSOAIS"
            E eu preencho "Confirmar Senha" com "1234"
            E eu clico na aba "CURRÍCULO"
            E eu clico no botão "Gravar"
            E eu devo ver o alert "Preencha os campos indicados:" e clico no ok
            E eu clico na aba "DADOS PESSOAIS"
        Então eu preencho "Nome" com "Boris Johnson"
            E eu preencho o campo (JS) "CPF" com "123.213.623-91"
            E eu clico na aba "CURRÍCULO"
            E eu clico no botão "Gravar"
            E eu devo ver o alert "Preencha os campos indicados:" e clico no ok
        Então eu clico na aba "DADOS PESSOAIS"
            E eu seleciono "Doutorado completo" de "Escolaridade"
            E eu preencho "num" com "4901"
            E eu seleciono "CE" de "Estado"
            E eu seleciono "Fortaleza" de "Cidade"
            E eu preencho "Telefone" com "88438383"
        Então eu clico na aba "CURRÍCULO"
            E eu clico no botão "Gravar"
            E eu devo ver o alert "Dados cadastrados com sucesso." e clico no ok

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Visualizar Vagas Disponíveis
       Quando eu acesso "externo/prepareLogin.action?empresaId=1"
            E eu clico "Visualizar oportunidades"

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Editar Cadastro de Candidato
         Dado que exista um candidato "Ellen Pompeo"
       Quando eu acesso "externo/prepareLogin.action?empresaId=1"
            E eu clico no botão "Entrar"   
        Então eu devo ver o alert do valida campos e clico no ok
            E eu preencho o campo (JS) "cpfRH" com "060.607.223-34"
            E eu preencho "senhaRH" com "1234"
            E eu clico no botão "Entrar"
        Então eu devo ver "Bem vindo(a)"
       Quando eu clico "Editar Currículo"
            E eu seleciono "Doutorado completo" de "Escolaridade"
            E eu preencho "num" com "4901"
            E eu seleciono "CE" de "Estado"
            E eu seleciono "Fortaleza" de "Cidade"
            E eu preencho "Telefone" com "88438383"
        Então eu clico na aba "CURRÍCULO"
            E eu clico no botão "Gravar"
      
#------------------------------------------------------------------------------------------------------------------------

  Cenário: Alterar Senha de usuário logado
         Dado que exista um candidato "Ellen Pompeo"
       Quando eu acesso "externo/prepareLogin.action?empresaId=1"
            E eu preencho o campo (JS) "cpfRH" com "060.607.223-34"
            E eu preencho "senhaRH" com "1234"
            E eu clico no botão "Entrar"
        Então eu devo ver "Bem vindo(a)"
       Quando eu clico "Alterar Senha"
            E eu preencho "senha" com "1234"
            E eu preencho "novaSenha" com "12345"
            E eu preencho "confSenha" com "12345"
            E eu clico no botão "Gravar" 
        Então eu clico "Sair"   
            E eu preencho o campo (JS) "cpfRH" com "060.607.223-34"
            E eu preencho "senhaRH" com "1234"
            E eu clico no botão "Entrar"
            E eu devo ver o alert "Senha não confere." e clico no ok 
            E eu preencho "senhaRH" com "12345"
            E eu clico no botão "Entrar"  

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Alterar Senha de usuário - Esqueci minha senha | Usuário sem email Associado |
         Dado que exista um candidato "Ellen Pompeo"
       Quando eu acesso "externo/prepareLogin.action?empresaId=1"
             E eu clico "Esqueci minha senha"
             E eu preencho "cpf" com "999.999.999-11"
             E eu clico no botão "Enviar"
             E eu clico no botão de Id "popup_ok"
             E eu preencho "cpf" com "387.717.179-60"
             E eu clico no botão "Enviar"
         Então eu devo ver "Candidato não localizado!"
             E eu preencho "cpf" com "060.607.223-34"
             E eu clico no botão "Enviar"
         Então eu devo ver "Candidato não possui email cadastrado! Por favor entre em contato com a empresa."  

#------------------------------------------------------------------------------------------------------------------------

  Cenário: Alterar Senha de usuário - Esqueci minha senha | Usuário COM email Associado |
         Dado que exista um candidato "Ellen Pompeo"
       Quando eu acesso "externo/prepareLogin.action?empresaId=1"
            E eu preencho o campo (JS) "cpfRH" com "060.607.223-34"
            E eu preencho "senhaRH" com "1234"
            E eu clico no botão "Entrar"
        Então eu devo ver "Bem vindo(a)"
       Quando eu clico "Editar Currículo"
            E eu seleciono "Doutorado completo" de "Escolaridade"
            E eu preencho "num" com "4901"
            E eu seleciono "CE" de "Estado"
            E eu seleciono "Fortaleza" de "Cidade"
            E eu preencho "Telefone" com "88438383"
            E eu preencho "E-mail" com "email@teste.com.br"
        Então eu clico na aba "CURRÍCULO"
            E eu clico no botão "Gravar"
        Então eu clico "Sair"
            E eu clico "Esqueci minha senha" 
            E eu preencho "cpf" com "060.607.223-34"
             E eu clico no botão "Enviar"  
        Então eu devo ver "Nova Senha enviada por e-mail (email@teste.com.br)."      

#------------------------------------------------------------------------------------------------------------------------
@teste
  Cenário: Acessar modulo externo com login e senha com 2 candidatos com mesmo CPF
         Dado que exista um candidato "Ellen Pompeo"
         Dado que exista um candidato "Sandra Oh"
       Quando eu acesso "externo/prepareLogin.action?empresaId=1"
            E eu preencho o campo (JS) "cpfRH" com "060.607.223-34"
            E eu preencho "senhaRH" com "1234"
            E eu clico no botão "Entrar"
        Então eu devo ver "Bem vindo(a)"