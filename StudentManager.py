class StudentManager(object):
	def _send_response(self):
		'''
			response the request of CRUD operation on
			the course
		'''
	def _comsume_message(self, message):
		'''
			private function where called 
			in start to consume message in the
			message queue
		'''
	def _send_message(self, message):
		'''
			private function where called 
			in start to send message to 
			the message queue in  Student server
		'''

	def start(self):
		'''
			Where we should write the server handler
			and implement a socket to listen to requests
		'''

	def __init__(self, config):
		'''
			initialize the configuration read from 
			config file
		'''