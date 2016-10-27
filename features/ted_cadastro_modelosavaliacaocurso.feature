# language: pt

Funcionalidade: Cadastrar Modelos de Avaliação de Turma

  Cenário: Modelos de Avaliação de Turma
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "T&D > Cadastros > Modelos de Avaliação de Curso"
    Então eu devo ver o título "Modelos de Avaliação de Curso"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Modelo de Avaliação de Curso"
    E eu clico no botão "Avancar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Modelos de Avaliação de Curso"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Modelo de Avaliação de Curso"
    E eu preencho "Título" com "oratória"
    E eu preencho "Observação" com "observações"
    E eu seleciono "Sim" de "Ativa"
    E eu clico no botão "Avancar"
    E eu devo ver o título "Avaliação da Turma - oratória"

    E eu clico "Inserir pergunta aqui"

    Então eu devo ver "Inserir Pergunta"
    E eu clico no botão "Gravar"
    E eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    E eu devo ver "Avaliação da Turma - oratória"
    Então eu clico "Inserir pergunta aqui"

    Então eu devo ver "Inserir Pergunta"
    E eu preencho "aspecto" com "familiar"
    E eu preencho "Pergunta" com "Quantos filhos?"
    E eu seleciono "Subjetiva" de "Tipos de Respostas"
    E eu clico no botão "Gravar"
    Então eu devo ver "Avaliação da Turma - oratória"

    Entao eu clico no botão "Voltar"
    E eu devo ver o título "Modelos de Avaliação de Curso"

    Entao eu clico em editar "oratória"
    E eu devo ver o título "Editar Modelo de Avaliação de Curso"
    E o campo "Título" deve conter "oratória"
    E eu preencho "Título" com "contabilidade"
    E eu clico no botão "Avancar"
    E eu devo ver o título "Avaliação da Turma - contabilidade"

    Então eu clico na imagem "Excluir" da pergunta "Quantos filhos?"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu não devo ver "Quantos filhos?"
    E eu clico no botão "Voltar"
    E eu devo ver o título "Modelos de Avaliação de Curso"

    Então eu clico na linha "contabilidade" da imagem "Clonar"
    E eu devo ver "Clonar: contabilidade"
    E eu marco "Empresa Padrão"
    E eu clico no botão "Clonar"
    E eu devo ver "contabilidade (Clone)"

    Então eu clico em excluir "contabilidade (Clone)"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Modelo de avaliação de curso excluído com sucesso."
    E eu não devo ver "contabilidade (Clone)"

    Então eu clico em excluir "contabilidade"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Modelo de avaliação de curso excluído com sucesso."
    E eu não devo ver "contabilidade"