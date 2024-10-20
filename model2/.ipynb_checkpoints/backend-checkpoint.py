# -*- coding: utf-8 -*-
"""backend.ipynb

Automatically generated by Colab.

Original file is located at
    https://colab.research.google.com/drive/1U3TyvPtew_lhpBDMPv6_j4HLEeza2lCC
"""

from flask import Flask, request, jsonify, render_template
from transformers import BertTokenizer, BertForQuestionAnswering
import torch

app = Flask(__name__)

# Load the model and tokenizer
tokenizer = BertTokenizer.from_pretrained('fine_tuned_model')
model = BertForQuestionAnswering.from_pretrained('fine_tuned_model')

context = "This document discusses various political issues and positions in the 2024 elections, including economic policies, healthcare, and reform initiatives."

def answer_question(question):
    inputs = tokenizer(question, context, return_tensors='pt', truncation=True)

    with torch.no_grad():
        outputs = model(**inputs)

    answer_start = torch.argmax(outputs.start_logits)
    answer_end = torch.argmax(outputs.end_logits) + 1

    if answer_start >= answer_end:
        return "No answer found"

    answer_tokens = inputs['input_ids'][0][answer_start:answer_end]
    answer = tokenizer.convert_tokens_to_string(tokenizer.convert_ids_to_tokens(answer_tokens))
    return answer.strip()

@app.route('/')
def home():
    return render_template('index.html')

@app.route('/ask', methods=['POST'])
def ask():
    data = request.json
    question = data.get('question', '')
    answer = answer_question(question)
    return jsonify({'answer': answer})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)