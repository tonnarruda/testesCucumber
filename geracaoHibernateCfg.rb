require 'rubygems'

path = ARGV[0]

hibernateCfg = File.new("#{path}/hibernate.cfg.xml","r+")

File.readlines("#{path}/hibernate.cfg.xml").each do |linha|
  linha = linha.sub("<property name=\"hibernate.show_sql\">true</property>", "<property name=\"hibernate.show_sql\">false</property>")  
  hibernateCfg.write linha
end

hibernateCfg.close
