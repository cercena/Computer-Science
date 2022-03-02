#!/bin/bash

if [ -z "$1" ]
  then
    MAP_NAME=mapa.in
  else
    MAP_NAME=$1
fi

HOSTS_STR=$(grep Maquinas ambiente.in | cut -d ":" -f 2)

IFS=' ' read -ra HOSTS <<< "$HOSTS_STR"
for i in "${HOSTS[@]}"; do
  echo "$i"
done

USER="alu2020s2"
COMPILE_SCRIPT="make -s -C machado_igor/cooking"
RUN_CHEFS="make chef-spawner -C machado_igor/cooking"
KITCHEN_SCRIPT="make kitchen MAP_NAME=${MAP_NAME} HOSTS=${HOSTS[*]} -C machado_igor/cooking"

# Compile the project
ssh "${HOSTS[0]}" "${COMPILE_SCRIPT}"

# Run the chef spawners in the hosts
./mussh -m -h "${HOSTS[*]}" -c "${RUN_CHEFS}" > /dev/null &

sleep 10

# Runs the kitchen manager
ssh "${HOSTS[0]}" "${KITCHEN_SCRIPT}"
