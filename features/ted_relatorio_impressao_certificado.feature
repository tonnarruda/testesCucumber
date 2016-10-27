# language: pt

Funcionalidade: Relatório de Lista de Frequência

  Cenário: Relatório de Lista de Frequência
    Dado que exista um curso "java"
    Dado que exista uma turma "manha" para o curso "java" realizada
    Dado que exista um colaborador "xica", da area "putaria", com o cargo "puta" e a faixa salarial "1"
    Dado que exista uma certificacao "cert. java" para o curso "java"

    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "T&D > Cadastros > Cursos/Treinamentos"
    E eu clico na linha "java" da imagem "Turmas"
    E eu clico na linha "manha" da imagem "Colaboradores Inscritos"
    E eu clico no botão "IncluirColaboradores"
    E eu clico no botão "Pesquisar"
    E eu clico no botão "InserirSelecionados"

    Quando eu acesso o menu "T&D > Relatórios > Certificados"
    Então eu devo ver o título "Impressão de Certificado"
    E eu seleciono "java" de "Curso"
    E eu seleciono "manha" de "Turma"
    E eu clico no botão "Pesquisar"

    Entao eu clico no botão "ImprimirPdf"
    E eu devo ver o alert "Preencha os campos indicados." e clico no ok