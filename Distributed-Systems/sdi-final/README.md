# SDI Cooking

A distributed kitchen.

#### Running the project locally

Note: if you want to have a log of what is happening use `JAVA_LOG=true` before each command, for example:
`JAVA_LOG=true make chef-spawner`

* Compile
    ```shell
    make
    ```

* Run the Chef Spawner
    ```shell
    make chef-spawner
    ```

* Run the kitchen manager
    ```shell
    make kitchen
    ```
    It assumes that the input is in `mapa.in`, in the root of the project as the input map.

#### Running the project in more machines

* Fill the `ambiente.in` file

* Run the script
  ```shell
  ./cooking.sh
  #or
  ./B  
  ```
