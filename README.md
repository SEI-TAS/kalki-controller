# Main Controller
 
## Prerequisites
 - Docker is required to compile and run this program.
 - The Kalki-db build env image should be created before compiling this program. You can find more details here: https://github.com/SEI-TTG/kalki-db
 - The Kalki-db test Postgres image should be created and running before compiling this program. You can find more details here: https://github.com/SEI-TTG/kalki-db 
 - The Kalki-db Postgres container has to be running for this program to work. You can find more details here: https://github.com/SEI-TTG/kalki-db
 
## Configuration
The config.json file has several configurable parameters. However, they usually do not need to be changed from their defaults. The parameters are:
 - <b>db_name, db_user, db_password</b>: need to be consistent with the actual DB information being used.
 
## Usage
To create the docker image for this program, execute the following command:

`bash build_container.sh`

To execute the program inside docker, execute the following command:

`bash run_compose.sh`

To see the logs:

`bash compose_logs.sh`

To stop it:

`bash stop_compose.sh`
