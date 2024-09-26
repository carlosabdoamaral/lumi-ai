# @title Gráfico

import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd
import numpy as np
from matplotlib.pyplot import figure

def scatterPlot(results: list):
  # Buscando os dados
  data = results
  data = pd.DataFrame(data)
  data = data.sort_values(by=['sent_at'])

  # Filtrando eixos x e y
  sent_at = data['sent_at']
  sent_at = pd.to_datetime(sent_at, dayfirst=True)
  percent = data['compound']

  # Configurando o gráfico
  neutralMargin = 0.10

  colors = []
  for p in percent:
      if (neutralMargin * -1) < p < neutralMargin: # Neutral
          colors.append('black')
      elif p < 0.0: # Negative
          colors.append('red')
      else: # Positive
          colors.append('blue')

  fig, ax = plt.subplots(figsize=(8, 6))
  ax.scatter(sent_at, percent, color=colors)
  ax.set(
      xlabel='Horário (HH:ss)',
      ylabel='Porcentagem (%)',
      title='Resultado da análise'
  )
  ax.grid()
  ax.set_ylim([-1, 1])
  ax.set_yticks(np.arange(-1, 1.25, 0.25))

  ax.axhline(y=neutralMargin * -1, color='red', linestyle=':', label='Margem positiva netro')
  ax.axhline(y=neutralMargin, color='blue', linestyle=':', label='Margem negativa netro')

  ax.axhline(y=0.0, color='black', linestyle='-', label='Referência: 0.0')
  fig.savefig("chat_analysis_scatter_colored.png")

  fig = plt.figure()
  plt.show()