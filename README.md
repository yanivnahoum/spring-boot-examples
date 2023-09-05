## Azure Feature Management

The following properties have to be set (via program args, system properties, env vars, etc.):
* azure.appconfig.enabled=true
* azure.appconfig.connection-string=<connection-string-from-azure-portal>

To test if a feature is enabled, send an http request to `http://localhost:8080/features/check/<feature-name>`.
<br>
Example using httpie:
```
http :8080/features/check/feature1
http :8080/features/check/feature2
```