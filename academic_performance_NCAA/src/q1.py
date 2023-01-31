'''
Briggs Twitchell
DS5110
Dr. Bogden
1/26/23
Assignment #1 - NCAA APRS
'''

import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from readit import csv


aprs = csv('data/2020RES_APR2019PubDataShare.csv')

fig1, apr_distributions = plt.subplots()

# creating a boxplot based on the year as the x axis and APR score as y-axis
apr_distributions = sns.boxplot(data=aprs, x="Year", y="APR",)

# formatting the boxplot
apr_distributions.set_ylim(700, 1025)
[l.set_visible(False) for (i, l) in enumerate(
    apr_distributions.xaxis.get_ticklabels()) if i % 2 != 0]
apr_distributions.set_title(
    "APR Distributions - 2004 to 2019", fontdict={'fontsize': 20})

fig1.set_size_inches(20.5, 15.5)
fig1.savefig('figs/q1.png', dpi=100)
