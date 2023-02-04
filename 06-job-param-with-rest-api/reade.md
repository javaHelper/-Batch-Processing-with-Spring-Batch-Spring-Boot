#

```
curl --location --request POST 'http://localhost:8080/api/job/start/Second Job' \
--header 'Content-Type: application/json' \
--data-raw '[
    {
        "paramKey": "abc",
        "paramValue": "abc123"
    },
    {
        "paramKey": "test",
        "paramValue": "test123"
    }
]'
```