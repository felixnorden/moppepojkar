# Product Backlog

[Back to top](../README.md)

- [Product Backlog](#product-backlog)
    - [Epics](#epics)
    - [Themes](#themes)
    - [Elicited requirements](#elicited-requirements)
        - [Adaptive cruise control](#adaptive-cruise-control)
        - [Platooning](#platooning)
        - [App](#app)
        - [Safety](#safety)
    - [User Stories](#user-stories)
        - [Base platform](#base-platform)
            - [As a development team, we want to research the legacy code base to see what tools already exist](#as-a-development-team-we-want-to-research-the-legacy-code-base-to-see-what-tools-already-exist)
            - [As a development team, we want to create a project structure to manage all different modules](#as-a-development-team-we-want-to-create-a-project-structure-to-manage-all-different-modules)
            - [As a development team, we want to reuse the already existing python support to reuse the already existing code for maneuvering the vehicle](#as-a-development-team-we-want-to-reuse-the-already-existing-python-support-to-reuse-the-already-existing-code-for-maneuvering-the-vehicle)
            - [As a development team, we want a functioning system architecture to act as the core of the MOPED platform with a single entry point for external communication](#as-a-development-team-we-want-a-functioning-system-architecture-to-act-as-the-core-of-the-moped-platform-with-a-single-entry-point-for-external-communication)
            - [As a development team, we want to make our platform handle requests from the mobile app, without the need of unnecessary extra features](#as-a-development-team-we-want-to-make-our-platform-handle-requests-from-the-mobile-app-without-the-need-of-unnecessary-extra-features)
        - [Adaptive Cruise Control](#adaptive-cruise-control)
            - [As a product owner, I want the product to automatically move forward as to allow the driver to only focus on lateral control](#as-a-product-owner-i-want-the-product-to-automatically-move-forward-as-to-allow-the-driver-to-only-focus-on-lateral-control)
            - [As a development team, we want to make us of a PID in order to get more even and responsive reactions to speed change](#as-a-development-team-we-want-to-make-us-of-a-pid-in-order-to-get-more-even-and-responsive-reactions-to-speed-change)
            - [As a product owner, I want the product to autonomously adapt its speed to moving objects in front as to assure safety for the driver](#as-a-product-owner-i-want-the-product-to-autonomously-adapt-its-speed-to-moving-objects-in-front-as-to-assure-safety-for-the-driver)
            - [As a development team, we want to implement an efficient filter to effectively filter out bad sensor values, which entails a smoother response in the ACC](#as-a-development-team-we-want-to-implement-an-efficient-filter-to-effectively-filter-out-bad-sensor-values-which-entails-a-smoother-response-in-the-acc)
            - [As a user, I want the driven vehicle to automatically keep a safe distance from the vehicle in front, as to allow me to react in time if the vehicle cannot](#as-a-user-i-want-the-driven-vehicle-to-automatically-keep-a-safe-distance-from-the-vehicle-in-front-as-to-allow-me-to-react-in-time-if-the-vehicle-cannot)
        - [Platooning](#platooning)
        - [App](#app)
            - [As a user, I want to be able to switch between different driving modes with a click of a button as to easily choose how I want to drive](#as-a-user-i-want-to-be-able-to-switch-between-different-driving-modes-with-a-click-of-a-button-as-to-easily-choose-how-i-want-to-drive)
        - [Communication](#communication)
            - [As a development team, we want to communicate with the MOPED through the application without sending data that we do not need](#as-a-development-team-we-want-to-communicate-with-the-moped-through-the-application-without-sending-data-that-we-do-not-need)
            - [As a development team, we want the server and client to communicate under the same protocol as to maintain a communicative standard](#as-a-development-team-we-want-the-server-and-client-to-communicate-under-the-same-protocol-as-to-maintain-a-communicative-standard)
        - [Safety](#safety)
            - [As a user, I want to be given necessary information about the vehicle to make critical decisions regarding safety](#as-a-user-i-want-to-be-given-necessary-information-about-the-vehicle-to-make-critical-decisions-regarding-safety)
            - [As a user, I want the vehicle to always start in the same, neutral state as to avoid surprising or erratic behaviour when started](#as-a-user-i-want-the-vehicle-to-always-start-in-the-same-neutral-state-as-to-avoid-surprising-or-erratic-behaviour-when-started)
    - [End Product](#end-product)
        - [Check later on](#check-later-on)

This document describes the key features that the Product Owner has in mind, in conjunction with the team's interpretation of the PO's requests in the for om user stories.

## Epics

- Make the MOPED act without user input
- Implement Adaptive cruise control (longitudinal management)
- Implement Platooning feature (longitudinal & lateral management)

## Themes

- Autonomy
- Maneuverability
- Cooperation
- Safety

## Elicited requirements

### Adaptive cruise control

- Adapt velocity **(Main focus)**
- Turn on/off adaptive cruise from the RC/driver seat. **(Important)**
- Take obstacles into account **(Not of utmost importance)**
- Maneuverability is already implemented, not necessary to change **(YAGNI)**

- Max velocity
- Varying distance depending on velocity

### Platooning

- Safety first! Moving people will be on the test area. **(Important!)**
- Keep track of hindrances and avoid collisions **(Not of utmost importance)**
- First vehicle will be maneuvered manually, the rest will follow
- Allow switching of roles, from follower to lead role and vice versa:
    - All cars should be able to do these **(Not of utmost importance)**
    - Positive if column can be split somewhere in the middle when cars are to go to different locations **(Not of utmost importance)**
    - Turn on/off follow mode from the RC/driver seat **(Necessary for demo)**
- Decision making regarding the following vehicles:
    - Will steer away or stop when a collision cannot be steered off **(Not of utmost importance)**
- Make the MOPED follow another MOPED

### App

- Change GUI for more UX
- Armed state on startup

### Safety

## User Stories

This section splits the elicited requirements into smaller and more refined user stories that are used in the sprint backlogs.

The sub list of each listed item is the list of tasks that the user story has then been split into.

Disclaimer: The listed user stories are not in the order that they were implemented. Instead, they have been categorized in their corresponding epic feature. However, the user stories in each category is in a somewhat chronological order.

### Base platform

#### As a development team, we want to research the legacy code base to see what tools already exist

- Research what SQUAWK's role is on the MOPED

#### As a development team, we want to create a project structure to manage all different modules

- Plan UML Package structure for components
- Create a project structure for the different modules
- Add the project structure to GitHub

#### As a development team, we want to reuse the already existing python support to reuse the already existing code for maneuvering the vehicle

- Test how to use the python scripts in the MOPED from Java
- Implement basic functionality for controlling the MOPED through JAVA using the python scripts
- Test the boot-up of the MOPED to ascertain that the vehicle functions according to its predefined specifications

#### As a development team, we want a functioning system architecture to act as the core of the MOPED platform with a single entry point for external communication

- Plan UML Core Domain
- Add the Core Domain boilerplate
- Implement the Core Domain
- Implement CommunicationsMediator

#### As a development team, we want to make our platform handle requests from the mobile app, without the need of unnecessary extra features

- Implement RemoteCommunicator
- Handle received data from the app
- Integrate updated app

### Adaptive Cruise Control

#### As a product owner, I want the product to automatically move forward as to allow the driver to only focus on lateral control

- Implement an ACC behaviour to automatically throttle when activated

#### As a development team, we want to make us of a PID in order to get more even and responsive reactions to speed change

- Plan UML PID structure
- Implement PID structure
- Abstract the implementation for code reusability

#### As a product owner, I want the product to autonomously adapt its speed to moving objects in front as to assure safety for the driver

- Implement a PIDParser for the PID return values
- Integrate PID-class into the PIDParser
- Integrate the PIDParser into ACC behaviour

#### As a development team, we want to implement an efficient filter to effectively filter out bad sensor values, which entails a smoother response in the ACC

- Research about Kalman filtering
- Implement a simpler Low-pass filter
- Integrate the Low-pass filter into the DistanceSensor-class
- Implement a more advanced filter to reduce the response time

#### As a user, I want the driven vehicle to automatically keep a safe distance from the vehicle in front, as to allow me to react in time if the vehicle cannot

- Integrate a safe distance parameter for vehicle to strive for.

### Platooning

### App


#### As a user, I want to be able to switch between different driving modes with a click of a button as to easily choose how I want to drive

- Integrate state switching buttons into the app
- Lock app rotation
- 

### Communication

#### As a development team, we want to communicate with the MOPED through the application without sending data that we do not need

- Research alternative solutions for a server between the app and MOPED
- Plan UML for the Server/Client structure
- Implement Server class and interface
- Implement Client class and interface
- Create interface for the Core domain to implement

#### As a development team, we want the server and client to communicate under the same protocol as to maintain a communicative standard

- Implement a DataType enum protocol
- Implement parsing of received data from client

### Safety

#### As a user, I want to be given necessary information about the vehicle to make critical decisions regarding safety

- Determine useful data to send from MOPED
- Store useful data for the app
- Send useful data to the app

#### As a user, I want the vehicle to always start in the same, neutral state as to avoid surprising or erratic behaviour when started

- Implement a safe behaviour in the architecture
- Set core to always start in the safe behaviour

---

## End Product

The end goal is to produce column based driving demo, where one main vehicle is driven manually and the rest follow in line.

This solution will help with sustainability and human safety for the future.

Cars will converse with each other in SoS, but have to be functioning independently and can make decisions on their own. The demonstration will be made using all the different MOPEDs, that each utilize their own software, but can cooperate together.

### Check later on

- Preferred distance between MOPEDs, based on sensors
- Absolute start position using coordinates or something
- Specifics for preferred GUI/maneuverability
- SoS