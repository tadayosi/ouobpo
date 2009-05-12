class Hello:
	def __init__(self, hello):
		self.hello = hello
	def __str__(self):
		return self.hello

print Hello("hello.")