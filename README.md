# Sortable Challenge
Auction Coding Challenge for Sortable 

# Notes:

## Assumptions
 - In case of multiple entries for a site in `config.json` the last one is considered
 - The `config.json` and `input.json` files are valid
 - In the case of a tie, the bidder who placed his/her bid first wins the auction
 - The output is written to the stdout

## Building/Running the Application
 - Build using `docker build -t challenge .` from the `SortableChallenge` directory
 - Run using `docker run -i -v /path/to/challenge/config.json:/auction/config.json challenge < /path/to/challenge/input.json`
   from the `SortableChallenge` directory
    
## Problem statement
- The problem statement is in https://github.com/sortable/auction-challenge and is also copied into the `ProblemStatementReadMe.md` file. 

The sample `config.json`, `input.json` and `output.json` I used for testing are in the `resources` directory of the project. 
  

