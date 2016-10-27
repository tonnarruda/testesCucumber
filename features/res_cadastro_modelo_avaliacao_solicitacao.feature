# language: pt

Funcionalidade: Cadastrar Modelos de Avaliação do Candidato

  Cenário: Cadastro de Modelos de Avaliação do Candidato
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "R&S > Cadastros > Modelos de Avaliação do Candidato"
    Então eu devo ver o título "Modelos de Avaliação do Candidato"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Modelo de Avaliação"
    E eu clico no botão "Avancar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Modelos de Avaliação do Candidato"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Modelo de Avaliação"
    E eu preencho "Título" com "Prova Java"
    E eu clico no botão "Avancar"
    E eu devo ver o título "Inserir Pergunta da Avaliação"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    E eu devo ver o título "Perguntas da Avaliação"
    E eu clico no botão "Inserir"

    Então eu devo ver o título "Inserir Pergunta da Avaliação"
    E eu preencho "aspecto" com "Programação"
    E eu preencho "pergunta" com "O que é O O?"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Inserir Pergunta da Avaliação"
    Então eu devo ver "Pergunta gravada com sucesso."
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Perguntas da Avaliação"
    Então eu devo ver "O que é O O?"
    Então eu clico em editar "O que é O O?"
    Então eu devo ver o título "Editar Pergunta da Avaliação"
    E o campo "aspecto" deve conter "Programação"
    E o campo "pergunta" deve conter "O que é O O?"
    E eu preencho "pergunta" com "O que é DAO?"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Perguntas da Avaliação"
    E eu devo ver "O que é DAO?"

    Então eu clico em excluir "O que é DAO?"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver o título "Perguntas da Avaliação"
    Então eu devo ver "Pergunta excluída com sucesso."
    E eu não devo ver "O que é DAO?"
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Modelos de Avaliação do Candidato"
    Então eu devo ver "Prova Java"
    Então eu clico em excluir "Prova Java"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Modelo de avaliação excluído com sucesso."
