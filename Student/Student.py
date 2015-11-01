class Student(object):
	def __init__(self, input_id, input_name):
		self._id = input_id
		self._name = input_name

	def get_id(self):
		return self._id

	def get_name(self):
		return self._name

	def set_id(self, input_id):
		self._id = input_id

	def set_name(self, input_name):
		self._name = input_name