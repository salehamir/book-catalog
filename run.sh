#!/bin/bash
start() {
  echo "Starting docker compose"
  sudo docker-compose up -d
}

stop() {
  echo "Stop docker compose"
  sudo docker-compose down
}
display_help() {
    echo 'Usage: run.sh [command]

      Available commands:
        - start:   Starts the application in detached mode using Docker Compose.
        - stop:    Stops the running application and removes containers, networks, and volumes created by Docker Compose.
        - restart: Calls the 'stop' command followed by the 'start' command to restart the application.

      Examples:
        To start the application:      run.sh start
        To stop the application:       run.sh stop
        To restart the application:    run.sh restart'

    echo
    # echo some stuff here for the -a or --add-options
    exit 1
}


case "$1" in
  start)
    start # calling function start()
    ;;
  stop)
    stop # calling function stop()
    ;;
  restart)
    stop  # calling function stop()
    start # calling function start()
    ;;
  *)
     display_help
     exit 1
     ;;
esac