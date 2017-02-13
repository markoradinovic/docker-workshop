### Manage data in containers

## Practice 1
- `docker run --name nginx -p 8888:80 -d nginx`
- `docker cp index.html nginx:/usr/share/nginx/html`
  - What just happend?

  ####Data volumes
  A data volume is a specially-designated directory within one or more containers that bypasses the Union File System. Data volumes provide several useful features for persistent or shared data:

  - Volumes are initialized when a container is created. If the container’s base image contains data at the specified mount point, that existing data is copied into the new volume upon volume initialization. (Note that this does not apply when mounting a host directory.)

  - Data volumes can be shared and reused among containers.
  - Changes to a data volume are made directly.
  - Changes to a data volume will not be included when you update an image.
  - Data volumes persist even if the container itself is deleted.

  Data volumes are designed to persist data, independent of the container’s life cycle. Docker therefore never automatically deletes volumes when you remove a container, nor will it “garbage collect” volumes that are no longer referenced by a container.

## Practice 2 - Adding a data volume - anonymous volume
- `docker run --name nginx -p 8888:80 -d -v /usr/share/nginx/html nginx`
- `docker cp index.html nginx:/usr/share/nginx/html`
  - Open index.html in browser
  - explain docker volume & docker inspect
- `docker rm -f nginx`
- `docker run --name nginx -p 8888:80 -d -v /usr/share/nginx/html nginx`
  - Open index.html in browser
  - What just happend?


## Practice 3 - Adding a data volume - named volume
- `docker run --name nginx -p 8888:80 -d -v html:/usr/share/nginx/html nginx`
- `docker cp index.html nginx:/usr/share/nginx/html`
  - Open index.html in browser
- `docker rm -f nginx`
- `docker run --name nginx -p 8888:80 -d -v html:/usr/share/nginx/html nginx`
  - Open index.html in browser
  - What just happend?

## Practice 4 - Mount a host directory as a data volume
- `docker run --name nginx -p 8888:80 -d -v nginx-html:/usr/share/nginx/html nginx`
  - Open index.html in browser
  - Edit `nginx-html/index.html` - refresh browser


### Removing volumes
A Docker data volume persists after a container is deleted. You can create named or anonymous volumes. Named volumes have a specific source form outside the container, for example awesome:/bar. Anonymous volumes have no specific source. When the container is deleted, you should instruct the Docker Engine daemon to clean up anonymous volumes. To do this, use the --rm option, for example:

`$ docker run --rm -v /foo -v awesome:/bar busybox top`
This command creates an anonymous /foo volume. When the container is removed, the Docker Engine removes the /foo volume but not the awesome volume.

## Practice 5
- `docker volume ls`
- `docker volume prune`
