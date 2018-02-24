#!/bin/bash
now=$(date +%Y%m%d)
command='java -Xms128m -Xmx2048m -jar oim-server-boot.jar'
log_file_url="logs/catalina.out"

if [ "$log_file_url" != "" ]; then
	exec $command  > "$log_file_url" &
else
	exec $command &
fi