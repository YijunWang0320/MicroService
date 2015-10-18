from flask import Flask, jsonify, request,url_for
import requests

app = Flask(__name__)
app.config["DEBUG"] = True

@app.route("/")
def hello():
    return "Hello, world!"

@app.route("/route/<method_name>/", methods = ["GET", "POST"])
def route(method_name):
    message =  "Redirect method: %s" %method_name
    print message
    return message


if __name__ == "__main__":
    app.run()