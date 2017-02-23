### The Dockerfile instructions

*FROM*

Dockerfile reference for the FROM instruction

Whenever possible, use current Official Repositories as the basis for your image.

*ENV*

Dockerfile reference for the ENV instruction

In order to make new software easier to run, you can use ENV to update the PATH environment variable for the software your container installs. For example, `ENV PATH /usr/local/nginx/bin:$PATH` will ensure that `CMD [“nginx”]` just works.

The ENV instruction is also useful for providing required environment variables specific to services you wish to containerize.

Lastly, ENV can also be used to set commonly used version numbers so that version bumps are easier to maintain, as seen in the following example:

```
ENV PG_MAJOR 9.3
ENV PG_VERSION 9.3.4
RUN curl -SL http://example.com/postgres-$PG_VERSION.tar.xz | tar -xJC /usr/src/postgress && …
ENV PATH /usr/local/postgres-$PG_MAJOR/bin:$PATH
```

Similar to having constant variables in a program (as opposed to hard-coding values), this approach lets you change a single ENV instruction to auto-magically bump the version of the software in your container.

*ARG*

The ARG instruction defines a variable that users can pass at build-time to the builder with the docker build command using the ``--build-arg <varname>=<value>`` flag. If a user specifies a build argument that was not defined in the Dockerfile, the build outputs a warning.


*RUN*

Dockerfile reference for the RUN instruction

As always, to make your Dockerfile more readable, understandable, and maintainable, split long or complex RUN statements on multiple lines separated with backslashes.

apt-get

Probably the most common use-case for RUN is an application of apt-get. The RUN apt-get command, because it installs packages, has several gotchas to look out for.

You should avoid RUN apt-get upgrade or dist-upgrade, as many of the “essential” packages from the base images won’t upgrade inside an unprivileged container. If a package contained in the base image is out-of-date, you should contact its maintainers. If you know there’s a particular package, foo, that needs to be updated, use apt-get install -y foo to update automatically.

_Always combine RUN apt-get update with apt-get install in the same RUN statement, for example:_
```
RUN apt-get update && apt-get install -y \
    package-bar \
    package-baz \
    package-foo
```

_Using apt-get update alone in a RUN statement causes caching issues and subsequent apt-get install instructions fail. For example, say you have a Dockerfile:_
```
  FROM ubuntu:14.04
  RUN apt-get update
  RUN apt-get install -y curl
```

_After building the image, all layers are in the Docker cache. Suppose you later modify apt-get install by adding extra package:_
```
  FROM ubuntu:14.04
  RUN apt-get update
  RUN apt-get install -y curl nginx
```
_Docker sees the initial and modified instructions as identical and reuses the cache from previous steps. As a result the apt-get update is NOT executed because the build uses the cached version. Because the apt-get update is not run, your build can potentially get an outdated version of the curl and nginx packages._

_Using RUN apt-get update && apt-get install -y ensures your Dockerfile installs the latest package versions with no further coding or manual intervention. This technique is known as “cache busting”. You can also achieve cache-busting by specifying a package version. This is known as version pinning, for example:_

```
RUN apt-get update && apt-get install -y \
    package-bar \
    package-baz \
    package-foo=1.3.*
```

_Version pinning forces the build to retrieve a particular version regardless of what’s in the cache. This technique can also reduce failures due to unanticipated changes in required packages._

_Below is a well-formed RUN instruction that demonstrates all the apt-get recommendations._

```
RUN apt-get update && apt-get install -y \
    aufs-tools \
    automake \
    build-essential \
    curl \
    dpkg-sig \
    libcap-dev \
    libsqlite3-dev \
    mercurial \
    reprepro \
    ruby1.9.1 \
    ruby1.9.1-dev \
    s3cmd=1.1.* \
 && rm -rf /var/lib/apt/lists/*
```

The s3cmd instructions specifies a version 1.1.0*. If the image previously used an older version, specifying the new one causes a cache bust of apt-get update and ensure the installation of the new version. Listing packages on each line can also prevent mistakes in package duplication.

In addition, cleaning up the apt cache and removing /var/lib/apt/lists helps keep the image size down. Since the RUN statement starts with apt-get update, the package cache will always be refreshed prior to apt-get install.

_Note: The official Debian and Ubuntu images automatically run apt-get clean, so explicit invocation is not required._

###### Using pipes

Some RUN commands depend on the ability to pipe the output of one command into another, using the pipe character (|), as in the following example:

```
RUN wget -O - https://some.site | wc -l > /number

```
Docker executes these commands using the /bin/sh -c interpreter, which only evaluates the exit code of the last operation in the pipe to determine success. In the example above this build step succeeds and produces a new image so long as the wc -l command succeeds, even if the wget command fails.

If you want the command to fail due to an error at any stage in the pipe, prepend `set -o pipefail &&` to ensure that an unexpected error prevents the build from inadvertently succeeding. For example:
```
RUN set -o pipefail && wget -O - https://some.site | wc -l > /number
```

