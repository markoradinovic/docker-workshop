### Exposing and Publishing ports

## Practice 1
- `docker run --name nginx -P -d nginx`
  - Try to access nginx in Browser - Which port?
- `docker run --name nginx -p :80 -d nginx`
  - what is the difference?
- `docker run --name nginx -p 8888:80 -d nginx`
  - try to access
- `docker run --name nginx -p 127.0.0.1::80 -d nginx`
- `docker run --name nginx -p 127.0.0.1:80:80 -d nginx`
