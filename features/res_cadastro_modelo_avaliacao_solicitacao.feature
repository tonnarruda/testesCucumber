# language: pt

Funcionalidade: Cadastrar Modelos de Avaliação de Solicitação

  Cenário: Cadastro de Modelos de Avaliação de Solicitação
    Dado que eu esteja logado
    Quando eu acesso o menu "R&S > Cadastros > Modelos de Avaliação do Candidato"
    Então eu devo ver o título "Modelos de Avaliação de Solicitação"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Modelo de Avaliação"
    E eu clico no botão "Avancar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Modelos de Avaliação de Solicitação"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Modelo de Avaliação"
    E eu preencho "Título" com "Prova Java"
    E eu clico no botão "Avancar"
    E eu devo ver o título "Inserir Critério de Avaliação"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    E eu devo ver o título "Critérios de Avaliação"
    E eu clico no botão "Inserir"

    Então eu devo ver o título "Inserir Critério de Avaliação"
    E eu preencho "aspecto" com "Programação"
    E eu preencho "criterio" com "O que é O O?"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Inserir Critério de Avaliação"
    Então eu devo ver "Critério gravado com sucesso."
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Critérios de Avaliação"
    Então eu devo ver "O que é O O?"
    Então eu clico em editar "O que é O O?"
    Então eu devo ver o título "Editar Critério de Avaliação"
    E o campo "aspecto" deve conter "Programação"
    E o campo "criterio" deve conter "O que é O O?"
    E eu preencho "criterio" com "O que é DAO?"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Critérios de Avaliação"
    E eu devo ver "O que é DAO?"

    Então eu clico em excluir "O que é DAO?"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver o título "Critérios de Avaliação"
    Então eu devo ver "Critério excluído com sucesso."
    E eu não devo ver "O que é DAO?"
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Modelos de Avaliação de Solicitação"
    Então eu devo ver "Prova Java"
    Então eu clico em excluir "Prova Java"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver " possui dependências em "
    E eu clico na linha "Prova Java" da imagem "Aspectos"
    Então eu devo ver o título "Aspectos da Avaliação"
    Então eu clico em excluir "Programação"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Aspecto excluído com sucesso."
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Modelos de Avaliação de Solicitação"
    Então eu clico em excluir "Prova Java"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Modelo de Avaliação excluído com sucesso."
