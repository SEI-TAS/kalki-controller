# Main Controller

The Kalki Main Controller is the main engine of the Control node of Kalki. It listens for new alerts, and updates the security state of each device according to the configured policy rules, deploying security measures as needed.

Kalki is an IoT platform for allowing untrusted IoT devices to connect to a network in a secure way, protecting both the IoT device and the network from malicious attackers.

Kalki comprises a total of 8 GitHub projects:
- kalki-node-setup (Kalki Main Repository, composes all non-UI components)
- kalki-controller (Kalki Main Controller)
- kalki-umbox-controller (Kalki Umbox Controller)
- kalki-device-controller (Kalki Device Controller)
- kalki-dashboard (Kalki Dashboard)
- kalki-db (Kalki Database Library)
- kalki-iot-interface (Kalki IoT Interface)
- kalki-umboxes (Kalki Umboxes, sample umboxes and umboxes components)
 
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
