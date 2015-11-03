__author__ = 'wangyijun'
import sqlite3
import os.path
import json


# The code for DB connection
def connect_db(database_name):
    return sqlite3.connect(database_name)


def setup_database(database_name):
    CREATE_STUDENT = 'CREATE TABLE STUDENT ( ' \
                     'ID INTEGER PRIMARY KEY ASC, ' \
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
    cur = db_conn.cursor()
    cur.execute('INSERT INTO STUDENT (NAME, DOB, MAJOR) VALUES(?, ?, ?)',
                (student_info['student_name'], student_info['dob'], student_info['major']))
    db_conn.commit()
    return 'The id of the added student is ' + str(cur.lastrowid)


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