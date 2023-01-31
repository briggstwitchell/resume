'''
Briggs Twitchell
DS5110
Dr. Bogden
1/26/23
Assignment #1 - NCAA APRS
'''

import pandas as pd
import numpy as np

'''
Function to read in the csv file and filter/transform it for visualizations
'''
def csv(url = "https://ncaaorg.s3.amazonaws.com/research/academics/2020RES_APR2019PubDataShare.csv"):
    '''
    Use pandas to read CSV data from the url (default: ISL wage dataset in my rdata repo)
    '''
    print(f"reading data from {url}")

    aprs = pd.read_csv(url)
    
    #creating a list of the columns used in the analysis
    target_columns = [f'APR_RATE_{year}_1000' for year in range(2004,2020,1)]
    target_columns.insert(0,'SPORT_CODE')

    #filtering for just the columns I want
    aprs = aprs[target_columns]

    #converting years to integer
    for col in aprs.columns:
        if col.startswith("APR"):
            aprs = aprs.rename(columns={col:int(col[9:13])})

    #transforming table to long-form
    aprs =aprs.melt(id_vars =['SPORT_CODE'],var_name='Year', value_name='APR')
    aprs['SPORT_GENDER'] = np.where(aprs['SPORT_CODE']<=17,'Mens','Womens')

    return aprs
