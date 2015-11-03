from flask import Flask, url_for, request, redirect, json
from RouterConfig import RouterConfig
app = Flask(__name__)
app.config["DEBUG"] = True
router_config = RouterConfig("service.cfg")

# get all the allowed api
method_list = router_config.get_all_api()
@app.route("/", methods=["GET", "POST"])
def hello():
    return "The Router for Student Micro-service"

@app.route("/api/<method_name>/", methods=["GET", "POST"])
def parse_api(method_name):

    if method_name not in method_list:
        return "Error: method name %s is not valid!" % method_name

    if request.method == 'POST':
        param_dict = generate_param_dict(request.query_string)
        student_name = param_dict["student_name"]

        if student_name is None:
            return "Error: student name is null and the request could not be shard!"

        # shard the request to different service
        shard_url = get_shard_url(student_name)
        param = json.dumps(param_dict)
        url = shard_url + build_url_based_on_api(method_name, param)
        response = redirect(url, code=307)

        return response
    else:
        return "Error: request method is not POST!"


'''
shard the request based on student name
return: url of shard host

'''


def get_shard_url(student_name):
    service_list = router_config.get_all_services()
    service_amount = len(service_list)

    # shard based on first letter of student name, 'a' is 97
    index = (ord(student_name[0]) - 97) / (26 / service_amount + 1)
    print "Student: %s will be direct to student service # %d/%d" % (student_name, index, service_amount)
    return service_list[index]


'''
Generate dict according the query string of post request
return: {u'DOB': u'1991', u'student_name': u'wangyijun', u'major': u'CS'}
'''


def generate_param_dict(query_string):
    query = query_string.split("&")
    param_dict = dict()
    for pair in query:
        pair = pair.split("=")
        param_dict[pair[0]] = pair[1]
    return param_dict


'''
Generate url based on the api name and params

'''


def build_url_based_on_api(method_name, param):

    data = url_for('display_redirect_result', data=param).split("?")[-1]
    url = "/student/%s/?%s" % (method_name, data)

    return url


@app.route('/redirect', methods=["GET", "POST"])
def display_redirect_result():
    if request.method == 'POST':
        json_data = str(request.args.get('data', ''))
        json_data = json.loads(json_data)

        student_name = json_data["student_name"]

        student_id = json_data["student_id"]
        if len(student_id) > 0:
            student_id = int(student_id)
        else:
            student_id = 0
        print "Student name :" + student_name + "Student ID: %d" % student_id

        # shard based on first letter of student name, 'a' is 97
        index = (ord(student_name[0]) - 97) / 9
        return "Student: %s (ID: %d) will be direct to student service # %d/3" % (student_name, student_id, index)
    else:
        return "Error: request method is not POST!"

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5000)