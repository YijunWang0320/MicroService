import DatabaseUtil
import json

from ast import literal_eval
from Config import Config
from flask import Flask, request, g, redirect, url_for, \
    abort, render_template, session

app = Flask(__name__)
config = Config('student_settings.cfg')
app.config['DEBUG'] = config.get('DEBUG') if config.get('DEBUG') is not None else False


@app.before_request
def before_request():
    g.db = DatabaseUtil.connect_db(config.get('DATABASE_STUDENT'))


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
    response = literal_eval(DatabaseUtil.search_student(g.db, info_dict))[0]
    return render_template('template.html', student_id=response[0], student_name=response[1],
                           DOB=response[2], major=response[3], operation='search')


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

@app.route("/student/check_exist/", methods=['post'])
def check_exist():
    student_info = request.args.get('data', '')
    info_dict = json.loads(student_info)
    assert type(info_dict) is dict
    response = DatabaseUtil.check_exist(g.db, info_dict)
    return response


if __name__ == "__main__":
    DatabaseUtil.setup_database(config.get('DATABASE_STUDENT'))
    app.run()