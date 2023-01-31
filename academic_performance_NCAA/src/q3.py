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

fig3, ax = plt.subplots(2, 1)
fig3.tight_layout()

# creating an list of names that matches sport codes by index
sport_names = ['Baseball', "Men's Basketball", "Men's Cross Country", 'Football', "Men's Fencing", "Men's Golf", "Men's Gymnastics", "Men's Ice Hockey",
               "Men's Lacrosse", "Men's Skiing", "Men's Soccer", "Men's Swimming", "Men's Tennis", "Men's Track", "Men’s Volleyball", "Men's Water Polo", "Men's Wrestling",
               "Women's Basketball", "Women's Bowling", " Women's Cross Country", "Women's Rowing", "Women's Fencing", " Women's Field Hockey", "Women's Golf", "Women's Gymnastics",
               "Women's Ice Hockey", "Women's Lacrosse", "Women's Softball", "Women's Skiing", "Women's Soccer", "Women’s Sand Volleyball", "Women's Swimming",
               "Women's Tennis", "Women's Indoor Track", "Women's Volleyball", "Women's Water Polo", "Mixed Rifle"]

# boxplot for each mens sport
ax[0] = sns.boxplot(ax=ax[0], data=aprs,
                    x=aprs["SPORT_CODE"].loc[aprs['SPORT_CODE'] <= 17], y='APR')
# formatting for mens boxplot
ax[0].set_ylim(800, 1010)
ax[0].set_ylabel("APR", fontdict={"fontsize": 15})
ax[0].set_xlabel(None)
ax[0].set_title("APR Distributions by Sport (Mens)", fontdict={'fontsize': 20})
ax[0].set_xticklabels(labels=sport_names[0:17], rotation=30)

# boxplot for each womens sport
sns.boxplot(ax=ax[1], data=aprs,
            x=aprs["SPORT_CODE"].loc[aprs['SPORT_CODE'] > 17], y='APR')
# formatting for mens boxplot
ax[1].set_ylim(800, 1010)
ax[1].set_xlabel("Sport", fontdict={'fontsize': 15})
ax[1].set_ylabel("APR", fontdict={"fontsize": 15})
ax[1].set_title("APR Distributions by Sport (Womens)",
                fontdict={'fontsize': 20})
ax[1].set_xticklabels(labels=sport_names[17:], rotation=30)

fig3.set_size_inches(20.5, 15.5)
fig3.savefig('figs/q3.png')
