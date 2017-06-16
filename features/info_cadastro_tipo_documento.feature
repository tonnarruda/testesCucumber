# language: pt

Funcionalidade: Tipo do Documento

  Cenário: Cadastro de Tipo do Documento |Validar Campo Obbrigatório|
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Tipo do Documento"
     Então  eu clico no botão "Inserir"
        E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
        E eu clico no botão "Voltar"


  Cenário: Cadastro de Tipo do Documento
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Tipo do Documento"
     Então  eu clico no botão "Inserir"
        E eu preencho "Descrição" com "Documento Teste"
        E eu clico no botão "Gravar"


  Cenário: Editar Cadastro de Tipo do Documento
      Dado que exista um documento "Documento Teste"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Tipo do Documento"
     Então  eu clico em editar "Documento Teste"
        E eu preencho "Descrição" com "Documento Teste Anexado"
        E eu clico no botão "Gravar"

  Cenário: Excluir Cadastro de Tipo do Documento
      Dado que exista um documento "Documento Teste"
      Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "Info. Funcionais > Cadastros > Tipo do Documento"
     Então  eu clico em excluir "Documento Teste"
         E eu devo ver o alert do confirmar exclusão e clico no ok
         E eu devo ver "Tipo do Documento excluído com sucesso."





