__author__ = 'wangyijun'
import sqlite3
import os.path


# The code for DB connection
def connect_db(database_name):
    return sqlite3.connect(database_name)


def setup_database(database_name):
    CREATE_STUDENT = 'CREATE TABLE STUDENT ( ' \
                     'ID INTEGER AUTO INCREMENT, ' \
                     'NAME TEXT NOT NULL, ' \
                     'DOB TEXT NOT NULL, ' \
                     'MAJOR TEXT NOT NULL, ' \
                     'PRIMARY KEY(ID))'
    if not os.path.exists(database_name):
        conn = connect_db(database_name)
        cur = conn.cursor()
        cur.execute(CREATE_STUDENT)
        conn.commit()
        conn.close()


def search_student(db_conn, student_name):
    result = list()
    cur = db_conn.cursor()
    for row in cur.execute('select * from STUDENT where student_name = ?', student_name):
        result.append(row)
    return result


def add_student(db_conn, student_info):
    cur = db_conn.cursor()
    cur.execute('INSERT INTO STUDENT(NAME, DOB, MAJOR) VALUES(?, ?, ?)',
                student_info['student_name'], student_info['DOB'], student_info['major'])
    cur.commit()
    return cur.lastrowid


def update_student(db_conn, student_info):
    cur = db_conn.cursor()
    update_sql = 'update STUDENT set '
    if 'name' in student_info.keys():
        update_sql += 'name = ' + student_info['name'] + ', '
    if 'DOB' in student_info.keys():
        update_sql += 'DOB = ' + student_info['DOB'] + ', '
    if 'major' in student_info.keys():
        update_sql += 'major = ' + student_info['major']
    cur.execute(update_sql)
    cur.commit()
    return 'SUCCESS'


def delete_student(db_conn, student_info):
    # test
    cur = db_conn.cursor()
    delete_sql = 'delete from STUDENT where ID = ' + student_info['student_id']
    return 'yes'