from nltk.classify import NaiveBayesClassifier
from nltk.corpus import subjectivity
from nltk.sentiment import SentimentAnalyzer
from nltk.sentiment.util import *
from nltk.sentiment.vader import SentimentIntensityAnalyzer
from nltk import tokenize
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from googletrans import Translator
import seaborn as sns
from datetime import datetime
from time import sleep
from zoneinfo import ZoneInfo
from transformers import pipeline

class Sentiment:
  def __init__(self) -> None:
    nltk.download('subjectivity')
    nltk.download('vader_lexicon')
    nltk.download('punkt')
    nltk.download('punkt_tab')
    
    self.datasetRecords = pd.read_csv('dataset.csv', encoding='utf-8', sep=';')
    self.sentences = self.datasetRecords['frase'].tolist()
    self.paragraph = "It was a beautiful day, and everything seemed to go well at first. I enjoyed my morning coffee, and the weather was perfect for a walk. However, as the day went on, things took a turn for the worse. The meeting at work was stressful, and nothing seemed to go as planned. By the end of the day, I was exhausted and frustrated. But when I got home, my family was there waiting for me with smiles and warm hugs, which made everything feel better."
    self.n_instances = 100
    self.subj_docs = [(sent, 'subj') for sent in subjectivity.sents(categories='subj')[:self.n_instances]]
    self.obj_docs = [(sent, 'obj') for sent in subjectivity.sents(categories='obj')[:self.n_instances]]  
    
    self.all_docs = self.subj_docs + self.obj_docs
    training_docs, testing_docs = train_test_split(self.all_docs, test_size=0.5, random_state=42)
    
    self.sentim_analyzer = SentimentAnalyzer()
    self.all_words_neg = self.sentim_analyzer.all_words([mark_negation(doc) for doc in training_docs])

    self.unigram_feats = self.sentim_analyzer.unigram_word_feats(self.all_words_neg, min_freq=4)
    self.sentim_analyzer.add_feat_extractor(extract_unigram_feats, unigrams=self.unigram_feats)
    
    self.training_set = self.sentim_analyzer.apply_features(training_docs)
    self.test_set = self.sentim_analyzer.apply_features(testing_docs)
    self.trainer = NaiveBayesClassifier.train
    self.classifier = self.sentim_analyzer.train(self.trainer, self.training_set)
    
    self.lines_list = tokenize.sent_tokenize(self.paragraph)
    self.sentences.extend(self.lines_list)
    
    self.results = []
    self.translator = Translator()

  def generatePercent(self, result):
      pos = result['positive']
      neu = result['neutral']
      neg = result['negative']

      total = pos + neu + neg
      if total == 0: return 50  # Se nenhum valor estiver presente, retorno neutro

      # Nova fórmula: foco em (pos - neg) sem o ajuste de 50
      percent = (pos - neg) * 100 / total

      # Garantindo que fique no intervalo de 0 a 100
      if percent < 0: percent = 0
      if percent > 100: percent = 100

      return percent


  def doAnalysis(self, sentenceTranslated:str, sentenceOriginal:str) -> object:
    sid = SentimentIntensityAnalyzer()
    ss = sid.polarity_scores(sentenceTranslated)
    result = {
        'sentence': {
          'original': sentenceOriginal,
          'translated': sentenceTranslated
        },
        'negative': ss['neg'],
        'neutral': ss['neu'],
        'positive': ss['pos'],
        'compound': ss['compound'],
        'sent_at': datetime.now(ZoneInfo("America/Sao_Paulo")).strftime("%d/%m/%Y %H:%M:%S")
    }
    result['percent'] = self.generatePercent(result)
    return result


  def analysisWithInput(self, maxInteractions: int = 3) -> list:
    count = 1
    while count <= maxInteractions:
      userSentence = input('O que você está sentindo?')
      userSentence = self.translator.translate(userSentence, src='pt', dest='en').text
      result = self.doAnalysis(userSentence)
      self.results.append(result)
      count += 1
    return self.results

  def singleMessage(self, message: str) -> list:
    sentenceOriginal = message
    sentenceTranslated = self.translator.translate(message, src='pt', dest='en').text
    result = self.doAnalysis(sentenceTranslated, sentenceOriginal)
    return result