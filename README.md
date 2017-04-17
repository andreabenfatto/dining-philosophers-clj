# dining-philosophers

A simple exercise in Clojure inspired to the Dining Philosopher problem.

Some of my assumptions:
* The philosophers spend a random amount of time for eating (between 0-30 milliseconds) and thinking (between 0-50 milliseconds).
* The program stops as soon as the first philosopher at the table eat for x times (20).
* The amount of food on the table is limitless. An improved version of the program could assume a limited amount of portions: the program terminate when the food finish.

## License

Copyright © 2017 

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
