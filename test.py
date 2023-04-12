import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv('results.csv', header=None)

players = df.iloc[0].tolist()
num_players = len(players)

num_repetitions = 3

for i in range((len(df.index)-1)//(num_repetitions+1)):
    start = i*(num_repetitions+1)
    print("start is ", start)
    for j in range(num_players):
        agent = df.iloc[start+1, j]
        print("agent is ", agent)

        print("df.iloc[start+2] is ", df.iloc[start+2])
        player_data = df.iloc[start+2:start+2+num_repetitions, j].astype(int)
        print("player_data is ", player_data)
        plt.plot(player_data, label=f'{players[j]} ({agent})')


    plt.title('Player Scores Over Time')
    plt.xlabel('Game Number')
    plt.ylabel('Score')
    plt.legend()
    plt.show()