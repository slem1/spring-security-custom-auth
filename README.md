# README #

This sample application shows how to migrate a custom authentication token scheme to Spring Security.

The baseline java version is 1.8.

Build:

``` shell
mvn clean package
```

Run:

``` shell
java -jar target/spring-security-custom-auth-*.jar
```

Test:

``` shell
curl http://localhost:8080
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   114    0   114    0     0    383      0 --:--:-- --:--:-- --:--:--   383{"timestamp":"2019-02-14T12:15:29.977+0000","status":403,"error":"Forbidden","message":"Access Denied","path":"/"}
```

The default token value is: ``` abcd-efgh ```

``` shell
$ curl http://localhost:8080 --header "Auth-Token:abcd-efgh"
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100     8  100     8    0     0    500      0 --:--:-- --:--:-- --:--:--   500It works
```