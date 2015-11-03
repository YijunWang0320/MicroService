__author__ = 'wangyijun'
import datetime
import sqlite3
import os.path
import random

# The code for DB connection
def connect_db(database_name):
    return sqlite3.connect(database_name)


def setup_database(database_name):
    CREATE_STUDENT = 'CREATE TABLE STUDENT ( ' \
                     'ID TEXT PRIMARY KEY, ' \
                     'NAME TEXT NOT NULL, ' \
                     'DOB TEXT NOT NULL, ' \
                     'MAJOR TEXT NOT NULL);'
    if not os.path.exists(database_name):
        conn = connect_db(database_name)
        cur = conn.cursor()
        cur.execute(CREATE_STUDENT)
        conn.commit()
        conn.close()


def search_student(db_conn, student_info):
    result = list()
    flag = False
    cur = db_conn.cursor()
    search_sql = 'select * from student where '
    temp_list = list()
    if 'student_id' in student_info.keys():
        search_sql += 'ID = ?'
        flag = True
        temp_list.append(student_info['student_id'])
    if 'student_name' in student_info.keys():
        if flag:
            search_sql += 'and'
        search_sql += ' NAME = ?'
        flag = True
        temp_list.append(student_info['student_name'])
    if 'dob' in student_info.keys():
        if flag:
            search_sql += 'and'
        search_sql += ' DOB = ?'
        flag = True
        temp_list.append(student_info['dob'])
    if 'major' in student_info.keys():
        if flag:
            search_sql += 'and'
        search_sql += ' major = ?'
        temp_list.append(student_info['major'])
    cur.execute(search_sql, tuple(temp_list))
    for row in cur.fetchall():
        result.append(row)
    return str(result)


def add_student(db_conn, student_info):
    def random_id(student_name):
        letter = student_name[0:2] + str(random.randrange(1000, 9999))
        return letter

    def valid_date(dob):
        try:
            datetime.datetime.strptime(dob, '%Y-%m-%d')
            return True
        except:
            return False
    if len(student_info['student_name']) < 2:
        return 'Please input a name that have a first and a last name'
    if 'dob' not in student_info.keys() or 'major' not in student_info.keys():
        return 'Please provide the date of birth and major of the student'
    if not valid_date(student_info['dob']):
        return 'Please provide the dob in the format of YYYY-MM-DD'
    cur = db_conn.cursor()
    check_id = 'Not None'
    student_id = None
    while check_id is not None:
        student_id = random_id(student_info['student_name'])
        cur.execute('SELECT * FROM STUDENT WHERE ID = ?', (student_id, ))
        check_id = cur.fetchone()
    cur.execute('INSERT INTO STUDENT (ID, NAME, DOB, MAJOR) VALUES(?, ?, ?, ?)',
                (student_id, student_info['student_name'], student_info['dob'], student_info['major']))
    db_conn.commit()
    return 'The id of the added student is ' + str(student_id)


def update_student(db_conn, student_info):
    cur = db_conn.cursor()
    update_sql = 'update STUDENT set '
    if 'dob' in student_info.keys():
        update_sql += 'DOB = ' + '?'
    elif 'major' in student_info.keys():
        update_sql += 'major = ' + '?'
    update_sql += ' where id = ' + str(student_info['student_id'])
    print update_sql
    cur.execute(update_sql, student_info['dob'] if 'dob' in student_info.keys() else student_info['major'])
    db_conn.commit()
    return 'SUCCESS'


def delete_student(db_conn, student_info):
    cur = db_conn.cursor()
    delete_sql = 'delete from STUDENT where ID = ?'
    cur.execute(delete_sql, (student_info['student_id'],))
    db_conn.commit()
    return 'SUCCESS'


def check_exist(db_conn, student_info):
    cur = db_conn.cursor()
    check_exist_sql = 'select count(*) from student where ID = ? and name = ? '
    cur.execute(check_exist_sql, (student_info['student_id'], student_info['student_name'], ))
    result = cur.fetchone()[0]
    return 'False' if result == 0 else 'True'