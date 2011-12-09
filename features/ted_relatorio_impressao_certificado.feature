# language: pt

Funcionalidade: Relatório de Lista de Frequência

  Cenário: Relatório de Lista de Frequência
    Dado que exista um curso "java"
    Dado que exista uma turma "manha" para o curso "java" realizada
    Dado que exista uma certificacao "cert. java" para o curso "java"

    Dado que eu esteja logado
    
    Quando eu acesso o menu "T&D > Relatórios > Certificados"
    Então eu devo ver o título "Impressão de Certificado"
    E eu seleciono "java" de "Curso"
    E eu seleciono "manha" de "Turma"
    E eu clico no botão "Pesquisar"

    E eu devo ver "Não existem Colaboradores aprovados."