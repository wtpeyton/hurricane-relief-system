# Hurricane Relief System

A JavaFX application concept for coordinating emergency assistance during and after hurricanes. It connects people who need help with volunteers and helps users find nearby shelters and available resources.

## How the system is organized

The class diagram describes the main parts of the system:

- **Users** have permissions that define their roles, such as registered user, volunteer, shelter administrator, or administrator. Volunteers may also have credentials and equipment.
- **Relief requests** record the request type, location, priority, description, requester, responders, and status. Volunteers can find nearby opportunities and respond to requests.
- **Shelters** provide location, capacity, accessibility, medical and veterinary services, pet acceptance, and available supplies.
- **Hurricanes** track a storm’s status and affected ZIP codes so the system can notify users in affected areas.
- **System services** coordinate user accounts, requests, shelters, and hurricane data; data loaders and writers provide JSON persistence.

## Project documents

- [Software requirements specification](docs/requirements.pdf)
- [UML class diagram](docs/uml-class-diagram.pdf)
- [User login and request submission sequence diagram](docs/uml-sequence-diagram1.pdf)
- [Volunteer login and response sequence diagram](docs/uml-sequence-diagram2.pdf)
- [Project board](https://github.com/wtpeyton/hurricane-relief-system/projects)

## Run locally

The application uses Java 11 and Maven. From the `hurricane_system` directory, run:

```sh
mvn javafx:run
```
