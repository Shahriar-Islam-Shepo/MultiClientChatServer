# MultiClientChatServer 

A lightweight **multi-client chat application** built in Java using raw TCP sockets, `ObjectInputStream` / `ObjectOutputStream`, and per-client threading. The server assigns a unique name to each connecting client and routes private messages between them using a simple `sender__receiver__message` protocol.

---

##  Overview

JavaSocketChat demonstrates core Java networking and concurrency concepts:

- Socket-based client-server communication
- Object serialization over the network (`ObjectInputStream` / `ObjectOutputStream`)
- Multithreading — a dedicated **input thread** and **output thread** per client
- Thread-safe shared state using `ConcurrentHashMap`
- A simple directed messaging protocol (send a message to a specific user by name)

---

##  Architecture

```
┌─────────────┐        Socket        ┌─────────────────────┐
│   Client 1  │ ───────────────────► │                      │
│  (client)   │ ◄─────────────────── │                      │
└─────────────┘                      │                      │
                                      │       Main            
┌─────────────┐        Socket        │   (Server / Chat     
│   Client 2  │ ───────────────────► │      Router)         
│  (client)   │ ◄─────────────────── │                      │
└─────────────┘                      │  clientMap<name, OOS>│
                                      └─────────────────────┘
```

Each connected client is handled by a `clientMaker` thread, which spins up:
- an `inputThread` — continuously reads incoming messages and routes them
- an `outputThread` — reads local console input and sends it to the server

The server keeps a `ConcurrentHashMap<String, ObjectOutputStream>` (`clientMap`) mapping each client's assigned name to their output stream, allowing any client's message to be forwarded directly to the intended recipient.

---

##  Features

- ✅ Supports up to **6 simultaneous clients**, auto-assigned friendly names (`shepo`, `dipo`, `fijo`, `torun`, `maruf`, `nobin`)
- ✅ Direct (private) messaging between named clients
- ✅ Simple, human-readable message protocol
- ✅ Separate read/write threads per client for non-blocking communication
- ✅ Thread-safe client registry

---

##  Project Structure

```
MultiClientChatServer/
├── .idea/                      # IDE project settings
├── out/                        # Compiled output
├── src/
│   ├── Main.java                # Server entry point — accepts connections, assigns names, manages clientMap
│   ├── clientMaker.java          # Per-client thread that spawns input/output threads
│   ├── inputThread.java          # Reads incoming messages and routes them to the correct recipient
│   ├── outputThread.java         # Reads console input and sends messages to the server
│   └── client.java               # Client entry point — connects to the server
├── .gitignore
├── MultiClientChatServer.iml
└── README.md
```

---

##  Message Protocol

Messages sent from a client must follow this format:

```
senderName__receiverName__message
```

**Example:**
```
shepo__dipo__Hello, how are you?
```

The server parses this, looks up `dipo` in the `clientMap`, and forwards the message as:

```
shepo :Hello, how are you?
```

---

##  Getting Started

### Prerequisites

- Java JDK 8 or higher
- Any IDE (IntelliJ IDEA recommended) or the command line

### Running the Server

```bash
cd src
javac Main.java clientMaker.java inputThread.java outputThread.java
java Main
```

You should see:
```
server is started ... waiting for user ....
```

### Running a Client

In a separate terminal (repeat for each client, up to 6):

```bash
cd src
javac client.java inputThread.java outputThread.java
java client
```

Each client will receive a welcome message with their assigned name and the message format to use:

```
your name is : shepo
message format : senderName__receiverName__message
```

### Sending a Message

Type a message in the client console using the required format:

```
shepo__dipo__Hey there!
```

The `dipo` client will receive:

```
shepo :Hey there!
```

---

##  Screenshot 

![MultiClientChatServer]([img.png](https://github.com/Shahriar-Islam-Shepo/MultiClientChatServer/blob/e48f941d288bec9549c06e4e1ee43b871364b119/Screenshot%202026-07-14%20203108.png))

---

## 🛠 Tech Stack

| Technology | Purpose |
|---|---|
| Java | Core language |
| `java.net.Socket` / `ServerSocket` | TCP networking |
| `ObjectInputStream` / `ObjectOutputStream` | Object serialization over the network |
| `java.lang.Thread` | Concurrent handling of clients |
| `ConcurrentHashMap` | Thread-safe client registry |

---

##  Future Improvements

- [ ] Dynamic/custom usernames instead of a fixed pool of 6
- [ ] Broadcast (group) messaging support
- [ ] Graceful client disconnect handling and cleanup from `clientMap`
- [ ] GUI client (JavaFX / Swing) instead of console-based interaction
- [ ] Message encryption for secure communication
- [ ] Configurable host/port via command-line arguments or a config file
- [ ] Unit tests for message parsing and routing logic

---

##  Contributing

Contributions, issues, and feature requests are welcome. Feel free to fork the repo and submit a pull request.

---

##  License

This project is open source and available under the [MIT License](LICENSE).

---

##  Author

Developed as a learning project to explore Java socket programming and multithreading.
