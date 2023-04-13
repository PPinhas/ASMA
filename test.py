import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv('results10rounds20Players.csv', header=None)

players = df.iloc[0].tolist()
num_players = len(players)

num_repetitions = 9

NUM_COLORS = 20
cm = plt.get_cmap('gist_rainbow')

for i in range((len(df.index)-1)//(num_repetitions+1)):
    start = i*(num_repetitions+1)
    fig = plt.figure()  # Moved the figure creation inside the loop
    ax = fig.add_subplot(111)
    ax.set_prop_cycle(color=[cm(1.*i/NUM_COLORS) for i in range(NUM_COLORS)])
    for j in range(num_players):
        agent = df.iloc[start+1, j]
        player_data = df.iloc[start+2:start+2+num_repetitions, j].astype(int)

        #plt.plot(player_data, label=f'{players[j]} ({agent})')
        ax.plot(range(len(player_data)), player_data, label=f'{players[j]} ({agent})')

    plt.title('Player Scores Over Time')
    plt.xlabel('Game Number')
    plt.ylabel('Score')

    plt.xticks(range(len(player_data)), ['Game {}'.format(x+1) for x in range(len(player_data))], fontsize=8, rotation=45)
    fig.legend()
    fig.savefig(f'plots/20players/plot_colors_{i}.png')
    plt.show()  # Moved the show function outside of the loop
