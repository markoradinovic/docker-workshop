### Dockerfile

#### Best practices for writing Dockerfiles
Docker can build images automatically by reading the instructions from a Dockerfile, a text file that contains all the commands, in order, needed to build a given image.

##### General guidelines and recommendations
- *Containers should be ephemeral*
The container produced by the image your Dockerfile defines should be as ephemeral as possible. By “ephemeral,” we mean that it can be stopped and destroyed and a new one built and put in place with an absolute minimum of set-up and configuration.

- *Use a .dockerignore file*
In most cases, it’s best to put each Dockerfile in an empty directory. Then, add to that directory only the files needed for building the Dockerfile. To increase the build’s performance, you can exclude files and directories by adding a .dockerignore file to that directory as well. This file supports exclusion patterns similar to .gitignore files.

- *Avoid installing unnecessary packages*
In order to reduce complexity, dependencies, file sizes, and build times, you should avoid installing extra or unnecessary packages just because they might be “nice to have.” For example, you don’t need to include a text editor in a database image.

- *Each container should have only one concern*
Decoupling applications into multiple containers makes it much easier to scale horizontally and reuse containers. For instance, a web application stack might consist of three separate containers, each with its own unique image, to manage the web application, database, and an in-memory cache in a decoupled manner.

_You may have heard that there should be “one process per container”. While this mantra has good intentions, it is not necessarily true that there should be only one operating system process per container. In addition to the fact that containers can now be spawned with an init process, some programs might spawn additional processes of their own accord. For instance, Celery can spawn multiple worker processes, or Apache might create a process per request. While “one process per container” is frequently a good rule of thumb, it is not a hard and fast rule. Use your best judgment to keep containers as clean and modular as possible._

- *Minimize the number of layers*
You need to find the balance between readability (and thus long-term maintainability) of the Dockerfile and minimizing the number of layers it uses.

- *Sort multi-line arguments*
Whenever possible, ease later changes by sorting multi-line arguments alphanumerically. This will help you avoid duplication of packages and make the list much easier to update. This also makes PRs a lot easier to read and review. Adding a space before a backslash (\) helps as well.

  Here’s an example from the buildpack-deps image:

  RUN apt-get update && apt-get install -y \
    bzr \
    cvs \
    git \
    mercurial \
    subversion


- *Build cache*
During the process of building an image Docker will step through the instructions in your Dockerfile executing each in the order specified. As each instruction is examined Docker will look for an existing image in its cache that it can reuse, rather than creating a new (duplicate) image. If you do not want to use the cache at all you can use the --no-cache=true option on the docker build command.

  However, if you do let Docker use its cache then it is very important to understand when it will, and will not, find a matching image. The basic rules that Docker will follow are outlined below:

  * Starting with a base image that is already in the cache, the next instruction is compared against all child images derived from that base image to see if one of them was built using the exact same instruction. If not, the cache is invalidated.

  * In most cases simply comparing the instruction in the Dockerfile with one of the child images is sufficient. However, certain instructions require a little more examination and explanation.

  * For the ADD and COPY instructions, the contents of the file(s) in the image are examined and a checksum is calculated for each file. The last-modified and last-accessed times of the file(s) are not considered in these checksums. During the cache lookup, the checksum is compared against the checksum in the existing images. If anything has changed in the file(s), such as the contents and metadata, then the cache is invalidated.

  * Aside from the ADD and COPY commands, cache checking will not look at the files in the container to determine a cache match. For example, when processing a RUN apt-get -y update command the files updated in the container will not be examined to determine if a cache hit exists. In that case just the command string itself will be used to find a match.

  * Once the cache is invalidated, all subsequent Dockerfile commands will generate new images and the cache will not be used.
