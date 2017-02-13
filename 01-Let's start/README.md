### Docker CLI basics

#### Docker Image Basics
* Pull first image from Docker Hub
* Explain docker image operations

#### Docker Container Basics
* Run first container
* start/stop/kill/rm container
* start/attach/exec/diff/commit

#### Docker Image tagging/push Image to registry
* Explain Docker Image naming convention
* Tag Image and push to Docker Hub


## Practice 1
- docker pull nginx
- docker image ls
- docker image inspect
- docker image history
- docker image rm

## Practice 2
- docker search alpine
- docker run alpine echo "Hello World"
- docker run docker run -it alpine sh
- docker run -d nginx
- docker exec -it CONTAINER_ID /bin/bash
- docker tag nginx:latest my-nginx:0.0.1
- docker tag nginx:latest my-nginx:latest
- docker login
- docker push my-nginx:0.0.1
