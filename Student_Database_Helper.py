import MySQLdb

class Student_Database_Helper:
	def __init__(self):
		# Open database connection
		self.db = MySQLdb.connect("localhost","testuser","test123","TESTDB" )
		# Prepare a cursor object using cursor() method
		self.cursor = db.cursor()
		# Prepare a empty query
		self.query = ""
		# Prepare a result
		self.result;


	def __init__(self, host, user, password, dbname):
		# Open database connection
		self.db = MySQLdb.connect(host, user, password, dbname)
		# Prepare a cursor object using cursor() method
		self.cursor = db.cursor()
		# Prepare a empty query
		self.query = ""
		# Prepare a result
		self.result;



	def read(self, query):
		self.query = query
		try:
			if self.query is None or len(self.query) < 1:
				raise Exception("error", "Invalid query input")
			self.cursor.execute(self.query)
			self.result = cursor.fetchall()
			for row in result:
				student_name = row[0]
				student_id = row[1]

			# Print fetched result
			print "Student name =%s,student id =%s" % (student_name, student_id)

		except:
			print "Unable to fetch data from database."


	def update(self, query):
		self.query = query
		try:
			if self.query is None or len(self.query) < 1:
				raise Exception("error", "Invalid query input")
			self.cursor.execute(self.query)
			self.db.commit()

		except:
			db.rollback()



	def delete(self, query):
		self.query = query
		try:
			if self.query is None or len(self.query) < 1:
				raise Exception("error", "Invalid query input")
			self.cursor.execute(self.query)
			self.db.commit()

		except:
			db.rollback()













