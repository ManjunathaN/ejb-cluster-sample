`./run-server.sh`
`cd psi-client && mvn test`

on docker console you should be able to see -- implies that all the three servers are participating in cluster.
```
org.apache.openejb.server.ejbd.ClusterRequestHandler.processRequest Sending client updated cluster locations: [ejbd://172.28.1.1:4201, ejbd://172.28.1.2:4201, ejbd://172.28.1.3:4201]
```