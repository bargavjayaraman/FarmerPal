from app import app
from flask import jsonify
from flask import request
import sqlite3
from flask import g

DATABASE = '/home/sargeras/Desktop/Webservice/test.db'

def connect_db():
    return sqlite3.connect(DATABASE)

@app.before_request
def before_request():
    g.db = connect_db()

@app.after_request
def after_request(response):
    g.db.close()
    return response

def query_db(query, args=(), one=False):
    cur = g.db.execute(query, args)
    rv = [dict((cur.description[idx][0], value)
               for idx, value in enumerate(row)) for row in cur.fetchall()]
    return (rv[0] if rv else None) if one else rv


@app.route('/')
@app.route('/index')
def index():
    return "Hello, World!"

users = [
	{
		'id': 1
	}
     ]



@app.route('/home')
def home():
    
    for user in query_db('select * from tbl1'):
	user = {
		'id': users[-1]['id'] + 1,
		'username': user['username'],
		'user_id': user['user_id']
	       }
	users.append(user)
    return jsonify( { 'users': users } )

items = [
	{
		'id': 1
	}
     ]

@app.route('/getItems/', methods = ['GET'])
def get_items():
	for item in query_db('select * from market'):
		item = { 
			'item': item['item'],
			'lat': item['lat'],
			'long': item['long'],
			'price': item['price'],
			'desc': item['desc'],
			'bs': item['bs'],
			'id': item['id'],
			'price':item['price']
		       }
		items.append(item)
	return jsonify( { 'items': items } )


@app.route('/insert/', methods = ['POST'])
def insert_market():
	if not request.json or not 'item' in request.json:
        	return jsonify({})
	row = query_db('select * from market where item  = ? and id = ?',[request.json['item'], request.json['id']], one=True)
	if row is None:
		g.db.execute('insert or replace into market (item, lat, long, price, desc, bs, id) values (?, ?, ?, ?, ?, ?, ?)', [request.json['item'], request.json['lat'], request.json['long'], request.json['price'], request.json['desc'],request.json['bs'], request.json['id']])
		g.db.commit()
	return jsonify( { 'task': users } ), 201
	


tasks = [
    {
        'id': 1,
        'title': u'Buy groceries',
        'description': u'Milk, Cheese, Pizza, Fruit, Tylenol', 
        'done': False
    },
    {
        'id': 2,
        'title': u'Learn Python',
        'description': u'Need to find a good Python tutorial on the web', 
        'done': False
    }
]
@app.route('/tasks', methods = ['GET'])
@app.route('/tasks/', methods = ['GET'])
def get_tasks():
    return jsonify( { 'tasks': tasks } )



@app.route('/tasks/<int:task_id>', methods = ['GET'])
def get_task(task_id):
    task = filter(lambda t: t['id'] == task_id, tasks)
    if len(task) == 0:
        return "not found"
    return jsonify( { 'task': task[0] } )


@app.route('/tasks/', methods = ['POST'])
def create_task():
    if not request.json or not 'title' in request.json:
        abort(400)
    task = {
        'id': tasks[-1]['id'] + 1,
        'title': request.json['title'],
        'description': request.json.get('description', ""),
        'done': False
    }
    tasks.append(task)
    return jsonify( { 'task': task } ), 201


