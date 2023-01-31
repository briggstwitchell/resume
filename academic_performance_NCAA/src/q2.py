import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from readit import csv

'''
Briggs Twitchell
DS5110
Dr. Bogden
1/26/23
Assignment #1 - NCAA APRS
'''

aprs = csv('data/2020RES_APR2019PubDataShare.csv')

fig2, apr_distributions = plt.subplots()

#creating a boxplot based on the year as the x axis and APR score as y-axis
apr_distributions = sns.boxplot(data=aprs,x="Year",y="APR",hue="SPORT_GENDER")

#formatting the boxplot
apr_distributions.set_ylim(750,1025)
[l.set_visible(False) for (i,l) in enumerate(apr_distributions.xaxis.get_ticklabels()) if i % 2 != 0]
apr_distributions.set_title("Men's vs Women's APR Distributions - 2004 to 2019",fontdict={'fontsize':20})
apr_distributions.set_xlabel('Year',fontsize=16)
apr_distributions.set_ylabel('APR',fontsize=16)

fig2.set_size_inches(18.5, 10.5)
fig2.savefig('figs/q2.png')
