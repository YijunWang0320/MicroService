import DatabaseUtil
import json
import datetime
import sys
from ast import literal_eval
from Config import Config
from flask import Flask, request, g, redirect, url_for, \
    abort, render_template, session

app = Flask(__name__)
config = Config('student_settings.cfg')
app.config['DEBUG'] = config.get('DEBUG') if config.get('DEBUG') is not None else False


@app.before_request
def before_request():
    g.db = DatabaseUtil.connect_db(config.get(service_number + ':DATABASE_STUDENT'))


@app.teardown_request
def after_request(exception=None):
    db = getattr(g, 'db', None)
    if db is not None:
        db.close()


# REST APIs
@app.route("/test", methods=["POST"])
def hello():
    return "You are in the student service right now!"


@app.route("/student/search/", methods=["POST"])
def search():
    student_info = request.args.get('data', '')
    info_dict = json.loads(str(student_info))
    assert type(info_dict) is dict
    if len(DatabaseUtil.search_student(g.db, info_dict)) == 2:
        return ''
    else:
        response = literal_eval(DatabaseUtil.search_student(g.db, info_dict))
        return render_template('template.html', response=response)


@app.route("/student/add/", methods=['POST'])
def add():
    student_info = request.args.get('data', '')
    info_dict = json.loads(str(student_info))
    assert type(info_dict) is dict
    response = DatabaseUtil.add_student(g.db, info_dict)
    return response


@app.route("/student/update/", methods=['POST'])
def update():
    student_info = request.args.get('data', '')
    info_dict = json.loads(str(student_info))
    assert type(info_dict) is dict
    response = DatabaseUtil.update_student(g.db, info_dict)
    return response


@app.route("/student/delete/", methods=['POST'])
def delete():
    student_info = request.args.get('data', '')
    info_dict = json.loads(str(student_info))
    assert type(info_dict) is dict
    response = DatabaseUtil.delete_student(g.db, info_dict)
    return response

@app.route("/student/check_exist/", methods=['POST'])
def check_exist():
    student_info = request.args.get('data', '')
    info_dict = json.loads(student_info)
    assert type(info_dict) is dict
    response = DatabaseUtil.check_exist(g.db, info_dict)
    return response


if __name__ == "__main__":
    if len(sys.argv) != 2:
        print 'Please input the service number'
        sys.exit(1)
    try:
        global service_number
        service_number = str(sys.argv[1])
    except:
        print 'The input service number not find'
        sys.exit(1)
    DatabaseUtil.setup_database(config.get(service_number + ':DATABASE_STUDENT'))
    app.run(host='0.0.0.0', port=int(config.get(service_number + ':PORT')))