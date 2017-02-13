### Docker container networking

### Preparation
- `docker pull markoradinovic/alpine-util`


## Legacy container links

Before the Docker networks feature, you could use the Docker link feature to allow containers to discover each other and securely transfer information about one container to another container.

With the introduction of the Docker networks feature, you can still create links but they behave differently between default bridge network and user defined networks

**Warning: The --link flag is a deprecated legacy feature of Docker. It may eventually be removed. Unless you absolutely need to continue using it, we recommend that you use user-defined networks to facilitate communication between two containers instead of using --link.**

## Practice 1
- `docker run --name nginx -P -d nginx`
- `docker run --rm -it --link nginx markoradinovic/alpine-util bash`
- `curl http://nginx`
  - What just happend?
- `docker run --rm -it --link nginx:web markoradinovic/alpine-util bash`
- `curl http://web`
- `cat /etc/host`
  - What just happend?


## Launch a container on the default network
Docker includes support for networking containers through the use of network drivers. By default, Docker provides two network drivers for you, the bridge and the overlay drivers. You can also write a network driver plugin so that you can create your own drivers but that is an advanced task.

Every installation of the Docker Engine automatically includes three default networks. You can list them:
```
$ docker network ls

NETWORK ID          NAME                DRIVER
18a2866682b8        none                null
c288470c46f6        host                host
7b369448dccb        bridge              bridge
```
The network named bridge is a special network. Unless you tell it otherwise, Docker always launches your containers in this network.

## Practice 2
- `docker network ls`
- `docker run -itd --name=networktest alpine sh`
- `docker inspect bridge`
  - What just happend?
- `docker rm -f CONTAINER_ID`
- `docker inspect bridge`
  - What just happend?

## Practice 3
- `docker network create -d bridge --attachable my-bridge-network`
- `docker run --name nginx -d --net my-bridge-network nginx `
- `docker run --rm -it --net my-bridge-network markoradinovic/alpine-util bash `
- `curl http://web`
- `cat /etc/host`
  - What just happend?
