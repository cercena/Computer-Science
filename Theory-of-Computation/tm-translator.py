# Estrutura da função programa
transitions = [ [],[],[],[],[] ]
currentState = transitions[0]
currentSymbol = transitions[1]
newSymbol = transitions[2]
direction = transitions[3]
newState = transitions[4]

# Leitura do arquivo
file = open("input.in","r")
lines = file.readlines()
contLines = 0
file.close()

# Armazenamento parametrizado
for line in lines:
    auxList = line.split(' ')
    currentState.append(auxList[0])
    currentSymbol.append(auxList[1])
    newSymbol.append(auxList[2])
    direction.append(auxList[3])
    newState.append(auxList[4])
    contLines+=1

# Substituição de espaço branco
for i in range(contLines):
    if currentSymbol[i]=='_' and newSymbol[i]=='_':
        newSymbol[i] = '¢'
        #Nova transição contendo o símbolo substituto na escrita e leitura
        currentState.append(currentState[i])
        currentSymbol.append('¢')
        newSymbol.append('¢')
        direction.append(direction[i])
        newState.append(newState[i])
    elif currentSymbol[i] == '_':
        #Nova transição contendo o símbolo substituto apenas na leitura
        currentState.append(currentState[i])
        currentSymbol.append('¢')
        newSymbol.append(currentSymbol[i])
        direction.append(direction[i])
        newState.append(newState[i])
    elif newSymbol[i] == '_':
        newSymbol[i] = '¢'
contTrans = len(currentState)

# Remoção de movimento estacionário
stationary = []
for i in range(contTrans):
    if direction[i] == '*':
        #Halt-states não caracterizam mov. estacionário
        if newState[i].find("halt") == -1:
            #Marcação de transição com mov. estacionário 
            stationary.append(i)

symbols = ['0','1','¢']
for trans in stationary:
    #Mudança da transição atual à direita p/ um estado adicional
    aux = newState[trans]
    direction[trans] = 'r'
    newState[trans] = 'a'+currentState[trans]+'\n'
    #Novas transições c/ todos os símbolos partindo do estado adicional à esquerda p/ o próximo estado
    for sym in symbols:
        currentState.append(newState[trans].strip("\n"))
        currentSymbol.append(sym)
        newSymbol.append(sym)
        direction.append('l')
        newState.append(aux)

# Escrita do arquivo
file = open("output.out",'w')
contTrans = len(currentState)
for i in range(contTrans): 
    file.write(currentState[i]+" "+
               currentSymbol[i]+" "+
               newSymbol[i]+" "+
               direction[i]+" "+
               newState[i])
file.close()

"""
print (currentState[47])
print (currentSymbol[47])
print (newSymbol[47])
print (direction[47])
print (newStates[47])
"""