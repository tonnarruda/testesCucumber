str = File.open("features/data/dumpInicial_data.sql", "r").read
tables = str.scan(/^COPY\s(.+?)\s/).flatten
tables_string = tables.join(',')

sql_truncate = "TRUNCATE #{tables_string}"

puts "### Limpando tabelas com: \n\n" + sql_truncate
out = `echo #{sql_truncate} | psql -U postgres fortesrh_test`
puts out

puts "### Restaurando dados iniciais:\n\n"
puts `psql -U postgres fortesrh_test < features/data/dumpInicial_data.sql`

#TODO: 
#
# - Chamar este script no After do env.rb
# - Ver se eh melhor colocar um if para executa-lo apenas se der erro ou se o usu�rio for�ar o clean quando o pr�prio teste n�o "desfazer o que faz"
# - Scritps para criar os dumps a partir do banco inicial
#      pg_dump -U postgres -a fortesrh_inicial > dumpInicial_data.sql
#      pg_dump -U postgres -s fortesrh_inicial > dumpInicial_schema.sql
