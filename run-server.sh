#!/bin/bash
set -e
cd psi-server && mvn clean package && cd .. && docker-compose up --build