#

```
curl --location --request GET 'http://localhost:8080/api/job/start/Second Job' \
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

Note: Put the breakpoint in reader and start the app in debug mode, and then get the Job Execution Id from DB and and hit the below endpoint

```
http://localhost:8080/api/job/stop/32
```

Here 32 is Job Execution Id.