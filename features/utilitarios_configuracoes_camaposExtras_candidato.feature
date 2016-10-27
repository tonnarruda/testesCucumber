# language: pt

Funcionalidade: Campos Extras para Candidato

  @dev
  Cenário: Campos Extras para Candidato
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "R&S > Cadastros > Candidatos"
    E eu clico no botão "Inserir"
    Então eu não devo ver a aba "EXTRA"

    Quando eu acesso o menu "Utilitários > Configurações > Campos Extras"
    Então eu devo ver o título "Configurações de Campos Extras"
    Quando eu marco "Habilitar campos extras no cadastro de Candidato"
    Quando eu marco "configuracaoCampoExtras[0].ativoCandidato"
    E eu preencho "configuracaoCampoExtras[0].titulo" com "Nome da mãe"
    E eu clico no botão "Gravar"
    Então eu devo ver "Essas configurações serão aplicadas para todas as empresas!"
    Quando eu aperto "OK"

    Quando eu acesso o menu "Utilitários > Configurações > Configurar Cadastro de Colaborador e Candidato > Campos extras"
    Entao eu clico na aba candidato de campo extra
    E eu espero 2 segundos
    E eu marco texto 1 da aba candidato
    E eu clico no botão "Gravar"

    Quando eu acesso o menu "R&S > Cadastros > Candidatos"
    E eu clico no botão "Inserir"
    Então eu devo ver a aba "EXTRA"
    Quando eu preencho "nome" com "_Pedro do Teste"
    E eu seleciono "Analfabeto, inclusive o que, embora tenha recebido instrução, não se alfabetizou" de "Escolaridade"
    E eu preencho "Logradouro" com "Eretides"
    E eu preencho "num" com "11"
    E eu seleciono "CE" de "Estado"
    E eu seleciono "Fortaleza" de "cidade"
    E eu preencho "DDD" com "85"
    E eu preencho "Telefone" com "88438383"
    Quando eu clico na aba "EXTRA"
    E eu preencho "Nome da mãe" com "Maria"
    E eu clico no botão "Gravar"
    Então eu devo ver "Operação efetuada com sucesso"
    Quando eu clico no botão "Voltar"
    E eu devo ver "_Pedro do Teste"

    Quando eu clico em editar "_Pedro do Teste"
    Então o campo "Nome" deve conter "_Pedro do Teste"
    Quando eu clico na aba "EXTRA"
    Então o campo "Nome da mãe" deve conter "Maria"
    Quando eu clico no botão "Gravar"

    Então eu devo ver "_Pedro do Teste"

    Quando eu clico em excluir "_Pedro do Teste"
    Então eu devo ver "Deseja realmente excluir o candidato _Pedro do Teste?"
    Quando eu aperto "OK"
    E eu não devo ver "_Pedro do Teste"

    Quando eu acesso o menu "Utilitários > Configurações > Campos Extras"
    Então eu devo ver o título "Configurações de Campos Extras"
    E eu preencho "configuracaoCampoExtras[0].titulo" com ""
    Quando eu desmarco "configuracaoCampoExtras[0].ativoCandidato"
    Quando eu desmarco "Habilitar campos extras no cadastro de Candidato"
    E eu clico no botão "Gravar"
    Então eu devo ver "Essas configurações serão aplicadas para todas as empresas!"
    Quando eu aperto "OK"

    Quando eu acesso o menu "R&S > Cadastros > Candidatos"
    E eu clico no botão "Inserir"
    Então eu não devo ver a aba "EXTRA"