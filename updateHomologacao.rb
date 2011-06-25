require 'rubygems'
require 'net/ssh'
require 'net/scp'

Net::SSH.start('10.1.3.48', 'web', :password => "123456") do |ssh|
	ssh.scp.upload!("/dist/updateHomologacao.sql", "/home/web/updateHomologacao.sql" ) do |channel, name, sent, total|
		print "\rUploading #{name}: #{(sent.to_f * 100 / total.to_f).to_i}%"
	end
	ssh.exec!("psql -U postgres fortesrhhomologacao < /home/web/updateHomologacao.sql") do |channel, stream, data|
		puts data
	end
end