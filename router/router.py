from flask import Flask, request, url_for, request, redirect, json
import requests
import urllib
app = Flask(__name__)
app.config["DEBUG"] = True

@app.route("/")
def hello():
    return "Hello, world!"

@app.route("/api/<method_name>/", methods=["GET", "POST"])
def parse_api(method_name):
    method_list = ["add", "search", "update", "delete"]

    if method_name not in method_list:
        return "Error: method name %s is not valid!" %method_name

    if request.method == 'POST':
        student_name = request.args.get('student_name', '')
        student_id = request.args.get('student_id', '')
        '''
        the data of request
        '''
        form_data = {"student_name": student_name,
                     "student_id": student_id}

        param = json.dumps(form_data)
        # url_for('dispaly_redirect_result', data = param)
        url = "http://127.0.0.1:5000" + build_url_based_on_api(method_name, param)
        print url
        return redirect(url, code=307)
    else:
        return "Error: request method is not POST!"


'''
Generate url based on the api name and params

'''


def build_url_based_on_api(method_name, param):
    api_list = ["add", "search", "update", "delete"]
    data = url_for('display_redirect_result', data=param).split("?")[-1]

    url = "/student/%s/?%s" % (method_name, data)
    return url


@app.route('/redirect', methods=["GET", "POST"])
def display_redirect_result():
    if request.method == 'POST':
        json_data = request.args.get('data', '')
        json_data = json.loads(str(json_data))

        student_name = json_data["student_name"]

        student_id = json_data["student_id"]
        if len(student_id) > 0:
            student_id = int(student_id)
        else:
            student_id = 0
        print "Student name :" + student_name + "Stundet ID: %d" %student_id

        # shard based on first letter of student name, 'a' is 97
        index = (ord(student_name[0]) - 97) / 9
        return "Student: %s (ID: %d) will be direct to student service # %d/3" % (student_name, student_id, index)
    else:
        return "Error: request method is not POST!"

if __name__ == "__main__":
    app.run(port=5002)