# Use this cell to begin your analysis, and add as many as you would like!
# Importing useful libraries

import numpy as np
import pandas as  pd
import matplotlib.pyplot as plt

plt.rcParams['figure.figsize'] = [11, 7]

office_episodes = pd.read_csv('datasets/office_episodes.csv', parse_dates=['release_date'])

for indx, row in office_episodes.iterrows():
    if row['scaled_ratings'] < 0.25:
        office_episodes.loc[indx,'colors'] = 'red'
    elif row['scaled_ratings'] < 0.5:
        office_episodes.loc[indx,'colors'] = 'orange'
    elif row['scaled_ratings'] < 0.75:
        office_episodes.loc[indx,'colors'] = 'lightgreen'
    else:
        office_episodes.loc[indx,'colors'] = 'darkgreen'

office_episodes['size'] = [250 if row['has_guests'] == True else 25 for _, row in office_episodes.iterrows()]

fig = plt.figure()

plt.scatter(x=office_episodes['episode_number'],
            y=office_episodes['viewership_mil'],
            c=office_episodes['colors'],
            s=office_episodes['size']
           )

plt.title('Popularity, Quality, and Guest Appearances on the Office')
plt.xlabel('Episode Number')
plt.ylabel('Viewership (Millions)')
plt.show()

top_star = office_episodes[office_episodes['viewership_mil'] == office_episodes['viewership_mil'].max()]['guest_stars'].tolist()[0].split(', ')[0]
print(top_star)

