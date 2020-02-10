# Laplacian Event Hub Service

## Running the application

### Run test cases

```console
$ ./script/local-test.sh

BUILD SUCCESSFUL in 6s
4 actionable tasks: 2 executed, 2 up-to-date
```

### Launch the service locally

```console
$ ./script/local-serve.sh &
[1] 5575
$
[1]  + suspended (tty input)  ./script/local-serve.sh
```

## Developing this application

### VSCode settings

Append the following entry to your `.vscode/settings.json`

```json
{
  "kotlin.compiler.jvm.target": "1.8"
}
```
