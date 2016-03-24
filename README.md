# cs304
Gym Database

## To get connected to the database
1. Start an ssh tunnel:
```
ssh -l f1c0b -L localhost:<LOCAL PORT TO USE>:dbhost.ugrad.cs.ubc.ca:1522 remote.ugrad.cs.ubc.ca -N
```
2. Create a secrets.txt file in the project root (this won't go in the repository) with the following (and nothing else):
  * The local port you chose for your ssh tunnel
  * Your Oracle Username
  * Your Oracle Password

This assumes your current working directory is the test_jdbc directory, this is what Eclipse defaults to.