_Note: Not all shells support the -o pipefail option. In such cases (such as the dash shell, which is the default shell on Debian-based images), consider using the exec form of RUN to explicitly choose a shell that does support the pipefail option. For example:_
```
RUN ["/bin/bash", "-c", "set -o pipefail && wget -O - https://some.site | wc -l > /number"]
```

*ADD or COPY*

Dockerfile reference for the ADD instruction
Dockerfile reference for the COPY instruction

Although ADD and COPY are functionally similar, generally speaking, COPY is preferred. That’s because it’s more transparent than ADD. COPY only supports the basic copying of local files into the container, while ADD has some features (like local-only tar extraction and remote URL support) that are not immediately obvious. Consequently, the best use for ADD is local tar file auto-extraction into the image, as in `ADD rootfs.tar.xz /`.

If you have multiple Dockerfile steps that use different files from your context, COPY them individually, rather than all at once. This will ensure that each step’s build cache is only invalidated (forcing the step to be re-run) if the specifically required files change.

For example:
```
COPY requirements.txt /tmp/
RUN pip install --requirement /tmp/requirements.txt
COPY . /tmp/
```

Results in fewer cache invalidations for the RUN step, than if you put the COPY . /tmp/ before it.

Because image size matters, using ADD to fetch packages from remote URLs is strongly discouraged; you should use curl or wget instead. That way you can delete the files you no longer need after they’ve been extracted and you won’t have to add another layer in your image. For example, you should avoid doing things like:

```
ADD http://example.com/big.tar.xz /usr/src/things/
RUN tar -xJf /usr/src/things/big.tar.xz -C /usr/src/things
RUN make -C /usr/src/things all
```

And instead, do something like:

```
RUN mkdir -p /usr/src/things \
    && curl -SL http://example.com/big.tar.xz \
    | tar -xJC /usr/src/things \
    && make -C /usr/src/things all
```

For other items (files, directories) that do not require ADD’s tar auto-extraction capability, you should always use COPY.


*VOLUME*

Dockerfile reference for the VOLUME instruction

The VOLUME instruction should be used to expose any database storage area, configuration storage, or files/folders created by your docker container. You are strongly encouraged to use VOLUME for any mutable and/or user-serviceable parts of your image.

*EXPOSE*

Dockerfile reference for the EXPOSE instruction

The EXPOSE instruction indicates the ports on which a container will listen for connections. Consequently, you should use the common, traditional port for your application. For example, an image containing the Apache web server would use `EXPOSE 80`, while an image containing MongoDB would use `EXPOSE 27017` and so on.

For external access, your users can execute docker run with a flag indicating how to map the specified port to the port of their choice.


*CMD*

Dockerfile reference for the CMD instruction

The CMD instruction should be used to run the software contained by your image, along with any arguments. CMD should almost always be used in the form of `CMD [“executable”, “param1”, “param2”…]`. Thus, if the image is for a service, such as Apache and Rails, you would run something like `CMD ["apache2","-DFOREGROUND"]`. This form of the instruction is recommended for any service-based image.

In most other cases, CMD should be given an interactive shell, such as bash, python and perl. For example, `CMD ["perl", "-de0"]`, `CMD ["python"]`, or `CMD [“php”, “-a”]`. Using this form means that when you execute something like `docker run -it python`, you’ll get dropped into a usable shell, ready to go.

*ENTRYPOINT*

Dockerfile reference for the ENTRYPOINT instruction

The best use for ENTRYPOINT is to set the image’s main command, allowing that image to be run as though it was that command (and then use CMD as the default flags).

Let’s start with an example of an image for the command line tool s3cmd:

```
ENTRYPOINT ["s3cmd"]
CMD ["--help"]
```
Now the image can be run like this to show the command’s help:

```
$ docker run s3cmd
```

Or using the right parameters to execute a command:

```
$ docker run s3cmd ls s3://mybucket
```

This is useful because the image name can double as a reference to the binary as shown in the command above.

The ENTRYPOINT instruction can also be used in combination with a helper script, allowing it to function in a similar way to the command above, even when starting the tool may require more than one step.

For example, the Postgres Official Image uses the following script as its ENTRYPOINT:
```
#!/bin/bash
set -e

if [ "$1" = 'postgres' ]; then
    chown -R postgres "$PGDATA"

    if [ -z "$(ls -A "$PGDATA")" ]; then
        gosu postgres initdb
    fi

    exec gosu postgres "$@"
fi

exec "$@"
```
Note: This script uses the exec Bash command so that the final running application becomes the container’s PID 1. This allows the application to receive any Unix signals sent to the container.

```
COPY ./docker-entrypoint.sh /
ENTRYPOINT ["/docker-entrypoint.sh"]
```

This script allows the user to interact with Postgres in several ways.

It can simply start Postgres:

```
$ docker run postgres
```

Or, it can be used to run Postgres and pass parameters to the server:

```
$ docker run postgres postgres --help
```

Lastly, it could also be used to start a totally different tool, such as Bash:

```
$ docker run --rm -it postgres bash
```
