# language: pt

Funcionalidade: Funções

  Cenário: Cadastro de Funções
    Dado que exista o cargo "Contador"
    
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Funções"
    Então eu devo ver o título "Funções"
    E eu seleciono "Contador" de "Cargo"
    E eu clico no botão "Pesquisar"
    E eu clico no botão "Inserir"
    Entao eu devo ver o título "Inserir Função - Contador"

    E eu preencho o campo (JS) "A partir de" com "02/02/2011"
    E eu preencho "Nome da Função" com "carregador"
    E eu preencho "Descrição das Atividades Executadas pela Função" com "descricao"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Funções - Contador"
    E eu devo ver "carregador"

    Entao eu clico em editar "carregador"
    E eu devo ver o título "Editar Função - Contador"
    E o campo "Nome da Função" deve conter "carregador"
    E eu preencho "Nome da Função" com "vendedor"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Funções - Contador"
    Então eu devo ver "vendedor"

    Então eu clico em excluir "vendedor"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Função excluída com sucesso."
