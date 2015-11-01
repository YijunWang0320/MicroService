from flask import Flask, request, url_for, request, redirect, json
import requests
import urllib
app = Flask(__name__)
app.config["DEBUG"] = True

@app.route("/")
def hello():
    return "Hello, world!"

@app.route("/api/<method_name>/", methods = ["GET", "POST"])
def parse_api(method_name):
    method_list = ["AddStudent", "DeleteStudent", "GetStudentInfo", "UpdateStudentInfo"]

    if method_name not in method_list:
        return "Error: method name %s is not valid!" %method_name

    if request.method == 'POST':
        student_name = request.args.get('student_name', '')
        form_data = {"student_name" : student_name}
        param = json.dumps(form_data)
        return redirect(url_for('dispaly_redirect_result', data = param), code=307)
    else:
        return "Error: request method is not POST!"

@app.route('/redirect', methods = ["GET", "POST"])
def dispaly_redirect_result():
    if request.method == 'POST':
        json_data = request.args.get('data', '')
        json_data = json.loads(str(json_data))

        student_name = json_data["student_name"]
        print "Student name :" + student_name
        
        # shard based on first letter of student name
        index = (ord(student_name[0]) - 97) / 9
        return "Student: %s will be direct to student service # %d/3" %(student_name, index)
    else:
        return "Error: request method is not POST!"






if __name__ == "__main__":
    app.run()