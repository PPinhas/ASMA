import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv('results.csv', header=None)

players = df.iloc[0].tolist()
num_players = len(players)

num_repetitions = 5

for i in range((len(df.index)-1)//(num_repetitions+1)):
    start = i*(num_repetitions+1)
    for j in range(num_players):
        agent = df.iloc[start+1, j]
        player_data = df.iloc[start+2:start+2+num_repetitions, j].astype(int)

        #plt.plot(player_data, label=f'{players[j]} ({agent})')
        plt.plot(range(len(player_data)), player_data, label=f'{players[j]} ({agent})')



    plt.title('Player Scores Over Time')
    plt.xlabel('Game Number')
    plt.ylabel('Score')
    plt.xticks(range(len(player_data)), ['Game {}'.format(x+1) for x in range(len(player_data))])
    plt.legend()
    plt.savefig(f'plots/plot_{i}.png')
    plt.show()

