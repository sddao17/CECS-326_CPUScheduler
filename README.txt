

CECS-326 Project 4 - CPU Scheduling


-----------------------------------------------
Authors
-----------------------------------------------


* Carlos Verduzco  (018718282)

* Steven Dao  (017503055)


-----------------------------------------------
Usage notes
-----------------------------------------------


* Purpose: To implement the logic of various CPU scheduling algorithms. The algorithms that we dive into in this project include: FCFS, Priority, and RR.

* Supported by Java-based applications.

* JDK version 9.0+ recommended.

* No installations required.


-----------------------------------------------
Compile / Run Instructions
-----------------------------------------------


------------------------
For Java-supported IDEs:
------------------------


* Load the source folder containing the various `CPUScheduler` files into the IDE of your choice.

* In the `Run` tab of the navigational tabs, select the option that would allow you to edit the run configuration. In the `Program Arguments` section of the run configuration, input and adjust the following arguments according to your desired algorithm and scheduling file: `<schedule> <algorithm>`.


* Compile and then run the `Main.java` file.


--------------------------------
For Command Prompts / Terminals:
--------------------------------


* On the command prompt / terminal, change the current directory to the path of the source folder containing the various `CPUScheduler` files. You can do this with the command `cd <insert path here>`. The path should end with a pattern similar to the following : `...\src`.

* Compile the `Main.java` file with the command: `javac Main.java`.

* Run the `Main.java` file with the adjusted command, remembering to replace the algorithm and scheduler placeholders: `java Main <algorithm> <schedule>`. You should see the simulation of the chosen scheduling algorithm using the input schedule file.

