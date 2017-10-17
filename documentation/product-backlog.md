# Product Backlog

[Back to top](../README.md)

- [Product Backlog](#product-backlog)
    - [Epics](#epics)
    - [Themes](#themes)
    - [Elicited requirements](#elicited-requirements)
        - [Adaptive cruise control](#adaptive-cruise-control)
        - [Platooning](#platooning)
        - [GUI](#gui)
        - [Safety](#safety)
    - [User Stories](#user-stories)
        - [Adaptive Cruise Control](#adaptive-cruise-control)
        - [Platooning](#platooning)
        - [GUI](#gui)
        - [Safety](#safety)
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

### Adaptive Cruise Control

1. As a development team, we want to research the legacy code base to see what tools already exist
    - Research what SQUAWK's role is on the MOPED
    - Test how to use the python scripts in the MOPED from Java
    - 
2. As a development team, we want to create a system architecture to structure the project
    - Plan 
    - Create a project structure for the different modules
    - Add the project structure to GitHub
    - 

### Platooning

### GUI

### Safety

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