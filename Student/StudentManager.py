import DatabaseUtil
import json
from Config import Config
from flask import Flask, request, g, redirect, url_for, \
    abort, render_template,  session

app = Flask(__name__)
config = Config('student_settings.cfg')
app.config['DEBUG'] = config.get('DEBUG') if 'DEBUG' in config.keys() else False


@app.before_request
def before_request():
    g.db = DatabaseUtil.connect_db()


@app.teardown_request
def after_request():
    db = getattr(g, 'db', None)
    if db is not None:
        db.close()


# REST APIs
@app.route("/test")
def hello():
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

@app.route("student/add/", methods=['POST'])
def add():
    try:
        student_info = request.data
        info_dict = json.loads(student_info)
        assert info_dict is dict
        response = DatabaseUtil.add_student(g.db, info_dict)
        return response
    except:
        return 'ERROR'

@app.route("student/update/", method=['POST'])
def update():
    try:
        student_info = request.data
        info_dict = json.loads(student_info)
        assert info_dict is dict
        response = DatabaseUtil.update_student(g.db, info_dict)
        return response
    except:
        return 'ERROR'

@app.route("student/delete/", method=['POST'])
def delete():
    try:
        student_info = request.data
        info_dict = json.loads(student_info)
        assert info_dict is dict
        response = DatabaseUtil.delete_student(g.db, info_dict)
        return response
    except:
        return 'ERROR'



if __name__ == "__main__":
    DatabaseUtil.setup_database(config.get('DATABASE_STUDENT'))
    app.run()