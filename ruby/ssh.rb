def connect ip, user, password
	Net::SSH.start(ip, user, :password => password.to_s) do |ssh|
		yield Connection.new ssh
	end
end

class Connection
	def initialize ssh
		@ssh = ssh
	end
	
	def exec cmd, options={}
		options[:output] ||= :console
		@ssh.exec! cmd do |channel, stream, data|
			puts data if options[:output] == :console
		end	
	end
	
	def upload source, dest
		@ssh.scp.upload! source, dest do |channel, name, sent, total|
			print "\rUploading #{name}: #{(sent.to_f * 100 / total.to_f).to_i}%"
		end
		puts
	end

	def create_file file, options={}
		options[:content] ||= ""
		@ssh.scp.upload! StringIO.new(options[:content]), file do |channel, name, sent, total|
			print "\rCreating file #{name}: #{(sent.to_f * 100 / total.to_f).to_i}%"
		end
		puts
	end
	
end