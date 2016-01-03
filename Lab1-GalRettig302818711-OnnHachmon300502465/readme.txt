
WebServer:
Flow #1:
ReadConfFile -> open server listener with conf object

ServerListener:
Flow #2:
Open Socket -> Read Input -> Check For Body in Requests -> Create Http Request (handle request) -> Create A HTTP Response -> handle response (print the response and send the files)

Flow #3
ServerSocket -> Init thread pool -> receive Tasks to data collection -> pull task and assign it to thread
