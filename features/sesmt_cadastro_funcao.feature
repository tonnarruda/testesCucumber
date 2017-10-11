# language: pt

Funcionalidade: Funções

  Cenário: Cadastro de Funções
    Dado que exista o cargo "Contador"
    
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Funções"
    Então eu devo ver o título "Funções"
    E eu clico no botão "Inserir"
    Entao eu devo ver o título "Inserir Função"

    E eu preencho o campo (JS) "A partir de" com "02/02/2011"
    E eu preencho "Nome da Função" com "carregador"
    E eu preencho "Descrição das Atividades Executadas pela Função" com "descricao"
    E eu clico no botão "Gravar"
    Então eu devo ver "Função carregador cadastrada com sucesso."

    Entao eu clico em editar "carregador"
    E eu devo ver o título "Históricos da Função carregador"
    
    Entao eu clico em editar "02/02/2011"
    E eu preencho "Nome da Função" com "vendedor"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Históricos da Função vendedor"
    E eu clico no botão "Voltar"

    Então eu clico em excluir "vendedor"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Função excluída com sucesso."
