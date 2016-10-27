# language: pt

Funcionalidade: Cadastrar Áreas de Formação

  Cenário: Cadastro de Áreas de Formação
    Dado que eu esteja logado com o usuário "SOS"
    
    Quando eu acesso o menu "R&S > Cadastros > Áreas de Formação"
    Então eu devo ver o título "Áreas de Formação"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Nova Área de Formação"
    E eu clico no botão "Gravar"
    E eu aperto "OK"
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Áreas de Formação"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Nova Área de Formação"
    E eu preencho "nome" com "arae formação 1"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Áreas de Formação"
    Então eu devo ver "arae formação 1"
    E eu clico em editar "arae formação 1"
    E eu devo ver o título "Editar Área de Formação"
    E o campo "nome" deve conter "arae formação 1"
    E eu preencho "nome" com "arae formação 2"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Áreas de Formação"
    E eu clico em excluir "arae formação 2"
    Então eu devo ver "Confirma exclusão?"
    E eu aperto "OK"
    Então eu devo ver "Área de Formação excluída com sucesso."
    E eu não devo ver "arae formação 2"