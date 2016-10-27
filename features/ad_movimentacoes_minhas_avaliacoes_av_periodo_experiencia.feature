# language: pt

Funcionalidade: Minhas Avaliações - Período de Experiência

  Cenário: Minhas Avaliações - Período de Experiência
    Dado que exista um colaborador "Samuel", da area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "3"
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Dias do Acompanhamento do Período de Experiência"
    Então eu devo ver o título "Períodos de Acompanhamento de Experiência"
    E eu clico no botão "Inserir"

    Então eu devo ver o título "Inserir Período de Acompanhamento de Experiência"
    E eu preencho "Qtd. de Dias" com "90"
    E eu preencho "Descrição" com "1ª Avaliação"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Períodos de Acompanhamento de Experiência"
    E eu devo ver "90"

    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Avaliações de Desempenho/Acomp. do Período de Experiência"
    Então eu devo ver o título "Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência"
    E eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Modelo de Avaliação"
    E eu preencho "Título" com "Av. período experiência"
    E eu seleciono "Acompanhamento do Período de Experiência" de "Tipo de Avaliação"
    E eu seleciono "90 - 1ª Avaliação" de "Períodos de Acompanhamento de Experiência"
    E eu clico no botão "Avancar"

    Então eu devo ver o título "Inserir Pergunta da Avaliação"
    E eu seleciono "1" de "Ordem"
    E eu preencho "pergunta" com "_pergunta 1"
    E eu preencho "peso" com "1"
    E eu seleciono "Nota" de "Tipo de Resposta"
    E eu preencho "notaMinima" com "5"
    E eu preencho "notaMaxima" com "10"
    E eu clico no botão "Gravar"
    E eu devo ver "Pergunta gravada com sucesso."

    Quando eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
    Então eu clico na linha "Samuel" da imagem "Criar Acesso ao Sistema"
    E eu preencho "Nome" com "Samuel"
    E eu preencho "Login" com "samuel"
    E eu preencho "Senha" com "1234"
    E eu preencho "Confirmar Senha" com "1234"
    E eu seleciono (JS) "1" de "selectPerfil_1"
    E eu clico no botão "Gravar"

    Quando eu acesso o menu "Sair"
    Dado que eu esteja logado com o usuário "samuel"

    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Acompanhamento do Período de Experiência"
    E eu clico no botão "Pesquisar"
    E eu seleciono "Samuel - 123.213.623-91" de "Colaborador"
    E eu espero 2 segundos
    E eu clico no botão "Inserir"

    Então eu devo ver o título "Inserir Acompanhamento do Período de Experiência"
    E eu seleciono "Av. período experiência" de "Avaliação do Período de Experiência"
    E eu preencho "Data" com "15/04/2014"
    E eu seleciono "8" de "Selecione a nota de 5 a 10"
    E eu clico no botão "Gravar"

    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Minhas Avaliações"
    E eu devo ver o título "Respostas das Minhas Avaliações"
    E eu clico em editar "Av. período experiência"
    E eu devo ver "Editar Acompanhamento do Período de Experiência"
    E eu clico no botão "Cancelar"
    E eu devo ver o título "Respostas das Minhas Avaliações"

    Então eu clico em editar "Av. período experiência"
    E eu seleciono "6" de "Selecione a nota de 5 a 10"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Respostas das Minhas Avaliações"
    E eu devo ver "Avaliação respondida com sucesso."

    Quando eu acesso o menu "Sair"
