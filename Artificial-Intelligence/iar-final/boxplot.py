import matplotlib.pyplot as plt 

x=[452.37871038399766,
    461.495786751614,
    462.93765338084256,
    450.90121890967623,
    461.73247503131057,
    462.56074752717694,
    446.1921700083918,
    466.8265962349784,
    468.85974462357126,
    456.31035647903593]

y=[24416.48125150414,
    25582.333537552222,
    24871.927366850534,
    24859.696341694125,
    24399.890554389665,
    23784.259717218225,
    24774.733961890353,
    24607.865690061746,
    25780.968511399078,
    24748.71841295537]

plt.boxplot(y)
plt.title("")
plt.show()