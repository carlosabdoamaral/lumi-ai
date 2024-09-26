from flask import Flask, request, jsonify
from flask_cors import cross_origin
import sentiments

app = Flask(__name__)
analyzer = sentiments.Sentiment()

@app.route("/teste")
def index():
	return 'Olá Mundo!'

@app.route("/api/intelligence/sentiment", methods=['POST'])
def predictMessageSentiment():
    message = request.json['message']
    if message == None or message == '':
        return "Message not found", 400
    
    response = analyzer.singleMessage(f'{message}')
    return jsonify(response)

if __name__ == "__main__":
	app.run()
