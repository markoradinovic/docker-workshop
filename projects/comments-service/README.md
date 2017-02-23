### Comments Service

You will need [Elasticsearch](https://hub.docker.com/_/elasticsearch/) and optionally [Kibana](https://hub.docker.com/_/kibana/) with SenseUI.

*Add comment*

POST /api/v1/comment
```
Header Authorization Bearer
```

```
{
	"topicId": "bd2f7658-f1c6-42bb-84ba-77ba6aa2291f",
	"comment": "This is my simple comment"
}
```

*Get Comment by id*

GET /api/v1/comment/AVpslTXYYhXmk738L--F

```
Header Authorization Bearer
```

*Get Comment by topicId*

GET /api/v1/comment/topic/bd2f7658-f1c6-42bb-84ba-77ba6aa2291f

```
Header Authorization Bearer
```
