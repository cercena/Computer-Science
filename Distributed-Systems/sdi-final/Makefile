
# default params
MAP_NAME?=mapa.in
HOSTS?=""

all: create_out_file
	@rm -f out/paths.txt
	@find -name "*.java" >> out/paths.txt
	@javac @out/paths.txt -d out -cp src
	@#echo "compiled the project successfully"

kitchen: all
	@java -cp out com.udesc.kitchen.KitchenManager ${MAP_NAME} "${HOSTS[*]}"

chef-spawner: all
	@java -cp out com.udesc.chefs.ChefSpawnerPublisher

teste:
	@echo ${MAP_NAME} ${HOSTS}

create_out_file:
	@mkdir -p out

clean:
	rm -rf out
