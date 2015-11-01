import DatabaseUtil
import json
from Config import Config
from flask import Flask, request, g, redirect, url_for, \
    abort, render_template, session

app = Flask(__name__)
config = Config('student_settings.cfg')
app.config['DEBUG'] = True


@app.before_request
def before_request(exception=None):
    pass
    #g.db = DatabaseUtil.connect_db()


@app.teardown_request
def after_request(exception=None):
    db = getattr(g, 'db', None)
    if db is not None:
        db.close()


# REST APIs
@app.route("/test", methods=["POST"])
def hello():
    print request
    return "You are in the student service right now!"


@app.route("/student/search/", methods=["POST"])
def search():
    try:
        student_info = request.data
        info_dict = json.loads(student_info)
        assert info_dict is dict
        response = DatabaseUtil.search_student(g.db, info_dict['student_name'])
        return response
    except:
        return 'ERROR'


@app.route("/student/add/", methods=['POST'])
def add():
    try:
        student_info = request.data
        info_dict = json.loads(student_info)
        assert info_dict is dict
        response = DatabaseUtil.add_student(g.db, info_dict)
        return response
    except:
        return 'ERROR'


@app.route("/student/update/", methods=['POST'])
def update():
    try:
        student_info = request.data
        info_dict = json.loads(student_info)
        assert info_dict is dict
        response = DatabaseUtil.update_student(g.db, info_dict)
        return response
    except:
        return 'ERROR'


@app.route("/student/delete/", methods=['POST'])
def delete():
    try:
        student_info = request.data
        info_dict = json.loads(student_info)
        assert info_dict is dict
        response = DatabaseUtil.delete_student(g.db, info_dict)
        return response
    except:
        return 'ERROR'


@app.route('/redirect', methods=["GET", "POST"])
def dispaly_redirect_result():
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

        # shard based on first letter of student name
        index = (ord(student_name[0]) - 97) / 9
        return "Student: %s (ID: %d) will be direct to student service # %d/3" %(student_name, student_id, index)
    else:
        return "Error: request method is not POST!"

if __name__ == "__main__":
    #DatabaseUtil.setup_database(config.get('DATABASE_STUDENT'))
    app.run()