str = File.open("features/data/dumpInicial_data.sql", "r").read
tables = str.scan(/^COPY\s(.+?)\s/).flatten
tables_string = tables.join(',')

sql_truncate = "TRUNCATE #{tables_string}"

puts "### Limpando tabelas com: \n\n" + sql_truncate
out = `echo #{sql_truncate} | psql -U postgres fortesrh_test`
puts out

puts "### Restaurando dados iniciais:\n\n"
puts `psql -U postgres fortesrh_test < features/data/dumpInicial_data.sql`
