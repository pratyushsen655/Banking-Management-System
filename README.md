# Banking Management System

## Overview

The **Banking Management System** is a comprehensive Java-based application with both desktop (Java Swing) and web (Spring Boot) interfaces. It handles banking operations such as account creation, login, deposits, withdrawals, transfers, and transaction history management.

## Repository Structure

This repository contains the complete Banking Management System in the `banking-system/` directory:
- **banking-system/desktop/** - Desktop application (Java Swing)
- **banking-system/web/** - Web application (Spring Boot)
- **banking-system/legacy/** - Legacy console-based banking files

All project code, documentation, and resources are organized within the `banking-system/` folder.

## Getting Started with Banking Management System

The main Banking Management System is located in the `banking-system/` directory.

### Quick Start

**Desktop Application:**
```bash
cd banking-system/desktop
./build.sh
java -cp target/classes com.banking.desktop.ui.LoginFrame
```

**Web Application:**
```bash
cd banking-system/web
mvn spring-boot:run
# Access at http://localhost:8080
```

### Default Test Credentials
- **Username:** admin
- **Password:** admin123

## Documentation

For complete documentation, please see:
- [banking-system/README.md](banking-system/README.md) - Complete overview
- [banking-system/QUICKSTART.md](banking-system/QUICKSTART.md) - Quick start guide
- [banking-system/desktop/README.md](banking-system/desktop/README.md) - Desktop app
- [banking-system/web/README.md](banking-system/web/README.md) - Web app with API docs
- [banking-system/IMPLEMENTATION_SUMMARY.md](banking-system/IMPLEMENTATION_SUMMARY.md) - Implementation details

## Features

### Desktop Application (Java Swing)
- Full-featured GUI with login, signup, dashboard, and transaction history
- In-memory data storage
- Real-time balance updates

### Web Application (Spring Boot + Thymeleaf)
- RESTful API with 10+ endpoints
- Session-based web interface
- Responsive design
- Complete banking operations

## Technologies

- Java 17
- Java Swing (Desktop)
- Spring Boot 3.1.5 (Web)
- Thymeleaf
- Maven

## Contributing

Feel free to fork the repository, make changes, and submit pull requests. All contributions and suggestions are welcome!

## License

This project is currently unlicensed. You may choose to add one as appropriate.

## Author

- [pratyushsen655](https://github.com/pratyushsen655)

---

For detailed information about the Banking Management System, see the [banking-system/](banking-system/) directory.