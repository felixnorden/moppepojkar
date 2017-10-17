# Product Backlog

[Back to top](../README.md)

- [Product Backlog](#product-backlog)
    - [Epics](#epics)
    - [Themes](#themes)
    - [Elicited requirements](#elicited-requirements)
    - [User Stories](#user-stories)
    - [End Product](#end-product)
        - [Check later on](#check-later-on)

This document describes the key features that the Product Owner has in mind, in conjunction with the Teams.

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

### GUI

- Change GUI for more UX
- Armed state on startup

### Safety

## User Stories

This section splits the elicited requirements into smaller and more refined user stories that are used in the sprint backlogs.

The sub list of each listed item is the list of tasks that the user story has then been split into.

Disclaimer: The listed user stories are not in the order that they were implemented. Instead, they have been categorized in their corresponding epic feature. However, the user stories in each category is in a somewhat chronological order.

### Base platform

1. As a development team, we want to research the legacy code base to see what tools already exist
    - Research what SQUAWK's role is on the MOPED
    - Test how to use the python scripts in the MOPED from Java
1. As a development team, we want to create a project structure to manage all different modules
    - Plan UML Module structure of components
    - Create a project structure for the different modules
    - Add the project structure to GitHub
1. As a development team, we want a functioning system architecture to act as the core of the MOPED platform with a single entry point for external communication
    - Plan UML Core Domain
    - Add the Core Domain boilerplate
    - Implement the Core Domain
    - Implement CommunicationsMediator
1. As a development team, we want to make our platform handle requests from the mobile app, without the need of unnecessary extra features
    - Implement RemoteCommunicator
    - Handle received data from the app
    - Integrate updated app

### Adaptive Cruise Control

1. As a user, I want the driven vehicle to automatically keep a safe distance from the vehicle in front, as to allow me to focus more on the vehicle's lateral positioning


### Platooning

### GUI

### Safety

1. As a user, I want to be given necessary information about the vehicle to make critical decisions regarding safety
    - Determine useful data to send from MOPED
    - Store useful data for the app
    - Send useful data to the app
    - 

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